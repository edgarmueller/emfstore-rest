package org.eclipse.emf.emfstore.client.ui.commands.handler.handler;

import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.client.ui.commands.handler.AbstractEMFStoreHandler;
import org.eclipse.emf.emfstore.client.ui.commands.handler.controller.UpdateProjectUIController;

public class UpdateProjectHandler extends AbstractEMFStoreHandler {

	@Override
	public Object doExecute() {
		new EMFStoreCommand() {
			@Override
			protected void doRun() {

				new UpdateProjectUIController(getShell()).update(getProjectSpace(), null);

			}
		}.run(true);
		return null;
	}

}
