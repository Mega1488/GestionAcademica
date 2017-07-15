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
    SIN_CALIFICAR("Sin calificar", 1), CALIFICADO("Calificado", 2), VALIDADO("Validado", 3);
    
    EstadoCalendarioEvaluacion(){
        
    }
    
    private String vEstadoNom;
    private int vEstado;

    EstadoCalendarioEvaluacion(String estadoNom, int pEstado) {
        this.vEstado = pEstado;
        this.vEstadoNom = estadoNom;
    }

    public int getEstado() {
        return vEstado;
    }
    
    public String getEstadoNombre() {
        return vEstadoNom;
    }
    
     public static EstadoCalendarioEvaluacion fromCode(int cod) {
        for (EstadoCalendarioEvaluacion estado :EstadoCalendarioEvaluacion.values()){
            if (estado.getEstado() == cod){
                return estado;
            }
        }
        throw new UnsupportedOperationException(
                "El estado " + cod + " is not supported!");
    }
}
