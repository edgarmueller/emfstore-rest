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
package org.eclipse.emf.emfstore.client.test.api;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.callbacks.ESCommitCallback;
import org.eclipse.emf.emfstore.client.callbacks.ESUpdateCallback;

/**
 * @author emueller
 * 
 */
public final class ClientTestUtil {

	private static IProgressMonitor nullProgressMonitor = new NullProgressMonitor();

	private ClientTestUtil() {

	}

	public static String noLogMessage() {
		return StringUtils.EMPTY;
	}

	public static IProgressMonitor noProgressMonitor() {
		return nullProgressMonitor;
	}

	public static ESCommitCallback noCommitCallback() {
		return ESCommitCallback.NOCALLBACK;
	}

	public static ESUpdateCallback noUpdateCallback() {
		return ESUpdateCallback.NOCALLBACK;
	}
}
