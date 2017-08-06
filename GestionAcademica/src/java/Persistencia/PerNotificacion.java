/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidad.Notificacion;
import Enumerado.TipoMensaje;
import Enumerado.TipoNotificacion;
import Interfaz.InABMGenerico;
import Utiles.Mensajes;
import Utiles.Retorno_MsgObj;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author alvar
 */
public class PerNotificacion implements InABMGenerico{
    
    private Session sesion;
    private Transaction tx;
    
   private void iniciaOperacion() throws HibernateException {
        try {
            sesion = NewHibernateUtil.getSessionFactory().openSession();
            tx = sesion.beginTransaction();
        } catch (HibernateException ec) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ec);
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
                case 1062:
                    mensaje = "Ya se ingreso el registro";
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
        
        Notificacion notificacion = (Notificacion) pObjeto;

        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al guardar", TipoMensaje.ERROR), notificacion);
        
        try {
            iniciaOperacion();
            notificacion.setNotCod((Long) sesion.save(notificacion));
            tx.commit();
            
            retorno = new Retorno_MsgObj(new Mensajes("Guardado correctamente", TipoMensaje.MENSAJE), notificacion);
            
        } catch (HibernateException he) {
            
            retorno = manejaExcepcion(he, retorno);
            
        } finally {
            sesion.close();
        }
        
        return retorno;
    }
    
    @Override
    public Object actualizar(Object pObjeto) {
        
        Notificacion notificacion = (Notificacion) pObjeto;
        
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al guardar", TipoMensaje.ERROR), notificacion);

        try {
            iniciaOperacion();
            sesion.update(notificacion);
            tx.commit();
            
            retorno = new Retorno_MsgObj(new Mensajes("Guardado correctamente", TipoMensaje.MENSAJE), notificacion);
            
        } catch (HibernateException he) {
            
            retorno = manejaExcepcion(he, retorno);
            
        } finally {
            sesion.close();
        }
        
        return retorno;
    }

    @Override
    public Object eliminar(Object pObjeto) {
        
        Notificacion notificacion = (Notificacion) pObjeto;

        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al eliminar", TipoMensaje.ERROR), null);

        try {
            iniciaOperacion();
            sesion.delete(notificacion);
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
            Notificacion notificacion = (Notificacion) sesion.get(Notificacion.class, codigo);
            retorno = new Retorno_MsgObj(new Mensajes("Ok", TipoMensaje.MENSAJE), notificacion);
            
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
            
            List<Object> listaRetorno = sesion.getNamedQuery("Notificacion.findAll").list();
            
            retorno.setMensaje(new Mensajes("Ok", TipoMensaje.MENSAJE));
            retorno.setLstObjetos(listaRetorno);
            
        } catch (HibernateException he) {
            
            retorno = manejaExcepcion(he, retorno);
            
        } finally {
            sesion.close();
        }

        return retorno;
    }
    
    public Retorno_MsgObj obtenerListaByTipoActiva(Boolean NotAct, TipoNotificacion NotTpo) {

        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al obtener lista", TipoMensaje.ERROR), null);

        try {
            iniciaOperacion();
            
            List<Object> listaRetorno = sesion.getNamedQuery("Notificacion.findAutoActiva")
                    .setParameter("NotAct", NotAct)
                    .setParameter("NotTpo", NotTpo)
                    .list();
            
            retorno.setMensaje(new Mensajes("Ok", TipoMensaje.MENSAJE));
            retorno.setLstObjetos(listaRetorno);
            
        } catch (HibernateException he) {
            
            retorno = manejaExcepcion(he, retorno);
            
        } finally {
            sesion.close();
        }

        return retorno;
    }
    
    public Retorno_MsgObj obtenerResultadosQuery(String sentencia) {

        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al obtener lista", TipoMensaje.ERROR), null);

        try {
            iniciaOperacion();
            
            Query query = sesion.createSQLQuery(sentencia);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            
            List list = query.list();

            retorno.setMensaje(new Mensajes("Ok", TipoMensaje.MENSAJE));
            retorno.setObjeto(list);
            
        } catch (HibernateException he) {
            
            retorno = manejaExcepcion(he, retorno);
            
        } finally {
            sesion.close();
        }

        return retorno;
    }
    
}
