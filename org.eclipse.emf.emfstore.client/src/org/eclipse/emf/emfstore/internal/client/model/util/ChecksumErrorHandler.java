/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.util;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.ILocalProject;
import org.eclipse.emf.emfstore.internal.client.common.UnknownEMFStoreWorkloadCommand;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.WorkspaceProvider;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.exceptions.EMFStoreException;
import org.eclipse.emf.emfstore.internal.server.model.ProjectId;
import org.eclipse.emf.emfstore.internal.server.model.SessionId;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.api.versionspec.IPrimaryVersionSpec;

/**
 * Pre-defined error handlers.
 * 
 * @author emueller
 * 
 */
public enum ChecksumErrorHandler implements IChecksumErrorHandler {

	/**
	 * Logs the checksum comparison failure and continues execution of the caller.
	 */
	LOG {
		/**
		 * {@inheritDoc}
		 */
		public boolean execute(ILocalProject project, IPrimaryVersionSpec versionSpec, IProgressMonitor monitor)
			throws EMFStoreException {
			WorkspaceUtil.logWarning("Checksum comparison failed.", null);
			return true;
		}
	},

	/**
	 * Aborts execution of the caller.
	 */
	CANCEL {
		/**
		 * {@inheritDoc}
		 */
		public boolean execute(ILocalProject project, IPrimaryVersionSpec versionSpec, IProgressMonitor monitor)
			throws EMFStoreException {
			return false;
		}
	},

	/**
	 * Fixes the checksum comparison failure by deleting the {@link ProjectSpace} that got
	 * in an inconsistent state and checking it out again.<br>
	 * <b>Note</b>: all references to the project space that will be deleted should to be taken care of.
	 */
	AUTOCORRECT {
		/**
		 * {@inheritDoc}
		 */
		public boolean execute(final ILocalProject project, final IPrimaryVersionSpec versionSpec,
			IProgressMonitor monitor) throws EMFStoreException {

			// TODO: OTS casts
			ProjectSpace projectSpace = (ProjectSpace) project;

			Project fetchedProject = new UnknownEMFStoreWorkloadCommand<Project>(monitor) {
				@Override
				public Project run(IProgressMonitor monitor) throws EMFStoreException {
					return WorkspaceProvider
						.getInstance()
						.getConnectionManager()
						.getProject((SessionId) project.getUsersession().getSessionId(),
							(ProjectId) project.getRemoteProject().getGlobalProjectId(),
							ModelUtil.clone((PrimaryVersionSpec) versionSpec));
				}
			}.execute();

			if (fetchedProject == null) {
				throw new EMFStoreException("Server returned a null project!");
			}

			projectSpace.setProject(fetchedProject);
			projectSpace.init();

			return true;
		}
	}
}
