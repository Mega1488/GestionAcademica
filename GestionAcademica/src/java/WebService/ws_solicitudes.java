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
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author aa
 */
@WebService(serviceName = "ws_solicitudes")
public class ws_solicitudes {

    /**
     * This is a sample web service operation
     * @param token
     * @param SolTpo
     * @param AluPerCod
     * @return 
     */
    @WebMethod(operationName = "realizarSolicitud")
    public Retorno_MsgObj realizarSolicitud(@WebParam(name = "token") String token, @WebParam(name = "SolTpo") int SolTpo, @WebParam(name = "AluPerCod") Long AluPerCod)
    {
        Solicitud sol = new Solicitud();
        Retorno_MsgObj retPer = new Retorno_MsgObj();
        Retorno_MsgObj retorno = new Retorno_MsgObj();
        LoSolicitud loSolicitud = LoSolicitud.GetInstancia();
        LoPersona loPersona = LoPersona.GetInstancia();
        
        if(token == null)
        {
            retorno.setObjeto(new Mensajes("No se recibió Token", TipoMensaje.ERROR));
        }
        else
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
     * @param token
     * @param PerCod
     * @return 
     */
    @WebMethod(operationName = "lstSolicitudesActivas")
    public Retorno_MsgObj lstSolicitudesActivas(@WebParam(name = "token") String token, @WebParam(name = "PerCod") Long PerCod)
    {
        Retorno_MsgObj retorno = new Retorno_MsgObj();
        Retorno_MsgObj ret = new Retorno_MsgObj();
        List<Object> lstObject = new ArrayList<>();
        List<Object> lstSolicitud = new ArrayList<>();
        LoSolicitud loSolicitud = LoSolicitud.GetInstancia();
        
        if(token == null)
        {
            retorno.setObjeto(new Mensajes("No se recibió Token", TipoMensaje.ERROR));
        }
        else
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
     */
    @WebMethod(operationName = "lstSolicitudesFinalizadas")
    public Retorno_MsgObj lstSolicitudesFinalizadas(@WebParam(name = "token") String token)
    {
        Retorno_MsgObj retorno = new Retorno_MsgObj();
        Retorno_MsgObj ret = new Retorno_MsgObj();
        List<Object> lstObject = new ArrayList<>();
        List<Object> lstSolicitud = new ArrayList<>();
        LoSolicitud loSolicitud = LoSolicitud.GetInstancia(); 
        
        if(token == null)
        {
            retorno.setObjeto(new Mensajes("No se recibió Token", TipoMensaje.ERROR));
        }
        else
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
}
