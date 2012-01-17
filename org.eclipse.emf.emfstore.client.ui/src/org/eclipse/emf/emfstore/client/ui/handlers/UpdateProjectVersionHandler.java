package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.controller.UIUpdateProjectController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;

public class UpdateProjectVersionHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		try {
			new UIUpdateProjectController(getShell()).askForVersionAndUpdate(requireSelection(ProjectSpace.class));
		} catch (RequiredSelectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EmfStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
