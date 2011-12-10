package org.eclipse.emf.emfstore.client.model.controller.callbacks;

import java.util.Map;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;

public interface CommitCallback extends GenericCallback {

	public final String NEWVERSION = "newversion";

	void noLocalChanges(ProjectSpace projectSpace);

	boolean baseVersionOutOfDate(ProjectSpace projectSpace);

	boolean inspectChanges(ProjectSpace projectSpace, ChangePackage changePackage);

	public final CommitCallback NOCALLBACK = new CommitCallback() {

		public void handleException(Exception exception) {
		}

		public void callCompleted(Map<Object, Object> values, boolean successful) {
		}

		public boolean inspectChanges(ProjectSpace projectSpace, ChangePackage changePackage) {
			return true;
		}

		public void noLocalChanges(ProjectSpace projectSpace) {

		}

		public boolean baseVersionOutOfDate(ProjectSpace projectSpace) {
			return false;
		}
	};
}
