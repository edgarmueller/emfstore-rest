package org.eclipse.emf.emfstore.client.test;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.internal.client.model.controller.callbacks.ICommitCallback;
import org.eclipse.emf.emfstore.internal.common.model.IModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.model.ESChangePackage;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;

public class CommitCallbackAdapter implements ICommitCallback {

	public boolean baseVersionOutOfDate(ESLocalProject project, IProgressMonitor progressMonitor) {
		return true;
	}

	public boolean inspectChanges(ESLocalProject project, ESChangePackage changePackage,
		IModelElementIdToEObjectMapping idToEObjectMapping) {
		return true;
	}

	public void noLocalChanges(ESLocalProject projectSpace) {
		// do nothing
	}

	public boolean checksumCheckFailed(ESLocalProject projectSpace, ESPrimaryVersionSpec versionSpec,
		IProgressMonitor monitor) throws ESException {
		return true;
	}

}
