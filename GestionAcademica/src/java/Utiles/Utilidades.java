/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utiles;

import Entidad.Parametro;
import Entidad.Version;
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

    public String GetUrlSistema(){
        Parametro param = loParam.obtener(1);
        
        return param.getParUrlSis();
    }
   
            
}
