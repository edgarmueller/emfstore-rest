package org.eclipse.emf.emfstore.jax.common;

import java.util.HashMap;

import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.emfstore.internal.common.ResourceFactoryRegistry;
import org.eclipse.emf.emfstore.internal.common.model.util.ModelUtil;
import org.eclipse.emf.emfstore.internal.common.model.util.SerializationException;

/**
 * super type class for TransferObjects ("TO") with helper methods
 * @author Pascal
 *
 */
@XmlRootElement
public abstract class TO {
	
	/**
	 * URI used to serialize EObject with the model util.
	 */
	public static final URI VIRTUAL_URI = URI.createURI("virtualUri");
	
	protected TO() {
		
	}
	
	/**
	 * converts an EObject to a String as XML without beeing a complete XML-Document (i. e. no documentType header)
	 * @param object
	 * @return
	 * @throws SerializationException
	 */
	protected String serializeEObjectToString(EObject object) throws SerializationException {
		
		if (object == null) {
			return null;
		}

		//create a XMLResource and convert the eObject ot a String 
		final ResourceSetImpl resourceSetImpl = new ResourceSetImpl();
		resourceSetImpl.setResourceFactoryRegistry(new ResourceFactoryRegistry());
		final XMIResource res = (XMIResource) resourceSetImpl.createResource(VIRTUAL_URI);
		((ResourceImpl) res).setIntrinsicIDToEObjectMap(new HashMap<String, EObject>());
		
		String resultFullXmlDoc = ModelUtil.copiedEObjectToString(object, res);
		
		//remove the xml doc declaration
		String xmlDocDecl = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		int lastIndexOfXmlDocDeclaration = resultFullXmlDoc.lastIndexOf(xmlDocDecl);
		String result = resultFullXmlDoc.substring(lastIndexOfXmlDocDeclaration + xmlDocDecl.length() + 1).trim();
		
		//TODO: Remove debug println!
		System.out.println("\n\nProjectDataTO.serializeEObjectToString result:\n" + result + "\n\n");
		
		return result;
	}
	
	
	protected EObject deserializeStringToEObject(String eObjectString) throws SerializationException {
		
		return ModelUtil.stringToEObject(eObjectString);
	}
}
