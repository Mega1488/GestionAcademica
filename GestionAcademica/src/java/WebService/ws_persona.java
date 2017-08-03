/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;

import Entidad.Persona;
import Enumerado.TipoMensaje;
import Logica.LoCalendario;
import Logica.LoPersona;
import Utiles.Mensajes;
import Utiles.Retorno_MsgObj;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author alvar
 */
@WebService(serviceName = "ws_persona")
public class ws_persona {

    /**
     * Retorna usuario por codigo
     * @param token token para validar consumo de servicio
     * @param pPerCod codigo de persona
     * @return objeto persona
     */
    @WebMethod(operationName = "ObtenerPersonaByCod")
    public Retorno_MsgObj ObtenerPersonaByCod(@WebParam(name = "token") String token, @WebParam(name = "pPerCod") Long pPerCod) {
        //Retorno_MsgObj retorno = LoPersona.GetInstancia().obtenerByMdlUsr("alumno1");
        Retorno_MsgObj retorno = new Retorno_MsgObj();
        
        if(token == null)
        {
            retorno.setMensaje(new Mensajes("No se recibió token", TipoMensaje.ERROR));
        }
        else
        {
            if(pPerCod == null)
            {
                retorno.setMensaje(new Mensajes("No se recibió parametro", TipoMensaje.ERROR));
            }
            else
            {
                retorno = LoPersona.GetInstancia().obtener(pPerCod);
                
                //LIMPIO CONTRASEÑA, NO DEBE VIAJAR POR SERVICIO
                if(!retorno.SurgioErrorObjetoRequerido())
                {
                    Persona persona = (Persona) retorno.getObjeto();
                    persona.setPerPass(null);
                    
                    retorno.setObjeto(persona);
                }
            }
        }
        
        return retorno;
    }
    
    /**
     * Retorna usuario por codigo
     * @param token token para validar consumo de servicio
     * @param pUser codigo de usuario
     * @return objeto persona
     */
    @WebMethod(operationName = "ObtenerPersonaByUser")
    public Retorno_MsgObj ObtenerPersonaByUser(@WebParam(name = "token") String token, @WebParam(name = "pUser") String pUser) {
        Retorno_MsgObj retorno = new Retorno_MsgObj();
        
        if(token == null)
        {
            retorno.setMensaje(new Mensajes("No se recibió token", TipoMensaje.ERROR));
        }
        else
        {
            if(pUser == null)
            {
                retorno.setMensaje(new Mensajes("No se recibió parametro", TipoMensaje.ERROR));
            }
            else
            {
                retorno = LoPersona.GetInstancia().obtenerByMdlUsr(pUser);
                
                //LIMPIO CONTRASEÑA, NO DEBE VIAJAR POR SERVICIO
                if(!retorno.SurgioErrorObjetoRequerido())
                {
                    Persona persona = (Persona) retorno.getObjeto();
                    persona.setPerPass(null);
                    
                    retorno.setObjeto(persona);
                    
                    System.err.println("Persona: " + persona.toString());
                }
            }
        }
        
        return retorno;
    }

    /**
     * Actualizar datos de la persona
     * @param token token para validar consumo de servicio
     * @param pPerCod codigo de persona
     * @param PerAppTkn token de app
     * @return Resultado
     */
    @WebMethod(operationName = "PersonaActualizarToken")
    public Retorno_MsgObj PersonaActualizarToken(@WebParam(name = "token") String token, @WebParam(name = "pPerCod") Long pPerCod, @WebParam(name = "pPerAppTkn") String PerAppTkn) {
        //TODO write your implementation code here:
        
        Retorno_MsgObj retorno = new Retorno_MsgObj();
        
        if(pPerCod == null)
        {
            retorno.setMensaje(new Mensajes("No se recibio persona", TipoMensaje.ERROR));
        }
        else
        {
            if(PerAppTkn == null)
            {
                retorno.setMensaje(new Mensajes("No se recibio parametro", TipoMensaje.ERROR));
            }
            else
            {
                retorno = LoPersona.GetInstancia().ActualizarToken(pPerCod, PerAppTkn);
            }
        }
        
        retorno.setObjeto(null);
        
        return retorno;
    }



}
