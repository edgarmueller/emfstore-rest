package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.controller.UIMergeController;

public class MergeBranchHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		new UIMergeController(getShell(), requireSelection(ProjectSpace.class)).execute();
	}

}