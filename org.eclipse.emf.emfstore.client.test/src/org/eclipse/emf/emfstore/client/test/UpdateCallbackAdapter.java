package org.eclipse.emf.emfstore.client.test;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.IChangeConflict;
import org.eclipse.emf.emfstore.client.ILocalProject;
import org.eclipse.emf.emfstore.internal.client.model.controller.callbacks.IUpdateCallback;
import org.eclipse.emf.emfstore.internal.common.model.IModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.internal.server.exceptions.EMFStoreException;
import org.eclipse.emf.emfstore.server.model.IChangePackage;
import org.eclipse.emf.emfstore.server.model.versionspec.IPrimaryVersionSpec;

public class UpdateCallbackAdapter implements IUpdateCallback {

	public boolean inspectChanges(ILocalProject project, List<? extends IChangePackage> changes,
		IModelElementIdToEObjectMapping idToEObjectMapping) {
		return true;
	}

	public void noChangesOnServer() {

	}

	public boolean conflictOccurred(IChangeConflict changeConflict, IProgressMonitor progressMonitor) {
		return true;
	}

	public boolean checksumCheckFailed(ILocalProject project, IPrimaryVersionSpec versionSpec,
		IProgressMonitor progressMonitor) throws EMFStoreException {
		return true;
	}

}
