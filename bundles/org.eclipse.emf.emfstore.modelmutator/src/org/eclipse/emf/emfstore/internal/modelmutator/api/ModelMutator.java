/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * StephanK?hler
 * EugenNeufeld
 * PhilipAchenbach
 * DmitryLitvinov
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.modelmutator.api;

import org.eclipse.emf.emfstore.internal.modelmutator.intern.AbstractModelMutator;

/**
 * Implementaion of AbstractModelMutator with empty preMutate and postMutate methods.
 * 
 * TODO Merge with AbstractModelMutator?
 * 
 * @author Eugen Neufeld
 * @author Stephan K?hler
 * @author Philip Achenbach
 * @author Dmitry Litvinov
 */
public class ModelMutator extends AbstractModelMutator {

	/**
	 * Generates a model as specified in the config.
	 * 
	 * @param config the configuration
	 */
	public static void generateModel(ModelMutatorConfiguration config) {
		ModelMutator modelMutator = new ModelMutator(config);
		modelMutator.generate();
	}

	/**
	 * Modifies a model as specified in the config.
	 * 
	 * @param config the configuration
	 */
	public static void changeModel(ModelMutatorConfiguration config) {
		ModelMutator modelMutator = new ModelMutator(config);
		modelMutator.mutate();
	}

	/**
	 * The constructor.
	 * 
	 * @param config
	 *            the configuration used in the process
	 */
	public ModelMutator(ModelMutatorConfiguration config) {
		super(config);
	}

	@Override
	public void preMutate() {
	}

	@Override
	public void postMutate() {
	}

	@Override
	public void mutate() {
		super.mutate();
	}
}
