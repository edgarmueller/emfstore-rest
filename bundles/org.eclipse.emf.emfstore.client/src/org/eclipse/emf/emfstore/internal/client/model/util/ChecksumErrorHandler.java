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
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.handler.ESChecksumErrorHandler;
import org.eclipse.emf.emfstore.internal.client.common.UnknownEMFStoreWorkloadCommand;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESLocalProjectImpl;
import org.eclipse.emf.emfstore.internal.common.model.Project;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.model.impl.api.ESGlobalProjectIdImpl;
import org.eclipse.emf.emfstore.internal.server.model.impl.api.ESSessionIdImpl;
import org.eclipse.emf.emfstore.internal.server.model.impl.api.versionspec.ESVersionSpecImpl;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersionSpec;
import org.eclipse.emf.emfstore.server.exceptions.ESException;
import org.eclipse.emf.emfstore.server.model.versionspec.ESPrimaryVersionSpec;

/**
 * Pre-defined error handlers.
 * 
 * @author emueller
 * 
 */
public enum ChecksumErrorHandler implements ESChecksumErrorHandler {

	/**
	 * Logs the checksum comparison failure and continues execution of the caller.
	 */
	LOG {
		/**
		 * {@inheritDoc}
		 */
		public boolean execute(ESLocalProject project, ESPrimaryVersionSpec versionSpec, IProgressMonitor monitor)
			throws ESException {
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
		public boolean execute(ESLocalProject project, ESPrimaryVersionSpec versionSpec, IProgressMonitor monitor)
			throws ESException {
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
		public boolean execute(final ESLocalProject project, final ESPrimaryVersionSpec versionSpec,
			IProgressMonitor monitor) throws ESException {

			// TODO: OTS casts
			ProjectSpace projectSpace = ((ESLocalProjectImpl) project).toInternalAPI();

			Project fetchedProject = new UnknownEMFStoreWorkloadCommand<Project>(monitor) {
				@Override
				public Project run(IProgressMonitor monitor) throws ESException {

					ESSessionIdImpl sessionIdImpl = (ESSessionIdImpl) project.getUsersession().getSessionId();
					ESGlobalProjectIdImpl globalProjectIdImpl = (ESGlobalProjectIdImpl) project.getRemoteProject()
						.getGlobalProjectId();
					ESVersionSpecImpl<?, ? extends VersionSpec> versionSpecImpl = ((ESVersionSpecImpl<?, ?>) versionSpec);

					return ESWorkspaceProviderImpl
						.getInstance()
						.getConnectionManager()
						.getProject(
							sessionIdImpl.toInternalAPI(),
							globalProjectIdImpl.toInternalAPI(),
							ModelUtil.clone(versionSpecImpl.toInternalAPI()));
				}
			}.execute();

			if (fetchedProject == null) {
				throw new ESException("Server returned a null project!");
			}

			projectSpace.setProject(fetchedProject);
			projectSpace.init();

			return true;
		}
	}
}
