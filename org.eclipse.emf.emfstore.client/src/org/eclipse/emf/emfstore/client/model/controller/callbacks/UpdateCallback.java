package org.eclipse.emf.emfstore.client.model.controller.callbacks;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.exceptions.ChangeConflictException;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;

public interface UpdateCallback extends GenericCallback {

	public boolean inspectChanges(ProjectSpace projectSpace, List<ChangePackage> changes);

	public void noChangesOnServer();

	public void conflictOccurred(ChangeConflictException changeConflictException);

	public final UpdateCallback NOCALLBACK = new UpdateCallback() {
		public boolean inspectChanges(ProjectSpace projectSpace, List<ChangePackage> changes) {
			return true;
		}

		public void handleException(Exception exception) {
		}

		public void noChangesOnServer() {
		}

		public void conflictOccurred(ChangeConflictException changeConflictException) {
		}

		public void callCompleted(Map<Object, Object> values, boolean successful) {
		}
	};

	/**
	 * Return constants
	 */

	public static final String PROJECTSPACE = "projectspace";
	public static final String NEWVERSION = "newversion";
	public static final String OLDVERSION = "oldversion";
}
