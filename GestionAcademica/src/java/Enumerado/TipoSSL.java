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
public enum TipoSSL {
    SSL(1), 
    STAR_SSL(2);
    
    TipoSSL(){
        
    }
    
    private int valor;

    TipoSSL(int pValor) {
        this.valor = pValor;
    }

    public int getValor() {
        return valor;
    }
    
}
