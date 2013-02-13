package org.eclipse.emf.emfstore.server.model.versionspec;

import org.eclipse.emf.emfstore.internal.server.model.versioning.VersionsFactory;

/**
 * <p>
 * Represents a version specifier.
 * </p>
 * <p>
 * A version specifier has a branch name which identifies the respective branch.
 * </p>
 * 
 * @author wesendon
 * @author emueller
 */
public interface IVersionSpec {

	/**
	 * The factory used for creating {@link IVersionSpec}s.
	 */
	IVersionFactory FACTORY = VersionsFactory.INSTANCE;

	String HEAD = "HEAD";

	String BASE = "BASE";

	String BRANCH_DEFAULT_NAME = "trunk";

	// magic global variable
	String GLOBAL = "___GLOBAL___";

	/**
	 * Returns the branch name.
	 * 
	 * @return the branch name of this version specifier
	 */
	String getBranch();

}
