/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * koegel
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.common;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * Utility class to retrieve meta model information.
 * 
 * @author koegel
 * 
 */
// TODO: check whether loadFromResource and validation may be removed
public final class CommonUtil {

	private static boolean testing;
	private static Set<EClass> registryEClasses;

	/**
	 * Private constructor since this is a utility class.
	 */
	private CommonUtil() {
		// nothing to do
	}

	/**
	 * Returns a containing reference of the <code>parent</code> that may contain the 
	 * given <code>containee</code>. 
	 * 
	 * @param containee 
	 * 			the EObject that may be contained by the parent's reference, if any    
	 * @param parent The EObject to get the containment references from
 	 * @return the <code>parent</code>'s container reference
	 */
	public static EReference getPossibleContainingReference(final EObject containee, final EObject parent) {
		
		final List<EReference> eAllContainments = parent.eClass().getEAllContainments();
		EReference reference = null;
		
		for (EReference containmentItem : eAllContainments) {

			final EClass eReferenceType = containmentItem.getEReferenceType();
			
			if (eReferenceType.equals(containee)) {
				reference = containmentItem;
				break;
			} else if (eReferenceType.equals(EcorePackage.eINSTANCE.getEObject())
				|| eReferenceType.isSuperTypeOf(containee.eClass())) {
				reference = containmentItem;
				break;
			}
		}
		
		return reference;
	}

	/**
	 * Gives all registryEClasses which can be contained in a given EClass.
	 * 
	 * @param eClass 
	 * 			the EClass 
	 * @return all Classes which can be contained
	 */
	public static Set<EClass> getAllEContainments(final EClass eClass) {
		
		final List<EReference> containments = eClass.getEAllContainments();
		final Set<EClass> eClasses = new LinkedHashSet<EClass>();
		
		for (EReference ref : containments) {
			final EClass eReferenceType = ref.getEReferenceType();
			eClasses.addAll(getAllSubEClasses(eReferenceType));
		}
		
		return eClasses;
	}

	/**
	 * Retrieve all EClasses from the ECore package registry that are subclasses of the given EClass. Does not include
	 * abstract classes or interfaces.
	 * 
	 * @param eClass the superClass of the subClasses to retrieve
	 * @return a set of EClasses
	 */
	public static Set<EClass> getAllSubEClasses(final EClass eClass) {
		
		final Set<EClass> allEClasses = getAllModelElementEClasses();
		
		if (EcorePackage.eINSTANCE.getEObject().equals(eClass)) {
			return allEClasses;
		}
		
		final Set<EClass> result = new LinkedHashSet<EClass>();
		
		for (EClass subClass : allEClasses) {
			final boolean isSuperTypeOf = eClass.isSuperTypeOf(subClass)
				|| eClass.equals(EcorePackage.eINSTANCE.getEObject());
			if (isSuperTypeOf && (!subClass.isAbstract()) && (!subClass.isInterface())) {
				result.add(subClass);
			}
		}
		return result;
	}

	/**
	 * Retrieve all EClasses from the ECore package registry.
	 * 
	 * @return a set of EClasses
	 */
	public static Set<EClass> getAllModelElementEClasses() {
		
		if (registryEClasses != null) {
			return new LinkedHashSet<EClass>(registryEClasses);
		}
		
		final Set<EClass> result = new LinkedHashSet<EClass>();
		final Registry registry = EPackage.Registry.INSTANCE;

		for (Entry<String, Object> entry : new LinkedHashSet<Entry<String, Object>>(registry.entrySet())) {
			try {
				final EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage(entry.getKey());
				result.addAll(getAllModelElementEClasses(ePackage));
			}
			// BEGIN SUPRESS CATCH EXCEPTION
			catch (RuntimeException exception) {
				Activator.getDefault().logException("Failed to load model package " + entry.getKey(), exception);
				// END SUPRESS CATCH EXCEPTION
			}

		}
		
		registryEClasses = result;
		return result;
	}

