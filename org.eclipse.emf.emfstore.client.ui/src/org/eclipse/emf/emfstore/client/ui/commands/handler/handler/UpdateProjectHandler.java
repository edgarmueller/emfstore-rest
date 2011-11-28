package org.eclipse.emf.emfstore.client.ui.commands.handler.handler;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.commands.handler.AbstractEMFStoreHandler;
import org.eclipse.emf.emfstore.client.ui.commands.handler.EMFStoreUICommand;
import org.eclipse.emf.emfstore.client.ui.commands.handler.controller.UIUpdateProjectController;

public class UpdateProjectHandler extends AbstractEMFStoreHandler {

	@Override
	public Object doExecute() {
		new EMFStoreUICommand() {
			@Override
			protected void doRun() {
				new UIUpdateProjectController(getShell()).update(requireSelection(ProjectSpace.class));
				// EMFStore.update(requireSelection(ProjectSpace.class));
			}
		}.run(true);
		return null;
	}

}
