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
package org.eclipse.emf.emfstore.internal.server.accesscontrol.authentication;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.server.exceptions.AccessControlException;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalESException;

/**
 * This verifyer can be used to store user and passwords in a property file. Entries in the property file look should
 * look like this: <b>user = password</b>
 * 
 * @author wesendonk
 */
public class SimplePropertyFileVerifier extends AbstractAuthenticationControl {

	private Properties passwordFile;

	private final Hash hash;

	/**
	 * Hash algorithms supported by spfv verifier.
	 * 
	 * @author wesendon
	 */
	public enum Hash {
		/**
		 * NONE - no hash, MD5 - md5 hash, SHA1 - sha1 hash.
		 */
		NONE, MD5, SHA1
	}

	/**
	 * Default constructor. No hash will be used for passwords
	 * 
	 * @see #SimplePropertyFileVerifier(String, Hash)
	 * @param filePath path to password file
	 * @throws FatalESException in case of failure
	 */
	public SimplePropertyFileVerifier(String filePath) throws FatalESException {
		this(filePath, Hash.NONE);
	}

	/**
	 * Constructor with ability to select hash algorithm for password.
	 * 
	 * @param filePath path to file
	 * @param hash selected hash
	 * @throws FatalESException if hash is null
	 */
	public SimplePropertyFileVerifier(String filePath, Hash hash) throws FatalESException {
		super();
		if (hash == null) {
			throw new FatalESException("Hash may not be null for verifier.");
		}
		this.hash = hash;

		passwordFile = new Properties();
		File propertyFile = new File(filePath);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(propertyFile);
			passwordFile.load(fis);
		} catch (IOException e) {
			ModelUtil.logInfo("Couldn't load password file from path: " + filePath);
			// Run with empty password file
			// throw new AccessControlException("Couldn't load password file from path: "+filePath);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					ModelUtil.logInfo("Couldn't load password file from path: " + filePath);
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean verifyPassword(String username, String password) throws AccessControlException {
		String expectedPassword = passwordFile.getProperty(username);
		password = hashPassword(password);
		if (expectedPassword == null || !expectedPassword.equals(password)) {
			return false;
		}
		return true;
	}

	private String hashPassword(String password) {
		if (password == null || hash.equals(Hash.NONE)) {
			return password;
		} else {
			try {
				MessageDigest md = null;
				switch (hash) {
				case SHA1:
					md = MessageDigest.getInstance("SHA-1");
					break;
				case MD5:
					md = MessageDigest.getInstance("MD5");
					break;
				default:
				}
				if (md != null) {
					return new String(md.digest(password.getBytes()));
				}
			} catch (NoSuchAlgorithmException e) {
			}
		}
		return null;
	}
}