	/**
	 * Retrieve all EClasses from the ECore package that are model element subclasses.
	 * 
	 * @param ePackage 
	 * 			the package to get the classes from
	 * @return a set of EClasses found in the given EPackage
	 */
	private static Set<EClass> getAllModelElementEClasses(final EPackage ePackage) {
		final Set<EClass> result = new LinkedHashSet<EClass>();
		for (EPackage subPackage : ePackage.getESubpackages()) {
			result.addAll(getAllModelElementEClasses(subPackage));
		}
		for (EClassifier classifier : ePackage.getEClassifiers()) {
			if (classifier instanceof EClass) {
				final EClass subEClass = (EClass) classifier;
				result.add(subEClass);
			}
		}
		return result;
	}

	/**
	 * Get the EContainer that contains the given model element and whose EContainer is null.
	 * 
	 * @param <T> parent type
	 * @param parent the Class of the parent
	 * @param child the model element whose container should get returned
	 * @return the container
	 */
	public static <T extends EObject> T getParent(final Class<T> parent, final EObject child) {
		final Set<EObject> seenModelElements = new LinkedHashSet<EObject>();
		seenModelElements.add(child);
		return getParent(parent, child, seenModelElements);
	}

	@SuppressWarnings("unchecked")
	private static <T extends EObject> T getParent(final Class<T> parent, final EObject child, final Set<EObject> seenModelElements) {
		if (child == null) {
			return null;
		}

		if (seenModelElements.contains(child.eContainer())) {
			throw new IllegalStateException("ModelElement is in a containment cycle");
		}

		if (parent.isInstance(child)) {
			return (T) child;
		} else {
			seenModelElements.add(child);
			return getParent(parent, child.eContainer(), seenModelElements);
		}
	}

	/**
	 * Check an EObject and its containment tree whether it is self-contained. A containment tree is self contained if it
	 * does not have references to EObjects outside the tree.
	 * 
	 * @param object 
	 * 			the eObject that is checked whether it is self-contained
	 * @return true if it is self-contained, false otherwise
	 */
	public static boolean isSelfContained(final EObject object) {
		return isSelfContained(object, false);
	}

	/**
	 * Check an EObject and its containment tree whether it is self-contained.<br/>
	 * A containment tree is self contained if it does not have references to EObjects outside the tree.
	 * 
	 * @param eObject 
	 * 			the EObject that is checked whether it is self-contained
	 * @param ignoreContainer 
	 * 			true, if references of object to its container should be ignored in the check
	 * @return true if it is self-contained, false otherwise
	 */
	public static boolean isSelfContained(final EObject eObject, final boolean ignoreContainer) {
		final Set<EObject> allChildEObjects = getNonTransientContents(eObject);
		final Set<EObject> allEObjects = new LinkedHashSet<EObject>(allChildEObjects);
		allEObjects.add(eObject);

		final Set<EObject> nonTransientReferences = getNonTransientCrossReferences(eObject);
		if (ignoreContainer && eObject.eContainer() != null) {
			nonTransientReferences.remove(eObject.eContainer());
		}
		if (!allEObjects.containsAll(nonTransientReferences)) {
			return false;
		}

		// TOOD: EM, handle ignored data types here
		// check if only cross references to known elements exist
		// for (EObject content : allChildEObjects) {
		// if (!allEObjects.containsAll(getNonTransientCrossReferences(content))) {
		// return false;
		// }
		// }
		return true;
	}

