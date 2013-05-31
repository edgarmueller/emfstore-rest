/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * wesendon
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.ui.dialogs.merge.ui.components;

import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.DecisionManager;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.ConflictOption;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Interface for adding custom merge widgets in the merge dialog.
 * 
 * @author wesendon
 */
public interface DetailsPart {

	/**
	 * Allows to add a custom merge widget.
	 * 
	 * @param manager
	 *            current {@link DecisionManager}
	 * @param option
	 *            the current {@link ConflictOption}
	 * @param parent
	 *            parent component to which the widget is added
	 * @return control
	 */
	Control initialize(DecisionManager manager, ConflictOption option, Composite parent);

}
