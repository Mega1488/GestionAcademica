/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.Curso;
import Entidad.Evaluacion;
import Entidad.Modulo;
import Entidad.Parametro;
import Enumerado.TipoMensaje;
import Enumerado.TipoPeriodo;
import Moodle.MoodleCategory;
import Moodle.MoodleCourse;
import Moodle.MoodleRestCourse;
import Persistencia.PerManejador;
import SDT.SDT_Parameters;
import Utiles.Mensajes;
import Utiles.Retorno_MsgObj;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author alvar
 */
public class LoCurso implements Interfaz.InCurso{
    private final Parametro         param;
    private final MoodleRestCourse  mdlCourse;
    private final LoCategoria       loCategoria;
    private static LoCurso instancia;
    private final LoEstudio  loEstudio;

    private LoCurso() {
        mdlCourse           = new MoodleRestCourse();
        param               = LoParametro.GetInstancia().obtener();
        loCategoria         = LoCategoria.GetInstancia();
        loEstudio           = LoEstudio.GetInstancia();
    }
    
    public static LoCurso GetInstancia(){
        if (instancia==null)
        {
            instancia   = new LoCurso();
            
        }

        return instancia;
    }

    @Override
    public Object guardar(Curso pCurso) {
        boolean error           = false;
        Retorno_MsgObj retorno  = new Retorno_MsgObj(new Mensajes("Error al guardar curso", TipoMensaje.ERROR), pCurso);
        
        if(param.getParUtlMdl())
        {
            if(pCurso.getCurCatCod() == null)
            {
                retorno = this.Mdl_AgregarCategoria(pCurso);
            }
            else
            {
               retorno = this.Mdl_ActualizarCategoria(pCurso); 
            }

            error   =  retorno.getMensaje().getTipoMensaje() == TipoMensaje.ERROR;
        }
            
        if(!error)
        {
            pCurso  = (Curso) retorno.getObjeto();
            
            PerManejador perManager = new PerManejador();
            
            pCurso.setObjFchMod(new Date());
            
            retorno = perManager.guardar(pCurso);
            
            if(!retorno.SurgioErrorObjetoRequerido())
            {
                pCurso.setCurCod((Long) retorno.getObjeto());
                retorno.setObjeto(pCurso);
            }
        }

        return retorno;
        
    }

    @Override
    public Object actualizar(Curso pCurso) {
        boolean error           = false;
        Retorno_MsgObj retorno  = new Retorno_MsgObj(new Mensajes("Error", TipoMensaje.ERROR), pCurso);
       
        if(param.getParUtlMdl())
        {
            if(pCurso.getCurCatCod() != null)
            {
                retorno = this.Mdl_ActualizarCategoria(pCurso);
            }
            else
            {
                retorno = this.Mdl_AgregarCategoria(pCurso);
            }
            
            error   =  retorno.getMensaje().getTipoMensaje() == TipoMensaje.ERROR;
        }
        
        if(!error)
        {
            pCurso  = (Curso) retorno.getObjeto();
            
            PerManejador perManager = new PerManejador();
            
            pCurso.setObjFchMod(new Date());
            
            retorno = perManager.actualizar(pCurso);
            
            if(!retorno.SurgioError())
            {
                retorno = this.obtener(pCurso.getCurCod());
            }

        }

        return retorno;
    }

    @Override
    public Object eliminar(Curso pCurso) {
        boolean error           = false;
        Retorno_MsgObj retorno  = new Retorno_MsgObj(new Mensajes("Error", TipoMensaje.ERROR));
       
        if(param.getParUtlMdl() && pCurso.getCurCatCod() != null)
        {
            retorno = this.Mdl_EliminarCategoria(pCurso);
            error   = retorno.getMensaje().getTipoMensaje() == TipoMensaje.ERROR;
        }

        if(!error)
        {
            PerManejador perManager = new PerManejador();
            retorno = perManager.eliminar(pCurso);
        }
       
       return retorno;
    }

    @Override
    public Retorno_MsgObj obtener(Long pCurCod) {
        PerManejador perManager = new PerManejador();
        return perManager.obtener(pCurCod, Curso.class);
    }

    @Override
    public Retorno_MsgObj obtenerLista() {
        PerManejador perManager = new PerManejador();

        return perManager.obtenerLista("Curso.findAll", null);
    }
    
    //------------------------------------------------------------------------------------
    //-MANEJO DE MODULO
    //------------------------------------------------------------------------------------
    
