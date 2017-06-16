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
public enum TipoPeriodo {
    MENSUAL(1), SEMESTRAL(2), ANUAL(3), MODULAR(4);
    
    TipoPeriodo(){
        
    }
    
    private int vTipoPeriodo;

    TipoPeriodo(int tpoPer) {
        this.vTipoPeriodo = tpoPer;
    }

    public int getTipoPeriodo() {
        return vTipoPeriodo;
    }
    
}
