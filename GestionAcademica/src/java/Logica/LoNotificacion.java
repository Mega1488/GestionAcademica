/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.Notificacion;
import Entidad.NotificacionBitacora;
import Entidad.NotificacionConsulta;
import Entidad.NotificacionDestinatario;
import Enumerado.TipoMensaje;
import Enumerado.TipoNotificacion;
import Interfaz.InABMGenerico;
import Persistencia.PerManejador;
import SDT.SDT_Parameters;
import Utiles.Mensajes;
import Utiles.Retorno_MsgObj;
import java.util.ArrayList;

/**
 *
 * @author alvar
 */
public class LoNotificacion implements InABMGenerico{

    private static LoNotificacion instancia;

    private LoNotificacion() {
    }
    
    public static LoNotificacion GetInstancia(){
        if (instancia==null)
        {
            instancia   = new LoNotificacion();
        }

        return instancia;
    }
    

    @Override
    public Object guardar(Object pObjeto) {
        Notificacion not = (Notificacion) pObjeto;
        
        PerManejador perManager = new PerManejador();
            
        Retorno_MsgObj retorno = perManager.guardar(not);

        if(!retorno.SurgioErrorObjetoRequerido())
        {
            not.setNotCod((Long) retorno.getObjeto());
            retorno.setObjeto(not);
        }
            
        return retorno; 
    }

    @Override
    public Object actualizar(Object pObjeto) {
        PerManejador perManager = new PerManejador();
        return perManager.actualizar(pObjeto);
    }

    @Override
    public Object eliminar(Object pObjeto) {
        PerManejador perManager = new PerManejador();
        return perManager.eliminar(pObjeto);
    }

    @Override
    public Retorno_MsgObj obtener(Object pObjeto) {
        PerManejador perManager = new PerManejador();
        return perManager.obtener((Long) pObjeto, Notificacion.class);
    }

    @Override
    public Retorno_MsgObj obtenerLista() {
        PerManejador perManager = new PerManejador();

        return perManager.obtenerLista("Notificacion.findAll", null);
    }
    
    public Retorno_MsgObj obtenerListaByTipoActiva(Boolean NotAct, TipoNotificacion NotTpo) {
        
        PerManejador perManager = new PerManejador();
        
        ArrayList<SDT_Parameters> lstParametros = new ArrayList<>();
        lstParametros.add(new SDT_Parameters(NotAct, "NotAct"));
        lstParametros.add(new SDT_Parameters(NotTpo, "NotTpo"));
        
        return perManager.obtenerLista("Notificacion.findAutoActiva", lstParametros);
    }
    
    public Retorno_MsgObj obtenerResultadosQuery(String query){
        
        Retorno_MsgObj retorno = this.ValidarQuery(query);
        
        if(!retorno.SurgioError())
        {
            PerManejador perManager = new PerManejador();
            retorno = perManager.obtenerResultadosQuery(query);
        }
        
        return retorno;
    }
    
    public Retorno_MsgObj ValidarQuery(String query){
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Ok", TipoMensaje.MENSAJE));
        
        if(query.toLowerCase().contains(";")) retorno.setMensaje(new Mensajes("La query contiene el caracter ';'", TipoMensaje.ERROR));
        if(query.toLowerCase().contains("insert")) retorno.setMensaje(new Mensajes("La query contiene el caracter 'insert'", TipoMensaje.ERROR));
        if(query.toLowerCase().contains("update")) retorno.setMensaje(new Mensajes("La query contiene el caracter 'update'", TipoMensaje.ERROR));
        if(query.toLowerCase().contains("drop")) retorno.setMensaje(new Mensajes("La query contiene el caracter 'drop'", TipoMensaje.ERROR));
        if(query.toLowerCase().contains("delete")) retorno.setMensaje(new Mensajes("La query contiene el caracter 'delete'", TipoMensaje.ERROR));
        if(query.toLowerCase().contains("create")) retorno.setMensaje(new Mensajes("La query contiene el caracter 'create'", TipoMensaje.ERROR));
        if(query.toLowerCase().contains("alter")) retorno.setMensaje(new Mensajes("La query contiene el caracter 'alter'", TipoMensaje.ERROR));
        
        return retorno;
    }
    
    
    //------------------------------------------------------------------------------------
    //-MANEJO DE DESTINATARIOS
    //------------------------------------------------------------------------------------
    
