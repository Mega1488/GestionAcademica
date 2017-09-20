/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.Curso;
import Entidad.Inscripcion;
import Entidad.Materia;
import Entidad.Modulo;
import Entidad.Parametro;
import Entidad.Periodo;
import Entidad.PeriodoEstudio;
import Entidad.PeriodoEstudioAlumno;
import Entidad.PeriodoEstudioDocente;
import Entidad.PeriodoEstudioDocumento;
import Entidad.PlanEstudio;
import Enumerado.TipoMensaje;
import Interfaz.InABMGenerico;
import Moodle.MoodleCourse;
import Moodle.MoodleCourseContent;
import Moodle.MoodleModule;
import Moodle.MoodleModuleContent;
import Moodle.Role;
import Persistencia.PerManejador;
import SDT.SDT_Parameters;
import Utiles.Mensajes;
import Utiles.Retorno_MsgObj;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author alvar
 */
public class LoPeriodo implements InABMGenerico{

    private static LoPeriodo instancia;
    private final LoEstudio loEstudio;
    private final Parametro         param;

    private LoPeriodo() {
        loEstudio = LoEstudio.GetInstancia();
        param     = LoParametro.GetInstancia().obtener();
    }
    
    public static LoPeriodo GetInstancia(){
        if (instancia==null)
        {
            instancia   = new LoPeriodo();
        }

        return instancia;
    }
    

    @Override
    public Object guardar(Object pObjeto) {
        Periodo periodo = (Periodo) pObjeto;
        
        //--------------------------------------------------------------------------------------------------------------------------------------------
        //MODULO
        //--------------------------------------------------------------------------------------------------------------------------------------------
        
        List<Object> lstModulo = LoCurso.GetInstancia().ModuloPorPeriodo(null, periodo.getPerTpo(), periodo.getPerVal()).getLstObjetos();

        if(lstModulo != null)
        {
            for(Object objeto : lstModulo)
            {
                Modulo mdl = (Modulo) objeto;
                
                PeriodoEstudio periEstudio = new PeriodoEstudio();
                periEstudio.setModulo(mdl);
                periEstudio.setPeriodo(periodo);
                periEstudio.setObjFchMod(new Date());
                periodo.getLstEstudio().add(periEstudio);
            }
        }
        
        //--------------------------------------------------------------------------------------------------------------------------------------------
        //MATERIA
        //--------------------------------------------------------------------------------------------------------------------------------------------
        List<Object> lstMateria = LoCarrera.GetInstancia().MateriaPorPeriodo(null, periodo.getPerTpo(), periodo.getPerVal()).getLstObjetos();

        if(lstMateria != null)
        {
            for(Object objeto : lstMateria)
            {
                Materia mat = (Materia) objeto;
                
                PeriodoEstudio periEstudio = new PeriodoEstudio();
                periEstudio.setMateria(mat);
                periEstudio.setPeriodo(periodo);
                periEstudio.setObjFchMod(new Date());
                periodo.getLstEstudio().add(periEstudio);
            }
        }
        
        
        PerManejador perManager = new PerManejador();
            
        periodo.setObjFchMod(new Date());

        Retorno_MsgObj retorno = perManager.guardar(periodo);

        if(!retorno.SurgioErrorObjetoRequerido())
        {
            periodo.setPeriCod((Long) retorno.getObjeto());
            retorno.setObjeto(periodo);
            
            
                if(periodo.getLstEstudio() != null && param.getParUtlMdl())
                {
                    for(PeriodoEstudio periEstudio : periodo.getLstEstudio())
                    {
                        this.EstudioActualizar(periEstudio);
                    }
                }
            
        }
            
        return retorno;
        
    }

    @Override
    public Object actualizar(Object pObjeto) {
        
        Periodo periodo = (Periodo) pObjeto;
            
        PerManejador perManager = new PerManejador();

        periodo.setObjFchMod(new Date());
        
        return perManager.actualizar(periodo);
    }

