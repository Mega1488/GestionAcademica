/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidad.TipoEvaluacion;
import Interfaz.InTipoEvaluacion;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author alvar
 */
public class PerTipoEvaluacion implements InTipoEvaluacion{
    
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
    public int guardar(TipoEvaluacion objeto) {

        int id = 0;
        objeto = this.DevolverNuevoID(objeto);

        try {
            iniciaOperacion();
            id = (int) sesion.save(objeto);
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
    public void actualizar(TipoEvaluacion objeto) {
        try {
            iniciaOperacion();
            sesion.update(objeto);
            tx.commit();
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
        }
    }

    @Override
    public void eliminar(TipoEvaluacion objeto) {
        try {
            iniciaOperacion();
            sesion.delete(objeto);
            tx.commit();
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
        }
    }

    @Override
    public TipoEvaluacion obtener(int codigo) {
        TipoEvaluacion objetoRetorno = new TipoEvaluacion();
        try {
            iniciaOperacion();
                objetoRetorno = (TipoEvaluacion) sesion.get(TipoEvaluacion.class, codigo);
            
        } finally {
            sesion.close();
        }
        return objetoRetorno;
    }
    
    @Override
    public List<TipoEvaluacion> obtenerLista() {
        List<TipoEvaluacion> listaRetorno = null;

        try {
            iniciaOperacion();
            
                listaRetorno = sesion.getNamedQuery("TipoEvaluacion.findAll").list();
            
        } finally {
            sesion.close();
        }

        return listaRetorno;
    }
    
    private TipoEvaluacion DevolverNuevoID(TipoEvaluacion objeto){

        objeto.setTpoEvlCod(this.DevolverUltimoID());
        
        return objeto;
    }
    
    private int DevolverUltimoID(){
        int retorno = 1;
        List<TipoEvaluacion> listaTpoEvaluacion = new ArrayList<TipoEvaluacion>(); 
        try {
            iniciaOperacion();
            listaTpoEvaluacion = sesion.getNamedQuery("TipoEvaluacion.findLastTpoEvl").setMaxResults(1).list();
        } finally {
            sesion.close();
        }
        if (!listaTpoEvaluacion.isEmpty()){
            TipoEvaluacion tpoEvaluacion = listaTpoEvaluacion.get(0);
            retorno = tpoEvaluacion.getTpoEvlCod() + 1;
        }
        
        return retorno;
    }
    
    
}
