/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.Carrera;
import Utiles.Mensajes;
import Entidad.Parametro;
import Enumerado.Constantes;
import Enumerado.TipoMensaje;
import Moodle.MoodleRestCourse;
import Moodle.MoodleCategory;
import Persistencia.PerCarrera;
import Utiles.Retorno_MsgObj;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aa
 */
public class LoCarrera implements Interfaz.InCarrera{
    private static LoCarrera        instancia;
    private final PerCarrera        perCarrera;
    private final MoodleRestCourse  mdlRestCourse;
    private final Parametro         param;
    private final LoCategoria       loCategoria;
    
    private LoCarrera(){
        perCarrera      = new PerCarrera();
        mdlRestCourse   = new MoodleRestCourse();
        param           = new Parametro();
        loCategoria     = LoCategoria.GetInstancia();
    }
    
    public static LoCarrera GetInstancia(){
        if(instancia == null){
            instancia = new LoCarrera();
        }
        return instancia;
    }
    
    @Override
    public Object guardar(Carrera pCarrera) {
        boolean error           = false;
        Retorno_MsgObj retorno  = new Retorno_MsgObj();
        Mensajes mensaje = new Mensajes("Error", TipoMensaje.ERROR);

        Object objeto = null;

        if(pCarrera.getCarCatCod() != null && param.getParUtlMdl())
        {
            if(!this.Mdl_ValidarCategoria(pCarrera.getCarCatCod()))
            {
                error = true;
                mensaje = new Mensajes("Categoría no valida", TipoMensaje.ERROR);
            }
        }

        if(!error)
        {
            objeto = perCarrera.guardar(pCarrera);

            if(((Carrera) objeto).getCarCod() == null)
            {
                mensaje = new Mensajes("Error al guardar", TipoMensaje.ERROR);
            }
            else
            {
                mensaje = new Mensajes("La Carrera fue Ingresada", TipoMensaje.MENSAJE);

                if(param.getParUtlMdl())
                {
                    mensaje = this.Mdl_AgregarCategoria(pCarrera);

                    if(mensaje.getTipoMensaje() == TipoMensaje.ERROR)
                    {
                        this.eliminar((Carrera) objeto);
                    }
                }
            }
        }
        retorno = new Retorno_MsgObj(mensaje, objeto);
        return retorno;
    }

    @Override
    public Object actualizar(Carrera pCarrera) {
        
        boolean error       = false;
        Carrera carr        = perCarrera.obtener(pCarrera);
        Mensajes mensaje    = new Mensajes("Error", TipoMensaje.ERROR);
        Retorno_MsgObj retorno  = new Retorno_MsgObj();
        
        error = (boolean) perCarrera.actualizar(pCarrera);

        if(!error)
        {
            mensaje = new Mensajes("Cambios aplicados", TipoMensaje.MENSAJE);

            if(param.getParUtlMdl())
            {
                mensaje = this.Mdl_ActualizarCarrera(pCarrera);

                if(mensaje.getTipoMensaje() == TipoMensaje.ERROR)
                {
                    this.actualizar(carr);
                }
            }
        }
        else
        {
            mensaje = new Mensajes("Error al aplicar cambios", TipoMensaje.ERROR);
        }
        retorno = new Retorno_MsgObj(mensaje, null);
        return retorno;
    }

    @Override
    public Object eliminar(Carrera pCarrera) {
        boolean error = false;
        perCarrera.eliminar(pCarrera);
        
        return false;
    }

    @Override
    public Carrera obtener(Carrera pCarrera) {
        return perCarrera.obtener(pCarrera);
    }

    @Override
    public Carrera obtenerByMdlUsr(String pMdlUsr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Carrera> obtenerLista() {
        return perCarrera.obtenerLista();
    }
    
    //----------------------------------------------------------------------------------------------------
    //-Modle
    //----------------------------------------------------------------------------------------------------
    
    //impacta la carrera como una categoría en moodle
    public Mensajes Mdl_AgregarCategoria(Carrera pCarrera)
    {
        Mensajes mensaje        = new Mensajes("Error al impactar en moodle", TipoMensaje.ERROR);
        Retorno_MsgObj retorno  = loCategoria.Mdl_AgregarCategoria(pCarrera.getCarDsc(), pCarrera.getCarNom(), Boolean.TRUE);
        
        if(retorno.getMensaje().getTipoMensaje() == TipoMensaje.ERROR)
        {
            mensaje = retorno.getMensaje();
        }
        else
        {
            MoodleCategory category = (MoodleCategory)retorno.getObjeto();
            
            pCarrera.setCarCatCod(category.getId());
            
            this.actualizar(pCarrera);
            
            mensaje = new Mensajes("Cambios Correctos", TipoMensaje.MENSAJE);
        }
        return mensaje;
    }
    
    public Boolean Mdl_ValidarCategoria(Long cod)
    {
        MoodleCategory category = loCategoria.Mdl_ObtenerCategoria(cod);
        return (category != null);
    }
    
    public Mensajes Mdl_ActualizarCarrera(Carrera pCarrera)
    {
        Mensajes mensaje        = new Mensajes("Error al impactar en moodle", TipoMensaje.ERROR);
        MoodleCategory category = Mdl_ObtenerCategoiaByID(pCarrera.getCarCatCod());
        
        category.setName(pCarrera.getCarNom());
        category.setDescription(pCarrera.getCarDsc());
 
//        try
//        {
//            
//        }
//        catch(Exception ex)
//        {
//            ex.printStackTrace();
//        }
        return mensaje;
    }
    
    private MoodleCategory Mdl_ObtenerCategoiaByID(Long id)
    {
        MoodleCategory category = new MoodleCategory();
        
        try
        {
//            category = mdlRestCourse.__createCategory(param.getParUrlMdl() + Constantes.URL_FOLDER_SERVICIO_MDL.getValor(), param.getParMdlTkn(), category);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return category;
    }
}
