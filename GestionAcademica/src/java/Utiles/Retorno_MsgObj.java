/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utiles;

import java.util.ArrayList;

/**
 *
 * @author alvar
 */
public class Retorno_MsgObj {

    private Mensajes mensaje;
    private ArrayList<Mensajes> lstMensajes;
    private Object   objeto;
    
    public Retorno_MsgObj() {
    }

    public Retorno_MsgObj(Mensajes mensaje, Object objeto) {
        this.mensaje = mensaje;
        this.objeto = objeto;
    }

    public Retorno_MsgObj(ArrayList<Mensajes> lstMensajes, Object objeto) {
        this.lstMensajes = lstMensajes;
        this.objeto = objeto;
    }

    public ArrayList<Mensajes> getLstMensajes() {
        return lstMensajes;
    }

    public void setLstMensajes(ArrayList<Mensajes> lstMensajes) {
        this.lstMensajes = lstMensajes;
    }
    
    public void addMensaje(Mensajes mensaje)
    {
        this.lstMensajes.add(mensaje);
    }
    

    public Mensajes getMensaje() {
        return mensaje;
    }

    public void setMensaje(Mensajes mensaje) {
        this.mensaje = mensaje;
    }

    public Object getObjeto() {
        return objeto;
    }

    public void setObjeto(Object objeto) {
        this.objeto = objeto;
    }
    
    
    
    
    
}
