package org.eclipse.emf.emfstore.server.model.api.versionspecs;

public interface IVersionSpec {

	String HEAD = "HEAD";

	String BASE = "BASE";

	String BRANCH_DEFAULT_NAME = "trunk";

	// magic global variable
	String GLOBAL = "___GLOBAL___";

	String getBranch();
}
