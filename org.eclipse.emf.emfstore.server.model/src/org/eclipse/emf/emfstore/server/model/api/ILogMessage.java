package org.eclipse.emf.emfstore.server.model.api;

import java.util.Date;

public interface ILogMessage {

	void setMessage(String logMessage);

	String getAuthor();

	Date getDate();

}
