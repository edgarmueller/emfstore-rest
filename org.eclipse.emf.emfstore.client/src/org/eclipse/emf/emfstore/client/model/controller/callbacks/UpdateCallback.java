package org.eclipse.emf.emfstore.client.model.controller.callbacks;

import java.util.List;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.exceptions.ChangeConflictException;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;

public interface UpdateCallback {

	public boolean inspectChanges(ProjectSpace projectSpace, List<ChangePackage> changes);

	public void noChangesOnServer();

	public boolean conflictOccurred(ChangeConflictException changeConflictException);

	public final UpdateCallback NOCALLBACK = new UpdateCallback() {
		public boolean inspectChanges(ProjectSpace projectSpace, List<ChangePackage> changes) {
			return true;
		}

		public void noChangesOnServer() {
		}

		public boolean conflictOccurred(ChangeConflictException changeConflictException) {
			return false;
		}
	};

	/**
	 * Return constants
	 */

	public static final String PROJECTSPACE = "projectspace";
	public static final String NEWVERSION = "newversion";
	public static final String OLDVERSION = "oldversion";
}
