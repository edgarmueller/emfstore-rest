/*******************************************************************************
 * Copyright (c) 2008-2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.common;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.model.util.WorkspaceUtil;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;

/**
 * This class provides a way to indicate the progress of a command without knowing
 * the actual workload, i.e. the {@link IProgressMonitor} instance that is passed
 * to the run method is incremented every
 * 
 * @author emueller
 * 
 * @param <T> the return type of the command's run method
 */
public abstract class UnknownEMFStoreWorkloadCommand<T> {

	private static final int DEFAULT_POLLING_INTERVAL = 500;
	private final IProgressMonitor monitor;
	private int worked;
	private int pollingInterval;

	/**
	 * Singleton.
	 */
	private static class SingletonHolder {
		private static final ExecutorService EXECUTOR = Executors.newCachedThreadPool();
	}

	/**
	 * Constructor.
	 * 
	 * @param monitor
	 *            the monitor that will be used to indicate that the command is in progress
	 */
	public UnknownEMFStoreWorkloadCommand(IProgressMonitor monitor) {
		this.monitor = monitor;
		this.pollingInterval = DEFAULT_POLLING_INTERVAL;
		worked = 0;
	}

	/**
	 * Constructor.
	 * 
	 * @param monitor
	 *            the monitor that will be used to indicate that the command is in progress
	 * @param pollingInterval
	 *            at which rate the <code>monitor</code> should get incremented, measured in milliseconds
	 */
	public UnknownEMFStoreWorkloadCommand(IProgressMonitor monitor, int pollingInterval) {
		this.monitor = monitor;
		this.pollingInterval = pollingInterval;
		worked = 0;
	}

	/**
	 * Executes the command.
	 * 
	 * @return the return value as determined by the run method
	 * @throws EmfStoreException
	 *             in case the command throws an exception
	 */
	public T execute() throws EmfStoreException {

		Future<T> future = SingletonHolder.EXECUTOR.submit(new Callable<T>() {
			public T call() throws Exception {
				return run(monitor);
			}
		});

		T result = null;
		boolean resultReceived = false;

		while (!resultReceived) {

			try {
				result = future.get(pollingInterval, TimeUnit.MILLISECONDS);
				resultReceived = true;
			} catch (InterruptedException e) {
				WorkspaceUtil.logException(e.getMessage(), e);
				throw new EmfStoreException("Workload command got interrupted", e);
			} catch (ExecutionException e) {
				WorkspaceUtil.logException(e.getMessage(), e);
				throw new EmfStoreException(e.getCause().getMessage());
			} catch (TimeoutException e) {
				// do nothing
			}

			monitor.worked(1);
			worked++;
		}

		return result;
	}

	/**
	 * Returns how many ticks the command has incremented the monitor.
	 * 
	 * @return amount of ticks that were incremented
	 */
	public int getWorked() {
		return worked;
	}

	/**
	 * The actual behavior of the command that is meant to be implemented by clients.
	 * 
	 * @param monitor
	 *            a progress monitor that is used to indicate
	 * @return an optional value of type <code>T</code>
	 * @throws EmfStoreException
	 *             in case the command throws an exception
	 */
	public abstract T run(IProgressMonitor monitor) throws EmfStoreException;
}