    @Override
    public Object eliminar(Object pObjeto) {
        PerManejador perManager = new PerManejador();
        return perManager.eliminar(pObjeto);
    }

    @Override
    public Retorno_MsgObj obtener(Object pObjeto) {
        PerManejador perManager = new PerManejador();
        return perManager.obtener((Long) pObjeto, Periodo.class);
    }

    @Override
    public Retorno_MsgObj obtenerLista() {
        PerManejador perManager = new PerManejador();

        return perManager.obtenerLista("Periodo.findAll", null);
    }
    
    public Retorno_MsgObj guardarPorEstudio(Periodo periodo, Curso curso, PlanEstudio plan) {
        int modAgregados = 0;
        int matAgregadas = 0;
        
        //--------------------------------------------------------------------------------------------------------------------------------------------
        //MODULO
        //--------------------------------------------------------------------------------------------------------------------------------------------
        if(curso != null)
        {
            List<Object> lstModulo = LoCurso.GetInstancia().ModuloPorPeriodo(curso.getCurCod(), periodo.getPerTpo(), periodo.getPerVal()).getLstObjetos();

            if(lstModulo != null)
            {
                for(Object objeto : lstModulo)
                {
                    Modulo mdl = (Modulo) objeto;
                    
                    if(!periodo.PeriodoPoseeModulo(mdl.getModCod()))
                    {
                        PeriodoEstudio periEstudio = new PeriodoEstudio();
                        periEstudio.setModulo(mdl);
                        periEstudio.setPeriodo(periodo);
                        periodo.getLstEstudio().add(periEstudio);

                        modAgregados += 1;
                    }
                }
            }
        }
        //--------------------------------------------------------------------------------------------------------------------------------------------
        //MATERIA
        //--------------------------------------------------------------------------------------------------------------------------------------------
        if(plan != null)
        {
            List<Object> lstMateria = LoCarrera.GetInstancia().MateriaPorPeriodo(plan.getPlaEstCod(), periodo.getPerTpo(), periodo.getPerVal()).getLstObjetos();

            if(lstMateria != null)
            {
                for(Object objeto : lstMateria)
                {
                    Materia mat = (Materia) objeto;
                    if(!periodo.PeriodoPoseeMateria(mat.getMatCod()))
                    {
                        PeriodoEstudio periEstudio = new PeriodoEstudio();
                        periEstudio.setMateria(mat);
                        periEstudio.setPeriodo(periodo);
                        periodo.getLstEstudio().add(periEstudio);

                        matAgregadas += 1;
                    }
                }
            }
            
        }
        
        
        Retorno_MsgObj retorno = (Retorno_MsgObj) this.actualizar(periodo);
        
        if(!retorno.SurgioError())
        {
            if(modAgregados > 0 || matAgregadas > 0 && param.getParUtlMdl())
            {
                for(PeriodoEstudio periEstudio : periodo.getLstEstudio())
                {
                    this.EstudioActualizar(periEstudio);
                }
            }

            retorno.setMensaje(new Mensajes("Se ingresaron " + modAgregados + " modulos, " + matAgregadas + " materias", TipoMensaje.MENSAJE));
        }

        return retorno;
    }
 
    public PeriodoEstudio obtenerLastPeriodoEstudioByMateria(Long MatCod) {
        
       
        PerManejador perManager = new PerManejador();
        
        ArrayList<SDT_Parameters> lstParametros = new ArrayList<>();
        lstParametros.add(new SDT_Parameters(MatCod, "MatCod"));
        
        Retorno_MsgObj retorno = perManager.obtenerLista("Periodo.findLastByMat", lstParametros);
       
        if(!retorno.SurgioErrorListaRequerida())
        {
            if(!retorno.getLstObjetos().isEmpty())
            {
                
                Periodo periodo = (Periodo) retorno.getLstObjetos().get(0);
                
                for(PeriodoEstudio perEst : periodo.getLstEstudio())
                {
                    if(perEst.getMateria().getMatCod().equals(MatCod))
                    {
                        return perEst;
                    }
                }
            }
        }
        
        return null;
    }
    
