/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.Curso;
import Entidad.Parametro;
import Enumerado.Constantes;
import Enumerado.TipoMensaje;
import Moodle.MoodleCategory;
import Moodle.MoodleRestCourse;
import Moodle.MoodleRestException;
import Utiles.Mensajes;
import Utiles.Retorno_MsgObj;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alvar
 */
public class LoCategoria {

    private final Parametro         param;
    private static LoCategoria      instancia;
    private final MoodleRestCourse  mdlCourse;
    

    private LoCategoria() {
        mdlCourse           = new MoodleRestCourse();
        LoParametro loParam = LoParametro.GetInstancia();
        param               = loParam.obtener(1);
    }
    
    public static LoCategoria GetInstancia(){
        if (instancia==null)
        {
            instancia   = new LoCategoria();
            
        }

        return instancia;
    }
    
    
    public Retorno_MsgObj Mdl_AgregarCategoria(String pDsc, String pNom, Boolean pVisible)
    {
        Mensajes mensaje;
        
        MoodleCategory mdlCategoria = new MoodleCategory();

        mdlCategoria.setDescription(pDsc);
        mdlCategoria.setName(pNom);
        mdlCategoria.setVisible(pVisible);
        mdlCategoria.setParent(Long.valueOf("0"));
        
        try {
            mdlCategoria    = mdlCourse.__createCategory(param.getParUrlMdl() + Constantes.URL_FOLDER_SERVICIO_MDL.getValor(), param.getParMdlTkn(), mdlCategoria);
            mensaje         = new Mensajes("Cambios correctos", TipoMensaje.MENSAJE);
        } catch (UnsupportedEncodingException | MoodleRestException ex) {
            mensaje        = new Mensajes("Error: " + ex.getMessage(), TipoMensaje.ERROR);
            Logger.getLogger(LoCategoria.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Retorno_MsgObj retorno = new Retorno_MsgObj(mensaje, mdlCategoria);

        return retorno;

    }
    
    
    public Retorno_MsgObj Mdl_ActualizarCategoria(Long codigo, String pDsc, String pNom, Boolean pVisible)
    {
        Mensajes mensaje;
        
        MoodleCategory mdlCategoria = this.Mdl_ObtenerCategoria(codigo);

        mdlCategoria.setDescription(pDsc);
        mdlCategoria.setName(pNom);
        mdlCategoria.setVisible(pVisible);
        
        try {
            mdlCourse.__updateCategory(param.getParUrlMdl() + Constantes.URL_FOLDER_SERVICIO_MDL.getValor(), param.getParMdlTkn(), mdlCategoria);
            mensaje         = new Mensajes("Cambios correctos", TipoMensaje.MENSAJE);
        } catch (UnsupportedEncodingException | MoodleRestException ex) {
            mensaje        = new Mensajes("Error: " + ex.getMessage(), TipoMensaje.ERROR);
            Logger.getLogger(LoCategoria.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Retorno_MsgObj retorno = new Retorno_MsgObj(mensaje, mdlCategoria);

        return retorno;

    }
    
    public Retorno_MsgObj Mdl_EliminarCategoria(Long codigo)
    {
        Mensajes mensaje;
        MoodleCategory mdlCat   = this.Mdl_ObtenerCategoria(codigo);
        
        try {
            mdlCourse.__deleteCategory(param.getParUrlMdl() + Constantes.URL_FOLDER_SERVICIO_MDL.getValor(), param.getParMdlTkn(), mdlCat);
            mensaje         = new Mensajes("Cambios correctos", TipoMensaje.MENSAJE);
        } catch (UnsupportedEncodingException | MoodleRestException ex) {
            mensaje        = new Mensajes("Error: " + ex.getMessage(), TipoMensaje.ERROR);
            Logger.getLogger(LoCategoria.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Retorno_MsgObj retorno = new Retorno_MsgObj(mensaje, null);

        return retorno;

    }
    
    
    public MoodleCategory[] Mdl_ObtenerListaCategorias(){
        
        try {
            MoodleCategory[] lstCategorias = mdlCourse.__getCategories(param.getParUrlMdl() + Constantes.URL_FOLDER_SERVICIO_MDL.getValor(), param.getParMdlTkn());
            return lstCategorias;
            
        } catch(MoodleRestException | UnsupportedEncodingException ex) {
            Logger.getLogger(LoCategoria.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public MoodleCategory Mdl_ObtenerCategoria(Long codigo){

        try {
            MoodleCategory[] lstCategorias = mdlCourse.__getCategories(param.getParUrlMdl() + Constantes.URL_FOLDER_SERVICIO_MDL.getValor(), param.getParMdlTkn(), codigo);
            if(lstCategorias.length > 0)
            {
                return lstCategorias[0];
            }

        } catch (MoodleRestException | UnsupportedEncodingException ex) {
            Logger.getLogger(LoCategoria.class.getName()).log(Level.SEVERE, null, ex);
        }

       return null;
    }
    
}
