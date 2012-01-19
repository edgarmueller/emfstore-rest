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
package org.eclipse.emf.emfstore.client.ui.decorators;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.emfstore.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.emf.emfstore.client.model.observers.CommitObserver;
import org.eclipse.emf.emfstore.client.model.observers.UpdateObserver;
import org.eclipse.emf.emfstore.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

/**
 * . The decorator to show version of a ProjectSpace
 * 
 * @author Helming
 */
public class VersionDecorator extends AdapterImpl implements ILightweightLabelDecorator, UpdateObserver, CommitObserver {

	private ArrayList<ILabelProviderListener> listeners = new ArrayList<ILabelProviderListener>();
	private ProjectSpace element;

	public VersionDecorator() {
		WorkspaceManager.getObserverBus().register(this, CommitObserver.class);
		WorkspaceManager.getObserverBus().register(this, UpdateObserver.class);
	}

	/**
	 * . {@inheritDoc}
	 */
	public void decorate(Object element, IDecoration decoration) {
		if (element instanceof ProjectSpace) {
			ProjectSpace projectSpace = (ProjectSpace) element;
			StringBuilder stringBuilder = new StringBuilder();
			if (projectSpace.getBaseVersion() != null) {
				stringBuilder.append("@");
				stringBuilder.append(projectSpace.getBaseVersion().getIdentifier());
			} else {
				stringBuilder.append("(Not shared)");
			}
			String string = stringBuilder.toString();
			decoration.addSuffix(string);
		}
		// //ZH Check this
		// if(this.element==null){
		// this.element=(ProjectSpace) element;
		// this.element.eAdapters().add(this);
		// }
	}

	/**
	 * . {@inheritDoc}
	 */
	public void addListener(ILabelProviderListener listener) {
		listeners.add(listener);
	}

	/**
	 * . {@inheritDoc}
	 */
	public void dispose() {
		listeners.removeAll(listeners);

	}

	/**
	 * . {@inheritDoc}
	 */
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	/**
	 * . {@inheritDoc}
	 */
	public void removeListener(ILabelProviderListener listener) {
		listeners.remove(listener);
	}

	/**
	 * . {@inheritDoc}
	 */
	public void decorationChanged() {
		LabelProviderChangedEvent event = new LabelProviderChangedEvent(this, element);
		for (ILabelProviderListener listener : listeners) {
			listener.labelProviderChanged(event);
		}
	}

	/**
	 * . {@inheritDoc}
	 */
	@Override
	public void notifyChanged(Notification msg) {
		decorationChanged();
	}

	public boolean inspectChanges(ProjectSpace projectSpace, ChangePackage changePackage) {
		return false;
	}

	public void commitCompleted(ProjectSpace projectSpace, PrimaryVersionSpec newRevision) {
		update();
	}

	public boolean inspectChanges(ProjectSpace projectSpace, List<ChangePackage> changePackages) {
		return false;
	}

	public void updateCompleted(ProjectSpace projectSpace) {
		update();
	}

	private void update() {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				PlatformUI.getWorkbench().getDecoratorManager()
					.update("org.eclipse.emf.emfstore.client.ui.decorators.VersionDecorator");
			}
		});
	}

}
