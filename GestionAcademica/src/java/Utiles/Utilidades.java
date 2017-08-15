/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utiles;

import Entidad.Parametro;
import Enumerado.ExpresionesRegulares;
import Enumerado.TipoDato;
import Logica.LoParametro;
import Logica.LoVersion;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
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
        loParam = LoParametro.GetInstancia();
        loVersion = LoVersion.GetInstancia();

    }

    public static Utilidades GetInstancia() {
        if (instancia == null) {
            instancia = new Utilidades();
        }

        return instancia;
    }

    public void MostrarMensajeConsola(String TAG, String Msg) {
        System.err.println(TAG + " ---> " + Msg);
    }

    public void MostrarMensaje(Mensajes mensaje) {
        System.err.println(mensaje.getTipoMensaje() + ": " + mensaje.getMensaje());
    }

    public String ObjetoToJson(Object objeto) {
        String retorno = "";
        ObjectMapper mapper = new ObjectMapper();

        try {
            // convert user object to json string and return it 
            retorno = mapper.writeValueAsString(objeto);
        } // catch various errors
        catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(Utilidades.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retorno;
    }

    public Object JsonToObject(String jsonValue, Object unObj) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            // convert user object to json string and return it 
            return mapper.readValue(jsonValue, unObj.getClass());
        } // catch various errors
        catch (JsonGenerationException e) {
            Logger.getLogger(Utilidades.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException ex) {
            Logger.getLogger(Utilidades.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public List<Object> JsonToListObject(String jsonValue, Class clase) {
        List<Object> lstObjeto = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        try {
            // convert user object to json string and return it 
            //return mapper.readValue(listaCala, Object.class);
            lstObjeto = mapper.readValue(jsonValue, mapper.getTypeFactory().constructCollectionType(List.class, clase));
        } // catch various errors
        catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(Utilidades.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lstObjeto;
    }

    public String GetUrlSistema() {
        Parametro param = loParam.obtener();
        if (param != null) {
            return param.getParUrlSis();
        }

        return "";
    }

    public Boolean ValidarTipoDato(TipoDato tipoDato, String valor) {

        boolean resultado = false;

        switch (tipoDato) {
            case BOOLEAN:
                resultado = valor.matches(ExpresionesRegulares.BOOLEAN.getValor());
                break;

            case NUMERO_ENTERO:
                resultado = valor.matches(ExpresionesRegulares.NUMERO_ENTERO.getValor());
                break;
        }

        return resultado;
    }

    public Object NuloToVacio(Object objeto) {
        return (objeto == null ? "" : objeto);
    }

    public Object NuloToCero(Object objeto) {
        return (objeto == null ? "0" : objeto);
    }

    public Object BooleanToChecked(Boolean objeto) {
        return (objeto == null ? "" : (objeto == true ? "checked" : ""));
    }

    public Object BooleanToSiNo(Boolean objeto) {
        return (objeto == null ? "No" : (objeto == true ? "Si" : "No"));
    }

    public String GetPaginaActual(HttpServletRequest request) {
        String url = request.getRequestURL().toString();

        if (!url.isEmpty()) {
            url = url.substring(url.lastIndexOf("/") + 1, url.length());
        }

        return url;
    }

    public String UrlEncode(String texto) {
        String encodedUrl = null;

        try {
            encodedUrl = URLEncoder.encode(texto, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            // Can be safely ignored because UTF-8 is always supported
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }

        return encodedUrl;
    }

    public String QuitarTagHTML(String contenido) {
        contenido = contenido.replaceAll("\\<.*?>", "");

        return contenido;
    }

    public Date StringToDateTime(String texto) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

        Date fecha = null;
        try {
            fecha = dateFormat.parse(texto);
        } catch (ParseException ex) {
            Logger.getLogger(Utilidades.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fecha;
    }

    public Date StringToDate(String texto) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date fecha = null;
        try {
            fecha = dateFormat.parse(texto);
        } catch (ParseException ex) {
            Logger.getLogger(Utilidades.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fecha;

    }
}
