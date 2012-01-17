package org.eclipse.emf.emfstore.client.ui.commands.handlers;

public abstract class AbstractEMFStoreHandler extends AbstractEMFStoreHandlerWithResult<Object> {

	@Override
	public Object handleWithResult() {
		handle();
		return null;
	}

	public abstract void handle();
}
