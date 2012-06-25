/*******************************************************************************
 * Copyright (c) 2008-2012 EclipseSource Muenchen GmbH,
 * All rights reserved. This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.modelmutator.intern;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.emfstore.modelmutator.api.ModelMutatorConfiguration;
import org.eclipse.emf.emfstore.modelmutator.api.ModelMutatorUtil;

/**
 * The base class for generating and changing a model.
 * 
 * @author Eugen Neufeld
 * @author Stephan K?hler
 * @author Philip Achenbach
 * @author Dmitry Litvinov
 */
public abstract class AbstractModelMutator {

	/**
	 * The configuration that is used in the process.
	 */
	private final ModelMutatorConfiguration configuration;

	
	/**
	 * The constructor for the model mutation process.
	 * @param config the configuration that is used
	 */
	public AbstractModelMutator(ModelMutatorConfiguration config) {
		this.configuration = config;
	}

	/**
	 * This function is called before the mutation is applied.
	 */
	public abstract void preMutate();

	/**
	 * This function is called after the mutation is applied.
	 */
	public abstract void postMutate();

	/**
	 * The mutation function.
	 */
	public void mutate() {
		preMutate();
		
		setContaintments();

		changeCrossReferences(Integer.MAX_VALUE);
		
		postMutate();
	}

	public void changeAttributes(int maxNumber) {
		int currentDepth;
		List<EObject> parentsInThisDepth;
		if (!configuration.isDoNotGenerateRoot()) {
			currentDepth = 0;
			parentsInThisDepth = new ArrayList<EObject>(1);
			parentsInThisDepth.add(configuration.getRootEObject());
		} else { // We can skip the root level if it is provided by the configuration
			currentDepth = 1;
			parentsInThisDepth = new ArrayList<EObject>(configuration.getRootEObject().eContents());
		}
		
		// Use a breadth-first search (BFS) to generate all children/containments
		while (currentDepth < configuration.getDepth()) {
			// for all parent EObjects in this depth
			for (EObject nextParentEObject : parentsInThisDepth) {
				maxNumber -= changeAttributes(nextParentEObject, maxNumber);
				if (maxNumber <= 0) {
					return;
				}
			}

			// proceed to the next level
			List<EObject> parentsInTheNextDepth = new ArrayList<EObject>();
			for (EObject nextParentEObject : parentsInThisDepth) {
				parentsInTheNextDepth.addAll(nextParentEObject.eContents());
			}
			currentDepth++;
			parentsInThisDepth = parentsInTheNextDepth;
		}

	}

	protected int changeAttributes(EObject parentEObject, int maxNumber) {
		int numAttrLeft = maxNumber;
		for (EObject curChild : parentEObject.eContents()) {
			if (configuration.getRandom().nextBoolean()) {
				numAttrLeft -= ModelMutatorUtil.setEObjectAttributes(curChild, configuration.getRandom(), configuration.getExceptionLog(), configuration.isIgnoreAndLog(), numAttrLeft);
				if (numAttrLeft <= 0) {
					return maxNumber;
				}
			}
		}
		return maxNumber - numAttrLeft;
	}
	
	public void createEObjects(int maxNumber) {
		int currentDepth;
		List<EObject> parentsInThisDepth;
		if (!configuration.isDoNotGenerateRoot()) {
			currentDepth = 0;
			parentsInThisDepth = new ArrayList<EObject>(1);
			parentsInThisDepth.add(configuration.getRootEObject());
		} else { // We can skip the root level if it is provided by the configuration
			currentDepth = 1;
			parentsInThisDepth = new ArrayList<EObject>(configuration.getRootEObject().eContents());
		}
		
		// Use a breadth-first search (BFS) to generate all children/containments
		while (currentDepth < configuration.getDepth()) {
			// for all parent EObjects in this depth
			List<EObject> parentsInTheNextDepth = new ArrayList<EObject>();
			for (EObject nextParentEObject : parentsInThisDepth) {
				List<EObject> children = createEObjects(nextParentEObject, currentDepth==0 && configuration.isAllElementsOnRoot(), maxNumber);
				// will the just created EObjects have children?
				parentsInTheNextDepth.addAll(children);
			}

			// proceed to the next level
			currentDepth++;
			parentsInThisDepth = parentsInTheNextDepth;
		}
	}
	
