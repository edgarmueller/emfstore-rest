/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.server.startup;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.emf.emfstore.internal.common.model.util.FileUtil;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.ServerConfiguration;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * When using the default EMF XMI persistence hrefs between resources are persisted based on the non-normalized URIs of
 * the resources. Since version 1.1 introduced a new URI scheme for EMFStore, files that were persisted with version 1.0
 * and prior need to be migrated.
 * <p>
 * This migrator will update the Hrefs in legacy files on the server side.
 * 
 * @author jfaltermeier
 * 
 */
public class ServerHrefMigrator {

	private File backup;

	/**
	 * Performs the migration, if needed. Creates a backup beforehand.
	 * 
	 * @return <code>true</code> if migration was successful, <code>false</code> if an error occurred and the server
	 *         startup should be canceled.
	 */
	public boolean migrate() {

		final String sEMFStoreServer = ServerConfiguration.getServerHome();

		// check if migration is needed
		if (isMigrationNeeded(sEMFStoreServer + "storage.uss")) { //$NON-NLS-1$

			if (backup != null) {
				return false;
			}

			try {
				backup = createBackup(ServerConfiguration.getServerHome(),
					ServerConfiguration.getServerHome() + "../backup" + System.currentTimeMillis()); //$NON-NLS-1$
			} catch (final IOException ex) {
				ModelUtil.logException(
					"Error during the backup creation.", ex);
				return false;
			}

			// perform migration
			try {
				doMigrate(sEMFStoreServer);
				return true;
			} catch (final InvocationTargetException ex) {
				ModelUtil.logException(
					"Error during the migration process.", ex);
				return false;
			}
		}
		return true;
	}

	private boolean isMigrationNeeded(String pathToServerSpace) {
		try {
			final String toMatch = getProjectAttribute(pathToServerSpace);
			if (toMatch == null) {
				return false;
			}
			return toMatch.contains("projectHistory.uph"); //$NON-NLS-1$
		} catch (final ParserConfigurationException ex) {
			ModelUtil.logException(
				"Cannot determine whether migration is needed. Migration will be skipped, backup will be created.", ex);
		} catch (final SAXException ex) {
			ModelUtil.logException(
				"Cannot determine whether migration is needed. Migration will be skipped, backup will be created.", ex);
		} catch (final IOException ex) {
			ModelUtil.logException(
				"Cannot determine whether migration is needed. Migration will be skipped, backup will be created.", ex);
		}
		try {
			backup = createBackup(ServerConfiguration.getServerHome(),
				ServerConfiguration.getServerHome() + "../backup" + System.currentTimeMillis()); //$NON-NLS-1$
		} catch (final IOException ex) {
			backup = new File(""); //$NON-NLS-1$
			ModelUtil.logException(
				"Creating the backup failed.", ex);
		}
		return true;
	}

	/**
	 * Creates a backup.
	 * 
	 * @param from path to source
	 * @param to path to destination
	 * @return the backup file
	 * @throws IOException on a IO problem during backup creation
	 */
	protected File createBackup(String from, String to) throws IOException {
		final File sourceFile = new File(from);
		final File backupFile = new File(to);
		FileUtil.copyDirectory(sourceFile, backupFile);
		return backupFile;
	}

