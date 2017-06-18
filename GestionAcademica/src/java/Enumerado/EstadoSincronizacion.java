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
public enum EstadoSincronizacion {
    CORRECTO(1), CON_ERRORES(2);
    
    EstadoSincronizacion(){
        
    }
    
    private int valor;

    EstadoSincronizacion(int pValor) {
        this.valor = pValor;
    }

    public int getValor() {
        return valor;
    }
    
}
