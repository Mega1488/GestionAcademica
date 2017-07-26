/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidad.Periodo;
import Entidad.PeriodoEstudio;
import Enumerado.TipoMensaje;
import Interfaz.InABMGenerico;
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
public class PerPeriodo implements InABMGenerico{
    
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
        Periodo periodo = (Periodo) pObjeto;

        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al guardar", TipoMensaje.ERROR), periodo);

        periodo.setObjFchMod(new Date());
        
        try {
            iniciaOperacion();
            periodo.setPeriCod((Long) sesion.save(periodo));
            tx.commit();
            
            retorno = new Retorno_MsgObj(new Mensajes("Guardado correctamente", TipoMensaje.MENSAJE), periodo);
            
        } catch (HibernateException he) {
            
            retorno = manejaExcepcion(he, retorno);
            
        } finally {
            sesion.close();
        }
        
        
        return retorno;
    }
    
    @Override
    public Object actualizar(Object pObjeto) {
        
        Periodo periodo = (Periodo) pObjeto;
        
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al guardar", TipoMensaje.ERROR), periodo);

        try {
            periodo.setObjFchMod(new Date());
            iniciaOperacion();
            sesion.update(periodo);
            tx.commit();
            
            retorno = new Retorno_MsgObj(new Mensajes("Guardado correctamente", TipoMensaje.MENSAJE), periodo);
            
        } catch (HibernateException he) {
            
            retorno = manejaExcepcion(he, retorno);
            
        } finally {
            sesion.close();
        }
        
        return retorno;
    }

    @Override
    public Object eliminar(Object pObjeto) {
        
        Periodo periodo = (Periodo) pObjeto;

        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al eliminar", TipoMensaje.ERROR), null);

        try {
            iniciaOperacion();
            sesion.delete(periodo);
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
            Periodo periodo = (Periodo) sesion.get(Periodo.class, codigo);
            retorno = new Retorno_MsgObj(new Mensajes("Ok", TipoMensaje.MENSAJE), periodo);
            
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
            
            List<Object> listaRetorno = sesion.getNamedQuery("Periodo.findAll").list();
            
            retorno.setMensaje(new Mensajes("Ok", TipoMensaje.MENSAJE));
            retorno.setLstObjetos(listaRetorno);
            
        } catch (HibernateException he) {
            
            retorno = manejaExcepcion(he, retorno);
            
        } finally {
            sesion.close();
        }
        
        

        return retorno;
    }
    
    public Retorno_MsgObj obtenerUltimoByMateria(Long MatCod) {
        
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al obtener lista", TipoMensaje.ERROR), null);

        try {
            iniciaOperacion();
            
            List<Object> listaRetorno = sesion.getNamedQuery("Periodo.findLastByMat")
                    .setParameter("MatCod", MatCod)
                    .setMaxResults(1)
                    .list();
            
            retorno.setMensaje(new Mensajes("Ok", TipoMensaje.MENSAJE));
             if(!listaRetorno.isEmpty())
            {
                retorno.setObjeto(listaRetorno.get(0));
            }
            
        } catch (HibernateException he) {
            
            retorno = manejaExcepcion(he, retorno);
            
        } finally {
            sesion.close();
        }
        
        

        return retorno;
    }
    
    public Retorno_MsgObj obtenerUltimoByModulo(Long ModCod) {
        
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al obtener lista", TipoMensaje.ERROR), null);

        try {
            iniciaOperacion();
            
            List<Object> listaRetorno = sesion.getNamedQuery("Periodo.findLastByMod")
                    .setParameter("ModCod", ModCod)
                    .setMaxResults(1)
                    .list();
            
            retorno.setMensaje(new Mensajes("Ok", TipoMensaje.MENSAJE));
             if(!listaRetorno.isEmpty())
            {
                retorno.setObjeto(listaRetorno.get(0));
            }
            
        } catch (HibernateException he) {
            
            retorno = manejaExcepcion(he, retorno);
            
        } finally {
            sesion.close();
        }
        
        

        return retorno;
    }
    
    public Retorno_MsgObj obtenerPeriodoEstudio(Long PeriEstCod){
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al obtener", TipoMensaje.ERROR), null);
        
        try {
            iniciaOperacion();
            tx.commit();
            PeriodoEstudio periEstudio = (PeriodoEstudio) sesion.get(PeriodoEstudio.class, PeriEstCod);
            retorno = new Retorno_MsgObj(new Mensajes("Ok", TipoMensaje.MENSAJE), periEstudio);
        } catch (HibernateException he) {
            
            retorno = manejaExcepcion(he, retorno);
            
        } finally {
            sesion.close();
        }
        

        return retorno;
    }
    
    public Retorno_MsgObj EstudioObtenerTodos()
    {
       Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al obtener lista", TipoMensaje.ERROR), null);

        try {
            iniciaOperacion();
            
            List<Object> listaRetorno = sesion.getNamedQuery("PeriodoEstudio.findAll").list();
            
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
