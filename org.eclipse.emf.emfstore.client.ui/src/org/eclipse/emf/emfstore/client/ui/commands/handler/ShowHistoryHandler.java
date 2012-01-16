package org.eclipse.emf.emfstore.client.ui.commands.handler;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.commands.handler.controller.UIShowHistoryController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.jface.dialogs.MessageDialog;

public class ShowHistoryHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		try {
			new UIShowHistoryController(getShell(), requireSelection(ProjectSpace.class),
				requireSelection(EObject.class)).showHistory();
		} catch (RequiredSelectionException e) {
			MessageDialog.openInformation(getShell(), "Information", "You did not select a element.");
		} catch (EmfStoreException e) {
			MessageDialog.openError(getShell(), "Error",
				"An error occurred while fetching the history: " + e.getMessage());
		}
	}

}
