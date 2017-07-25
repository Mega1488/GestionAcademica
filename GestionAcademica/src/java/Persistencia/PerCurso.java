/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidad.Curso;
import Entidad.Modulo;
import Enumerado.TipoMensaje;
import Enumerado.TipoPeriodo;
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
public class PerCurso implements Interfaz.InCurso{
    
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
    public Object guardar(Curso pCurso) {
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al guardar curso", TipoMensaje.ERROR), pCurso);
        
        pCurso.setObjFchMod(new Date());
        
        try {
            iniciaOperacion();
            pCurso.setCurCod((Long) sesion.save(pCurso));
            tx.commit();
            
            retorno = new Retorno_MsgObj(new Mensajes("Curso guardado correctamente", TipoMensaje.MENSAJE), pCurso);
        } catch (HibernateException he) {
            retorno = manejaExcepcion(he, retorno);
        } finally {
            sesion.close();
        }
        
        return retorno;
    }

    @Override
    public Object actualizar(Curso pCurso) {
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al actualizar curso", TipoMensaje.ERROR), pCurso);
        
        try {
            pCurso.setObjFchMod(new Date());
            System.err.println("Guardando curso: " + pCurso.getCurCod());
           
            iniciaOperacion();
            sesion.update(pCurso);
            tx.commit();
            
            retorno = new Retorno_MsgObj(new Mensajes("Curso actualizado correctamente", TipoMensaje.MENSAJE), pCurso);
        } catch (HibernateException he) {
            retorno = manejaExcepcion(he, retorno);
      
        } finally {
            sesion.close();
        }
        
        return retorno;
    }

    @Override
    public Object eliminar(Curso pCurso) {
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al eliminar", TipoMensaje.ERROR));
        try {
            iniciaOperacion();
            sesion.delete(pCurso);
            tx.commit();
            
            retorno = new Retorno_MsgObj(new Mensajes("Ok", TipoMensaje.MENSAJE));
            
        } catch (HibernateException he) {
            retorno = manejaExcepcion(he, retorno);
         
        } finally {
            sesion.close();
        }
        return retorno;
    }

    @Override
    public Retorno_MsgObj obtener(Long pCurCod) {
       
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al obtener", TipoMensaje.ERROR), null);
       
        try {
            
            iniciaOperacion();
            Curso curso   = (Curso) sesion.get(Curso.class, pCurCod);
            retorno = new Retorno_MsgObj(new Mensajes("Ok", TipoMensaje.MENSAJE), curso);
        
        } catch (HibernateException he) {
        
            retorno = manejaExcepcion(he, retorno);
        }  finally {
            sesion.close();
        }
        return retorno;
    }

    @Override
    public Retorno_MsgObj obtenerLista() {
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al obtener lista", TipoMensaje.ERROR), null);
        
        try {
            
            iniciaOperacion();
            
            List<Object> listaRetorno =  sesion.getNamedQuery("Curso.findAll").list();
            
            retorno.setMensaje(new Mensajes("Ok", TipoMensaje.MENSAJE));
            retorno.setLstObjetos(listaRetorno);
        
        } catch (HibernateException he) {
        
           retorno = manejaExcepcion(he, retorno);
        }  finally {
            sesion.close();
        }

        return retorno;
    }
    
    public Retorno_MsgObj obtenerModuloPorPeriodo(Long CurCod, TipoPeriodo tpoPer, Double perVal) {
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al obtener lista", TipoMensaje.ERROR), null);
        
        try {
            
            iniciaOperacion();
            
            List<Object> listaRetorno =  sesion.getNamedQuery("Modulo.findByPeriodo")
                    .setParameter("TpoPer", tpoPer)
                    .setParameter("PerVal", perVal)
                    .setParameter("CurCod", CurCod)
                    .list();
            
            retorno.setMensaje(new Mensajes("Ok", TipoMensaje.MENSAJE));
            retorno.setLstObjetos(listaRetorno);
        
        } catch (HibernateException he) {
        
           retorno = manejaExcepcion(he, retorno);
        }  finally {
            sesion.close();
        }

        return retorno;
    }
    
    public Retorno_MsgObj ModuloObtener(Long pModCod) {
       
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al obtener", TipoMensaje.ERROR), null);
       
        try {
            
            iniciaOperacion();
            Modulo modulo   = (Modulo) sesion.get(Modulo.class, pModCod);
            retorno = new Retorno_MsgObj(new Mensajes("Ok", TipoMensaje.MENSAJE), modulo);
        
        } catch (HibernateException he) {
        
            retorno = manejaExcepcion(he, retorno);
        }  finally {
            sesion.close();
        }
        return retorno;
    }
    
}
