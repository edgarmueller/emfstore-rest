/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.model.observer;

import java.util.List;

import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.common.IObserver;
import org.eclipse.emf.emfstore.server.model.IChangePackage;

/**
 * Callback that is called during the merge process.<br/>
 * The life-cycle of the merging process can be divided into three steps:
 * 
 * <ol>
 * <li>first all local changes are reverted</li>
 * <li>then the remote changes are applied</li>
 * <li>finally, the local changes are re-applied</li>
 * </ol>
 * 
 * The changes being applied in the 2nd and 3rd steps are filtered by means of a conflict resolver.<br/>
 * 
 * @author emueller
 */
public interface ESMergeObserver extends IObserver {

	/**
	 * Called before all local changes are reverted.
	 * 
	 * @param project
	 *            the project space upon local changes have been reverted
	 * @param changePackage
	 *            the {@link ChangePackage} containing the operations being reverted
	 */
	void preRevertMyChanges(ESLocalProject project, IChangePackage changePackage);

	/**
	 * Called after local changes have been reverted and before incoming
	 * changes are applied.
	 * 
	 * @param project
	 *            the project space upon local changes have been reverted
	 */
	void postRevertMyChanges(ESLocalProject project);

	/**
	 * Called after incoming changes have been applied upon the {@link ESLocalProject} and before
	 * our changes are re-applied.
	 * 
	 * @param project
	 *            the project space upon local changes have been reverted
	 * @param theirChangePackages
	 *            a list of change packages containing the changes that have been applied
	 *            upon the project space
	 */
	void postApplyTheirChanges(ESLocalProject project, List<IChangePackage> theirChangePackages);

	/**
	 * Called after merge result has been re-applied, i.e. after the incoming changes
	 * from other parties have been applied upon the given project space.
	 * 
	 * @param project
	 *            the project space upon which changes should be reapplied
	 * @param changePackage
	 *            the change package containing the changes to be applied upon the project space
	 */
	void postApplyMergedChanges(ESLocalProject project, IChangePackage changePackage);
}
