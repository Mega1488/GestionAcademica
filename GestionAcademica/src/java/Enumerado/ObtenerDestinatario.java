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
public enum ObtenerDestinatario {
    POR_CADA_REGISTRO(1), UNICA_VEZ(2);
    
    ObtenerDestinatario(){
        
    }
    
    private int valor;

    ObtenerDestinatario(int pValor) {
        this.valor = pValor;
    }

    public int getValor() {
        return valor;
    }
    
}
