package org.eclipse.emf.emfstore.client.ui.commands.handler;

import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecp.common.util.DialogHandler;
import org.eclipse.emf.emfstore.client.model.controller.callbacks.GenericCallback;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.widgets.Shell;

public abstract class AbstractEMFStoreUIController implements GenericCallback {

	protected Shell shell;
	protected ProgressMonitorDialog progressDialog;

	public Shell getShell() {
		return shell;
	}

	public void setShell(Shell shell) {
		this.shell = shell;
	}

	public void handleException(Exception exception) {
		DialogHandler.showExceptionDialog(exception);
		closeProgress();
	}

	public void callCompleted(Map<Object, Object> values, boolean successful) {
	}

	protected ProgressMonitorDialog getProgressMonitorDialog() {
		progressDialog = new ProgressMonitorDialog(getShell());
		progressDialog.open();
		progressDialog.setCancelable(true);
		return progressDialog;
	}

	protected void closeProgress() {
		if (progressDialog != null) {
			progressDialog.close();
		}
	}

	protected IProgressMonitor getProgressMonitor() {
		if (progressDialog != null) {
			return progressDialog.getProgressMonitor();
		}
		return new NullProgressMonitor();
	}

}
