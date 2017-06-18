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
public enum EstadoCalendarioEvaluacion {
    SIN_CALIFICAR(1), CALIFICADO(2), VALIDADO(3);
    
    EstadoCalendarioEvaluacion(){
        
    }
    
    private int vEstado;

    EstadoCalendarioEvaluacion(int pEstado) {
        this.vEstado = pEstado;
    }

    public int getEstado() {
        return vEstado;
    }
    
}
