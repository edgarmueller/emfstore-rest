package org.eclipse.emf.emfstore.server.model.api.versionspecs;

public interface IAncestorVersionSpec extends IVersionSpec {

	IPrimaryVersionSpec getTarget();

	IPrimaryVersionSpec getSource();

}
