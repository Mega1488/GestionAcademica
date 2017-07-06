/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.Modulo;
import Entidad.Parametro;
import Enumerado.TipoMensaje;
import Moodle.MoodleCourse;
import Moodle.MoodleRestCourse;
import Persistencia.PerModulo;
import Utiles.Mensajes;
import Utiles.Retorno_MsgObj;
import java.util.List;

/**
 *
 * @author alvar
 */
public class LoModulo implements Interfaz.InModulo{
    
    private static LoModulo instancia;
    private PerModulo perModulo;
    private final Parametro         param;
    private final LoEstudio  loEstudio;

    private LoModulo() {
        perModulo           = new PerModulo();
        loEstudio           = LoEstudio.GetInstancia();
        LoParametro loParam = LoParametro.GetInstancia();
        param               = loParam.obtener(1);
    }
    
    public static LoModulo GetInstancia(){
        if (instancia==null)
        {
            instancia   = new LoModulo();
            
        }

        return instancia;
    }

    @Override
    public Object guardar(Modulo pObjeto) {
        boolean error           = false;
        Mensajes mensaje        = new Mensajes("Error", TipoMensaje.ERROR);
        Retorno_MsgObj retorno  = new Retorno_MsgObj();
        
        Modulo objeto = (Modulo) perModulo.guardar(pObjeto);
        
        if(objeto.getModCod() == null)
            {
                mensaje = new Mensajes("Error al guardar", TipoMensaje.ERROR);
            }
            else
            {
                mensaje = new Mensajes("Cambios guardados correctamente", TipoMensaje.MENSAJE);
                 
                if(param.getParUtlMdl())
                {
                    mensaje = this.Mdl_AgregarEstudio(pObjeto);

                    if(mensaje.getTipoMensaje() == TipoMensaje.ERROR)
                    {
                        perModulo.eliminar(objeto);
                    }
                }
            }
        
        retorno = new Retorno_MsgObj(mensaje, objeto);
        return retorno;
    }

    @Override
    public Object actualizar(Modulo pObjeto) {
        boolean error           = false;
        Mensajes mensaje        = new Mensajes("Error", TipoMensaje.ERROR);
        Retorno_MsgObj retorno  = new Retorno_MsgObj();
        
            Modulo sinModificar = perModulo.obtener(pObjeto);
            
            error = (boolean) perModulo.actualizar(pObjeto);
            
            if(!error)
            {
                mensaje = new Mensajes("Cambios aplicados", TipoMensaje.MENSAJE);
                
                if(param.getParUtlMdl())
                {
                    mensaje = this.Mdl_ActualizarEstudio(pObjeto);
                    
                    if(mensaje.getTipoMensaje() == TipoMensaje.ERROR)
                    {
                        this.actualizar(sinModificar);
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
    public Object eliminar(Modulo pObjeto) {
       boolean error           = false;
       Mensajes mensaje        = new Mensajes("Error", TipoMensaje.ERROR);
       Retorno_MsgObj retorno  = new Retorno_MsgObj();
       
       if(!perModulo.ValidarEliminacion(pObjeto))
       {
           if(pObjeto.getModEstCod() != null)
           {
               mensaje = this.Mdl_EliminarEstudio(pObjeto);
           }

            if(mensaje.getTipoMensaje() != TipoMensaje.ERROR || pObjeto.getModEstCod() == null)
            {
                error = (boolean) perModulo.eliminar(pObjeto);
                if(error)
                {
                    mensaje = new Mensajes("Error al impactar en la base de datos", TipoMensaje.ERROR);
                }
                else
                {
                    mensaje = new Mensajes("Eliminacion correcta", TipoMensaje.MENSAJE);
                }
            }
       }
       else
       {
           mensaje = new Mensajes("Error al impactar en la base de datos", TipoMensaje.ERROR);
       }
       
       retorno = new Retorno_MsgObj(mensaje, null);
       
       return retorno;
    }

    @Override
    public Modulo obtener(Object pCodigo) {
        return perModulo.obtener(pCodigo);
    }

    @Override
    public List<Modulo> obtenerLista() {
        return perModulo.obtenerLista();
    }
    
    public List<Modulo> obtenerListaByCurso(Integer curCod)
    {
        return perModulo.obtenerListaByCurso(curCod);
    }
    
     //--------------------------------------------------------------------------------------------------------
    //Moodle
    //--------------------------------------------------------------------------------------------------------
    
    private Mensajes Mdl_AgregarEstudio(Modulo pModulo){
        Mensajes mensaje = new Mensajes("Error al impactar en moodle", TipoMensaje.ERROR);

        Retorno_MsgObj retorno = loEstudio.Mdl_AgregarEstudio(pModulo.getCurso().getCurCatCod(), pModulo.getModNom(), pModulo.getModNom(), pModulo.getModDsc());
        
        if(retorno.getMensaje().getTipoMensaje() == TipoMensaje.ERROR)
        {    
            mensaje = retorno.getMensaje();
        }
        else
        {
            MoodleCourse mdlEstudio = (MoodleCourse) retorno.getObjeto();

            pModulo.setModEstCod(mdlEstudio.getId());
            
            this.actualizar(pModulo);
            
            mensaje = new Mensajes("Cambios correctos", TipoMensaje.MENSAJE);
        }
        
        return mensaje;

    }
    
    private Mensajes Mdl_ActualizarEstudio(Modulo pModulo){
        Mensajes mensaje = new Mensajes("Error al impactar en moodle", TipoMensaje.ERROR);

        Retorno_MsgObj retorno = loEstudio.Mdl_ActualizarEstudio(pModulo.getModEstCod(), pModulo.getCurso().getCurCatCod(), pModulo.getModNom(), pModulo.getModNom(), pModulo.getModDsc());
        
        mensaje = retorno.getMensaje();
        
        return mensaje;
    }
    
    private Mensajes Mdl_EliminarEstudio(Modulo pModulo){
        Mensajes mensaje = new Mensajes("Error al impactar en moodle", TipoMensaje.ERROR);

        Retorno_MsgObj retorno = loEstudio.Mdl_EliminarEstudio(pModulo.getModEstCod());
        
        mensaje = retorno.getMensaje();
        
        return mensaje;
    }
    
    //--------------------------------------------------------------------------------------------------------
}
