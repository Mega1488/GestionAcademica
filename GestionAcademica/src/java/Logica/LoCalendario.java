/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.Calendario;
import Entidad.CalendarioAlumno;
import Entidad.CalendarioDocente;
import Entidad.Curso;
import Entidad.Evaluacion;
import Entidad.Inscripcion;
import Entidad.Materia;
import Entidad.MateriaPrevia;
import Entidad.Modulo;
import Entidad.PeriodoEstudio;
import Entidad.PeriodoEstudioAlumno;
import Entidad.PeriodoEstudioDocente;
import Entidad.Persona;
import Enumerado.EstadoCalendarioEvaluacion;
import Enumerado.TipoMensaje;
import Interfaz.InABMGenerico;
import Persistencia.PerCalendario;
import SDT.SDT_Evento;
import Utiles.Mensajes;
import Utiles.Retorno_MsgObj;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    public Retorno_MsgObj guardarLista(List<Object> lstCalendario){
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
        Retorno_MsgObj retornoObj = new Retorno_MsgObj();
        
        for(Object objeto : lstCalendario)
        {
            Calendario calendario = (Calendario) objeto;
            
            if(calendario.getCalFch() != null )
            {
                try {
                    String fecha = sdf.format(new Date(calendario.getCalFch().getTime() + TimeUnit.DAYS.toMillis(1)));
                    calendario.setCalFch(sdf.parse(fecha));
                }catch (ParseException ex) {
                        Logger.getLogger(LoCalendario.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            
            if(calendario.getEvlInsFchDsd() != null )
            {
                try {
                    String fecha = sdf.format(new Date(calendario.getEvlInsFchDsd().getTime() + TimeUnit.DAYS.toMillis(1)));
                    calendario.setEvlInsFchDsd(sdf.parse(fecha));
                }catch (ParseException ex) {
                        Logger.getLogger(LoCalendario.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            
            if(calendario.getEvlInsFchHst() != null )
            {
                try {
                    String fecha = sdf.format(new Date(calendario.getEvlInsFchHst().getTime() + TimeUnit.DAYS.toMillis(1)));
                    calendario.setEvlInsFchHst(sdf.parse(fecha));
                }catch (ParseException ex) {
                        Logger.getLogger(LoCalendario.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            if(calendario.getEvaluacion().getEvlCod() !=null) calendario.setEvaluacion((Evaluacion) LoEvaluacion.GetInstancia().obtener(calendario.getEvaluacion().getEvlCod()).getObjeto());

            retornoObj = (Retorno_MsgObj) this.guardar(calendario);
            
            if(retornoObj.SurgioError())
            {
                int indice = lstCalendario.indexOf(objeto);
                EliminarCalendariosHasta(lstCalendario, indice);
                break;
            }

        }
        
        return retornoObj;
    }
    
    private void EliminarCalendariosHasta(List<Object> lstCalendario, int indice){
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
    
    public Retorno_MsgObj ObtenerListaPorAlumno(Long PerCod)
    {
        Retorno_MsgObj retorno  =  perCalendario.obtenerByAlumno(PerCod);
        
        List<Object> lstRetorno = new ArrayList<>();
        
        if(!retorno.SurgioErrorListaRequerida())
        {
            for(Object objeto : retorno.getLstObjetos())
            {
                Calendario calendar = (Calendario) objeto;
                
                if(calendar.getAlumnoByPersona(PerCod).getEvlCalEst().equals(EstadoCalendarioEvaluacion.VALIDADO))
                {
                    lstRetorno.add(objeto);
                }
            }
        }
       
        retorno.setLstObjetos(lstRetorno);
        return retorno;
    }
    
    public Retorno_MsgObj ObtenerListaParaInscripcion(Long PerCod)
    {
        Retorno_MsgObj retorno  = this.obtenerLista();
        List<Object> lstRetorno = new ArrayList<>();
        
        if(!retorno.SurgioErrorListaRequerida())
        {
            for(Object objeto : retorno.getLstObjetos())
            {
                Calendario calendar = (Calendario) objeto;
                if(calendar.getEvaluacion().getTpoEvl().getTpoEvlExm() && calendar.getCalFch().after(new Date())) 
                {
                    if(!LoPersona.GetInstancia().PersonaAproboEstudio(PerCod, calendar.getEvaluacion().getMatEvl(), calendar.getEvaluacion().getModEvl(), calendar.getEvaluacion().getCurEvl()) || !LoPersona.GetInstancia().PersonaRevalidaMateria(PerCod, calendar.getEvaluacion().getMatEvl()))
                    {
                        if(AlumnoPuedeDarExamen(PerCod, calendar.getEvaluacion().getMatEvl(), calendar.getEvaluacion().getModEvl(), calendar.getEvaluacion().getCurEvl()))
                        {
                            lstRetorno.add(objeto);
                        }
                    }
                    
                }
            }
        }
        
        retorno.setLstObjetos(lstRetorno);
        
        return retorno;
    }
    
    public Retorno_MsgObj ObtenerListaPendiente(Long PerCod)
    {
        Retorno_MsgObj retorno  =  perCalendario.obtenerByAlumno(PerCod);
        List<Object> lstRetorno = new ArrayList<>();
        
        if(!retorno.SurgioErrorListaRequerida())
        {
            for(Object objeto : retorno.getLstObjetos())
            {
                Calendario calendar = (Calendario) objeto;
                if(!calendar.getAlumnoByPersona(PerCod).getEvlCalEst().equals(EstadoCalendarioEvaluacion.VALIDADO))
                {
                    lstRetorno.add(objeto);
                }
            }
        }
        
        retorno.setLstObjetos(lstRetorno);
        
        return retorno;
    }
    
    public ArrayList<SDT_Evento> ObtenerEventoTodosCalendario(){
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<SDT_Evento> lstEvento = new ArrayList<>();
        Retorno_MsgObj retorno = this.obtenerLista();
        
        if(!retorno.SurgioErrorListaRequerida())
        {
            for(Object objeto : retorno.getLstObjetos())
            {
                SDT_Evento evento   = new SDT_Evento();
                Calendario calendar = (Calendario) objeto;
                
                evento.setId(calendar.getCalCod());
                evento.setAllDay(Boolean.TRUE);
                evento.setDurationEditable(Boolean.FALSE);
                evento.setEditable(Boolean.FALSE);
                evento.setStart(calendar.getCalFch());
                evento.setResourceEditable(Boolean.FALSE);
                evento.setStartEditable(Boolean.FALSE);
                evento.setTitle(calendar.getEvaluacion().getEvlNom() + " - " + calendar.getEvaluacion().getEstudioNombre());
                evento.setDescription(calendar.getEvaluacion().getCarreraCursoNombre() + " - " + calendar.getEvaluacion().getEstudioNombre() + " " + calendar.getEvaluacion().getEvlNom());
                
                lstEvento.add(evento);
                
                //Si es una evaluacion que requiere un plazo, agrego como evento el periodo de inscripcion
                if(!calendar.getEvaluacion().getTpoEvl().getTpoEvlInsAut())
                {
                    SDT_Evento evPeriodo   = new SDT_Evento();
                    
                    String fechaHasta = sdf.format(new Date(calendar.getEvlInsFchHst().getTime() + TimeUnit.DAYS.toMillis(1)));
                    
                    evPeriodo.setId(calendar.getCalCod());
                    evPeriodo.setAllDay(Boolean.TRUE);
                    evPeriodo.setDurationEditable(Boolean.FALSE);
                    evPeriodo.setEditable(Boolean.FALSE);
                    evPeriodo.setStart(calendar.getEvlInsFchDsd());
                    try {
                        evPeriodo.setEnd(sdf.parse(fechaHasta));
                    } catch (ParseException ex) {
                        Logger.getLogger(LoCalendario.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    evPeriodo.setResourceEditable(Boolean.FALSE);
                    evPeriodo.setStartEditable(Boolean.FALSE);
                    evPeriodo.setTitle("Inscripción: " + calendar.getEvaluacion().getEvlNom() + " - " + calendar.getEvaluacion().getEstudioNombre());
                    evPeriodo.setDescription("Inscripción: " + calendar.getEvaluacion().getCarreraCursoNombre() + " - " + calendar.getEvaluacion().getEstudioNombre() + " " + calendar.getEvaluacion().getEvlNom());

                    lstEvento.add(evPeriodo); 
                }
                
            }
        }
        
        return lstEvento;
    }
    
    //------------------------------------------------------------------------------------
    //-MANEJO DE ALUMNOS
    //------------------------------------------------------------------------------------
    
    public Object AlumnoAgregar(CalendarioAlumno alumno){
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
    
    public Object AlumnoActualizar(CalendarioAlumno alumno){
        
        Calendario calendario = alumno.getCalendario();
        int indice  = calendario.getLstAlumnos().indexOf(alumno);
        alumno.setObjFchMod(new Date());
        calendario.getLstAlumnos().set(indice, alumno);

        Retorno_MsgObj retorno = (Retorno_MsgObj) this.actualizar(calendario);

        return retorno;
    }
    
    public Object AlumnoEliminar(CalendarioAlumno alumno){
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
                    if(calendario.getDocenteByPersona(persona.getPerCod()).getCalDocCod() != null)
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
    
    public Retorno_MsgObj AlumnoAgregarPorPeriodo(Long CalCod, Long PeriEstCod){
        
        int agregados   = 0;
        int noAgregados = 0;

        Calendario calendario  = (Calendario) this.obtener(CalCod).getObjeto();
        PeriodoEstudio periEst = (PeriodoEstudio) LoPeriodo.GetInstancia().EstudioObtener(PeriEstCod).getObjeto();
        
        for(PeriodoEstudioAlumno alumno : periEst.getLstAlumno())
        {
            boolean agregar = true;
            
            if(calendario.getEvaluacion().getMatEvl() != null)
            {
                if(calendario.getEvaluacion().getMatEvl().getLstPrevias() != null)
                {
                    for(MateriaPrevia matPrevia : calendario.getEvaluacion().getMatEvl().getLstPrevias())
                    {
                        agregar = LoPersona.GetInstancia().PersonaAproboEstudio(alumno.getAlumno().getPerCod(), matPrevia.getMateriaPrevia(), null, null);
                        if(!agregar) break;
                    }
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
    
    public Boolean AlumnoPuedeDarExamen(Long PerCod, Materia materia, Modulo modulo, Curso curso){
        if(materia != null)
        {
            Retorno_MsgObj retorno = perCalendario.obtenerByMateriaPersona(PerCod, materia.getMatCod());

            if(!retorno.SurgioErrorListaRequerida())
            {
                Double creditosParciales    = 0.0;

                for(Object objeto : retorno.getLstObjetos())
                {
                    Calendario calendar = (Calendario) objeto;

                    if(!calendar.getEvaluacion().getTpoEvl().getTpoEvlExm() && calendar.getAlumnoByPersona(PerCod).getEvlCalEst().equals(EstadoCalendarioEvaluacion.VALIDADO))
                    {
                        creditosParciales += calendar.getAlumnoCalificacion(PerCod);
                    }

                }

                if(materia.MateriaPuedeDarExamen(creditosParciales)) return true;

            }
        }
        
        if(modulo != null)
        {
            Retorno_MsgObj retorno = perCalendario.obtenerByModuloPersona(PerCod, modulo.getModCod());

            if(!retorno.SurgioErrorListaRequerida())
            {
                Double creditosParciales    = 0.0;

                for(Object objeto : retorno.getLstObjetos())
                {
                    Calendario calendar = (Calendario) objeto;

                    if(!calendar.getEvaluacion().getTpoEvl().getTpoEvlExm() && calendar.getAlumnoByPersona(PerCod).getEvlCalEst().equals(EstadoCalendarioEvaluacion.VALIDADO))
                    {
                        creditosParciales += calendar.getAlumnoCalificacion(PerCod);
                    }

                }

                //if(modulo.ModuloPuedeDarExamen(creditosParciales)) return true;
                return true;

            }
        }
        
        if(curso != null)
        {
            Retorno_MsgObj retorno = perCalendario.obtenerByCursoPersona(PerCod, curso.getCurCod());

            if(!retorno.SurgioErrorListaRequerida())
            {
                Double creditosParciales    = 0.0;

                for(Object objeto : retorno.getLstObjetos())
                {
                    Calendario calendar = (Calendario) objeto;

                    if(!calendar.getEvaluacion().getTpoEvl().getTpoEvlExm() && calendar.getAlumnoByPersona(PerCod).getEvlCalEst().equals(EstadoCalendarioEvaluacion.VALIDADO))
                    {
                        creditosParciales += calendar.getAlumnoCalificacion(PerCod);
                    }

                }

                //if(modulo.ModuloPuedeDarExamen(creditosParciales)) return true;
                return true;

            }
        }
        
        return false;
    }
    
    public Boolean AlumnoExonera(Long PerCod, Materia materia, Modulo modulo, Curso curso){
        if(materia != null)
        {
            Retorno_MsgObj retorno = perCalendario.obtenerByMateriaPersona(PerCod, materia.getMatCod());

            if(!retorno.SurgioErrorListaRequerida())
            {
                Double creditosParciales    = 0.0;

                for(Object objeto : retorno.getLstObjetos())
                {
                    Calendario calendar = (Calendario) objeto;

                    if(!calendar.getEvaluacion().getTpoEvl().getTpoEvlExm() && calendar.getAlumnoByPersona(PerCod).getEvlCalEst().equals(EstadoCalendarioEvaluacion.VALIDADO))
                    {
                        creditosParciales += calendar.getAlumnoCalificacion(PerCod);
                    }

                }

                if(materia.MateriaExonera(creditosParciales)) return true;

            }
        }
        
        if(modulo != null)
        {
            Retorno_MsgObj retorno = perCalendario.obtenerByMateriaPersona(PerCod, modulo.getModCod());

            if(!retorno.SurgioErrorListaRequerida())
            {
                Double creditosParciales    = 0.0;

                for(Object objeto : retorno.getLstObjetos())
                {
                    Calendario calendar = (Calendario) objeto;

                    if(!calendar.getEvaluacion().getTpoEvl().getTpoEvlExm() && calendar.getAlumnoByPersona(PerCod).getEvlCalEst().equals(EstadoCalendarioEvaluacion.VALIDADO))
                    {
                        creditosParciales += calendar.getAlumnoCalificacion(PerCod);
                    }

                }

                //if(materia.MateriaExonera(creditosParciales)) return true;
                return true;

            }
        }
        
        if(curso != null)
        {
            Retorno_MsgObj retorno = perCalendario.obtenerByMateriaPersona(PerCod, curso.getCurCod());

            if(!retorno.SurgioErrorListaRequerida())
            {
                Double creditosParciales    = 0.0;

                for(Object objeto : retorno.getLstObjetos())
                {
                    Calendario calendar = (Calendario) objeto;

                    if(!calendar.getEvaluacion().getTpoEvl().getTpoEvlExm() && calendar.getAlumnoByPersona(PerCod).getEvlCalEst().equals(EstadoCalendarioEvaluacion.VALIDADO))
                    {
                        creditosParciales += calendar.getAlumnoCalificacion(PerCod);
                    }

                }

                //if(materia.MateriaExonera(creditosParciales)) return true;
                return true;

            }
        }
        
        return false;
    }
    
    public Double AlumnoCreditoParcial(Long PerCod, Materia materia, Modulo modulo, Curso curso){
        Retorno_MsgObj retorno      = new Retorno_MsgObj();
        Double creditosParciales    = 0.0;
         
        if(materia != null)
        {
            retorno = perCalendario.obtenerByMateriaPersona(PerCod, materia.getMatCod());
        }
        if(modulo != null)
        {
            retorno = perCalendario.obtenerByModuloPersona(PerCod, modulo.getModCod());
        }
        if(curso != null)
        {
            retorno = perCalendario.obtenerByCursoPersona(PerCod, curso.getCurCod());
        }
        
        if(!retorno.SurgioErrorListaRequerida())
        {
            for(Object objeto : retorno.getLstObjetos())
            {
                Calendario calendar = (Calendario) objeto;

                if(!calendar.getEvaluacion().getTpoEvl().getTpoEvlExm() && calendar.getAlumnoByPersona(PerCod).getEvlCalEst().equals(EstadoCalendarioEvaluacion.VALIDADO))
                {
                    creditosParciales += calendar.getAlumnoCalificacion(PerCod);
                }

            }
        }
        
        return creditosParciales;
    }

    public Boolean AlumnoCursoEstudio(Long PerCod, Materia materia, Modulo modulo, Curso curso){
        Retorno_MsgObj retorno      = new Retorno_MsgObj();
         
        if(materia != null)
        {
            retorno = perCalendario.obtenerByMateriaPersona(PerCod, materia.getMatCod());
        }
        if(modulo != null)
        {
            retorno = perCalendario.obtenerByModuloPersona(PerCod, modulo.getModCod());
        }
        if(curso != null)
        {
            retorno = perCalendario.obtenerByCursoPersona(PerCod, curso.getCurCod());
        }
        
        if(!retorno.SurgioErrorListaRequerida())
        {
            return retorno.getLstObjetos().size() > 0;
        }
        
        return false;
    }    
    
    //------------------------------------------------------------------------------------
    //-MANEJO DE DOCENTES
    //------------------------------------------------------------------------------------
    
    public Object DocenteAgregar(CalendarioDocente docente){
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
    
    public Object DocenteActualizar(CalendarioDocente docente){
        
        Calendario calendario = docente.getCalendario();
        int indice  = calendario.getLstDocentes().indexOf(docente);
        docente.setObjFchMod(new Date());
        calendario.getLstDocentes().set(indice, docente);

        Retorno_MsgObj retorno = (Retorno_MsgObj) this.actualizar(calendario);

        return retorno;
    }
    
    public Object DocenteEliminar(CalendarioDocente docente){
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
    
    public Retorno_MsgObj DocenteAgregarPorPeriodo(Long CalCod, Long PeriEstCod){
        
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
