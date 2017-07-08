/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidad.Carrera;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author aa
 */
public class PerCarrera implements Interfaz.InCarrera{
    
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
    public Object guardar(Carrera pCarrera) {
//        int id = 0;
        pCarrera = this.DevolverNuevoID(pCarrera);
        pCarrera.setObjFchMod(new Date());

        try {
            iniciaOperacion();
            pCarrera.setCarCod((int) sesion.save(pCarrera));
            tx.commit();
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
        }
        return pCarrera;
    }
    
    @Override
    public Object actualizar(Carrera pCarrera) {
        boolean error   = false;
        try
        {
            pCarrera.setObjFchMod(new Date());
            iniciaOperacion();
            sesion.update(pCarrera);
            tx.commit();
        }
        catch(HibernateException he)
        {
            error = true;
            manejaExcepcion(he);
            throw he;
        }
        finally
        {
            sesion.close();
        }
        return error;
    }

    @Override
    public Object eliminar(Carrera pObjeto) {
        boolean error = false;
        try {
            iniciaOperacion();
            sesion.delete(pObjeto);
            tx.commit();
            
            //Agregar objeto eliminado a la tabla de sincronización
            //-
            
        } catch (HibernateException he) {
            error = true;
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
        }
        return error;
    }
    
    public boolean ValidarEliminacion(Carrera pCarrera)
    {
        boolean error   = false;
        try
        {
            iniciaOperacion();
            sesion.delete(pCarrera);
            tx.rollback();
        }
        catch(HibernateException he)
        {
            error = true;
            manejaExcepcion(he);
            throw he;
        }
        finally
        {
            sesion.close();
        }
        return error;
    }

    @Override
    public Carrera obtener(Carrera pCarrera) {
        int codigo              = pCarrera.getCarCod();
        Carrera objetoRetorno   = new Carrera();
        try {
                iniciaOperacion();
                objetoRetorno = (Carrera) sesion.get(Carrera.class, codigo);            
        } finally {
            sesion.close();
        }
        return objetoRetorno;
    }

    @Override
    public Carrera obtenerByMdlUsr(String pMdlUsr) {
        List<Carrera> listaObjeto = new ArrayList<Carrera>(); 
        Carrera retorno = new Carrera();
        
        try {
            iniciaOperacion();
            listaObjeto = sesion.getNamedQuery("Carrera.findByMdlUsr").setParameter("MdlUsr", pMdlUsr).setMaxResults(1).list();
        } finally {
            sesion.close();
        }
        if (!listaObjeto.isEmpty()){
            retorno = listaObjeto.get(0);
        }
        
        return retorno;
    }

    @Override
    public List<Carrera> obtenerLista() {
        List<Carrera> listaRetorno = null;

        try {
            iniciaOperacion();
            
                listaRetorno = sesion.getNamedQuery("Carrera.findAll").list();
            
        } finally {
            sesion.close();
        }

        return listaRetorno;
    }
    
    private Carrera DevolverNuevoID(Carrera objeto){

        objeto.setCarCod(this.DevolverUltimoID());

        return objeto;
    }

    private int DevolverUltimoID(){
        int retorno = 1;
        List<Carrera> listaObjeto = new ArrayList<Carrera>(); 
        try {
            iniciaOperacion();
            listaObjeto = sesion.getNamedQuery("Carrera.findLastCarrera").setMaxResults(1).list();
        } finally {
            sesion.close();
        }
        if (!listaObjeto.isEmpty()){
            Carrera objeto = listaObjeto.get(0);
            retorno = objeto.getCarCod()+ 1;
        }
        return retorno;
    }

    
}