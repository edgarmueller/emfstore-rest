package org.eclipse.emf.emfstore.client.ui.commands.handler;

import java.util.List;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.controller.UpdateCallback;
import org.eclipse.emf.emfstore.client.model.exceptions.ChangeConflictException;
import org.eclipse.emf.emfstore.client.model.exceptions.NoChangesOnServerException;
import org.eclipse.emf.emfstore.client.model.util.WorkspaceUtil;
import org.eclipse.emf.emfstore.client.ui.commands.MergeProjectHandler;
import org.eclipse.emf.emfstore.client.ui.dialogs.UpdateDialog;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersionSpec;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class UpdateProjectUIController extends AbstractEMFStoreUIController implements UpdateCallback {

	public UpdateProjectUIController(Shell shell) {
		this.setShell(shell);
	}

	public void update(ProjectSpace projectSpace, VersionSpec version) {
		progressDialog = getProgressMonitorDialog();
		projectSpace.update(version, this, progressDialog.getProgressMonitor());
	}

	public void handleException(Exception exception) {
		if (exception instanceof NoChangesOnServerException) {
			handleNoChangesException();
		} else if (exception instanceof ChangeConflictException) {
			handleChangeConflictException((ChangeConflictException) exception);
		} else {
			super.handleException(exception);
		}
	}

	protected void handleNoChangesException() {
		closeProgress();
		MessageDialog.openInformation(getShell(), "No need to update",
			"Your project is up to date, you do not need to update.");
	}

	protected void handleChangeConflictException(ChangeConflictException conflictException) {
		closeProgress();
		ProjectSpace projectSpace = conflictException.getProjectSpace();
		try {
			PrimaryVersionSpec targetVersion = projectSpace.resolveVersionSpec(VersionSpec.HEAD_VERSION);
			projectSpace.merge(targetVersion, new MergeProjectHandler(conflictException));
		} catch (EmfStoreException e) {
			WorkspaceUtil.logException("Exception when merging the project!", e);
		}
	}

	public boolean inspectChanges(ProjectSpace projectSpace, List<ChangePackage> changePackages) {
		UpdateDialog updateDialog = new UpdateDialog(getShell(), projectSpace, changePackages);
		if (updateDialog.open() == Window.OK) {
			return true;
		}
		return false;
	}

	public void updateCompleted(ProjectSpace projectSpace, PrimaryVersionSpec oldVersion, PrimaryVersionSpec newVersion) {
		WorkspaceUtil.logUpdate(projectSpace, oldVersion, newVersion);
		// explicitly refresh the decorator since no simple attribute has
		// been changed
		// (as opposed to committing where the dirty property is being set)
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				PlatformUI.getWorkbench().getDecoratorManager()
					.update("org.eclipse.emf.emfstore.client.ui.decorators.VersionDecorator");
			}
		});
		closeProgress();
	}

}
