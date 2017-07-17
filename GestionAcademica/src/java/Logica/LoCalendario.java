/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.Calendario;
import Entidad.CalendarioAlumno;
import Entidad.CalendarioDocente;
import Entidad.Persona;
import Enumerado.EstadoCalendarioEvaluacion;
import Enumerado.TipoMensaje;
import Interfaz.InABMGenerico;
import Persistencia.PerCalendario;
import Utiles.Mensajes;
import Utiles.Retorno_MsgObj;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author alvar
 */
public class LoCalendario implements InABMGenerico{

    private static LoCalendario instancia;
    private PerCalendario perCalendario;

    private LoCalendario() {
        perCalendario  = new PerCalendario();
    }
    
    public static LoCalendario GetInstancia(){
        if (instancia==null)
        {
            instancia   = new LoCalendario();
        }

        return instancia;
    }
    

    @Override
    public Object guardar(Object pObjeto) {
        return perCalendario.guardar(pObjeto);
    }

    @Override
    public Object actualizar(Object pObjeto) {
        return perCalendario.actualizar(pObjeto);
    }

    @Override
    public Object eliminar(Object pObjeto) {
        return perCalendario.eliminar(pObjeto);
    }

    @Override
    public Retorno_MsgObj obtener(Object pObjeto) {
        return perCalendario.obtener(pObjeto);
    }

    @Override
    public Retorno_MsgObj obtenerLista() {
        return perCalendario.obtenerLista();
    }
    
    
    //------------------------------------------------------------------------------------
    //-MANEJO DE ALUMNOS
    //------------------------------------------------------------------------------------
    
    public Object AlumnoAgregar(CalendarioAlumno alumno)
    {
        boolean error           = false;
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al agregar",TipoMensaje.ERROR), alumno);
        
        if(!error)
        {
            Calendario calendario = alumno.getCalendario();
            alumno.setObjFchMod(new Date());
            alumno.setEvlCalEst(EstadoCalendarioEvaluacion.SIN_CALIFICAR);
            calendario.getLstAlumnos().add(alumno);
            retorno = (Retorno_MsgObj) this.actualizar(calendario);
        }
       
        
        return retorno;
    }
    
    public Object AlumnoActualizar(CalendarioAlumno alumno)
    {
        
        Calendario calendario = alumno.getCalendario();
        int indice  = calendario.getLstAlumnos().indexOf(alumno);
        alumno.setObjFchMod(new Date());
        calendario.getLstAlumnos().set(indice, alumno);

        Retorno_MsgObj retorno = (Retorno_MsgObj) this.actualizar(calendario);

        return retorno;
    }
    
    public Object AlumnoEliminar(CalendarioAlumno alumno)
    {
        boolean error           = false;
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al eliminar", TipoMensaje.ERROR), alumno);
       
        if(!error)
        {
            Calendario calendario = alumno.getCalendario();
            int indice  = calendario.getLstAlumnos().indexOf(alumno);
            calendario.getLstAlumnos().remove(indice);
       
            retorno = (Retorno_MsgObj) this.actualizar(calendario);
        }
        return retorno;
    }
    
    public List<CalendarioAlumno> AlumnoObtenerListaPorUsuario(Calendario calendario, String usuario){
        
        List<CalendarioAlumno> retorno = new ArrayList<>();
        
        Persona persona = new Persona();
        if(usuario != null) persona = (Persona) LoPersona.GetInstancia().obtenerByMdlUsr(usuario).getObjeto();
        
        if(persona != null)
        {
            if(persona.getPerEsAdm())
            {
                retorno = calendario.getLstAlumnos();
            }
            else
            {
                if(persona.getPerEsDoc())
                {
                    if(calendario.getDocenteById(persona.getPerCod()).getCalDocCod() != null)
                    {
                        for(CalendarioAlumno alumno : calendario.getLstAlumnos())
                        {
                            if(alumno.getEvlCalEst().equals(EstadoCalendarioEvaluacion.CALIFICADO) || alumno.getEvlCalEst().equals(EstadoCalendarioEvaluacion.PENDIENTE_CORRECCION) || alumno.getEvlCalEst().equals(EstadoCalendarioEvaluacion.SIN_CALIFICAR))
                            {
                                retorno.add(alumno);
                            }
                        }
                    }
                }
            }
        }
        
        return retorno;
    }
    
    
    //------------------------------------------------------------------------------------
    //-MANEJO DE DOCENTES
    //------------------------------------------------------------------------------------
    
    public Object DocenteAgregar(CalendarioDocente docente)
    {
        boolean error           = false;
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al agregar",TipoMensaje.ERROR), docente);
        
        if(!error)
        {
            Calendario calendario = docente.getCalendario();
            docente.setObjFchMod(new Date());
            
            calendario.getLstDocentes().add(docente);
            retorno = (Retorno_MsgObj) this.actualizar(calendario);
        }
       
        
        return retorno;
    }
    
    public Object DocenteActualizar(CalendarioDocente docente)
    {
        
        Calendario calendario = docente.getCalendario();
        int indice  = calendario.getLstDocentes().indexOf(docente);
        docente.setObjFchMod(new Date());
        calendario.getLstDocentes().set(indice, docente);

        Retorno_MsgObj retorno = (Retorno_MsgObj) this.actualizar(docente);

        return retorno;
    }
    
    public Object DocenteEliminar(CalendarioDocente docente)
    {
        boolean error           = false;
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al eliminar", TipoMensaje.ERROR), docente);
       
        if(!error)
        {
            Calendario calendario = docente.getCalendario();
            int indice  = calendario.getLstDocentes().indexOf(docente);
            calendario.getLstDocentes().remove(indice);

            retorno = (Retorno_MsgObj) this.actualizar(calendario);
        }
        return retorno;
    }
    
    
}