    public Retorno_MsgObj ModuloObtener(Long ModCod)
    {
        PerManejador perManager = new PerManejador();
        return perManager.obtener(ModCod, Modulo.class);
    }
    
    public Object ModuloAgregar(Modulo modulo)
    {
        boolean error           = false;
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al agregar el modulo",TipoMensaje.ERROR), modulo);
       
        if(param.getParUtlMdl())
        {
            retorno = this.Mdl_AgregarEstudio(modulo);
            error   =  retorno.getMensaje().getTipoMensaje() == TipoMensaje.ERROR;
        }        
        
        if(!error)
        {
            modulo = (Modulo) retorno.getObjeto();
            Curso curso = modulo.getCurso();
            modulo.setObjFchMod(new Date());
            curso.getLstModulos().add(modulo);
            retorno = (Retorno_MsgObj) this.actualizar(curso);
        }
       
        
        return retorno;
    }
    
    public Object ModuloActualizar(Modulo modulo)
    {
        boolean error           = false;
        Retorno_MsgObj retorno  = new Retorno_MsgObj(new Mensajes("Error al actualizar el modulo", TipoMensaje.ERROR), modulo);
       
        if(param.getParUtlMdl() && modulo.getModEstCod() != null)
        {
            retorno = this.Mdl_ActualizarEstudio(modulo);
            error = retorno.getMensaje().getTipoMensaje() == TipoMensaje.ERROR;
        }
        
        if(!error)
        {
            modulo = (Modulo) retorno.getObjeto();
            Curso curso = modulo.getCurso();
            int indice  = curso.getLstModulos().indexOf(modulo);
            modulo.setObjFchMod(new Date());
            curso.getLstModulos().set(indice, modulo);

            retorno = (Retorno_MsgObj) this.actualizar(curso);
        }

        return retorno;
    }
    
    public Object ModuloEliminar(Modulo modulo)
    {
        boolean error           = false;
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al eliminar modulo", TipoMensaje.ERROR), modulo);
       
        if(param.getParUtlMdl() && modulo.getModEstCod() != null)
        {
            retorno = this.Mdl_EliminarEstudio(modulo);
            error = retorno.getMensaje().getTipoMensaje() == TipoMensaje.ERROR;
        }
        
        if(!error)
        {
            modulo = (Modulo) retorno.getObjeto();
            Curso curso = modulo.getCurso();
            int indice  = curso.getLstModulos().indexOf(modulo);
            curso.getLstModulos().remove(indice);

            retorno = (Retorno_MsgObj) this.actualizar(curso);
        }
        return retorno;
    }
    
    public Retorno_MsgObj ModuloPorPeriodo(Long CurCod, TipoPeriodo tpoPer, Double perVal) {
        
        PerManejador perManager = new PerManejador();

        ArrayList<SDT_Parameters> lstParametros = new ArrayList<>();
        lstParametros.add(new SDT_Parameters(tpoPer, "TpoPer"));
        lstParametros.add(new SDT_Parameters(perVal, "PerVal"));
        lstParametros.add(new SDT_Parameters(CurCod, "CurCod"));

        return perManager.obtenerLista("Modulo.findByPeriodo", lstParametros);
    }
    
    //------------------------------------------------------------------------------------
    //-MANEJO DE EVALUACION CURSO
    //------------------------------------------------------------------------------------
    public Object CursoEvaluacionAgregar(Evaluacion evaluacion)
    {
        evaluacion.setObjFchMod(new Date());
        
        Curso curso = evaluacion.getCurEvl();
        curso.getLstEvaluacion().add(evaluacion);
        
        Retorno_MsgObj retorno = (Retorno_MsgObj) this.actualizar(curso);
        
        return retorno;
    }
    
    public Object CursoEvaluacionActualizar(Evaluacion evaluacion)
    {
        evaluacion.setObjFchMod(new Date());
        
        Curso curso = evaluacion.getCurEvl();
        int indice  = curso.getLstEvaluacion().indexOf(evaluacion);
        
        curso.getLstEvaluacion().set(indice, evaluacion);

        Retorno_MsgObj retorno = (Retorno_MsgObj) this.actualizar(curso);
        
        return retorno;
    }
    
    public Object CursoEvaluacionEliminar(Evaluacion evaluacion)
    {
        Curso curso = evaluacion.getCurEvl();
        int indice  = curso.getLstEvaluacion().indexOf(evaluacion);
        
        curso.getLstEvaluacion().remove(indice);

        Retorno_MsgObj retorno = (Retorno_MsgObj) this.actualizar(curso);
        
        return retorno;
    }
    
