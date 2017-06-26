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
public enum Constantes {
    URL_FOLDER_SERVICIO_MDL("/webservice/rest/server.php"), 
    ENCRYPT_VECTOR_INICIO("a#!?d./*@@^^''_a"),
    ENCRYPT_SEMILLA("-KeY!!AD#AM!!KeY"),
    VERSION("0.1.0");

    Constantes(){
        
    }
    
    private String vValor;

    Constantes(String pValor) {
        this.vValor = pValor;
    }

    public String getValor() {
        return vValor;
    }
    
}
