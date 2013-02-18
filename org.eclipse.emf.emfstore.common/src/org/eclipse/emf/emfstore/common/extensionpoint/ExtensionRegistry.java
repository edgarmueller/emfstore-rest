package org.eclipse.emf.emfstore.common.extensionpoint;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExtensionRegistry {

	public static final ExtensionRegistry INSTANCE = new ExtensionRegistry();
	
	private Map<String, List<ESExtensionElement>> extensionElements;
	private Map<String, ESExtensionPoint> extensionPoints;

	private ExtensionRegistry() {
		extensionPoints = new LinkedHashMap<String, ESExtensionPoint>();
		extensionElements = new LinkedHashMap<String, List<ESExtensionElement>>();
	}
	
	public void registerBoolean(String id, String attributeId, boolean defaultValue) {
		ESExtensionPoint extensionPoint = getExtensionPoint(id);
	}
	
	public boolean getBoolean(String id, String attributeId, boolean defaultValue) {
		ESExtensionPoint extensionPoint = getExtensionPoint(id);
		return extensionPoint.getBoolean(attributeId, defaultValue);
	}
	
	public <T> void registerClass(String id, String attributeId, T t) {
		
	}

	public void register(ESExtensionPoint extensionPoint, boolean checkElements) {
		List<ESExtensionElement> elements = extensionPoint.getExtensionElements();

		if (checkElements && (elements == null || elements.size() == 0)) {
			throw new IllegalArgumentException(MessageFormat.format(
				"Extension point {0} to be registered does not own any extension elements.", extensionPoint.getId()));
		}

		extensionPoints.put(extensionPoint.getId(), extensionPoint);
		extensionElements.put(extensionPoint.getId(), elements);
	} 

	public ESExtensionPoint getExtensionPoint(String id) {

		if (extensionPoints.containsKey(id)) {
			return extensionPoints.get(id);
		}

		ESExtensionPoint extensionPoint = new ESExtensionPoint(id);
		register(extensionPoint, false);
		return extensionPoint;
	}

	public List<ESExtensionElement> getExtensionElements(String id) throws ExtensionPointNotRegisteredException {
		if (extensionElements.containsKey(id)) {
			return extensionElements.get(id);
		}

		throw new ExtensionPointNotRegisteredException();
	}

	public ESExtensionElement getElementWithHighestPriority(String id) throws ExtensionPointNotRegisteredException {

		if (extensionElements.containsKey(id)) {
			return extensionPoints.get(id).getElementWithHighestPriority();
		}

		throw new ExtensionPointNotRegisteredException();
	}

	// TODO: throw exception?
	public <T> T getType(String id, String attributeId, Class<T> clazz) {
		ESExtensionPoint extensionPoint = getExtensionPoint(id);
		return extensionPoint.getClass(attributeId, clazz);
	}


}
