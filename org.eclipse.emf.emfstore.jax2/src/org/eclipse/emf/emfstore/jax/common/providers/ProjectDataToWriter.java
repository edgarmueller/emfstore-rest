package org.eclipse.emf.emfstore.jax.common.providers;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.eclipse.emf.emfstore.jax.common.ProjectDataTO;

@Provider
@Produces({ MediaType.TEXT_XML })
public class ProjectDataToWriter implements MessageBodyWriter<ProjectDataTO> {

	public boolean isWriteable(Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		// TODO Auto-generated method stub
		return false;
	}

	public long getSize(ProjectDataTO t, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		// deprecated since Jersey 2.0
		return 0;
	}

	public void writeTo(ProjectDataTO t, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException,
			WebApplicationException {
		// TODO Auto-generated method stub
		
	}

}
