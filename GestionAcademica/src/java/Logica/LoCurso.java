/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.Curso;
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
    private final LoCategoria       loCategoria;
    private static LoCurso instancia;
    private final LoEstudio  loEstudio;

    private LoCurso() {
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
                retorno = this.Mdl_AgregarCategoria(0L, pCurso.getCurNom(), pCurso.getCurDsc());
            }
            else
            {
               retorno = this.Mdl_ActualizarCategoria(0L, pCurso.getCurCatCod(), pCurso.getCurNom(), pCurso.getCurDsc()); 
            }

            error   =  retorno.SurgioError();
            
            pCurso.setCurCatCod((Long) retorno.getObjeto());
            retorno.setObjeto(pCurso);
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
            if(pCurso.getCurCatCod() == null)
            {
                retorno = this.Mdl_AgregarCategoria(0L, pCurso.getCurNom(), pCurso.getCurDsc());
            }
            else
            {
               retorno = this.Mdl_ActualizarCategoria(0L, pCurso.getCurCatCod(), pCurso.getCurNom(), pCurso.getCurDsc()); 
            }

            error   =  retorno.SurgioError();
            
            pCurso.setCurCatCod((Long) retorno.getObjeto());
            retorno.setObjeto(pCurso);
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
            retorno = this.Mdl_EliminarCategoria(pCurso.getCurCatCod());
            error   = retorno.SurgioError();
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
            if(modulo.getModEstCod() == null)
            {
                retorno = this.Mdl_AgregarCategoria(modulo.getCurso().getCurCatCod(), modulo.getModNom(), modulo.getModDsc());
            }
            else
            {
               retorno = this.Mdl_ActualizarCategoria(modulo.getCurso().getCurCatCod(), modulo.getModEstCod(), modulo.getModNom(), modulo.getModDsc()); 
            }

            error   =  retorno.SurgioError();
            
            modulo.setModEstCod((Long) retorno.getObjeto());
            retorno.setObjeto(modulo);
        }        
        
        if(!error)
        {
            modulo = (Modulo) retorno.getObjeto();
            modulo.setObjFchMod(new Date());
            
            PerManejador perManejador   = new PerManejador();
            retorno = (Retorno_MsgObj) perManejador.guardar(modulo);
        }
       
        
        return retorno;
    }
    
    public Object ModuloActualizar(Modulo modulo)
    {
        boolean error           = false;
        Retorno_MsgObj retorno  = new Retorno_MsgObj(new Mensajes("Error al actualizar el modulo", TipoMensaje.ERROR), modulo);
       
        if(param.getParUtlMdl())
        {
            if(modulo.getModEstCod() == null)
            {
                retorno = this.Mdl_AgregarCategoria(modulo.getCurso().getCurCatCod(), modulo.getModNom(), modulo.getModDsc());
            }
            else
            {
               retorno = this.Mdl_ActualizarCategoria(modulo.getCurso().getCurCatCod(), modulo.getModEstCod(), modulo.getModNom(), modulo.getModDsc()); 
            }

            error   =  retorno.SurgioError();
            
            modulo.setModEstCod((Long) retorno.getObjeto());
            retorno.setObjeto(modulo);
        }  
        
        if(!error)
        {
            modulo = (Modulo) retorno.getObjeto();
            modulo.setObjFchMod(new Date());
            
            PerManejador perManejador   = new PerManejador();
            retorno = (Retorno_MsgObj) perManejador.actualizar(modulo);
        }

        return retorno;
    }
    
    public Object ModuloEliminar(Modulo modulo)
    {
        boolean error           = false;
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al eliminar modulo", TipoMensaje.ERROR), modulo);
       
        if(param.getParUtlMdl() && modulo.getModEstCod() != null)
        {
            retorno = this.Mdl_EliminarCategoria(modulo.getModEstCod());
            error = retorno.SurgioError();
        }
        
        if(!error)
        {
            modulo = (Modulo) retorno.getObjeto();
            
            PerManejador perManejador   = new PerManejador();
            retorno = (Retorno_MsgObj) perManejador.eliminar(modulo);
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
    
    //--------------------------------------------------------------------------------------------------------
    //Moodle
    //--------------------------------------------------------------------------------------------------------
    
    private Retorno_MsgObj Mdl_AgregarCategoria(Long parent, String mdlNom, String mdlDsc)
    {
        Retorno_MsgObj retorno = loCategoria.Mdl_AgregarCategoria(mdlDsc, mdlNom, Boolean.TRUE, parent);
        
        if(!retorno.SurgioErrorObjetoRequerido())
        {
            MoodleCategory mdlCategoria = (MoodleCategory) retorno.getObjeto();
            Long mdlCod = mdlCategoria.getId();
            
            retorno.setObjeto(mdlCod);
        }
        
        return retorno;

    }
    
    private Retorno_MsgObj Mdl_ActualizarCategoria(Long parent, Long mdlCod, String mdlNom, String mdlDsc)
    {
        Retorno_MsgObj retorno = loCategoria.Mdl_ActualizarCategoria(mdlCod, mdlDsc, mdlNom, Boolean.TRUE, parent);
        
        MoodleCategory mdlCategoria = (MoodleCategory) retorno.getObjeto();
        retorno.setObjeto(mdlCategoria.getId());

        return retorno;
    }
    
    private Retorno_MsgObj Mdl_EliminarCategoria(Long mdlCod)
    {
        return loCategoria.Mdl_EliminarCategoria(mdlCod);
    }
    
    //--------------------------------------------------------------------------------------------------------    
    
    private Retorno_MsgObj dpr_Mdl_AgregarEstudio(Modulo pModulo){
        Retorno_MsgObj retorno = loEstudio.Mdl_AgregarEstudio(pModulo.getCurso().getCurCatCod(), pModulo.getModNom(), pModulo.getModNom(), pModulo.getModDsc());
        
        if(retorno.getMensaje().getTipoMensaje() != TipoMensaje.ERROR)
        {
            MoodleCourse mdlEstudio = (MoodleCourse) retorno.getObjeto();
            pModulo.setModEstCod(mdlEstudio.getId());
        }
        
        retorno.setObjeto(pModulo);
        return retorno;

    }
    
    private Retorno_MsgObj dpr_Mdl_ActualizarEstudio(Modulo pModulo){
        Retorno_MsgObj retorno = loEstudio.Mdl_ActualizarEstudio(pModulo.getModEstCod(), pModulo.getCurso().getCurCatCod(), pModulo.getModNom(), pModulo.getModNom(), pModulo.getModDsc());
        retorno.setObjeto(pModulo);
        return retorno;
    }
    
    private Retorno_MsgObj dpr_Mdl_EliminarEstudio(Modulo pModulo){
        Retorno_MsgObj retorno = loEstudio.Mdl_EliminarEstudio(pModulo.getModEstCod());
        retorno.setObjeto(pModulo);
        return retorno;
    }
    
    //--------------------------------------------------------------------------------------------------------
    
    
}
