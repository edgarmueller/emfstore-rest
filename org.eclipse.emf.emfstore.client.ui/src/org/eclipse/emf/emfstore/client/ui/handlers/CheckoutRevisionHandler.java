package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.emf.emfstore.client.ui.controller.UICheckoutController;
import org.eclipse.emf.emfstore.client.ui.views.historybrowserview.HistoryBrowserView;
import org.eclipse.emf.emfstore.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.ProjectInfo;
import org.eclipse.emf.emfstore.server.model.versioning.HistoryInfo;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * Handler for checking out a project.
 * 
 * @author emueller
 * 
 */
public class CheckoutRevisionHandler extends AbstractEMFStoreHandler {

	@Override
	public void handle() throws EmfStoreException {

		HistoryInfo historyInfo = requireSelection(HistoryInfo.class);
		PrimaryVersionSpec versionSpec = ModelUtil.clone(historyInfo.getPrimerySpec());

		// TODO: remove HistoryBrowserView
		IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
		if (activePage == null) {
			return;
		}

		if (!(activePage.getActivePart() instanceof HistoryBrowserView)) {
			return;
		}

		HistoryBrowserView view = (HistoryBrowserView) activePage.getActivePart();

		ProjectInfo projectInfo = view.getProjectSpace().getProjectInfo();

		// FIXME: eContainer call
		new UICheckoutController(getShell()).checkout(view.getProjectSpace().getUsersession().getServerInfo(),
			projectInfo, versionSpec);
	}

}
