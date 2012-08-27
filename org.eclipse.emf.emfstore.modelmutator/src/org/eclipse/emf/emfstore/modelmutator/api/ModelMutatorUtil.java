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
package org.eclipse.emf.emfstore.modelmutator.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AbstractOverrideableCommand;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.emfstore.modelmutator.intern.attribute.AttributeSetter;
import org.eclipse.emf.emfstore.modelmutator.intern.attribute.AttributeSetterEBigDecimal;
import org.eclipse.emf.emfstore.modelmutator.intern.attribute.AttributeSetterEBigInteger;
import org.eclipse.emf.emfstore.modelmutator.intern.attribute.AttributeSetterEBoolean;
import org.eclipse.emf.emfstore.modelmutator.intern.attribute.AttributeSetterEByte;
import org.eclipse.emf.emfstore.modelmutator.intern.attribute.AttributeSetterEByteArray;
import org.eclipse.emf.emfstore.modelmutator.intern.attribute.AttributeSetterEChar;
import org.eclipse.emf.emfstore.modelmutator.intern.attribute.AttributeSetterEDate;
import org.eclipse.emf.emfstore.modelmutator.intern.attribute.AttributeSetterEDouble;
import org.eclipse.emf.emfstore.modelmutator.intern.attribute.AttributeSetterEEnum;
import org.eclipse.emf.emfstore.modelmutator.intern.attribute.AttributeSetterEFloat;
import org.eclipse.emf.emfstore.modelmutator.intern.attribute.AttributeSetterEInt;
import org.eclipse.emf.emfstore.modelmutator.intern.attribute.AttributeSetterELong;
import org.eclipse.emf.emfstore.modelmutator.intern.attribute.AttributeSetterEShort;
import org.eclipse.emf.emfstore.modelmutator.intern.attribute.AttributeSetterEString;

/**
 * Util class for the ModelMutator.
 * 
 * @author Eugen Neufeld
 * @author Stephan Köhler
 * @author Philip Achenbach
 * @author Dmitry Litvinov
 * @author Julian Sommerfeldt
 */
public final class ModelMutatorUtil {

	/**
	 * Indicates deleting with the {@link DeleteCommand}.
	 */
	public static final int DELETE_DELETE_COMMAND = 0;

	/**
	 * Indicates deleting through removing containment references.
	 */
	public static final int DELETE_CUT_CONTAINMENT = 1;

	/**
	 * Indicates deleting with the {@link EcoreUtil#delete(EObject)} method.
	 */
	public static final int DELETE_ECORE = 2;

	private Map<EClassifier, AttributeSetter<?>> attributeSetters;

	private Map<EObject, List<EReference>> validContainmentReferences = new LinkedHashMap<EObject, List<EReference>>();

	private Map<EObject, List<EReference>> validCrossReferences = new LinkedHashMap<EObject, List<EReference>>();

	private Map<EReference, List<EClass>> allContainments = new LinkedHashMap<EReference, List<EClass>>();

	private Map<EClass, List<EClass>> allSubClasses = new LinkedHashMap<EClass, List<EClass>>();

	private Map<EPackage, List<EClass>> allClassesInPackage = new LinkedHashMap<EPackage, List<EClass>>();

	private ModelMutatorConfiguration config;

	private List<EClass> allEClasses;

	/**
	 * A new {@link ModelMutatorUtil}.
	 * 
	 * @param config The {@link ModelMutatorConfiguration} of the {@link ModelMutatorUtil}.
	 */
	public ModelMutatorUtil(ModelMutatorConfiguration config) {
		this.config = config;
	}

	/**
	 * Returns the EPackage to the specified <code>nsURI</code>.
	 * 
	 * @param nsURI
	 *            the NsUri of the EPackage to get
	 * @return the EPackage belonging to <code>nsURI</code>
	 * @see Registry#getEPackage(String)
	 */
	public static EPackage getEPackage(String nsURI) {
		return EPackage.Registry.INSTANCE.getEPackage(nsURI);
	}

