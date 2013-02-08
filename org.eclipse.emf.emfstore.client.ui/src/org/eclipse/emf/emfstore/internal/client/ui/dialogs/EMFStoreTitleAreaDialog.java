/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.ui.dialogs;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * Common dialog class that extends {@link TitleAreaDialog} and takes care of setting the height
 * and the position of a dialog.
 * 
 * @author emueller
 * 
 */
public class EMFStoreTitleAreaDialog extends TitleAreaDialog {

	/**
	 * Instantiate a new EMFStore title area dialog.
	 * 
	 * @param parentShell
	 *            the parent SWT shell
	 */
	public EMFStoreTitleAreaDialog(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		int width = screenSize.width / 3;
		int height = screenSize.height / 2;
		newShell.setSize(width, height);
		newShell.setLocation((screenSize.width - width) / 2, (int) ((screenSize.height - height * 1.25) / 2));
	}
}
