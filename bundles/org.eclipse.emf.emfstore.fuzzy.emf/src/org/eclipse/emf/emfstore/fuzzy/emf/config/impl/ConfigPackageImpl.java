/**
 * Copyright (c) 2012 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 */
package org.eclipse.emf.emfstore.fuzzy.emf.config.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.emfstore.fuzzy.emf.config.ConfigFactory;
import org.eclipse.emf.emfstore.fuzzy.emf.config.ConfigPackage;
import org.eclipse.emf.emfstore.fuzzy.emf.config.DiffReport;
import org.eclipse.emf.emfstore.fuzzy.emf.config.MutatorConfig;
import org.eclipse.emf.emfstore.fuzzy.emf.config.Root;
import org.eclipse.emf.emfstore.fuzzy.emf.config.TestConfig;
import org.eclipse.emf.emfstore.fuzzy.emf.config.TestDiff;
import org.eclipse.emf.emfstore.fuzzy.emf.config.TestResult;
import org.eclipse.emf.emfstore.fuzzy.emf.config.TestRun;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class ConfigPackageImpl extends EPackageImpl implements ConfigPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass testConfigEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass testRunEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass testResultEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass testDiffEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass diffReportEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass rootEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass mutatorConfigEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with {@link org.eclipse.emf.ecore.EPackage.Registry
	 * EPackage.Registry} by the
	 * package package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory method {@link #init init()}, which also
	 * performs initialization of the package, or returns the registered package, if one already exists. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.ConfigPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ConfigPackageImpl() {
		super(eNS_URI, ConfigFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model,
	 * and for any others upon which it depends.
	 * 
	 * <p>
	 * This method is used to initialize {@link ConfigPackage#eINSTANCE} when that field is accessed. Clients should not
	 * invoke it directly. Instead, they should simply access that field to obtain the package. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ConfigPackage init() {
		if (isInited)
			return (ConfigPackage) EPackage.Registry.INSTANCE
				.getEPackage(ConfigPackage.eNS_URI);

		// Obtain or create and register package
		ConfigPackageImpl theConfigPackage = (ConfigPackageImpl) (EPackage.Registry.INSTANCE
			.get(eNS_URI) instanceof ConfigPackageImpl ? EPackage.Registry.INSTANCE
			.get(eNS_URI) : new ConfigPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theConfigPackage.createPackageContents();

		// Initialize created meta-data
		theConfigPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theConfigPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ConfigPackage.eNS_URI, theConfigPackage);
		return theConfigPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getTestConfig() {
		return testConfigEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTestConfig_Seed() {
		return (EAttribute) testConfigEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTestConfig_Count() {
		return (EAttribute) testConfigEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTestConfig_TestClass() {
		return (EAttribute) testConfigEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTestConfig_Id() {
		return (EAttribute) testConfigEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getTestConfig_MutatorConfig() {
		return (EReference) testConfigEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getTestRun() {
		return testRunEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getTestRun_Config() {
		return (EReference) testRunEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTestRun_Time() {
		return (EAttribute) testRunEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getTestRun_Results() {
		return (EReference) testRunEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getTestResult() {
		return testResultEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTestResult_SeedCount() {
		return (EAttribute) testResultEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTestResult_TestName() {
		return (EAttribute) testResultEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTestResult_Error() {
		return (EAttribute) testResultEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTestResult_Failure() {
		return (EAttribute) testResultEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTestResult_ExecutionTime() {
		return (EAttribute) testResultEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getTestDiff() {
		return testDiffEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTestDiff_LastUpdate() {
		return (EAttribute) testDiffEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getTestDiff_Config() {
		return (EReference) testDiffEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getTestDiff_OldResult() {
		return (EReference) testDiffEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getTestDiff_NewResult() {
		return (EReference) testDiffEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getDiffReport() {
		return diffReportEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getDiffReport_Diffs() {
		return (EReference) diffReportEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getRoot() {
		return rootEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getRoot_Elements() {
		return (EReference) rootEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getMutatorConfig() {
		return mutatorConfigEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getMutatorConfig_RootEClass() {
		return (EReference) mutatorConfigEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getMutatorConfig_MinObjectsCount() {
		return (EAttribute) mutatorConfigEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getMutatorConfig_IgnoreAndLog() {
		return (EAttribute) mutatorConfigEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getMutatorConfig_DoNotGenerateRoot() {
		return (EAttribute) mutatorConfigEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getMutatorConfig_UseEcoreUtilDelete() {
		return (EAttribute) mutatorConfigEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getMutatorConfig_EClassesToIgnore() {
		return (EReference) mutatorConfigEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getMutatorConfig_EStructuralFeaturesToIgnore() {
		return (EReference) mutatorConfigEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getMutatorConfig_EPackages() {
		return (EReference) mutatorConfigEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getMutatorConfig_MaxDeleteCount() {
		return (EAttribute) mutatorConfigEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ConfigFactory getConfigFactory() {
		return (ConfigFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package. This method is guarded to
	 * have no affect on any invocation but its first. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		testConfigEClass = createEClass(TEST_CONFIG);
		createEAttribute(testConfigEClass, TEST_CONFIG__SEED);
		createEAttribute(testConfigEClass, TEST_CONFIG__COUNT);
		createEAttribute(testConfigEClass, TEST_CONFIG__TEST_CLASS);
		createEAttribute(testConfigEClass, TEST_CONFIG__ID);
		createEReference(testConfigEClass, TEST_CONFIG__MUTATOR_CONFIG);

		testRunEClass = createEClass(TEST_RUN);
		createEReference(testRunEClass, TEST_RUN__CONFIG);
		createEAttribute(testRunEClass, TEST_RUN__TIME);
		createEReference(testRunEClass, TEST_RUN__RESULTS);

		testResultEClass = createEClass(TEST_RESULT);
		createEAttribute(testResultEClass, TEST_RESULT__SEED_COUNT);
		createEAttribute(testResultEClass, TEST_RESULT__TEST_NAME);
		createEAttribute(testResultEClass, TEST_RESULT__ERROR);
		createEAttribute(testResultEClass, TEST_RESULT__FAILURE);
		createEAttribute(testResultEClass, TEST_RESULT__EXECUTION_TIME);

		testDiffEClass = createEClass(TEST_DIFF);
		createEAttribute(testDiffEClass, TEST_DIFF__LAST_UPDATE);
		createEReference(testDiffEClass, TEST_DIFF__CONFIG);
		createEReference(testDiffEClass, TEST_DIFF__OLD_RESULT);
		createEReference(testDiffEClass, TEST_DIFF__NEW_RESULT);

		diffReportEClass = createEClass(DIFF_REPORT);
		createEReference(diffReportEClass, DIFF_REPORT__DIFFS);

		rootEClass = createEClass(ROOT);
		createEReference(rootEClass, ROOT__ELEMENTS);

		mutatorConfigEClass = createEClass(MUTATOR_CONFIG);
		createEReference(mutatorConfigEClass, MUTATOR_CONFIG__ROOT_ECLASS);
		createEAttribute(mutatorConfigEClass, MUTATOR_CONFIG__MIN_OBJECTS_COUNT);
		createEAttribute(mutatorConfigEClass, MUTATOR_CONFIG__IGNORE_AND_LOG);
		createEAttribute(mutatorConfigEClass,
			MUTATOR_CONFIG__DO_NOT_GENERATE_ROOT);
		createEAttribute(mutatorConfigEClass,
			MUTATOR_CONFIG__USE_ECORE_UTIL_DELETE);
		createEReference(mutatorConfigEClass,
			MUTATOR_CONFIG__ECLASSES_TO_IGNORE);
		createEReference(mutatorConfigEClass,
			MUTATOR_CONFIG__ESTRUCTURAL_FEATURES_TO_IGNORE);
		createEReference(mutatorConfigEClass, MUTATOR_CONFIG__EPACKAGES);
		createEAttribute(mutatorConfigEClass, MUTATOR_CONFIG__MAX_DELETE_COUNT);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model. This
	 * method is guarded to have no affect on any invocation but its first. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(testConfigEClass, TestConfig.class, "TestConfig",
			!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTestConfig_Seed(), ecorePackage.getELong(), "seed",
			null, 0, 1, TestConfig.class, !IS_TRANSIENT, !IS_VOLATILE,
			IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
			IS_ORDERED);
		initEAttribute(getTestConfig_Count(), ecorePackage.getEInt(), "count",
			null, 0, 1, TestConfig.class, !IS_TRANSIENT, !IS_VOLATILE,
			IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
			IS_ORDERED);
		EGenericType g1 = createEGenericType(ecorePackage.getEJavaClass());
		EGenericType g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		initEAttribute(getTestConfig_TestClass(), g1, "testClass", null, 0, 1,
			TestConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
			!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTestConfig_Id(), ecorePackage.getEString(), "id",
			null, 0, 1, TestConfig.class, !IS_TRANSIENT, !IS_VOLATILE,
			IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
			IS_ORDERED);
		initEReference(getTestConfig_MutatorConfig(), this.getMutatorConfig(),
			null, "mutatorConfig", null, 0, 1, TestConfig.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
			!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
			IS_ORDERED);

		initEClass(testRunEClass, TestRun.class, "TestRun", !IS_ABSTRACT,
			!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTestRun_Config(), this.getTestConfig(), null,
			"config", null, 0, 1, TestRun.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTestRun_Time(), ecorePackage.getEDate(), "time",
			null, 0, 1, TestRun.class, !IS_TRANSIENT, !IS_VOLATILE,
			IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
			IS_ORDERED);
		initEReference(getTestRun_Results(), this.getTestResult(), null,
			"results", null, 0, -1, TestRun.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(testResultEClass, TestResult.class, "TestResult",
			!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTestResult_SeedCount(), ecorePackage.getEInt(),
			"seedCount", null, 0, 1, TestResult.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);
		initEAttribute(getTestResult_TestName(), ecorePackage.getEString(),
			"testName", null, 0, 1, TestResult.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);
		initEAttribute(getTestResult_Error(), ecorePackage.getEString(),
			"error", null, 0, 1, TestResult.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);
		initEAttribute(getTestResult_Failure(), ecorePackage.getEString(),
			"failure", null, 0, 1, TestResult.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);
		initEAttribute(getTestResult_ExecutionTime(), ecorePackage.getELong(),
			"executionTime", null, 0, 1, TestResult.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);

		initEClass(testDiffEClass, TestDiff.class, "TestDiff", !IS_ABSTRACT,
			!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTestDiff_LastUpdate(), ecorePackage.getEDate(),
			"lastUpdate", null, 0, 1, TestDiff.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);
		initEReference(getTestDiff_Config(), this.getTestConfig(), null,
			"config", null, 0, 1, TestDiff.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTestDiff_OldResult(), this.getTestResult(), null,
			"oldResult", null, 0, 1, TestDiff.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTestDiff_NewResult(), this.getTestResult(), null,
			"newResult", null, 0, 1, TestDiff.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(diffReportEClass, DiffReport.class, "DiffReport",
			!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDiffReport_Diffs(), this.getTestDiff(), null,
			"diffs", null, 0, -1, DiffReport.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(rootEClass, Root.class, "Root", !IS_ABSTRACT, !IS_INTERFACE,
			IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRoot_Elements(), ecorePackage.getEObject(), null,
			"elements", null, 0, -1, Root.class, !IS_TRANSIENT,
			!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mutatorConfigEClass, MutatorConfig.class, "MutatorConfig",
			!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMutatorConfig_RootEClass(), ecorePackage.getEClass(),
			null, "rootEClass", null, 0, 1, MutatorConfig.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
			IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
			IS_ORDERED);
		initEAttribute(getMutatorConfig_MinObjectsCount(),
			ecorePackage.getEInt(), "minObjectsCount", "100", 0, 1,
			MutatorConfig.class, !IS_TRANSIENT, !IS_VOLATILE,
			IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
			IS_ORDERED);
		initEAttribute(getMutatorConfig_IgnoreAndLog(),
			ecorePackage.getEBoolean(), "ignoreAndLog", "false", 0, 1,
			MutatorConfig.class, !IS_TRANSIENT, !IS_VOLATILE,
			IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
			IS_ORDERED);
		initEAttribute(getMutatorConfig_DoNotGenerateRoot(),
			ecorePackage.getEBoolean(), "doNotGenerateRoot", "false", 0, 1,
			MutatorConfig.class, !IS_TRANSIENT, !IS_VOLATILE,
			IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
			IS_ORDERED);
		initEAttribute(getMutatorConfig_UseEcoreUtilDelete(),
			ecorePackage.getEBoolean(), "useEcoreUtilDelete", "false", 0,
			1, MutatorConfig.class, !IS_TRANSIENT, !IS_VOLATILE,
			IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
			IS_ORDERED);
		initEReference(getMutatorConfig_EClassesToIgnore(),
			ecorePackage.getEClass(), null, "eClassesToIgnore", null, 0,
			-1, MutatorConfig.class, !IS_TRANSIENT, !IS_VOLATILE,
			IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMutatorConfig_EStructuralFeaturesToIgnore(),
			ecorePackage.getEStructuralFeature(), null,
			"eStructuralFeaturesToIgnore", null, 0, -1,
			MutatorConfig.class, !IS_TRANSIENT, !IS_VOLATILE,
			IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMutatorConfig_EPackages(),
			ecorePackage.getEPackage(), null, "ePackages", null, 0, -1,
			MutatorConfig.class, !IS_TRANSIENT, !IS_VOLATILE,
			IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMutatorConfig_MaxDeleteCount(),
			ecorePackage.getEIntegerObject(), "maxDeleteCount", null, 0, 1,
			MutatorConfig.class, !IS_TRANSIENT, !IS_VOLATILE,
			IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
			IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} // ConfigPackageImpl