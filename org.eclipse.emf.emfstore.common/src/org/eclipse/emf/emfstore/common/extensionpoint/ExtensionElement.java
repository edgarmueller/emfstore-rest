package org.eclipse.emf.emfstore.common.extensionpoint;

import static org.eclipse.emf.emfstore.common.extensionpoint.ExtensionPoint.handleErrorOrNull;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

public class ExtensionElement {

	private final IConfigurationElement element;
	private boolean exceptionInsteadOfNull = false;

	public ExtensionElement(IConfigurationElement element) {
		this.element = element;
	}

	public boolean getThrowException() {
		return this.exceptionInsteadOfNull;
	}

	public Boolean getBoolean(String name) {
		return Boolean.parseBoolean(getAttribute(name));
	}

	public String getAttribute(String name) {
		String attribute = this.element.getAttribute(name);
		if (attribute == null) {
			handleErrorOrNull(exceptionInsteadOfNull, null);
		}
		return attribute;
	}

	public ExtensionElement(IConfigurationElement element, boolean exceptionInsteadOfNull) {
		this(element);
		this.exceptionInsteadOfNull = exceptionInsteadOfNull;
	}

	public void setThrowException(boolean b) {
		this.exceptionInsteadOfNull = b;
	}

	@SuppressWarnings("unchecked")
	public <T> T getClass(String class_id, Class<T> returnType) {
		try {
			Object executableExtension = this.element.createExecutableExtension(class_id);
			if (returnType.isInstance(executableExtension)) {
				return (T) executableExtension;
			}
			return (T) handleErrorOrNull(exceptionInsteadOfNull, null);
		} catch (CoreException e) {
			return (T) handleErrorOrNull(exceptionInsteadOfNull, e);
		}
	}

	public Integer getInteger(String string) {
		String num = getAttribute(string);
		try {
			return Integer.parseInt(num);
		} catch (NumberFormatException e) {
			return (Integer) handleErrorOrNull(exceptionInsteadOfNull, e);
		}
	}
}