package org.eclipse.emf.emfstore.client.test;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.ILocalProject;
import org.eclipse.emf.emfstore.internal.client.model.controller.callbacks.ICommitCallback;
import org.eclipse.emf.emfstore.internal.common.model.IModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.internal.server.exceptions.EMFStoreException;
import org.eclipse.emf.emfstore.server.model.IChangePackage;
import org.eclipse.emf.emfstore.server.model.versionspec.IPrimaryVersionSpec;

public class CommitCallbackAdapter implements ICommitCallback {

	public boolean baseVersionOutOfDate(ILocalProject project, IProgressMonitor progressMonitor) {
		return true;
	}

	public boolean inspectChanges(ILocalProject project, IChangePackage changePackage,
		IModelElementIdToEObjectMapping idToEObjectMapping) {
		return true;
	}

	public void noLocalChanges(ILocalProject projectSpace) {
		// do nothing
	}

	public boolean checksumCheckFailed(ILocalProject projectSpace, IPrimaryVersionSpec versionSpec,
		IProgressMonitor monitor) throws EMFStoreException {
		return true;
	}

}