	/**
	 * Returns all valid containment references for an EObject. A reference is valid if it is neither
	 * derived nor volatile and if it is changeable and either many-valued or
	 * not already set.
	 * 
	 * @param eObject
	 *            the EObject to get references for
	 * 
	 * @return all valid references as a list
	 */
	public List<EReference> getValidContainmentReferences(EObject eObject) {
		List<EReference> list = validContainmentReferences.get(eObject);
		if (list == null) {
			list = new ArrayList<EReference>();
			for (EReference reference : eObject.eClass().getEAllReferences()) {
				if (reference.isContainment() && isValid(reference, eObject)) {
					list.add(reference);
				}
			}
			validContainmentReferences.put(eObject, list);
		}
		return list;
	}

	/**
	 * Returns all valid references for an EObject. This excludes
	 * container/containment references. A reference is valid if it is neither
	 * derived nor volatile and if it is changeable and either many-valued or
	 * not already set.
	 * 
	 * @param eObject
	 *            the EObject to get references for
	 * @return all valid references as a list
	 */
	public List<EReference> getValidCrossReferences(EObject eObject) {
		List<EReference> list = validCrossReferences.get(eObject);
		if (list == null) {
			list = new ArrayList<EReference>();
			for (EReference reference : eObject.eClass().getEAllReferences()) {
				if (!reference.isContainer() && !reference.isContainment() && isValid(reference, eObject)) {
					list.add(reference);
				}
			}
			validCrossReferences.put(eObject, list);
		}
		return list;
	}

	/**
	 * Returns whether an EStructuralFeature is valid for an EObject or not. A
	 * reference is valid, if it can be set or added to.
	 * 
	 * @param feature
	 *            the EStructuralFeature in question
	 * @param eObject
	 *            the EObject to check the feature for
	 * @return if the feature can be set or added to
	 */
	public boolean isValid(EStructuralFeature feature, EObject eObject) {
		boolean result = false;
		try {
			if (feature.isMany()) {
				// has the maximum amount of referenced objects been reached?
				Collection<?> referencedItems = (Collection<?>) eObject.eGet(feature);
				if (feature.getUpperBound() >= 0 && referencedItems.size() >= feature.getUpperBound()) {
					return false;
				}
			}
			// can the feature be changed reflectively?
			result = feature.isChangeable() && !feature.isVolatile() && !feature.isDerived();
			// BEGIN SUPRESS CATCH EXCEPTION
		} catch (RuntimeException e) {
			// END SUPRESS CATCH EXCEPTION
			handle(e, config);
		}
		return result;
	}

	/**
	 * Handles <code>exception</code>, meaning it is thrown if <code>ignoreAndLog</code> is <code>false</code>.
	 * Otherwise <code>exception</code> is ignored and added to <code>exceptionLog</code>.
	 * 
	 * @param exception the exception to handle
	 * @param exceptionLog the current log of exceptions
	 * @param ignoreAndLog should exceptions be ignored and added to <code>exceptionLog</code>?
	 */
	private static void handle(RuntimeException exception, ModelMutatorConfiguration config) {
		if (config.isIgnoreAndLog()) {
			config.getExceptionLog().add(exception);
		} else {
			throw exception;
		}
	}

	/**
	 * Get all containments of a reference.
	 * 
	 * @param reference The {@link EReference} for which to get all containments.
	 * @return All containments of the {@link EReference}.
	 */
	public List<EClass> getAllEContainments(EReference reference) {
		List<EClass> list = allContainments.get(reference);
		if (list == null) {
			list = new ArrayList<EClass>();

			EClass referenceType = reference.getEReferenceType();
			if (EcorePackage.eINSTANCE.getEObject().equals(referenceType)) {
				for(EPackage ePackage : config.getModelPackages()){
					list.addAll(getAllEClasses(ePackage));
				}
			}

			if (canHaveInstance(referenceType)) {
				list.add(referenceType);
			}

			list.addAll(getAllSubEClasses(referenceType));

			allContainments.put(reference, list);
		}
		return list;
	}

	/**
	 * Returns whether <code>eClass</code> can be instantiated or not. An EClass
	 * can be instantiated, if it is neither an interface nor abstract.
	 * 
	 * @param eClass
	 *            the EClass in question
	 * @return whether <code>eClass</code> can be instantiated or not.
	 */
	public static boolean canHaveInstance(EClass eClass) {
		return !eClass.isInterface() && !eClass.isAbstract();
	}

