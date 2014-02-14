package org.eclipse.emf.emfstore.jax.common;

/**
 * class which contains static Strings for URIs, PathParams etc.
 * @author Pascal
 *
 */
public interface CallParamStrings {
	
	//Path param constants  //TODO: use these params + do create more!!!
	public static final String PROJECT_ID_PATH_PARAM = "projectId";
	
	//Base uri where the services are published
	public final static String BASE_URI = "http://localhost:9090/services";
	
	//path constants
	public final static String PROJECTS_PATH = "projects";
	public final static String BRANCHES_PATH = PROJECTS_PATH + "/" + "{" + PROJECT_ID_PATH_PARAM + "}" + "branches";
	
	
}
