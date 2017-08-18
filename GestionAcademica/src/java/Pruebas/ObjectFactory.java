
package Pruebas;

import WebServiceClient.*;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the WebServiceClient package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SincronizarResponse_QNAME = new QName("http://WebService/", "sincronizarResponse");
    private final static QName _UpdateFechaResponse_QNAME = new QName("http://WebService/", "update_fechaResponse");
    private final static QName _Sincronizar_QNAME = new QName("http://WebService/", "sincronizar");
    private final static QName _UpdateFecha_QNAME = new QName("http://WebService/", "update_fecha");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: WebServiceClient
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Sincronizar }
     * 
     */
    public Sincronizar createSincronizar() {
        return new Sincronizar();
    }

    /**
     * Create an instance of {@link UpdateFecha }
     * 
     */
    public UpdateFecha createUpdateFecha() {
        return new UpdateFecha();
    }

    /**
     * Create an instance of {@link UpdateFechaResponse }
     * 
     */
    public UpdateFechaResponse createUpdateFechaResponse() {
        return new UpdateFechaResponse();
    }

    /**
     * Create an instance of {@link SincronizarResponse }
     * 
     */
    public SincronizarResponse createSincronizarResponse() {
        return new SincronizarResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SincronizarResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://WebService/", name = "sincronizarResponse")
    public JAXBElement<SincronizarResponse> createSincronizarResponse(SincronizarResponse value) {
        return new JAXBElement<SincronizarResponse>(_SincronizarResponse_QNAME, SincronizarResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateFechaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://WebService/", name = "update_fechaResponse")
    public JAXBElement<UpdateFechaResponse> createUpdateFechaResponse(UpdateFechaResponse value) {
        return new JAXBElement<UpdateFechaResponse>(_UpdateFechaResponse_QNAME, UpdateFechaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Sincronizar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://WebService/", name = "sincronizar")
    public JAXBElement<Sincronizar> createSincronizar(Sincronizar value) {
        return new JAXBElement<Sincronizar>(_Sincronizar_QNAME, Sincronizar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateFecha }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://WebService/", name = "update_fecha")
    public JAXBElement<UpdateFecha> createUpdateFecha(UpdateFecha value) {
        return new JAXBElement<UpdateFecha>(_UpdateFecha_QNAME, UpdateFecha.class, null, value);
    }

}