	private void doMigrate(String sEMFStoreServer) throws InvocationTargetException {
		migrateNonContainment(sEMFStoreServer + "storage.uss", "projects", new ServerSpaceRule()); //$NON-NLS-1$ //$NON-NLS-2$

		final File fEMFStoreServer = new File(sEMFStoreServer);
		final File[] projects = fEMFStoreServer
			.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.startsWith("project-"); //$NON-NLS-1$
				}
			});
		for (final File f : projects) {
			final String pH = f.getAbsolutePath() + "/projectHistory.uph"; //$NON-NLS-1$
			migrateContainmentHRefs(pH, "versions", //$NON-NLS-1$
				new VersionRule());

			final File[] versions = f.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.startsWith("version-"); //$NON-NLS-1$
				}
			});
			for (final File v : versions) {
				final String versionPath = v.getAbsolutePath();
				migrateNonContainment(versionPath, "nextVersion", new VersionMultiRule()); //$NON-NLS-1$
				migrateNonContainment(versionPath, "previousVersion", new VersionMultiRule()); //$NON-NLS-1$
				migrateNonContainment(versionPath, "ancestorVersion", new VersionMultiRule()); //$NON-NLS-1$
				migrateNonContainment(versionPath, "branchedVersions", new VersionMultiRule()); //$NON-NLS-1$
				migrateNonContainment(versionPath, "mergedToVersion", new VersionMultiRule()); //$NON-NLS-1$
				migrateNonContainment(versionPath, "mergedFromVersion", new VersionMultiRule()); //$NON-NLS-1$
			}
		}
	}

	private String getProjectAttribute(String pathToFile) throws ParserConfigurationException, SAXException,
		IOException {
		final DocumentBuilderFactory docFactory = DocumentBuilderFactory
			.newInstance();
		final DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		final Document doc = docBuilder.parse(pathToFile);

		final Node serverSpace = doc.getFirstChild();
		final NamedNodeMap attr = serverSpace.getAttributes();
		final Node nodeAttr = attr.getNamedItem("projects"); //$NON-NLS-1$
		if (nodeAttr == null) {
			return null;
		}
		final String projectsOld = nodeAttr.getTextContent();
		final String[] projects = projectsOld.split(" "); //$NON-NLS-1$
		if (projects.length < 1) {
			return null;
		}
		return projects[0];
	}

	/**
	 * Updates the href attribute in tags with the given name.
	 * 
	 * @param pathToFile the path of the xmi file to be updated
	 * @param tagName the tag for which the hrefs are to be updated
	 * @param rule the rule computing the new value
	 * @throws InvocationTargetException in case of error
	 */
	protected void migrateContainmentHRefs(String pathToFile,
		String tagName, UpdateXMIAttributeRule rule) throws InvocationTargetException {

		try {
			final DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();
			final DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			final Document doc = docBuilder.parse(pathToFile);

			final NodeList tagElements = doc.getElementsByTagName(tagName);

			for (int i = 0; i < tagElements.getLength(); i++) {
				final Node pS = tagElements.item(i);
				final NamedNodeMap attr = pS.getAttributes();
				final Node nodeAttr = attr.getNamedItem("href"); //$NON-NLS-1$
				final String hrefOld = nodeAttr.getTextContent();
				final String hrefNew = rule.getNewAttribute(hrefOld);
				nodeAttr.setTextContent(hrefNew);
			}

			final TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
			final Transformer transformer = transformerFactory.newTransformer();
			final DOMSource source = new DOMSource(doc);
			final StreamResult result = new StreamResult(new File(pathToFile));
			transformer.transform(source, result);
		} catch (final DOMException ex) {
			throw new InvocationTargetException(ex);
		} catch (final TransformerConfigurationException ex) {
			throw new InvocationTargetException(ex);
		} catch (final ParserConfigurationException ex) {
			throw new InvocationTargetException(ex);
		} catch (final SAXException ex) {
			throw new InvocationTargetException(ex);
		} catch (final IOException ex) {
			throw new InvocationTargetException(ex);
		} catch (final TransformerFactoryConfigurationError ex) {
			throw new InvocationTargetException(ex);
		} catch (final TransformerException ex) {
			throw new InvocationTargetException(ex);
		}
	}

	/**
	 * Updates the attribute with the given name.
	 * 
	 * @param pathToFile the path of the xmi file to be updated
	 * @param tagName the tag for which the attribute contents are to be updated
	 * @param rule the rule computing the new value
	 * @throws InvocationTargetException in case of error
	 */
	protected void migrateNonContainment(String pathToFile,
		String tagName, UpdateXMIAttributeRule rule) throws InvocationTargetException {
		try {
			final DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();
			final DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			final Document doc = docBuilder.parse(pathToFile);

			final Node serverSpace = doc.getFirstChild();
			final NamedNodeMap attr = serverSpace.getAttributes();
			final Node nodeAttr = attr.getNamedItem(tagName);
			if (nodeAttr == null) {
				return;
			}
			final String attributeOld = nodeAttr.getTextContent();
			final String attributeNew = rule.getNewAttribute(attributeOld);
			nodeAttr.setTextContent(attributeNew);

			final TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
			final Transformer transformer = transformerFactory.newTransformer();
			final DOMSource source = new DOMSource(doc);
			final StreamResult result = new StreamResult(new File(pathToFile));
			transformer.transform(source, result);
		} catch (final DOMException ex) {
			throw new InvocationTargetException(ex);
		} catch (final TransformerConfigurationException ex) {
			throw new InvocationTargetException(ex);
		} catch (final ParserConfigurationException ex) {
			throw new InvocationTargetException(ex);
		} catch (final SAXException ex) {
			throw new InvocationTargetException(ex);
		} catch (final IOException ex) {
			throw new InvocationTargetException(ex);
		} catch (final TransformerFactoryConfigurationError ex) {
			throw new InvocationTargetException(ex);
		} catch (final TransformerException ex) {
			throw new InvocationTargetException(ex);
		}
	}

}
