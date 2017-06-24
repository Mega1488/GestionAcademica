/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utiles;

import Enumerado.TipoMensaje;

/**
 *
 * @author alvar
 */
public class Mensajes {
    private String mensaje;
    private TipoMensaje tipoMensaje;

    public Mensajes(String mensaje, TipoMensaje tipoMensaje) {
        this.mensaje = mensaje;
        this.tipoMensaje = tipoMensaje;
    }

    public Mensajes() {
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public TipoMensaje getTipoMensaje() {
        return tipoMensaje;
    }

    public void setTipoMensaje(TipoMensaje tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }
    
    
}
