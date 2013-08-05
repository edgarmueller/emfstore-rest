/**
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 */
package org.eclipse.emf.emfstore.fuzzy.emf.config;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains
 * accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.ConfigFactory
 * @model kind="package"
 * @generated
 */
public interface ConfigPackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "config";

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http://org/eclipse/emf/emfstore/fuzzy/emf/config";

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.emf.emfstore.fuzzy.emf.config";

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	ConfigPackage eINSTANCE = org.eclipse.emf.emfstore.fuzzy.emf.config.impl.ConfigPackageImpl
		.init();

	/**
	 * The meta object id for the ' {@link org.eclipse.emf.emfstore.fuzzy.emf.config.impl.TestConfigImpl
	 * <em>Test Config</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.impl.TestConfigImpl
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.impl.ConfigPackageImpl#getTestConfig()
	 * @generated
	 */
	int TEST_CONFIG = 0;

	/**
	 * The feature id for the '<em><b>Seed</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEST_CONFIG__SEED = 0;

	/**
	 * The feature id for the '<em><b>Count</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEST_CONFIG__COUNT = 1;

	/**
	 * The feature id for the '<em><b>Test Class</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEST_CONFIG__TEST_CLASS = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEST_CONFIG__ID = 3;

	/**
	 * The feature id for the '<em><b>Mutator Config</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEST_CONFIG__MUTATOR_CONFIG = 4;

	/**
	 * The number of structural features of the '<em>Test Config</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEST_CONFIG_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the ' {@link org.eclipse.emf.emfstore.fuzzy.emf.config.impl.TestRunImpl
	 * <em>Test Run</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.impl.TestRunImpl
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.impl.ConfigPackageImpl#getTestRun()
	 * @generated
	 */
	int TEST_RUN = 1;

	/**
	 * The feature id for the '<em><b>Config</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEST_RUN__CONFIG = 0;

	/**
	 * The feature id for the '<em><b>Time</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEST_RUN__TIME = 1;

	/**
	 * The feature id for the '<em><b>Results</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEST_RUN__RESULTS = 2;

	/**
	 * The number of structural features of the '<em>Test Run</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEST_RUN_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the ' {@link org.eclipse.emf.emfstore.fuzzy.emf.config.impl.TestResultImpl
	 * <em>Test Result</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.impl.TestResultImpl
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.impl.ConfigPackageImpl#getTestResult()
	 * @generated
	 */
	int TEST_RESULT = 2;

	/**
	 * The feature id for the '<em><b>Seed Count</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEST_RESULT__SEED_COUNT = 0;

	/**
	 * The feature id for the '<em><b>Test Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEST_RESULT__TEST_NAME = 1;

	/**
	 * The feature id for the '<em><b>Error</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEST_RESULT__ERROR = 2;

	/**
	 * The feature id for the '<em><b>Failure</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEST_RESULT__FAILURE = 3;

	/**
	 * The feature id for the '<em><b>Execution Time</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEST_RESULT__EXECUTION_TIME = 4;

	/**
	 * The number of structural features of the '<em>Test Result</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEST_RESULT_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the ' {@link org.eclipse.emf.emfstore.fuzzy.emf.config.impl.TestDiffImpl
	 * <em>Test Diff</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.impl.TestDiffImpl
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.impl.ConfigPackageImpl#getTestDiff()
	 * @generated
	 */
	int TEST_DIFF = 3;

	/**
	 * The feature id for the '<em><b>Last Update</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEST_DIFF__LAST_UPDATE = 0;

	/**
	 * The feature id for the '<em><b>Config</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEST_DIFF__CONFIG = 1;

	/**
	 * The feature id for the '<em><b>Old Result</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEST_DIFF__OLD_RESULT = 2;

	/**
	 * The feature id for the '<em><b>New Result</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEST_DIFF__NEW_RESULT = 3;

	/**
	 * The number of structural features of the '<em>Test Diff</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEST_DIFF_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the ' {@link org.eclipse.emf.emfstore.fuzzy.emf.config.impl.DiffReportImpl
	 * <em>Diff Report</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.impl.DiffReportImpl
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.impl.ConfigPackageImpl#getDiffReport()
	 * @generated
	 */
	int DIFF_REPORT = 4;

	/**
	 * The feature id for the '<em><b>Diffs</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DIFF_REPORT__DIFFS = 0;

	/**
	 * The number of structural features of the '<em>Diff Report</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DIFF_REPORT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the ' {@link org.eclipse.emf.emfstore.fuzzy.emf.config.impl.RootImpl
	 * <em>Root</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.impl.RootImpl
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.impl.ConfigPackageImpl#getRoot()
	 * @generated
	 */
	int ROOT = 5;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROOT__ELEMENTS = 0;

	/**
	 * The number of structural features of the '<em>Root</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ROOT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the ' {@link org.eclipse.emf.emfstore.fuzzy.emf.config.impl.MutatorConfigImpl
	 * <em>Mutator Config</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.impl.MutatorConfigImpl
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.impl.ConfigPackageImpl#getMutatorConfig()
	 * @generated
	 */
	int MUTATOR_CONFIG = 6;

	/**
	 * The feature id for the '<em><b>Root EClass</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MUTATOR_CONFIG__ROOT_ECLASS = 0;

	/**
	 * The feature id for the '<em><b>Min Objects Count</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MUTATOR_CONFIG__MIN_OBJECTS_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Ignore And Log</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MUTATOR_CONFIG__IGNORE_AND_LOG = 2;

	/**
	 * The feature id for the '<em><b>Do Not Generate Root</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MUTATOR_CONFIG__DO_NOT_GENERATE_ROOT = 3;

	/**
	 * The feature id for the '<em><b>Use Ecore Util Delete</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MUTATOR_CONFIG__USE_ECORE_UTIL_DELETE = 4;

	/**
	 * The feature id for the '<em><b>EClasses To Ignore</b></em>' reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MUTATOR_CONFIG__ECLASSES_TO_IGNORE = 5;

	/**
	 * The feature id for the '<em><b>EStructural Features To Ignore</b></em>'
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MUTATOR_CONFIG__ESTRUCTURAL_FEATURES_TO_IGNORE = 6;

	/**
	 * The feature id for the '<em><b>EPackages</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MUTATOR_CONFIG__EPACKAGES = 7;

	/**
	 * The feature id for the '<em><b>Max Delete Count</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MUTATOR_CONFIG__MAX_DELETE_COUNT = 8;

	/**
	 * The number of structural features of the '<em>Mutator Config</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MUTATOR_CONFIG_FEATURE_COUNT = 9;

	/**
	 * Returns the meta object for class ' {@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestConfig
	 * <em>Test Config</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Test Config</em>'.
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.TestConfig
	 * @generated
	 */
	EClass getTestConfig();

	/**
	 * Returns the meta object for the attribute ' {@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestConfig#getSeed
	 * <em>Seed</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Seed</em>'.
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.TestConfig#getSeed()
	 * @see #getTestConfig()
	 * @generated
	 */
	EAttribute getTestConfig_Seed();

	/**
	 * Returns the meta object for the attribute ' {@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestConfig#getCount
	 * <em>Count</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Count</em>'.
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.TestConfig#getCount()
	 * @see #getTestConfig()
	 * @generated
	 */
	EAttribute getTestConfig_Count();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestConfig#getTestClass
	 * <em>Test Class</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Test Class</em>'.
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.TestConfig#getTestClass()
	 * @see #getTestConfig()
	 * @generated
	 */
	EAttribute getTestConfig_TestClass();

	/**
	 * Returns the meta object for the attribute ' {@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestConfig#getId
	 * <em>Id</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.TestConfig#getId()
	 * @see #getTestConfig()
	 * @generated
	 */
	EAttribute getTestConfig_Id();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestConfig#getMutatorConfig
	 * <em>Mutator Config</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference ' <em>Mutator Config</em>'.
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.TestConfig#getMutatorConfig()
	 * @see #getTestConfig()
	 * @generated
	 */
	EReference getTestConfig_MutatorConfig();

	/**
	 * Returns the meta object for class ' {@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestRun
	 * <em>Test Run</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Test Run</em>'.
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.TestRun
	 * @generated
	 */
	EClass getTestRun();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestRun#getConfig
	 * <em>Config</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Config</em>'.
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.TestRun#getConfig()
	 * @see #getTestRun()
	 * @generated
	 */
	EReference getTestRun_Config();

	/**
	 * Returns the meta object for the attribute ' {@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestRun#getTime
	 * <em>Time</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Time</em>'.
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.TestRun#getTime()
	 * @see #getTestRun()
	 * @generated
	 */
	EAttribute getTestRun_Time();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestRun#getResults
	 * <em>Results</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list ' <em>Results</em>'.
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.TestRun#getResults()
	 * @see #getTestRun()
	 * @generated
	 */
	EReference getTestRun_Results();

	/**
	 * Returns the meta object for class ' {@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestResult
	 * <em>Test Result</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Test Result</em>'.
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.TestResult
	 * @generated
	 */
	EClass getTestResult();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestResult#getSeedCount
	 * <em>Seed Count</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Seed Count</em>'.
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.TestResult#getSeedCount()
	 * @see #getTestResult()
	 * @generated
	 */
	EAttribute getTestResult_SeedCount();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestResult#getTestName
	 * <em>Test Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Test Name</em>'.
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.TestResult#getTestName()
	 * @see #getTestResult()
	 * @generated
	 */
	EAttribute getTestResult_TestName();

	/**
	 * Returns the meta object for the attribute ' {@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestResult#getError
	 * <em>Error</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Error</em>'.
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.TestResult#getError()
	 * @see #getTestResult()
	 * @generated
	 */
	EAttribute getTestResult_Error();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestResult#getFailure
	 * <em>Failure</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Failure</em>'.
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.TestResult#getFailure()
	 * @see #getTestResult()
	 * @generated
	 */
	EAttribute getTestResult_Failure();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestResult#getExecutionTime
	 * <em>Execution Time</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Execution Time</em>'.
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.TestResult#getExecutionTime()
	 * @see #getTestResult()
	 * @generated
	 */
	EAttribute getTestResult_ExecutionTime();

	/**
	 * Returns the meta object for class ' {@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestDiff
	 * <em>Test Diff</em>} '. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Test Diff</em>'.
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.TestDiff
	 * @generated
	 */
	EClass getTestDiff();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestDiff#getLastUpdate
	 * <em>Last Update</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Last Update</em>'.
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.TestDiff#getLastUpdate()
	 * @see #getTestDiff()
	 * @generated
	 */
	EAttribute getTestDiff_LastUpdate();

	/**
	 * Returns the meta object for the reference ' {@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestDiff#getConfig
	 * <em>Config</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Config</em>'.
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.TestDiff#getConfig()
	 * @see #getTestDiff()
	 * @generated
	 */
	EReference getTestDiff_Config();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestDiff#getOldResult
	 * <em>Old Result</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference ' <em>Old Result</em>'.
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.TestDiff#getOldResult()
	 * @see #getTestDiff()
	 * @generated
	 */
	EReference getTestDiff_OldResult();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.TestDiff#getNewResult
	 * <em>New Result</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference ' <em>New Result</em>'.
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.TestDiff#getNewResult()
	 * @see #getTestDiff()
	 * @generated
	 */
	EReference getTestDiff_NewResult();

	/**
	 * Returns the meta object for class ' {@link org.eclipse.emf.emfstore.fuzzy.emf.config.DiffReport
	 * <em>Diff Report</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Diff Report</em>'.
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.DiffReport
	 * @generated
	 */
	EClass getDiffReport();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.DiffReport#getDiffs
	 * <em>Diffs</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list ' <em>Diffs</em>'.
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.DiffReport#getDiffs()
	 * @see #getDiffReport()
	 * @generated
	 */
	EReference getDiffReport_Diffs();

	/**
	 * Returns the meta object for class ' {@link org.eclipse.emf.emfstore.fuzzy.emf.config.Root <em>Root</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Root</em>'.
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.Root
	 * @generated
	 */
	EClass getRoot();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.Root#getElements
	 * <em>Elements</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list ' <em>Elements</em>'.
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.Root#getElements()
	 * @see #getRoot()
	 * @generated
	 */
	EReference getRoot_Elements();

	/**
	 * Returns the meta object for class ' {@link org.eclipse.emf.emfstore.fuzzy.emf.config.MutatorConfig
	 * <em>Mutator Config</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Mutator Config</em>'.
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.MutatorConfig
	 * @generated
	 */
	EClass getMutatorConfig();

	/**
	 * Returns the meta object for the reference '
	 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.MutatorConfig#getRootEClass
	 * <em>Root EClass</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Root EClass</em>'.
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.MutatorConfig#getRootEClass()
	 * @see #getMutatorConfig()
	 * @generated
	 */
	EReference getMutatorConfig_RootEClass();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.MutatorConfig#getMinObjectsCount
	 * <em>Min Objects Count</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for the attribute '<em>Min Objects Count</em>'.
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.MutatorConfig#getMinObjectsCount()
	 * @see #getMutatorConfig()
	 * @generated
	 */
	EAttribute getMutatorConfig_MinObjectsCount();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.MutatorConfig#isIgnoreAndLog
	 * <em>Ignore And Log</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Ignore And Log</em>'.
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.MutatorConfig#isIgnoreAndLog()
	 * @see #getMutatorConfig()
	 * @generated
	 */
	EAttribute getMutatorConfig_IgnoreAndLog();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.MutatorConfig#isDoNotGenerateRoot
	 * <em>Do Not Generate Root</em>} '. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Do Not Generate Root</em> '.
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.MutatorConfig#isDoNotGenerateRoot()
	 * @see #getMutatorConfig()
	 * @generated
	 */
	EAttribute getMutatorConfig_DoNotGenerateRoot();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.MutatorConfig#isUseEcoreUtilDelete
	 * <em>Use Ecore Util Delete</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Use Ecore Util Delete</em> '.
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.MutatorConfig#isUseEcoreUtilDelete()
	 * @see #getMutatorConfig()
	 * @generated
	 */
	EAttribute getMutatorConfig_UseEcoreUtilDelete();

	/**
	 * Returns the meta object for the reference list '
	 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.MutatorConfig#getEClassesToIgnore
	 * <em>EClasses To Ignore</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for the reference list ' <em>EClasses To Ignore</em>'.
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.MutatorConfig#getEClassesToIgnore()
	 * @see #getMutatorConfig()
	 * @generated
	 */
	EReference getMutatorConfig_EClassesToIgnore();

	/**
	 * Returns the meta object for the reference list '
	 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.MutatorConfig#getEStructuralFeaturesToIgnore
	 * <em>EStructural Features To Ignore</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the reference list ' <em>EStructural Features To Ignore</em>'.
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.MutatorConfig#getEStructuralFeaturesToIgnore()
	 * @see #getMutatorConfig()
	 * @generated
	 */
	EReference getMutatorConfig_EStructuralFeaturesToIgnore();

	/**
	 * Returns the meta object for the reference list '
	 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.MutatorConfig#getEPackages
	 * <em>EPackages</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>EPackages</em>'.
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.MutatorConfig#getEPackages()
	 * @see #getMutatorConfig()
	 * @generated
	 */
	EReference getMutatorConfig_EPackages();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.emfstore.fuzzy.emf.config.MutatorConfig#getMaxDeleteCount
	 * <em>Max Delete Count</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for the attribute '<em>Max Delete Count</em>'.
	 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.MutatorConfig#getMaxDeleteCount()
	 * @see #getMutatorConfig()
	 * @generated
	 */
	EAttribute getMutatorConfig_MaxDeleteCount();

	/**
	 * Returns the factory that creates the instances of the model. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ConfigFactory getConfigFactory();

	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that
	 * represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the ' {@link org.eclipse.emf.emfstore.fuzzy.emf.config.impl.TestConfigImpl
		 * <em>Test Config</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.impl.TestConfigImpl
		 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.impl.ConfigPackageImpl#getTestConfig()
		 * @generated
		 */
		EClass TEST_CONFIG = eINSTANCE.getTestConfig();

		/**
		 * The meta object literal for the '<em><b>Seed</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TEST_CONFIG__SEED = eINSTANCE.getTestConfig_Seed();

		/**
		 * The meta object literal for the '<em><b>Count</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TEST_CONFIG__COUNT = eINSTANCE.getTestConfig_Count();

		/**
		 * The meta object literal for the '<em><b>Test Class</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TEST_CONFIG__TEST_CLASS = eINSTANCE
			.getTestConfig_TestClass();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TEST_CONFIG__ID = eINSTANCE.getTestConfig_Id();

		/**
		 * The meta object literal for the '<em><b>Mutator Config</b></em>'
		 * containment reference feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TEST_CONFIG__MUTATOR_CONFIG = eINSTANCE
			.getTestConfig_MutatorConfig();

		/**
		 * The meta object literal for the ' {@link org.eclipse.emf.emfstore.fuzzy.emf.config.impl.TestRunImpl
		 * <em>Test Run</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.impl.TestRunImpl
		 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.impl.ConfigPackageImpl#getTestRun()
		 * @generated
		 */
		EClass TEST_RUN = eINSTANCE.getTestRun();

		/**
		 * The meta object literal for the '<em><b>Config</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TEST_RUN__CONFIG = eINSTANCE.getTestRun_Config();

		/**
		 * The meta object literal for the '<em><b>Time</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TEST_RUN__TIME = eINSTANCE.getTestRun_Time();

		/**
		 * The meta object literal for the '<em><b>Results</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TEST_RUN__RESULTS = eINSTANCE.getTestRun_Results();

		/**
		 * The meta object literal for the ' {@link org.eclipse.emf.emfstore.fuzzy.emf.config.impl.TestResultImpl
		 * <em>Test Result</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.impl.TestResultImpl
		 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.impl.ConfigPackageImpl#getTestResult()
		 * @generated
		 */
		EClass TEST_RESULT = eINSTANCE.getTestResult();

		/**
		 * The meta object literal for the '<em><b>Seed Count</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TEST_RESULT__SEED_COUNT = eINSTANCE
			.getTestResult_SeedCount();

		/**
		 * The meta object literal for the '<em><b>Test Name</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TEST_RESULT__TEST_NAME = eINSTANCE.getTestResult_TestName();

		/**
		 * The meta object literal for the '<em><b>Error</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TEST_RESULT__ERROR = eINSTANCE.getTestResult_Error();

		/**
		 * The meta object literal for the '<em><b>Failure</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TEST_RESULT__FAILURE = eINSTANCE.getTestResult_Failure();

		/**
		 * The meta object literal for the '<em><b>Execution Time</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TEST_RESULT__EXECUTION_TIME = eINSTANCE
			.getTestResult_ExecutionTime();

		/**
		 * The meta object literal for the ' {@link org.eclipse.emf.emfstore.fuzzy.emf.config.impl.TestDiffImpl
		 * <em>Test Diff</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.impl.TestDiffImpl
		 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.impl.ConfigPackageImpl#getTestDiff()
		 * @generated
		 */
		EClass TEST_DIFF = eINSTANCE.getTestDiff();

		/**
		 * The meta object literal for the '<em><b>Last Update</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TEST_DIFF__LAST_UPDATE = eINSTANCE.getTestDiff_LastUpdate();

		/**
		 * The meta object literal for the '<em><b>Config</b></em>' reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TEST_DIFF__CONFIG = eINSTANCE.getTestDiff_Config();

		/**
		 * The meta object literal for the '<em><b>Old Result</b></em>'
		 * containment reference feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TEST_DIFF__OLD_RESULT = eINSTANCE.getTestDiff_OldResult();

		/**
		 * The meta object literal for the '<em><b>New Result</b></em>'
		 * containment reference feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TEST_DIFF__NEW_RESULT = eINSTANCE.getTestDiff_NewResult();

		/**
		 * The meta object literal for the ' {@link org.eclipse.emf.emfstore.fuzzy.emf.config.impl.DiffReportImpl
		 * <em>Diff Report</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.impl.DiffReportImpl
		 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.impl.ConfigPackageImpl#getDiffReport()
		 * @generated
		 */
		EClass DIFF_REPORT = eINSTANCE.getDiffReport();

		/**
		 * The meta object literal for the '<em><b>Diffs</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference DIFF_REPORT__DIFFS = eINSTANCE.getDiffReport_Diffs();

		/**
		 * The meta object literal for the ' {@link org.eclipse.emf.emfstore.fuzzy.emf.config.impl.RootImpl
		 * <em>Root</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.impl.RootImpl
		 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.impl.ConfigPackageImpl#getRoot()
		 * @generated
		 */
		EClass ROOT = eINSTANCE.getRoot();

		/**
		 * The meta object literal for the '<em><b>Elements</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ROOT__ELEMENTS = eINSTANCE.getRoot_Elements();

		/**
		 * The meta object literal for the ' {@link org.eclipse.emf.emfstore.fuzzy.emf.config.impl.MutatorConfigImpl
		 * <em>Mutator Config</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.impl.MutatorConfigImpl
		 * @see org.eclipse.emf.emfstore.fuzzy.emf.config.impl.ConfigPackageImpl#getMutatorConfig()
		 * @generated
		 */
		EClass MUTATOR_CONFIG = eINSTANCE.getMutatorConfig();

		/**
		 * The meta object literal for the '<em><b>Root EClass</b></em>'
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference MUTATOR_CONFIG__ROOT_ECLASS = eINSTANCE
			.getMutatorConfig_RootEClass();

		/**
		 * The meta object literal for the '<em><b>Min Objects Count</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MUTATOR_CONFIG__MIN_OBJECTS_COUNT = eINSTANCE
			.getMutatorConfig_MinObjectsCount();

		/**
		 * The meta object literal for the '<em><b>Ignore And Log</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MUTATOR_CONFIG__IGNORE_AND_LOG = eINSTANCE
			.getMutatorConfig_IgnoreAndLog();

		/**
		 * The meta object literal for the '<em><b>Do Not Generate Root</b></em> ' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MUTATOR_CONFIG__DO_NOT_GENERATE_ROOT = eINSTANCE
			.getMutatorConfig_DoNotGenerateRoot();

		/**
		 * The meta object literal for the ' <em><b>Use Ecore Util Delete</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MUTATOR_CONFIG__USE_ECORE_UTIL_DELETE = eINSTANCE
			.getMutatorConfig_UseEcoreUtilDelete();

		/**
		 * The meta object literal for the '<em><b>EClasses To Ignore</b></em>'
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference MUTATOR_CONFIG__ECLASSES_TO_IGNORE = eINSTANCE
			.getMutatorConfig_EClassesToIgnore();

		/**
		 * The meta object literal for the ' <em><b>EStructural Features To Ignore</b></em>' reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference MUTATOR_CONFIG__ESTRUCTURAL_FEATURES_TO_IGNORE = eINSTANCE
			.getMutatorConfig_EStructuralFeaturesToIgnore();

		/**
		 * The meta object literal for the '<em><b>EPackages</b></em>' reference
		 * list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference MUTATOR_CONFIG__EPACKAGES = eINSTANCE
			.getMutatorConfig_EPackages();

		/**
		 * The meta object literal for the '<em><b>Max Delete Count</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MUTATOR_CONFIG__MAX_DELETE_COUNT = eINSTANCE
			.getMutatorConfig_MaxDeleteCount();

	}

} // ConfigPackage