	protected List<EObject> createEObjects(EObject parentEObject, boolean generateAllReferences, int maxNumber) {
		Map<EReference, List<EObject>> currentContainments = ModelMutatorUtil.getCurrentContainments(parentEObject);

		List<EObject> result = new ArrayList<EObject>();
		List<EReference> references = new ArrayList<EReference>();
		//generate the children of the current element so that the lower bound holds or that there is a child of each sort 
		for (EReference reference : parentEObject.eClass().getEAllContainments()) {
			if (configuration.geteStructuralFeaturesToIgnore().contains(reference)
					|| !ModelMutatorUtil.isValid(reference, parentEObject, configuration.getExceptionLog(), configuration.isIgnoreAndLog())) {
				continue;
			}
			references.add(reference);
			int numCurrentContainments = 0;
			List<EObject> list = currentContainments.get(reference);
			if (list != null) {
				numCurrentContainments = list.size();
			}
			
			List<EObject> contain=null;
			int maxNumToGen = maxNumber - result.size();
			if (generateAllReferences) {
				contain = generateFullDifferentContainment(parentEObject, reference, maxNumToGen);
			} else {
				contain = generateMinContainments(parentEObject, reference, Math.min(reference.getLowerBound() - numCurrentContainments, maxNumToGen));
			}
			
			result.addAll(contain);
			if (result.size() >= maxNumber) {
				return result;
			}
			if (list == null) {
				list = new ArrayList<EObject>();
				currentContainments.put(reference, list);
			}
			list.addAll(contain);
		}
		// fill up the references where more elements are needed
		if (references.size() != 0) {
			for (int i = result.size(); i < configuration.getWidth() && references.size() != 0; i++) {
				int refIndex = configuration.getRandom().nextInt(references.size());
				EReference reference = references.get(refIndex);
				int upperBound = Integer.MAX_VALUE;
				if (reference.getUpperBound()!=EReference.UNBOUNDED_MULTIPLICITY && reference.getUpperBound()!=EReference.UNSPECIFIED_MULTIPLICITY) {
					upperBound = reference.getUpperBound();
				}
				List<EObject> list = currentContainments.get(reference);
				if (list.size() < upperBound) {
					List<EObject> contain = generateMinContainments(parentEObject, reference, 1);
					list.addAll(contain);
					result.addAll(contain);
				} else {
					references.remove(refIndex);
					i--;
				}
			}
		}
		return result;
	}	

	public void deleteEObjects(int maxNumber) {
		int currentDepth;
		List<EObject> parentsInThisDepth;
		if (!configuration.isDoNotGenerateRoot()) {
			currentDepth = 0;
			parentsInThisDepth = new ArrayList<EObject>(1);
			parentsInThisDepth.add(configuration.getRootEObject());
		} else { // We can skip the root level if it is provided by the configuration
			currentDepth = 1;
			parentsInThisDepth = new ArrayList<EObject>(configuration.getRootEObject().eContents());
		}
		
		// Use a breadth-first search (BFS) to generate all children/containments
		while (currentDepth < configuration.getDepth()) {
			// for all parent EObjects in this depth
			for (EObject nextParentEObject : parentsInThisDepth) {
				maxNumber -= deleteEObjects(nextParentEObject, maxNumber);
				if (maxNumber <= 0) {
					return;
				}
			}

			// proceed to the next level
			List<EObject> parentsInTheNextDepth = new ArrayList<EObject>();
			for (EObject nextParentEObject : parentsInThisDepth) {
				parentsInTheNextDepth.addAll(nextParentEObject.eContents());
			}
			currentDepth++;
			parentsInThisDepth = parentsInTheNextDepth;
		}
	}
	
	/**
	 * Delete child EObjects of the given parent
	 * 
	 * @param parentEObject
	 *            the EObject to delete children from
	 * @param maxNumber
	 * 			  Maximal number of EObjects to delete
	 * @return number of deleted EObjects
	 */
	protected int deleteEObjects(EObject parentEObject, int maxNumber) {
		List<EObject> toDelete=new ArrayList<EObject>();
		//If the current element contains children, delete them randomly
		for (EObject curChild : parentEObject.eContents()) {
			if (configuration.getRandom().nextBoolean()) {
				toDelete.add(curChild);
				if (toDelete.size() == maxNumber) {
					break;
				}
			}
		}
		//delete randomly selected elements
		for(EObject curChild : toDelete){
			ModelMutatorUtil.removeFullPerCommand(curChild, configuration.getExceptionLog(), configuration.isIgnoreAndLog());
		}
		return toDelete.size();
	}
	
