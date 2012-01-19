package org.eclipse.emf.emfstore.client.ui.handlers;

import org.eclipse.emf.emfstore.client.model.util.EMFStoreCommand;

public abstract class EMFStoreUICommand extends EMFStoreCommand {

	@Override
	protected void commandBody() {
		try {
			super.commandBody();
		} catch (RequiredSelectionException e) {
			// TODO Handle Exception
		}
	}
}
