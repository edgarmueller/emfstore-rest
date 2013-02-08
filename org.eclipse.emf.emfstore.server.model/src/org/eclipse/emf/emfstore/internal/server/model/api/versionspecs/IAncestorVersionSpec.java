package org.eclipse.emf.emfstore.internal.server.model.api.versionspecs;

public interface IAncestorVersionSpec extends IVersionSpec {

	IPrimaryVersionSpec getTarget();

	IPrimaryVersionSpec getSource();

}
