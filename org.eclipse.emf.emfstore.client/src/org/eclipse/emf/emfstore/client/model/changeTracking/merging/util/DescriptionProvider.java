package org.eclipse.emf.emfstore.client.model.changeTracking.merging.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.emf.emfstore.client.model.Activator;
import org.osgi.framework.Bundle;

public class DescriptionProvider {

	private Properties properties;
	private String prefix;

	public DescriptionProvider(String prefix) {
		properties = load();
		this.prefix = prefix;
	}

	public DescriptionProvider() {
		this(null);
	}

	private Properties load() {
		Properties properties = new Properties();

		try {
			Bundle bundle = Activator.getDefault().getBundle();
			URL fileURL = bundle.getEntry("resources/conflictdescription.ini");
			properties.load(new FileInputStream(new File(FileLocator.resolve(fileURL).toURI())));
		} catch (URISyntaxException e1) {
		} catch (IOException e1) {
		}

		return properties;
	}

	public String getDescription(String key) {
		return properties.getProperty(getKey(key), getDefaultValue());
	}

	private String getKey(String key) {
		if (prefix == null || prefix == "") {
			return key;
		}
		return prefix + "." + key;
	}

	protected String getDefaultValue() {
		return "";
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

}
