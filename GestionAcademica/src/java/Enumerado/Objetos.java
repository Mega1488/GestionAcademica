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
public enum Objetos {
    TIPO_EVALUACION("TipoEvaluacion", "TpoEvlCod");
    
    Objetos(){
        
    }
    
    private String namedQuery;
    private String primaryKey;

    Objetos(String pNombre, String pPrimaryKey) {
        this.namedQuery = pNombre;
        this.primaryKey = pPrimaryKey;
    }

    public String getNamedQuery() {
        return namedQuery;
    }
    
    public String getPrimaryKey() {
        return primaryKey;
    }
    
   public static Objetos fromQueryName(String upperQueryName) {
        for (Objetos objeto  : Objetos.values()){
            if (objeto.getNamedQuery().toUpperCase().equals(upperQueryName)){
                return objeto;
            }
        }
        throw new UnsupportedOperationException(
                "El objeto " + upperQueryName + " is not supported!");
    }
 
}
