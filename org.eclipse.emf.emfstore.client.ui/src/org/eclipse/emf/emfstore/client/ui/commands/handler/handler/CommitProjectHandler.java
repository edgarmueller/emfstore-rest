package org.eclipse.emf.emfstore.client.ui.commands.handler.handler;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.commands.handler.AbstractEMFStoreHandler;
import org.eclipse.emf.emfstore.client.ui.commands.handler.controller.UICommitProjectController;

public class CommitProjectHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		new UICommitProjectController(getShell()).commit(requireSelection(ProjectSpace.class));
	}

}
