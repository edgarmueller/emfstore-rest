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
package org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.conflicts;

import org.eclipse.osgi.util.NLS;

/**
 * Reference operations related messages.
 * 
 * @author emueller
 * 
 */
public final class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.conflicts.messages"; //$NON-NLS-1$
	public static String MultiReference_Move_To;
	public static String MultiReferenceSetConflict_Remove;
	public static String MultiReferenceSetConflict_Set;
	public static String SingleReferenceConflict_Unset;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
