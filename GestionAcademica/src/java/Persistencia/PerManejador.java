/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Enumerado.TipoMensaje;
import SDT.SDT_Parameters;
import Utiles.Mensajes;
import Utiles.Retorno_MsgObj;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;

/**
 *
 * @author alvar
 */
public class PerManejador{
    
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

    public Retorno_MsgObj guardar(Object pObjeto) {
        
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al guardar", TipoMensaje.ERROR), pObjeto);
        
        try {
            iniciaOperacion();
            retorno.setObjeto((Long) sesion.save(pObjeto));
            
            tx.commit();
            
            retorno.setMensaje(new Mensajes("Guardado correctamente", TipoMensaje.MENSAJE));
            
        } catch (HibernateException he) {
            
            retorno = manejaExcepcion(he, retorno);
            
        } finally {
            sesion.close();
        }
        
        return retorno;
    }
    
    public Retorno_MsgObj actualizar(Object pObjeto) {
        
        //Notificacion notificacion = (Notificacion) pObjeto;
        
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al modificar", TipoMensaje.ERROR), pObjeto);

        try {
            iniciaOperacion();
            sesion.update(pObjeto);
            tx.commit();
            
            retorno.setMensaje(new Mensajes("Modificado correctamente", TipoMensaje.MENSAJE));
            
        } catch (HibernateException he) {
            
            retorno = manejaExcepcion(he, retorno);
            
        } finally {
            sesion.close();
        }
        
        return retorno;
    }

    public Retorno_MsgObj eliminar(Object pObjeto) {
        
        //Notificacion notificacion = (Notificacion) pObjeto;

        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al eliminar", TipoMensaje.ERROR), null);

        try {
            iniciaOperacion();
            sesion.delete(pObjeto);
            tx.commit();
            
            retorno = new Retorno_MsgObj(new Mensajes("Eliminado correctamente", TipoMensaje.MENSAJE), null);
            
        } catch (HibernateException he) {
            
            retorno = manejaExcepcion(he, retorno);
        } finally {
            sesion.close();
        }
        
        return retorno;
    }

    public Retorno_MsgObj obtener(Long pCodigo, Class clase) {
        
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al obtener", TipoMensaje.ERROR), null);
        
        try {
            iniciaOperacion();
            Object objRetorno = sesion.get(clase, pCodigo);
            retorno = new Retorno_MsgObj(new Mensajes("Ok", TipoMensaje.MENSAJE), objRetorno);
            
        } catch (HibernateException he) {
            
            retorno = manejaExcepcion(he, retorno);
            
        } finally {
            sesion.close();
        }

        return retorno;
    }
    
    public Retorno_MsgObj obtenerLista(String namedQuery, ArrayList<SDT_Parameters> parametros) {

        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al obtener lista", TipoMensaje.ERROR), null);

        try {
            iniciaOperacion();
            
            
            Query query = sesion.getNamedQuery(namedQuery);
            
            if(parametros != null)
            {
                for(SDT_Parameters parametro : parametros)
                {
                    query.setParameter(parametro.getNombre(), parametro.getObjeto());
                }
            }
            
            List<Object> listaRetorno = query.list();
            
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