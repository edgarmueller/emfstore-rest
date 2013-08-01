/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.importexport;

/**
 * Represent a single unit that is considered valid to be exported/imported.
 */
public interface ExportImportDataUnit {

	/**
	 * Returns the file extension that is used to persist this data unit.
	 * 
	 * @return the file extension of this data unit.
	 */
	String getExtension();

	/**
	 * Returns the name of this data unit.
	 * 
	 * @return the name of this data unit.
	 */
	String getName();
}