package org.eclipse.emf.emfstore.client.ui.commands.handler.handler;

import org.eclipse.emf.emfstore.client.ui.commands.handler.AbstractEMFStoreHandler;
import org.eclipse.emf.emfstore.client.ui.commands.handler.controller.UIForceRevertCommitController;
import org.eclipse.emf.emfstore.server.model.versioning.HistoryInfo;

public class ForceRevertHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		new UIForceRevertCommitController(getShell(), requireSelection(HistoryInfo.class)).revert();
	}

}
