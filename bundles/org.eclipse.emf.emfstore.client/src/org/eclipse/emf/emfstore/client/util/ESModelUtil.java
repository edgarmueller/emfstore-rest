/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.util;

import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESLocalProjectImpl;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;

/**
 * @author emueller
 * 
 */
public class ESModelUtil {

	public static boolean areEqual(ESLocalProject localProject, ESLocalProject otherLocalProject) {
		final ESLocalProjectImpl cast = ESLocalProjectImpl.class.cast(localProject);
		final ESLocalProjectImpl cast2 = ESLocalProjectImpl.class.cast(otherLocalProject);
		return ModelUtil.areEqual(
			cast.toInternalAPI().getProject(),
			cast2.toInternalAPI().getProject());
	}
}
