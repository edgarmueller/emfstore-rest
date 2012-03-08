package org.eclipse.emf.emfstore.common.extensionpoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

/**
 * TODO: extend this helper. looping, getBoolean, getInt ...
 * 
 * @author wesendon
 */

public class ExtensionPoint {
	private List<ExtensionElement> elements;
	private final String id;
	private boolean exceptionInsteadOfNull;
	private Comparator<ExtensionElement> comparator;

	public ExtensionPoint(String id) {
		this(id,false);
	}

	
	public ExtensionPoint(String id, boolean throwException) {
		this.id = id;
		exceptionInsteadOfNull = throwException;
		this.comparator = getDefaultComparator();
		reload();
	}


	public void reload() {
		this.elements = new ArrayList<ExtensionElement>();
		for (IConfigurationElement element : Platform.getExtensionRegistry().getConfigurationElementsFor(this.id)) {
			elements.add(new ExtensionElement(element, exceptionInsteadOfNull));
		}
		Collections.sort(this.elements, this.comparator);
	}
	
	protected Comparator<ExtensionElement> getDefaultComparator() {
		return new Comparator<ExtensionElement>() {
			public int compare(ExtensionElement o1, ExtensionElement o2) {
				return 0;
			}
		};
	}

	public <T> T getClass(String class_id, Class<T> returnType) {
		ExtensionElement first = getElementWithHighestPriority();
		if (first != null) {
			return first.getClass(class_id, returnType);
		}
		return (T) handleErrorOrNull(exceptionInsteadOfNull, null);
	}

	public Boolean getBoolean(String name) {
		ExtensionElement element = getElementWithHighestPriority();
		if (element != null) {
			return element.getBoolean(name);
		}
		return (Boolean) handleErrorOrNull(exceptionInsteadOfNull, null);
	}

	public String getAttribute(String name) {
		ExtensionElement element = getElementWithHighestPriority();
		if (element != null) {
			return element.getAttribute(name);
		}
		return (String) handleErrorOrNull(exceptionInsteadOfNull, null);
	}

	public ExtensionElement getElementWithHighestPriority() {
		return getFirst();
	}
	
	public void setComparator(Comparator<ExtensionElement> comparator) {
		this.comparator = comparator;
	}

	public ExtensionElement getFirst() {
		if (elements.size() > 0) {
			return elements.get(0);
		}
		return (ExtensionElement) handleErrorOrNull(exceptionInsteadOfNull, null);
	}

	public List<ExtensionElement> getExtensionElements() {
		return Collections.unmodifiableList(elements);
	}

	public ExtensionPoint setThrowException(boolean b) {
		this.exceptionInsteadOfNull = b;
		return this;
	}

//	public void batch(ForEach expt) {
//		for (ExtensionElement element : elements) {
//			boolean throwException = element.getThrowException();
//			element.setThrowException(true);
//			try {
//				expt.execute(element);
//			} catch (ExtensionPointException e) {
//				// do nothing
//			}
//			element.setThrowException(throwException);
//		}
//	}

	protected static Object handleErrorOrNull(boolean useException, Exception expOrNull) {
		if (useException) {
			if (expOrNull == null) {
				throw new ExtensionPointException("Value not found.");
			}
			throw new ExtensionPointException(expOrNull);
		}
		return null;
	}


	public int size() {
		return this.elements.size();
	}
}
