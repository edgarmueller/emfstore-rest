/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.util;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.ConflictOption;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.ConflictOption.OptionType;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.VisualConflict;

/**
 * Class offering common methods for the merge dialog.
 * 
 * @author wesendon
 */
// TODO: BRANCH some of this stuff is UI related and isn't supposed to be defined here.
public final class DecisionUtil {

	private static final String SPACE = " "; //$NON-NLS-1$

	private static final String TO_BE_REPLACED = "\n\r|\r\n|\n \r|\r \n|\n|\r"; //$NON-NLS-1$

	private static final String ENDING_QUOTE = "\""; //$NON-NLS-1$

	private static final String OPENING_QUOTE = " \""; //$NON-NLS-1$

	private static final String BRANCHMERGE = "branchmerge"; //$NON-NLS-1$

	private static final String DOTS = "..."; //$NON-NLS-1$

	private DecisionUtil() {
		// for checkstyle, my love
	}

	/**
	 * Length of option label.
	 */
	public static final int OPTION_LENGTH = 45;

	/**
	 * Separator symbol for detail provider.
	 */
	public static final String SEPERATOR = "#"; //$NON-NLS-1$

	/**
	 * Editable detail provider.
	 */
	public static final String EDITABLE = "editable"; //$NON-NLS-1$

	/**
	 * Multiline widget detail provider.
	 */
	public static final String WIDGET_MULTILINE = "org.eclipse.emf.emfstore.client.ui.merge.widget.multiline"; //$NON-NLS-1$

	/**
	 * Multiline editable widget detail provider.
	 */
	public static final String WIDGET_MULTILINE_EDITABLE = WIDGET_MULTILINE + SEPERATOR + EDITABLE;

	/**
	 * Cuts a text to certain length and adds "..." at the end if needed.
	 * 
	 * @param str
	 *            text
	 * @param length
	 *            length
	 * @param addPoints
	 *            true, if ending dots
	 * @return shortened string
	 */
	public static String cutString(String str, int length, boolean addPoints) {

		if (str == null) {
			return StringUtils.EMPTY;
		}

		if (str.length() > length) {
			str = str.substring(0, length);
			if (addPoints) {
				str += DOTS;
			}
			return str;
		}

		return str;
	}

	/**
	 * Strips line breaking characters from text.
	 * 
	 * @param text
	 *            text
	 * @return line of text
	 */
	public static String stripNewLine(String text) {
		if (text == null) {
			return StringUtils.EMPTY;
		}
		return text.replaceAll(TO_BE_REPLACED, SPACE);
	}

	/**
	 * Get Option by is type.
	 * 
	 * @param options
	 *            list of options
	 * @param type
	 *            type
	 * @return resulting option or null
	 */
	public static ConflictOption getConflictOptionByType(List<ConflictOption> options, OptionType type) {
		for (final ConflictOption option : options) {
			if (option.getType().equals(type)) {
				return option;
			}
		}
		return null;
	}

	/**
	 * Checks whether a conflict needs details.
	 * 
	 * @param conflict
	 *            conflict
	 * @return true, if so
	 */
	public static boolean detailsNeeded(VisualConflict conflict) {
		if (!conflict.hasDetails()) {
			return false;
		}
		for (final ConflictOption option : conflict.getOptions()) {
			if (!option.isDetailsProvider()) {
				continue;
			}
			if (option.getDetailProvider().startsWith(DecisionUtil.WIDGET_MULTILINE)) {
				if (option.getOptionLabel().length() > DecisionUtil.OPTION_LENGTH) {
					return true;
				}
			}
		}
		return false;
	}

	private static DescriptionProvider descriptionProvider;

	/**
	 * Returns conflict descriptions on basis of the {@link DescriptionProvider} .
	 * 
	 * @param key
	 *            key
	 * @param isBranchMerge
	 *            if true, a prefix will be added to the key in order to get
	 *            branch wording
	 * @return description
	 */
	public static String getDescription(String key, boolean isBranchMerge) {
		if (descriptionProvider == null) {
			descriptionProvider = new DescriptionProvider();
		}
		descriptionProvider.setPrefix(isBranchMerge ? BRANCHMERGE : null);
		return descriptionProvider.getDescription(key);
	}

	/**
	 * Uses the object's toString method or returns unset string.
	 * 
	 * @param obj
	 *            obj to string
	 * @param unset
	 *            unset string
	 * @return obj.toString or unset
	 */
	public static String getLabel(Object obj, String unset) {
		return obj != null && obj.toString().length() > 0 ? obj.toString() : unset;
	}

	/**
	 * Returns Class and Name of {@link EObject}.
	 * 
	 * @param modelElement
	 *            modelelement
	 * @return string
	 */
	public static String getClassAndName(EObject modelElement) {
		if (modelElement == null) {
			return StringUtils.EMPTY;
		}
		return modelElement.eClass().getName() + OPENING_QUOTE + getModelElementName(modelElement) + ENDING_QUOTE;
	}

	/**
	 * Returns name for an element by using {@link MergeLabelProvider}.
	 * 
	 * @param modelElement
	 *            specified element
	 * @return name for element;
	 */
	public static String getModelElementName(EObject modelElement) {

		if (modelElement == null) {
			return StringUtils.EMPTY;
		}

		final MergeLabelProvider labelProvider = ESWorkspaceProviderImpl.getObserverBus().notify(
			MergeLabelProvider.class,
			true);
		if (labelProvider == null) {
			return modelElement.toString();
		}
		return labelProvider.getText(modelElement);
	}
}
