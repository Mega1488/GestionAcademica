/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidad.Carrera;
import Enumerado.TipoMensaje;
import Utiles.Mensajes;
import Utiles.Retorno_MsgObj;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private Retorno_MsgObj manejaExcepcion(HibernateException he, Retorno_MsgObj retorno) throws HibernateException 
    {     
        tx.rollback();
        String mensaje;
        Throwable cause = he.getCause();
        if(cause instanceof SQLException)
        {
            switch (((SQLException) cause).getErrorCode())
            {
                case 1451:
                    mensaje = "Existen datos en otros registros";
                    break;
                default:
                    mensaje = cause.getMessage();
                            break;
            }
        }
        else
        {
            mensaje = he.getMessage();
        }
        retorno.setMensaje(new Mensajes("Error: " + mensaje, TipoMensaje.ERROR));
        
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, he);
        
        return retorno;
    }

    @Override
    public Object guardar(Carrera pCarrera) {
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al guardar la Carrera", TipoMensaje.ERROR), pCarrera);
        
        pCarrera.setObjFchMod(new Date());
        
        try {
            iniciaOperacion();
            pCarrera.setCarCod((Long) sesion.save(pCarrera));
            tx.commit();
            
            retorno = new Retorno_MsgObj(new Mensajes("Carrera guardada correctamente", TipoMensaje.MENSAJE), pCarrera);
        } catch (HibernateException he) {
            retorno = manejaExcepcion(he, retorno);
        } finally {
            sesion.close();
        }
        return retorno;
    }
    
    @Override
    public Object actualizar(Carrera pCarrera) {
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al Modificar la Carrera", TipoMensaje.ERROR), pCarrera);
        try
        {
            pCarrera.setObjFchMod(new Date());
            iniciaOperacion();
            sesion.update(pCarrera);
            tx.commit();
            
            retorno = new Retorno_MsgObj(new Mensajes("Carrera modificada correctamente", TipoMensaje.MENSAJE), pCarrera);
        }
        catch(HibernateException he)
        {
            retorno = manejaExcepcion(he, retorno);
        }
        finally
        {
            sesion.close();
        }
        return retorno;
    }

    @Override
    public Object eliminar(Carrera pObjeto) {
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al eliminar la curso", TipoMensaje.ERROR));
        try {
            iniciaOperacion();
            sesion.delete(pObjeto);
            tx.commit();
            
            retorno = new Retorno_MsgObj(new Mensajes("Carrera eliminada correctamente", TipoMensaje.MENSAJE));
        } catch (HibernateException he) {
            retorno = manejaExcepcion(he, retorno);
        } finally {
            sesion.close();
        }
        return retorno;
    }
   
    @Override
    public Retorno_MsgObj obtener(Long pCarCod) {
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al obtener", TipoMensaje.ERROR), null);
        try {
                iniciaOperacion();
                Carrera car = (Carrera) sesion.get(Carrera.class, pCarCod);
                retorno     = new Retorno_MsgObj(new Mensajes("OK", TipoMensaje.MENSAJE), car );
        }catch(HibernateException he)
        {
            retorno = manejaExcepcion(he, retorno);
        }
        finally
        {
            sesion.close();
        }
        return retorno;
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
    public Retorno_MsgObj obtenerLista() {
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al obtener la lista de Carreras", TipoMensaje.ERROR), null);
        try {
            iniciaOperacion();
            List<Object> listaRetorno = sesion.getNamedQuery("Carrera.findAll").list();

            retorno.setMensaje(new Mensajes("Lista de carreras OK", TipoMensaje.MENSAJE));
            retorno.setLstObjetos(listaRetorno);
        }catch(HibernateException he){
            retorno = manejaExcepcion(he, retorno);
        } finally {
            sesion.close();
        }
        return retorno;
    }
    
    private Carrera DevolverNuevoID(Carrera objeto){

        objeto.setCarCod(this.DevolverUltimoID());

        return objeto;
    }

    private Long DevolverUltimoID(){
        Long retorno = 1L;
        List<Carrera> listaObjeto = new ArrayList<Carrera>(); 
        try {
            iniciaOperacion();
            listaObjeto = sesion.getNamedQuery("Carrera.findLastCarrera").setMaxResults(1).list();
        } finally {
            sesion.close();
        }
        if (!listaObjeto.isEmpty()){
            Carrera objeto = listaObjeto.get(0);
            retorno = Long.valueOf(objeto.getCarCod().intValue() + 1);
        }
        return retorno;
    }

    
}