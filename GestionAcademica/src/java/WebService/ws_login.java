/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;

import Enumerado.Constantes;
import Logica.LoPersona;
import Logica.Seguridad;
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
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "mdltoken") String txt) {
        return "Hello " + txt + " !";
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "Login")
    public String Login(@WebParam(name = "mdltoken") String mdltoken, @WebParam(name = "pUser") String pUser, @WebParam(name = "pPassword") String pPassword) {
        //TODO write your implementation code here:
        Boolean resultado   = false;
        
        System.err.println("mdltoken: " + mdltoken);
        System.err.println("Usuario: " + pUser);
        System.err.println("Contraseña: " + pPassword);
        
        Seguridad seguridad = Seguridad.GetInstancia();
        LoPersona loPersona = LoPersona.GetInstancia();
        
        String usuarioDecrypt   = seguridad.decrypt(pUser, Constantes.ENCRYPT_VECTOR_INICIO.getValor(), Constantes.ENCRYPT_SEMILLA.getValor());
        String passwordDecrypt  = seguridad.decrypt(pPassword, Constantes.ENCRYPT_VECTOR_INICIO.getValor(), Constantes.ENCRYPT_SEMILLA.getValor());
        
        resultado = loPersona.IniciarSesion(usuarioDecrypt, seguridad.cryptWithMD5(passwordDecrypt));
        
        String retorno  = resultado.toString();        
        
        System.err.println("Retorno: " + retorno);
        
        return retorno;
    }
    
  

}