	public void changeContainmentReferences(int maxNumber) {
		int currentDepth;
		List<EObject> parentsInThisDepth;
		if (!configuration.isDoNotGenerateRoot()) {
			currentDepth = 0;
			parentsInThisDepth = new ArrayList<EObject>(1);
			parentsInThisDepth.add(configuration.getRootEObject());
		} else { // We can skip the root level if it is provided by the configuration
			currentDepth = 1;
			parentsInThisDepth = new ArrayList<EObject>(configuration.getRootEObject().eContents());
		}
		
		// Use a breadth-first search (BFS) to go through all children/containments
		while (currentDepth < configuration.getDepth()) {
			if (parentsInThisDepth.size() > 1) {
				//build a map of containment EObjects for better performance
				Map<EObject, Map<EReference, List<EObject>>> allContainments = new LinkedHashMap<EObject, Map<EReference, List<EObject>>>();
				for (EObject parentEObject : parentsInThisDepth) {
					allContainments.put(parentEObject, ModelMutatorUtil.getCurrentContainments(parentEObject));
				}
				
				// for all parent EObjects in this depth
				for (EObject nextParentEObject : parentsInThisDepth) {
					maxNumber -= changeContainmentReferences(nextParentEObject, allContainments, maxNumber);
					if (maxNumber <= 0) {
						return; 
					}
				}
			}

			// proceed to the next level
			List<EObject> parentsInTheNextDepth = new ArrayList<EObject>();
			for (EObject nextParentEObject : parentsInThisDepth) {
				parentsInTheNextDepth.addAll(nextParentEObject.eContents());
			}
			currentDepth++;
			parentsInThisDepth = parentsInTheNextDepth;
		}		
	}
	
	protected int changeContainmentReferences(EObject parentEObject, Map<EObject, Map<EReference, List<EObject>>> allContainments, int maxNumber) {
		Map<EReference, List<EObject>> currentContainments = allContainments.get(parentEObject);
		List<EObject> parentsInThisDepth = new ArrayList<EObject>(allContainments.keySet());

		List<EReference> references = new ArrayList<EReference>();
		//generate the children of the current element so that the lower bound holds or that there is a child of each sort 
		for (EReference reference : parentEObject.eClass().getEAllContainments()) {
			if (configuration.geteStructuralFeaturesToIgnore().contains(reference)
					|| !ModelMutatorUtil.isValid(reference, parentEObject, configuration.getExceptionLog(), configuration.isIgnoreAndLog())) {
				continue;
			}
			references.add(reference);
			List<EObject> list = currentContainments.get(reference);
			if (list == null) {
				list = new ArrayList<EObject>(0);
				currentContainments.put(reference, list);
			}
		}
		
		int numRefMoved = 0;
		EClass parentEClass = parentEObject.eClass();
		// fill up the references where more elements are needed
		for (EReference reference : references) {
			List<EObject> srcList = currentContainments.get(reference);
			if (srcList.size() <= reference.getLowerBound()) {
				continue;
			}
			
			if (parentEObject.eIsSet(reference)) {
				//check whether to change reference or not
				if(configuration.getRandom().nextBoolean()){
					//find destination parent, where objects will be moved
					Collections.shuffle(parentsInThisDepth, configuration.getRandom());
					EObject dstParentEObject = null;
					for (EObject curEObject : parentsInThisDepth) {
						if (!curEObject.equals(parentEObject) && parentEClass.isSuperTypeOf(curEObject.eClass())) {
							dstParentEObject = curEObject;
							break;
						}
					}
					if (dstParentEObject == null) {
						continue;
					}
					
					//do different stuff, depending on reference type
					if(reference.isMany()){
						List<EObject> toMove=new ArrayList<EObject>();
//						EList<EObject> srcList = (EList<EObject>)parentEObject.eGet(reference);
						//check whether to delete references randomly or all at once 
						if(configuration.getRandom().nextBoolean()){
							for(EObject refObj : srcList){
								//check whether to delete this reference
								if(configuration.getRandom().nextBoolean()){
									toMove.add(refObj);
								}
							}
						}
						else{
							toMove.addAll(srcList);
						}
//						ModelMutatorUtil.removePerCommand(parentEObject, reference, toMove, configuration.getExceptionLog(), configuration.isIgnoreAndLog());
						ModelMutatorUtil.addPerCommand(dstParentEObject, reference, toMove,
							configuration.getExceptionLog(), configuration.isIgnoreAndLog());
					}
					else {
//						EObject child = (EObject)parentEObject.eGet(reference);
						EObject child = srcList.get(0);
						ModelMutatorUtil.setPerCommand(dstParentEObject, reference, child,
							configuration.getExceptionLog(), configuration.isIgnoreAndLog());
					}
					if (++numRefMoved >= maxNumber) {
						return numRefMoved;
					}					
				}
			}
		}
		return numRefMoved;
	}
	
