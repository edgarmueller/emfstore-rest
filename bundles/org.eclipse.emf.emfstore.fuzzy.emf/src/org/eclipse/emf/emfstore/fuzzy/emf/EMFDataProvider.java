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
 ******************************************************************************/
package org.eclipse.emf.emfstore.fuzzy.emf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.dom4j.DocumentException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.emfstore.fuzzy.FuzzyDataProvider;
import org.eclipse.emf.emfstore.fuzzy.Test;
import org.eclipse.emf.emfstore.fuzzy.Util;
import org.eclipse.emf.emfstore.fuzzy.emf.config.ConfigFactory;
import org.eclipse.emf.emfstore.fuzzy.emf.config.ConfigPackage;
import org.eclipse.emf.emfstore.fuzzy.emf.config.DiffReport;
import org.eclipse.emf.emfstore.fuzzy.emf.config.MutatorConfig;
import org.eclipse.emf.emfstore.fuzzy.emf.config.TestConfig;
import org.eclipse.emf.emfstore.fuzzy.emf.config.TestDiff;
import org.eclipse.emf.emfstore.fuzzy.emf.config.TestResult;
import org.eclipse.emf.emfstore.fuzzy.emf.config.TestRun;
import org.eclipse.emf.emfstore.fuzzy.emf.diff.HudsonTestRunProvider;
import org.eclipse.emf.emfstore.internal.modelmutator.api.ModelMutator;
import org.eclipse.emf.emfstore.internal.modelmutator.api.ModelMutatorConfiguration;
import org.junit.runner.notification.RunListener;
import org.junit.runners.model.TestClass;

/**
 * This implementation of a {@link FuzzyDataProvider} provides generated models
 * using the functionality of {@link ModelMutator}. <br>
 * <br>
 * The run of a test is based on a {@link TestConfig}, defining model etc. <br>
 * <br>
 * During the run it records {@link TestResult}s to create a {@link TestRun} for
 * reporting purpose.
 * 
 * @author Julian Sommerfeldt
 * 
 */
public class EMFDataProvider implements FuzzyDataProvider<EObject> {

	private Random random;

	private int count;

	private int seedCount;

	private TestClass testClass;

	private TestRun testRun;

	private TestConfig config;

	private boolean filterTests;

	private String configFile;

	private long nextSeed;

	private EClass rootEClass;

	private ModelMutatorConfiguration modelMutatorConfig;

	private Resource diffResource;

	/**
	 * Prefix of the properties concerning the {@link EMFDataProvider}.
	 */
	public static final String PROP_EMFDATAPROVIDER = ".emfdataprovider";

	/**
	 * Property specifying the path to the config file for the {@link EMFDataProvider}.
	 */
	public static final String PROP_CONFIGS_FILE = ".configsFile";

	/**
	 * Options constant for the exception log set for the mutator. Has to be
	 * filled with a <code>Set</code> of <code>RuntimeException</code>.
	 */
	public static final String MUTATOR_EXC_LOG = "mutatorExcLog";

	/**
	 * Options constant for the {@link EditingDomain} for the {@link ModelMutator}.
	 */
	public static final String MUTATOR_EDITINGDOMAIN = "mutatorEditingDomain";

	/**
	 * Init the {@link EMFDataProvider}.
	 */
	public void init() {
		// fill properties like the config file
		fillProperties();

		// load testconfig from file
		Resource loadResource = FuzzyUtil.createResource(configFile);
		try {
			loadResource.load(null);
		} catch (IOException e) {
			throw new RuntimeException("Could not load " + configFile, e);
		}

		// get the testconfig fitting to the current testclass
		config = FuzzyUtil.getTestConfig(loadResource, testClass);

		// add the config to the configs file
		addToConfigFile();

		// init variables
		random = new Random(config.getSeed());
		count = config.getCount();
		seedCount = 0;

		// read variables out of mutatorConfig and write into modelmutatorConfig
		MutatorConfig mutatorConfig = config.getMutatorConfig();
		rootEClass = mutatorConfig.getRootEClass();
		if (rootEClass == null) {
			rootEClass = ConfigPackage.Literals.ROOT;
		}
		modelMutatorConfig = new ModelMutatorConfiguration();
		modelMutatorConfig.setMinObjectsCount(mutatorConfig
			.getMinObjectsCount());
		modelMutatorConfig.setDoNotGenerateRoot(mutatorConfig
			.isDoNotGenerateRoot());
		modelMutatorConfig.seteClassesToIgnore(mutatorConfig
			.getEClassesToIgnore());
		modelMutatorConfig.seteStructuralFeaturesToIgnore(mutatorConfig
			.getEStructuralFeaturesToIgnore());
		modelMutatorConfig.setIgnoreAndLog(mutatorConfig.isIgnoreAndLog());
		modelMutatorConfig.setUseEcoreUtilDelete(mutatorConfig
			.isUseEcoreUtilDelete());
		modelMutatorConfig.setMaxDeleteCount(mutatorConfig.getMaxDeleteCount());
		modelMutatorConfig.setModelPackages(mutatorConfig.getEPackages());

		// create new TestRun with config
		testRun = ConfigFactory.eINSTANCE.createTestRun();
		testRun.setConfig(config);
		testRun.setTime(new Date());
	}

