/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Maximilian Koegel
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.internal.client.model.CompositeOperationHandle;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.commands.CommandObserver;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.notification.recording.NotificationRecorder;
import org.eclipse.emf.emfstore.internal.client.model.observers.OperationObserver;
import org.eclipse.emf.emfstore.internal.common.IDisposable;
import org.eclipse.emf.emfstore.internal.common.extensionpoint.ExtensionPoint;
import org.eclipse.emf.emfstore.internal.common.model.IdEObjectCollection;
import org.eclipse.emf.emfstore.internal.common.model.util.IdEObjectCollectionChangeObserver;
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
public class OperationManager implements OperationRecorderListener, IDisposable, CommandObserver,
	IdEObjectCollectionChangeObserver {

	private OperationRecorder operationRecorder;
	private List<OperationObserver> operationListeners;
	private ProjectSpace projectSpace;

	/**
	 * Constructor.
	 * 
	 * @param projectSpace
	 *            the project space the operation manager should be attached to
	 */
	public OperationManager(ProjectSpaceBase projectSpace) {
		operationRecorder = new OperationRecorder(projectSpace, projectSpace.getProject().getChangeNotifier());
		operationRecorder.addOperationRecorderListener(this);
		operationListeners = new ArrayList<OperationObserver>();
		configureOperationRecorder();
		this.projectSpace = operationRecorder.getProjectSpace();
	}

	private void configureOperationRecorder() {
		// cut off incoming cross-references by default
		operationRecorder.getConfig().setCutOffIncomingCrossReferences(
			new ExtensionPoint("org.eclipse.emf.emfstore.internal.client.recording.options")
				.getBoolean("cutOffIncomingCrossReferences", true));
		// usage of commands is not forced by default
		operationRecorder.getConfig().setForceCommands(
			new ExtensionPoint("org.eclipse.emf.emfstore.internal.client.recording.options")
				.getBoolean("forceCommands", false));
		// cut elements are added automatically as regular model elements by default
		operationRecorder.getConfig().setDenyAddCutElementsToModelElements(
			new ExtensionPoint("org.eclipse.emf.emfstore.internal.client.recording.options")
				.getBoolean("denyAddCutElementsToModelElements", false));
		operationRecorder.getConfig().setOperationModificator(
			new ExtensionPoint("org.eclipse.emf.emfstore.internal.client.recording.options").getClass(
				"operationModificator", OperationModificator.class));
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
	 * @see org.eclipse.emf.emfstore.internal.client.model.impl.OperationRecorderListener#operationsRecorded(java.util.List)
	 */
	public void operationsRecorded(List<? extends AbstractOperation> operations) {
		projectSpace.addOperations(operations);
	}

	/**
	 * Clears all recorded operations.
	 * 
	 * @return the cleared operations
	 */
	public List<AbstractOperation> clearOperations() {
		return operationRecorder.clearOperations();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.IDisposable#dispose()
	 */
	public void dispose() {
		operationRecorder.removeOperationRecorderListener(this);
	}

	/**
	 * Returns the notification recorder.
	 * 
	 * @return the notification recorder
	 */
	public NotificationRecorder getNotificationRecorder() {
		return operationRecorder.getNotificationRecorder();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.changeTracking.commands.CommandObserver#commandStarted(org.eclipse.emf.common.command.Command)
	 */
	public void commandStarted(Command command) {
		operationRecorder.commandStarted(command);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.changeTracking.commands.CommandObserver#commandCompleted(org.eclipse.emf.common.command.Command)
	 */
	public void commandCompleted(Command command) {
		operationRecorder.commandCompleted(command);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.changeTracking.commands.CommandObserver#commandFailed(org.eclipse.emf.common.command.Command,
	 *      java.lang.Exception)
	 */
	public void commandFailed(Command command, Exception exception) {
		operationRecorder.commandFailed(command, exception);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.model.util.internal.common.model.util.IdEObjectCollectionChangeObserver#notify(org.eclipse.emf.common.notify.Notification,
	 *      org.eclipse.emf.emfstore.internal.common.model.internal.common.model.IdEObjectCollection, org.eclipse.emf.ecore.EObject)
	 */
	public void notify(Notification notification, IdEObjectCollection collection, EObject modelElement) {
		operationRecorder.notify(notification, collection, modelElement);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.model.util.internal.common.model.util.IdEObjectCollectionChangeObserver#modelElementAdded(org.eclipse.emf.emfstore.internal.common.model.internal.common.model.IdEObjectCollection,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	public void modelElementAdded(IdEObjectCollection collection, EObject modelElement) {
		operationRecorder.modelElementAdded(collection, modelElement);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.model.util.internal.common.model.util.IdEObjectCollectionChangeObserver#modelElementRemoved(org.eclipse.emf.emfstore.internal.common.model.internal.common.model.IdEObjectCollection,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	public void modelElementRemoved(IdEObjectCollection collection, EObject modelElement) {
		operationRecorder.modelElementRemoved(collection, modelElement);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.model.util.internal.common.model.util.IdEObjectCollectionChangeObserver#collectionDeleted(org.eclipse.emf.emfstore.internal.common.model.internal.common.model.IdEObjectCollection)
	 */
	public void collectionDeleted(IdEObjectCollection collection) {
		operationRecorder.collectionDeleted(collection);
	}

	/**
	 * Starts change recording.
	 */
	public void startChangeRecording() {
		operationRecorder.startChangeRecording();
	}

	/**
	 * Stops change recording.
	 */
	public void stopChangeRecording() {
		operationRecorder.stopChangeRecording();
	}

	/**
	 * Returns the configuration options for the operation recorder.
	 * 
	 * @return the operation recorder configuration options
	 */
	public OperationRecorderConfig getRecorderConfig() {
		return operationRecorder.getConfig();
	}
}