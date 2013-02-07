package org.eclipse.emf.emfstore.server.model.api;

import java.util.List;

import org.eclipse.emf.emfstore.server.model.api.versionspecs.IPrimaryVersionSpec;

public interface IHistoryInfo {

	IPrimaryVersionSpec getPrimarySpec();

	List<? extends IPrimaryVersionSpec> getNextSpecs();

	IPrimaryVersionSpec getPreviousSpec();

	List<? extends IPrimaryVersionSpec> getMergedFrom();

	List<? extends IPrimaryVersionSpec> getMergedTo();

	ILogMessage getLogMessage();

}
