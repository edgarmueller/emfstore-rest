/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.client.ui.testers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.observers.SaveStateChangedObserver;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.AbstractSourceProvider;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchListener;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ListSelectionDialog;

/**
 * Source provider for properties of ProjectSpace.
 * 
 * @author mkoegel
 * 
 */
public class ProjectSpacePropertySourceProvider extends AbstractSourceProvider {

	/**
	 * Name of the property defining the save state of the currently selected project space.
	 */
	public static final String CURRENT_SAVE_STATE_PROPERTY = "org.eclipse.emf.emfstore.client.ui.currentProjectSpaceSaveState";

	private SaveStateChangedObserver saveStateChangedObserver;

	private Map<String, Boolean> currentSaveStates;

	/**
	 * Default constructor.
	 */
	public ProjectSpacePropertySourceProvider() {
		currentSaveStates = new HashMap<String, Boolean>();
		saveStateChangedObserver = new SaveStateChangedObserver() {
			public void saveStateChanged(ProjectSpace projectSpace, boolean hasUnsavedChangesNow) {
				Boolean newValue = new Boolean(hasUnsavedChangesNow);
				currentSaveStates.put(projectSpace.getIdentifier(), newValue);
				fireSourceChanged(ISources.WORKBENCH, CURRENT_SAVE_STATE_PROPERTY, newValue);
			}

		};
		WorkspaceManager.getObserverBus().register(saveStateChangedObserver);
		PlatformUI.getWorkbench().addWorkbenchListener(new IWorkbenchListener() {

			public boolean preShutdown(IWorkbench workbench, boolean forced) {
				return handlePreShutdownOfWorkbench();
			}

			public void postShutdown(IWorkbench workbench) {
				// do nothing
			}
		});
	}

	private boolean handlePreShutdownOfWorkbench() {
		AdapterFactoryLabelProvider labelProvider = new AdapterFactoryLabelProvider(new ComposedAdapterFactory(
			ComposedAdapterFactory.Descriptor.Registry.INSTANCE));
		ArrayContentProvider contentProvider = new ArrayContentProvider();
		ArrayList<ProjectSpace> inputArray = new ArrayList<ProjectSpace>();
		for (ProjectSpace projectSpace : WorkspaceManager.getInstance().getCurrentWorkspace().getProjectSpaces()) {
			if (projectSpace.hasUnsavedChanges()) {
				inputArray.add(projectSpace);
			}
		}
		if (inputArray.size() < 1) {
			return true;
		}
		ListSelectionDialog listselectionDialog = new ListSelectionDialog(Display.getCurrent().getActiveShell(),
			inputArray, contentProvider, labelProvider,
			"Which of the following projects with pending changes do you want to save?");
		listselectionDialog.setTitle("Pending changes on exit");
		listselectionDialog.setHelpAvailable(false);
		int result = listselectionDialog.open();
		if (result == ListSelectionDialog.CANCEL) {
			return false;
		}
		Object[] selectedObjects = listselectionDialog.getResult();
		for (Object projectSpaceObject : selectedObjects) {
			if (projectSpaceObject instanceof ProjectSpace) {
				ProjectSpace projectSpace = (ProjectSpace) projectSpaceObject;
				projectSpace.save();
			}
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.ISourceProvider#dispose()
	 */
	public void dispose() {
		WorkspaceManager.getObserverBus().unregister(saveStateChangedObserver);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.ISourceProvider#getCurrentState()
	 */
	public Map<String, Object> getCurrentState() {
		HashMap<String, Object> map = new HashMap<String, Object>();
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