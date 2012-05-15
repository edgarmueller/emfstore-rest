package org.eclipse.emf.emfstore.client.ui.controller;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.ui.handlers.AbstractEMFStoreUIController;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.swt.widgets.Shell;

public class UIUndoLastOperationController extends AbstractEMFStoreUIController<Void> {

	private final ProjectSpace projectSpace;

	public UIUndoLastOperationController(Shell shell, ProjectSpace projectSpace) {
		super(shell);
		this.projectSpace = projectSpace;
	}

	@Override
	protected Void doRun(IProgressMonitor progressMonitor) throws EmfStoreException {
		projectSpace.getOperationManager().undoLastOperation();
		return null;
	}

}