    public PeriodoEstudio obtenerLastPeriodoEstudioByModulo(Long ModCod) {
        
        PerManejador perManager = new PerManejador();
        
        ArrayList<SDT_Parameters> lstParametros = new ArrayList<>();
        lstParametros.add(new SDT_Parameters(ModCod, "ModCod"));
        
        Retorno_MsgObj retorno = perManager.obtenerLista("Periodo.findLastByMod", lstParametros);
       
        if(!retorno.SurgioErrorListaRequerida())
        {
            if(!retorno.getLstObjetos().isEmpty())
            {
                
                Periodo periodo = (Periodo) retorno.getLstObjetos().get(0);
                
                for(PeriodoEstudio perEst : periodo.getLstEstudio())
                {
                    if(perEst.getModulo().getModCod().equals(ModCod))
                    {
                        return perEst;
                    }
                }
            }
        }
        
        return null;
        
    }
    
    //------------------------------------------------------------------------------------
    //-MANEJO DE ESTUDIO
    //------------------------------------------------------------------------------------
    
    public Object EstudioAgregar(PeriodoEstudio periEstudio)
    {
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Guardando estudio", TipoMensaje.MENSAJE), periEstudio);
        
        if(param.getParUtlMdl())
        {
            
            Long parent = 0L;

            if(periEstudio.getMateria() != null) parent = periEstudio.getMateria().getMdlCod();
            if(periEstudio.getModulo() != null) parent = periEstudio.getModulo().getModEstCod();

            if(periEstudio.getMdlCod() == null)
            {
                retorno = this.Mdl_AgregarEstudio(parent
                        ,periEstudio.getPeriodo().TextoPeriodo() + " - " + periEstudio.getPeriodo().getPerFchIni() 
                        ,periEstudio.GetPrimaryKey() +"_"+ periEstudio.getPeriodo().getPerTpoNombre() +"_"+ periEstudio.getPeriodo().getPerVal() 
                        ,periEstudio.getCarreraCursoNombre() + "\n" + periEstudio.getPeriodo().TextoPeriodo());
            }
            else
            {
                retorno = this.Mdl_ActualizarEstudio(parent
                        , periEstudio.getMdlCod()
                        , periEstudio.getPeriodo().TextoPeriodo() + " - " + periEstudio.getPeriodo().getPerFchIni()
                        , periEstudio.GetPrimaryKey() +"_"+ periEstudio.getPeriodo().getPerTpoNombre() +"_"+ periEstudio.getPeriodo().getPerVal()
                        , periEstudio.getCarreraCursoNombre() + "\n" + periEstudio.getPeriodo().TextoPeriodo());
            }
            
            periEstudio.setMdlCod((Long) retorno.getObjeto());
            retorno.setObjeto(periEstudio);
        }
        
        if(!retorno.SurgioError())
        {
            periEstudio = (PeriodoEstudio) retorno.getObjeto();
            periEstudio.setObjFchMod(new Date());
            PerManejador perManejador   = new PerManejador();
            retorno = perManejador.guardar(periEstudio);
            
        }

        return retorno;
    }
    
