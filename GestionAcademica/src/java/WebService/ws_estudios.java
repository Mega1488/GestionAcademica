/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;

import Entidad.Escolaridad;
import Entidad.PeriodoEstudio;
import Entidad.PeriodoEstudioDocumento;
import Entidad.Persona;
import Enumerado.TipoMensaje;
import Logica.LoCalendario;
import Logica.LoPeriodo;
import Logica.LoPersona;
import SDT.SDT_PersonaEstudio;
import Utiles.Mensajes;
import Utiles.Retorno_MsgObj;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author aa
 */
@WebService(serviceName = "ws_estudios")
public class ws_estudios {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "lstEstudiosPorAlumno")
    public Retorno_MsgObj lstEstudiosPorAlumno(@WebParam(name = "token") String token, @WebParam(name = "PerCod") Long PerCod) 
    {
        Retorno_MsgObj retorno = new Retorno_MsgObj();
        LoPersona lopersona = LoPersona.GetInstancia();
        ArrayList<SDT_PersonaEstudio> lstEstudios = new ArrayList<>();
        
        if(token == null)
        {
            retorno.setMensaje(new Mensajes("No se recibió ningún valor token", TipoMensaje.ERROR));
        }
        else
        {
            if(PerCod == null)
            {
                retorno.setMensaje(new Mensajes("No se recibió ningún parametro Alumno", TipoMensaje.ERROR));
            }
            else
            {
                lstEstudios = lopersona.ObtenerEstudios(PerCod);
                for(SDT_PersonaEstudio lstEst : lstEstudios)
                {
                    retorno.getLstObjetos().add(lstEst);
                }
                retorno.setMensaje(new Mensajes("OK", TipoMensaje.MENSAJE));
            }
        }
        return retorno;
    }
    
    @WebMethod(operationName = "lstEstudiosPreviosPorAlumno")
    public Retorno_MsgObj lstEstudiosPreviosPorAlumno(@WebParam(name = "token") String token, @WebParam(name = "PerCod") Long PerCod)
    {
        Retorno_MsgObj retorno = new Retorno_MsgObj();
        LoCalendario loCalendario = LoCalendario.GetInstancia();
        
        if(token == null)
        {
            retorno.setMensaje(new Mensajes("No se recibió ningún valor token", TipoMensaje.ERROR));
        }
        else
        {
            if(PerCod == null)
            {
                retorno.setMensaje(new Mensajes("No se recibió ningún parametro Alumno", TipoMensaje.ERROR));
            }
            else
            {
                retorno = loCalendario.ObtenerListaPendiente(PerCod);
            }
        }
        return retorno;
    }
}
