package org.eclipse.emf.emfstore.client.ui.commands.handler;

import org.eclipse.core.commands.IHandler;
import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommand;

public class ShareProjectHandler extends AbstractEMFStoreHandler implements IHandler {

	@Override
	public Object doExecute() {
		new EMFStoreCommand() {
			@Override
			protected void doRun() {
				new ShareProjectUIController(getShell()).share(getProjectSpace());
			}
		}.run(false);
		return null;
	}

}