	/**
	 * Returns all subclasses of an EClass, excluding abstract classes and
	 * interfaces.
	 * 
	 * @param eClass
	 *            the EClass to get subclasses for
	 * @return all subclasses of <code>eClass</code>
	 */
	public List<EClass> getAllSubEClasses(EClass eClass) {
		List<EClass> list = allSubClasses.get(eClass);
		if (list == null) {
			list = new ArrayList<EClass>();
			List<EClass> allEClasses = getAllEClasses(config.getModelPackages());
			for (EClass possibleSubClass : allEClasses) {
				// is the EClass really a subClass, while not being abstract or an interface?
				if (eClass.isSuperTypeOf(possibleSubClass) && canHaveInstance(possibleSubClass)) {
					list.add(possibleSubClass);
				}
			}

			allSubClasses.put(eClass, list);
		}

		return list;
	}

	/**
	 * Iterates over all registered EPackages in order to retrieve all available
	 * EClasses, excluding abstract classes and interfaces, and returns them as
	 * a Set.
	 * 
	 * @return a set of all EClasses that are contained in registered EPackages
	 * @see Registry
	 */
	public List<EClass> getAllEClasses() {
		if (allEClasses == null) {
			allEClasses = new ArrayList<EClass>();
			Registry registry = EPackage.Registry.INSTANCE;
			// for all registered EPackages
			for (Entry<String, Object> entry : new HashSet<Entry<String, Object>>(registry.entrySet())) {
				EPackage ePackage = registry.getEPackage(entry.getKey());
				for (EClass eClass : getAllEClasses(ePackage)) {
					// no abstracts or interfaces
					if (canHaveInstance(eClass)) {
						allEClasses.add(eClass);
					}
				}
			}
		}
		return allEClasses;
	}
	
	/**
	 * Get all {@link EClass}es in the {@link EPackage}s.
	 * 
	 * @param ePackages The {@link EPackage}s containg the {@link EClass}es.
	 * @return The {@link EClass}es contained in the {@link EPackage}s.
	 */
	public List<EClass> getAllEClasses(Collection<EPackage> ePackages){
		List<EClass> eClasses = new ArrayList<EClass>(); 
		for (EPackage ePackage : ePackages) {
			eClasses.addAll(getAllEClasses(ePackage));
		}
		return eClasses;
	}

	/**
	 * Retrieve all EClasses that are contained in <code>ePackage</code>.
	 * 
	 * @param ePackage
	 *            the package to get contained EClasses from
	 * @return a set of EClasses contained in <code>ePackage</code>
	 */
	public List<EClass> getAllEClasses(EPackage ePackage) {
		List<EClass> list = allClassesInPackage.get(ePackage);
		if (list == null) {
			list = new ArrayList<EClass>();
			// obtain all EClasses from sub packages
			for (EPackage subPackage : ePackage.getESubpackages()) {
				list.addAll(getAllEClasses(subPackage));
			}
			// obtain all EClasses that are direct contents of the EPackage
			for (EClassifier classifier : ePackage.getEClassifiers()) {
				if (classifier instanceof EClass) {
					list.add((EClass) classifier);
				}
			}
			allClassesInPackage.put(ePackage, list);
		}

		return list;
	}

	/**
	 * Returns all direct and indirect contents of <code>rootObject</code> as a
	 * map. All EObjects that appear in these contents are mapped to their
	 * corresponding EClass.<br>
	 * 
	 * NOTE: this is a very expensive method!
	 * 
	 * 
	 * @param rootObject
	 *            the EObject to get contents for
	 * @return all contents as a map from EClass to lists of EObjects
	 */
	public static Map<EClass, List<EObject>> getAllClassesAndObjects(EObject rootObject) {
		// initialize the computation process
		Map<EClass, List<EObject>> result = new LinkedHashMap<EClass, List<EObject>>();
		TreeIterator<EObject> allContents = rootObject.eAllContents();
		List<EObject> newList = new ArrayList<EObject>();
		newList.add(rootObject);
		result.put(rootObject.eClass(), newList);
		// iterate over all direct and indirect contents
		while (allContents.hasNext()) {
			EObject eObject = allContents.next();
			// did this EObject's EClass appear before?
			if (result.containsKey(eObject.eClass())) {
				result.get(eObject.eClass()).add(eObject);
			} else {
				newList = new ArrayList<EObject>();
				newList.add(eObject);
				result.put(eObject.eClass(), newList);
			}
		}
		return result;
	}

