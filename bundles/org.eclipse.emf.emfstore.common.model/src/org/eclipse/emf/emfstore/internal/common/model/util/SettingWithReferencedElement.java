/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * EdgarMueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.common.model.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;

/**
 * Represents a Setting (EObject and Feature) and its referenced element.
 * 
 * @author Edgar Mueller
 */
public class SettingWithReferencedElement {

	private Setting setting;
	private EObject referencedElement;

	/**
	 * Constructor.
	 * 
	 * @param setting the setting
	 * @param referencedElement the referenced element
	 */
	public SettingWithReferencedElement(Setting setting, EObject referencedElement) {
		this.referencedElement = referencedElement;
		this.setting = setting;
	}

	/**
	 * Get the referenced element.
	 * 
	 * @return the element
	 */
	public EObject getReferencedElement() {
		return referencedElement;
	}

	/**
	 * Get the setting.
	 * 
	 * @return the setting
	 */
	public Setting getSetting() {
		return setting;
	}
}
