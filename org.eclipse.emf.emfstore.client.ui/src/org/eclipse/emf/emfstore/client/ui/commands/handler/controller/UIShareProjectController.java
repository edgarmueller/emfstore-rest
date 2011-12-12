package org.eclipse.emf.emfstore.client.ui.commands.handler.controller;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.impl.ProjectSpaceImpl;
import org.eclipse.emf.emfstore.client.ui.commands.handler.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.widgets.Shell;

public class UIShareProjectController extends AbstractEMFStoreUIController {

	public UIShareProjectController(Shell shell) {
		super(shell);
	}

	public void share(ProjectSpace projectSpace) throws EmfStoreException {
		ProgressMonitorDialog progressDialog = openProgress();
		((ProjectSpaceImpl) projectSpace).shareProject(null, progressDialog.getProgressMonitor());
		closeProgress();
	}

	public void shareCompleted(ProjectSpace projectSpace, boolean successful) {
		closeProgress();
		if (successful) {
			MessageDialog.openInformation(getShell(), "Share completed", "The project was successfully shared.");
		}
	}

}
