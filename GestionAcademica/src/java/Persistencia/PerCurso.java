/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidad.Curso;
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
    public int guardar(Curso pCurso) {
        int id = 0;
        pCurso = this.DevolverNuevoID(pCurso);
        pCurso.setObjFchMod(new Date());
        
        try {
            iniciaOperacion();
            id = (int) sesion.save(pCurso);
            tx.commit();
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
        }
        
        return id;
    }

    @Override
    public void actualizar(Curso pCurso) {
         try {
            pCurso.setObjFchMod(new Date());
            iniciaOperacion();
            sesion.update(pCurso);
            tx.commit();
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
        }
    }

    @Override
    public void eliminar(Curso pCurso) {
        try {
            iniciaOperacion();
            sesion.delete(pCurso);
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
    public Curso obtener(int pCurCod) {
       Curso objetoRetorno = new Curso();
        try {
            iniciaOperacion();
                objetoRetorno = (Curso) sesion.get(Curso.class, pCurCod);
            
        } finally {
            sesion.close();
        }
        return objetoRetorno;
    }

    @Override
    public List<Curso> obtenerLista() {
        List<Curso> listaRetorno = null;

        try {
            iniciaOperacion();
            
                listaRetorno = sesion.getNamedQuery("Curso.findAll").list();
            
        } finally {
            sesion.close();
        }

        return listaRetorno;
    }
    
    private Curso DevolverNuevoID(Curso objeto){

        objeto.setCurCod(this.DevolverUltimoID());
        
        return objeto;
    }
    
    private int DevolverUltimoID(){
        int retorno = 1;
        List<Curso> listaObjeto = new ArrayList<Curso>(); 
        try {
            iniciaOperacion();
            listaObjeto = sesion.getNamedQuery("Curso.findLastCurso").setMaxResults(1).list();
        } finally {
            sesion.close();
        }
        if (!listaObjeto.isEmpty()){
            Curso objeto = listaObjeto.get(0);
            retorno = objeto.getCurCod() + 1;
        }
        
        return retorno;
    }
    
}
