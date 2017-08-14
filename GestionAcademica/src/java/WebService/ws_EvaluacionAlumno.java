/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;

import Entidad.Calendario;
import Entidad.CalendarioAlumno;
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
 * @author aa
 */
@WebService(serviceName = "ws_EvaluacionAlumno")
public class ws_EvaluacionAlumno {

    /**
     * This is a sample web service operation
     * @param token parametro
     * @param AluPerCod parametro
     * @return : Devuelve una lista para determinado alumno con evaluaciones que se encuentran pendientes para inscribirse
     */
    @WebMethod(operationName = "EvaluacionesParaInscripcion")
    public Retorno_MsgObj EvaluacionesParaInscripcion(@WebParam(name = "token") String token, @WebParam(name = "AluPerCod") Long AluPerCod)
    {
        LoCalendario loCalendario = LoCalendario.GetInstancia();
        Retorno_MsgObj retorno = new Retorno_MsgObj();

        if(token == null)
        {
            retorno.setMensaje(new Mensajes("No se recibió ningún valor token", TipoMensaje.ERROR));
        }
        else
        {
            if(AluPerCod == null)
            {    
                retorno.setMensaje(new Mensajes("No se recibió un parámetro", TipoMensaje.ERROR));
            }
            else
            {
                retorno = (Retorno_MsgObj) loCalendario.ObtenerListaParaInscripcion(AluPerCod);
            }
        }
        return retorno;
    }
    
    /**
     *
     * @param token parametro
     * @param UsuAlumno parametro
     * @return : Metodo que devuelve la lista de evaluaciones de determinado alumno, que fueron finalizadas.
     */
    @WebMethod(operationName = "EvaluacionesFinalizadas")
    public Retorno_MsgObj EvaluacionesFinalizadas(@WebParam(name = "token") String token, @WebParam(name = "UsuAlumno") Long UsuAlumno)
    {
        
        LoCalendario loCalendario = LoCalendario.GetInstancia();
        Retorno_MsgObj retorno = new Retorno_MsgObj();

        if(token == null)
        {
            retorno.setMensaje(new Mensajes("No se recibió ningún valor token", TipoMensaje.ERROR));
        }
        else
        {
            if(UsuAlumno == null)
            {    
                retorno.setMensaje(new Mensajes("No se recibió un parámetro", TipoMensaje.ERROR));
            }
            else
            {
                retorno = (Retorno_MsgObj) loCalendario.ObtenerListaPorAlumno(UsuAlumno);
            }
        }
        return retorno;
    }
    
    /**
     *
     * @param token parametro
     * @param UsuAlumno parametro
     * @return : Metodo que devuelve una lista de las evaluaciones que el alumno se encuentra inscripto pero estan pendientes para rendír la preuba
     */
    @WebMethod(operationName = "ListaPendiente")
    public Retorno_MsgObj ListaPendiente(@WebParam(name = "token") String token, @WebParam(name = "UsuAlumno") Long UsuAlumno)
    {
        
        LoCalendario loCalendario = LoCalendario.GetInstancia();
        Retorno_MsgObj retorno = new Retorno_MsgObj();

        if(token == null)
        {
            retorno.setMensaje(new Mensajes("No se recibió ningún valor token", TipoMensaje.ERROR));
        }
        else
        {
            if(UsuAlumno == null)
            {    
                retorno.setMensaje(new Mensajes("No se recibió un parámetro", TipoMensaje.ERROR));
            }
            else
            {
                retorno = (Retorno_MsgObj) loCalendario.ObtenerListaPendiente(UsuAlumno);
            }
        }
        return retorno;
    }