	/**
	 * This function generates the Containments of a model.
	 */
	public void setContaintments() {
		int currentDepth;
		List<EObject> parentsInThisDepth;
		if (!configuration.isDoNotGenerateRoot()) {
			currentDepth = 0;
			parentsInThisDepth = new ArrayList<EObject>(1);
			parentsInThisDepth.add(configuration.getRootEObject());
		} else { // We can skip the root level if it is provided by the configuration
			currentDepth = 1;
			parentsInThisDepth = new ArrayList<EObject>(configuration.getRootEObject().eContents());
		}
		List<EObject> parentsInTheNextDepth = new ArrayList<EObject>();
		
		// Use a breadth-first search (BFS) to generate all children/containments
		while (currentDepth < configuration.getDepth()) {
			// for all parent EObjects in this depth
			for (EObject nextParentEObject : parentsInThisDepth) {
				//ModelMutatorUtil.setEObjectAttributes(nextParentEObject, configuration.getRandom(), configuration.getExceptionLog(), configuration.isIgnoreAndLog());
				List<EObject> children = generateChildren(nextParentEObject, currentDepth==0 && configuration.isAllElementsOnRoot());
				// will the just created EObjects have children?
				parentsInTheNextDepth.addAll(children);
			}

			// proceed to the next level
			currentDepth++;
			parentsInThisDepth = parentsInTheNextDepth;
			parentsInTheNextDepth = new ArrayList<EObject>();
		}
	}

