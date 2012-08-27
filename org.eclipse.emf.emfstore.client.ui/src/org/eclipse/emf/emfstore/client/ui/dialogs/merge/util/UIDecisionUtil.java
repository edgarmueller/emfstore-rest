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
package org.eclipse.emf.emfstore.client.ui.dialogs.merge.util;

import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.DecisionManager;
import org.eclipse.emf.emfstore.client.ui.Activator;
import org.eclipse.emf.emfstore.client.ui.views.changes.ChangePackageVisualizationHelper;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * Class offering common methods for the merge dialog.
 * 
 * @author wesendon
 */
public final class UIDecisionUtil {

	private UIDecisionUtil() {
	}

	private static FontRegistry fontRegistry;

	/**
	 * Fetches image by path.
	 * 
	 * @param path
	 *            path
	 * @return image
	 */
	public static Image getImage(String path) {
		return getImageDescriptor(path).createImage();
	}

	/**
	 * Fetches image descriptor by path.
	 * 
	 * @param path
	 *            path
	 * @return {@link ImageDescriptor}
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		final String key = path;
		ImageDescriptor regImage = JFaceResources.getImageRegistry().getDescriptor(key);
		if (regImage == null) {
			regImage = Activator.getImageDescriptor("icons/merge/" + path);
			JFaceResources.getImageRegistry().put(key, regImage);
		}
		return regImage;
	}

	/**
	 * Cuts a text to certain length and adds "..." at the end if needed.
	 * 
	 * @param str
	 *            text
	 * @param length
	 *            length
	 * @param addPoints
	 *            true, if ending dotts
	 * @return shortened string
	 */
	public static String cutString(String str, int length, boolean addPoints) {
		if (str == null) {
			return "";
		}
		if (str.length() > length) {
			str = str.substring(0, length);
			if (addPoints) {
				str += "...";
			}
			return str;
		} else {
			return str;
		}
	}

	/**
	 * Strips line breaking characters from text.
	 * 
	 * @param text
	 *            text
	 * @return linf of text
	 */
	public static String stripNewLine(String text) {
		if (text == null) {
			return "";
		}
		return text.replaceAll("\n\r|\r\n|\n \r|\r \n|\n|\r", " ");
	}

	/**
	 * Returns FontRegistry.
	 * 
	 * @return fonts
	 */
	public static synchronized FontRegistry getFontRegistry() {
		if (fontRegistry == null) {
			fontRegistry = new FontRegistry(Display.getCurrent());
			UIDecisionConfig.initFonts(fontRegistry);
		}
		return fontRegistry;
	}

	// TODO BRANCH
	/**
	 * Returns the visualizationhelper.
	 * 
	 * @param decisionManager instance of the decisionManager
	 * 
	 * @return visualizationhelper
	 */
	public static ChangePackageVisualizationHelper getChangePackageVisualizationHelper(DecisionManager decisionManager) {
		// ArrayList<ChangePackage> list = new ArrayList<ChangePackage>();
		// list.addAll(decisionManager.Internal.getMyChangePackages());
		// list.addAll(decisionManager.Internal.getTheirChangePackages());
		return new ChangePackageVisualizationHelper(decisionManager.getProject());
	}

	/**
	 * Returns a label provider.
	 * 
	 * @return label provider
	 */
	public static AdapterFactoryLabelProvider getAdapterFactory() {
		return new AdapterFactoryLabelProvider(new ComposedAdapterFactory(
			ComposedAdapterFactory.Descriptor.Registry.INSTANCE));
	}
}
