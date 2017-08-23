/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;

import Entidad.Parametro;
import Enumerado.EstadoServicio;
import Enumerado.ServicioWeb;
import Enumerado.TipoMensaje;
import Logica.LoParametro;
import Logica.LoSincronizacion;
import Logica.LoWS;
import Utiles.Mensajes;
import Utiles.Retorno_MsgObj;
//import Utiles.SincRetorno;
import java.util.Date;
import javax.annotation.Resource;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

/**
 *
 * @author alvar
 */
@WebService(serviceName = "ws_Sincronizar")
public class ws_sincronizar {

    @Resource WebServiceContext context;
    
    /**
     * Servicio de sincronizacion
     * @param token token para validar consumo
     * @param cambios recibe los cambios a impactar
     * @return retorna los cambios nuevos, o las inconsistencias
     */
    @WebMethod(operationName = "sincronizar")
    public Retorno_MsgObj sincronizar(@WebParam(name = "token") String token, @WebParam(name = "cambios") Retorno_MsgObj cambios) {

        System.err.println("Consumiendo sincronizar");
        

        //OBTENER DIRECCION DE QUIEN LLAMA AL SERVICIO
        HttpServletRequest request = (HttpServletRequest) context.getMessageContext().get(MessageContext.SERVLET_REQUEST);
        String direccion           = "IP: "+request.getRemoteAddr()+", Port: "+request.getRemotePort()+", Host: "+request.getRemoteHost();
        
        Retorno_MsgObj retorno  = new Retorno_MsgObj();
                
        if(token == null)
        {
            retorno.setMensaje(new Mensajes("No se recibió token", TipoMensaje.ERROR));
            LoWS.GetInstancia().GuardarMensajeBitacora(null, direccion + "\n Token invalido", EstadoServicio.CON_ERRORES, ServicioWeb.SINCRONIZAR);
        }
        else
        {
            if(!LoWS.GetInstancia().ValidarConsumo(token, ServicioWeb.SINCRONIZAR, direccion))
            {
                retorno.setMensaje(new Mensajes("Token invalido, no se puede consumir el servicio", TipoMensaje.ERROR));
            }
            else
            {
                if(cambios == null)
                {
                    retorno.setMensaje(new Mensajes("No se recibieron cambios, objeto nulo", TipoMensaje.ERROR));
                }
                else
                {
                    retorno = LoSincronizacion.GetInstancia().Sincronizar(cambios);
                    //retorno.setMensaje(new Mensajes("Chacha", TipoMensaje.ERROR));
                }
            }
        }

        return retorno;
    }

    /**
     * Actualiza fecha de sincronizacion
     * @param token token para validar consumo
     * @param fecha fecha a impactar
     * @return retorna los cambios nuevos, o las inconsistencias
     */
    @WebMethod(operationName = "update_fecha")
    public Retorno_MsgObj update_fecha(@WebParam(name = "token") String token, @WebParam(name = "fecha") Date fecha) {
        
        System.err.println("Consumiendo update_fecha");

        //OBTENER DIRECCION DE QUIEN LLAMA AL SERVICIO
        HttpServletRequest request = (HttpServletRequest) context.getMessageContext().get(MessageContext.SERVLET_REQUEST);
        String direccion           = "IP: "+request.getRemoteAddr()+", Port: "+request.getRemotePort()+", Host: "+request.getRemoteHost();
        
        Retorno_MsgObj retorno  = new Retorno_MsgObj(new Mensajes("Actualizar fecha", TipoMensaje.MENSAJE));
                
        if(token == null)
        {
            retorno.setMensaje(new Mensajes("No se recibió token", TipoMensaje.ERROR));
            LoWS.GetInstancia().GuardarMensajeBitacora(null, direccion + "\n Token invalido", EstadoServicio.CON_ERRORES, ServicioWeb.SINCRONIZAR);
        }
        else
        {
            if(!LoWS.GetInstancia().ValidarConsumo(token, ServicioWeb.SINCRONIZAR, direccion))
            {
                retorno.setMensaje(new Mensajes("Token invalido, no se puede consumir el servicio", TipoMensaje.ERROR));
            }
            else
            {
                if(fecha == null)
                {
                    retorno.setMensaje(new Mensajes("No se recibio fecha, objeto nulo", TipoMensaje.ERROR));
                }
                else
                {
                    Parametro param = LoParametro.GetInstancia().obtener();
                    param.setParFchUltSinc(fecha);
                    LoParametro.GetInstancia().actualizar(param);
                    retorno.setMensaje(new Mensajes("Modificado ok", TipoMensaje.MENSAJE));

                }
            }
        }
        
        return retorno;
    }

    /**
     * Web service operation
     * @param token validar consumo de servicio
     * @param cambios recibe inconsistencias
     * @return retorna el resultado de la operacion 
     */
    @WebMethod(operationName = "impactar_inconsistencia")
    public Retorno_MsgObj impactar_inconsistencia(@WebParam(name = "token") String token, @WebParam(name = "cambios") Retorno_MsgObj cambios) {
        //OBTENER DIRECCION DE QUIEN LLAMA AL SERVICIO
        HttpServletRequest request = (HttpServletRequest) context.getMessageContext().get(MessageContext.SERVLET_REQUEST);
        String direccion           = "IP: "+request.getRemoteAddr()+", Port: "+request.getRemotePort()+", Host: "+request.getRemoteHost();
        
        Retorno_MsgObj retorno  = new Retorno_MsgObj();
                
        if(token == null)
        {
            retorno.setMensaje(new Mensajes("No se recibió token", TipoMensaje.ERROR));
            LoWS.GetInstancia().GuardarMensajeBitacora(null, direccion + "\n Token invalido", EstadoServicio.CON_ERRORES, ServicioWeb.SINCRONIZAR);
        }
        else
        {
            if(!LoWS.GetInstancia().ValidarConsumo(token, ServicioWeb.SINCRONIZAR, direccion))
            {
                retorno.setMensaje(new Mensajes("Token invalido, no se puede consumir el servicio", TipoMensaje.ERROR));
            }
            else
            {
                if(cambios == null)
                {
                    retorno.setMensaje(new Mensajes("No se recibieron cambios, objeto nulo", TipoMensaje.ERROR));
                }
                else
                {
                    if(cambios.getObjeto() != null)
                    {
                        retorno = LoSincronizacion.GetInstancia().ProcesarInconsistencia(cambios);
                    }
                }
            }
        }
        
        return retorno;
    }
}
