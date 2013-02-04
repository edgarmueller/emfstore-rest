package org.eclipse.emf.emfstore.client.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.client.model.changeTracking.merging.ConflictResolver;
import org.eclipse.emf.emfstore.client.model.controller.callbacks.CommitCallback;
import org.eclipse.emf.emfstore.client.model.controller.callbacks.UpdateCallback;
import org.eclipse.emf.emfstore.client.model.exceptions.ChangeConflictException;
import org.eclipse.emf.emfstore.common.model.api.IModelElementId;
import org.eclipse.emf.emfstore.server.exceptions.EmfStoreException;
import org.eclipse.emf.emfstore.server.model.api.IBranchInfo;
import org.eclipse.emf.emfstore.server.model.api.IBranchVersionSpec;
import org.eclipse.emf.emfstore.server.model.api.IHistoryQuery;
import org.eclipse.emf.emfstore.server.model.api.IPrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.api.IProjectId;
import org.eclipse.emf.emfstore.server.model.api.IProjectInfo;
import org.eclipse.emf.emfstore.server.model.api.ITagVersionSpec;
import org.eclipse.emf.emfstore.server.model.api.IVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.server.model.versioning.operations.AbstractOperation;

public interface IProject {

	/**
	 * Adds a tag to the specified version of this project.
	 * 
	 * @param versionSpec
	 *            the versionSpec
	 * @param tag
	 *            the tag
	 * @throws EmfStoreException
	 *             if exception occurs on the server
	 * 
	 * @generated NOT
	 */
	void addTag(IPrimaryVersionSpec versionSpec, ITagVersionSpec tag) throws EmfStoreException;

	/**
	 * Commits all pending changes of the project space.
	 * 
	 * @throws EmfStoreException
	 *             in case the commit went wrong
	 * 
	 * @return the current version spec
	 **/
	PrimaryVersionSpec commit() throws EmfStoreException;

	IPrimaryVersionSpec commit(ILogMessage logMessage, CommitCallback callback, IProgressMonitor monitor)
		throws EmfStoreException;

	IPrimaryVersionSpec commitToBranch(IBranchVersionSpec branch, ILogMessage logMessage, CommitCallback callback,
		IProgressMonitor monitor) throws EmfStoreException;

	void mergeBranch(IPrimaryVersionSpec branchSpec, ConflictResolver conflictResolver) throws EmfStoreException;

	List<? extends IBranchInfo> getBranches() throws EmfStoreException;

	IPrimaryVersionSpec getBaseVersion();

	List<IHistoryInfo> getHistoryInfo(IHistoryQuery query) throws EmfStoreException;

	Date getLastUpdated();

	EList<String> getOldLogMessages();

	List<AbstractOperation> getOperations();

	String getProjectDescription();

	IProjectId getProjectId();

	IProjectInfo getProjectInfo();

	String getProjectName();

	IUsersession getUsersession();

	void delete() throws IOException;

	boolean isDirty();

	boolean isUpdated() throws EmfStoreException;

	boolean merge(IPrimaryVersionSpec target, ChangeConflictException conflictException,
		ConflictResolver conflictResolver, UpdateCallback callback, IProgressMonitor progressMonitor)
		throws EmfStoreException;

	void removeTag(IPrimaryVersionSpec versionSpec, ITagVersionSpec tag) throws EmfStoreException;

	IPrimaryVersionSpec resolveVersionSpec(IVersionSpec versionSpec) throws EmfStoreException;

	void revert();

	void setProjectDescription(String value);

	void setProjectName(String value);

	void shareProject() throws EmfStoreException;

	void shareProject(IUsersession session, IProgressMonitor monitor) throws EmfStoreException;

	void undoLastOperation();

	void undoLastOperations(int nrOperations);

	IPrimaryVersionSpec update() throws EmfStoreException;

	IPrimaryVersionSpec update(IVersionSpec version) throws EmfStoreException;

	IPrimaryVersionSpec update(IVersionSpec version, UpdateCallback callback, IProgressMonitor progress)
		throws EmfStoreException;

	boolean hasUnsavedChanges();

	void save();

	boolean isShared();

	boolean contains(IModelElementId modelElementId);

	// TOOD: parameter type
	boolean contains(EObject object);

	EObject get(IModelElementId modelElementId);

	IModelElementId getModelElementId(EObject eObject);

	abstract Collection<EObject> getModelElements();

	abstract Collection<EObject> getCutElements();

	Set<EObject> getAllModelElements();

	<T extends EObject> EList<T> getAllModelElementsbyClass(Class modelElementClass);

	<T extends EObject> EList<T> getAllModelElementsbyClass(Class modelElementClass, Boolean subclasses);

	void importChanges(InputStream inputStream, IProgressMonitor monitor);
}