        //------------------------------------------------------------------------------------
    //-MANEJO DE EVALUACION MODULO
    //------------------------------------------------------------------------------------
    public Object ModuloEvaluacionAgregar(Evaluacion evaluacion)
    {
        evaluacion.setObjFchMod(new Date());
        
        Modulo modulo = evaluacion.getModEvl();
        modulo.getLstEvaluacion().add(evaluacion);
        Retorno_MsgObj retorno = (Retorno_MsgObj) this.ModuloActualizar(modulo);
        
        return retorno;
    }
    
    public Object ModuloEvaluacionActualizar(Evaluacion evaluacion)
    {
        evaluacion.setObjFchMod(new Date());
        
        Modulo modulo = evaluacion.getModEvl();
        int indice  = modulo.getLstEvaluacion().indexOf(evaluacion);
        
        modulo.getLstEvaluacion().set(indice, evaluacion);

        Retorno_MsgObj retorno = (Retorno_MsgObj) this.ModuloActualizar(modulo);
        
        return retorno;
    }
    
    public Object ModuloEvaluacionEliminar(Evaluacion evaluacion)
    {
        Modulo modulo = evaluacion.getModEvl();
        int indice  = modulo.getLstEvaluacion().indexOf(evaluacion);
        
        modulo.getLstEvaluacion().remove(indice);

        Retorno_MsgObj retorno = (Retorno_MsgObj) this.ModuloActualizar(modulo);
        
        return retorno;
    }
    
    
    //--------------------------------------------------------------------------------------------------------
    //Moodle
    //--------------------------------------------------------------------------------------------------------
    
    private Retorno_MsgObj Mdl_AgregarCategoria(Curso curso)
    {
        Retorno_MsgObj retorno = loCategoria.Mdl_AgregarCategoria(curso.getCurDsc(), curso.getCurNom(), Boolean.TRUE);
        
        if(retorno.getMensaje().getTipoMensaje() != TipoMensaje.ERROR)
        {
            MoodleCategory mdlCategoria = (MoodleCategory) retorno.getObjeto();
            curso.setCurCatCod(mdlCategoria.getId());
        }
        
        retorno.setObjeto(curso);
        
        return retorno;

    }
    
    private Retorno_MsgObj Mdl_ActualizarCategoria(Curso pCurso)
    {
        Retorno_MsgObj retorno = loCategoria.Mdl_ActualizarCategoria(pCurso.getCurCatCod(), pCurso.getCurDsc(), pCurso.getCurNom(), Boolean.TRUE);
        retorno.setObjeto(pCurso);
        return retorno;
    }
    
    private Retorno_MsgObj Mdl_EliminarCategoria(Curso pCurso)
    {
        return loCategoria.Mdl_EliminarCategoria(pCurso.getCurCatCod());
    }
    
    //--------------------------------------------------------------------------------------------------------    
    
    private Retorno_MsgObj Mdl_AgregarEstudio(Modulo pModulo){
        Retorno_MsgObj retorno = loEstudio.Mdl_AgregarEstudio(pModulo.getCurso().getCurCatCod(), pModulo.getModNom(), pModulo.getModNom(), pModulo.getModDsc());
        
        if(retorno.getMensaje().getTipoMensaje() != TipoMensaje.ERROR)
        {
            MoodleCourse mdlEstudio = (MoodleCourse) retorno.getObjeto();
            pModulo.setModEstCod(mdlEstudio.getId());
        }
        
        retorno.setObjeto(pModulo);
        return retorno;

    }
    
    private Retorno_MsgObj Mdl_ActualizarEstudio(Modulo pModulo){
        Retorno_MsgObj retorno = loEstudio.Mdl_ActualizarEstudio(pModulo.getModEstCod(), pModulo.getCurso().getCurCatCod(), pModulo.getModNom(), pModulo.getModNom(), pModulo.getModDsc());
        retorno.setObjeto(pModulo);
        return retorno;
    }
    
    private Retorno_MsgObj Mdl_EliminarEstudio(Modulo pModulo){
        Retorno_MsgObj retorno = loEstudio.Mdl_EliminarEstudio(pModulo.getModEstCod());
        retorno.setObjeto(pModulo);
        return retorno;
    }
    
    //--------------------------------------------------------------------------------------------------------
    
    
}