    public Retorno_MsgObj DestinatarioAgregar(NotificacionDestinatario destinatario){
        Retorno_MsgObj retorno = this.ValidarDestinatario(destinatario);
        Boolean error = retorno.SurgioError();
        
        if(!error)
        {
            Notificacion notificacion = destinatario.getNotificacion();
            notificacion.getLstDestinatario().add(destinatario);
            retorno = (Retorno_MsgObj) this.actualizar(notificacion);
        }
        
        return retorno;
    }
    
    public Retorno_MsgObj DestinatarioActualizar(NotificacionDestinatario destinatario){
        
        Retorno_MsgObj retorno = this.ValidarDestinatario(destinatario);
        Boolean error = retorno.SurgioError();
        
        if(!error)
        {
            Notificacion notificacion = destinatario.getNotificacion();
            int indice  = notificacion.getLstDestinatario().indexOf(destinatario);
            notificacion.getLstDestinatario().set(indice, destinatario);
            retorno = (Retorno_MsgObj) this.actualizar(notificacion);
        }
        
        return retorno;
    }
    
    public Retorno_MsgObj DestinatarioEliminar(NotificacionDestinatario destinatario){
        Retorno_MsgObj retorno = this.ValidarDestinatario(destinatario);
        Boolean error = retorno.SurgioError();
        
        if(!error)
        {
            Notificacion notificacion = destinatario.getNotificacion();
            int indice  = notificacion.getLstDestinatario().indexOf(destinatario);
            notificacion.getLstDestinatario().remove(indice);
            retorno = (Retorno_MsgObj) this.actualizar(notificacion);
        }
        
        return retorno;
    }
    
    public Retorno_MsgObj ValidarDestinatario(NotificacionDestinatario destinatario){
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Ok", TipoMensaje.MENSAJE));
        
        if(destinatario == null)
        {
            retorno.setMensaje(new Mensajes("No se recibio destinatario", TipoMensaje.ERROR));
            
        }
        else{
            if(destinatario.getNotificacion() == null)
            {
                retorno.setMensaje(new Mensajes("No se recibio notificacion", TipoMensaje.ERROR));
            }
            else
            {
                if(destinatario.getPersona() == null && destinatario.getNotEmail() == null)
                {
                    retorno.setMensaje(new Mensajes("No se recibio destinatario", TipoMensaje.ERROR));
                }
                else
                {
                    if(destinatario.getPersona() != null && destinatario.getNotEmail() != null)
                    {
                        retorno.setMensaje(new Mensajes("Se debe indicar una persona, o un email, no ambos.", TipoMensaje.ERROR));
                    }
                }
            }
        }
        
        return retorno;
    }
    
    //------------------------------------------------------------------------------------
    //-MANEJO DE CONSULTAS
    //------------------------------------------------------------------------------------
    
    public Retorno_MsgObj ConsultaAgregar(NotificacionConsulta consulta){
        Retorno_MsgObj retorno = this.ValidarConsulta(consulta);
        Boolean error = retorno.SurgioError();
        
        if(!error)
        {
            Notificacion notificacion = consulta.getNotificacion();
            notificacion.getLstConsulta().add(consulta);
            retorno = (Retorno_MsgObj) this.actualizar(notificacion);
        }
        
        return retorno;
    }
    
    public Retorno_MsgObj ConsultaActualizar(NotificacionConsulta consulta){
        
        Retorno_MsgObj retorno = this.ValidarConsulta(consulta);
        Boolean error = retorno.SurgioError();
        
        if(!error)
        {
            Notificacion notificacion = consulta.getNotificacion();
            int indice  = notificacion.getLstConsulta().indexOf(consulta);
            notificacion.getLstConsulta().set(indice, consulta);
            retorno = (Retorno_MsgObj) this.actualizar(notificacion);
        }
        
        return retorno;
    }
    
