package org.eclipse.emf.emfstore.client.ui.commands.handler.handler;

import org.eclipse.core.commands.IHandler;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.commands.handler.AbstractEMFStoreHandler;
import org.eclipse.emf.emfstore.client.ui.commands.handler.EMFStoreUICommand;
import org.eclipse.emf.emfstore.client.ui.commands.handler.controller.UIShareProjectController;

public class ShareProjectHandler extends AbstractEMFStoreHandler implements IHandler {

	@Override
	public Object doExecute() {
		new EMFStoreUICommand() {
			@Override
			protected void doRun() {
				new UIShareProjectController(getShell()).share(requireSelection(ProjectSpace.class));
			}
		}.run(false);
		return null;
	}

}
