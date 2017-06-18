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
public enum TipoEnvio {
    COMUN(1), AGRUPADO(2);
    
    TipoEnvio(){
        
    }
    
    private int valor;

    TipoEnvio(int pValor) {
        this.valor = pValor;
    }

    public int getValor() {
        return valor;
    }
    
}
