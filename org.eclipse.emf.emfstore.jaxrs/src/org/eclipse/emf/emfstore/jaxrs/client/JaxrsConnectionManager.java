/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Pascal - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.jaxrs.client;

import org.eclipse.emf.emfstore.internal.server.model.ProjectId;
import org.eclipse.emf.emfstore.internal.server.model.ProjectInfo;
import org.eclipse.emf.emfstore.internal.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;

/**
 * @author Pascal
 * 
 */
public class JaxrsConnectionManager {

	public ProjectInfo createEmptyProject(String name, String description, LogMessage logMessage) {
		// TODO: implement!!!

		return new ProjectInfo() {

			public void setVersion(PrimaryVersionSpec value) {
				// TODO Auto-generated method stub

			}

			public void setProjectId(ProjectId value) {
				// TODO Auto-generated method stub

			}

			public void setName(String value) {
				// TODO Auto-generated method stub

			}

			public void setDescription(String value) {
				// TODO Auto-generated method stub

			}

			public PrimaryVersionSpec getVersion() {
				// TODO Auto-generated method stub
				return null;
			}

			public ProjectId getProjectId() {
				// TODO Auto-generated method stub
				return null;
			}

			public String getName() {
				// TODO Auto-generated method stub
				return null;
			}

			public String getDescription() {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}

}
