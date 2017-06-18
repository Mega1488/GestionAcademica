/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidad.MateriaRevalida;
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
public class PerMateriaRevalida implements Interfaz.InMateriaRevalida{
    
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
    public Object guardar(MateriaRevalida pObjeto) {
        
        pObjeto.setObjFchMod(new Date());
        
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
        
        System.err.println("ID de la revalida " + pObjeto.toString());
        
        return pObjeto;
        
    }

    @Override
    public void actualizar(MateriaRevalida pObjeto) {
        try {
            pObjeto.setObjFchMod(new Date());
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
    public void eliminar(MateriaRevalida pObjeto) {
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
    public MateriaRevalida obtener(Object pCodigo) {
        MateriaRevalida  codigo          = (MateriaRevalida) pCodigo;
        MateriaRevalida objetoRetorno    = new MateriaRevalida();
        try {
                iniciaOperacion();
                objetoRetorno = (MateriaRevalida) sesion.get(MateriaRevalida.class, codigo);            
        } finally {
            sesion.close();
        }
        return objetoRetorno;
    }

    @Override
    public List<MateriaRevalida> obtenerLista() {
        List<MateriaRevalida> listaRetorno = null;

        try {
            iniciaOperacion();
            
                listaRetorno = sesion.getNamedQuery("MateriaRevalida.findAll").list();
            
        } finally {
            sesion.close();
        }

        return listaRetorno;
    }
    
    
}
