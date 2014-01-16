/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.test.common.cases;

import java.util.Map;

import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.internal.server.model.ProjectHistory;

/**
 * Server interface meant to be used within tests.
 * 
 * @author emueller
 * 
 */
public interface IServer {

	void startEMFStore(Map<String, String> properties);

	void startEMFStore();

	void stopEMFStore();

	ProjectHistory getHistory(ESLocalProject localProject);

}
