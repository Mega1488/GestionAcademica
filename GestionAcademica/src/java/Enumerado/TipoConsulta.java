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
public enum TipoConsulta {
    CONSULTA(1), DESTINATARIO(2);
    
    TipoConsulta(){
        
    }
    
    private int valor;

    TipoConsulta(int pValor) {
        this.valor = pValor;
    }

    public int getValor() {
        return valor;
    }
    
}
