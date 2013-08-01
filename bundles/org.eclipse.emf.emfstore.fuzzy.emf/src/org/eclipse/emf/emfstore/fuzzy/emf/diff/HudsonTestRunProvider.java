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
package org.eclipse.emf.emfstore.fuzzy.emf.diff;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.emfstore.fuzzy.emf.FuzzyUtil;
import org.eclipse.emf.emfstore.fuzzy.emf.config.TestConfig;
import org.eclipse.emf.emfstore.fuzzy.emf.config.TestRun;

/**
 * An implementation of {@link TestRunProvider} to provide {@link TestRun}s
 * created by the CI-Server Hudson.
 * 
 * @author Julian Sommerfeldt
 * 
 */
public class HudsonTestRunProvider extends TestRunProvider {

	private static final String JOB = "job/";

	private static SAXReader saxReader = new SAXReader();

	private String hudsonUrl;

	private static String jobUrl;

	private int firstBuildNumber;

	private int secondBuildNumber;

	/**
	 * The prefix for hudson peroperties.
	 */
	public static final String PROP_HUDSON = ".hudson";

	/**
	 * The hudson url property.
	 */
	public static final String PROP_URL = ".url";

	/**
	 * The hudson artifact folder property.
	 */
	public static final String PROP_ARTIFACT_FOLDER = ".artifact.folder";

	/**
	 * The hudson port property.
	 */
	public static final String PROP_PORT = ".port";

	/**
	 * The name of the hudson job property.
	 */
	public static final String PROP_JOB = ".job";

	/**
	 * The property of the hudson diff job.
	 */
	public static final String PROP_DIFF_JOB = ".diffjob";

	private static final String LAST_BUILD = "lastBuild";

	/**
	 * An array containing all valid states of a hudson build. Valid means it
	 * can be used for creating diffs.
	 */
	public static final String[] VALID_STATES = new String[] { "SUCCESS",
		"UNSTABLE" };

	private static final String ARTIFACT = FuzzyUtil.getProperty(PROP_HUDSON
		+ PROP_ARTIFACT_FOLDER, "/artifact/");

	/**
	 * Standard constructor using the last build and the build before the last
	 * build for reading testruns.
	 * 
	 * @throws DocumentException
	 *             If it cannot read the buildnumbers correctly from hudson.
	 * @throws IOException
	 *             If it cannot read the buildnumbers correctly from hudson.
	 */
	public HudsonTestRunProvider() throws DocumentException, IOException {
		initProperties();

		firstBuildNumber = getLastValidBuildNumber(
			Integer.parseInt(getFirstElementValue(jobUrl + LAST_BUILD
				+ "/api/xml?tree=number")), jobUrl);
		secondBuildNumber = getLastValidBuildNumber(firstBuildNumber - 1,
			jobUrl);
	}

	/**
	 * Constructor using tow special numbers for testruns.
	 * 
	 * @param firstBuildNumber
	 *            The number of the first build (first from the last one
	 *            backwards, so it is later than the second one).
	 * @param secondBuildNumber
	 *            The number of the second build.
	 */
	public HudsonTestRunProvider(int firstBuildNumber, int secondBuildNumber) {
		initProperties();

		this.firstBuildNumber = firstBuildNumber;
		this.secondBuildNumber = secondBuildNumber;
	}

	private void initProperties() {
		hudsonUrl = getHudsonUrl();
		jobUrl = hudsonUrl + JOB
			+ FuzzyUtil.getProperty(PROP_HUDSON + PROP_JOB, "Explorer")
			+ "/";
	}

	private static String getHudsonUrl() {
		String port = FuzzyUtil.getProperty(PROP_HUDSON + PROP_PORT, null);
		return FuzzyUtil
			.getProperty(PROP_HUDSON + PROP_URL, "http://localhost")
			+ (port != null ? (":" + port) : "") + "/";
	}

	private static int getLastValidBuildNumber(int maxBuildNumber, String jobUrl)
		throws MalformedURLException, DocumentException {
		if (maxBuildNumber < 0) {
			throw new RuntimeException(
				"There are not enough valid builds till now!");
		}
		if (isValidBuild(maxBuildNumber, jobUrl)) {
			return maxBuildNumber;
		} else {
			return getLastValidBuildNumber(maxBuildNumber - 1, jobUrl);
		}
	}

	private static boolean isValidBuild(int buildNumber, String jobUrl)
		throws MalformedURLException, DocumentException {
		String result = getFirstElementValue(jobUrl + buildNumber
			+ "/api/xml?tree=result");
		for (String valid : VALID_STATES) {
			if (valid.equals(result)) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	private static String getFirstElementValue(String url)
		throws MalformedURLException, DocumentException {
		Document doc = saxReader.read(new URL(url));
		List<Element> elements = doc.getRootElement().elements();
		if (elements.size() == 0) {
			throw new RuntimeException(
				"There are no elements in the result of the url: " + url);
		}
		return elements.get(0).getText();
	}

	@Override
	public TestRun[] getTestRuns() throws IOException {

		TestRun[] runs = new TestRun[2];

		Resource resource = getTestRunResource(firstBuildNumber);
		if (!FuzzyUtil.resourceExists(resource)) {
			throw new RuntimeException("No TestRun file for first run!");
		}
		resource.load(null);

		runs[0] = getTestRun(resource);

		resource = getTestRunResource(secondBuildNumber);
		if (!FuzzyUtil.resourceExists(resource)) {
			throw new RuntimeException("No TestRun file for second run!");
		}
		resource.load(null);

		runs[1] = getTestRun(resource);

		return runs;
	}

	private Resource getTestRunResource(int buildNumber) {
		return FuzzyUtil.createResource(jobUrl + buildNumber + ARTIFACT
			+ FuzzyUtil.FUZZY_FOLDER + FuzzyUtil.RUN_FOLDER
			+ getTestConfig().getId() + FuzzyUtil.FILE_SUFFIX);
	}

	/**
	 * @return All {@link TestConfig} which are loadable via this {@link HudsonTestRunProvider}.
	 */
	public List<TestConfig> getAllConfigs() {
		Resource resource = FuzzyUtil.createResource(jobUrl + firstBuildNumber
			+ ARTIFACT + FuzzyUtil.FUZZY_FOLDER
			+ FuzzyUtil.TEST_CONFIG_FILE);
		try {
			resource.load(null);
		} catch (IOException e) {
			throw new RuntimeException("Could not load configs file!", e);
		}
		List<TestConfig> configs = new ArrayList<TestConfig>();
		for (EObject obj : resource.getContents()) {
			if (obj instanceof TestConfig) {
				configs.add((TestConfig) obj);
			}
		}
		return configs;
	}

	/**
	 * @return The diff resource created by hudson.
	 * @throws DocumentException
	 *             in case an error occurs during obtainment of the resource.
	 * @throws MalformedURLException
	 *             in case an error occurs during obtainment of the resource.
	 */
	public static Resource getDiffResource() throws MalformedURLException,
		DocumentException {
		String diffJobUrl = getHudsonUrl() + JOB
			+ FuzzyUtil.getProperty(PROP_HUDSON + PROP_DIFF_JOB, "Diff")
			+ "/";
		int lastValidNumber = getLastValidBuildNumber(
			Integer.parseInt(getFirstElementValue(diffJobUrl + LAST_BUILD
				+ "/api/xml?tree=number")), diffJobUrl);
		return FuzzyUtil.createResource(diffJobUrl + lastValidNumber + ARTIFACT
			+ FuzzyUtil.FUZZY_FOLDER + "diff" + FuzzyUtil.FILE_SUFFIX);
	}
}
