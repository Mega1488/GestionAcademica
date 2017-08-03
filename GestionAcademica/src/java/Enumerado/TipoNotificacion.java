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
public enum TipoNotificacion {
    AUTOMATICA("Automática", 1), A_DEMANDA("A demanda", 2);
    
    TipoNotificacion(){
        
    }
    
    private int valor;
    private String nombre;

    TipoNotificacion(String pNombre, int pValor) {
        this.valor = pValor;
        this.nombre = pNombre;
    }

    public int getValor() {
        return valor;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public static TipoNotificacion fromCode(int pCod) {
        for (TipoNotificacion tpoNot  : TipoNotificacion.values()){
            if (tpoNot.getValor() == pCod){
                return tpoNot;
            }
        }
        throw new UnsupportedOperationException(
                "El tipo de Notificacion " + pCod + " is not supported!");
    }
}