    public Object EstudioActualizar(PeriodoEstudio periEstudio)
    {
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Actualizando estudio", TipoMensaje.MENSAJE), periEstudio);
        
        if(param.getParUtlMdl())
        {
            Long parent = 0L;

            if(periEstudio.getMateria() != null) parent = periEstudio.getMateria().getMdlCod();
            if(periEstudio.getModulo() != null) parent = periEstudio.getModulo().getModEstCod();

            if(periEstudio.getMdlCod() == null)
            {
                retorno = this.Mdl_AgregarEstudio(parent
                        ,periEstudio.getPeriodo().TextoPeriodo() + " - " + periEstudio.getPeriodo().getPerFchIni() 
                        ,periEstudio.GetPrimaryKey() +"_"+ periEstudio.getPeriodo().getPerTpoNombre() +"_"+ periEstudio.getPeriodo().getPerVal() 
                        ,periEstudio.getCarreraCursoNombre() + "\n" + periEstudio.getPeriodo().TextoPeriodo());
            }
            else
            {
                retorno = this.Mdl_ActualizarEstudio(parent
                        , periEstudio.getMdlCod()
                        , periEstudio.getPeriodo().TextoPeriodo() + " - " + periEstudio.getPeriodo().getPerFchIni()
                        , periEstudio.GetPrimaryKey() +"_"+ periEstudio.getPeriodo().getPerTpoNombre() +"_"+ periEstudio.getPeriodo().getPerVal()
                        , periEstudio.getCarreraCursoNombre() + "\n" + periEstudio.getPeriodo().TextoPeriodo());
            }
            
            periEstudio.setMdlCod((Long) retorno.getObjeto());
            retorno.setObjeto(periEstudio);
        }
        
        if(!retorno.SurgioError())
        {
            periEstudio = (PeriodoEstudio) retorno.getObjeto();
            periEstudio.setObjFchMod(new Date());

            PerManejador perManejador   = new PerManejador();
            retorno = perManejador.actualizar(periEstudio);
        }
        
        return retorno;
    }
    
    public Object EstudioEliminar(PeriodoEstudio periEstudio)
    {
        if(param.getParUtlMdl() && periEstudio.getMdlCod() != null)
        {
            Retorno_MsgObj retorno = this.Mdl_EliminarEstudio(periEstudio.getMdlCod());
            if(retorno.SurgioError()) return retorno;
        }

        PerManejador perManejador   = new PerManejador();
        return perManejador.eliminar(periEstudio);
    }
    
    public Retorno_MsgObj EstudioObtener(Long PeriEstCod){
        PerManejador perManager = new PerManejador();
        return perManager.obtener(PeriEstCod, PeriodoEstudio.class);
    }
    
    public Retorno_MsgObj EstudioObtenerByPersona(Long PerCod){
        PerManejador perManager = new PerManejador();

        ArrayList<SDT_Parameters> lstParametros = new ArrayList<>();
        
        lstParametros.add(new SDT_Parameters(PerCod, "PerCod"));
        
        return perManager.obtenerLista("PeriodoEstudio.findByPersona", lstParametros);
    }
    
    public Retorno_MsgObj EstudioObtenerTodos()
    {
        PerManejador perManager = new PerManejador();
        return perManager.obtenerLista("PeriodoEstudio.findAll", null);
    }
    
    //------------------------------------------------------------------------------------
    //-MANEJO DE ALUMNO
    //------------------------------------------------------------------------------------
    
    public Object AlumnoAgregar(PeriodoEstudioAlumno alumno)
    {
        boolean error           = false;
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al agregar",TipoMensaje.ERROR), alumno);
        
        
        if(alumno.getPeriodoEstudio().getMateria() != null)
        {
            Inscripcion inscripcion = (Inscripcion) LoInscripcion.GetInstancia().obtenerInscByPlan(alumno.getAlumno().getPerCod(), alumno.getPeriodoEstudio().getMateria().getPlan().getPlaEstCod()).getObjeto();
            
            if(inscripcion != null)
            {
                if(inscripcion.MateriaRevalidada(alumno.getPeriodoEstudio().getMateria().getMatCod()))
                {
                    error = true;
                    retorno.setMensaje(new Mensajes("El alumno revalida la materia", TipoMensaje.ERROR));
                }
            }
        }
                
                
        
        if(!error)
        {
            if(this.param.getParUtlMdl())
            {
                retorno = loEstudio.Mdl_AsignUserCourse(alumno.getAlumno().getPerUsrModID(), 
                        alumno.getPeriodoEstudio().getMdlCod(), Role.STUDENT);

                if(retorno.SurgioError()) return retorno;
            }
            alumno.setObjFchMod(new Date());
            
            PerManejador perManejador   = new PerManejador();
            retorno = (Retorno_MsgObj) perManejador.guardar(alumno);
        }
       
        
        return retorno;
    }
    
