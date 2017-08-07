/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SDT;

/**
 *
 * @author alvar
 */
public class SDT_NotificacionDato {
    private String message;
    private String titulo;

    public SDT_NotificacionDato() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public SDT_NotificacionDato(String message, String titulo) {
        this.message = message;
        this.titulo = titulo;
    }

    
    
    
    
}
