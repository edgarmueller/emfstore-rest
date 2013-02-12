package org.eclipse.emf.emfstore.client;

import org.eclipse.emf.emfstore.internal.client.model.controller.ChangeConflict;

// TODO: OTS rename?
/**
 * Exception caused by a {@link ChangeConflict}.
 * 
 * @author mkoegel
 * 
 */
public interface IChangeConflictException {

	/**
	 * Get the causing change conflict.
	 * 
	 * @return the conflict
	 */
	IChangeConflict getChangeConflict();
}
