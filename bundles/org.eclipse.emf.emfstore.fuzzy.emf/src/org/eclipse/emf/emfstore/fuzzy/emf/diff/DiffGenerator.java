/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * JulianSommerfeldt
 ******************************************************************************/
package org.eclipse.emf.emfstore.fuzzy.emf.diff;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.emfstore.fuzzy.emf.FuzzyUtil;
import org.eclipse.emf.emfstore.fuzzy.emf.config.ConfigFactory;
import org.eclipse.emf.emfstore.fuzzy.emf.config.DiffReport;
import org.eclipse.emf.emfstore.fuzzy.emf.config.TestConfig;
import org.eclipse.emf.emfstore.fuzzy.emf.config.TestDiff;
import org.eclipse.emf.emfstore.fuzzy.emf.config.TestResult;
import org.eclipse.emf.emfstore.fuzzy.emf.config.TestRun;

/**
 * Generates {@link TestDiff}s out of {@link TestRun}.
 * 
 * @author Julian Sommerfeldt
 * 
 */
public class DiffGenerator {

	private DiffReport diffReport;

	private Resource diffResource;

	/**
	 * A {@link DiffGenerator} with the standard diff file {@link FuzzyUtil#DIFF_FILE}.
	 */
	public DiffGenerator() {
		this(FuzzyUtil.DIFF_FILE);
	}

	/**
	 * A {@link DiffGenerator} with using the diffPath.
	 * 
	 * @param diffPath
	 *            The path to the Diff file.
	 */
	public DiffGenerator(String diffPath) {
		this(FuzzyUtil.createResource(diffPath));
	}

	/**
	 * A {@link DiffGenerator} using the diffResource.
	 * 
	 * @param diffResource
	 *            The resource to use for the diff.
	 */
	public DiffGenerator(Resource diffResource) {
		this.diffResource = diffResource;

		if (FuzzyUtil.resourceExists(diffResource)) {
			try {
				diffResource.load(null);
			} catch (IOException e) {
				throw new RuntimeException("Could not load resource: "
					+ diffResource.getURI(), e);
			}
		}

		diffReport = getDiffReport(diffResource);
	}

	/**
	 * Create a diff from two {@link TestRun}s.
	 * 
	 * @param firstRun
	 *            The first {@link TestRun}.
	 * @param secondRun
	 *            The second {@link TestRun}.
	 * @throws IOException
	 *             If there is a saving/loading failure with resources.
	 */
	public void createDiff(TestRun firstRun, TestRun secondRun)
		throws IOException {

		TestConfig config = firstRun.getConfig();

		// check if it already contains the config
		boolean containsConfig = false;
		// create map containing existing diffs
		// identified through testname + seedcount (e.g. test1)
		Map<String, TestDiff> existingDiffs = new HashMap<String, TestDiff>();
		List<TestDiff> diffs = diffReport.getDiffs();
		for (TestDiff diff : diffs) {

			// add existing diffs
			TestResult result = FuzzyUtil.getValidTestResult(diff);
			existingDiffs.put(getResultIdentifier(result), diff);

			// check for configs
			if ((!containsConfig)
				&& (diff.getConfig().getId().equals(config.getId()))) {
				containsConfig = true;
				config = diff.getConfig();
			}
		}

		// if the resource does not contain the config already add it
		if (!containsConfig) {
			diffResource.getContents().add(config);
		}

		// create diffs for the two testruns
		checkForDiffs(firstRun.getResults(), secondRun.getResults(), config,
			existingDiffs);
		checkForDiffs(secondRun.getResults(), firstRun.getResults(), config,
			existingDiffs);

		diffResource.getContents().add(diffReport);
		diffResource.save(null);
	}

	private void checkForDiffs(List<TestResult> firstResults,
		List<TestResult> secondResults, TestConfig config,
		Map<String, TestDiff> existingDiffs) {
		EList<TestDiff> diffs = diffReport.getDiffs();
		for (TestResult result : new ArrayList<TestResult>(firstResults)) {
			TestResult corrResult = getCorrespondingTestResult(result,
				secondResults);

			TestDiff diff = getChangedTestDiff(result, corrResult);
			if (diff != null) {
				diff.setConfig(config);

				// remove diff if it already contains it
				diffs.remove(existingDiffs.get(getResultIdentifier(result)));
				diffs.add(diff);
			}
		}
	}

	private static String getResultIdentifier(TestResult result) {
		return result.getTestName() + result.getSeedCount();
	}

	private static TestDiff getChangedTestDiff(TestResult fRes, TestResult sRes) {
		boolean changed = false;

		// check if a state switch occured
		// TODO test state switches
		if (fRes == null || sRes == null) {
			changed = true;
		} else if (changed(fRes.getFailure(), sRes.getFailure())) {
			changed = true;
		} else if (changed(fRes.getError(), sRes.getError())) {
			changed = true;
		} else if (fRes.getFailure() != null && sRes.getError() != null) {
			changed = true;
		} else if (fRes.getError() != null && sRes.getFailure() != null) {
			changed = true;
		}

		// if it changed, create a new TestDiff
		if (changed) {
			return createTestDiff(fRes, sRes);
		}

		return null;
	}

	private static boolean changed(Object o1, Object o2) {
		if (o1 == null && o2 != null) {
			return true;
		}
		if (o1 != null && o2 == null) {
			return true;
		}
		return false;
	}

	private static TestDiff createTestDiff(TestResult fRes, TestResult sRes) {
		TestDiff diff = ConfigFactory.eINSTANCE.createTestDiff();
		diff.setLastUpdate(new Date(System.currentTimeMillis()));
		diff.setOldResult(fRes);
		diff.setNewResult(sRes);
		return diff;
	}

	private static TestResult getCorrespondingTestResult(TestResult result,
		List<TestResult> results) {
		for (TestResult res : results) {
			if (res.getSeedCount() == result.getSeedCount()
				&& res.getTestName().equals(result.getTestName())) {
				return res;
			}
		}
		return null;
	}

	private static DiffReport getDiffReport(Resource resource) {
		for (EObject obj : resource.getContents()) {
			if (obj instanceof DiffReport) {
				return (DiffReport) obj;
			}
		}
		return ConfigFactory.eINSTANCE.createDiffReport();
	}
}
