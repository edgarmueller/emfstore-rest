package org.eclipse.emf.emfstore.client.ui.commands.handlers;

import org.eclipse.emf.emfstore.client.ui.commands.controller.UIForceRevertCommitController;
import org.eclipse.emf.emfstore.server.model.versioning.HistoryInfo;

public class ForceRevertHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		new UIForceRevertCommitController(getShell(), requireSelection(HistoryInfo.class)).revert();
	}

}
