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
package org.eclipse.emf.emfstore.internal.client.ui.views.historybrowserview;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.emf.emfstore.internal.server.model.versioning.HistoryInfo;
import org.eclipse.emf.emfstore.internal.server.model.versioning.LogMessage;
import org.eclipse.jface.viewers.ColumnLabelProvider;

/**
 * @author aleaum
 * 
 */
public class CommitInfoColumnLabelProvider extends ColumnLabelProvider {
	@Override
	public String getText(Object element) {
		if (element instanceof HistoryInfo) {
			HistoryInfo historyInfo = (HistoryInfo) element;
			LogMessage logMessage = null;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd, HH:mm");
			StringBuilder builder = new StringBuilder();
			if (historyInfo.getLogMessage() != null) {
				logMessage = historyInfo.getLogMessage();
			} else if (historyInfo.getChangePackage() != null && historyInfo.getChangePackage().getLogMessage() != null) {
				logMessage = historyInfo.getChangePackage().getLogMessage();
			}
			if (logMessage != null) {
				builder.append(" [");
				builder.append(logMessage.getAuthor());
				Date clientDate = logMessage.getClientDate();
				if (clientDate != null) {
					builder.append(" @ ");
					builder.append(dateFormat.format(clientDate));
				}
				builder.append("] ");
			}
			return builder.toString();

		}
		return null;
	}

	@Override
	public String getToolTipText(Object element) {
		return getText(element);
	}
}