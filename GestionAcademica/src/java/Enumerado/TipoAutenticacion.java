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
public enum TipoAutenticacion {
    NORMAL(1), OTRA(2);
    
    TipoAutenticacion(){
        
    }
    
    private int valor;

    TipoAutenticacion(int pValor) {
        this.valor = pValor;
    }

    public int getValor() {
        return valor;
    }
    
}
