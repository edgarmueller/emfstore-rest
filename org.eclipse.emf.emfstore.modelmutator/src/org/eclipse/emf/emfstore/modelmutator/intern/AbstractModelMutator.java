/*******************************************************************************
 * Copyright (c) 2008-2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.modelmutator.intern;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.emfstore.modelmutator.api.ModelMutatorConfiguration;
import org.eclipse.emf.emfstore.modelmutator.api.ModelMutatorUtil;

/**
 * Basic implementation of the {@link org.eclipse.emf.emfstore.modelmutator.api.ModelMutator}.
 * 
 * @author Julian Sommerfeldt
 * 
 */
public abstract class AbstractModelMutator {

	private ModelMutatorConfiguration config;

	private Map<EReference, List<EClass>> referencesToClasses = new LinkedHashMap<EReference, List<EClass>>();

	// randomly eobjects are saved into this map and are used later instead of creating new ones
	private Map<EClass, List<EObject>> freeObjects = new LinkedHashMap<EClass, List<EObject>>();

	private Map<EClass, List<EObject>> allObjects = new LinkedHashMap<EClass, List<EObject>>();

	private ModelMutatorUtil util;

	private int currentObjectCount;

	private int targetObjectCount;

	private int currentWidth = 1;

	private int currentDepth = 1;

	/**
	 * @param config The {@link ModelMutatorConfiguration} to use for mutation.
	 */
	public AbstractModelMutator(ModelMutatorConfiguration config) {
		this.config = config;
		this.util = new ModelMutatorUtil(config);
		this.targetObjectCount = config.getMinObjectsCount();
	}

	/**
	 * Called before generation and mutation.
	 */
	public abstract void preMutate();

	/**
	 * Called after generation and mutation.
	 */
	public abstract void postMutate();

	/**
	 * Initial generation.
	 */
	public void generate() {
		preMutate();

		// generate till therer are enough objects
		Random random = config.getRandom();
		while (currentObjectCount < targetObjectCount) {
			createChildrenForRoot();
			currentWidth++;
			if (random.nextBoolean() && random.nextBoolean() && random.nextBoolean()) {
				currentDepth++;
			}
		}

		postMutate();
	}

	/**
	 * Mutation after an initial generation.
	 */
	public void mutate() {
		deleteEObjects(config.getRootEObject());

		currentObjectCount = ModelMutatorUtil.getAllObjectsCount(config.getRootEObject());

		generate();

		changeCrossReferences();

		mutateAttributes();
	}

	/**
	 * Create the children for the root object.
	 */
	public void createChildrenForRoot() {
		// if the root depth should not be generated
		if (config.isDoNotGenerateRoot()) {
			// create children for each of the children of the root
			for (EObject obj : config.getRootEObject().eContents()) {
				createChildren(obj, 1);
			}
		} else {
			// if the root depth should be generated, create children for the root
			createChildren(config.getRootEObject(), 0);
		}
	}

	/**
	 * Create recursively direct and indirect children for a given {@link EObject} and its children.
	 * 
	 * @param root The {@link EObject} for which children should be generated.
	 * @param depth The depth of the EObject in the total tree.
	 */
	public void createChildren(EObject root, int depth) {
		if (currentObjectCount >= targetObjectCount) {
			return;
		}

		// check if we reached the maximal depth
		if (depth >= currentDepth) {
			return;
		}

		// create children for the current root object
		List<EObject> children = createChildren(root);

		// create children for the children (one step deeper)
		for (EObject obj : children) {
			createChildren(obj, depth + 1);
		}
	}

	/**
	 * Creates/deletes direct children for the given {@link EObject}.
	 * 
	 * @param root The {@link EObject} for which children should be created.
	 * @return A list of the newly generated children.
	 */
	public List<EObject> createChildren(EObject root) {
		List<EObject> children = new ArrayList<EObject>();
		Collection<EStructuralFeature> ignore = config.geteStructuralFeaturesToIgnore();
		Random random = config.getRandom();

		// iterate over all references
		for (EReference reference : root.eClass().getEAllContainments()) {

			// check if the reference is valid: not to be ignored AND can be set/added
			if (ignore.contains(reference) || !util.isValid(reference, root)) {
				continue;
			}

			// add remaining children (specified through config)
			int i = (currentWidth / 2) - root.eContents().size();

			// add children to fulfill width constraint
			for (; i > 0; i--) {
				EClass eClass = getValidEClass(reference);
				if (eClass != null) {
					EObject obj = getEObject(eClass);

					// randomly first changeCrossReferences
					if (random.nextBoolean()) {
						changeCrossReferences(obj);
					}

					// add directly to parent or add to free objects
					// only add if it is many or if it is only one
					if ((reference.isMany() || i == 1)) {
						addToParent(root, obj, reference);
						children.add(obj);
						currentObjectCount++;
						currentObjectCount += obj.eContents().size();
					} else {
						addToEClassToObjectsMap(obj, freeObjects);
					}

					addToEClassToObjectsMap(obj, allObjects);

					if (currentObjectCount >= targetObjectCount) {
						return children;
					}
				}
			}
		}

		return children;
	}

