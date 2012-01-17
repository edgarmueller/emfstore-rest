package org.eclipse.emf.emfstore.client.ui.commands.controller;

import org.eclipse.emf.emfstore.client.model.controller.RemoveTagController;
import org.eclipse.emf.emfstore.client.ui.commands.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.client.ui.views.historybrowserview.HistoryBrowserView;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.versioning.HistoryInfo;
import org.eclipse.emf.emfstore.server.model.versioning.TagVersionSpec;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

public class UIRemoveTagController extends AbstractEMFStoreUIController {

	private final HistoryInfo historyInfo;

	// TODO: remove historyBrowserView
	public UIRemoveTagController(Shell shell, HistoryInfo historyInfo) {
		super(shell);
		this.historyInfo = historyInfo;
	}

	public void removeTag() {

		final LabelProvider tagLabelProvider = new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((TagVersionSpec) element).getName();
			}
		};

		ElementListSelectionDialog dlg = new ElementListSelectionDialog(PlatformUI.getWorkbench()
			.getActiveWorkbenchWindow().getShell(), tagLabelProvider);
		dlg.setElements(historyInfo.getTagSpecs().toArray());
		dlg.setTitle("Tag selection");
		dlg.setBlockOnOpen(true);
		dlg.setMultipleSelection(true);
		int ret = dlg.open();
		if (ret != Window.OK) {
			return;
		}

		// TODO: controller currently does not work if the active workbench window is not
		// the history view
		IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
		if (activePage == null || !(activePage.getActivePart() instanceof HistoryBrowserView)) {
			return;
		}

		HistoryBrowserView historyBrowserView = (HistoryBrowserView) activePage.getActivePart();

		Object[] tags = dlg.getResult();
		for (Object tag : tags) {
			try {
				new RemoveTagController(historyBrowserView.getProjectSpace(), historyInfo.getPrimerySpec(),
					(TagVersionSpec) tag).execute();
			} catch (EmfStoreException e) {
				MessageDialog.openError(getShell(), "Error",
					"An error occurred during the remove of the tag: " + e.getMessage());
			}
		}

		historyBrowserView.refresh();

		return;
	}
}
