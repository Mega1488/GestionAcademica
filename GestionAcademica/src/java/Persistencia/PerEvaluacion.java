/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidad.Evaluacion;
import Entidad.TipoEvaluacion;
import Enumerado.TipoMensaje;
import Interfaz.InABMGenerico;
import Interfaz.InTipoEvaluacion;
import Utiles.Mensajes;
import Utiles.Retorno_MsgObj;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author alvar
 */
public class PerEvaluacion implements InABMGenerico{
    
    private Session sesion;
    private Transaction tx;
    
   private void iniciaOperacion() throws HibernateException {
        try {
            sesion = NewHibernateUtil.getSessionFactory().openSession();
            tx = sesion.beginTransaction();
        } catch (HibernateException ec) {
            ec.printStackTrace();

        }
    }

    private Retorno_MsgObj manejaExcepcion(HibernateException he, Retorno_MsgObj retorno) throws HibernateException {
        tx.rollback();
        String mensaje;
        
        Throwable cause = he.getCause();
        if (cause instanceof SQLException) {
            switch(((SQLException) cause).getErrorCode())
            {
                case 1451:
                    mensaje = "Existen datos en otros registros";
                    break;
                default: 
                    mensaje = cause.getMessage();
                    break;
            }
        }
        else
        {
            mensaje = he.getMessage();
        }
        
        retorno.setMensaje(new Mensajes("Error: " + mensaje, TipoMensaje.ERROR));
        
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, he);
        
        return retorno;
    }

    @Override
    public Object guardar(Object pObjeto) {
        
        Evaluacion evaluacion = (Evaluacion) pObjeto;

        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al guardar", TipoMensaje.ERROR), evaluacion);

        evaluacion.setObjFchMod(new Date());
        
        try {
            iniciaOperacion();
            evaluacion.setEvlCod((Long) sesion.save(evaluacion));
            tx.commit();
            
            retorno = new Retorno_MsgObj(new Mensajes("Guardado correctamente", TipoMensaje.MENSAJE), evaluacion);
            
        } catch (HibernateException he) {
            
            retorno = manejaExcepcion(he, retorno);
            
        } finally {
            sesion.close();
        }
        
        return retorno;
    }
    
    @Override
    public Object actualizar(Object pObjeto) {
        
        Evaluacion evaluacion = (Evaluacion) pObjeto;
        
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al guardar", TipoMensaje.ERROR), evaluacion);

        try {
            evaluacion.setObjFchMod(new Date());
            iniciaOperacion();
            sesion.update(evaluacion);
            tx.commit();
            
            retorno = new Retorno_MsgObj(new Mensajes("Guardado correctamente", TipoMensaje.MENSAJE), evaluacion);
            
        } catch (HibernateException he) {
            
            retorno = manejaExcepcion(he, retorno);
            
        } finally {
            sesion.close();
        }
        
        return retorno;
    }

    @Override
    public Object eliminar(Object pObjeto) {
        
        Evaluacion evaluacion = (Evaluacion) pObjeto;

        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al eliminar", TipoMensaje.ERROR), null);

        try {
            iniciaOperacion();
            sesion.delete(evaluacion);
            tx.commit();
            
            retorno = new Retorno_MsgObj(new Mensajes("Eliminado correctamente", TipoMensaje.MENSAJE), null);
            
        } catch (HibernateException he) {
            
            retorno = manejaExcepcion(he, retorno);
        } finally {
            sesion.close();
        }
        
        return retorno;
    }

    @Override
    public Retorno_MsgObj obtener(Object pObjeto) {
        Long codigo = (Long) pObjeto;
        
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al obtener", TipoMensaje.ERROR), null);
        
        try {
            iniciaOperacion();
            Evaluacion evaluacion = (Evaluacion) sesion.get(Evaluacion.class, codigo);
            retorno = new Retorno_MsgObj(new Mensajes("Ok", TipoMensaje.MENSAJE), evaluacion);
            
        } catch (HibernateException he) {
            
            retorno = manejaExcepcion(he, retorno);
            
        } finally {
            sesion.close();
        }

        
        return retorno;
    }
    
    @Override
    public Retorno_MsgObj obtenerLista() {

        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al obtener lista", TipoMensaje.ERROR), null);

        try {
            iniciaOperacion();
            
            List<Object> listaRetorno = sesion.getNamedQuery("Evaluacion.findAll").list();
            
            retorno.setMensaje(new Mensajes("Ok", TipoMensaje.MENSAJE));
            retorno.setLstObjetos(listaRetorno);
            
        } catch (HibernateException he) {
            
            retorno = manejaExcepcion(he, retorno);
            
        } finally {
            sesion.close();
        }

        return retorno;
    }
    
}