    public Object AlumnoActualizar(PeriodoEstudioAlumno alumno)
    {
        alumno.setObjFchMod(new Date());
        PerManejador perManejador   = new PerManejador();
        return perManejador.actualizar(alumno);
    }
    
    public Object AlumnoEliminar(PeriodoEstudioAlumno alumno)
    {
        Retorno_MsgObj retorno = loEstudio.Mdl_UnAsignUserCourse(alumno.getAlumno().getPerUsrModID(), 
                    alumno.getPeriodoEstudio().getMdlCod(), Role.STUDENT);

        if(retorno.SurgioError()) return retorno;
            
        PerManejador perManejador   = new PerManejador();
        return perManejador.eliminar(alumno);
    }
   
    public Retorno_MsgObj GeneracionAgregar(Long PeriCod, Integer InsGenAnio)
    {
        boolean error           = false;
        boolean existenAlumnos  = false;
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al agregar",TipoMensaje.ERROR), null);
        
        if(!error)
        {
            Periodo periodo = (Periodo) this.obtener(PeriCod).getObjeto();
            
            for(PeriodoEstudio periEst : periodo.getLstEstudio())
            {
                List<Object> inscripciones = new ArrayList<>();
                
                if(periEst.getMateria() != null) inscripciones = LoInscripcion.GetInstancia().obtenerListaByPlan(null, periEst.getMateria().getPlan().getPlaEstCod()).getLstObjetos();
                if(periEst.getModulo() != null) inscripciones = LoInscripcion.GetInstancia().obtenerListaByCurso(null, periEst.getModulo().getCurso().getCurCod()).getLstObjetos();
                if(inscripciones.size() > 0)
                {
                    for(Object objeto : inscripciones )
                    {
                        Inscripcion inscripcion = (Inscripcion) objeto;

                        if(inscripcion.getInsGenAnio().equals(InsGenAnio))
                        {
                            boolean cargar = true;

                            if(periEst.getMateria() != null)
                            {
                                cargar = !inscripcion.MateriaRevalidada(periEst.getMateria().getMatCod());
                            }

                            if(cargar)
                            {
                                existenAlumnos = true;
                                
                                PeriodoEstudioAlumno periEstAlu = new PeriodoEstudioAlumno();
                                periEstAlu.setAlumno(inscripcion.getAlumno());
                                periEstAlu.setPerInsFchInsc(new Date());
                                periEstAlu.setPeriodoEstudio(periEst);
                                retorno = (Retorno_MsgObj) this.AlumnoAgregar(periEstAlu);

                                if(retorno.SurgioError()) return retorno;
                            }
                        }
                    }
                }
            }
            
        }
       
        if(!existenAlumnos) retorno = new Retorno_MsgObj(new Mensajes("No existen alumnos inscriptos a la generación indicada",TipoMensaje.ADVERTENCIA), null);
        
        return retorno;
    }
    
    //------------------------------------------------------------------------------------
    //-MANEJO DE DOCENTES
    //------------------------------------------------------------------------------------
    
    public Object DocenteAgregar(PeriodoEstudioDocente docente)
    {
   
        if(this.param.getParUtlMdl())
        { 
           Retorno_MsgObj retorno = loEstudio.Mdl_AsignUserCourse(docente.getDocente().getPerUsrModID(), 
                        docente.getPeriodoEstudio().getMdlCod(), Role.TEACHER);
            if(retorno.SurgioError()) return retorno;
        }
        
         
        docente.setObjFchMod(new Date());
        PerManejador perManejador   = new PerManejador();
        return perManejador.guardar(docente);

    }
    
