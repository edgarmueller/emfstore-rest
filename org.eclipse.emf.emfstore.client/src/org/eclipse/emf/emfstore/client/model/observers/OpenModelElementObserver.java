package org.eclipse.emf.emfstore.client.model.observers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.common.observer.IObserver;

public interface OpenModelElementObserver extends IObserver {

	void openModelElement(EObject modelElement);
}
