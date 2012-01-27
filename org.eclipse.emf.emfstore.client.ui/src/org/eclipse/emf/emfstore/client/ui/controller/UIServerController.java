package org.eclipse.emf.emfstore.client.ui.controller;

import java.util.ArrayList;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.ServerInfo;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommand;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.client.ui.views.emfstorebrowser.views.NewRepositoryWizard;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class UIServerController extends AbstractEMFStoreUIController {

	/**
	 * 
	 * @param shell
	 */
	public UIServerController(Shell shell) {
		super(shell);
	}

	public void addServer() {
		NewRepositoryWizard wizard = new NewRepositoryWizard();
		IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		wizard.init(activeWorkbenchWindow.getWorkbench(), (IStructuredSelection) activeWorkbenchWindow
			.getSelectionService().getSelection());
		WizardDialog dialog = new WizardDialog(activeWorkbenchWindow.getShell(), wizard);
		dialog.create();
		dialog.open();
	}

	public void editProperties(ServerInfo serverInfo) {
		NewRepositoryWizard wizard = new NewRepositoryWizard();
		wizard.setServerInfo(serverInfo);
		WizardDialog dialog = new WizardDialog(getShell(), wizard);
		dialog.create();
		dialog.open();
	}

	/**
	 * TODO REFACTOR
	 */
	public void removeServer(final ServerInfo serverInfo) {
		if (!MessageDialog.openQuestion(Display.getCurrent().getActiveShell(), "Confirm deletion",
			"Are you sure you want to delete \'" + serverInfo.getName() + "\'")) {
			return;
		}

		try {
			EList<ProjectSpace> projectSpaces = WorkspaceManager.getInstance().getCurrentWorkspace().getProjectSpaces();
			ArrayList<ProjectSpace> usedSpaces = new ArrayList<ProjectSpace>();
			for (ProjectSpace projectSpace : projectSpaces) {
				if (projectSpace.getUsersession() != null
					&& projectSpace.getUsersession().getServerInfo().equals(serverInfo)) {
					usedSpaces.add(projectSpace);
				}
			}
			if (usedSpaces.size() > 0) {
				String message = "";
				for (ProjectSpace pSpace : usedSpaces) {
					message += "\n" + pSpace.getProjectName();
				}
				throw new IllegalStateException("Cannot delete \'" + serverInfo.getName()
					+ "\' because it is currently used by the following projects: \n" + message);
			} else {
				// TODO: add code to add & remove server
				new EMFStoreCommand() {
					@Override
					protected void doRun() {
						WorkspaceManager.getInstance().getCurrentWorkspace().getServerInfos().remove(serverInfo);
						EcoreUtil.delete(serverInfo);
						WorkspaceManager.getInstance().getCurrentWorkspace().save();
					};
				}.run(false);
			}
		} catch (IllegalStateException e) {
			MessageDialog.openError(getShell(), "Error", e.getMessage());
		}
	}
}