	/**
	 * Get all contained EObjects not including transient containment features.
	 * 
	 * @param eObject 
	 * 		the EObject whose non-transient contents should be retrieved
	 * @return a set of contained elements not including root (the passed EObject itself)
	 */
	public static Set<EObject> getNonTransientContents(final EObject eObject) {
		final Set<EObject> result = new LinkedHashSet<EObject>();
		if (eObject == null) {
			return result;
		}
		for (EReference containmentReference : eObject.eClass().getEAllContainments()) {
			if (!containmentReference.isTransient()) {
				final Object contentObject = eObject.eGet(containmentReference, true);
				if (containmentReference.isMany()) {
					@SuppressWarnings("unchecked")
					final EList<? extends EObject> contentList = (EList<? extends EObject>) contentObject;
					for (EObject content : contentList) {
						result.add(content);
						result.addAll(getNonTransientContents(content));
					}
				} else {
					final EObject content = (EObject) contentObject;
					if (content == null) {
						continue;
					}
					result.add(content);
					result.addAll(getNonTransientContents(content));
				}
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private static Set<EObject> getNonTransientCrossReferences(final EObject object) {
		final Set<EObject> result = new LinkedHashSet<EObject>();
		if (object == null) {
			return result;
		}
		for (EReference reference : object.eClass().getEAllReferences()) {
			if (!reference.isTransient() && !reference.isContainment()) {
				final Object referenceObject = object.eGet(reference, true);
				if (reference.isMany()) {
					final EList<? extends EObject> referencesList = (EList<? extends EObject>) referenceObject;
					result.addAll(referencesList);
					for (EObject ref : referencesList) {
						if (CommonUtil.isSingletonEObject(ref)) {
							continue;
						}

						result.add(ref);
					}

				} else {
					final EObject crossReference = (EObject) referenceObject;
					if (crossReference == null || CommonUtil.isSingletonEObject(crossReference)) {
						continue;
					}
					result.add(crossReference);
				}
			}
		}
		return result;
	}

	/**
	 * Determines whether an EObject is a singleton object. All EObjects being children of ECorePackage are considered
	 * as singletons.
	 * 
	 * @param eObject 
	 * 			the EObject that will be checked whether it is a singleton
	 * @return true if it is a singleton, false otherwise
	 */
	public static boolean isSingletonEObject(final EObject eObject) {
		if (eObject.eContainer() != null && eObject.eContainer().equals(EcorePackage.eINSTANCE)) {
			return true;
		}

		return false;
	}

	/**
	 * Loads a Set of EObject from a given resource. Content which couldn't be loaded creates a error string which will
	 * be added to the errorStrings list. After the return from the method to the caller the return value contains the
	 * loaded EObjects.
	 * 
	 * @param resource contains the items which should be loaded.
	 * @param errorStrings contains all messages about items which couldn't be loaded by the method.
	 * @return Set with the loaded an valid EObjects
	 */
	private static Set<EObject> validation(final Resource resource, final List<String> errorStrings) {
		final Set<EObject> childrenSet = new LinkedHashSet<EObject>();
		final Set<EObject> rootNodes = new LinkedHashSet<EObject>();

		TreeIterator<EObject> contents = resource.getAllContents();

		// 1. Run: Put all children in set
		while (contents.hasNext()) {
			EObject content = contents.next();
			childrenSet.addAll(content.eContents());
		}

		contents = resource.getAllContents();

		// 2. Run: Check if RootNodes are children -> set.contains(RootNode) 
		// -- no:  RootNode in rootNode-Set 
		// -- yes: Drop RootNode, will be imported as a child
		while (contents.hasNext()) {
			EObject content = contents.next();

			if (!childrenSet.contains(content)) {
				rootNodes.add(content);
			}
		}

		// 3. Check if RootNodes are SelfContained -- yes: import -- no: error
		Set<EObject> notSelfContained = new LinkedHashSet<EObject>();
		for (EObject rootNode : rootNodes) {
			if (!CommonUtil.isSelfContained(rootNode)) {
				errorStrings.add(rootNode + " is not self contained\n");
				notSelfContained.add(rootNode);
			}
		}
		rootNodes.removeAll(notSelfContained);

		return rootNodes;
	}

	/**
	 * Loads a Set of EObject from a given resource. Content which couldn't be loaded creates a error string which will
	 * be added to the errorStrings list. After the return from the method to the caller the return value contains the
	 * loaded EObjects.
	 * 
	 * @param resource contains the items which should be loaded.
	 * @param errorStrings contains all messages about items which couldn't be loaded by the method.
	 * @return Set with the loaded an valid EObjects
	 */
	public static Set<EObject> loadFromResource(final Resource resource, final List<String> errorStrings) {
		return validation(resource, errorStrings);
	}

	/**
	 * If we are running tests. In this case the workspace will be created in
	 * USERHOME/.emfstore.test.
	 * 
	 * @param testing
	 *            the testing to set
	 */
	public static void setTesting(final boolean testing) {
		CommonUtil.testing = testing;
	}

	/**
	 * @return if we are running tests. In this case the workspace will be
	 *         created in USERHOME/.emfstore.test.
	 */
	public static boolean isTesting() {
		return testing;
	}

	/**
	 * Returns the file encoding in use.
	 * 
	 * @return the file encoding
	 */
	public static String getEncoding() {
		return "UTF-8";
	}
}
