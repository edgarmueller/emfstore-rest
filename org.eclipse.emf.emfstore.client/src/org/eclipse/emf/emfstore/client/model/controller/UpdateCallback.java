package org.eclipse.emf.emfstore.client.model.controller;

import java.util.List;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.exceptions.ChangeConflictException;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;

public interface UpdateCallback extends CallbackInterface {

	public boolean inspectChanges(ProjectSpace projectSpace, List<ChangePackage> changes);

	public void noChangesOnServer();

	public void conflictOccurred(ChangeConflictException changeConflictException);

	public void updateCompleted(ProjectSpace projectSpace, PrimaryVersionSpec oldVersion, PrimaryVersionSpec newVersion);

	public final UpdateCallback NOCALLBACK = new UpdateCallback() {
		public boolean inspectChanges(ProjectSpace projectSpace, List<ChangePackage> changes) {
			return true;
		}

		public void handleException(Exception exception) {
		}

		public void updateCompleted(ProjectSpace projectSpace, PrimaryVersionSpec baseVersion,
			PrimaryVersionSpec newVersion) {
		}

		public void noChangesOnServer() {
		}

		public void conflictOccurred(ChangeConflictException changeConflictException) {
		}
	};
}