	/**
	 * Adds <code>newValue</code> to the many-valued feature of <code>eObject</code> using an AddCommand. Exceptions are
	 * caught if <code>ignoreAndLog</code> is true, otherwise a RuntimeException might be
	 * thrown if the command fails.
	 * 
	 * @param eObject the EObject to which <code>newObject</code> shall be added
	 * @param feature the EStructuralFeature that <code>newObject</code> shall be added to
	 * @param newValue the Object that shall be added to <code>feature</code>
	 * @param index the index where to add the object or null if it should be added to the end.
	 * @see AddCommand#AddCommand(EditingDomain, EObject, EStructuralFeature, Object)
	 */
	public void addPerCommand(EObject eObject, EStructuralFeature feature, Object newValue, Integer index) {
		try {
			if (feature.isUnique() && ((Collection<?>) eObject.eGet(feature)).contains(newValue)) {
				// unique feature already contains object -> nothing to do
				return;
			}
			EditingDomain domain = config.getEditingDomain();
			if (index == null) {
				domain.getCommandStack().execute(new AddCommand(domain, eObject, feature, newValue));
			} else {
				domain.getCommandStack().execute(new AddCommand(domain, eObject, feature, newValue, index));
			}

			// BEGIN SUPRESS CATCH EXCEPTION
		} catch (RuntimeException e) {
			// END SUPRESS CATCH EXCEPTION
			handle(e, config);
		}
	}

	/**
	 * Adds all <code>objects</code> to the many-valued feature of <code>eObject</code> using an AddCommand. Exceptions
	 * are caught if <code>ignoreAndLog</code> is true, otherwise a RuntimeException might be
	 * thrown if the command fails.
	 * 
	 * @param eObject the EObject to which <code>objects</code> shall be added
	 * @param feature the EReference that <code>objects</code> shall be added to
	 * @param objects collection of objects that shall be added to <code>feature</code>
	 */
	public void addPerCommand(EObject eObject, EStructuralFeature feature, Collection<?> objects) {
		try {
			for (Object object : objects) {
				if (feature.isUnique() && ((Collection<?>) eObject.eGet(feature)).contains(object)) {
					// object already exists in unique feature, don't add it again
					objects.remove(object);
				}
			}
			// no objects to add left
			if (objects.isEmpty()) {
				return;
			}
			EditingDomain domain = config.getEditingDomain();
			domain.getCommandStack().execute(new AddCommand(domain, eObject, feature, objects));
			// BEGIN SUPRESS CATCH EXCEPTION
		} catch (RuntimeException e) {
			// END SUPRESS CATCH EXCEPTION
			handle(e, config);
		}
	}

	/**
	 * Move an object.
	 * 
	 * @param parent The parent object.
	 * @param feature The feature of the parent object.
	 * @param objectToMove The object to move within the parents feature.
	 * @param index The index where to move the object to.
	 */
	public void movePerCommand(EObject parent, EStructuralFeature feature, Object objectToMove, Integer index) {
		try {

			Collection<?> containments = (Collection<?>) parent.eGet(feature);
			if (!containments.contains(objectToMove)) {
				return;
			}
			EditingDomain domain = config.getEditingDomain();
			domain.getCommandStack().execute(new MoveCommand(domain, parent, feature, objectToMove, index));
			// BEGIN SUPRESS CATCH EXCEPTION
		} catch (RuntimeException e) {
			// END SUPRESS CATCH EXCEPTION
			handle(e, config);
		}
	}

