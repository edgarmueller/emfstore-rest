package org.eclipse.emf.emfstore.client.model.controller;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;

// TODO allow to change project name and description with this callback;
public interface ShareCallback extends CallbackInterface {

	void shareCompleted(ProjectSpace projectSpace, boolean canceled);

	ShareCallback NOCALLBACK = new ShareCallback() {
		public void handleException(Exception exception) {
		}

		public void shareCompleted(ProjectSpace projectSpace, boolean canceled) {
		}
	};

}
