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
public enum EstadoSolicitud {
    SIN_TOMAR(1), TOMADA(2), FINALIZADA(3);
    
    EstadoSolicitud(){
        
    }
    
    private int vEstSol;

    EstadoSolicitud(int pEstSol) {
        this.vEstSol = pEstSol;
    }

    public int getEstadoSolicitud() {
        return vEstSol;
    }
    
}
