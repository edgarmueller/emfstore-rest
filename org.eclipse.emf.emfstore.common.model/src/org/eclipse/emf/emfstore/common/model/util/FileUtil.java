/*******************************************************************************
 * Copyright 2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.common.model.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

/**
 * Helper class for file system operations.
 * 
 * @author wesendonk
 */
public final class FileUtil {

	/**
	 * Private constructor.
	 */
	private FileUtil() {

	}

	/**
	 * This method copies a single file.
	 * 
	 * @param source the source
	 * @param destination the destination
	 * @throws IOException copy problem
	 */
	public static void copyFile(File source, File destination) throws IOException {
		copyFile(new FileInputStream(source), destination);
	}

	/**
	 * This method copies a single file.
	 * 
	 * @param source the source input stream
	 * @param destination the destination
	 * @throws IOException copy problem
	 */
	public static void copyFile(InputStream source, File destination) throws IOException {
		FileOutputStream outputStream = null;

		try {

			if (source == null || destination == null) {
				throw new IOException("Source or destination is null.");
			}

			if (destination.getParentFile() != null) {
				destination.getParentFile().mkdirs();
			}

			outputStream = new FileOutputStream(destination);

			byte[] buffer = new byte[4096];
			int read;
			while ((read = source.read(buffer)) != -1) {
				outputStream.write(buffer, 0, read);
			}
		} finally {
			if (source != null) {
				source.close();
			}
			if (outputStream != null) {
				outputStream.close();
			}
		}
	}

	/**
	 * Copy a directory from source to target including its contained files and directories.
	 * 
	 * @param source directory
	 * @param destination directory
	 * @throws IOException on a IO problem during copy
	 */
	public static void copyDirectory(File source, File destination) throws IOException {

		destination.mkdirs();
		if (!source.exists()) {
			return;
		}
		for (File file : source.listFiles()) {
			if (file.isDirectory()) {
				copyDirectory(file, new File(destination.getAbsolutePath() + File.separatorChar + file.getName()));
			} else {
				copyFile(file, new File(destination.getAbsolutePath() + File.separatorChar + file.getName()));
			}
		}
	}

	/**
	 * This method allows you to zip a folder. *UNDER CONSTRUCTION*
	 * 
	 * @param source folder to zip
	 * @param destination target zip file
	 * @throws IOException in case of failure
	 */
	public static void zipFolder(File source, File destination) throws IOException {
		if (!source.isDirectory()) {
			throw new IOException("Source must be folder.");
		}
		if (destination.exists()) {
			throw new IOException("Destination already exists.");
		}
		ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(
			new FileOutputStream(destination)));
		String path = source.getPath();
		path += (path.endsWith(File.separator) ? "" : File.separatorChar);
		zip(source, path, zipOutputStream, new byte[8192]);
		zipOutputStream.close();
	}

	private static void zip(File current, String rootPath, ZipOutputStream zipStream, byte[] buffer) throws IOException {
		if (current.isDirectory()) {
			for (File file : current.listFiles()) {
				if (!".".equals(file.getName()) && !"..".equals(file.getName())) {
					zip(file, rootPath, zipStream, buffer);
				}
			}
		} else if (current.isFile()) {
			zipStream.putNextEntry(new ZipEntry(current.getPath().replace(rootPath, "")));
			FileInputStream file = new FileInputStream(current);
			int read;
			while ((read = file.read(buffer)) != -1) {
				zipStream.write(buffer, 0, read);
			}
			zipStream.closeEntry();
			file.close();
		} else {
			throw new IllegalStateException();
		}
	}

	/**
	 * Compares the contents of two files.
	 * 
	 * @param file1
	 *            the first file
	 * @param file2
	 *            the second file
	 * @return true, if the content of both files is equal, false otherwise
	 */
	public static boolean areEqual(File file1, File file2) {
		return areEqual(file1, file2, new NullProgressMonitor());
	}

	/**
	 * Compares the contents of two files.
	 * 
	 * @param file1
	 *            the first file
	 * @param file2
	 *            the second file
	 * @param monitor
	 *            a progress monitor that may be used to indicate the progress of the equality check
	 * @return true, if the content of both files is equal, false otherwise
	 */
	public static boolean areEqual(File file1, File file2, IProgressMonitor monitor) {

		BufferedInputStream stream1 = null;
		BufferedInputStream stream2 = null;
		try {
			stream1 = new BufferedInputStream(new FileInputStream(file1));
			stream2 = new BufferedInputStream(new FileInputStream(file2));
			monitor.beginTask("Comparing...",
				file1.length() > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) file1.length());
			boolean equals = areEqual(stream1, stream2, monitor);
			monitor.done();
			return equals;
		} catch (IOException e) {
			return false;
		} finally {
			try {
				stream1.close();
				stream2.close();
			} catch (IOException e) {
				return false;
			}
		}
	}

	private static boolean areEqual(InputStream inputStream1, InputStream input2, IProgressMonitor monitor)
		throws IOException {

		int char1 = inputStream1.read();

		while (char1 != -1) {
			int char2 = input2.read();
			if (char1 != char2) {
				return false;
			}
			char1 = inputStream1.read();
		}

		return input2.read() == -1;
	}

	/**
	 * Deletes a directory.
	 * 
	 * @param file the directory
	 * @param force true if delete should try to delete forcefully including retries, this may be slow.
	 * @throws IOException if delete fails
	 */
	public static void deleteDirectory(File file, boolean force) throws IOException {
		int maxRetry = 3;
		if (!force) {
			maxRetry = 1;
		}
		for (int i = 0; i <= maxRetry; i++) {
			try {
				FileUtils.deleteDirectory(file);
				return;
			} catch (IOException exception) {
				// ignore exception if retry counter below max
				if (i >= maxRetry) {
					throw exception;
				}
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					throw exception;
				}
			}
		}
	}

	/**
	 * Returns the extension of the given file.
	 * 
	 * @param file
	 *            the file whose extension should be determined
	 * @return the file extension, if any, otherwise empty string
	 */
	public static String getExtension(File file) {
		int lastIndexOf = file.getName().lastIndexOf(".");

		if (lastIndexOf == -1) {
			return StringUtils.EMPTY;
		}

		return StringUtils.substring(file.getName(), lastIndexOf);
	}
}
