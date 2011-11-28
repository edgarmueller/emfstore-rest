package org.eclipse.emf.emfstore.client.model.controller.callbacks;

import java.util.Map;

public interface CommitCallback extends GenericCallback {

	public final CommitCallback NOCALLBACK = new CommitCallback() {

		public void handleException(Exception exception) {
		}

		public void callCompleted(Map<Object, Object> values, boolean successful) {
		}

	};
}