	/**
	 * Sets a feature between <code>eObject</code> and <code>newValue</code> using a SetCommand. Exceptions are caught
	 * if <code>ignoreAndLog</code> is
	 * true, otherwise a RuntimeException might be thrown if the command fails.
	 * 
	 * @param eObject the EObject for which <code>feature</code> shall be set
	 * @param feature the EStructuralFeature that shall be set
	 * @param newValue the Object that shall be set as a feature in <code>parentEObject</code>
	 * @param index Where to set to object to or null if it should be set to the end.
	 * @return <code>newValue</code> if the <code>SetCommand</code> was performed successful or <code>null</code> if it
	 *         failed
	 * @see SetCommand
	 */
	public EObject setPerCommand(EObject eObject, EStructuralFeature feature, Object newValue, Integer index) {
		// no new value to set? -> unset the feature or set null
		if (newValue == null && config.getRandom().nextBoolean()) {
			newValue = SetCommand.UNSET_VALUE;
		}
		try {
			EditingDomain domain = config.getEditingDomain();
			if (index != null) {
				if (feature.isUnique() && ((Collection<?>) eObject.eGet(feature)).contains(newValue)) {
					return null;
				}
				domain.getCommandStack().execute(new SetCommand(domain, eObject, feature, newValue, index));
			} else {
				domain.getCommandStack().execute(new SetCommand(domain, eObject, feature, newValue));
			}
			if (newValue instanceof EObject) {
				return (EObject) newValue;
			} else {
				return null;
			}
			// BEGIN SUPRESS CATCH EXCEPTION
		} catch (RuntimeException e) {
			// END SUPRESS CATCH EXCEPTION
			handle(e, config);
			return null;
		}
	}

	/**
	 * Sets a feature between <code>eObject</code> and <code>newValue</code> using a SetCommand. Exceptions are caught
	 * if <code>ignoreAndLog</code> is
	 * true, otherwise a RuntimeException might be thrown if the command fails.
	 * 
	 * @param eObject the EObject for which <code>feature</code> shall be set
	 * @param feature the EStructuralFeature that shall be set
	 * @param newValue the Object that shall be set as a feature in <code>parentEObject</code>
	 * @return <code>newValue</code> if the <code>SetCommand</code> was
	 *         performed successful or <code>null</code> if it failed
	 * @see SetCommand
	 */
	public EObject setPerCommand(EObject eObject, EStructuralFeature feature, Object newValue) {
		return setPerCommand(eObject, feature, newValue, null);
	}

	/**
	 * Removes <code>objects</code> from a feature of <code>eObject</code> using
	 * a RemoveCommand. Exceptions are caught if <code>ignoreAndLog</code> is
	 * true, otherwise a RuntimeException might be thrown if the command fails.
	 * 
	 * @param eObject
	 *            the EObject to remove <code>objects</code> from
	 * @param feature
	 *            the EStructuralFeature <code>objects</code> shall be removed
	 *            from
	 * @param objects
	 *            collection of Objects that shall be removed
	 * @see RemoveCommand
	 */
	public void removePerCommand(EObject eObject, EStructuralFeature feature, Collection<?> objects) {
		try {
			EditingDomain domain = config.getEditingDomain();
			domain.getCommandStack().execute(new RemoveCommand(domain, eObject, feature, objects));
			// BEGIN SUPRESS CATCH EXCEPTION
		} catch (RuntimeException e) {
			// END SUPRESS CATCH EXCEPTION
			handle(e, config);
		}
	}

	/**
	 * Deletes the {@link EObject} using the specified <code>howToDelete</code>.
	 * 
	 * @param eObject The {@link EObject} to delete.
	 * @param howToDelete The way to delete: {@link #DELETE_ECORE}, {@link #DELETE_DELETE_COMMAND} or
	 *            {@link #DELETE_CUT_CONTAINMENT}.
	 */
	@SuppressWarnings("unchecked")
	public void removeFullPerCommand(final EObject eObject, int howToDelete) {
		try {
			EditingDomain domain = config.getEditingDomain();
			// delete with DeleteCommand
			if (DELETE_DELETE_COMMAND == howToDelete) {
				domain.getCommandStack().execute(new DeleteCommand(domain, Collections.singleton(eObject)));

				// delete through cutting containment (RemoveCommand)
			} else if (DELETE_CUT_CONTAINMENT == howToDelete) {
				EStructuralFeature feature = eObject.eContainingFeature();
				if (feature == null) {
					EcoreUtil.delete(eObject, true);
				}

				EObject eContainer = eObject.eContainer();
				if (feature.isMany()) {
					((EList<Object>) eContainer.eGet(feature)).remove(eObject);
				} else {
					eContainer.eSet(feature, null);
				}

				// delete with EcoreUtil
			} else if (DELETE_ECORE == howToDelete) {
				EcoreUtil.delete(eObject, false);

				// no valid delete mode
			} else {
				throw new IllegalArgumentException("This is not a valid delete mode argument: " + howToDelete);
			}
			// BEGIN SUPRESS CATCH EXCEPTION
		} catch (RuntimeException e) {
			// END SUPRESS CATCH EXCEPTION
			handle(e, config);
		}
	}

