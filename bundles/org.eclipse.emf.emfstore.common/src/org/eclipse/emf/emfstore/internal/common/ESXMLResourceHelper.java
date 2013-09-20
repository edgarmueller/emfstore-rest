/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.common;

import java.util.Locale;

import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.xmi.impl.XMIHelperImpl;

/**
 * Specialized XML Helper that formats float and doubles across
 * different JVM versions alike.
 * 
 * 
 * @author emueller
 * 
 */
public class ESXMLResourceHelper extends XMIHelperImpl {

	/**
	 * Constructor.
	 * 
	 * @param resource
	 *            the EMFStore resource for which to create the helper
	 */
	public ESXMLResourceHelper(EMFStoreResource resource) {
		super(resource);
	}

	@Override
	public String convertToString(EFactory factory, EDataType dataType, Object value)
	{
		final int classifierID = dataType.getClassifierID();
		if (isFloat(classifierID)) {
			return format((Float) value);
		} else if (isDouble(classifierID)) {
			return format((Double) value);
		}

		return super.convertToString(factory, dataType, value);
	}

	private static boolean isFloat(int classifierID) {
		return classifierID == EcorePackage.EFLOAT
			|| classifierID == EcorePackage.EFLOAT_OBJECT;
	}

	private static boolean isDouble(int classifierID) {
		return classifierID == EcorePackage.EDOUBLE
			|| classifierID == EcorePackage.EDOUBLE_OBJECT;
	}

	private String format(Float value) {
		return String.format(Locale.US, "%f", value.floatValue());
	}

	private String format(Double value) {
		return String.format(Locale.US, "%f", value.doubleValue());
	}
}
