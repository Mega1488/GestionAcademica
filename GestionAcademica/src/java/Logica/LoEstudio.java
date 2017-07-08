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
import Moodle.MoodleCourse;
import Moodle.MoodleCourse;
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
public class LoEstudio {

    private final Parametro         param;
    private static LoEstudio      instancia;
    private final MoodleRestCourse  mdlCourse;
    

    private LoEstudio() {
        mdlCourse           = new MoodleRestCourse();
        LoParametro loParam = LoParametro.GetInstancia();
        param               = loParam.obtener(1);
    }
    
    public static LoEstudio GetInstancia(){
        if (instancia==null)
        {
            instancia   = new LoEstudio();
            
        }

        return instancia;
    }
    
    
    public Retorno_MsgObj Mdl_AgregarEstudio(Long pCategory, String pFullName, String pShortName, String pDescripcion)
    {
        Mensajes mensaje;
        
        MoodleCourse mdlEstudio = new MoodleCourse();

        mdlEstudio.setCategoryId(pCategory);
        mdlEstudio.setFullname(pFullName);
        mdlEstudio.setShortname(pShortName);
        mdlEstudio.setSummary(pDescripcion);
        
        try {
            mdlEstudio    = mdlCourse.__createCourse(param.getParUrlMdl() + Constantes.URL_FOLDER_SERVICIO_MDL.getValor(), param.getParMdlTkn(), mdlEstudio);
            mensaje         = new Mensajes("Cambios correctos", TipoMensaje.MENSAJE);

        } catch (UnsupportedEncodingException | MoodleRestException ex) {
            mensaje         = new Mensajes("Error: " + ex.getMessage(), TipoMensaje.ERROR);
            Logger.getLogger(LoEstudio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Retorno_MsgObj retorno = new Retorno_MsgObj(mensaje, mdlEstudio);

        return retorno;

    }
    
    
    public Retorno_MsgObj Mdl_ActualizarEstudio(Long pCodigo, Long pCategory, String pFullName, String pShortName, String pDescripcion)
    {
        Mensajes mensaje;
        
        MoodleCourse mdlEstudio = this.Mdl_ObtenerEstudio(pCodigo);

        mdlEstudio.setCategoryId(pCategory);
        mdlEstudio.setFullname(pFullName);
        mdlEstudio.setShortname(pShortName);
        mdlEstudio.setSummary(pDescripcion);
        
        
        try {
            mdlCourse.__updateCourse(param.getParUrlMdl() + Constantes.URL_FOLDER_SERVICIO_MDL.getValor(), param.getParMdlTkn(), mdlEstudio);
            mensaje         = new Mensajes("Cambios correctos", TipoMensaje.MENSAJE);
    
        } catch (UnsupportedEncodingException | MoodleRestException ex) {
            mensaje         = new Mensajes("Error: " + ex.getMessage(), TipoMensaje.ERROR);
            Logger.getLogger(LoEstudio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Retorno_MsgObj retorno = new Retorno_MsgObj(mensaje, mdlEstudio);

        return retorno;

    }
    
    public Retorno_MsgObj Mdl_EliminarEstudio(Long codigo)
    {
        Mensajes mensaje;
        
        try {
            mdlCourse.__deleteCourse(param.getParUrlMdl() + Constantes.URL_FOLDER_SERVICIO_MDL.getValor(), param.getParMdlTkn(), codigo);
            mensaje         = new Mensajes("Cambios correctos", TipoMensaje.MENSAJE);
        } catch (UnsupportedEncodingException | MoodleRestException ex) {
            mensaje         = new Mensajes("Error: " + ex.getMessage(), TipoMensaje.ERROR);
            Logger.getLogger(LoEstudio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Retorno_MsgObj retorno  = new Retorno_MsgObj(mensaje, null);

        return retorno;

    }
    
    
    public MoodleCourse Mdl_ObtenerEstudio(Long codigo){

        try {
            MoodleCourse course = mdlCourse.__getCourseFromId(param.getParUrlMdl() + Constantes.URL_FOLDER_SERVICIO_MDL.getValor(), param.getParMdlTkn(), codigo);
            
            return course;

        } catch (MoodleRestException | UnsupportedEncodingException ex) {
            Logger.getLogger(LoEstudio.class.getName()).log(Level.SEVERE, null, ex);
        }

       return null;
    }
    
}
