/*******************************************************************************
 * Copyright (c) 2012-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.common;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.emfstore.common.extensionpoint.ESExtensionPoint;

/**
 * The extension registry may be used as simple replacement for the extension
 * point mechanism in case one does not want to expose internal types.
 * 
 * It is capable of retrieving actual extension point information, but currently
 * does not support contributing such.
 * 
 * @author emueller
 */
public class ExtensionRegistry {

	public static final ExtensionRegistry INSTANCE = new ExtensionRegistry();

	private Map<String, ESConfigElement> configElements;

	private ExtensionRegistry() {
		configElements = new HashMap<String, ExtensionRegistry.ESConfigElement>();
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String id, Class<T> clazz, T defaultInstance, boolean shouldSetDefault) {

		T extensionPointInstnace = getExtensionElement(id, clazz);

		if (extensionPointInstnace != null) {
			return extensionPointInstnace;
		}

		ESConfigElement configElement = configElements.get(id);
		T t;

		if (configElement != null) {
			t = (T) configElement.get();
		} else {
			t = defaultInstance;
			if (shouldSetDefault) {
				set(id, t);
			}
		}

		return t;
	}

	public <T> T get(String id, Class<T> clazz) {
		return get(id, clazz, null, false);
	}

	public <T> void set(String id, T t) {
		// TODO: if already present?
		configElements.put(id, new ESConfigElement(t));
	}

	private <T> T getExtensionElement(String id, Class<T> t) {

		int idx = id.lastIndexOf('.');
		String extensionPointId = id.substring(0, idx);
		String attributeName = id.substring(idx + 1, id.length());

		ESExtensionPoint extensionPoint = new ESExtensionPoint(extensionPointId);

		if (extensionPoint.getFirst() == null) {
			return null;
		}

		return extensionPoint.getFirst().getClass(attributeName, t);
	}

	class ESConfigElement {

		private Object t;

		public ESConfigElement(Object o) {
			t = o;
		}

		public Object get() {
			return t;
		}

		public void set(Object t) {
			this.t = t;
		}
	}

	public void remove(String id) {
		configElements.remove(id);
	}
}
