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
public enum TipoDestinatario {
    PERSONA("Persona", 1), 
    EMAIL("Email", 2);
    
    TipoDestinatario(){
        
    }
    
    private int valor;
    private String nombre;

    TipoDestinatario(String pNombre, int pValor) {
        this.valor = pValor;
        this.nombre = pNombre;
    }

    public int getValor() {
        return valor;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public static TipoDestinatario fromCode(int pCod) {
        for (TipoDestinatario objeto  : TipoDestinatario.values()){
            if (objeto.getValor() == pCod){
                return objeto;
            }
        }
        throw new UnsupportedOperationException(
                "El tipo de destinatario " + pCod + " is not supported!");
    }
    
}
