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
package org.eclipse.emf.emfstore.internal.client.model.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.AbstractOverrideableCommand;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.AbstractOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.CompositeOperation;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.OperationsFactory;

/**
 * Very basic implementation of {@link OperationModificator}. All operations are merged into one composite operation.
 * 
 * @author wesendon
 */
public class AutoOperationWrapper implements OperationModificator {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.emfstore.internal.client.model.impl.OperationModificator#modify(java.util.List,
	 *      org.eclipse.emf.common.command.Command)
	 */
	public List<AbstractOperation> modify(List<AbstractOperation> operations, Command command) {

		if (operations.size() < 1) {
			return operations;
		} else if (operations.size() == 1 && command instanceof AbstractOverrideableCommand) {
			return operations;
		} else if (operations.size() == 1 && operations.get(0) instanceof CompositeOperation) {
			CompositeOperation compositeOperation = (CompositeOperation) operations.get(0);
			if (compositeOperation.getMainOperation() == null) {
				return operations;
			}
		}

		CompositeOperation compositeOperation = OperationsFactory.eINSTANCE.createCompositeOperation();
		compositeOperation.setClientDate(new Date());
		compositeOperation.setCompositeName(getText(command.getLabel()));
		compositeOperation.setCompositeDescription(getText(command.getDescription()));
		compositeOperation.setModelElementId(ModelUtil.clone(operations.get(0).getModelElementId()));
		compositeOperation.getSubOperations().addAll(operations);
		ArrayList<AbstractOperation> result = new ArrayList<AbstractOperation>();
		result.add(compositeOperation);
		return result;
	}

	private String getText(String str) {
		return (str == null) ? "" : str;
	}
}