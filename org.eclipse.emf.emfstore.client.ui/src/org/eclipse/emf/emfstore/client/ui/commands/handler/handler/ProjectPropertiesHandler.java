package org.eclipse.emf.emfstore.client.ui.commands.handler.handler;

import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.ui.commands.handler.AbstractEMFStoreHandler;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.ui.dialogs.PreferencesUtil;

public class ProjectPropertiesHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		// TODO: Active Project good?
		PreferenceDialog propertyDialog = PreferencesUtil.createPropertyDialogOn(getShell(), WorkspaceManager
			.getInstance().getCurrentWorkspace().getActiveProjectSpace().getProject(), null, null, null);
		if (propertyDialog != null) {
			propertyDialog.open();
		}
	}

}