	/**
	 * Generates children for a certain parent EObject. Generation includes
	 * setting containment references and attributes. All required references
	 * are set first, thus the specified width might be exceeded.
	 * 
	 * @param parentEObject
	 *            the EObject to generate children for
	 * @param generateAllReferences
	 * 			  Should we generate every EObject on root level
	 * @return all children (old and new) as a list
	 * @see #generateContainments(EObject, EReference, int)
	 */
	protected List<EObject> generateChildren(EObject parentEObject, boolean generateAllReferences) {
		Map<EReference, List<EObject>> currentContainments = new HashMap<EReference, List<EObject>>();
		List<EObject> result = new ArrayList<EObject>();
		List<EObject> toDelete=new ArrayList<EObject>();
		//If the current element contains already children, delete them randomly or count them 
		for (EObject curChild : parentEObject.eContents()) {
			if(configuration.getRandom().nextBoolean()){
				toDelete.add(curChild);
				continue;
			}
			EReference containment = curChild.eContainmentFeature();
			List<EObject> list = currentContainments.get(containment);
			if (list == null) {
				list = new ArrayList<EObject>();
				currentContainments.put(containment, list);
			}
			list.add(curChild);
			if (configuration.getRandom().nextBoolean()) {
				ModelMutatorUtil.setEObjectAttributes(curChild, configuration.getRandom(), configuration.getExceptionLog(), configuration.isIgnoreAndLog());
			}
			result.add(curChild);
		}
		//delete random selected elements
		for(EObject curChild:toDelete){
			ModelMutatorUtil.removeFullPerCommand(curChild, configuration.getExceptionLog(), configuration.isIgnoreAndLog());
		}
//		deleteEObjects(parentEObject, Integer.MAX_VALUE);
//		changeAttributes(parentEObject, Integer.MAX_VALUE);
//		Map<EReference, List<EObject>> currentContainments = new HashMap<EReference, List<EObject>>();
//		List<EObject> result = new ArrayList<EObject>();
//		//Create a map of containments 
//		for (EObject curChild : parentEObject.eContents()) {
//			EReference containment = curChild.eContainmentFeature();
//			List<EObject> list = currentContainments.get(containment);
//			if (list == null) {
//				list = new ArrayList<EObject>();
//				currentContainments.put(containment, list);
//			}
//			list.add(curChild);
//			result.add(curChild);
//		}

		List<EReference> references = new ArrayList<EReference>();
		//generate the children of the current element so that the lower bound holds or that there is a child of each sort 
		for (EReference reference : parentEObject.eClass().getEAllContainments()) {
			if (configuration.geteStructuralFeaturesToIgnore().contains(reference)
				|| !ModelMutatorUtil.isValid(reference, parentEObject, configuration.getExceptionLog(),
					configuration.isIgnoreAndLog())) {
				continue;
			}
			references.add(reference);
			int numCurrentContainments = 0;
			List<EObject> list = currentContainments.get(reference);
			if (list != null) {
				numCurrentContainments = list.size();
			}

			List<EObject> contain = null;
			if (generateAllReferences) {
				contain = generateFullDifferentContainment(parentEObject, reference, Integer.MAX_VALUE);
			} else {
				contain = generateMinContainments(parentEObject, reference, reference.getLowerBound()
					- numCurrentContainments);
			}

			if (list == null) {
				list = new ArrayList<EObject>();
				currentContainments.put(reference, list);
			}
			list.addAll(contain);

			result.addAll(contain);
		}
		// fill up the references where more elements are needed
		if (references.size() != 0) {
			for (int i = result.size(); i < configuration.getWidth() && references.size() != 0; i++) {
				int refIndex = configuration.getRandom().nextInt(references.size());
				EReference reference = references.get(refIndex);
				int upperBound = Integer.MAX_VALUE;
				if (reference.getUpperBound()!=EReference.UNBOUNDED_MULTIPLICITY && reference.getUpperBound()!=EReference.UNSPECIFIED_MULTIPLICITY) {
					upperBound = reference.getUpperBound();
				}
				List<EObject> list = currentContainments.get(reference);
				if (list.size() < upperBound) {
					List<EObject> contain = generateMinContainments(parentEObject, reference, 1);
					list.addAll(contain);
					result.addAll(contain);
				} else {
					references.remove(refIndex);
					i--;
				}
			}
		}
		return result;
	}

	private List<EObject> generateFullDifferentContainment(EObject parentEObject, EReference reference, int maxNumber) {
		List<EClass> allEClasses = new ArrayList<EClass>(ModelMutatorUtil.getAllEContainments(reference));

		// only allow EClasses that appear in the specified EPackage
		allEClasses.retainAll(ModelMutatorUtil.getAllEClasses(configuration.getModelPackage()));
		// don't allow any EClass or sub class of all EClasses specified in ignoredClasses
		for (EClass eClass : configuration.geteClassesToIgnore()) {
			allEClasses.remove(eClass);
			allEClasses.removeAll(ModelMutatorUtil.getAllSubEClasses(eClass));
		}
		
		List<EObject> result = new ArrayList<EObject>(allEClasses.size());
		for (EClass eClass : allEClasses){
			EObject newChild = generateElement(parentEObject,eClass,reference);
			// was creating the child successful?
			if (newChild != null) {
				result.add(newChild);
				if (result.size() == maxNumber) {
					return result;
				}
			}
		}
		// Fill with random objects to get to the lowerBound
		int numToFillMin = Math.min(reference.getLowerBound(), maxNumber) - result.size();
		if (numToFillMin > 0) {
			result.addAll(generateMinContainments(parentEObject, reference, numToFillMin));
		}
		return result;
	}

