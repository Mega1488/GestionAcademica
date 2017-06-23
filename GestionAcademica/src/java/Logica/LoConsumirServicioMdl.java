/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.Parametro;
import Enumerado.ServiciosMoodle;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import static javax.servlet.SessionTrackingMode.URL;

/**
 *
 * @author alvar
 */
public class LoConsumirServicioMdl {
    private LoParametro loParam;
    private Parametro parametro = new Parametro();
    private static LoConsumirServicioMdl instancia;
    
    private LoConsumirServicioMdl() {
        loParam     = LoParametro.GetInstancia();
        parametro   = loParam.obtener(1);
    }
    
    public static LoConsumirServicioMdl GetInstancia(){
        if (instancia==null)
        {
            instancia   = new LoConsumirServicioMdl();
        }

        return instancia;
    }
    
    public void ProbarServicio(){
        /// NEED TO BE CHANGED
        String token        = parametro.getParMdlTkn();
        String domainName   = parametro.getParUrlMdl();

        /// REST RETURNED VALUES FORMAT
        String restformat = "xml"; //Also possible in Moodle 2.2 and later: 'json'
                                   //Setting it to 'json' will fail all calls on earlier Moodle version
        if (restformat.equals("json")) {
            restformat = "&moodlewsrestformat=" + restformat;
        } else {
            restformat = "";
        }

        try{
            
        /// PARAMETERS - NEED TO BE CHANGED IF YOU CALL A DIFFERENT FUNCTION
        String functionName = "core_user_update_users";

        String urlParameters =
        "users[0][id]=" + URLEncoder.encode("7", "UTF-8") +
        "&users[0][firstname]=" + URLEncoder.encode("ayer", "UTF-8");
        


        /// REST CALL

        // Send request
        String serverurl = domainName + "/webservice/rest/server.php" + "?wstoken=" + token + "&wsfunction=" + functionName;
        HttpURLConnection con = (HttpURLConnection) new URL(serverurl).openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type",
           "application/x-www-form-urlencoded");
        con.setRequestProperty("Content-Language", "en-US");
        con.setDoOutput(true);
        con.setUseCaches (false);
        con.setDoInput(true);
        DataOutputStream wr = new DataOutputStream (
                  con.getOutputStream ());
        wr.writeBytes (urlParameters);
        wr.flush ();
        wr.close ();

        //Get Response
        InputStream is =con.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuilder response = new StringBuilder();
        while((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        rd.close();
        
        System.out.println(response.toString());
        
        }
        catch(Exception ex)
        {
          ex.printStackTrace();
        }
    }
    
    
    public String ConsumirServicio(ServiciosMoodle servicio, String parmEntrada){
        String retorno = "";
        
        
        /// NEED TO BE CHANGED
        String token        = parametro.getParMdlTkn();
        String domainName   = parametro.getParUrlMdl();

        /// REST RETURNED VALUES FORMAT
        String restformat = "xml"; //Also possible in Moodle 2.2 and later: 'json'
                                   //Setting it to 'json' will fail all calls on earlier Moodle version
        if (restformat.equals("json")) {
            restformat = "&moodlewsrestformat=" + restformat;
        } else {
            restformat = "";
        }

        try{
            // Send request
            String serverurl        = domainName + "/webservice/rest/server.php" + "?wstoken=" + token + "&wsfunction=" + servicio.getValor();
            HttpURLConnection con   = (HttpURLConnection) new URL(serverurl).openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("Content-Language", "en-US");
            con.setDoOutput(true);
            con.setUseCaches (false);
            con.setDoInput(true);

            try
            {
                try (DataOutputStream wr = new DataOutputStream (con.getOutputStream ())) {
                    wr.writeBytes(parmEntrada);
                    wr.flush ();
                }

                //Get Response
                InputStream is      = con.getInputStream();
                StringBuilder response;
                try (BufferedReader rd = new BufferedReader(new InputStreamReader(is))) {
                    String line;
                    response = new StringBuilder();
                    while((line = rd.readLine()) != null) {
                        response.append(line);
                        //response.append('\r');
                    }
                }
               retorno = response.toString();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        catch(Exception ex)
        {
          ex.printStackTrace();
        }
        
        
        return retorno;
    }
    
}
