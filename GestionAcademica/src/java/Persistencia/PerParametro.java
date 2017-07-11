/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidad.Parametro;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author alvar
 */
public class PerParametro implements Interfaz.InParametro{
    
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
    public Object guardar(Parametro pObjeto) {
        
        pObjeto.setParCod(1);
        
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
    public void actualizar(Parametro pObjeto) {
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
    public Parametro obtener(Object pCodigo) {
        
        int  codigo          = (int) pCodigo;
        Parametro objetoRetorno    = new Parametro();
        
        try {
            iniciaOperacion();
            objetoRetorno = (Parametro) sesion.get(Parametro.class, codigo);            
        } finally {
            sesion.close();
        }

        return objetoRetorno;
    }

    
}
