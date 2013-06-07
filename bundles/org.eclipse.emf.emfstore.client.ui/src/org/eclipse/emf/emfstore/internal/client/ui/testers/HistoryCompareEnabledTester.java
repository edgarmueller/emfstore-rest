/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Julian Sommerfeldt
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.ui.testers;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.emf.emfstore.internal.client.ui.views.historybrowserview.HistoryCompare;

/**
 * Test if there is a history compare extension registered.
 * 
 * @author jsommerfeldt
 * 
 */
public class HistoryCompareEnabledTester extends PropertyTester {

	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		return HistoryCompare.hasRegisteredExtensions();
	}
}
