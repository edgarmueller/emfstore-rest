package org.eclipse.emf.emfstore.server.model.versionspec;

public interface IAncestorVersionSpec extends IVersionSpec {

	IPrimaryVersionSpec getTarget();

	IPrimaryVersionSpec getSource();

}
