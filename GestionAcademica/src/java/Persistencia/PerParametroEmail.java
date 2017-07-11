/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidad.ParametroEmail;
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
public class PerParametroEmail implements Interfaz.InParametroEmail{
    
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
    public Object guardar(ParametroEmail pObjeto) {
        
        try {
            iniciaOperacion();
            sesion.save(pObjeto);
            tx.commit();
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
        }
        
        return pObjeto;
        
    }

    @Override
    public void actualizar(ParametroEmail pObjeto) {
        try {
            iniciaOperacion();
            sesion.update(pObjeto);
            tx.commit();
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
        }
    }

    @Override
    public void eliminar(ParametroEmail pObjeto) {
        try {
            iniciaOperacion();
            sesion.delete(pObjeto);
            tx.commit();
            
            //Agregar objeto eliminado a la tabla de sincronización
            //-
            
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
        }
    }

    @Override
    public ParametroEmail obtener(Object pCodigo) {
        int  codigo                     = (int) pCodigo;
        ParametroEmail objetoRetorno    = new ParametroEmail();
        try {
                iniciaOperacion();
                objetoRetorno = (ParametroEmail) sesion.get(ParametroEmail.class, codigo);            
        } finally {
            sesion.close();
        }

        return objetoRetorno;
    }

    
    @Override
    public List<ParametroEmail> obtenerLista() {
        List<ParametroEmail> listaRetorno = null;

        try {
            iniciaOperacion();
            
                listaRetorno = sesion.getNamedQuery("ParametroEmail.findAll").list();
            
        } finally {
            sesion.close();
        }

        return listaRetorno;
    }
    
    
    
}
