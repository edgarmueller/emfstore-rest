package org.eclipse.emf.emfstore.internal.server.model.impl.api;

import org.eclipse.emf.emfstore.internal.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.LogMessageFactory;
import org.eclipse.emf.emfstore.server.model.ESLogMessage;
import org.eclipse.emf.emfstore.server.model.ESLogMessageFactory;

public class ESLogMessageFactoryImpl implements ESLogMessageFactory {

	public ESLogMessage createLogMessage(String message, String author) {
		LogMessage logMessage = LogMessageFactory.INSTANCE.createLogMessage(message, author);
		return logMessage.toAPI();
	}

}