	/**
	 * Sets all possible attributes of known types to random values using {@link IAttributeSetter} and
	 * SetCommands/AddCommands.
	 * 
	 * @param eObject the EObject to set attributes for
	 * @see IAttributeSetter
	 * @see AttributeHandler
	 * @see #addPerCommand(EObject, EStructuralFeature, Collection, Set, boolean)
	 * @see #addPerCommand(EObject, EStructuralFeature, Object, Set, boolean)
	 * @see #setPerCommand(EObject, EStructuralFeature, Object, Set, boolean)
	 */
	public void setEObjectAttributes(EObject eObject) {
		setEObjectAttributes(eObject, Integer.MAX_VALUE);
	}

	/**
	 * Sets all possible attributes of known types to random values using {@link IAttributeSetter} and
	 * SetCommands/AddCommands.
	 * 
	 * @param eObject the EObject to set attributes for
	 * @param maxNumber The maximal number of attributes to mutate.
	 * @see IAttributeSetter
	 * @see AttributeHandler
	 * @see #addPerCommand(EObject, EStructuralFeature, Collection, Set, boolean)
	 * @see #addPerCommand(EObject, EStructuralFeature, Object, Set, boolean)
	 * @see #setPerCommand(EObject, EStructuralFeature, Object, Set, boolean)
	 */
	public void setEObjectAttributes(EObject eObject, int maxNumber) {
		Random random = config.getRandom();
		int numAttrLeft = maxNumber;
		for (EAttribute attribute : eObject.eClass().getEAllAttributes()) {
			EClassifier attributeType = attribute.getEAttributeType();

			// randomly remove attributes
			if (random.nextBoolean() && eObject.eIsSet(attribute) && attribute.isMany()) {
				removePerCommand(eObject, attribute, (Collection<?>) eObject.eGet(attribute));
			}

			if (!isValid(attribute, eObject)) {
				continue;
			}

			// the attribute setter used to create new attributes
			AttributeSetter<?> attributeSetter = getAttributeSetter(attributeType);

			// was there a fitting attribute setter?
			if (attributeSetter == null) {
				continue;
			}

			if (attribute.isMany()) {
				int numberOfAttributes = computeFeatureAmount(attribute, random);

				// vary between empty attributes and already value containing ones
				int size = ((Collection<?>) eObject.eGet(attribute)).size();
				if (size == 0) {
					addPerCommand(eObject, attribute, attributeSetter.createNewAttributes(numberOfAttributes));
				} else {
					// vary between set and and move for higher coverage
					for (int i = 0; i < size; i++) {
						if (random.nextBoolean()) {
							setPerCommand(eObject, attribute, attributeSetter.createNewAttribute(), i);
						} else {
							Object attributeToMove = ((Collection<?>) eObject.eGet(attribute)).toArray()[random
								.nextInt(size)];
							movePerCommand(eObject, attribute, attributeToMove, random.nextInt(size));
						}
					}
				}
			} else {
				setPerCommand(eObject, attribute, attributeSetter.createNewAttribute());
			}
			numAttrLeft--;
			if (numAttrLeft == 0) {
				return;
			}
		}
	}

	/**
	 * Returns whether <code>attributeType</code> is an instance of EEnum.
	 * 
	 * @param attributeType
	 *            the EClassifier in question
	 * @return is <code>attributeType</code> an instance of EEnum?
	 */
	private static boolean isEnum(EClassifier attributeType) {
		return EcorePackage.eINSTANCE.getEEnum().isInstance(attributeType);
	}

