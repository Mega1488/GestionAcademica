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
public enum ObtenerDestinatario {
    INTERNO("Interno", 0),
    POR_CADA_REGISTRO("Por cada registro", 1), 
    UNICA_VEZ("Única vez", 2);
    
    ObtenerDestinatario(){
        
    }
    
    private int valor;
    private String nombre;

    ObtenerDestinatario(String pNom, int pValor) {
        this.valor = pValor;
        this.nombre = pNom;
    }

    public int getValor() {
        return valor;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public static ObtenerDestinatario fromCode(int pCod) {
        for (ObtenerDestinatario obtDest  : ObtenerDestinatario.values()){
            if (obtDest.getValor() == pCod){
                return obtDest;
            }
        }
        throw new UnsupportedOperationException(
                "El tipo de Obtener Destinatario " + pCod + " is not supported!");
    }
    
}
