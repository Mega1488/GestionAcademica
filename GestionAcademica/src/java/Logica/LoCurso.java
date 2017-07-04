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
import Persistencia.PerCurso;
import Utiles.Mensajes;
import Utiles.Retorno_MsgObj;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    private LoCurso() {
        mdlCourse           = new MoodleRestCourse();
        LoParametro loParam = LoParametro.GetInstancia();
        param               = loParam.obtener(1);
        perCurso            = new PerCurso();
        loCategoria         = LoCategoria.GetInstancia();
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
        Mensajes mensaje        = new Mensajes("Error", TipoMensaje.ERROR);
        Retorno_MsgObj retorno  = new Retorno_MsgObj();
        
        Object objeto = null;
        
        if(pCurso.getCurCatCod() != null && param.getParUtlMdl())
        {
            if(!this.Mdl_ValidarCategoria(pCurso.getCurCatCod()))
            {
                error = true;
                mensaje = new Mensajes("Categoría no valida", TipoMensaje.ERROR);
            }
        }
        
        if(!error)
        {
            objeto = perCurso.guardar(pCurso);
            
            if(((Curso) objeto).getCurCod() == null)
            {
                mensaje = new Mensajes("Error al guardar", TipoMensaje.ERROR);
            }
            else
            {
                mensaje = new Mensajes("Cambios guardados correctamente", TipoMensaje.MENSAJE);
                 
                if(param.getParUtlMdl() && pCurso.getCurCatCod() == null)
                {
                    mensaje = this.Mdl_AgregarCategoria(pCurso);

                    if(mensaje.getTipoMensaje() == TipoMensaje.ERROR)
                    {
                        this.eliminar((Curso) objeto);
                    }
                }
            }
        }
        
        retorno = new Retorno_MsgObj(mensaje, objeto);
        return retorno;
        
    }

    @Override
    public Object actualizar(Curso pCurso) {
        boolean error           = false;
        Mensajes mensaje        = new Mensajes("Error", TipoMensaje.ERROR);
        Retorno_MsgObj retorno  = new Retorno_MsgObj();
        
        if(pCurso.getCurCatCod() != null && param.getParUtlMdl())
        {
            if(!this.Mdl_ValidarCategoria(pCurso.getCurCatCod()))
            {
                error = true;
                mensaje = new Mensajes("Categoría no valida", TipoMensaje.ERROR);
            }
        }
        
        if(!error)
        {
            Curso sinModificar = perCurso.obtener(pCurso.getCurCod());
            
            error = (boolean) perCurso.actualizar(pCurso);
            
            if(!error)
            {
                mensaje = new Mensajes("Cambios aplicados", TipoMensaje.MENSAJE);
                
                if(param.getParUtlMdl())
                {
                    mensaje = this.Mdl_ActualizarCategoria(pCurso);
                    
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
        }
        
        retorno = new Retorno_MsgObj(mensaje, null);
        return retorno;
    }

    @Override
    public Object eliminar(Curso pCurso) {
        perCurso.eliminar(pCurso);
        
       boolean error           = false;
       Mensajes mensaje        = new Mensajes("Error", TipoMensaje.ERROR);
       Retorno_MsgObj retorno  = new Retorno_MsgObj();
       
       if(!perCurso.ValidarEliminacion(pCurso))
       {
           mensaje = this.Mdl_EliminarCategoria(pCurso);

            if(mensaje.getTipoMensaje() != TipoMensaje.ERROR)
            {
                error = (boolean) perCurso.eliminar(pCurso);
                if(error)
                {
                    mensaje = new Mensajes("Error al impactar en la base de datos", TipoMensaje.ERROR);
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
    public Curso obtener(int pCurCod) {
        return perCurso.obtener(pCurCod);
    }

    @Override
    public List<Curso> obtenerLista() {
        return perCurso.obtenerLista();
    }
    
    //--------------------------------------------------------------------------------------------------------
    //Moodle
    //--------------------------------------------------------------------------------------------------------
    
    private Mensajes Mdl_AgregarCategoria(Curso curso)
    {
        Mensajes mensaje = new Mensajes("Error al impactar en moodle", TipoMensaje.ERROR);

        Retorno_MsgObj retorno = loCategoria.Mdl_AgregarCategoria(curso.getCurDsc(), curso.getCurNom(), Boolean.TRUE);
        
        if(retorno.getMensaje().getTipoMensaje() == TipoMensaje.ERROR)
        {    
            mensaje = retorno.getMensaje();
        }
        else
        {
            MoodleCategory mdlCategoria = (MoodleCategory) retorno.getObjeto();

            curso.setCurCatCod(mdlCategoria.getId());
            
            this.actualizar(curso);
            
            mensaje = new Mensajes("Cambios correctos", TipoMensaje.MENSAJE);
        }
        
        return mensaje;

    }
    
    private Boolean Mdl_ValidarCategoria(Long codigo)
    {
        MoodleCategory mdlCategoria  = loCategoria.Mdl_ObtenerCategoria(codigo);
        
        return (mdlCategoria != null);

    }
    
    private Mensajes Mdl_ActualizarCategoria(Curso pCurso)
    {
        Mensajes mensaje = new Mensajes("Error al impactar en moodle", TipoMensaje.ERROR);

        Retorno_MsgObj retorno = loCategoria.Mdl_ActualizarCategoria(pCurso.getCurCatCod(), pCurso.getCurDsc(), pCurso.getCurNom(), Boolean.TRUE);
        
        mensaje = retorno.getMensaje();
        
        return mensaje;
    }
    
    private Mensajes Mdl_EliminarCategoria(Curso pCurso)
    {
        Mensajes mensaje = new Mensajes("Error al impactar en moodle", TipoMensaje.ERROR);

        Retorno_MsgObj retorno = loCategoria.Mdl_EliminarCategoria(pCurso.getCurCatCod());
        
        mensaje = retorno.getMensaje();
        
        return mensaje;
    }
    
    //--------------------------------------------------------------------------------------------------------
    
    
}
