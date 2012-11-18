/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.common.observer;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ProxyObserver, returned by the {@link ObserverBus} when calling {@link ObserverBus#notify(Class))}, normally it has
 * the type of the class extending IObserver in order to call the observer interface with type safety.
 * However, the same proxies can be casted into {@link ObserverCall} in order to access the results by all registered
 * observers.
 * 
 * @author wesendon
 * 
 */
public interface ObserverCall {

	/**
	 * Returns the results of each notified observer. This method will always return the returns from the last call
	 * executed on the proxy.
	 * 
	 * @return list of results
	 */
	List<Result> getObserverCallResults();

	/**
	 * This class represents an result from an observer call. It contains the observer, the called method and the result
	 * or an throwable, if occurred.
	 * 
	 * @author wesendon
	 */
	class Result {

		private final IObserver observer;
		private final Method method;
		private final Object result;
		private final Throwable throwable;

		/**
		 * This constructor is used if <b>NO</b> throwable occurred.
		 * 
		 * @param observer observer
		 * @param method method
		 * @param result result
		 */
		public Result(IObserver observer, Method method, Object result) {
			this.observer = observer;
			this.method = method;
			this.result = result;
			this.throwable = null;
		}

		/**
		 * This constructor is used if an throwable <b>HAS</b> occurred.
		 * 
		 * @param observer observer
		 * @param throwable throwable
		 * @param method method
		 */
		public Result(IObserver observer, Method method, Throwable throwable) {
			this.observer = observer;
			this.method = method;
			this.result = null;
			this.throwable = throwable;
		}

		/**
		 * Specifies whether this Result contains an throwable.
		 * 
		 * @return boolean
		 */
		public boolean exceptionOccurred() {
			return throwable != null;
		}

		/**
		 * .
		 * 
		 * @return throwable or null
		 */
		public Throwable getException() {
			return throwable;
		}

		/**
		 * The observer.
		 * 
		 * @return this can't be null
		 */
		public IObserver getObserver() {
			return this.observer;
		}

		/**
		 * The result.
		 * 
		 * @return the result or null
		 */
		public Object getResult() {
			return this.result;
		}

		/**
		 * Returns the result or the default value for primitive types.
		 * 
		 * @return result, null or in case of primitive type, the default value.
		 */
		public Object getResultOrDefaultValue() {
			Object result = getResult();
			if (result == null) {
				result = getDefaultValue(this.method);
			}
			return result;
		}

		/**
		 * Returns the default value for a given method, which is null or the default primitive value.
		 * 
		 * @param method method
		 * @return null or default primitive value
		 */
		public static Object getDefaultValue(Method method) {
			if (method.getReturnType().isPrimitive()) {
				return DEFAULT_VALUES.get(method.getReturnType().getSimpleName());
			}
			return null;
		}

		private static final Map<String, Object> DEFAULT_VALUES = new HashMap<String, Object>();
		static {
			DEFAULT_VALUES.put("int", Integer.valueOf(0));
			DEFAULT_VALUES.put("boolean", Boolean.FALSE);
			DEFAULT_VALUES.put("long", Long.valueOf(0));
			DEFAULT_VALUES.put("float", new Float(0));
			DEFAULT_VALUES.put("double", new Double(0));
			DEFAULT_VALUES.put("byte", Byte.MIN_VALUE);
			DEFAULT_VALUES.put("short", Short.MIN_VALUE);
		}
	}
}