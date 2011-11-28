package org.eclipse.emf.emfstore.client.ui.commands.handler.handler;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.commands.handler.AbstractEMFStoreHandler;
import org.eclipse.emf.emfstore.client.ui.commands.handler.EMFStoreUICommand;
import org.eclipse.emf.emfstore.client.ui.commands.handler.controller.UIDeleteProjectController;

public class DeleteProjectHandler extends AbstractEMFStoreHandler {

	@Override
	public Object doExecute() {
		new EMFStoreUICommand() {
			@Override
			protected void doRun() {
				new UIDeleteProjectController(getShell()).deleteProject(requireSelection(ProjectSpace.class));
			}
		}.run(false);
		return null;
	}
}
