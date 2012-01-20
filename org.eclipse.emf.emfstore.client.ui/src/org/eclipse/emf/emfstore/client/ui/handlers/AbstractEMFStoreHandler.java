package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.emf.emfstore.client.ui.util.EMFStoreMessageDialog;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;

public abstract class AbstractEMFStoreHandler extends AbstractEMFStoreHandlerWithResult<Object> {

	@Override
	public Object handleWithResult() {
		try {
			handle();
		} catch (RequiredSelectionException e) {
			// Todo better message
			handleException(e);
		} catch (Exception e) {
			handleException(e);
		}
		return null;
	}

	public abstract void handle() throws EmfStoreException;

	public void handleException(Exception exception) {
		EMFStoreMessageDialog.showExceptionDialog(exception);
	}

}