    public Object DocenteActualizar(PeriodoEstudioDocente docente)
    {
        
        docente.setObjFchMod(new Date());
        PerManejador perManejador   = new PerManejador();
        return perManejador.actualizar(docente);
    }
    
    public Object DocenteEliminar(PeriodoEstudioDocente docente)
    {
        Retorno_MsgObj retorno = loEstudio.Mdl_UnAsignUserCourse(docente.getDocente().getPerUsrModID(), 
                    docente.getPeriodoEstudio().getMdlCod(), Role.TEACHER);

        if(retorno.SurgioError()) return retorno;
            
        PerManejador perManejador   = new PerManejador();
        return perManejador.eliminar(docente);
    }
    
    //------------------------------------------------------------------------------------
    //-MANEJO DE DOCUMENTO
    //------------------------------------------------------------------------------------
    
    public Object DocumentoAgregar(PeriodoEstudioDocumento documento)
    {
        documento.setObjFchMod(new Date());
        PerManejador perManejador   = new PerManejador();
        return perManejador.guardar(documento);
    }
    
    public Object DocumentoActualizar(PeriodoEstudioDocumento documento)
    {
        
        documento.setObjFchMod(new Date());
        PerManejador perManejador   = new PerManejador();
        return perManejador.actualizar(documento);
    }
    
    public Object DocumentoEliminar(PeriodoEstudioDocumento documento)
    {
        PerManejador perManejador   = new PerManejador();
        return perManejador.eliminar(documento);
    }
    
    public void DocumentoImportarMoodle(){
        if(param.getParUtlMdl())
        {
            Retorno_MsgObj retorno = this.EstudioObtenerTodos();
            
            for(Object objeto : retorno.getLstObjetos())
            {
                PeriodoEstudio estudio = (PeriodoEstudio) objeto;
                
                if(estudio.getMdlCod() != null)
                {
                    MoodleCourseContent[] courseContent = loEstudio.Mdl_ObtenerEstudioContent(estudio.getMdlCod());
                    
                    //Busco archivos
                    
                    if(courseContent != null)
                    {
                        //Recorro cada contenido
                        for(MoodleCourseContent content : courseContent) {
                            if(content.getMoodleModules() != null)
                            {
                                //Recorro los modulos del curso
                                for (MoodleModule modulo : content.getMoodleModules()) {
                                    if(modulo.getContent() != null)
                                    {
                                        //Recorro el contenido de cada modulo
                                        for (MoodleModuleContent modContent : modulo.getContent()) {
                                            //Controlo fecha.
                                            Date fechaCreado = new Date(modContent.getTimeCreated() * 1000);
                                            if(estudio.getFchSincMdl() != null)
                                            {
                                                if(fechaCreado.after(estudio.getFchSincMdl()))
                                                {
                                                    PeriodoEstudioDocumento documento = loEstudio.Mdl_ObtenerEstudioArchivo(modContent);
                                                    
                                                    Retorno_MsgObj ret = (Retorno_MsgObj) LoArchivo.GetInstancia().guardar(documento.getObjArchivo());
                                                    if(!ret.SurgioError())
                                                    {
                                                        documento.setPeriodo(estudio);
                                                        this.DocumentoAgregar(documento);
                                                    }
                                                    
                                                }
                                            }
                                            else
                                            {
                                                PeriodoEstudioDocumento documento = loEstudio.Mdl_ObtenerEstudioArchivo(modContent);
                                                
                                                Retorno_MsgObj ret = (Retorno_MsgObj) LoArchivo.GetInstancia().guardar(documento.getObjArchivo());
                                                if(!ret.SurgioError())
                                                {
                                                    documento.setPeriodo(estudio);
                                                    this.DocumentoAgregar(documento);
                                                }
                                                
                                            }
                                            
                                            
                                        }
                                    }
                                }
                            }
                        }
                    }
                    
                    estudio.setFchSincMdl(new Date());
                    this.EstudioActualizar(estudio);
                }
            }                        
        
        }
    }
    
