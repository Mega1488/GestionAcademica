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
public enum ServicioWeb {
    LOGIN(1), 
    EVALUACION_ALUMNO(2), 
    ESTUDIOS(3),
    PERSONA(4),
    SOLICITUDES(5);
    
    ServicioWeb(){
        
    }
    
    private int valor;

    ServicioWeb(int pValor) {
        this.valor = pValor;
    }

    public int getValor() {
        return valor;
    }
    
}
