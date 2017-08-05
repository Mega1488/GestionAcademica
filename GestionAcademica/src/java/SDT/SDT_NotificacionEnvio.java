/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SDT;

import Entidad.NotificacionDestinatario;

/**
 *
 * @author alvar
 */
public class SDT_NotificacionEnvio {
    private Boolean NotApp;
    private Boolean NotWeb;
    private Boolean NotEml;
    private NotificacionDestinatario destinatario;
    private String contenido;
    private String asunto;

    public SDT_NotificacionEnvio() {
    }

    public SDT_NotificacionEnvio(Boolean NotApp, Boolean NotWeb, Boolean NotEml, NotificacionDestinatario destinatario, String contenido, String asunto) {
        this.NotApp = NotApp;
        this.NotWeb = NotWeb;
        this.NotEml = NotEml;
        this.destinatario = destinatario;
        this.contenido = contenido;
        this.asunto = asunto;
    }

    public Boolean getNotApp() {
        return NotApp;
    }

    public void setNotApp(Boolean NotApp) {
        this.NotApp = NotApp;
    }

    public Boolean getNotWeb() {
        return NotWeb;
    }

    public void setNotWeb(Boolean NotWeb) {
        this.NotWeb = NotWeb;
    }

    public Boolean getNotEml() {
        return NotEml;
    }

    public void setNotEml(Boolean NotEml) {
        this.NotEml = NotEml;
    }

    public NotificacionDestinatario getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(NotificacionDestinatario destinatario) {
        this.destinatario = destinatario;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }
    
    
    
}