	/**
	 * Computes the random amount of objects to add to a feature.
	 * 
	 * @param feature
	 *            the feature to compute the amount of objects for
	 * @param random
	 *            the Random object used to obtain random values
	 * @return 1 if the feature is single valued,<br>
	 *         a random value from 0 to 10 if the feature is many-valued and has
	 *         no upper bound,<br>
	 *         a random value between the feature's lower and upper bound
	 *         otherwise
	 */
	private static int computeFeatureAmount(EStructuralFeature feature, Random random) {
		if (!feature.isMany()) {
			return 1;
		}
		if (feature.getUpperBound() < feature.getLowerBound()) {
			return random.nextInt(10);
		}
		return feature.getLowerBound() + random.nextInt(feature.getUpperBound() - feature.getLowerBound() + 1);
	}

	/**
	 * Returns a map containing an AttributeSetter-instance for each attribute
	 * type, granting access to all AttributeSetters.
	 * 
	 * @return the map that maps every attribute type to its attribute setter
	 * @see AttributeSetter
	 */
	public Map<EClassifier, AttributeSetter<?>> getAttributeSetters() {

		if (attributeSetters == null) {

			Random random = config.getRandom();

			EcorePackage ecoreInstance = EcorePackage.eINSTANCE;

			attributeSetters = new LinkedHashMap<EClassifier, AttributeSetter<?>>();
			AttributeSetter<?> oAttributeSetter;

			oAttributeSetter = new AttributeSetterEBoolean(random);
			attributeSetters.put(ecoreInstance.getEBoolean(), oAttributeSetter);
			attributeSetters.put(ecoreInstance.getEBooleanObject(), oAttributeSetter);

			attributeSetters.put(ecoreInstance.getEByteArray(), new AttributeSetterEByteArray(random, 100));

			attributeSetters.put(ecoreInstance.getEString(), new AttributeSetterEString(random));

			oAttributeSetter = new AttributeSetterEInt(random);
			attributeSetters.put(ecoreInstance.getEInt(), oAttributeSetter);
			attributeSetters.put(ecoreInstance.getEIntegerObject(), oAttributeSetter);

			attributeSetters.put(ecoreInstance.getEDate(), new AttributeSetterEDate(random));

			oAttributeSetter = new AttributeSetterELong(random);
			attributeSetters.put(ecoreInstance.getELong(), oAttributeSetter);
			attributeSetters.put(ecoreInstance.getELongObject(), oAttributeSetter);

			oAttributeSetter = new AttributeSetterEByte(random);
			attributeSetters.put(ecoreInstance.getEByte(), oAttributeSetter);
			attributeSetters.put(ecoreInstance.getEByteObject(), oAttributeSetter);

			oAttributeSetter = new AttributeSetterEChar(random);
			attributeSetters.put(ecoreInstance.getEChar(), oAttributeSetter);
			attributeSetters.put(ecoreInstance.getECharacterObject(), oAttributeSetter);

			oAttributeSetter = new AttributeSetterEDouble(random);
			attributeSetters.put(ecoreInstance.getEDouble(), oAttributeSetter);
			attributeSetters.put(ecoreInstance.getEDoubleObject(), oAttributeSetter);

			oAttributeSetter = new AttributeSetterEFloat(random);
			attributeSetters.put(ecoreInstance.getEFloat(), oAttributeSetter);
			attributeSetters.put(ecoreInstance.getEFloatObject(), oAttributeSetter);

			oAttributeSetter = new AttributeSetterEShort(random);
			attributeSetters.put(ecoreInstance.getEShort(), oAttributeSetter);
			attributeSetters.put(ecoreInstance.getEShortObject(), oAttributeSetter);

			attributeSetters.put(ecoreInstance.getEBigInteger(), new AttributeSetterEBigInteger(random));

			attributeSetters.put(ecoreInstance.getEBigDecimal(), new AttributeSetterEBigDecimal(random));

		}

		return attributeSetters;
	}

