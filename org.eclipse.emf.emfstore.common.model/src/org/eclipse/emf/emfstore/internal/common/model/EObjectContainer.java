package org.eclipse.emf.emfstore.internal.common.model;

import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.common.model.IModelElementId;

public interface EObjectContainer {

	EObject getModelElement(IModelElementId modelElementId);

	IModelElementId getModelElementId(EObject eObject);

	EList<EObject> getModelElements();

	Set<EObject> getAllModelElements();

	boolean contains(IModelElementId modelElementId);

	// TOOD: parameter type
	boolean contains(EObject object);

	/**
	 * Retrieve a list of all model elements of a certain type in the
	 * collection.
	 * 
	 * @param <T>
	 *            a sub-type of model element
	 * @param modelElementClass
	 *            the {@link EClass}
	 * @param includeSubclasses
	 *            whether to also include all subclasses of the given {@link EClass} in the list
	 * @return a list of model elements of the given type
	 */
	<T extends EObject> Set<T> getAllModelElementsByClass(Class<T> modelElementClass, Boolean includeSubclasses);

	/**
	 * Retrieve a list of all model elements of a certain type in the
	 * collection.
	 * 
	 * @param <T>
	 *            a sub-type of model element
	 * @param modelElementClass
	 *            the {@link EClass}
	 * @return a list of model elements of the given type
	 */
	<T extends EObject> Set<T> getAllModelElementsByClass(Class<T> modelElementClass);

}