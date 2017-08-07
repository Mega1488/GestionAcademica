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
public enum NombreSesiones {
    USUARIO("SGA_USUARIO"), 
    TOKEN("SGA_TOKEN"), 
    URL_SISTEMA("URL_SISTEMA"),
    USUARIO_NOMBRE("USUARIO_NOMBRE"),
    USUARIO_ADM("USUARIO_ADM"),
    USUARIO_PER("USUARIO_PER"),
    USUARIO_DOC("USUARIO_DOC"),
    USUARIO_ALU("USUARIO_ALU");
    
    NombreSesiones(){
        
    }
    
    private String vValor;

    NombreSesiones(String pValor) {
        this.vValor = pValor;
    }

    public String getValor() {
        return vValor;
    }
    
}
