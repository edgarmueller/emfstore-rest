/*******************************************************************************
 * Copyright 2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.model.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.emfstore.client.model.CompositeOperationHandle;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.observers.OperationObserver;
import org.eclipse.emf.emfstore.common.IDisposable;
import org.eclipse.emf.emfstore.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.server.model.versioning.operations.CompositeOperation;
import org.eclipse.emf.emfstore.server.model.versioning.operations.semantic.SemanticCompositeOperation;

/**
 * This class acts as a simple wrapper around the operation recorder and provides convenience methods
 * for undoing operations and handling composite operations.
 * 
 * @author koegel
 * @author emueller
 */
public class OperationManager implements OperationRecorderListener, IDisposable {

	private OperationRecorder operationRecorder;
	private List<OperationObserver> operationListeners;
	private ProjectSpace projectSpace;

	/**
	 * Constructor.
	 * 
	 * @param operationRecorder
	 *            the operation recorder that should get wrapped
	 */
	public OperationManager(OperationRecorder operationRecorder) {
		this.operationRecorder = operationRecorder;
		this.projectSpace = operationRecorder.getProjectSpace();
		operationListeners = new ArrayList<OperationObserver>();
	}

	/**
	 * Undo the last operation of the projectSpace.
	 */
	public void undoLastOperation() {
		projectSpace.undoLastOperations(1);
	}

	/**
	 * Adds an operation observer that gets notified whenever an operation
	 * is either executed or undone.
	 * 
	 * @param operationObserver
	 *            the operation observer to be added
	 */
	public void addOperationListener(OperationObserver operationObserver) {
		operationListeners.add(operationObserver);
	}

	/**
	 * Removed the given operation observer from the list of operation observers.
	 * 
	 * @param operationObserver
	 *            the operation observer to be removed
	 */
	public void removeOperationListener(OperationObserver operationObserver) {
		operationListeners.remove(operationObserver);

	}

	/**
	 * Notifies all operations observer that an operation has been undone.
	 * 
	 * @param operation
	 *            the operation that has been undone
	 */
	public void notifyOperationUndone(AbstractOperation operation) {
		for (OperationObserver operationListener : operationListeners) {
			operationListener.operationUnDone(operation);
		}
	}

	/**
	 * Notify the operation observer that an operation has just completed.
	 * 
	 * @param operation
	 *            the operation
	 */
	void notifyOperationExecuted(AbstractOperation operation) {

		// do not notify on composite start, wait until completion
		if (operation instanceof CompositeOperation) {
			// check of automatic composite, if yes then continue
			if (((CompositeOperation) operation).getMainOperation() == null) {
				// && ((CompositeOperation) operation).getModelElementId() == null) {
				// return;
			}
		}

		for (OperationObserver operationListener : operationListeners) {
			operationListener.operationExecuted(operation);
		}
	}

	/**
	 * Aborts the current composite operation.
	 */
	public void abortCompositeOperation() {
		undoLastOperation();
		operationRecorder.abortCompositeOperation();
	}

	/**
	 * Complete the current composite operation.
	 */
	public void endCompositeOperation() {
		notifyOperationExecuted(operationRecorder.getCompositeOperation());
		operationRecorder.endCompositeOperation();
	}

	/**
	 * Replace and complete the current composite operation.
	 * 
	 * @param semanticCompositeOperation
	 *            the semantic operation that replaces the composite operation
	 */
	public void endCompositeOperation(SemanticCompositeOperation semanticCompositeOperation) {
		List<AbstractOperation> operations = projectSpace.getOperations();
		operations.remove(operations.size() - 1);
		operations.add(semanticCompositeOperation);
		endCompositeOperation();
	}

	/**
	 * Opens up a handle for creating a composite operation.
	 * 
	 * @return the handle for the composite operation
	 */
	public CompositeOperationHandle beginCompositeOperation() {
		return operationRecorder.beginCompositeOperation();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.client.model.impl.OperationRecorderListener#operationsRecorded(java.util.List)
	 */
	public void operationsRecorded(List<? extends AbstractOperation> operations) {
		projectSpace.addOperations(operations);
	}

	/**
	 * Clears all recorded operations.
	 */
	public void clearOperations() {
		operationRecorder.clearOperations();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.IDisposable#dispose()
	 */
	public void dispose() {
		operationRecorder.removeOperationRecorderListener(this);
	}

	/**
	 * Sets an optional operation modificator.
	 * 
	 * @param modificator
	 *            the operation modificator to be used
	 */
	public void setOperationModificator(OperationModificator modificator) {
		operationRecorder.setModificator(modificator);
	}

	/**
	 * Returns the operation modificator, if any.
	 * 
	 * @return the operation modificator
	 */
	public OperationModificator getOperationModificator() {
		return operationRecorder.getModificator();
	}
}
