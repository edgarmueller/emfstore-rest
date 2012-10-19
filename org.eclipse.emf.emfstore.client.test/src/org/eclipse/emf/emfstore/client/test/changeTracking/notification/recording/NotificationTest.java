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
package org.eclipse.emf.emfstore.client.test.changeTracking.notification.recording;

import org.eclipse.emf.emfstore.client.model.impl.ProjectSpaceImpl;
import org.eclipse.emf.emfstore.client.test.WorkspaceTest;

/**
 * Abstract super class for operation tests, contains setup.
 * 
 * @author chodnick
 * @author koegel
 */
public abstract class NotificationTest extends WorkspaceTest {

	/**
	 * @return the projectSpace
	 */
	public ProjectSpaceImpl getProjectSpaceImpl() {
		return (ProjectSpaceImpl) getProjectSpace();
	}

}
