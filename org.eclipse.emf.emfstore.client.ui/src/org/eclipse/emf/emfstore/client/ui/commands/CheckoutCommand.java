package org.eclipse.emf.emfstore.client.ui.commands;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecp.common.util.DialogHandler;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.observers.CheckoutObserver;
import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.client.model.util.WorkspaceUtil;
import org.eclipse.emf.emfstore.client.ui.views.emfstorebrowser.views.ESBrowserView;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.ui.PlatformUI;

/**
 * Command for checkout.
 * 
 * @author koegel
 */
public class CheckoutCommand extends EMFStoreCommand {

	private final ServerInfo serverInfo;
	private final ProjectInfo projectInfo;

	public CheckoutCommand(ServerInfo serverInfo, ProjectInfo projectInfo) {
		this.serverInfo = serverInfo;
		this.projectInfo = projectInfo;
	}

	@Override
	protected void doRun() {
		final ProgressMonitorDialog progressDialog = new ProgressMonitorDialog(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
		try {
			progressDialog.open();
			progressDialog.getProgressMonitor().beginTask(
					"Checkout project...", IProgressMonitor.UNKNOWN);
			ProjectSpace projectSpace = serverInfo.getLastUsersession()
					.checkout(projectInfo);
			WorkspaceUtil.logCheckout(projectSpace,
					projectSpace.getBaseVersion());
			WorkspaceManager.getObserverBus().notify(CheckoutObserver.class)
					.checkoutDone(projectSpace);
		} catch (EmfStoreException e) {
			DialogHandler.showExceptionDialog(e);
			// BEGIN SUPRESS CATCH EXCEPTION
		} catch (RuntimeException e) {
			DialogHandler.showExceptionDialog(e);
			WorkspaceUtil.logWarning("RuntimeException in "
					+ ESBrowserView.class.getName(), e);
			// END SUPRESS CATCH EXCEPTION
		} finally {
			progressDialog.getProgressMonitor().done();
			progressDialog.close();
		}
	}
}
