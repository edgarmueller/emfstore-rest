package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.controller.UIRevertCommitController;
import org.eclipse.emf.emfstore.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.versioning.HistoryInfo;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;

public class RevertCommitHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() throws EmfStoreException {
		HistoryInfo historyInfo = requireSelection(HistoryInfo.class);
		PrimaryVersionSpec versionSpec = ModelUtil.clone(historyInfo.getPrimerySpec());
		new UIRevertCommitController(getShell(), requireSelection(ProjectSpace.class), versionSpec).execute();
	}
}
