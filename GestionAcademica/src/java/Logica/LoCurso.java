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
import Moodle.MoodleCategory;
import Moodle.MoodleCourse;
import Moodle.MoodleRestCourse;
import Persistencia.PerCurso;
import Utiles.Mensajes;
import Utiles.Retorno_MsgObj;
import java.util.Date;
import java.util.List;

/**
 *
 * @author alvar
 */
public class LoCurso implements Interfaz.InCurso{
    private final Parametro         param;
    private final MoodleRestCourse  mdlCourse;
    private final LoCategoria       loCategoria;
    private static LoCurso instancia;
    private PerCurso perCurso;
    private final LoEstudio  loEstudio;

    private LoCurso() {
        mdlCourse           = new MoodleRestCourse();
        LoParametro loParam = LoParametro.GetInstancia();
        param               = loParam.obtener(1);
        perCurso            = new PerCurso();
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
            retorno = (Retorno_MsgObj) perCurso.guardar(pCurso);
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
            retorno = (Retorno_MsgObj) perCurso.actualizar(pCurso);
            
            if(retorno.getMensaje().getTipoMensaje() != TipoMensaje.ERROR)
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
            retorno = (Retorno_MsgObj) perCurso.eliminar(pCurso);
        }
       
       return retorno;
    }

    @Override
    public Retorno_MsgObj obtener(Long pCurCod) {
        return perCurso.obtener(pCurCod);
    }

    @Override
    public Retorno_MsgObj obtenerLista() {
        return perCurso.obtenerLista();
    }
    
    //------------------------------------------------------------------------------------
    //-MANEJO DE MODULO
    //------------------------------------------------------------------------------------
    
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
