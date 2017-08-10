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
     */
    @WebMethod(operationName = "EvaluacionesPorAlumno")
    public Retorno_MsgObj EvaluacionesPorAlumno(@WebParam(name = "token") String token, @WebParam(name = "UsuAlumno") Long UsuAlumno)
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
                retorno = (Retorno_MsgObj) loCalendario.ObtenerListaParaInscripcion(UsuAlumno);
            }
        }
        return retorno;
    }
    
    @WebMethod(operationName = "ListaPorAlumno")
    public Retorno_MsgObj ListaPorAlumno(@WebParam(name = "token") String token, @WebParam(name = "UsuAlumno") Long UsuAlumno)
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
