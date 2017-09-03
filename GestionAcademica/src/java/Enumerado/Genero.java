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
public enum Genero {
    FEMENINO("Femenino"),
    INDEFINIDO("Indefinido"),
    MASCULINO("Masculino");
    
    Genero(){
        
    }
    
    private String valor;

    Genero(String  pValor) {
        this.valor = pValor;
    }

    public String getValor() {
        return valor;
    }
    
    
    public static Genero fromCode(String pCod) {
        for (Genero objeto  : Genero.values()){
            if (objeto.getValor().equals(pCod)){
                return objeto;
            }
        }
        throw new UnsupportedOperationException(
                "El generos " + pCod + " is not supported!");
    }
    
}
