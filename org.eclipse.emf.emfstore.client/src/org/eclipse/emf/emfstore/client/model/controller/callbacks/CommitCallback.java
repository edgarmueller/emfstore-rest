package org.eclipse.emf.emfstore.client.model.controller.callbacks;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;

public interface CommitCallback {

	/**
	 * Called when {@link ProjectSpace} that should be updated is out of date.
	 * A caller may veto against updating the project space by returning false.
	 * 
	 * @param projectSpace
	 *            the project space being out of date
	 * @return true, if the caller is willing to update the project space, false otherwise
	 */
	boolean baseVersionOutOfDate(ProjectSpace projectSpace);

	boolean inspectChanges(ProjectSpace projectSpace, ChangePackage changePackage);

	void noLocalChanges(ProjectSpace projectSpace);

	public final CommitCallback NOCALLBACK = new CommitCallback() {

		public boolean inspectChanges(ProjectSpace projectSpace, ChangePackage changePackage) {
			return true;
		}

		public boolean baseVersionOutOfDate(ProjectSpace projectSpace) {
			return false;
		}

		public void noLocalChanges(ProjectSpace projectSpace) {
			// do nothing
		}
	};
}
