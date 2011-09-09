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

package org.eclipse.emf.emfstore.common.model.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.emfstore.common.model.IdEObjectCollection;
import org.eclipse.emf.emfstore.common.model.ModelElementId;
import org.eclipse.emf.emfstore.common.model.NotifiableIdEObjectCollection;
import org.eclipse.emf.emfstore.common.model.util.EObjectChangeNotifier;
import org.eclipse.emf.emfstore.common.model.util.EObjectChangeObserver;
import org.eclipse.emf.emfstore.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.common.model.util.ProjectChangeObserver;

/**
 * A collection that is able of maintaining a list of
 * {@link EObjectChangeObserver}.
 * 
 * @author koegel
 * @author naughton
 * @author emueller
 */
public abstract class NotifiableIdEObjectCollectionImpl extends
		IdEObjectCollectionImpl implements NotifiableIdEObjectCollection {

	// observer related attributes
	private boolean isNotifiying;
	private List<EObjectChangeObserver> observers;
	private Set<EObjectChangeObserver> exceptionThrowingObservers;
	private Set<EObjectChangeObserver> observersToRemove;
	private Set<EObjectChangeObserver> undetachableObservers;

	private EObjectChangeNotifier changeNotifier;

	/**
	 * Constructor.
	 */
	protected NotifiableIdEObjectCollectionImpl() {
		super();
		initObservers();
	}

	/**
	 * Constructor. Adds the contents of the given {@link XMIResource} as model
	 * elements to the collection. If the {@link XMIResource} also has XMI IDs
	 * assigned to the {@link EObject}s it contains, they will be used for
	 * creating the {@link ModelElementId}s within the project, if not, the
	 * {@link ModelElementId}s will get created on the fly.
	 * 
	 * An {@link EObjectChangeNotifier} is also attached to the given
	 * {@link XMIResource}.
	 * 
	 * @param xmiResource
	 *            a {@link XMIResource}
	 * @throws IOException
	 *             if the given {@link XMIResource} could not be loaded
	 */
	public NotifiableIdEObjectCollectionImpl(XMIResource xmiResource)
			throws IOException {
		super(xmiResource);
		initObservers();
		changeNotifier = new EObjectChangeNotifier(this, xmiResource);
	}

	/**
	 * Initializes all observers.
	 */
	private void initObservers() {
		observers = new ArrayList<EObjectChangeObserver>();
		observersToRemove = new HashSet<EObjectChangeObserver>();
		exceptionThrowingObservers = new HashSet<EObjectChangeObserver>();
		undetachableObservers = new HashSet<EObjectChangeObserver>();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ProjectChangeObserver#modelElementAdded(org.eclipse.emf.emfstore.common.model.Project,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	public void modelElementAdded(final IdEObjectCollection project,
			final EObject eObject) {
		addModelElementAndChildrenToCache(eObject);

		EObjectChangeObserverNotificationCommand command = new EObjectChangeObserverNotificationCommand() {
			public void run(EObjectChangeObserver projectChangeObserver) {
				projectChangeObserver.modelElementAdded(project, eObject);
			}
		};
		notifyEObjectChangeObservers(command);
	}

	protected void notifyEObjectChangeObservers(
			EObjectChangeObserverNotificationCommand command) {
		isNotifiying = true;
		for (EObjectChangeObserver projectChangeObserver : this.observers) {
			try {
				command.run(projectChangeObserver);
				// BEGIN SUPRESS CATCH EXCEPTION
			} catch (RuntimeException ex) {
				// END SUPRESS CATCH EXCEPTION
				if (exceptionThrowingObservers.contains(projectChangeObserver)) {
					if (!undetachableObservers.contains(projectChangeObserver)) {
						observersToRemove.add(projectChangeObserver);
						ModelUtil
								.logException(
										"EObject Change Observer threw an exception again, it has been detached, UI may not update now: "
												+ projectChangeObserver
														.getClass().getName(),
										ex);
					} else {
						ModelUtil
								.logException(
										"EObject Change Observer threw an exception again, but it will not be detached."
												+ projectChangeObserver
														.getClass().getName(),
										ex);
					}
				} else {
					exceptionThrowingObservers.add(projectChangeObserver);
					ModelUtil.logWarning(
							"EObject Change Observer threw an exception: "
									+ projectChangeObserver.getClass()
											.getName(), ex);
				}
			}
		}
		isNotifiying = false;
		for (EObjectChangeObserver observer : this.observersToRemove) {
			removeEObjectChangeObserver(observer);
		}
		this.observersToRemove.clear();
	}

	@Override
	public void initCaches() {
		super.initCaches();
		if (changeNotifier == null) {
			changeNotifier = new EObjectChangeNotifier(this, this);
		}
	};

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.Project#initCaches(java.util.Map,
	 *      java.util.Map)
	 */
	@Override
	public void initCaches(Map<EObject, ModelElementId> eObjectToIdMap,
			Map<ModelElementId, EObject> idToEObjectMap) {
		super.initCaches(eObjectToIdMap, idToEObjectMap);
		changeNotifier = new EObjectChangeNotifier(this, this);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ProjectChangeObserver#notify(org.eclipse.emf.common.notify.Notification,
	 *      org.eclipse.emf.emfstore.common.model.Project,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	public void notify(final Notification notification,
			final IdEObjectCollection project, final EObject modelElement) {
		EObjectChangeObserverNotificationCommand command = new EObjectChangeObserverNotificationCommand() {
			public void run(EObjectChangeObserver projectChangeObserver) {
				projectChangeObserver.notify(notification, project,
						modelElement);
			}
		};
		notifyEObjectChangeObservers(command);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.Project#addProjectChangeObserver(org.eclipse.emf.emfstore.common.model.util.ProjectChangeObserver)
	 */
	public void addEObjectChangeObserver(
			EObjectChangeObserver eObjectChangeObserver) {
		initCaches();
		this.observers.add(eObjectChangeObserver);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.Project#removeProjectChangeObserver(org.eclipse.emf.emfstore.common.model.util.ProjectChangeObserver)
	 */
	public void removeEObjectChangeObserver(
			EObjectChangeObserver projectChangeObserver) {
		if (isNotifiying) {
			observersToRemove.add(projectChangeObserver);
			return;
		}
		this.observers.remove(projectChangeObserver);
		exceptionThrowingObservers.remove(projectChangeObserver);
		undetachableObservers.remove(projectChangeObserver);
	}

	/**
	 * Make a project change observer undetachable.
	 * 
	 * @param observer
	 *            the observer
	 */
	public void setUndetachable(ProjectChangeObserver observer) {
		undetachableObservers.add(observer);
	}

	/**
	 * Handle the removal of an element from the containment hierachy.
	 * 
	 * @param projectImpl
	 *            the project
	 * @param modelElement
	 *            the model element
	 */
	public void modelElementRemoved(final IdEObjectCollection projectImpl,
			final EObject modelElement) {
		removeModelElementAndChildrenFromCache(modelElement);
		EObjectChangeObserverNotificationCommand command = new EObjectChangeObserverNotificationCommand() {
			public void run(EObjectChangeObserver projectChangeObserver) {
				projectChangeObserver.modelElementRemoved(projectImpl,
						modelElement);
			}
		};
		notifyEObjectChangeObservers(command);
	}

	/**
	 * Copies the current project.
	 * 
	 * @return the copied project
	 */
	@Override
	public <T extends IdEObjectCollection> T copy() {

		if (this.changeNotifier != null) {
			this.changeNotifier.disableNotifications(true);
		}

		T result = super.copy();

		if (changeNotifier != null) {
			changeNotifier.disableNotifications(false);
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.common.model.util.ProjectChangeObserver#projectDeleted(org.eclipse.emf.emfstore.common.model.Project)
	 */
	public void projectDeleted(NotifiableIdEObjectCollection project) {

	}

	public EObjectChangeNotifier getChangeNotifier() {
		return changeNotifier;
	}
}
