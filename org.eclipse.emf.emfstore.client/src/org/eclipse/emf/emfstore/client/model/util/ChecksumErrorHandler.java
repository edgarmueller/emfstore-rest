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
package org.eclipse.emf.emfstore.client.model.util;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.emfstore.client.common.UnknownEMFStoreWorkloadCommand;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.common.model.Project;
import org.eclipse.emf.emfstore.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;

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
		public boolean execute(ProjectSpace projectSpace, PrimaryVersionSpec versionSpec, IProgressMonitor monitor)
			throws EmfStoreException {
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
		public boolean execute(ProjectSpace projectSpace, PrimaryVersionSpec versionSpec, IProgressMonitor monitor)
			throws EmfStoreException {
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
		public boolean execute(final ProjectSpace projectSpace, final PrimaryVersionSpec versionSpec,
			IProgressMonitor monitor) throws EmfStoreException {

			Project project = new UnknownEMFStoreWorkloadCommand<Project>(monitor) {
				@Override
				public Project run(IProgressMonitor monitor) throws EmfStoreException {
					return WorkspaceManager.getInstance().getConnectionManager()
						.getProject(projectSpace.getUsersession().getSessionId(),
							projectSpace.getProjectInfo().getProjectId(),
							ModelUtil.clone(versionSpec));
					// projectInfoCopy.getVersion());
				}
			}.execute();

			if (project == null) {
				throw new EmfStoreException("Server returned a null project!");
			}

			projectSpace.setProject(project);
			projectSpace.init();
			return true;
		}
	}
}
