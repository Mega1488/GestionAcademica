/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.Carrera;
import Entidad.Materia;
import Utiles.Mensajes;
import Entidad.Parametro;
import Entidad.PlanEstudio;
import Enumerado.TipoMensaje;
import Enumerado.TipoPeriodo;
import Moodle.MoodleCategory;
import Moodle.MoodleCourse;
import Persistencia.PerManejador;
import SDT.SDT_Parameters;
import Utiles.Retorno_MsgObj;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author aa
 */
public class LoCarrera implements Interfaz.InCarrera{
    private static LoCarrera    instancia;
    private final Parametro     param;
    private final LoCategoria   loCategoria;
    private final LoEstudio     loEstudio;
    
    private LoCarrera(){
        param               = LoParametro.GetInstancia().obtener();
        loCategoria         = LoCategoria.GetInstancia();
        loEstudio           = LoEstudio.GetInstancia();
    }
    
    public static LoCarrera GetInstancia(){
        if(instancia == null){
            instancia = new LoCarrera();
        }
        return instancia;
    }
    
    @Override
    public Object guardar(Carrera pCarrera) 
    {
        boolean error           = false;
        Retorno_MsgObj retorno  = new Retorno_MsgObj(new Mensajes("Error al guardar la Carrera", TipoMensaje.ERROR), pCarrera);
        if(param.getParUtlMdl())
        {   
            if(pCarrera.getCarCatCod() == null)
            {
                retorno = this.Mdl_AgregarCategoria(pCarrera);
                pCarrera    = (Carrera) retorno.getObjeto();
            }
            else
            {
                retorno = this.Mdl_ActualizarCategoria(pCarrera);
                pCarrera    = (Carrera) retorno.getObjeto();
            }
            error = retorno.getMensaje().getTipoMensaje() == TipoMensaje.ERROR;
        }

        if(!error)
        {
            pCarrera    = (Carrera) retorno.getObjeto();
            
            PerManejador perManager = new PerManejador();
            
            pCarrera.setObjFchMod(new Date());
            
            retorno = perManager.guardar(pCarrera);
            
            if(!retorno.SurgioErrorObjetoRequerido())
            {
                pCarrera.setCarCod((Long) retorno.getObjeto());
                retorno.setObjeto(pCarrera);
            }
            
            
        }
        return retorno;
    }

    @Override
    public Object actualizar(Carrera pCarrera) {
        boolean error       = false;
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error", TipoMensaje.ERROR), pCarrera);
        
        if (param.getParUtlMdl())
        {
            if(pCarrera.getCarCatCod() != null)
            {
                retorno = this.Mdl_AgregarCategoria(pCarrera);
            }
            else
            {
                retorno = this.Mdl_ActualizarCategoria(pCarrera);
            }
            error = retorno.getMensaje().getTipoMensaje() == TipoMensaje.ERROR;
        }
        
        if (!error)
        {
            pCarrera = (Carrera) retorno.getObjeto();
            
            PerManejador perManager = new PerManejador();
            
            pCarrera.setObjFchMod(new Date());
            
            retorno = perManager.actualizar(pCarrera);
            
            if(!retorno.SurgioErrorObjetoRequerido())
            {
                retorno = this.obtener(pCarrera.getCarCod());
            }
        }
        return retorno;
    }

    @Override
    public Object eliminar(Carrera pCarrera) {
        boolean error       = false;
        Retorno_MsgObj retorno  = new Retorno_MsgObj(new Mensajes("Error", TipoMensaje.ERROR));        
        if(param.getParUtlMdl() && pCarrera.getCarCatCod() != null)
        {
            retorno = this.Mdl_EliminarCategoria(pCarrera);
            error   = retorno.getMensaje().getTipoMensaje() == TipoMensaje.ERROR;
        }

        if(!error)
        {
            PerManejador perManager = new PerManejador();
            retorno = (Retorno_MsgObj) perManager.eliminar(pCarrera);
        }
        return retorno;
    }

    @Override
    public Retorno_MsgObj obtener(Long pCarCod) {
        PerManejador perManager = new PerManejador();
        return perManager.obtener(pCarCod, Carrera.class);
    }

