/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * mkoegel
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.ui.testers;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.emf.emfstore.internal.client.observers.SaveStateChangedObserver;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.ui.AbstractSourceProvider;
import org.eclipse.ui.ISources;

// import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;

/**
 * Source provider for properties of ProjectSpace.
 * 
 * @author mkoegel
 * 
 */
public class ESLocalProjectPropertySourceProvider extends AbstractSourceProvider {

	/**
	 * Name of the property defining the save state of the currently selected project space.
	 */
	public static final String CURRENT_SAVE_STATE_PROPERTY = "org.eclipse.emf.emfstore.client.ui.currentProjectSpaceSaveState";

	private SaveStateChangedObserver saveStateChangedObserver;

	private Map<String, Boolean> currentSaveStates;

	/**
	 * Default constructor.
	 */
	public ESLocalProjectPropertySourceProvider() {

		currentSaveStates = new LinkedHashMap<String, Boolean>();
		// check if workspace can init, exit otherwise
		try {
			ESWorkspaceProviderImpl.init();
			// BEGIN SUPRESS CATCH EXCEPTION
		} catch (RuntimeException exception) {
			// END SUPRESS CATCH EXCEPTION
			ModelUtil.logException(
				"ProjectSpacePropertySourceProvider init failed because workspace init failed with exception.",
				exception);
			return;
		}
		saveStateChangedObserver = new SaveStateChangedObserver() {
			public void saveStateChanged(ESLocalProject localProject, boolean hasUnsavedChangesNow) {
				Boolean newValue = new Boolean(hasUnsavedChangesNow);
				currentSaveStates.put(localProject.getLastUpdated().toString(), newValue);
				fireSourceChanged(ISources.WORKBENCH, CURRENT_SAVE_STATE_PROPERTY, newValue);
			}

		};
		ESWorkspaceProviderImpl.getObserverBus().register(saveStateChangedObserver);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.ISourceProvider#dispose()
	 */
	public void dispose() {
		if (saveStateChangedObserver != null) {
			ESWorkspaceProviderImpl.getObserverBus().unregister(saveStateChangedObserver);
		}

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.ISourceProvider#getCurrentState()
	 */
	public Map<String, Object> getCurrentState() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put(CURRENT_SAVE_STATE_PROPERTY, currentSaveStates);
		return map;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.ISourceProvider#getProvidedSourceNames()
	 */
	public String[] getProvidedSourceNames() {
		return new String[] { CURRENT_SAVE_STATE_PROPERTY };
	}

}
