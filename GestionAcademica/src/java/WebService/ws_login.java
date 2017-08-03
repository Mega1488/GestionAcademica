/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;

import Enumerado.Constantes;
import Enumerado.TipoMensaje;
import Logica.LoPersona;
import Logica.Seguridad;
import Utiles.Mensajes;
import Utiles.Retorno_MsgObj;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author alvar
 */
@WebService(serviceName = "ws_login")
public class ws_login {

   
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
        
        Retorno_MsgObj retorno  = new Retorno_MsgObj();
        Boolean resultado       = false;
        
        System.err.println("A");
                
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
                if(pPassword == null)
                {
                    retorno.setMensaje(new Mensajes("No se recibió parametro", TipoMensaje.ERROR));
                }
                else
                {
                    
                    System.err.println("B");
                    
                    Seguridad seguridad = Seguridad.GetInstancia();
                    LoPersona loPersona = LoPersona.GetInstancia();

                    System.err.println("C" + pUser);
                    
                    String usuarioDecrypt   = seguridad.decrypt(pUser, Constantes.ENCRYPT_VECTOR_INICIO.getValor(), Constantes.ENCRYPT_SEMILLA.getValor());
                    String passwordDecrypt  = seguridad.decrypt(pPassword, Constantes.ENCRYPT_VECTOR_INICIO.getValor(), Constantes.ENCRYPT_SEMILLA.getValor());

                    System.err.println("d");
                    
                    System.err.println("Usuario: " + usuarioDecrypt);
                    System.err.println("Password: " + usuarioDecrypt);
                    
                    resultado = loPersona.IniciarSesion(usuarioDecrypt, seguridad.cryptWithMD5(passwordDecrypt));

                    System.err.println("Resultado: " + resultado);
                }
            }
        }
        
        return resultado.toString();
    }
    
  

}