	private AttributeSetter<?> getAttributeSetter(EClassifier attributeType) {
		getAttributeSetters();
		if (attributeSetters.containsKey(attributeType)) {
			return attributeSetters.get(attributeType);
		} else if (isEnum(attributeType)) {
			return new AttributeSetterEEnum((EEnum) attributeType, config.getRandom());
		}
		return null;
	}

	/**
	 * Retrieves all EClasses from <code>allEClasses</code> that can possibly be
	 * referenced by <code>reference</code> and returns them as a list.
	 * 
	 * @param reference
	 *            the EReference to get EClasses for
	 * @param allEClasses
	 *            set of all possible EClasses
	 * @return list of all EClasses that can be referenced by <code>reference</code>
	 */
	public Set<EClass> getReferenceClasses(EReference reference, Set<EClass> allEClasses) {
		Set<EClass> result = new LinkedHashSet<EClass>();
		EClass referenceType = reference.getEReferenceType();
		// 'referenceType: EObject' allows all kinds of EObjects
		if (referenceType.equals(EcorePackage.eINSTANCE.getEObject())) {
			return allEClasses;
		}
		for (EClass eClass : allEClasses) {
			// can eClass be referenced by reference
			if (referenceType.equals(eClass) || referenceType.isSuperTypeOf(eClass)) {
				result.add(eClass);
			}
		}
		return result;
	}

	/**
	 * Sets or adds to a reference for an EObject with any generated instance of <code>referenceClass</code> using
	 * SetCommand/AddCommand. If the reference
	 * is not required, <code>random</code> decides whether the reference is set
	 * or how many EObjects are added to it.
	 * 
	 * @param eObject the EObject to set the reference for
	 * @param referenceClass the EClass all referenced EObject shall be instances of
	 * @param reference the reference to set
	 * @param allEObjects Map containing all available EObjects mapped to their EClasses.
	 * @see #addPerCommand(EObject, EStructuralFeature, Collection, Set, boolean)
	 * @see #addPerCommand(EObject, EStructuralFeature, Object, Set, boolean)
	 * @see #setPerCommand(EObject, EStructuralFeature, Object, Set, boolean)
	 */
	public void setReference(EObject eObject, EClass referenceClass, EReference reference,
		Map<EClass, List<EObject>> allEObjects) {
		Random random = config.getRandom();

		List<EObject> possibleReferenceObjects = allEObjects.get(referenceClass);
		Collections.shuffle(possibleReferenceObjects, random);

		if (possibleReferenceObjects.isEmpty()) {
			return;
		}

		int index = 0;
		if (reference.isMany()) {
			int numberOfReferences = computeFeatureAmount(reference, random);
			numberOfReferences -= ((EList<?>) eObject.eGet(reference)).size();
			for (int i = 0; i < numberOfReferences; i++) {
				EList<Object> ownerList = AbstractOverrideableCommand.getOwnerList(eObject, reference);

				// change between set, move and add for higher operation coverage
				int size = ownerList.size();
				if (size > 0 && random.nextBoolean()) {
					if (random.nextBoolean()) {
						// if the reference is ordered, do not use an index
						Integer newIndex = reference.isOrdered() ? null : random.nextInt(size);
						setPerCommand(eObject, reference, possibleReferenceObjects.get(index), newIndex);
					} else {
						Object objectToMove = ownerList.get(random.nextInt(size));
						movePerCommand(eObject, reference, objectToMove, index);
					}
				} else {
					addPerCommand(eObject, reference, possibleReferenceObjects.get(index), random.nextBoolean()
						&& size > 0 ? random.nextInt(size) : null);
				}

				// ensures every EObject is set at most once
				if (++index == possibleReferenceObjects.size()) {
					break;
				}
			}
		} else if (random.nextBoolean() || reference.isRequired()) {
			setPerCommand(eObject, reference, possibleReferenceObjects.get(index));
		}
	}

	/**
	 * @param obj The root object containing the direct and indirect children.
	 * @return The count of all direct and indirect children of the object.
	 */
	public static int getAllObjectsCount(EObject obj) {
		TreeIterator<EObject> eAllContents = obj.eAllContents();
		int i = 0;
		while (eAllContents.hasNext()) {
			i++;
			eAllContents.next();
		}
		return i;
	}
}
