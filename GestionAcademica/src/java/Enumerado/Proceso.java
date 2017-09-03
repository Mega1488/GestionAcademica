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
public enum Proceso {
    IMPORTACION("Importacion"),
    SISTEMA("Sistema");
    
    Proceso(){
        
    }
    
    private String valor;

    Proceso(String  pValor) {
        this.valor = pValor;
    }

    public String getValor() {
        return valor;
    }
    
    
    public static Proceso fromCode(String pCod) {
        for (Proceso objeto  : Proceso.values()){
            if (objeto.getValor().equals(pCod)){
                return objeto;
            }
        }
        throw new UnsupportedOperationException(
                "El proceso " + pCod + " is not supported!");
    }
    
}
