package org.eclipse.emf.emfstore.client.ui.commands.handler.controller;

import java.util.Map;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.controller.callbacks.GenericCallback;
import org.eclipse.emf.emfstore.client.model.impl.ProjectSpaceImpl;
import org.eclipse.emf.emfstore.client.ui.commands.handler.AbstractEMFStoreUIController;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.widgets.Shell;

public class UIShareProjectController extends AbstractEMFStoreUIController implements GenericCallback {

	public UIShareProjectController(Shell shell) {
		super(shell);
	}

	public void share(ProjectSpace projectSpace) {
		ProgressMonitorDialog progressDialog = openProgress();
		((ProjectSpaceImpl) projectSpace).shareProject(null, this, progressDialog.getProgressMonitor());
	}

	public void shareCompleted(ProjectSpace projectSpace, boolean successful) {
		closeProgress();
		if (successful) {
			MessageDialog.openInformation(getShell(), "Share completed", "The project was successfully shared.");
		}
	}

	@Override
	public void callCompleted(Map<Object, Object> values, boolean successful) {
		shareCompleted((ProjectSpace) values.get(ProjectSpace.class), successful);
	}
}