	/**
	 * Creates valid instances of children for <code>parentEObject</code> using
	 * the information in the <code>reference</code>. They are set as a child of
	 * <code>parentEObject</code> with AddCommand/SetCommand.
	 * 
	 * @param parentEObject
	 *            the EObject that shall contain the new instances of children
	 * @param reference
	 *            the containment reference
	 * @param width
	 *            the amount of children to create
	 * @return a list containing the instances of children or an empty list if
	 *         the operation failed
	 * 
	 * @see ModelGeneratorUtil#addPerCommand(EObject, EStructuralFeature,
	 *      Object, Set, boolean)
	 * @see ModelGeneratorUtil#setPerCommand(EObject, EStructuralFeature,
	 *      Object, Set, boolean)
	 */
	protected List<EObject> generateMinContainments(EObject parentEObject, EReference reference, int width) {
		List<EObject> result = new ArrayList<EObject>(width > 0 ? width : 0);
		for (int i = 0; i < width; i++) {
			EClass eClass = getValidEClass(reference);
			if (eClass != null) {
				EObject newChild = generateElement(parentEObject, eClass, reference);
				// was creating the child successful?
				if (newChild != null) {
					result.add(newChild);
				}
			}
		}
		return result;
	}

	protected EObject generateElement(EObject parentEObject, EClass eClass, EReference reference) {
		// create child and add it to parentEObject
		// Old version which used another method:
		//EObject newChild = setContainment(parentEObject, eClass, reference);
		EObject newChild = null;
		// create and set attributes
		EObject newEObject = EcoreUtil.create(eClass);
		ModelMutatorUtil.setEObjectAttributes(newEObject, configuration.getRandom(), configuration.getExceptionLog(), configuration.isIgnoreAndLog());
		// reference created EObject to the parent
		if (reference.isMany()) {
			newChild = ModelMutatorUtil.addPerCommand(parentEObject, reference, newEObject, configuration.getExceptionLog(), configuration.isIgnoreAndLog());
		} else {
			newChild = ModelMutatorUtil.setPerCommand(parentEObject, reference, newEObject, configuration.getExceptionLog(), configuration.isIgnoreAndLog());
		}
		return newChild;
	}

	/**
	 * Returns a valid EClasses randomly for the given reference.
	 * @param eReference
	 * 			the eReference the EClass is searched for
	 * @return
	 * 			a valid eClass for the eReference
	 */
	protected EClass getValidEClass(EReference eReference) {
		List<EClass> allEClasses = new ArrayList<EClass>(ModelMutatorUtil.getAllEContainments(eReference));

		// only allow EClasses that appear in the specified EPackage
		allEClasses.retainAll(ModelMutatorUtil.getAllEClasses(configuration.getModelPackage()));
		// don't allow any EClass or sub class of all EClasses specified in
		// ignoredClasses
		for (EClass eClass : configuration.geteClassesToIgnore()) {
			allEClasses.remove(eClass);
			allEClasses.removeAll(ModelMutatorUtil.getAllSubEClasses(eClass));
		}
		if (allEClasses.isEmpty()) {
			// no valid EClass left
			return null;
		}
		// random seed all the time
		int ind = configuration.getRandom().nextInt(allEClasses.size());
		return allEClasses.get(ind);
	}
	
	/**
	 * Sets all references for every child (direct and indirect)
	 * of <code>root</code>.
	 * 
	 * @param maxNumber
	 *            maximal number of references to set 
	 * @see #changeEObjectAttributes(EObject)
	 * @see #changeEObjectReferences(EObject, Map)
	 */
	public void changeCrossReferences(int maxNumber) {
		EObject rootObject = configuration.getRootEObject();
		Map<EClass, List<EObject>> allObjectsByEClass = ModelMutatorUtil.getAllClassesAndObjects(rootObject);
		for (List<EObject> list : allObjectsByEClass.values()) {
			for (EObject eObject : list) {
				maxNumber -= generateReferences(eObject, allObjectsByEClass, maxNumber);
				if (maxNumber <= 0) {
					return;
				}
			}
		}
	}
	
	/**
	 * Generates references (no containment references) for an EObject. All
	 * valid references are set with EObjects generated during the generation
	 * process.
	 * 
	 * @param eObject
	 *            the EObject to set references for
	 * @param allObjectsByEClass
	 *            all possible EObjects that can be referenced, mapped to their
	 *            EClass
	 * @param maxNumber
	 *            maximal number of references to set 
	 * @see ModelGeneratorHelper#setReference(EObject, EClass, EReference, Map)
	 */
	protected int generateReferences(EObject eObject, Map<EClass, List<EObject>> allObjectsByEClass, int maxNumber) {
		int i = 0;
		for (EReference reference : ModelMutatorUtil.getValidCrossReferences(eObject, configuration.getExceptionLog(), configuration.isIgnoreAndLog())) {
			for (EClass nextReferenceClass : ModelMutatorUtil.getReferenceClasses(reference, allObjectsByEClass.keySet())) {
				setEObjectReference(eObject, nextReferenceClass, reference, allObjectsByEClass);
				if (++i == maxNumber) {
					return i;
				}
			}
		}
		return i;
	}
	
