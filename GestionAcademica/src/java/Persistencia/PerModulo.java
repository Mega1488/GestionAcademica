/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidad.Modulo;
import Entidad.Modulo.ModuloPK;
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

        pObjeto.setObjFchMod(new Date());
        
        try {
            iniciaOperacion();
            ModuloPK pk = (ModuloPK) sesion.save(pObjeto);
            pObjeto.setCurso(pk.getCurso());
            pObjeto.setModCod(pk.getModCod());
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
    public Object actualizar(Modulo pObjeto) {
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
    public Object eliminar(Modulo pObjeto) {
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

    @Override
    public Modulo obtener(Object pCodigo) {
        
        Modulo  codigo          = (Modulo) pCodigo;
        Modulo objetoRetorno    = new Modulo();
       
        List<Modulo> listaObjeto = null;

        try {
            iniciaOperacion();
            
                listaObjeto = sesion.getNamedQuery("Modulo.findByPK").setParameter("CurCod", codigo.getCurso().getCurCod()).setParameter("ModCod", codigo.getModCod()).setMaxResults(1).list();
            
        } finally {
            sesion.close();
        }
        
        if (!listaObjeto.isEmpty()){
            objetoRetorno = listaObjeto.get(0);
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
    
    public List<Modulo> obtenerListaByCurso(Integer curCod) {
        List<Modulo> listaRetorno = null;

        try {
            iniciaOperacion();
            
                listaRetorno = sesion.getNamedQuery("Modulo.findByCurso").setParameter("CurCod", curCod).list();
            
        } finally {
            sesion.close();
        }

        return listaRetorno;
    }
    
    
     private Modulo DevolverNuevoID(Modulo objeto){

        objeto.setModCod(this.DevolverUltimoID(objeto.getCurso().getCurCod()));
        
        return objeto;
    }
     
     public boolean ValidarEliminacion(Modulo pObjeto) {
        boolean error = false; 
        try {
            iniciaOperacion();
            sesion.delete(pObjeto);
            tx.rollback();
            
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
