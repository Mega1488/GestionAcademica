/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;

import Entidad.Persona;
import Entidad.Solicitud;
import Enumerado.EstadoSolicitud;
import Enumerado.NombreSesiones;
import Enumerado.TipoMensaje;
import Enumerado.TipoSolicitud;
import Logica.LoPersona;
import Logica.LoSolicitud;
import Utiles.Mensajes;
import Utiles.Retorno_MsgObj;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import java.util.Map;
import Enumerado.EstadoServicio;
import Enumerado.ServicioWeb;
import Logica.LoWS;

/**
 *
 * @author aa
 */
@WebService(serviceName = "ws_solicitudes")
public class ws_solicitudes {
    
    @Resource WebServiceContext context;

    /**
     * This is a sample web service operation
     * @param SolTpo
     * @param AluPerCod
     * @return 
     */
    @WebMethod(operationName = "realizarSolicitud")
    public Retorno_MsgObj realizarSolicitud(@WebParam(name = "SolTpo") int SolTpo, @WebParam(name = "AluPerCod") Long AluPerCod)
    {
        Retorno_MsgObj retPer = this.isAuthenticated();
        Retorno_MsgObj retorno = this.isAuthenticated();
        
        Solicitud sol = new Solicitud();
        LoSolicitud loSolicitud = LoSolicitud.GetInstancia();
        LoPersona loPersona = LoPersona.GetInstancia();
        
        if(!retorno.SurgioError() || !retPer.SurgioError())
        {
            if(AluPerCod == null)
            {
                retorno.setObjeto(new Mensajes("No se recibió parametro Alumno", TipoMensaje.ERROR));
            }
            else
            {
                retPer = loPersona.obtener(AluPerCod);
                if(SolTpo <= 0)
                {
                    retorno.setObjeto(new Mensajes("No se recibió parametro TipoSolicitud", TipoMensaje.ERROR));
                }
                else
                {
                    switch(String.valueOf(SolTpo))
                    {
                        case "1":
                            sol.setSolTpo(TipoSolicitud.ESCOLARIDAD);
                            break;
                        case "2":
                            sol.setSolTpo(TipoSolicitud.CONSTANCIA_ESTUDIO);
                            break;
                        case "3":
                            sol.setSolTpo(TipoSolicitud.DUPLICADO_FACTURA);
                            break;
                    }
                    sol.setAlumno((Persona) retPer.getObjeto());

                    loSolicitud.guardar(sol);
                    retorno.setMensaje(new Mensajes("Solicitud enviada", TipoMensaje.MENSAJE));
                }
            }
        }
        return retorno;
    }
    
    /**
     * This is a sample web service operation
     * @param PerCod
     * @return 
     */
    @WebMethod(operationName = "lstSolicitudesActivas")
    public Retorno_MsgObj lstSolicitudesActivas(@WebParam(name = "PerCod") Long PerCod)
    {
        Retorno_MsgObj retorno = this.isAuthenticated();
        Retorno_MsgObj ret = this.isAuthenticated();
        
        List<Object> lstObject = new ArrayList<>();
        List<Object> lstSolicitud = new ArrayList<>();
        LoSolicitud loSolicitud = LoSolicitud.GetInstancia();
        
        if(!retorno.SurgioError() || !ret.SurgioError())
        {
            if(PerCod == null)
            {
                retorno.setObjeto(new Mensajes("No se recibió parametro Alumno", TipoMensaje.ERROR));
            }
            else
            {
                ret = loSolicitud.obtenerLista();
                if (!ret.SurgioErrorListaRequerida()) {
                    lstObject   = ret.getLstObjetos();
                    for(Object objeto : lstObject)
                    {
                        Solicitud sol = (Solicitud) objeto;
                        if( sol.getAlumno().getPerCod() == PerCod && (sol.getSolEst().equals(EstadoSolicitud.SIN_TOMAR) || sol.getSolEst().equals(EstadoSolicitud.TOMADA)))
                        {
                            lstSolicitud.add(sol);
                        }
                    }
                    retorno.setMensaje(new Mensajes("OK", TipoMensaje.MENSAJE));
                    retorno.setLstObjetos(lstSolicitud);
                }
                else
                {
                    retorno.setObjeto(new Mensajes("No se pudo obtener los Datos", TipoMensaje.ERROR));
                }
            }
        }
        
        return retorno;
    }
    
    /**
     * This is a sample web service operation
     * @return 
     */
    @WebMethod(operationName = "lstSolicitudesFinalizadas")
    public Retorno_MsgObj lstSolicitudesFinalizadas()
    {
        Retorno_MsgObj retorno = this.isAuthenticated();
        Retorno_MsgObj ret = this.isAuthenticated();
        
        List<Object> lstObject = new ArrayList<>();
        List<Object> lstSolicitud = new ArrayList<>();
        LoSolicitud loSolicitud = LoSolicitud.GetInstancia(); 
        
        
        if(!retorno.SurgioError() || !ret.SurgioError())
        {
            ret = loSolicitud.obtenerLista();
            if (!ret.SurgioErrorListaRequerida()) {
                lstObject   = ret.getLstObjetos();
                for(Object objeto : lstObject)
                {
                    Solicitud sol = (Solicitud) objeto;
                    if(sol.getSolEst().equals(EstadoSolicitud.FINALIZADA))
                    {
                        lstSolicitud.add(sol);
                    }
                }
                retorno.setLstObjetos(lstSolicitud);
            }
            else
            {
                retorno.setObjeto(new Mensajes("No se pudo obtener la lista de Solicitudes Activas", TipoMensaje.ERROR));
            }
        }
        return retorno;
    }
    
    private Retorno_MsgObj isAuthenticated() {
        
        Retorno_MsgObj retorno  = new Retorno_MsgObj(new Mensajes("Autenticando", TipoMensaje.ERROR));

        MessageContext messageContext = context.getMessageContext();
        HttpServletRequest request = (HttpServletRequest) messageContext.get(MessageContext.SERVLET_REQUEST);
        Map httpHeaders = (Map) messageContext.get(MessageContext.HTTP_REQUEST_HEADERS);
        
        String direccion           = "IP: "+request.getRemoteAddr()+", Port: "+request.getRemotePort()+", Host: "+request.getRemoteHost();

        List tknList = (List) httpHeaders.get("token");
        
        if (tknList != null)
        {
            if(tknList.size() > 0)
            {
                String token = (String) tknList.get(0);
                
                if(token == null)
                {
                    retorno.setMensaje(new Mensajes("No se recibió token", TipoMensaje.ERROR));
                    LoWS.GetInstancia().GuardarMensajeBitacora(null, direccion + "\n Token invalido", EstadoServicio.CON_ERRORES, ServicioWeb.SOLICITUDES);
                }
                else
                {
                    if(!LoWS.GetInstancia().ValidarConsumo(token, ServicioWeb.SOLICITUDES, direccion))
                    {
                        retorno.setMensaje(new Mensajes("Token invalido, no se puede consumir el servicio", TipoMensaje.ERROR));
                    }
                    else
                    {
                        retorno.setMensaje(new Mensajes("Token valido, puede consumir el servicio", TipoMensaje.MENSAJE));                        
                    }
                }
            }
        }
        else
        {
           retorno.setMensaje(new Mensajes("No se recibió token", TipoMensaje.ERROR));
           LoWS.GetInstancia().GuardarMensajeBitacora(null, direccion + "\n Token invalido", EstadoServicio.CON_ERRORES, ServicioWeb.SOLICITUDES); 
        }


        return retorno;

    }
}
