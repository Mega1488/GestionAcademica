/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidad.NotificacionBandeja;
import Enumerado.BandejaEstado;
import Enumerado.BandejaTipo;
import Enumerado.TipoMensaje;
import Interfaz.InABMGenerico;
import Utiles.Mensajes;
import Utiles.Retorno_MsgObj;
import java.sql.SQLException;
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
public class PerBandeja implements InABMGenerico{
    
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
        
        NotificacionBandeja bandeja = (NotificacionBandeja) pObjeto;

        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al guardar", TipoMensaje.ERROR), bandeja);
        
        try {
            iniciaOperacion();
            bandeja.setNotBanCod((Long) sesion.save(bandeja));
            tx.commit();
            
            retorno = new Retorno_MsgObj(new Mensajes("Guardado correctamente", TipoMensaje.MENSAJE), bandeja);
            
        } catch (HibernateException he) {
            
            retorno = manejaExcepcion(he, retorno);
            
        } finally {
            sesion.close();
        }
        
        return retorno;
    }
    
    @Override
    public Object actualizar(Object pObjeto) {
        
        NotificacionBandeja bandeja = (NotificacionBandeja) pObjeto;
        
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al guardar", TipoMensaje.ERROR), bandeja);

        try {
            iniciaOperacion();
            sesion.update(bandeja);
            tx.commit();
            
            retorno = new Retorno_MsgObj(new Mensajes("Guardado correctamente", TipoMensaje.MENSAJE), bandeja);
            
        } catch (HibernateException he) {
            
            retorno = manejaExcepcion(he, retorno);
            
        } finally {
            sesion.close();
        }
        
        return retorno;
    }

    @Override
    public Object eliminar(Object pObjeto) {
        
        NotificacionBandeja bandeja = (NotificacionBandeja) pObjeto;

        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al eliminar", TipoMensaje.ERROR), null);

        try {
            iniciaOperacion();
            sesion.delete(bandeja);
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
            NotificacionBandeja bandeja = (NotificacionBandeja) sesion.get(NotificacionBandeja.class, codigo);
            retorno = new Retorno_MsgObj(new Mensajes("Ok", TipoMensaje.MENSAJE), bandeja);
            
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
            
            List<Object> listaRetorno = sesion.getNamedQuery("NotificacionBandeja.findAll").list();
            
            retorno.setMensaje(new Mensajes("Ok", TipoMensaje.MENSAJE));
            retorno.setLstObjetos(listaRetorno);
            
        } catch (HibernateException he) {
            
            retorno = manejaExcepcion(he, retorno);
            
        } finally {
            sesion.close();
        }

        return retorno;
    }
    
    public Retorno_MsgObj obtenerListaByTipoEstado(Long PerCod, BandejaTipo NotBanTpo, BandejaEstado NotBanEst) {

        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al obtener lista", TipoMensaje.ERROR), null);

        try {
            iniciaOperacion();
            
            List<Object> listaRetorno = sesion.getNamedQuery("NotificacionBandeja.findByTpoEst")
                    .setParameter("PerCod", PerCod)
                    .setParameter("NotBanTpo", NotBanTpo)
                    .setParameter("NotBanEst", NotBanEst)
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
   
}
