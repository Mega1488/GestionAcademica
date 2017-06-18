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
public enum TipoNotificacion {
    AUTOMATICA(1), A_DEMANDA(2);
    
    TipoNotificacion(){
        
    }
    
    private int valor;

    TipoNotificacion(int pValor) {
        this.valor = pValor;
    }

    public int getValor() {
        return valor;
    }
    
}
