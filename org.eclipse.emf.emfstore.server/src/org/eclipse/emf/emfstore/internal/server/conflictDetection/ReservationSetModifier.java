package org.eclipse.emf.emfstore.internal.server.conflictDetection;

import org.eclipse.emf.emfstore.internal.common.model.ModelElementIdToEObjectMapping;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;

public interface ReservationSetModifier {

	ReservationSet addCustomReservation(AbstractOperation operation,
		ReservationSet reservationSet, ModelElementIdToEObjectMapping mapping);
}
