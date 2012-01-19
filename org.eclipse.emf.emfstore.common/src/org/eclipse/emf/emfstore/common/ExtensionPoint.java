package org.eclipse.emf.emfstore.common;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

/**
 * TODO: extend this helper. looping, getBoolean, getInt ... 
 * 
 * @author wesendon
 */

public class ExtensionPoint {
	private IConfigurationElement[] rawExtensions;

	public ExtensionPoint(String id) {
		rawExtensions = Platform.getExtensionRegistry().getConfigurationElementsFor(id);
	}

	@SuppressWarnings("unchecked")
	public <T> T getClass(String id, Class<T> returnType) {
		if (rawExtensions.length < 1) {
			return null;
		}
		try {
			Object executableExtension = rawExtensions[0].createExecutableExtension(id);
			return (returnType.isInstance(executableExtension)) ? (T) executableExtension : null;
		} catch (CoreException e) {
			return null;
		}
	}
}
