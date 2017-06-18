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
public enum TipoRepeticion {
    MINUTOS(1), HORAS(2), DIAS(3), SEMANAS(4), MESES(5), ANIOS(6);
    
    TipoRepeticion(){
        
    }
    
    private int valor;

    TipoRepeticion(int pValor) {
        this.valor = pValor;
    }

    public int getValor() {
        return valor;
    }
    
}
