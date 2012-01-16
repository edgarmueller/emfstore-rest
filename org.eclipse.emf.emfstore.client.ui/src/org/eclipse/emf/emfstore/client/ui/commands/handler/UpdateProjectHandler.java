package org.eclipse.emf.emfstore.client.ui.commands.handler;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.commands.handler.controller.UIUpdateProjectController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;

public class UpdateProjectHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		try {
			new UIUpdateProjectController(getShell()).update(requireSelection(ProjectSpace.class));
		} catch (RequiredSelectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EmfStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
