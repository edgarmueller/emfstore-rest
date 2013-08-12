/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Maximilian Koegel
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.transaction;

import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.impl.TransactionalCommandStackImpl;
import org.eclipse.emf.transaction.internal.Tracing;

/**
 * Abstract superclass for EMFStore CommandStacks supporting transaction. Will be replaced by correspponding class from
 * EMF Transaction as soon as it becomes available which is planned for EMF 2.10.
 * 
 * @author mkoegel
 * 
 */
@SuppressWarnings("restriction")
public abstract class AbstractEMFStoreTransactionalCommandStackImpl extends TransactionalCommandStackImpl {

	private static final String REDO_METHOD_NAME = "redo";
	private static final String UNDO_METHOD_NAME = "undo";

	/*
	 * Copied from parent to enable undo hook.
	 */
	@Override
	public void undo() {
		if (canUndo()) {
			try {
				final Transaction tx = createTransaction(getUndoCommand(), getUndoRedoOptions());

				basicUndo();

				tx.commit();

				// BEGIN SUPRESS CATCH EXCEPTION
			} catch (final RuntimeException e) {
				// END SUPRESS CATCH EXCEPTION
				logAndRollback(e, UNDO_METHOD_NAME);
			} catch (final RollbackException ex) {
				logAndRollback(ex, UNDO_METHOD_NAME);
			} catch (final InterruptedException ex) {
				logAndRollback(ex, UNDO_METHOD_NAME);
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
				final Transaction tx = createTransaction(getRedoCommand(), getUndoRedoOptions());

				basicRedo();

				tx.commit();
				// BEGIN SUPRESS CATCH EXCEPTION
			} catch (final RuntimeException e) {
				// END SUPRESS CATCH EXCEPTION
				logAndRollback(e, REDO_METHOD_NAME);
			} catch (final RollbackException ex) {
				logAndRollback(ex, REDO_METHOD_NAME);
			} catch (final InterruptedException ex) {
				logAndRollback(ex, REDO_METHOD_NAME);
			}
		}
	}

	private void logAndRollback(final Exception e, String methodName) {
		Tracing.catching(TransactionalCommandStackImpl.class, methodName, e);
		handleError(e);
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
