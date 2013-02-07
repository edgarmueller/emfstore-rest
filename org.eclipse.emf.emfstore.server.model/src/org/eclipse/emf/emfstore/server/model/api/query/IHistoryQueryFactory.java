package org.eclipse.emf.emfstore.server.model.api.query;

import java.util.List;

import org.eclipse.emf.emfstore.common.model.api.IEMFStoreFactory;
import org.eclipse.emf.emfstore.common.model.api.IModelElementId;
import org.eclipse.emf.emfstore.server.model.api.versionspecs.IPrimaryVersionSpec;

public interface IHistoryQueryFactory extends IEMFStoreFactory {
	/**
	 * Factory method for range query.
	 * 
	 * @param source
	 *            source version
	 * @param upper
	 *            upper limit
	 * @param lower
	 *            lower limit
	 * @param allVersions
	 *            include all versions, from all branches
	 * @param incoming
	 *            include incoming versions, only if allVersions is false
	 * @param outgoing
	 *            include outgoing versions
	 * @param includeCp
	 *            include changepackges
	 * @return query
	 */
	IRangeQuery rangeQuery(IPrimaryVersionSpec source, int upper, int lower, boolean allVersions, boolean incoming,
		boolean outgoing, boolean includeCp);

	/**
	 * Factory method for path query. Getting all changes from source to target.
	 * 
	 * @param source
	 *            source version
	 * @param target
	 *            target version
	 * @param allVersions
	 *            include all versions, from all branches
	 * @param includeCp
	 *            include changepackages
	 * @return query
	 */
	IPathQuery pathQuery(IPrimaryVersionSpec source, IPrimaryVersionSpec target, boolean allVersions, boolean includeCp);

	/**
	 * Factory method for modelelements range queries.
	 * 
	 * @param source
	 *            source version
	 * @param modelElements
	 *            modelelements
	 * @param upper
	 *            upper limit
	 * @param lower
	 *            lower limit
	 * @param allVersions
	 *            include all versions, from all branches
	 * @param includeCp
	 *            include change packages
	 * @return query
	 */
	IModelElementQuery modelelementQuery(IPrimaryVersionSpec source, List<IModelElementId> modelElements, int upper,
		int lower, boolean allVersions, boolean includeCp);

	/**
	 * Factory method for modelelement range queries.
	 * 
	 * @param source
	 *            source version
	 * @param id
	 *            modelelement
	 * @param upper
	 *            upper limit
	 * @param lower
	 *            lower limit
	 * @param allVersions
	 *            include all versions, from all branches
	 * @param includeCp
	 *            include change packages
	 * @return query
	 */
	IModelElementQuery modelelementQuery(IPrimaryVersionSpec source, IModelElementId id, int upper, int lower,
		boolean allVersions, boolean includeCp);
}
