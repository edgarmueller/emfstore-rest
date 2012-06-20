package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.core.commands.IHandler;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.controller.UIShareProjectController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.jface.dialogs.MessageDialog;

public class ShareProjectHandler extends AbstractEMFStoreHandler implements IHandler {

	private final ProjectSpace projectSpace;

	public ShareProjectHandler() {
		projectSpace = null;
	}

	public ShareProjectHandler(ProjectSpace projectSpace) {
		this.projectSpace = projectSpace;
	}

	@Override
	public void handle() throws EmfStoreException {
		if (projectSpace == null) {
			new UIShareProjectController(getShell(), requireSelection(ProjectSpace.class)).execute();
		} else {
			new UIShareProjectController(getShell(), projectSpace).execute();
		}

		MessageDialog.openInformation(getShell(), "Share succeeded", "The project has been successfully shared.");
	}
}