    public Retorno_MsgObj ConsultaEliminar(NotificacionConsulta consulta){
        Retorno_MsgObj retorno = this.ValidarConsulta(consulta);
        Boolean error = retorno.SurgioError();
        
        if(!error)
        {
            Notificacion notificacion = consulta.getNotificacion();
            int indice  = notificacion.getLstConsulta().indexOf(consulta);
            notificacion.getLstConsulta().remove(indice);
            retorno = (Retorno_MsgObj) this.actualizar(notificacion);
        }
        
        return retorno;
    }
    
    public Retorno_MsgObj ValidarConsulta(NotificacionConsulta consulta){
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Ok", TipoMensaje.MENSAJE));
        
        if(consulta == null)
        {
            retorno.setMensaje(new Mensajes("No se recibio consulta", TipoMensaje.ERROR));
            
        }
        else{
            if(consulta.getNotificacion() == null)
            {
                retorno.setMensaje(new Mensajes("No se recibio notificacion", TipoMensaje.ERROR));
            }
            else
            {
                if(consulta.getNotCnsSQL() == null)
                {
                    retorno.setMensaje(new Mensajes("No se recibio consulta", TipoMensaje.ERROR));
                }
                else
                {
                    if(consulta.getNotCnsSQL().isEmpty())
                    {
                        retorno.setMensaje(new Mensajes("No se recibio tipo de consulta", TipoMensaje.ERROR));
                    }
                    else
                    {
                        if(consulta.getNotCnsTpo() == null)
                        {
                            retorno.setMensaje(new Mensajes("No se recibio tipo de consulta", TipoMensaje.ERROR));
                        }
                    }
                }
            }
        }
        
        return retorno;
    }
    
    //------------------------------------------------------------------------------------
    //-MANEJO DE BITACORA
    //------------------------------------------------------------------------------------
    
    public Retorno_MsgObj BitacoraAgregar(NotificacionBitacora bitacora){
        Retorno_MsgObj retorno = this.ValidarBitacora(bitacora);
        Boolean error = retorno.SurgioError();
        
        if(!error)
        {
            Notificacion notificacion = bitacora.getNotificacion();
            notificacion.getLstBitacora().add(bitacora);
            retorno = (Retorno_MsgObj) this.actualizar(notificacion);
        }
        
        retorno.setObjeto(bitacora);
        
        return retorno;
    }
    
    public Retorno_MsgObj BitacoraActualizar(NotificacionBitacora bitacora){
        
        Retorno_MsgObj retorno = this.ValidarBitacora(bitacora);
        Boolean error = retorno.SurgioError();
        
        if(!error)
        {
            Notificacion notificacion = bitacora.getNotificacion();
            int indice  = notificacion.getLstBitacora().indexOf(bitacora);
            
            System.err.println("Notificacion: " + notificacion.getLstBitacora().size());
            System.err.println("Bitacora: " + bitacora.getNotBitCod());
                        
            notificacion.getLstBitacora().set(indice, bitacora);
            retorno = (Retorno_MsgObj) this.actualizar(notificacion);
        }
        
        retorno.setObjeto(bitacora);
        
        return retorno;
    }
    
    public Retorno_MsgObj BitacoraEliminar(NotificacionBitacora bitacora){
        Retorno_MsgObj retorno = this.ValidarBitacora(bitacora);
        Boolean error = retorno.SurgioError();
        
        if(!error)
        {
            Notificacion notificacion = bitacora.getNotificacion();
            int indice  = notificacion.getLstBitacora().indexOf(bitacora);
            notificacion.getLstBitacora().remove(indice);
            retorno = (Retorno_MsgObj) this.actualizar(notificacion);
        }
        
        return retorno;
    }
    
    public Retorno_MsgObj BitacoraDepurar(Notificacion notificacion){
        
        notificacion.setLstBitacora(null);
        
        Retorno_MsgObj retorno = (Retorno_MsgObj) this.actualizar(notificacion);
        
        return retorno;
    }
    
    public Retorno_MsgObj ValidarBitacora(NotificacionBitacora bitacora){
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Ok", TipoMensaje.MENSAJE));
        
        if(bitacora == null)
        {
            retorno.setMensaje(new Mensajes("No se recibio bitacora", TipoMensaje.ERROR));
            
        }
        else{
            if(bitacora.getNotificacion() == null)
            {
                retorno.setMensaje(new Mensajes("No se recibio notificacion", TipoMensaje.ERROR));
            }
        }
        
        return retorno;
    }
}
