package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.controller.UICommitProjectController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;

public class CommitProjectHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		try {
			new UICommitProjectController(getShell()).commit(requireSelection(ProjectSpace.class));
		} catch (RequiredSelectionException e) {
			// TODO:
			e.printStackTrace();
		} catch (EmfStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