    public void DocumentoImportarMoodle(PeriodoEstudio estudio){
        if(estudio.getMdlCod() != null)
                {
                    MoodleCourseContent[] courseContent = loEstudio.Mdl_ObtenerEstudioContent(estudio.getMdlCod());
                    
                    //Busco archivos
                    
                    if(courseContent != null)
                    {
                        //Recorro cada contenido
                        for(MoodleCourseContent content : courseContent) {
                            if(content.getMoodleModules() != null)
                            {
                                //Recorro los modulos del curso
                                for (MoodleModule modulo : content.getMoodleModules()) {
                                    if(modulo.getContent() != null)
                                    {
                                        //Recorro el contenido de cada modulo
                                        for (MoodleModuleContent modContent : modulo.getContent()) {
                                            //Controlo fecha.
                                            Date fechaCreado = new Date(modContent.getTimeCreated() * 1000);
                                            if(estudio.getFchSincMdl() != null)
                                            {
                                                if(fechaCreado.after(estudio.getFchSincMdl()))
                                                {
                                                    PeriodoEstudioDocumento documento = loEstudio.Mdl_ObtenerEstudioArchivo(modContent);
                                                    
                                                    Retorno_MsgObj ret = (Retorno_MsgObj) LoArchivo.GetInstancia().guardar(documento.getObjArchivo());
                                                    if(!ret.SurgioError())
                                                    {
                                                        documento.setPeriodo(estudio);
                                                        this.DocumentoAgregar(documento);
                                                    }
                                                    
                                                }
                                            }
                                            else
                                            {
                                                PeriodoEstudioDocumento documento = loEstudio.Mdl_ObtenerEstudioArchivo(modContent);
                                                
                                                Retorno_MsgObj ret = (Retorno_MsgObj) LoArchivo.GetInstancia().guardar(documento.getObjArchivo());
                                                if(!ret.SurgioError())
                                                {
                                                    documento.setPeriodo(estudio);
                                                    this.DocumentoAgregar(documento);
                                                }
                                                
                                            }
                                            
                                            
                                        }
                                    }
                                }
                            }
                        }
                    }
                    
                    estudio.setFchSincMdl(new Date());
                    this.EstudioActualizar(estudio);
                }
    }

    
    
    //--------------------------------------------------------------------------------------------------------
    //Moodle
    //--------------------------------------------------------------------------------------------------------
    
    private Retorno_MsgObj Mdl_AgregarEstudio(Long parent, String mdlFullNom, String mdlShrtNom, String mdlDsc)
    {
        Retorno_MsgObj retorno = loEstudio.Mdl_AgregarEstudio(parent, mdlFullNom, mdlShrtNom, mdlDsc);
        
        if(!retorno.SurgioErrorObjetoRequerido())
        {
            MoodleCourse mdlEstudio = (MoodleCourse) retorno.getObjeto();
            retorno.setObjeto(mdlEstudio.getId());
        }
        
        return retorno;
    }
    
    private Retorno_MsgObj Mdl_ActualizarEstudio(Long parent, Long mdlCod, String mdlFullNom, String mdlShrtNom, String mdlDsc)
    {
        Retorno_MsgObj retorno = loEstudio.Mdl_ActualizarEstudio(mdlCod, parent, mdlFullNom, mdlShrtNom, mdlDsc);
        
        if(!retorno.SurgioErrorObjetoRequerido())
        {
            MoodleCourse mdlEstudio = (MoodleCourse) retorno.getObjeto();
            retorno.setObjeto(mdlEstudio.getId());
        }
        
        return retorno;
    }
    
    private Retorno_MsgObj Mdl_EliminarEstudio(Long mdlCod)
    {
        return loEstudio.Mdl_EliminarEstudio(mdlCod);
    }
    
}
