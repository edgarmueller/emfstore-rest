package org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.util;

import org.eclipse.emf.emfstore.common.ESObserver;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;

public interface OperationAuthorProvider extends ESObserver {

	String getAuthor(AbstractOperation operation);

}
