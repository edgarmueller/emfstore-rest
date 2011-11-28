package org.eclipse.emf.emfstore.client.model.controller.callbacks;

import java.util.Map;

public interface GenericCallback {

	public void handleException(Exception exception);

	public void callCompleted(Map<Object, Object> values, boolean successful);

	final static GenericCallback NOCALLBACK = new GenericCallback() {
		public void handleException(Exception exception) {
		}

		public void callCompleted(Map<Object, Object> values, boolean successful) {
		}
	};

}
