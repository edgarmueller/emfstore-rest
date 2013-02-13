package org.eclipse.emf.emfstore.server.model;

import java.util.List;

import org.eclipse.emf.emfstore.server.model.versionspec.IPrimaryVersionSpec;

public interface IHistoryInfo {

	IPrimaryVersionSpec getPrimarySpec();

	List<IPrimaryVersionSpec> getNextSpecs();

	IPrimaryVersionSpec getPreviousSpec();

	List<IPrimaryVersionSpec> getMergedFromSpecs();

	List<IPrimaryVersionSpec> getMergedToSpecs();

	ILogMessage getLogMessage();

}