	/**
	 * Sets a reference, if the upper bound allows it, using
	 * {@link ModelGeneratorUtil#setReference}.
	 * 
	 * @param eObject
	 *            the EObject to set the reference for
	 * @param referenceClass
	 *            the EClass of EObjects that shall be referenced
	 * @param reference
	 *            the EReference that shall be set
	 * @param allEObjects
	 *            all possible EObjects that can be referenced
	 * @see ModelGeneratorUtil#setReference(EObject, EClass, EReference, Random,
	 *      Set, boolean, Map)
	 */
	@SuppressWarnings("unchecked")
	protected void setEObjectReference(EObject eObject, EClass referenceClass, EReference reference,
		Map<EClass, List<EObject>> allEObjects) {
		
		// Delete already set references (only applies when changing a model)
		if (eObject.eIsSet(reference)) {
			//check whether to delete or not
			if(configuration.getRandom().nextBoolean()){
				//do different stuff, depending on reference type
				if(reference.isMany()){
					List<EObject> toDelte=new ArrayList<EObject>();
					//check whether to delete references randomly or all at once 
					if(configuration.getRandom().nextBoolean()){
						for(EObject refObj:(EList<EObject>)eObject.eGet(reference)){
							//check whether to delete this reference
							if(configuration.getRandom().nextBoolean()){
								toDelte.add(refObj);
							}
						}
					}
					else{
						toDelte.addAll((EList<EObject>)eObject.eGet(reference));
					}
					ModelMutatorUtil.removePerCommand(eObject, reference, toDelte, configuration.getExceptionLog(), configuration.isIgnoreAndLog());	
				}
				else
					eObject.eUnset(reference);
			}
			else{
				//nothing was deleted so no references need to be set
				return;
			}
		}
		// check if the upper bound is reached
		if (!ModelMutatorUtil.isValid(reference, eObject, configuration.getExceptionLog(), configuration.isIgnoreAndLog()) ||
				(!reference.isMany() && eObject.eIsSet(reference))) {
			return;
		}
		
		ModelMutatorUtil.setReference(eObject, referenceClass, reference, configuration.getRandom(),
			configuration.getExceptionLog(), configuration.isIgnoreAndLog(), allEObjects);
	}
	
	@SuppressWarnings("unchecked")
	public void changeEObjectReference(EObject eObject, EClass referenceClass, EReference reference,
		Map<EClass, List<EObject>> allEObjects) {
		
		// Delete already set references (only applies when changing a model)
		if (eObject.eIsSet(reference)) {
			//check whether to delete or not
			if(configuration.getRandom().nextBoolean()){
				//do different stuff, depending on reference type
				if(reference.isMany()){
					List<EObject> toDelte=new ArrayList<EObject>();
					//check whether to delete references randomly or all at once 
					if(configuration.getRandom().nextBoolean()){
						for(EObject refObj:(EList<EObject>)eObject.eGet(reference)){
							//check whether to delete this reference
							if(configuration.getRandom().nextBoolean()){
								toDelte.add(refObj);
							}
						}
					}
					else{
						toDelte.addAll((EList<EObject>)eObject.eGet(reference));
					}
//					ModelMutatorUtil.removePerCommand(eObject, reference, toDelte, configuration.getExceptionLog(), configuration.isIgnoreAndLog());	
				}
				else
					eObject.eUnset(reference);
			}
			else{
				//nothing was deleted so no references need to be set
				return;
			}
		}
		// check if the upper bound is reached
		if (!ModelMutatorUtil.isValid(reference, eObject, configuration.getExceptionLog(), configuration.isIgnoreAndLog()) ||
				(!reference.isMany() && eObject.eIsSet(reference))) {
			return;
		}
		
		ModelMutatorUtil.setReference(eObject, referenceClass, reference, configuration.getRandom(),
			configuration.getExceptionLog(), configuration.isIgnoreAndLog(), allEObjects);
	}
}