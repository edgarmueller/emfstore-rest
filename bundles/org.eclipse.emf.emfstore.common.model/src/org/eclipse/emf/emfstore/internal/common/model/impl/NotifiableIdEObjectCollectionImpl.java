/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * naughton
 * emueller
 * koegel
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.common.model.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.emfstore.common.ESSafeRunnable;
import org.eclipse.emf.emfstore.common.ESSafeRunner;
import org.eclipse.emf.emfstore.internal.common.model.IdEObjectCollection;
import org.eclipse.emf.emfstore.internal.common.model.NotifiableIdEObjectCollection;
import org.eclipse.emf.emfstore.internal.common.model.util.EObjectChangeNotifier;
import org.eclipse.emf.emfstore.internal.common.model.util.IdEObjectCollectionChangeObserver;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;

/**
 * A collection that is able of maintaining a list of {@link IdEObjectCollectionChangeObserver}.
 * 
 * @author koegel
 * @author naughton
 * @author emueller
 */
public abstract class NotifiableIdEObjectCollectionImpl extends IdEObjectCollectionImpl implements
	NotifiableIdEObjectCollection {

	// observer related attributes
	private boolean isNotifiying;
	private List<IdEObjectCollectionChangeObserver> observers;
	private Set<IdEObjectCollectionChangeObserver> exceptionThrowingObservers;
	private Set<IdEObjectCollectionChangeObserver> observersToRemove;
	private Set<IdEObjectCollectionChangeObserver> undetachableObservers;
	private Set<IdEObjectCollectionChangeObserver> observersToAttach;

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
	 * creating the model element IDs within the project, if not, the model element IDs will get created on
	 * the fly.
	 * 
	 * An {@link EObjectChangeNotifier} is also attached to the given {@link XMIResource}.
	 * 
	 * @param xmiResource
	 *            a {@link XMIResource}
	 * @throws IOException
	 *             if the given {@link XMIResource} could not be loaded
	 */
	public NotifiableIdEObjectCollectionImpl(XMIResource xmiResource) throws IOException {
		super(xmiResource);
		initObservers();
		changeNotifier = new EObjectChangeNotifier(this, xmiResource);
	}

	/**
	 * Initializes all observers.
	 */
	private void initObservers() {
		observers = new ArrayList<IdEObjectCollectionChangeObserver>();
		observersToRemove = new LinkedHashSet<IdEObjectCollectionChangeObserver>();
		exceptionThrowingObservers = new LinkedHashSet<IdEObjectCollectionChangeObserver>();
		undetachableObservers = new LinkedHashSet<IdEObjectCollectionChangeObserver>();
		observersToAttach = new LinkedHashSet<IdEObjectCollectionChangeObserver>();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.model.NotifiableIdEObjectCollection#modelElementAdded(org.eclipse.emf.emfstore.internal.common.model.IdEObjectCollection,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	public void modelElementAdded(final IdEObjectCollection collection, final EObject eObject) {
		addToResource(eObject);
		addModelElementAndChildrenToCache(eObject);
		final EObjectChangeObserverNotificationCommand command = new EObjectChangeObserverNotificationCommand() {
			public void run(IdEObjectCollectionChangeObserver projectChangeObserver) {
				projectChangeObserver.modelElementAdded(collection, eObject);
			}
		};
		notifyIdEObjectCollectionChangeObservers(command);
	}

	private void addToResource(EObject eObject) {
		final Resource resource = eObject.eResource();
		if (resource != null && resource != eResource()) {
			EcoreUtil.remove(eObject);
		}
	}

	/**
	 * Add a new cut element.
	 * 
	 * @param eObject The new cut element.
	 */
	public abstract void addCutElement(EObject eObject);

	/**
	 * Notifies all collection change observers.
	 * 
	 * @param command
	 *            the notification command
	 */
	protected synchronized void notifyIdEObjectCollectionChangeObservers(
		final EObjectChangeObserverNotificationCommand command) {
		isNotifiying = true;
		for (final IdEObjectCollectionChangeObserver changeObserver : observers) {

			final ESSafeRunnable code = new ESSafeRunnable() {

				public void run() {
					command.run(changeObserver);
				}

				public void handleException(Throwable exception) {
					if (exceptionThrowingObservers.contains(changeObserver)) {
						if (!undetachableObservers.contains(changeObserver)) {
							observersToRemove.add(changeObserver);
							ModelUtil.logException(
								"EObject Change Observer threw an exception again, it has been detached, UI may not update now: "
									+ changeObserver.getClass().getName(), exception);
						} else {
							ModelUtil.logException(
								"EObject Change Observer threw an exception again, but it will not be detached."
									+ changeObserver.getClass().getName(), exception);
						}
					} else {
						exceptionThrowingObservers.add(changeObserver);
						ModelUtil.logWarning("EObject Change Observer threw an exception: "
							+ changeObserver.getClass().getName(), exception);
					}
				}
			};

			ESSafeRunner.run(code);
		}
		isNotifiying = false;
		for (final IdEObjectCollectionChangeObserver observer : observersToRemove) {
			removeIdEObjectCollectionChangeObserver(observer);
		}

		for (final IdEObjectCollectionChangeObserver observer : observersToAttach) {
			addIdEObjectCollectionChangeObserver(observer);
		}

		observersToRemove.clear();
	}

	@Override
	public void initMapping() {
		super.initMapping();
		if (changeNotifier == null) {
			changeNotifier = new EObjectChangeNotifier(this, this);
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.model.impl.IdEObjectCollectionImpl#initMapping(java.util.Map,
	 *      java.util.Map)
	 */
	@Override
	public void initMapping(Map<EObject, String> eObjectToIdMap, Map<String, EObject> idToEObjectMap) {
		super.initMapping(eObjectToIdMap, idToEObjectMap);
		if (changeNotifier == null) {
			changeNotifier = new EObjectChangeNotifier(this, this);
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.model.util.IdEObjectCollectionChangeObserver#notify(org.eclipse.emf.common.notify.Notification,
	 *      org.eclipse.emf.emfstore.internal.common.model.IdEObjectCollection, org.eclipse.emf.ecore.EObject)
	 */
	public void notify(final Notification notification, final IdEObjectCollection project, final EObject modelElement) {
		final EObjectChangeObserverNotificationCommand command = new EObjectChangeObserverNotificationCommand() {
			public void run(IdEObjectCollectionChangeObserver projectChangeObserver) {
				projectChangeObserver.notify(notification, project, modelElement);
			}
		};

		final Resource resource = modelElement.eResource();

		if (resource != null && resource instanceof ResourceImpl) {
			final ResourceImpl resourceImpl = (ResourceImpl) resource;
			if (resourceImpl.isLoading()) {
				return;
			}
		}

		notifyIdEObjectCollectionChangeObservers(command);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.model.NotifiableIdEObjectCollection#addIdEObjectCollectionChangeObserver(org.eclipse.emf.emfstore.internal.common.model.util.IdEObjectCollectionChangeObserver)
	 */
	public synchronized void addIdEObjectCollectionChangeObserver(
		IdEObjectCollectionChangeObserver eObjectChangeObserver) {
		initMapping();

		if (isNotifiying) {
			observersToAttach.add(eObjectChangeObserver);
			return;
		}

		observers.add(eObjectChangeObserver);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.model.NotifiableIdEObjectCollection#removeIdEObjectCollectionChangeObserver(org.eclipse.emf.emfstore.internal.common.model.util.IdEObjectCollectionChangeObserver)
	 */
	public synchronized void removeIdEObjectCollectionChangeObserver(
		IdEObjectCollectionChangeObserver projectChangeObserver) {
		if (isNotifiying) {
			observersToRemove.add(projectChangeObserver);
			return;
		}
		observers.remove(projectChangeObserver);
		exceptionThrowingObservers.remove(projectChangeObserver);
		undetachableObservers.remove(projectChangeObserver);
	}

	/**
	 * Make a project change observer undetachable.
	 * 
	 * @param observer
	 *            the observer
	 */
	public void setUndetachable(IdEObjectCollectionChangeObserver observer) {
		undetachableObservers.add(observer);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.model.NotifiableIdEObjectCollection#modelElementRemoved(org.eclipse.emf.emfstore.internal.common.model.IdEObjectCollection,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	public void modelElementRemoved(final IdEObjectCollection projectImpl, final EObject modelElement) {
		removeModelElementAndChildrenFromCache(modelElement);
		final EObjectChangeObserverNotificationCommand command = new EObjectChangeObserverNotificationCommand() {
			public void run(IdEObjectCollectionChangeObserver projectChangeObserver) {
				projectChangeObserver.modelElementRemoved(projectImpl, modelElement);
			}
		};
		notifyIdEObjectCollectionChangeObservers(command);
	}

	/**
	 * Copies the current collection.
	 * 
	 * @param <T>
	 *            the actual collection type to be copied
	 * 
	 * @return the copied collection
	 */
	@Override
	public <T extends IdEObjectCollection> T copy() {

		if (changeNotifier != null) {
			changeNotifier.disableNotifications(true);
		}

		final T result = super.copy();

		if (changeNotifier != null) {
			changeNotifier.disableNotifications(false);
		}

		return result;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.model.util.IdEObjectCollectionChangeObserver#collectionDeleted(org.eclipse.emf.emfstore.internal.common.model.IdEObjectCollection)
	 */
	public void collectionDeleted(IdEObjectCollection collection) {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.common.model.NotifiableIdEObjectCollection#getChangeNotifier()
	 */
	public EObjectChangeNotifier getChangeNotifier() {
		return changeNotifier;
	}
}
