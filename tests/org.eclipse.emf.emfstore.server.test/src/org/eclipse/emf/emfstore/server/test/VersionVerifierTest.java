/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.server.test;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.emfstore.internal.server.ServerConfiguration;
import org.eclipse.emf.emfstore.internal.server.accesscontrol.authentication.verifiers.VersionVerifier;
import org.eclipse.emf.emfstore.internal.server.exceptions.ClientVersionOutOfDateException;
import org.eclipse.emf.emfstore.internal.server.model.ClientVersionInfo;
import org.eclipse.emf.emfstore.internal.server.model.ModelFactory;
import org.junit.Test;

/**
 * @author Edgar
 * 
 */
public class VersionVerifierTest {

	private static final String[] ACCEPTED_VERSION_1_2_3 = new String[] { "1.2.3" };
	private static final String[] ACCEPTED_VERSION_1_2_4 = new String[] { "1.2.4" };
	private static final String[] WILDCARD_VERSION = new String[] { "1.2.4.*" };
	private static final String[] WILDCARD_VERSION_WITH_SUFFIX = new String[] { "1.2.4.*FOO" };
	private static final String[] ANY_VERSION = new String[] { ServerConfiguration.ACCEPTED_VERSIONS_ANY };
	private static final String[] ACCEPTED_VERSIONS = new String[] {
		ACCEPTED_VERSION_1_2_3[0], ACCEPTED_VERSION_1_2_4[0] };

	private static ClientVersionInfo createVersionInfo(String version) {
		final ClientVersionInfo clientVersionInfo = ModelFactory.eINSTANCE.createClientVersionInfo();
		clientVersionInfo.setVersion(version);
		return clientVersionInfo;
	}

	@Test(expected = ClientVersionOutOfDateException.class)
	public void testVerifyNull() throws ClientVersionOutOfDateException {
		VersionVerifier.verify(ACCEPTED_VERSIONS, null);
	}

	@Test
	public void testSupplyNoVersions() throws ClientVersionOutOfDateException {
		VersionVerifier.verify(null, createVersionInfo("1.2.3"));
	}

	@Test
	public void testMathesOneOfMany() throws ClientVersionOutOfDateException {
		VersionVerifier.verify(ACCEPTED_VERSION_1_2_3, createVersionInfo("1.2.3"));
	}

	@Test
	public void testMathesOne() throws ClientVersionOutOfDateException {
		VersionVerifier.verify(ACCEPTED_VERSIONS, createVersionInfo("1.2.3"));
	}

	@Test
	public void testMatchesWildcard() throws ClientVersionOutOfDateException {
		VersionVerifier.verify(WILDCARD_VERSION, createVersionInfo("1.2.4.1"));
		VersionVerifier.verify(WILDCARD_VERSION, createVersionInfo("1.2.4.1xyz"));
	}

	@Test(expected = ClientVersionOutOfDateException.class)
	public void testMatchesNotWildcard1() throws ClientVersionOutOfDateException {
		VersionVerifier.verify(WILDCARD_VERSION, createVersionInfo("1.2.3.1"));
	}

	@Test(expected = ClientVersionOutOfDateException.class)
	public void testMatchesNotWildcard2() throws ClientVersionOutOfDateException {
		VersionVerifier.verify(WILDCARD_VERSION, createVersionInfo("1.2.3.4.1xyz"));
	}

	@Test(expected = ClientVersionOutOfDateException.class)
	public void testMatchesNotWildcard3() throws ClientVersionOutOfDateException {
		VersionVerifier.verify(WILDCARD_VERSION, createVersionInfo("1.2.3.4"));
	}

	@Test
	public void testMatchesWildcardWithSuffix() throws ClientVersionOutOfDateException {
		VersionVerifier.verify(WILDCARD_VERSION_WITH_SUFFIX, createVersionInfo("1.2.4.1xyzFOO"));
		VersionVerifier.verify(WILDCARD_VERSION_WITH_SUFFIX, createVersionInfo("1.2.4.1FOO"));
	}

	@Test(expected = ClientVersionOutOfDateException.class)
	public void testMatchesNotWildcardWithSuffix1() throws ClientVersionOutOfDateException {
		VersionVerifier.verify(WILDCARD_VERSION_WITH_SUFFIX, createVersionInfo("1.2.4.1xyz"));
	}

	@Test(expected = ClientVersionOutOfDateException.class)
	public void testMatchesNotWildcardWithSuffix2() throws ClientVersionOutOfDateException {
		VersionVerifier.verify(WILDCARD_VERSION_WITH_SUFFIX, createVersionInfo("1.2.4FOO"));
	}

	@Test(expected = ClientVersionOutOfDateException.class)
	public void testMatchesNotWildcardWithSuffix3() throws ClientVersionOutOfDateException {
		VersionVerifier.verify(WILDCARD_VERSION_WITH_SUFFIX, createVersionInfo("1X2.4FOO"));
	}

	@Test
	public void testMatchesAny() throws ClientVersionOutOfDateException {
		VersionVerifier.verify(ANY_VERSION, createVersionInfo("abc"));
	}

	@Test
	public void testInspectClientVersionOutOfDateMessage() {
		try {
			VersionVerifier.verify(ACCEPTED_VERSION_1_2_3, createVersionInfo("1.1.1"));
		} catch (final ClientVersionOutOfDateException ex) {
			assertEquals("Client version: " + "1.1.1"
				+ " - Accepted versions: " + "1.2.3.", ex.getMessage());
		}
	}

	@Test
	public void testInspectClientVersionOutOfDateMessages() {
		try {
			VersionVerifier.verify(ACCEPTED_VERSIONS, createVersionInfo("1.1.1"));
		} catch (final ClientVersionOutOfDateException ex) {
			assertEquals("Client version: " + "1.1.1"
				+ " - Accepted versions: " + "1.2.3, 1.2.4.", ex.getMessage());
		}
	}
}
