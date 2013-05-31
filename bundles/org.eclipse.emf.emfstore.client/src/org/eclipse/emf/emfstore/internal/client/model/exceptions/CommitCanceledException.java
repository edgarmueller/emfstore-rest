/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * AdrianStaudt
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.exceptions;

import org.eclipse.emf.emfstore.server.exceptions.ESException;

/**
 * If the preparation phase of the commit fails, this exception will be thrown. This is usually the case if the user
 * cancels the commit dialog.
 * 
 * @author Adrian Staudt
 */
@SuppressWarnings("serial")
public class CommitCanceledException extends ESException {

	/**
	 * Constructor.
	 * 
	 * @param message reason why this exception will be thrown
	 */
	public CommitCanceledException(String message) {
		super(message);
	}

}
