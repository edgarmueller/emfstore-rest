package org.eclipse.emf.emfstore.server.model.api.versionspec;

public interface IAncestorVersionSpec extends IVersionSpec {

	IPrimaryVersionSpec getTarget();

	IPrimaryVersionSpec getSource();

}