    @Override
    public Carrera obtenerByMdlUsr(String pMdlUsr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Retorno_MsgObj obtenerLista() {
        
        PerManejador perManager = new PerManejador();

        return perManager.obtenerLista("Carrera.findAll", null);
        
    }
    
    //----------------------------------------------------------------------------------------------------
    //-Manejo de Plan Estudio
    //----------------------------------------------------------------------------------------------------
    
    public Object PlanEstudioAgregar(PlanEstudio plan)
    {
        boolean error           = false;
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al agregar el Plan de Estudio",TipoMensaje.ERROR), plan);
       
        if(param.getParUtlMdl())
        {
            retorno = this.Mdl_AgregarSubCategoria(plan);
            error   =  retorno.getMensaje().getTipoMensaje() == TipoMensaje.ERROR;
        }        
        
        if(!error)
        {
            plan = (PlanEstudio) retorno.getObjeto();
            Carrera car = plan.getCarrera();
            plan.setObjFchMod(new Date());
            car.getPlan().add(plan);
            
            retorno = (Retorno_MsgObj) this.actualizar(car);
        }
        return retorno;
    }
    
    public Object PlanEstudioActualizar(PlanEstudio plan)
    {
        boolean error           = false;
        Retorno_MsgObj retorno  = new Retorno_MsgObj(new Mensajes("Error al actualizar el Plan de Estudio", TipoMensaje.ERROR), plan);
       
        if(param.getParUtlMdl() && plan.getPlaEstCatCod()!= null)
        {
            retorno = this.Mdl_ActualizarSubCategoria(plan);
            error = retorno.getMensaje().getTipoMensaje() == TipoMensaje.ERROR;
        }
        
        if(!error)
        {
            plan = (PlanEstudio) retorno.getObjeto();
            Carrera car = plan.getCarrera();
            int indice  = car.getPlan().indexOf(plan);
            plan.setObjFchMod(new Date());
            car.getPlan().set(indice, plan);

            retorno = (Retorno_MsgObj) this.actualizar(car);
        }
        return retorno;
    }
    
    public Object PlanEstudioEliminar(PlanEstudio plan)
    {
        boolean error           = false;
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al eliminar el Plan de Estudio", TipoMensaje.ERROR), plan);
       
        if(param.getParUtlMdl() && plan.getPlaEstCatCod()!= null)
        {
            retorno = this.Mdl_EliminarSubCategoria(plan);
            error = retorno.getMensaje().getTipoMensaje() == TipoMensaje.ERROR;
        }
        
        if(!error)
        {
            plan = (PlanEstudio) retorno.getObjeto();
            Carrera car = plan.getCarrera();
            int indice  = car.getPlan().indexOf(plan);
            car.getPlan().remove(indice);

            retorno = (Retorno_MsgObj) this.actualizar(car);
        }
        return retorno;
    }
    
    //----------------------------------------------------------------------------------------------------
    //-Manejo de Materia
    //----------------------------------------------------------------------------------------------------
    
    public Object MateriaAgregar(Materia mat)
    {
        boolean error           = false;
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al agregar la Materia",TipoMensaje.ERROR), mat);
       
        if(param.getParUtlMdl())
        {
            retorno = this.Mdl_AgregarEstudio(mat);
            error   =  retorno.getMensaje().getTipoMensaje() == TipoMensaje.ERROR;
        }        
        
        if(!error)
        {
            mat = (Materia) retorno.getObjeto();
            PlanEstudio plan = mat.getPlan();
            mat.setObjFchMod(new Date());
            plan.getLstMateria().add(mat);
            
            retorno = (Retorno_MsgObj) this.PlanEstudioActualizar(plan);
        } 
        return retorno;
    }
    
    public Object MateriaActualizar(Materia mat)
    {
        boolean error           = false;
        Retorno_MsgObj retorno  = new Retorno_MsgObj(new Mensajes("Error al actualizar la Materia", TipoMensaje.ERROR), mat);
       
        if(param.getParUtlMdl())
        {
            retorno = this.Mdl_ActualizarEstudio(mat);
            error = retorno.getMensaje().getTipoMensaje() == TipoMensaje.ERROR;
        }
        
        if(!error)
        {
            mat = (Materia) retorno.getObjeto();
            PlanEstudio plan = mat.getPlan();
            int indice  = plan.getLstMateria().indexOf(mat);
            mat.setObjFchMod(new Date());
            plan.getLstMateria().set(indice, mat);

            retorno = (Retorno_MsgObj) this.PlanEstudioActualizar(plan);
        }
        return retorno;
    }
    
    public Object MateriaEliminar(Materia mat)
    {
        boolean error           = false;
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al eliminar la Materia", TipoMensaje.ERROR), mat);
       
        if(param.getParUtlMdl())
        {
            retorno = this.Mdl_EliminarEstudio(mat);
            error = retorno.getMensaje().getTipoMensaje() == TipoMensaje.ERROR;
        }
        
        if(!error)
        {
            mat = (Materia) retorno.getObjeto();
            PlanEstudio plan = mat.getPlan();
            int indice  = plan.getLstMateria().indexOf(mat);
            plan.getLstMateria().remove(indice);

            retorno = (Retorno_MsgObj) this.PlanEstudioActualizar(plan);
        }
        return retorno;
    }
    
    public Retorno_MsgObj obtenerPopUp(Long PlaEstCod)
    {
        PerManejador perManager = new PerManejador();

        ArrayList<SDT_Parameters> lstParametros = new ArrayList<>();
        lstParametros.add(new SDT_Parameters(PlaEstCod, "PlaEstCod"));

        return perManager.obtenerLista("Materia.findByPlan", lstParametros);
        
    }
    
    public Retorno_MsgObj MateriaPorPeriodo(Long PlaEstCod, TipoPeriodo tpoPer, Double perVal)
    {
        
        PerManejador perManager = new PerManejador();

        ArrayList<SDT_Parameters> lstParametros = new ArrayList<>();
        lstParametros.add(new SDT_Parameters(tpoPer, "TpoPer"));
        lstParametros.add(new SDT_Parameters(perVal, "PerVal"));
        lstParametros.add(new SDT_Parameters(PlaEstCod, "PlaEstCod"));

        return perManager.obtenerLista("Materia.findByPeriodo", lstParametros);
    }
    
    //----------------------------------------------------------------------------------------------------
    //-Modle_Categoría
    //----------------------------------------------------------------------------------------------------

    public Retorno_MsgObj Mdl_AgregarCategoria(Carrera pCarrera)
    {
        Retorno_MsgObj retorno  = loCategoria.Mdl_AgregarCategoria(pCarrera.getCarDsc(), pCarrera.getCarNom(), Boolean.TRUE);
        
        if(retorno.getMensaje().getTipoMensaje() == TipoMensaje.ERROR)
        {
            MoodleCategory mdlCat= (MoodleCategory) retorno.getObjeto();
            pCarrera.setCarCatCod(mdlCat.getId());
        }
        retorno.setObjeto(pCarrera);
        return retorno;
    }
    
    private Retorno_MsgObj Mdl_ActualizarCategoria(Carrera pCarrera)
    {
        Retorno_MsgObj retorno = loCategoria.Mdl_ActualizarCategoria(pCarrera.getCarCatCod(), pCarrera.getCarDsc(), pCarrera.getCarNom(), Boolean.TRUE);
        retorno.setObjeto(pCarrera);
        return retorno;
    }
    
    private Retorno_MsgObj Mdl_EliminarCategoria(Carrera pCarrera)
    {
        return loCategoria.Mdl_EliminarCategoria(pCarrera.getCarCatCod());
    }
    
    //----------------------------------------------------------------------------------------------------
    //-Modle_SubCategoría
    //----------------------------------------------------------------------------------------------------

    public Retorno_MsgObj Mdl_AgregarSubCategoria(PlanEstudio pPlan)
    {
        Retorno_MsgObj retorno  = loCategoria.Mdl_AgregarCategoria(pPlan.getPlaEstDsc(), pPlan.getPlaEstNom(), Boolean.TRUE);
        
        if(retorno.getMensaje().getTipoMensaje() == TipoMensaje.ERROR)
        {
            MoodleCategory mdlCat= (MoodleCategory) retorno.getObjeto();
            pPlan.setPlaEstCatCod(mdlCat.getId());
        }
        retorno.setObjeto(pPlan);
        return retorno;
    }
    
    private Retorno_MsgObj Mdl_ActualizarSubCategoria(PlanEstudio pPlan)
    {
        Retorno_MsgObj retorno = loCategoria.Mdl_ActualizarCategoria(pPlan.getPlaEstCatCod(), pPlan.getPlaEstDsc(), pPlan.getPlaEstNom(), Boolean.TRUE);
        retorno.setObjeto(pPlan);
        return retorno;
    }
    
    private Retorno_MsgObj Mdl_EliminarSubCategoria(PlanEstudio pPlan)
    {
        return loCategoria.Mdl_EliminarCategoria(pPlan.getPlaEstCatCod());
    }
    
    //----------------------------------------------------------------------------------------------------
    //-Modle_Estudio
    //--------------------------------------------------------------------------------------------------------
    
    private Retorno_MsgObj Mdl_AgregarEstudio(Materia pMateria)
    {
        Retorno_MsgObj retorno = loEstudio.Mdl_AgregarEstudio(pMateria.getPlan().getPlaEstCatCod(), pMateria.getMatNom(), pMateria.getMatNom(), pMateria.getMatNom());
        
        if(retorno.getMensaje().getTipoMensaje() != TipoMensaje.ERROR)
        {
            MoodleCourse mdlEstudio = (MoodleCourse) retorno.getObjeto();
            pMateria.setMatCod(mdlEstudio.getId());
        }
        
        retorno.setObjeto(pMateria);
        return retorno;
    }
    
    private Retorno_MsgObj Mdl_ActualizarEstudio(Materia pMateria)
    {
        Retorno_MsgObj retorno = loEstudio.Mdl_ActualizarEstudio(pMateria.getMatCod(), pMateria.getPlan().getPlaEstCod(), pMateria.getMatNom(), pMateria.getMatNom(), pMateria.getMatNom());
        retorno.setObjeto(pMateria);
        return retorno;
    }
    
    private Retorno_MsgObj Mdl_EliminarEstudio(Materia pMateria)
    {
        Retorno_MsgObj retorno = loEstudio.Mdl_EliminarEstudio(pMateria.getMatCod());
        retorno.setObjeto(pMateria);
        return retorno;
    }
    
//----------------------------------------------------------------------------------------------------
    //-Funcionalidades en Común
    //----------------------------------------------------------------------------------------------------
    
    public Boolean Mdl_ValidarCategoria(Long cod)
    {
        MoodleCategory category = loCategoria.Mdl_ObtenerCategoria(cod);
        return (category != null);
    }

}
