package org.eclipse.emf.emfstore.client.ui.commands.handlers;

import org.eclipse.emf.emfstore.client.ui.commands.controller.UIRemoveTagController;
import org.eclipse.emf.emfstore.server.model.versioning.HistoryInfo;

public class RemoveTagHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		new UIRemoveTagController(getShell(), requireSelection(HistoryInfo.class)).removeTag();
	}

}
