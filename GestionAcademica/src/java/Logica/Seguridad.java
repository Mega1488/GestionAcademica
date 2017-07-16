/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Dominio.Sitios;
import Entidad.Persona;
import Enumerado.Accion;
import Enumerado.TipoMensaje;
import Utiles.Mensajes;
import Utiles.Retorno_MsgObj;
import Utiles.Utilidades;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.tomcat.util.codec.binary.Base64;

/**
 *
 * @author alvar
 */
public class Seguridad {
    
    private static Seguridad instancia;

    private Seguridad() {
    }
    
    public static Seguridad GetInstancia(){
        if (instancia==null)
        {
            instancia   = new Seguridad();
            
        }

        return instancia;
    }
    
    public String md5(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");

        byte[] messageDigest = md.digest(input.getBytes());
        BigInteger number = new BigInteger(1, messageDigest);
        return number.toString(16);
    }

    public String decrypt(String encryptedData, String initialVectorString, String secretKey) {
        String decryptedData = null;
        try {
            SecretKeySpec skeySpec          = new SecretKeySpec(secretKey.getBytes(), "AES");
            IvParameterSpec initialVector   = new IvParameterSpec(initialVectorString.getBytes());
            Cipher cipher                   = Cipher.getInstance("AES/CFB8/NoPadding");

            cipher.init(Cipher.DECRYPT_MODE, skeySpec, initialVector);

            byte[] encryptedByteArray = (new Base64()).decode(encryptedData.getBytes());
            byte[] decryptedByteArray = cipher.doFinal(encryptedByteArray);

            decryptedData = new String(decryptedByteArray, "UTF8");

        } catch (Exception e) {

            e.printStackTrace();
            //System.err.println("Problem decrypting the data" + e.getMessage());
        }
        return decryptedData;
    }

    public String cryptWithMD5(String pass){
       MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] passBytes = pass.getBytes();
            md.reset();
            byte[] digested = md.digest(passBytes);
            StringBuffer sb = new StringBuffer();
            for(int i=0;i<digested.length;i++){
                sb.append(Integer.toHexString(0xff & digested[i]));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Seguridad.class.getName()).log(Level.SEVERE, null, ex);
        }
            return null;


       }
   
    private boolean PermisoSitio(String sitioActual, Boolean esAdm, Boolean esDoc, Boolean esAlu)
    {
        if(Sitios.GetInstancia().getLstSinSeguridad().contains(sitioActual)) return true;
            
        if(esAdm) return true;
        
        if(esDoc)
        {
            return Sitios.GetInstancia().getLstDocente().contains(sitioActual);
        }
        
        if(esAlu)
        {
            return Sitios.GetInstancia().getLstAlumno().contains(sitioActual);
        }
        
        return false;
    }
    
    public Retorno_MsgObj ControlarAcceso(String usuario, Boolean esAdm, Boolean esDoc, Boolean esAlu, String sitioActual)
    {
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error", TipoMensaje.ERROR));
        
        esAdm = (esAdm == null ? false : esAdm);
        esAlu = (esAlu == null ? false : esAlu);
        esDoc = (esDoc == null ? false : esDoc);

        if(usuario == null )
        {
            if(!Sitios.GetInstancia().getLstSinSeguridad().contains(sitioActual))
            {
                String texto = "Debe iniciar sesion para ver esta página";
                texto = Utilidades.GetInstancia().GetUrlSistema() + "Error.jsp?pMensaje=" + Utilidades.GetInstancia().UrlEncode(texto);
                retorno.setObjeto(texto);
                return retorno;
            }
        }

        if (!Seguridad.GetInstancia().PermisoSitio(sitioActual, esAdm, esDoc, esAlu))
        {
            String texto = "No tiene acceso a está página";
            
            texto = Utilidades.GetInstancia().GetUrlSistema() + "Error.jsp?pMensaje=" + Utilidades.GetInstancia().UrlEncode(texto);
            retorno.setObjeto(texto);
            
        }
        else
        {
            retorno.setMensaje(new Mensajes("Ok", TipoMensaje.MENSAJE));
        }
        
        return retorno;

    }
   


}
