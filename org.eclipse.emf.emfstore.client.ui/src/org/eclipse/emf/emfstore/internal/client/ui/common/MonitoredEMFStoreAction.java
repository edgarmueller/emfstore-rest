/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.ui.common;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.emfstore.internal.client.model.util.WorkspaceUtil;
import org.eclipse.emf.emfstore.internal.server.exceptions.EMFStoreException;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressService;

/**
 * A monitored action will be executed using the {@link IProgressService} of
 * Eclipse. Clients may use the passed {@link IProgressMonitor} to update the
 * status of their progress.
 * 
 * @author emueller
 * 
 * @param <T>
 *            the return type of the action
 */
public abstract class MonitoredEMFStoreAction<T> {

	private final boolean cancelable;
	private T returnValue;
	private final boolean fork;

	/**
	 * Constructor.
	 * 
	 * @param fork
	 *            whether the {@link IProgressService} should fork the request
	 * @param cancelable
	 *            whether the request is cancelable
	 */
	public MonitoredEMFStoreAction(boolean fork, boolean cancelable) {
		this.fork = fork;
		this.cancelable = cancelable;
	}

	/**
	 * Executes the request using the {@link IProgressService} of Eclipse.
	 * 
	 * @return the return value as determined by {@link #doRun(IProgressMonitor)}
	 * 
	 */
	public final T execute() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		IProgressService progressService = workbench.getProgressService();
		try {
			if (!preRun()) {
				return null;
			}
			progressService.run(fork, cancelable, new IRunnableWithProgress() {
				public void run(final IProgressMonitor monitor) {
					try {
						returnValue = doRun(monitor);
					} catch (EMFStoreException e) {
						handleException(e);
					}
				}
			});
		} catch (InvocationTargetException e) {
			WorkspaceUtil.logException("Error during execution of an EMFStore UI controller: " + e.getMessage(), e);
		} catch (InterruptedException e) {
			WorkspaceUtil.logException("Error during execution of an EMFStore UI controller: " + e.getMessage(), e);
		}

		return returnValue;
	}

	/**
	 * Executes the request creating a {@link SubProgressMonitor}.
	 * 
	 * @param monitor the currently used {@link IProgressMonitor}
	 * @return the return value as determined by {@link #doRun(IProgressMonitor)}
	 */
	public final T executeSub(IProgressMonitor monitor) {

		if (!preRun()) {
			return null;
		}
		try {
			returnValue = doRun(new SubProgressMonitor(monitor, 5, SubProgressMonitor.PREPEND_MAIN_LABEL_TO_SUBTASK));//
		} catch (EMFStoreException e) {
			handleException(e);
		}

		return returnValue;
	}

	/**
	 * Called right before {@link #doRun(IProgressMonitor)} is called. This
	 * method will not be executed via the {@link IProgressService} and is
	 * intended to be overridden by clients to initialize data that needs user
	 * involvement via UI calls. Client should not execute long-lasting
	 * operations via this method.
	 * 
	 * @return true, if execution may continue, false, if requirements for
	 *         executing {@link #doRun(IProgressMonitor)} are not met
	 */
	public boolean preRun() {
		// default is true
		return true;
	}

	/**
	 * Generic exception handling method that is called in case {@link #doRun(IProgressMonitor)} throws an
	 * {@link EMFStoreException}.<br/>
	 * Clients may override this method if they want to treat all {@link EMFStoreException} equally. Otherwise they are
	 * obliged to handle {@link EMFStoreException} in {@link #doRun(IProgressMonitor)}, if
	 * possible.
	 * 
	 * @param e
	 *            the exception that has been thrown
	 */
	protected abstract void handleException(EMFStoreException e);

	/**
	 * The actual behavior that should be performed when the {@link #execute()} is called.<br/>
	 * Must be implemented by clients.
	 * 
	 * @param monitor
	 *            the {@link IProgressMonitor} that should be used by clients to
	 *            update the status of their progress
	 * @return an optional return value
	 * 
	 * @throws EMFStoreException
	 *             in case an error occurs
	 */
	public abstract T doRun(IProgressMonitor monitor) throws EMFStoreException;

	/**
	 * Whether this action runs in its own thread.
	 * 
	 * @return true, if this action has been forked to run in its own thread,
	 *         false otherwise
	 */
	public boolean isForked() {
		return fork;
	}
}