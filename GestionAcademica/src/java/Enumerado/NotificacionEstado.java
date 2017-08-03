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
public enum NotificacionEstado {
    ENVIO_CORRECTO("Envío correcto", 1), 
    ENVIO_CON_ERRORES("Envío con errores", 2), 
    ENVIO_EN_PROGRESO("Envío en progreso", 3);
    
    NotificacionEstado(){
        
    }
    
    private int valor;
    private String nombre;

    NotificacionEstado(String pNombre, int pValor) {
        this.valor = pValor;
        this.nombre = pNombre;
    }

    public int getValor() {
        return valor;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public static NotificacionEstado fromCode(int pCod) {
        for (NotificacionEstado objeto  : NotificacionEstado.values()){
            if (objeto.getValor() == pCod){
                return objeto;
            }
        }
        throw new UnsupportedOperationException(
                "El notificacion estado " + pCod + " is not supported!");
    }
    
}