    /**
     *
     * @param token parametro
     * @param AluPerCod parametro
     * @param CalCod parametro
     * @return : Metodo que Inscribe un alumno a la evaluacion
     */
    @WebMethod(operationName = "InscribirAlumno")
    public Retorno_MsgObj InscribirAlumno(@WebParam(name = "token") String token, @WebParam(name = "AluPerCod") Long AluPerCod, @WebParam(name = "CalCod") Long CalCod)
    {
        
        LoCalendario loCalendario   = LoCalendario.GetInstancia();
        LoPersona loPersona         = LoPersona.GetInstancia();
        Retorno_MsgObj retorno      = new Retorno_MsgObj();

        if(token.equals(""))
        {
            retorno.setMensaje(new Mensajes("No se recibió el token", TipoMensaje.ERROR));
        }
        else
        {
            if(AluPerCod == null)
            {    
                retorno.setMensaje(new Mensajes("No se recibió el parámetro Alumno", TipoMensaje.ERROR));
            }
            else
            {
                if(CalCod == null)
                {
                    retorno.setMensaje(new Mensajes("No se recibió el parámetro Calendario", TipoMensaje.ERROR));
                }
                else
                {
                    CalendarioAlumno CalAlumno  = new CalendarioAlumno();
                    
                    CalAlumno.setCalendario((Calendario)loCalendario.obtener(CalCod).getObjeto());
                    CalAlumno.setAlumno((Persona)loPersona.obtener(AluPerCod).getObjeto());
                    
                    if(CalAlumno.getCalendario() != null)
                    {
                        if(CalAlumno.getAlumno() != null)
                        {
                            CalAlumno.setEvlCalFch(new java.util.Date());
                            
                            retorno = (Retorno_MsgObj) loCalendario.AlumnoAgregar(CalAlumno);
                        }
                        else
                        {
                            retorno.setMensaje(new Mensajes("No existe el Alumno", TipoMensaje.ERROR));
                        }
                    }
                    else
                    {
                        retorno.setMensaje(new Mensajes("No existe el Calendario", TipoMensaje.ERROR));
                    }
                }
            }
        }
        return retorno;
    }
    
    /**
     *
     * @param token parametro
     * @param CalAlCod parametro
     * @param CalCod parametro
     * @return : Metodo que Borra un alumno de la evaluación a la que está inscripto
     */
    @WebMethod(operationName = "DesinscribirAlumno")
    public Retorno_MsgObj DesinscribirAlumno(@WebParam(name = "token") String token, @WebParam(name = "CalAlCod") Long CalAlCod, @WebParam(name = "CalCod") Long CalCod)
    {
        LoCalendario loCalendario   = LoCalendario.GetInstancia();
        LoPersona loPersona         = LoPersona.GetInstancia();
        Retorno_MsgObj retorno      = new Retorno_MsgObj();

        if(token.equals(""))
        {
            retorno.setMensaje(new Mensajes("No se recibió el token", TipoMensaje.ERROR));
        }
        else
        {
            if(CalAlCod == null)
            {    
                retorno.setMensaje(new Mensajes("No se recibió el parámetro CalendarioAlumno", TipoMensaje.ERROR));
            }
            else
            {
                if(CalCod == null)
                {
                    retorno.setMensaje(new Mensajes("No se recibió el parámetro Calendario", TipoMensaje.ERROR));
                }
                else
                {
                    CalendarioAlumno CalAlumno  = new CalendarioAlumno();
                    
                    CalAlumno.setCalendario((Calendario)loCalendario.obtener(CalCod).getObjeto());
                    CalAlumno.setCalAlCod(CalAlCod);
                    
                    if(CalAlumno.getCalendario() != null)
                    {
                        if(CalAlumno.getCalAlCod() != null)
                        {
                            CalAlumno.setEvlCalFch(new java.util.Date());
                            
                            retorno = (Retorno_MsgObj) loCalendario.AlumnoEliminar(CalAlumno);
                            
                            retorno.setMensaje(new Mensajes("Eliminado Correctamente", TipoMensaje.MENSAJE));
                        }
                        else
                        {
                            retorno.setMensaje(new Mensajes("No existe el CalendarioAlumno", TipoMensaje.ERROR));
                        }
                    }
                    else
                    {
                        retorno.setMensaje(new Mensajes("No existe el Calendario", TipoMensaje.ERROR));
                    }
                }
            }
        }
        return retorno;
    }
    
}
