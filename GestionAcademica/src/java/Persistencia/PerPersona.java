/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidad.Persona;
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

    private void manejaExcepcion(HibernateException he) throws HibernateException {
        tx.rollback();
        throw new HibernateException("Ocurrió un error en la capa de acceso a datos", he);
    }

    @Override
    public Object guardar(Persona pObjeto) {
        
        pObjeto = this.DevolverNuevoID(pObjeto);

        pObjeto.setObjFchMod(new Date());
        
        try {
            iniciaOperacion();
            pObjeto.setPerCod((int) sesion.save(pObjeto));
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
    public Object actualizar(Persona pObjeto) {
        boolean error = false;
        try {
            pObjeto.setObjFchMod(new Date());
            iniciaOperacion();
            sesion.update(pObjeto);
            tx.commit();
        } catch (HibernateException he) {
            error = true;
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
        }
        
        return error;
    }

    @Override
    public Object eliminar(Persona pObjeto) {
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
    
    public boolean ValidarEliminacion(Persona pObjeto)
    {
       boolean error = false;
        try {
            iniciaOperacion();
            sesion.delete(pObjeto);
            tx.rollback();
            
        } catch (HibernateException he) {
            error = true;
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
        }
                
         return error;       
    }

    @Override
    public Persona obtener(Object pCodigo) {
        Persona  objetoRetorno  = (Persona) pCodigo;
        int codigo              = objetoRetorno.getPerCod();
        try {
                iniciaOperacion();
                objetoRetorno = (Persona) sesion.get(Persona.class, codigo);            
        } finally {
            sesion.close();
        }
        return objetoRetorno;
    }

    @Override
    public Persona obtenerByMdlUsr(String pMdlUsr) {
        List<Persona> listaObjeto = new ArrayList<Persona>(); 
        Persona retorno = new Persona();
        
        try {
            iniciaOperacion();
            listaObjeto = sesion.getNamedQuery("Persona.findByMdlUsr").setParameter("MdlUsr", pMdlUsr).setMaxResults(1).list();
        } finally {
            sesion.close();
        }
        if (!listaObjeto.isEmpty()){
            retorno = listaObjeto.get(0);
        }
        
        return retorno;
    }

    
    
    @Override
    public List<Persona> obtenerLista() {
        List<Persona> listaRetorno = null;

        try {
            iniciaOperacion();
            
                listaRetorno = sesion.getNamedQuery("Persona.findAll").list();
            
        } finally {
            sesion.close();
        }

        return listaRetorno;
    }
    
    
     private Persona DevolverNuevoID(Persona objeto){

        objeto.setPerCod(this.DevolverUltimoID());
        
        return objeto;
    }
    
    private int DevolverUltimoID(){
        int retorno = 1;
        List<Persona> listaObjeto = new ArrayList<Persona>(); 
        try {
            iniciaOperacion();
            listaObjeto = sesion.getNamedQuery("Persona.findLast").setMaxResults(1).list();
        } finally {
            sesion.close();
        }
        
        if (!listaObjeto.isEmpty()){
            Persona objeto = listaObjeto.get(0);
            retorno = objeto.getPerCod() + 1;
        }
        
        return retorno;
    }
    
    public Persona obtenerByEmail(String pEmail) {
        Persona retorno = new Persona();
        List<Persona> listaObjeto = new ArrayList<Persona>(); 
        try {
            iniciaOperacion();
            listaObjeto = sesion.getNamedQuery("Persona.findByEmail").setParameter("PerEml", pEmail).setMaxResults(1).list();
        } finally {
            sesion.close();
        }
        
        if (!listaObjeto.isEmpty()){
            retorno = listaObjeto.get(0);
        }
        return retorno;
    }
}
