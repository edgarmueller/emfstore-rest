package org.eclipse.emf.emfstore.client.ui.commands.handler.controller;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.controller.callbacks.UpdateCallback;
import org.eclipse.emf.emfstore.client.model.exceptions.ChangeConflictException;
import org.eclipse.emf.emfstore.client.model.util.WorkspaceUtil;
import org.eclipse.emf.emfstore.client.ui.commands.handler.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.client.ui.dialogs.UpdateDialog;
import org.eclipse.emf.emfstore.client.ui.dialogs.merge.MergeProjectHandler;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.VersionSpec;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class UIUpdateProjectController extends AbstractEMFStoreUIController implements UpdateCallback {

	public UIUpdateProjectController(Shell shell) {
		super(shell);
	}

	public void update(ProjectSpace projectSpace) {
		update(projectSpace, null);
	}

	public void update(ProjectSpace projectSpace, VersionSpec version) {
		// TODO sanity check projectspace (is null, is shared)
		openProgress();
		projectSpace.update(version, this, getProgressMonitor());
	}

	public void askForVersionAndUpdate(ProjectSpace projectSpace) {
		update(projectSpace, openVersionDialog(projectSpace));
	}

	protected VersionSpec openVersionDialog(ProjectSpace projectSpace) {
		// TODO implement
		return null;
	}

	public void noChangesOnServer() {
		closeProgress();
		MessageDialog.openInformation(getShell(), "No need to update",
			"Your project is up to date, you do not need to update.");
	}

	public void conflictOccurred(ChangeConflictException conflictException) {
		ProjectSpace projectSpace = conflictException.getProjectSpace();
		try {
			PrimaryVersionSpec targetVersion = projectSpace.resolveVersionSpec(VersionSpec.HEAD_VERSION);
			projectSpace.merge(targetVersion, new MergeProjectHandler(conflictException));
		} catch (EmfStoreException e) {
			WorkspaceUtil.logException("Exception when merging the project!", e);
		}
		closeProgress();
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
		// TODO replace by Observerbus or listener mechanism
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				PlatformUI.getWorkbench().getDecoratorManager()
					.update("org.eclipse.emf.emfstore.client.ui.decorators.VersionDecorator");
			}
		});
		closeProgress();
	}

	@Override
	public void callCompleted(Map<Object, Object> values, boolean successful) {
		updateCompleted((ProjectSpace) values.get(UpdateCallback.PROJECTSPACE),
			(PrimaryVersionSpec) values.get(UpdateCallback.OLDVERSION),
			(PrimaryVersionSpec) values.get(UpdateCallback.NEWVERSION));
	}

}
