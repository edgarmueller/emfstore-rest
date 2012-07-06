package org.eclipse.emf.emfstore.client.ui.dialogs.merge.ui.components;

import org.eclipse.emf.emfstore.client.model.changeTracking.merging.DecisionManager;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.ConflictOption;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public interface DetailsPart {

	Control initialize(DecisionManager manager, ConflictOption option, Composite parent);

}
