/*******************************************************************************
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * JulianSommerfeldt
 * StephanK?hler
 * EugenNeufeld
 * PhilipAchenbach
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.modelmutator.api;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;

/**
 * Configuration for the ModelMutator.
 * 
 * @author Eugen Neufeld
 * @author Stephan K?hler
 * @author Philip Achenbach
 * @author Julian Sommerfeldt
 */
public class ModelMutatorConfiguration {

	private Collection<EPackage> modelPackages;
	private EObject rootEObject;
	private Random random;

	private Long seed;

	private int minObjectsCount;
	private Integer maxDeleteCount;

	private boolean ignoreAndLog;
	private Collection<EClass> eClassesToIgnore;
	private Collection<EStructuralFeature> eStructuralFeaturesToIgnore;
	private Set<RuntimeException> exceptionLog;

	private boolean doNotGenerateRoot;
	private boolean useEcoreUtilDelete;

	private EditingDomain editingDomain;

	/**
	 * Initialize variables with null. Variables have to be set later!
	 */
	public ModelMutatorConfiguration() {
		this((EPackage) null, null, null);
	}

	/**
	 * The constructor for the configuration.
	 * 
	 * @param modelPackage the EPackages
	 * @param rootEObject the rootObject for the generation/change
	 * @param seed the seed for the generation/change
	 */
	public ModelMutatorConfiguration(EPackage modelPackage, EObject rootEObject, Long seed) {
		this(Collections.singleton(modelPackage), rootEObject, seed);
	}

	/**
	 * The constructor for the configuration.
	 * 
	 * @param modelPackages the EPackages
	 * @param rootEObject the rootObject for the generation/change
	 * @param seed the seed for the generation/change
	 */
	public ModelMutatorConfiguration(Collection<EPackage> modelPackages, EObject rootEObject, Long seed) {
		this.modelPackages = modelPackages;
		this.rootEObject = rootEObject;
		this.seed = seed;

		this.eClassesToIgnore = new LinkedHashSet<EClass>();
		this.eStructuralFeaturesToIgnore = new LinkedHashSet<EStructuralFeature>();
		this.exceptionLog = new LinkedHashSet<RuntimeException>();
		this.ignoreAndLog = false;

		minObjectsCount = 100;

		useEcoreUtilDelete = false;
	}

	/**
	 * Reset the {@link ModelMutatorConfiguration}. Means that it has the same state after the first initialization.
	 */
	public void reset() {
		random = null;
		editingDomain = null;
	}

	/**
	 * @param modelPackages the EPackages
	 */
	public void setModelPackages(Collection<EPackage> modelPackages) {
		this.modelPackages = modelPackages;
	}

	/**
	 * @param rootEObject the rootObject for the generation/change
	 */
	public void setRootEObject(EObject rootEObject) {
		this.rootEObject = rootEObject;
	}

	/**
	 * @param seed The seed for the random.
	 */
	public void setSeed(Long seed) {
		this.seed = seed;
	}

	/**
	 * @return The minimum number of objects to generate.
	 */
	public int getMinObjectsCount() {
		return minObjectsCount;
	}

	/**
	 * @param minObjectsCount The minimum number of objects to generate.
	 */
	public void setMinObjectsCount(int minObjectsCount) {
		this.minObjectsCount = minObjectsCount;
	}

	/**
	 * @return the ignoreAndLog
	 */
	public boolean isIgnoreAndLog() {
		return ignoreAndLog;
	}

	/**
	 * @param ignoreAndLog
	 *            the ignoreAndLog to set
	 */
	public void setIgnoreAndLog(boolean ignoreAndLog) {
		this.ignoreAndLog = ignoreAndLog;
	}

	/**
	 * @return the eClassesToIgnore
	 */
	public Collection<EClass> geteClassesToIgnore() {
		return eClassesToIgnore;
	}

	/**
	 * @param eClassesToIgnore
	 *            the eClassesToIgnore to set
	 */
	public void seteClassesToIgnore(Collection<EClass> eClassesToIgnore) {
		this.eClassesToIgnore = eClassesToIgnore;
	}

	/**
	 * @return the modelPackage
	 */
	public Collection<EPackage> getModelPackages() {
		return modelPackages;
	}

	/**
	 * @return the rootEObject
	 */
	public EObject getRootEObject() {
		return rootEObject;
	}

	/**
	 * @return the exceptionLog
	 */
	public Set<RuntimeException> getExceptionLog() {
		return exceptionLog;
	}

	/**
	 * @param exceptionLog
	 *            the exceptionLog to set
	 */
	public void setExceptionLog(Set<RuntimeException> exceptionLog) {
		this.exceptionLog = exceptionLog;
	}

	/**
	 * @return the random
	 */
	public Random getRandom() {
		if (random == null) {
			this.random = new Random(seed);
		}
		return random;
	}

	/**
	 * @return the eStructuralFeaturesToIgnore
	 */
	public Collection<EStructuralFeature> geteStructuralFeaturesToIgnore() {
		return eStructuralFeaturesToIgnore;
	}

	/**
	 * @param eStructuralFeaturesToIgnore
	 *            the eStructuralFeaturesToIgnore to set
	 */
	public void seteStructuralFeaturesToIgnore(Collection<EStructuralFeature> eStructuralFeaturesToIgnore) {
		this.eStructuralFeaturesToIgnore = eStructuralFeaturesToIgnore;
	}

	/**
	 * @return the doNotGenerateRoot
	 */
	public boolean isDoNotGenerateRoot() {
		return doNotGenerateRoot;
	}

	/**
	 * @param doNotGenerateRoot
	 *            the doNotGenerateRoot to set
	 */
	public void setDoNotGenerateRoot(boolean doNotGenerateRoot) {
		this.doNotGenerateRoot = doNotGenerateRoot;
	}

	/**
	 * @return The {@link EditingDomain} specified in the config.
	 */
	public EditingDomain getEditingDomain() {
		if (editingDomain == null) {
			editingDomain = new AdapterFactoryEditingDomain(new ComposedAdapterFactory(
				ComposedAdapterFactory.Descriptor.Registry.INSTANCE), new BasicCommandStack());
		}
		return editingDomain;
	}

	/**
	 * @param editingDomain The {@link EditingDomain} to use by commands.
	 */
	public void setEditingDomain(EditingDomain editingDomain) {
		this.editingDomain = editingDomain;
	}

	/**
	 * @return Should the Mutator use {@link org.eclipse.emf.ecore.util.EcoreUtil#delete(EObject)}?
	 */
	public boolean isUseEcoreUtilDelete() {
		return useEcoreUtilDelete;
	}

	/**
	 * Should the Mutator use {@link org.eclipse.emf.ecore.util.EcoreUtil#delete(EObject)}?<br>
	 * NOTE: This is a very expensive method and will decrease the performance dramatically.
	 * 
	 * @param useEcoreUtilDelete Should the Mutator use {@link org.eclipse.emf.ecore.util.EcoreUtil#delete(EObject)}?
	 */
	public void setUseEcoreUtilDelete(boolean useEcoreUtilDelete) {
		this.useEcoreUtilDelete = useEcoreUtilDelete;
	}

	/**
	 * @return How many objects should the mutation process delete maximal?
	 */
	public int getMaxDeleteCount() {
		return maxDeleteCount != null ? maxDeleteCount : minObjectsCount;
	}

	/**
	 * @param maxDeleteCount How many objects should the mutation process delete maximal?
	 */
	public void setMaxDeleteCount(Integer maxDeleteCount) {
		this.maxDeleteCount = maxDeleteCount;
	}

}
