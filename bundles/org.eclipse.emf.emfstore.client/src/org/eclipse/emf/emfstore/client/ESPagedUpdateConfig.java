package org.eclipse.emf.emfstore.client;

public interface ESPagedUpdateConfig {

	String ID = "org.eclipse.emf.emfstore.client.pagedUpdate.config";

	boolean isEnabled();

	int getNumberOfAllowedChanges();
}
