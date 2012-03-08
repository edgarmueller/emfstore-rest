package org.eclipse.emf.emfstore.common.extensionpoint;

import org.eclipse.core.runtime.CoreException;

public class ExtensionPointException extends RuntimeException {
	public ExtensionPointException(Exception e) {
		super(e);
	}

	public ExtensionPointException(String string) {
		super(string);
	}
}