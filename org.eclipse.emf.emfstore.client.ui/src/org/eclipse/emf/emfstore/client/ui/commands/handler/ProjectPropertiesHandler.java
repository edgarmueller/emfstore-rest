package org.eclipse.emf.emfstore.client.ui.commands.handler;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.ui.dialogs.PreferencesUtil;

public class ProjectPropertiesHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() {
		// TODO: Active Project good?
		// TODO: Controller?
		PreferenceDialog propertyDialog = PreferencesUtil.createPropertyDialogOn(getShell(),
			requireSelection(ProjectSpace.class).getProject(), null, null, null);
		if (propertyDialog != null) {
			propertyDialog.open();
		}
	}

}
