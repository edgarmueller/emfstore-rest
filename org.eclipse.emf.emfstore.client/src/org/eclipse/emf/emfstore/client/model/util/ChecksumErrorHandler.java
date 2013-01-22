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

import java.io.IOException;

import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;

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

		@Override
		public String toString() {
			return "log";
		}

		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.emfstore.client.model.util.IChecksumErrorHandler#execute(org.eclipse.emf.emfstore.client.model.ProjectSpace)
		 */
		public ProjectSpace execute(ProjectSpace projectSpace) throws EmfStoreException {
			WorkspaceUtil.logWarning("Checksum comparison failed.", null);
			return projectSpace;
		}

		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.emfstore.client.model.util.IChecksumErrorHandler#shouldContinue()
		 */
		public boolean shouldContinue() {
			return true;
		}
	},

	/**
	 * Aborts execution of the caller.
	 */
	CANCEL {

		@Override
		public String toString() {
			return "abort";
		}

		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.emfstore.client.model.util.IChecksumErrorHandler#execute(org.eclipse.emf.emfstore.client.model.ProjectSpace)
		 */
		public ProjectSpace execute(ProjectSpace projectSpace) throws EmfStoreException {
			return projectSpace;
		}

		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.emfstore.client.model.util.IChecksumErrorHandler#shouldContinue()
		 */
		public boolean shouldContinue() {
			return false;
		}
	},

	/**
	 * Fixes the checksum comparison failure by deleting the {@link ProjectSpace} that got
	 * in an inconsistent state and checking it out again.<br>
	 * <b>Note</b>: all references to the project space that will be deleted should to be taken care of.
	 */
	AUTOCORRECT {

		@Override
		public String toString() {
			return "autocorrect";
		}

		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.emfstore.client.model.util.IChecksumErrorHandler#execute(org.eclipse.emf.emfstore.client.model.ProjectSpace)
		 */
		public ProjectSpace execute(ProjectSpace projectSpace) throws EmfStoreException {
			try {
				WorkspaceManager.getInstance().getCurrentWorkspace().deleteProjectSpace(projectSpace);
			} catch (IOException e) {
				WorkspaceUtil.logException("Could not delete project space while autocorrecting checksum failure.", e);
			}

			return WorkspaceManager.getInstance().getCurrentWorkspace()
				.checkout(projectSpace.getUsersession(), projectSpace.getProjectInfo());
		}

		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.emfstore.client.model.util.IChecksumErrorHandler#shouldContinue()
		 */
		public boolean shouldContinue() {
			return false;
		}
	}
}
