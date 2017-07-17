/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.Carrera;
import Utiles.Mensajes;
import Entidad.Parametro;
import Entidad.PlanEstudio;
import Enumerado.TipoMensaje;
import Moodle.MoodleCategory;
import Persistencia.PerCarrera;
import Utiles.Retorno_MsgObj;
import java.util.Date;

/**
 *
 * @author aa
 */
public class LoCarrera implements Interfaz.InCarrera{
    private static LoCarrera        instancia;
    private final PerCarrera        perCarrera;
    private final Parametro         param;
    private final LoCategoria       loCategoria;
    
    private LoCarrera(){
        perCarrera      = new PerCarrera();
        LoParametro loParam = LoParametro.GetInstancia();
        param               = loParam.obtener(1);
        loCategoria     = LoCategoria.GetInstancia();
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
            retorno     = (Retorno_MsgObj) perCarrera.guardar(pCarrera);
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
            retorno = (Retorno_MsgObj) perCarrera.actualizar(pCarrera);
        
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
            retorno = (Retorno_MsgObj) perCarrera.eliminar(pCarrera);
        }
        return retorno;
    }

    @Override
    public Retorno_MsgObj obtener(Long pCarCod) {
        return perCarrera.obtener(pCarCod);
    }

    @Override
    public Carrera obtenerByMdlUsr(String pMdlUsr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Retorno_MsgObj obtenerLista() {
        return perCarrera.obtenerLista();
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
            System.out.println("2");
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
    //-Funcionalidades en Común
    //----------------------------------------------------------------------------------------------------
    
    public Boolean Mdl_ValidarCategoria(Long cod)
    {
        MoodleCategory category = loCategoria.Mdl_ObtenerCategoria(cod);
        return (category != null);
    }
}
