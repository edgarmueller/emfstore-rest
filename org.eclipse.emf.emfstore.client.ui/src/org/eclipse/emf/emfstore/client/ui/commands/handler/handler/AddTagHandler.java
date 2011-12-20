package org.eclipse.emf.emfstore.client.ui.commands.handler.handler;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.commands.handler.AbstractEMFStoreHandler;
import org.eclipse.emf.emfstore.client.ui.commands.handler.controller.UIAddTagController;
import org.eclipse.emf.emfstore.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.server.model.versioning.HistoryInfo;

public class AddTagHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		HistoryInfo historyInfo = requireSelection(HistoryInfo.class);
		ProjectSpace projectSpace = (ProjectSpace) ModelUtil.getParent(ProjectSpace.class, historyInfo);
		new UIAddTagController(getShell(), projectSpace, historyInfo).addTag();
	}
}
