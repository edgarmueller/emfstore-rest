package org.eclipse.emf.emfstore.server.model.api;

import java.util.List;

import org.eclipse.emf.emfstore.server.model.api.versionspec.IPrimaryVersionSpec;

public interface IHistoryInfo {

	IPrimaryVersionSpec getPrimarySpec();

	List<IPrimaryVersionSpec> getNextSpecs();

	IPrimaryVersionSpec getPreviousSpec();

	List<IPrimaryVersionSpec> getMergedFromSpecs();

	List<IPrimaryVersionSpec> getMergedToSpecs();

	ILogMessage getLogMessage();

}
