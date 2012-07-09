package org.eclipse.emf.emfstore.client.model.changeTracking.merging;

import org.eclipse.emf.emfstore.client.model.changeTracking.merging.DecisionManager.Conflicting;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.conflict.Conflict;

public interface ConflictHandler {

	boolean canHandle(Conflicting conflicting);

	Conflict handle(DecisionManager dm, Conflicting conflicting);
}
