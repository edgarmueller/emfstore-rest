package org.eclipse.emf.emfstore.jax.common;

/**
 * class which contains static Strings for URIs, PathParams etc.
 * @author Pascal
 *
 */
public interface CallParamStrings {
	
	//Path param constants  //TODO: use these params + do create more!!!
	public static final String PROJECT_ID_PATH_PARAM = "projectId";
	public static final String BRANCH_ID_PATH_PARAM = "branchId";
	
	//Base uri where the services are published
	public final static String BASE_URI = "http://localhost:9090/services";
	
	//path constants
	public final static String PROJECTS_PATH = "projects";
	public final static String BRANCHES_PATH_BEFORE_PROJECTID = PROJECTS_PATH;
	public final static String BRANCHES_PATH_AFTER_PROJECTID = "branches";
	public final static String BRANCHES_PATH_COMPLETE = BRANCHES_PATH_BEFORE_PROJECTID + "/" + "{" + PROJECT_ID_PATH_PARAM + "}" + "/" + BRANCHES_PATH_AFTER_PROJECTID;
	public static final String BRANCHES_PATH_CHANGES = "changes";
	public static final String PROJECTS_PATH_CHANGES = "changes";

	
	
	
	
}
