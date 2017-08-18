/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;

import Enumerado.Constantes;
import Enumerado.EstadoServicio;
import Enumerado.ServicioWeb;
import Enumerado.TipoMensaje;
import Logica.LoPersona;
import Logica.LoWS;
import Logica.Seguridad;
import Utiles.Mensajes;
import Utiles.Retorno_MsgObj;
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
@WebService(serviceName = "ws_login")
public class ws_login {

    @Resource WebServiceContext context;
   
    /**
     * Inicia sesión
     * @param token token para validar servicio
     * @param pUser usuario
     * @param pPassword contraseña
     * @return 
     */
    @WebMethod(operationName = "Login")
    public String Login(@WebParam(name = "token") String token, @WebParam(name = "pUser") String pUser, @WebParam(name = "pPassword") String pPassword) {
        //TODO write your implementation code here:
        
        //OBTENER DIRECCION DE QUIEN LLAMA AL SERVICIO
        HttpServletRequest request = (HttpServletRequest)context.getMessageContext().get(MessageContext.SERVLET_REQUEST);
        String direccion           = "IP: "+request.getRemoteAddr()+", Port: "+request.getRemotePort()+", Host: "+request.getRemoteHost();
        
        Retorno_MsgObj retorno  = new Retorno_MsgObj();
        Boolean resultado       = false;
        
                
        if(token == null)
        {
            retorno.setMensaje(new Mensajes("No se recibió token", TipoMensaje.ERROR));
            LoWS.GetInstancia().GuardarMensajeBitacora(null, direccion + "\n Token invalido", EstadoServicio.CON_ERRORES, ServicioWeb.LOGIN);
        }
        else
        {
            if(!LoWS.GetInstancia().ValidarConsumo(token, ServicioWeb.LOGIN, direccion))
            {
                retorno.setMensaje(new Mensajes("Token invalido, no se puede consumir el servicio", TipoMensaje.ERROR));
            }
            else
            {
                if(pUser == null)
                {
                    retorno.setMensaje(new Mensajes("No se recibió parametro", TipoMensaje.ERROR));
                }
                else
                {
                    if(pPassword == null)
                    {
                        retorno.setMensaje(new Mensajes("No se recibió parametro", TipoMensaje.ERROR));
                    }
                    else
                    {


                        Seguridad seguridad = Seguridad.GetInstancia();
                        LoPersona loPersona = LoPersona.GetInstancia();


                        String usuarioDecrypt   = seguridad.decrypt(pUser, Constantes.ENCRYPT_VECTOR_INICIO.getValor(), Constantes.ENCRYPT_SEMILLA.getValor());
                        String passwordDecrypt  = seguridad.decrypt(pPassword, Constantes.ENCRYPT_VECTOR_INICIO.getValor(), Constantes.ENCRYPT_SEMILLA.getValor());

                        resultado = loPersona.IniciarSesion(usuarioDecrypt, seguridad.cryptWithMD5(passwordDecrypt));

                    }
                }
            }
        }
        
        return resultado.toString();
    }
    
  
    /**
     * Cierra sesión
     * @param token token para validar servicio
     * @param PerCod usuario
     * @return Resultado
     */
    @WebMethod(operationName = "Logout")
    public Retorno_MsgObj LogOut(@WebParam(name = "token") String token, @WebParam(name = "pPerCod") String PerCod) {
        //TODO write your implementation code here:
        
        //OBTENER DIRECCION DE QUIEN LLAMA AL SERVICIO
        HttpServletRequest request = (HttpServletRequest)context.getMessageContext().get(MessageContext.SERVLET_REQUEST);
        String direccion           = "IP: "+request.getRemoteAddr()+", Port: "+request.getRemotePort()+", Host: "+request.getRemoteHost();
        
        Retorno_MsgObj retorno  = new Retorno_MsgObj();
                
        if(token == null)
        {
            retorno.setMensaje(new Mensajes("No se recibió token", TipoMensaje.ERROR));
            LoWS.GetInstancia().GuardarMensajeBitacora(null, direccion + "\n Token invalido", EstadoServicio.CON_ERRORES, ServicioWeb.LOGIN);
        }
        else
        {
            if(!LoWS.GetInstancia().ValidarConsumo(token, ServicioWeb.LOGIN, direccion))
            {
                retorno.setMensaje(new Mensajes("Token invalido, no se puede consumir el servicio", TipoMensaje.ERROR));
            }
            else
            {
            
                if(PerCod == null)
                {
                    retorno.setMensaje(new Mensajes("No se recibió parametro", TipoMensaje.ERROR));
                }
                else
                {

                    Seguridad seguridad = Seguridad.GetInstancia();
                    LoPersona loPersona = LoPersona.GetInstancia();

                    String usuarioDecrypt   = seguridad.decrypt(PerCod, Constantes.ENCRYPT_VECTOR_INICIO.getValor(), Constantes.ENCRYPT_SEMILLA.getValor());

                    retorno = loPersona.LimpiarToken(Long.valueOf(usuarioDecrypt));

                }
            }
        }
        
        retorno.setObjeto(null);
        
        return retorno;
    }

}
