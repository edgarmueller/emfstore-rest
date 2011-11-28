package org.eclipse.emf.emfstore.client.ui.commands.handler.handler;

import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.client.ui.commands.handler.AbstractEMFStoreHandler;
import org.eclipse.emf.emfstore.client.ui.commands.handler.controller.UIUpdateProjectController;

public class UpdateProjectHandler extends AbstractEMFStoreHandler {

	@Override
	public Object doExecute() {
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				new UIUpdateProjectController(getShell()).update(getProjectSpace(), null);
			}
		}.run(true);
		return null;
	}

}
