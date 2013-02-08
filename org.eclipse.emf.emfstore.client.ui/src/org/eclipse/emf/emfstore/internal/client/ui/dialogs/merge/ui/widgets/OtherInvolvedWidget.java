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
package org.eclipse.emf.emfstore.internal.client.ui.dialogs.merge.ui.widgets;

import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.DecisionManager;
import org.eclipse.emf.emfstore.internal.client.model.changeTracking.merging.conflict.ConflictOption;
import org.eclipse.emf.emfstore.internal.client.ui.dialogs.merge.util.UIDecisionUtil;
import org.eclipse.emf.emfstore.internal.client.ui.views.changes.ChangePackageVisualizationHelper;
import org.eclipse.emf.emfstore.server.model.versioning.operations.AbstractOperation;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

/**
 * This details widget shows other involved operations using default
 * representation.
 * 
 * @author wesendon
 */
public class OtherInvolvedWidget extends Composite {

	private static final int COLUMNS = 1;
	private static final int MAX_OPS_SIZE = 20;

	/**
	 * Default constructor.
	 * 
	 * @param parent
	 *            parent
	 * @param decisionManager
	 *            decisionManager
	 * @param option
	 *            option
	 */
	public OtherInvolvedWidget(Composite parent, DecisionManager decisionManager, ConflictOption option) {
		super(parent, SWT.None);
		TableWrapLayout wrapLayout = new TableWrapLayout();
		wrapLayout.numColumns = COLUMNS;
		wrapLayout.makeColumnsEqualWidth = true;
		setLayout(wrapLayout);
		setBackground(parent.getBackground());

		Label label = new Label(this, SWT.NONE);
		label.setText("Other Involved Changes: ");
		label.setBackground(parent.getBackground());
		TableWrapData wrapData = new TableWrapData();
		wrapData.colspan = COLUMNS;
		label.setLayoutData(wrapData);

		ChangePackageVisualizationHelper visualizationHelper = UIDecisionUtil
			.getChangePackageVisualizationHelper(decisionManager);

		if (option.getOperations().size() <= MAX_OPS_SIZE) {

			for (AbstractOperation ao : option.getOperations()) {
				CLabel meLabel = new CLabel(this, SWT.WRAP);
				meLabel.setBackground(parent.getBackground());

				Image image = visualizationHelper.getImage(UIDecisionUtil.getAdapterFactory(), ao);

				if (image != null) {
					meLabel.setImage(image);
				}
				meLabel.setText(visualizationHelper.getDescription(ao));
			}
		} else {
			CLabel meLabel = new CLabel(this, SWT.WRAP);
			meLabel.setBackground(parent.getBackground());
			meLabel.setText("More than " + MAX_OPS_SIZE + " other operations...");
		}

		visualizationHelper.dispose();
	}
}