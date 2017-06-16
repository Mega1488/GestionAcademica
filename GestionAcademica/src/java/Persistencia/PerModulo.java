/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidad.Modulo;
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
public class PerModulo implements Interfaz.InModulo{
    
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
    public Object guardar(Modulo pObjeto) {
        
        pObjeto = this.DevolverNuevoID(pObjeto);

        System.err.println("Curso: " + pObjeto.getCurso().getCurCod().toString());
        System.err.println("Modulo: " + pObjeto.getModCod().toString());

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
        
        System.err.println("ID del modulo " + pObjeto.toString());
        
        return pObjeto;
        
    }

    @Override
    public void actualizar(Modulo pObjeto) {
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
    public void eliminar(Modulo pObjeto) {
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
    public Modulo obtener(Object pCodigo) {
        Modulo  codigo          = (Modulo) pCodigo;
        Modulo objetoRetorno    = new Modulo();
        try {
                iniciaOperacion();
                objetoRetorno = (Modulo) sesion.get(Modulo.class, codigo);            
        } finally {
            sesion.close();
        }
        return objetoRetorno;
    }

    @Override
    public List<Modulo> obtenerLista() {
        List<Modulo> listaRetorno = null;

        try {
            iniciaOperacion();
            
                listaRetorno = sesion.getNamedQuery("Modulo.findAll").list();
            
        } finally {
            sesion.close();
        }

        return listaRetorno;
    }
    
    
     private Modulo DevolverNuevoID(Modulo objeto){

        objeto.setModCod(this.DevolverUltimoID(objeto.getCurso().getCurCod()));
        
        return objeto;
    }
    
    private int DevolverUltimoID(int pCurCod){
        int retorno = 1;
        List<Modulo> listaObjeto = new ArrayList<Modulo>(); 
        try {
            iniciaOperacion();
            listaObjeto = sesion.getNamedQuery("Modulo.findLast").setParameter("CurCod", pCurCod).setMaxResults(1).list();
        } finally {
            sesion.close();
        }
        if (!listaObjeto.isEmpty()){
            Modulo objeto = listaObjeto.get(0);
            retorno = objeto.getModCod() + 1;
        }
        
        return retorno;
    }
}