	/**
	 * Randomly deletes direct and indirect children of the given root {@link EObject}.
	 * 
	 * @param root The {@link EObject} from which children should be deleted.
	 */
	public void deleteEObjects(EObject root) {
		List<EObject> toDelete = new ArrayList<EObject>();
		Random random = config.getRandom();
		
		// randomly select objects to delete		
		for (TreeIterator<EObject> it = root.eAllContents(); it.hasNext();) {
			EObject obj = it.next();
			if (random.nextBoolean()) {
				toDelete.add(obj);				
				addToEClassToObjectsMap(obj, freeObjects);
			}
			addToEClassToObjectsMap(obj, allObjects);
		}

		// check for delete modes to use
		List<Integer> deleteModes = new ArrayList<Integer>();
		deleteModes.add(ModelMutatorUtil.DELETE_DELETE_COMMAND);
		deleteModes.add(ModelMutatorUtil.DELETE_CUT_CONTAINMENT);
		if (config.isUseEcoreUtilDelete()) {
			deleteModes.add(ModelMutatorUtil.DELETE_ECORE);
		}
		
		// delete selected objects
		int size = deleteModes.size();
		for (EObject obj : new ArrayList<EObject>(toDelete)) {
			util.removeFullPerCommand(obj, deleteModes.get(random.nextInt(size)));
		}
	}

	private void addToEClassToObjectsMap(EObject obj, Map<EClass, List<EObject>> map) {
		List<EObject> objects = map.get(obj.eClass());
		if (objects == null) {
			objects = new ArrayList<EObject>();
			map.put(obj.eClass(), objects);
		}
		objects.add(obj);
	}

	/**
	 * Get a {@link EClass}, which is valid for the given {@link EReference}.
	 * 
	 * @param reference The {@link EReference} to search a {@link EClass} for.
	 * @return A valid {@link EClass} for the given {@link EReference} or <code>null</code> if there is none.
	 */
	protected EClass getValidEClass(EReference reference) {
		// get already saved classes list
		List<EClass> classes = referencesToClasses.get(reference);

		// if there is none, create it
		if (classes == null) {

			// get all classes of the modelpackage
			classes = util.getAllEContainments(reference);

			// check if they should be ignored
			for (EClass eClass : config.geteClassesToIgnore()) {
				classes.remove(eClass);
				classes.removeAll(util.getAllSubEClasses(eClass));
			}

			// remove classes which cannot have an instance
			for (EClass eClass : new ArrayList<EClass>(classes)) {
				if (!ModelMutatorUtil.canHaveInstance(eClass)) {
					classes.remove(eClass);
				}
			}

			// return null if there is no valid class
			if (classes.isEmpty()) {
				return null;
			}

			// save for future use
			referencesToClasses.put(reference, classes);
		}

		// randomly select one class
		int index = config.getRandom().nextInt(classes.size());
		return classes.get(index);
	}

	/**
	 * Creates a new {@link EObject} and sets its attributes.
	 * 
	 * @param eClass The {@link EClass} of the new {@link EObject}.
	 * @return The newly created and modified {@link EObject}.
	 */
	protected EObject getEObject(EClass eClass) {
		Random random = config.getRandom();
		EObject newObject = null;

		// try to get an already existing object if there is one
		List<EObject> objects = freeObjects.get(eClass);
		if (objects != null && objects.size() != 0 && random.nextBoolean()) {
			newObject = objects.remove(random.nextInt(objects.size()));
		} else {
			newObject = EcoreUtil.create(eClass);
		}

		util.setEObjectAttributes(newObject);
		return newObject;
	}

	/**
	 * Adds an {@link EObject} to the given parent.
	 * 
	 * @param parent The {@link EObject} where to add the newObject
	 * @param newObject The new {@link EObject} to add to the parent.
	 * @param reference The {@link EReference} where to add the newObject.
	 */
	private void addToParent(EObject parent, EObject newObject, EReference reference) {
		Random random = config.getRandom();
		if (reference.isMany()) {
			util.addPerCommand(parent, reference, newObject, random.nextBoolean() ? 0 : null);
		} else {
			util.setPerCommand(parent, reference, newObject);
		}
	}

	/**
	 * Randomly mutates all attributes.
	 */
	public void mutateAttributes() {
		for (TreeIterator<EObject> it = config.getRootEObject().eAllContents(); it.hasNext();) {
			EObject obj = (EObject) it.next();
			util.setEObjectAttributes(obj);
		}
	}

	/**
	 * Changes CrossReferences for all {@link EObject}s of the model.
	 */
	public void changeCrossReferences() {
		for (Entry<EClass, List<EObject>> entry : allObjects.entrySet()) {
			for (EObject obj : entry.getValue()) {
				changeCrossReferences(obj);
			}
		}
	}

	/**
	 * Changes CrossReferences of an {@link EObject}.
	 * 
	 * @param obj The {@link EObject} where to change the CrossReferences.
	 */
	public void changeCrossReferences(EObject obj) {
		for (EReference reference : util.getValidCrossReferences(obj)) {
			for (EClass referenceClass : util.getReferenceClasses(reference, allObjects.keySet())) {
				util.setReference(obj, referenceClass, reference, allObjects);
			}
		}
	}
}
