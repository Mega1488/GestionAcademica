/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.Carrera;
import Utiles.Mensajes;
import Entidad.Parametro;
import Enumerado.TipoMensaje;
import Moodle.MoodleCategory;
import Persistencia.PerCarrera;
import Utiles.Retorno_MsgObj;

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
                retorno = this.Mdl_ActualizarCategoria(pCarrera);
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
        
            if(retorno.getMensaje().getTipoMensaje() != TipoMensaje.ERROR)
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
    //-Modle
    //----------------------------------------------------------------------------------------------------

    public Retorno_MsgObj Mdl_AgregarCategoria(Carrera pCarrera)
    {
        if(pCarrera == null){System.out.println("CARRERA NULL EN LO CARRERA Mdl_AgregarCategoría");}
        Retorno_MsgObj retorno  = loCategoria.Mdl_AgregarCategoria(pCarrera.getCarDsc(), pCarrera.getCarNom(), Boolean.TRUE);
        
        if(retorno.getMensaje().getTipoMensaje() == TipoMensaje.ERROR)
        {
            MoodleCategory mdlCat= (MoodleCategory) retorno.getObjeto();
            pCarrera.setCarCatCod(mdlCat.getId());
        }
        retorno.setObjeto(pCarrera);
        return retorno;
    }
    
    public Boolean Mdl_ValidarCategoria(Long cod)
    {
        MoodleCategory category = loCategoria.Mdl_ObtenerCategoria(cod);
        return (category != null);
    }
    
    private Retorno_MsgObj Mdl_ActualizarCategoria(Carrera pCarrera)
    {
        if(pCarrera == null){System.out.println("CARRERA NULL EN LO CARRERA mdl_ActualizarCategoría");}
        Retorno_MsgObj retorno = loCategoria.Mdl_ActualizarCategoria(pCarrera.getCarCatCod(), pCarrera.getCarDsc(), pCarrera.getCarNom(), Boolean.TRUE);
        retorno.setObjeto(pCarrera);
        return retorno;
    }
    
    private Retorno_MsgObj Mdl_EliminarCategoria(Carrera pCarrera)
    {
        return loCategoria.Mdl_EliminarCategoria(pCarrera.getCarCatCod());
    }
}
