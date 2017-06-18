/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Enumerado;

/**
 *
 * @author Alvaro
 */
public enum NotificacionEstado {
    ENVIO_CORRECTO(1), ENVIO_CON_ERRORES(2);
    
    NotificacionEstado(){
        
    }
    
    private int valor;

    NotificacionEstado(int pValor) {
        this.valor = pValor;
    }

    public int getValor() {
        return valor;
    }
    
}
