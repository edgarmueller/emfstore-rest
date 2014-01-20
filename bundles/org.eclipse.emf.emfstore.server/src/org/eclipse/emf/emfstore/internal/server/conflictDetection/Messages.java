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
package org.eclipse.emf.emfstore.internal.server.conflictDetection;

import org.eclipse.osgi.util.NLS;

/**
 * Conflict detection related messages.
 * 
 * @author emueller
 * 
 */
public final class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.emf.emfstore.internal.server.conflictDetection.messages"; //$NON-NLS-1$
	public static String ReservationToConflictBucketCandidateMap_Illegal_Reservation_With_And_Without_Opposite;
	public static String ReservationToConflictBucketCandidateMap_Key_Is_Null;
	public static String ReservationToConflictBucketCandidateMap_SingleRefOp_Of_CreateOp_Missing;
	public static String ReservationToConflictBucketCandidateMap_Unknown_Operation;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
