package org.eclipse.emf.emfstore.client.api;

import java.util.List;

import org.eclipse.emf.emfstore.server.model.api.IProjectInfo;

public interface IServerInfo {

	String getName();

	int getPort();

	String getUrl();

	List<? extends IProjectInfo> getProjectInfos();

	IUsersession getLastUsersession();

	String getCertificateAlias();

}
