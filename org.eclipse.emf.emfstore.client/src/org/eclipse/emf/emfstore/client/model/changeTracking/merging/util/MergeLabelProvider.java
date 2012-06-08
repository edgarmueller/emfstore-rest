package org.eclipse.emf.emfstore.client.model.changeTracking.merging.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.common.observer.PrioritizedIObserver;

public interface MergeLabelProvider extends PrioritizedIObserver {

	String getText(EObject modelElement);

}
