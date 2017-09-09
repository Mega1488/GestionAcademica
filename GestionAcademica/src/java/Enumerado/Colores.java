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
public enum Colores {
    PRIMERO("#1abc9c"),
    SEGUNDO("#16a085"),
    TERCERO("#f1c40f"),
    CUARTO("#f39c12"),
    QUINTO("#e74c3c"),
    SEXTO("#c0392b"),
    SEPTIMO("#3498db"),
    OCTAVO("#2980b9"),
    NOVENO("#9b59b6"),
    DECIMO("#8e44ad");
    
    Colores(){
        
    }
    
    private String valor;

    Colores(String  pValor) {
        this.valor = pValor;
    }

    public String getValor() {
        return valor;
    }
    
    
    public static Colores fromCode(String pCod) {
        for (Colores objeto  : Colores.values()){
            if (objeto.getValor().equals(pCod)){
                return objeto;
            }
        }
        throw new UnsupportedOperationException(
                "El color " + pCod + " is not supported!");
    }
    
}