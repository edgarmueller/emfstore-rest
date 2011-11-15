package org.eclipse.emf.emfstore.client.ui.commands.handler;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.controller.ShareCallback;
import org.eclipse.emf.emfstore.client.model.impl.ProjectSpaceImpl;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class ShareProjectUIController extends AbstractEMFStoreUIController implements ShareCallback {

	public ShareProjectUIController(Shell shell) {
		setShell(shell);
	}

	public void share(ProjectSpace projectSpace) {
		progressDialog = getProgressMonitorDialog();
		((ProjectSpaceImpl) projectSpace).shareProject(null, this, progressDialog.getProgressMonitor());
	}

	public void shareCompleted(ProjectSpace projectSpace, boolean canceled) {
		closeProgress();
		if (!canceled) {
			MessageDialog.openInformation(getShell(), "Share completed", "The project was successfully shared.");
		}
	}

}
