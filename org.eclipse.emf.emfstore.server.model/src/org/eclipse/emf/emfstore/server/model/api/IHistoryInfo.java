package org.eclipse.emf.emfstore.server.model.api;

import java.util.List;

import org.eclipse.emf.emfstore.server.model.api.versionspecs.IPrimaryVersionSpec;

public interface IHistoryInfo {

	IPrimaryVersionSpec getPrimarySpec();

	List<IPrimaryVersionSpec> getNextSpecs();

	IPrimaryVersionSpec getPreviousSpec();

	List<IPrimaryVersionSpec> getMergedFrom();

	List<IPrimaryVersionSpec> getMergedTo();

	ILogMessage getLogMessage();

}
