
package de.uniba.rz.backend;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.3.2
 * Generated source version: 2.2
 * 
 */
@WebService(name = "JaxWsInterface", targetNamespace = "http://backend.rz.uniba.de/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface JaxWsInterface {


    /**
     * 
     * @param arg0
     * @return
     *     returns de.uniba.rz.backend.Message
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "sendMessage", targetNamespace = "http://backend.rz.uniba.de/", className = "de.uniba.rz.backend.SendMessage")
    @ResponseWrapper(localName = "sendMessageResponse", targetNamespace = "http://backend.rz.uniba.de/", className = "de.uniba.rz.backend.SendMessageResponse")
    @Action(input = "http://backend.rz.uniba.de/JaxWsInterface/sendMessageRequest", output = "http://backend.rz.uniba.de/JaxWsInterface/sendMessageResponse")
    public Message sendMessage(
        @WebParam(name = "arg0", targetNamespace = "")
        Message arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns de.uniba.rz.backend.Message
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "searchMessage", targetNamespace = "http://backend.rz.uniba.de/", className = "de.uniba.rz.backend.SearchMessage")
    @ResponseWrapper(localName = "searchMessageResponse", targetNamespace = "http://backend.rz.uniba.de/", className = "de.uniba.rz.backend.SearchMessageResponse")
    @Action(input = "http://backend.rz.uniba.de/JaxWsInterface/searchMessageRequest", output = "http://backend.rz.uniba.de/JaxWsInterface/searchMessageResponse")
    public Message searchMessage(
        @WebParam(name = "arg0", targetNamespace = "")
        TicketSearch arg0);

}