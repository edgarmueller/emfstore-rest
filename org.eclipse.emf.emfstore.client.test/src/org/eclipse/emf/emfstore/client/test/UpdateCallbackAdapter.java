package org.eclipse.emf.emfstore.client.test;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.ESChangeConflict;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.internal.client.model.controller.callbacks.IUpdateCallback;
import org.eclipse.emf.emfstore.internal.common.model.IModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.internal.server.exceptions.EMFStoreException;
import org.eclipse.emf.emfstore.server.model.IChangePackage;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;

public class UpdateCallbackAdapter implements IUpdateCallback {

	public boolean inspectChanges(ESLocalProject project, List<? extends IChangePackage> changes,
		IModelElementIdToEObjectMapping idToEObjectMapping) {
		return true;
	}

	public void noChangesOnServer() {

	}

	public boolean conflictOccurred(ESChangeConflict changeConflict, IProgressMonitor progressMonitor) {
		return true;
	}

	public boolean checksumCheckFailed(ESLocalProject project, ESPrimaryVersionSpec versionSpec,
		IProgressMonitor progressMonitor) throws EMFStoreException {
		return true;
	}

}
