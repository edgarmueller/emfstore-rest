package org.eclipse.emf.emfstore.internal.server.model.impl.api;

import org.eclipse.emf.emfstore.internal.common.api.AbstractAPIImpl;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.server.model.ESOperation;

public class ESOperationImpl extends AbstractAPIImpl<ESOperation, AbstractOperation> implements ESOperation {

	private AbstractOperation abstractOperation;

	public ESOperationImpl(AbstractOperation internalType) {
		super(internalType);
		this.abstractOperation = internalType;
	}

}
