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
     * This is a sample web service operation
     * @param token token para validar consumo de servicio
     * @param pPerCod codigo de persona
     * @return objeto persona
     */
    @WebMethod(operationName = "ObtenerPersona")
    public Retorno_MsgObj ObtenerPersona(@WebParam(name = "token") String token, @WebParam(name = "pPerCod") Long pPerCod) {
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
                retorno = LoPersona.GetInstancia().obtener(Long.valueOf(pPerCod));
                
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
}
