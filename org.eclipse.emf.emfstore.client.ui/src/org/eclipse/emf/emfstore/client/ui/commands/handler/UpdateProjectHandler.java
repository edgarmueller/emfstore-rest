package org.eclipse.emf.emfstore.client.ui.commands.handler;

import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommand;

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
