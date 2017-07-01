/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utiles;

import Entidad.Parametro;
import Entidad.Version;
import Enumerado.ExpresionesRegulares;
import Enumerado.TipoAutenticacion;
import Enumerado.TipoDato;
import Enumerado.TipoSSL;
import Logica.LoParametro;
import Logica.LoVersion;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.ObjectMapper;


/**
 *
 * @author alvar
 */
public class Utilidades {
    private static Utilidades instancia;
    private final LoParametro loParam;
    private final LoVersion loVersion;

    private Utilidades() {
        loParam     = LoParametro.GetInstancia();
        loVersion   = LoVersion.GetInstancia();
    }
    
    public static Utilidades GetInstancia(){
        if (instancia == null)
        {
            instancia = new Utilidades();
        }
        
        return instancia;
    }
    
    public void MostrarMensajeConsola(String TAG, String Msg){
        System.err.println(TAG + " ---> " + Msg);
    }
    
    
    public String ObjetoToJson(Object objeto)
    {
        String retorno = "";
        ObjectMapper mapper = new ObjectMapper();

        try {
            // convert user object to json string and return it 
            retorno = mapper.writeValueAsString(objeto);
        }

          // catch various errors
          catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(Utilidades.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return retorno;
    }
    
    public Object JsonToObject(String jsonValue)
    {
        
        ObjectMapper mapper = new ObjectMapper();
        
        try {
            // convert user object to json string and return it 
            return mapper.readValue(jsonValue, Object.class);
        }

          // catch various errors
          catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(Utilidades.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    public String GetUrlSistema(){
        Parametro param = loParam.obtener(1);
        
        return param.getParUrlSis();
    }
    
    public Boolean ValidarTipoDato(TipoDato tipoDato, String valor)
    {
        
        boolean resultado = false;
        
        switch(tipoDato)
        {
            case BOOLEAN:
                resultado = valor.matches(ExpresionesRegulares.BOOLEAN.getValor());
                break;
            
            case NUMERO_ENTERO:
                resultado = valor.matches(ExpresionesRegulares.NUMERO_ENTERO.getValor());
                break;
            
            case TIPO_AUTENTICACION:
                resultado = ValidarTipoAutenticacion(valor);
                break;
                
            case TIPO_SSL:
                resultado = ValidarTipoSSL(valor);
                break;
        }

        return resultado;
    }
   
    private Boolean ValidarTipoAutenticacion(String valor){
        
        System.err.println("Valor: " + valor);
        for(TipoAutenticacion tpoAut : TipoAutenticacion.values())
        {
            if(tpoAut.getValor() == Integer.valueOf(valor))
            {
                return true;
            }
        }
        return false;
    }
    
    private Boolean ValidarTipoSSL(String valor){
  
        for(TipoSSL tpoSSL : TipoSSL.values())
        {
            if(tpoSSL.getValor() == Integer.valueOf(valor))
            {
                return true;
            }
        }
        return false;
    }
    
    public TipoSSL StringToTipoSSL(String valor)
    {
        for(TipoSSL tpoSSL : TipoSSL.values())
        {
            if(tpoSSL.getValor() == Integer.valueOf(valor))
            {
                return tpoSSL;
            }
        }
        return null;
    }
    
     public TipoAutenticacion StringToTipoAutenticacion(String valor)
    {
        for(TipoAutenticacion tpoAut : TipoAutenticacion.values())
        {
            if(tpoAut.getValor() == Integer.valueOf(valor))
            {
                return tpoAut;
            }
        }
        return null;
    }
     
     public Object NuloToVacio(Object objeto)
     {
         return (objeto == null ? "" : objeto);
     }
     
     public Object NuloToCero(Object objeto)
     {
         return (objeto == null ? "0" : objeto);
     }
     
     public Object BooleanToChecked(Boolean objeto)
     {
         return (objeto == null ? "" : (objeto == true ? "checked" : ""));
     }
     
     public Object BooleanToSiNo(Boolean objeto)
     {
         return (objeto == null ? "No" : (objeto == true ? "Si" : "No"));
     }
     
            
}
