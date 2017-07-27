/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.Calendario;
import Entidad.CalendarioAlumno;
import Entidad.CalendarioDocente;
import Entidad.Evaluacion;
import Entidad.Inscripcion;
import Entidad.PeriodoEstudio;
import Entidad.PeriodoEstudioAlumno;
import Entidad.PeriodoEstudioDocente;
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
    private final PerCalendario perCalendario;

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
    
    public Retorno_MsgObj guardarLista(List<Object> lstCalendario)
    {
        Retorno_MsgObj retornoObj = new Retorno_MsgObj();
        
        for(Object objeto : lstCalendario)
        {
            Calendario calendario = (Calendario) objeto;

            if(calendario.getEvaluacion().getEvlCod() !=null) calendario.setEvaluacion((Evaluacion) LoEvaluacion.GetInstancia().obtener(calendario.getEvaluacion().getEvlCod()).getObjeto());

            retornoObj = (Retorno_MsgObj) this.guardar(calendario);
            
            if(retornoObj.SurgioError())
            {
                int indice = lstCalendario.indexOf(objeto);
                EliminarCalendariosHasta(lstCalendario, indice);
                break;
            }
            /*else
            {
                if(calendario.getEvaluacion().getTpoEvl().getTpoEvlInsAut())
                {
                    PeriodoEstudio perEst = new PeriodoEstudio();

                    if(calendario.getEvaluacion().getMatEvl() != null) perEst = LoPeriodo.GetInstancia().obtenerLastPeriodoEstudioByMateria(calendario.getEvaluacion().getMatEvl().getMatCod());
                    if(calendario.getEvaluacion().getModEvl() != null) perEst = LoPeriodo.GetInstancia().obtenerLastPeriodoEstudioByModulo(calendario.getEvaluacion().getModEvl().getModCod());

                    for(PeriodoEstudioAlumno alumno : perEst.getLstAlumno())
                    {
                        CalendarioAlumno calAlumno = new CalendarioAlumno();
                        calAlumno.setAlumno(alumno.getAlumno());
                        calAlumno.setCalendario(calendario);
                        calAlumno.setEvlCalEst(EstadoCalendarioEvaluacion.SIN_CALIFICAR);

                        calendario.getLstAlumnos().add(calAlumno);

                    }

                    for(PeriodoEstudioDocente docente : perEst.getLstDocente())
                    {
                        CalendarioDocente calDocente = new CalendarioDocente();
                        calDocente.setDocente(docente.getDocente());
                        calDocente.setCalendario(calendario);

                        calendario.getLstDocentes().add(calDocente);                
                    }
                }
            }*/


        }
        
        return retornoObj;
    }
    
    private void EliminarCalendariosHasta(List<Object> lstCalendario, int indice)
    {
        for(Object objeto : lstCalendario)
        {
            if(lstCalendario.indexOf(objeto) < indice)
            {
                this.eliminar(objeto);
            }
            else
            {
                return;
            }
        }
        
    }
    
    
    //------------------------------------------------------------------------------------
    //-MANEJO DE ALUMNOS
    //------------------------------------------------------------------------------------
    
    public Object AlumnoAgregar(CalendarioAlumno alumno)
    {
        boolean error           = false;
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al agregar",TipoMensaje.ERROR), alumno);
        
        if(alumno.getCalendario().getEvaluacion().getMatEvl() != null)
        {
            Inscripcion inscripcion = (Inscripcion) LoInscripcion.GetInstancia().obtenerInscByPlan(alumno.getAlumno().getPerCod(), alumno.getCalendario().getEvaluacion().getMatEvl().getPlan().getPlaEstCod()).getObjeto();
            
            if(inscripcion != null)
            {
                if(inscripcion.MateriaRevalidada(alumno.getCalendario().getEvaluacion().getMatEvl().getMatCod()))
                {
                    error = true;
                    retorno.setMensaje(new Mensajes("El alumno revalida la materia", TipoMensaje.ERROR));
                }
            }
        }
        
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
    
    public Retorno_MsgObj AlumnoAgregarPorPeriodo(Long CalCod, Long PeriEstCod)
    {
        
        int agregados   = 0;
        int noAgregados = 0;

        Calendario calendario  = (Calendario) this.obtener(CalCod).getObjeto();
        PeriodoEstudio periEst = (PeriodoEstudio) LoPeriodo.GetInstancia().EstudioObtener(PeriEstCod).getObjeto();
        
        for(PeriodoEstudioAlumno alumno : periEst.getLstAlumno())
        {
            boolean agregar = true;
            
            if(calendario.getEvaluacion().getMatEvl() != null)
            {
                if(calendario.getEvaluacion().getMatEvl().getMateriaPrevia() != null)
                {
                    agregar = LoPersona.GetInstancia().PersonaAproboMateria(alumno.getAlumno().getPerCod(), calendario.getEvaluacion().getMatEvl().getMateriaPrevia().getMatCod());
                }
            }
            
            if(agregar)
            {
                boolean cargar = true;
                
                if(alumno.getPeriodoEstudio().getMateria() != null)
                {
                    Inscripcion inscripcion = (Inscripcion) LoInscripcion.GetInstancia().obtenerInscByPlan(alumno.getAlumno().getPerCod(), alumno.getPeriodoEstudio().getMateria().getPlan().getPlaEstCod()).getObjeto();

                    if(inscripcion != null)
                    {
                        if(inscripcion.MateriaRevalidada(alumno.getPeriodoEstudio().getMateria().getMatCod()))
                        {
                            cargar = false;
                        }
                    }
                }
                
                if(cargar)
                {
                    CalendarioAlumno calAlumno = new CalendarioAlumno();
                    calAlumno.setAlumno(alumno.getAlumno());
                    calAlumno.setCalendario(calendario);
                    calAlumno.setEvlCalEst(EstadoCalendarioEvaluacion.SIN_CALIFICAR);

                    Retorno_MsgObj resultado = (Retorno_MsgObj) this.AlumnoAgregar(calAlumno);

                    if(resultado.SurgioError())
                    {
                        noAgregados += 1;
                    }
                    else
                    {
                        agregados += 1;
                    }
                }
                else
                {
                    noAgregados += 1;
                }
                
            }
        }
        
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Alumnos agregados: " + agregados + ". Alumnos no agregados: " + noAgregados, (noAgregados > 0 ? TipoMensaje.ADVERTENCIA : TipoMensaje.MENSAJE)));
        
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

        Retorno_MsgObj retorno = (Retorno_MsgObj) this.actualizar(calendario);

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
    
    public Retorno_MsgObj DocenteAgregarPorPeriodo(Long CalCod, Long PeriEstCod)
    {
        
        int agregados   = 0;
        int noAgregados = 0;

        Calendario calendario  = (Calendario) this.obtener(CalCod).getObjeto();
        PeriodoEstudio periEst = (PeriodoEstudio) LoPeriodo.GetInstancia().EstudioObtener(PeriEstCod).getObjeto();
        
        for(PeriodoEstudioDocente docente : periEst.getLstDocente())
        {
            CalendarioDocente calDocente = new CalendarioDocente();
            calDocente.setDocente(docente.getDocente());
            calDocente.setCalendario(calendario);
                    
            Retorno_MsgObj resultado = (Retorno_MsgObj) this.DocenteAgregar(calDocente);

            if(resultado.SurgioError())
            {
                noAgregados += 1;
            }
            else
            {
                agregados += 1;
            }
                
            
        }
        
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Docentes agregados: " + agregados + ". Docentes no agregados: " + noAgregados, (noAgregados > 0 ? TipoMensaje.ADVERTENCIA : TipoMensaje.MENSAJE)));
        
        return retorno;
    }
    
    
}
