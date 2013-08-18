/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.server.conflictDetection;

import org.eclipse.emf.emfstore.internal.common.model.ModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;

/**
 * Interface for types that want to modify the {@link ReservationSet} that is
 * created during conflict detection.
 * 
 * @author emueller
 * 
 */
public interface ReservationSetModifier {

	/**
	 * ID of the {@link ReservationSetModifier} option.
	 */
	String ID = "org.eclipse.emf.emfstore.server.conflictDetection.reservationSetModifiier";

	/**
	 * Allows to modify the given {@link ReservationSet}.
	 * 
	 * @param operation
	 *            the current operation in scope
	 * @param reservationSet
	 *            the existing {@link ReservationSet}
	 * @param mapping
	 *            a mapping of EObjects to ModelElementIds and vice versa containing
	 *            all involved model elements
	 * @return the possibly modified reservation set
	 */
	ReservationSet addCustomReservation(AbstractOperation operation,
		ReservationSet reservationSet, ModelElementIdToEObjectMapping mapping);
}
