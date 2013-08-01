package org.eclipse.emf.emfstore.client.transaction;

import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.impl.TransactionalCommandStackImpl;
import org.eclipse.emf.transaction.internal.Tracing;

public abstract class AbstractEMFStoreTransactionalCommandStackImpl extends TransactionalCommandStackImpl {

	/*
	 * Copied from parent to enable undo hook.
	 */
	@Override
	public void undo() {
		if (canUndo()) {
			try {
				Transaction tx = createTransaction(getUndoCommand(), getUndoRedoOptions());

				basicUndo();

				tx.commit();
			} catch (Exception e) {
				// just log it and roll back if necessary
				Tracing.catching(TransactionalCommandStackImpl.class, "undo", e); //$NON-NLS-1$
				handleError(e);
			}
		}
	}

	/*
	 * Copied from parent to enable redo hook.
	 */
	@Override
	public void redo() {
		if (canRedo()) {
			try {
				Transaction tx = createTransaction(getRedoCommand(), getUndoRedoOptions());

				basicRedo();

				tx.commit();
			} catch (Exception e) {
				// just log it and roll back if necessary
				Tracing.catching(TransactionalCommandStackImpl.class, "redo", e); //$NON-NLS-1$
				handleError(e);
			}
		}
	}

	/**
	 * Undo hook to enable the execution of the undo within a transaction.
	 */
	protected abstract void basicUndo();

	/**
	 * Redo hook to enable the execution of the redo within a transaction.
	 */
	protected abstract void basicRedo();

}
