/*******************************************************************************
 * Copyright (c) 2013 EclipseSource Muenchen GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Otto von Wesendonk
 * Edgar Mueller
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.common;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.emfstore.internal.common.api.APIDelegate;
import org.eclipse.emf.emfstore.internal.common.api.InternalAPIDelegator;

/**
 * Convenience class for mapping internal and external types contained in lists onto each other.
 * 
 * @author ovonwesen 
 * @author emueller
 * @author eneufeld
 */
public class APIUtil {

	private APIUtil(){
	}

	/**
	 * Copies the given list.
	 * 
	 * @param toCopy
	 * 			the list to be copied
	 * @return the copied list
	 * 
	 * @param <T> the type of the instances the list is holding
	 */
	public static <T> List<T> copy(List<T> toCopy) {
		
		if(toCopy == null) {
			return null;
		}
		
		ArrayList<T> result = new ArrayList<T>(toCopy.size());
		
		for(T element : toCopy) {
			result.add(element);
		}
		
		return result;
	}
	
	public static <IMPL extends InternalAPIDelegator<API, INT>, INT extends APIDelegate<API>, API> 
	List<INT> toInternal(List<API> toCopy) {
		
		if(toCopy == null) {
			return null;
		}
		
		ArrayList<INT> result = new ArrayList<INT>(toCopy.size());
		
		for(API element : toCopy) {
			@SuppressWarnings("unchecked")
			IMPL i = (IMPL) element;
			result.add(i.toInternalAPI());
		}
		
		return result;
	}
	
	public static <IMPL extends InternalAPIDelegator<API, INT>, INT extends APIDelegate<API>, API, DESIRED> 
	List<INT> toInternal(Class<DESIRED> cls, List<API> toCopy) {
		
		if(toCopy == null) {
			return null;
		}
		
		ArrayList<INT> result = new ArrayList<INT>(toCopy.size());
		
		for(API element : toCopy) {
			@SuppressWarnings("unchecked")
			IMPL i = (IMPL) element;
			result.add(i.toInternalAPI());
		}
		
		return result;
	}
	
	public static <IMPL extends InternalAPIDelegator<API, INT>, INT extends APIDelegate<API>, API> 
	List<API> toExternal(List<INT> toCopy) {
		
		if(toCopy == null) {
			return null;
		}
		
		ArrayList<API> result = new ArrayList<API>(toCopy.size());
		
		for(INT element : toCopy) {
			result.add(element.toAPI());
		}
		
		return result;
	}
	
	public static <IMPL extends InternalAPIDelegator<API, INT>, INT extends APIDelegate<API>, API> 
	Set<API> toExternal(Set<INT> toCopy) {
		
		if(toCopy == null) {
			return null;
		}
		
		LinkedHashSet<API> result = new LinkedHashSet<API>(toCopy.size());
		
		for(INT element : toCopy) {
			result.add(element.toAPI());
		}
		
		return result;
	}

	/**
	 * Maps a list of a given internal type to its corresponding API type by copying it. 
	 * 
	 * @param toCopy
	 * 			the list to be copied
	 * @return the copied list with its elements mapped to their API type 
	 * 
	 * @param <T> the internal API mapping to an API implementation class
	 * @param <V> the type of the API interface
	 * @param <U> the type of the API implementation class
	 */
	// e.g.
	// T = Usersession
	// U = ESUsersessionImpl
	// V = ESUsersession
	public static <T extends APIDelegate<U>, V, U extends V> List<V> mapToAPI(Class<V> apiClass, List<T> toCopy) {
		
		if(toCopy == null) {
			return null;
		}
		
		ArrayList<V> result = new ArrayList<V>(toCopy.size());
		
		for(T element : toCopy) {
			result.add(element.toAPI());
		}
		
		return result;
	}
	
	/**
	 * Maps a list of a given external API  type to its corresponding internal type by copying it. 
	 * 
	 * @param toCopy
	 * 			the list to be copied
	 * @return the copied list with its elements mapped to their API type 
	 * 
	 * @param <T> the internal API mapping to an API implementation class
	 * @param <V> the type of the API interface
	 * @param <U> the type of the API implementation class
	 */
	// e.g.
	// T = Usersession
	// U = ESUsersessionImpl
	// V = ESUsersession
	@SuppressWarnings("unchecked")
	public static <V, U extends InternalAPIDelegator<U,T>,T extends APIDelegate<U>> List<T> mapToInternalAPI(Class<T> apiClass, List<V> toCopy) {
		
		if (toCopy == null) {
			return null;
		}
		
		ArrayList<T> result = new ArrayList<T>(toCopy.size());
		
		for(V element : toCopy) {
			result.add(((U)element).toInternalAPI());
		}
		
		return result;
	}

	public static <IMPL extends InternalAPIDelegator<API, INT>, INT extends APIDelegate<API>, API> 
	Set<INT> toInternal(Set<API> toCopy) {
		
		if(toCopy == null) {
			return null;
		}
		
		Set<INT> result = new LinkedHashSet<INT>(toCopy.size());
		
		for(API element : toCopy) {
			@SuppressWarnings("unchecked")
			IMPL i = (IMPL) element;
			result.add(i.toInternalAPI());
		}
		
		return result;
	}

}
