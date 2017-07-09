/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidad.Persona;
import Enumerado.TipoMensaje;
import Utiles.Mensajes;
import Utiles.Retorno_MsgObj;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class PerPersona implements Interfaz.InPersona{
    
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
    public Object guardar(Persona pObjeto) {
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al guardar", TipoMensaje.ERROR), pObjeto);

        pObjeto.setObjFchMod(new Date());
        
        try {
            iniciaOperacion();
            pObjeto.setPerCod((Long) sesion.save(pObjeto));
            tx.commit();
            
            retorno = new Retorno_MsgObj(new Mensajes("Guardado correctamente", TipoMensaje.MENSAJE), pObjeto);
            
        } catch (HibernateException he) {
            retorno = manejaExcepcion(he, retorno);
        } finally {
            sesion.close();
        }
        
        return retorno;
        
    }

    @Override
    public Object actualizar(Persona pObjeto) {
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al actualizar", TipoMensaje.ERROR), pObjeto);
        try {
            pObjeto.setObjFchMod(new Date());
            iniciaOperacion();
            sesion.update(pObjeto);
            tx.commit();

            retorno = new Retorno_MsgObj(new Mensajes("Guardado correctamente", TipoMensaje.MENSAJE), pObjeto);
        } catch (HibernateException he) {
            
            retorno = manejaExcepcion(he, retorno);
        } finally {
            sesion.close();
        }
        
        return retorno;
    }

    @Override
    public Object eliminar(Persona pObjeto) {
       Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al eliminar", TipoMensaje.ERROR), pObjeto);
           try {
                iniciaOperacion();
                sesion.delete(pObjeto);
                tx.commit();

                retorno = new Retorno_MsgObj(new Mensajes("Eliminado correctamente", TipoMensaje.MENSAJE), pObjeto);             

           } catch (HibernateException he) {

               retorno = manejaExcepcion(he, retorno);
            } finally {
                sesion.close();
            }
         return retorno;       
    }

    @Override
    public Retorno_MsgObj obtener(Object pCodigo) {
        
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al obtener", TipoMensaje.ERROR), null);
        
        try {
                iniciaOperacion();
                Persona  objetoRetorno = (Persona) sesion.get(Persona.class, (Long) pCodigo);            
                
                retorno = new Retorno_MsgObj(new Mensajes("Ok", TipoMensaje.MENSAJE), objetoRetorno);
        } catch (HibernateException he) {

               retorno = manejaExcepcion(he, retorno);
            }  finally {
            sesion.close();
        }

        return retorno;
    }

    @Override
    public Retorno_MsgObj obtenerByMdlUsr(String pMdlUsr) {
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al obtener", TipoMensaje.ERROR), null);
        
        try {
            iniciaOperacion();
            List<Persona> listaObjeto = sesion.getNamedQuery("Persona.findByMdlUsr").setParameter("MdlUsr", pMdlUsr).setMaxResults(1).list();
            
            retorno.setMensaje(new Mensajes("Ok", TipoMensaje.MENSAJE));
            if (!listaObjeto.isEmpty()){
                
                retorno.setObjeto(listaObjeto.get(0));
            }
        
        } catch (HibernateException he) {

               retorno = manejaExcepcion(he, retorno);
            }   finally {
            sesion.close();
        }
        
        return retorno;
    }
    
    @Override
    public Retorno_MsgObj obtenerLista() {
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al obtener", TipoMensaje.ERROR), null);
        try
        {
            iniciaOperacion();
            
            List<Object> listaRetorno =  sesion.getNamedQuery("Persona.findAll").list();
            
            retorno.setMensaje(new Mensajes("Ok", TipoMensaje.MENSAJE));
            retorno.setLstObjetos(listaRetorno);
        
        } catch (HibernateException he) {
        
            retorno = manejaExcepcion(he, retorno);
        }  finally {
            sesion.close();
        }

        return retorno;
    }
    
    public Retorno_MsgObj obtenerByEmail(String pEmail) {
        
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al obtener", TipoMensaje.ERROR), null);
        try {
            iniciaOperacion();
            List<Persona> listaObjeto = sesion.getNamedQuery("Persona.findByEmail").setParameter("PerEml", pEmail).setMaxResults(1).list();
            
            retorno.setMensaje(new Mensajes("Ok", TipoMensaje.MENSAJE));
            if (!listaObjeto.isEmpty()){
                retorno.setObjeto(listaObjeto.get(0));
            }
        
        } catch (HibernateException he) {

               retorno = manejaExcepcion(he, retorno);
            }   finally {
            sesion.close();
        }

        return retorno;
    }
}
