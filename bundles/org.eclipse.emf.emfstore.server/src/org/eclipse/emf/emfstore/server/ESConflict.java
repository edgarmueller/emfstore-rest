package org.eclipse.emf.emfstore.server;

import java.util.Set;

import org.eclipse.emf.emfstore.server.model.ESOperation;

public interface ESConflict {

	Set<ESOperation> getLocalOperations();

	Set<ESOperation> getRemoteOperations();

	void resolveConflict(Set<ESOperation> acceptedLocalOperations, Set<ESOperation> rejectedRemoteOperations);
}