	/**
	 * Add the config to the file containing all configs.
	 */
	private void addToConfigFile() {
		Resource resource = FuzzyUtil.createResource(FuzzyUtil.ROOT_FOLDER
			+ FuzzyUtil.TEST_CONFIG_FILE);
		try {
			if (FuzzyUtil.resourceExists(resource)) {
				resource.load(null);
			}
			if (!FuzzyUtil.containsConfig(resource, config)) {
				resource.getContents().add(config);
				resource.save(null);
			}
		} catch (IOException e) {
			throw new RuntimeException("Could not save config!", e);
		}
	}

	/**
	 * See {@link FuzzyDataProvider}.
	 * 
	 * @param count
	 *            Which run is it?
	 * @return The new {@link EObject}.
	 */
	public EObject get(int count) {
		seedCount++;

		// adjust the seed
		fitSeed(count);

		// generate the model
		nextSeed = random.nextLong();

		// get the root eclass for the generation
		EObject root = EcoreUtil.create(rootEClass);

		// generate the model
		modelMutatorConfig.reset();
		modelMutatorConfig.setRootEObject(root);
		modelMutatorConfig.setSeed(nextSeed);
		ModelMutator.generateModel(modelMutatorConfig);

		return root;
	}

	private void fitSeed(int count) {
		if (count == seedCount) {
			return;
		} else if (count < seedCount) {
			random = new Random(config.getSeed());
			seedCount = 0;
		}

		while (seedCount < count) {
			random.nextLong();
			seedCount++;
		}
	}

	/**
	 * Call finish as last action of the {@link EMFDataProvider}. Used for
	 * saving the results.
	 */
	public void finish() {
		// create run resource
		Resource runResource = FuzzyUtil
			.createResource(FuzzyUtil.ROOT_FOLDER + FuzzyUtil.RUN_FOLDER
				+ config.getId() + FuzzyUtil.FILE_SUFFIX);
		runResource.getContents().add(testRun);

		try {
			runResource.save(null);
		} catch (IOException e) {
			throw new RuntimeException(
				"Could not save the run result after running!", e);
		}
	}

	/**
	 * @return How much objects will be created?
	 */
	public int size() {
		return count;
	}

	/**
	 * @param testClass
	 *            The {@link TestClass} of this run.
	 */
	public void setTestClass(TestClass testClass) {
		this.testClass = testClass;
	}

	/**
	 * @return The {@link RunListener} of this {@link EMFDataProvider}.
	 */
	public List<RunListener> getListener() {
		return Arrays.asList(new RunListener[] { new EMFRunListener(this,
			testRun) });
	}

	/**
	 * @return all {@link Test}s to run or null if all tests should run.
	 */
	public List<Test> getTestsToRun() {
		if (!filterTests) {
			return null;
		}

		// first time load diffResource
		if (diffResource == null) {
			try {
				diffResource = HudsonTestRunProvider.getDiffResource();
				diffResource.load(null);
			} catch (IOException e) {
				throw new RuntimeException("Could not load diff file!", e);
			} catch (DocumentException e) {
				throw new RuntimeException("Could not load diff file!", e);
			}
		}

		// filter for correct config
		EList<EObject> contents = diffResource.getContents();
		List<Test> tests = new ArrayList<Test>();
		for (EObject obj : contents) {
			if (obj instanceof DiffReport) {
				for (TestDiff diff : ((DiffReport) obj).getDiffs()) {
					if (diff.getConfig().getId().equals(config.getId())) {
						TestResult result = FuzzyUtil.getValidTestResult(diff);
						tests.add(new Test(result.getTestName(), result
							.getSeedCount()));
					}
				}
			}
		}

		return tests;
	}

	/**
	 * @return The current seed used to create the model
	 */
	public int getCurrentSeedCount() {
		return seedCount;
	}

	/**
	 * @return The current seed for the {@link ModelMutator}.
	 */
	public long getSeed() {
		return nextSeed;
	}

	/**
	 * @return The {@link EPackage} of the model to generate/mutate.
	 */
	public Collection<EPackage> getEPackages() {
		return modelMutatorConfig.getModelPackages();
	}

	private void fillProperties() {
		String filterTests = System.getProperty("filterTests");
		if (filterTests == null) {
			this.filterTests = false;
		} else {
			this.filterTests = Boolean.parseBoolean(filterTests);
		}
		configFile = FuzzyUtil.getProperty(PROP_EMFDATAPROVIDER
			+ PROP_CONFIGS_FILE, FuzzyUtil.TEST_CONFIG_PATH);
	}

	/**
	 * @return The {@link MutateUtil} for this {@link EMFDataProvider}.
	 */
	public Util getUtil() {
		return new MutateUtil(this);
	}

	/**
	 * @return The config specifying this run of the {@link EMFDataProvider}.
	 */
	public TestConfig getConfig() {
		return config;
	}

	/**
	 * Set the options for the {@link EMFDataProvider}.
	 * 
	 * @param options
	 *            the options.
	 */
	@SuppressWarnings("unchecked")
	public void setOptions(Map<String, Object> options) {
		// exc log
		Object o = options.get(MUTATOR_EXC_LOG);
		if (o != null && o instanceof Set<?>) {
			this.modelMutatorConfig.setExceptionLog((Set<RuntimeException>) o);
		}

		// model mutator editing domain
		o = options.get(MUTATOR_EDITINGDOMAIN);
		if (o != null && o instanceof EditingDomain) {
			this.modelMutatorConfig.setEditingDomain((EditingDomain) o);
		}
	}

	/**
	 * @return The currently active {@link ModelMutatorConfiguration}.
	 */
	public ModelMutatorConfiguration getModelMutatorConfiguration() {
		return modelMutatorConfig;
	}
}
