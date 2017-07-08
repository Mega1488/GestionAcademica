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
        Retorno_MsgObj retorno  = new Retorno_MsgObj();
        Mensajes mensaje        = new Mensajes("Error al impactar en moodle", TipoMensaje.ERROR);
        
        MoodleCategory mdlCategoria = new MoodleCategory();

        mdlCategoria.setDescription(pDsc);
        mdlCategoria.setName(pNom);
        mdlCategoria.setVisible(pVisible);
        mdlCategoria.setParent(Long.valueOf("0"));
        
        try {
            mdlCategoria    = mdlCourse.__createCategory(param.getParUrlMdl() + Constantes.URL_FOLDER_SERVICIO_MDL.getValor(), param.getParMdlTkn(), mdlCategoria);
            mensaje         = new Mensajes("Cambios correctos", TipoMensaje.MENSAJE);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(LoCategoria.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MoodleRestException ex) {
            Logger.getLogger(LoCategoria.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        retorno = new Retorno_MsgObj(mensaje, mdlCategoria);

        return retorno;

    }
    
    
    public Retorno_MsgObj Mdl_ActualizarCategoria(Long codigo, String pDsc, String pNom, Boolean pVisible)
    {
        Retorno_MsgObj retorno  = new Retorno_MsgObj();
        Mensajes mensaje        = new Mensajes("Error al impactar en moodle", TipoMensaje.ERROR);
        
        MoodleCategory mdlCategoria = this.Mdl_ObtenerCategoria(codigo);

        mdlCategoria.setDescription(pDsc);
        mdlCategoria.setName(pNom);
        mdlCategoria.setVisible(pVisible);
        
        try {
            mdlCourse.__updateCategory(param.getParUrlMdl() + Constantes.URL_FOLDER_SERVICIO_MDL.getValor(), param.getParMdlTkn(), mdlCategoria);
            mensaje         = new Mensajes("Cambios correctos", TipoMensaje.MENSAJE);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(LoCategoria.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MoodleRestException ex) {
            Logger.getLogger(LoCategoria.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        retorno = new Retorno_MsgObj(mensaje, mdlCategoria);

        return retorno;

    }
    
    public Retorno_MsgObj Mdl_EliminarCategoria(Long codigo)
    {
        Retorno_MsgObj retorno  = new Retorno_MsgObj();
        Mensajes mensaje        = new Mensajes("Error al impactar en moodle", TipoMensaje.ERROR);
        MoodleCategory mdlCat   = this.Mdl_ObtenerCategoria(codigo);
        
        System.err.println("Categoria padre: " + mdlCat.getParent());
        
        try {
            mdlCourse.__deleteCategory(param.getParUrlMdl() + Constantes.URL_FOLDER_SERVICIO_MDL.getValor(), param.getParMdlTkn(), mdlCat);
            mensaje         = new Mensajes("Cambios correctos", TipoMensaje.MENSAJE);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(LoCategoria.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MoodleRestException ex) {
            Logger.getLogger(LoCategoria.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        retorno = new Retorno_MsgObj(mensaje, null);

        return retorno;

    }
    
    
    public MoodleCategory[] Mdl_ObtenerListaCategorias(){
        
        try {
            System.err.println("Token: " + param.getParMdlTkn());
            MoodleCategory[] lstCategorias = mdlCourse.__getCategories(param.getParUrlMdl() + Constantes.URL_FOLDER_SERVICIO_MDL.getValor(), param.getParMdlTkn());
            return lstCategorias;
            
        } catch (MoodleRestException ex) {
            Logger.getLogger(LoCategoria.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
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

        } catch (MoodleRestException ex) {
            Logger.getLogger(LoCategoria.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(LoCategoria.class.getName()).log(Level.SEVERE, null, ex);
        }

       return null;
    }
    
}
