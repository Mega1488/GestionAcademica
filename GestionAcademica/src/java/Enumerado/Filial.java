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
public enum Filial {
    COLONIA(1), ROSARIO(2);
    
    Filial(){
        
    }
    
    private int vFilial;

    Filial(int pFil) {
        this.vFilial = pFil;
    }

    public int getFilial() {
        return vFilial;
    }
    
}
