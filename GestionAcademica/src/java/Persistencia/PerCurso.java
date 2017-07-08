/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidad.Curso;
import Entidad.Modulo;
import Enumerado.TipoMensaje;
import Utiles.Mensajes;
import Utiles.Retorno_MsgObj;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    private void manejaExcepcion(HibernateException he) throws HibernateException {
        tx.rollback();
        throw new HibernateException("Ocurrió un error en la capa de acceso a datos", he);
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
            retorno = new Retorno_MsgObj(new Mensajes("Error al guardar curso: " + he.getMessage(), TipoMensaje.ERROR), pCurso);
            manejaExcepcion(he);
            throw he;
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
            retorno = new Retorno_MsgObj(new Mensajes("Error al actualizar curso: " + he.getMessage(), TipoMensaje.ERROR), pCurso);
            manejaExcepcion(he);
            throw he;
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
            retorno = new Retorno_MsgObj(new Mensajes("Error al eliminar: " + he.getMessage(), TipoMensaje.ERROR));
            manejaExcepcion(he);
            throw he;
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
        
            retorno = new Retorno_MsgObj(new Mensajes("Error al obtener: " + he.getMessage(), TipoMensaje.ERROR), null);
            manejaExcepcion(he);
            throw he;
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
        
            retorno = new Retorno_MsgObj(new Mensajes("Error al obtener lista: " + he.getMessage(), TipoMensaje.ERROR), null);
            manejaExcepcion(he);
            throw he;
        }  finally {
            sesion.close();
        }

        return retorno;
    }
    
}
