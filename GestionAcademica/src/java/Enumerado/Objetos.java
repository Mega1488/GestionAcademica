/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Enumerado;

import Entidad.TipoEvaluacion;

/**
 *
 * @author Alvaro
 */
public enum Objetos {
    TIPO_EVALUACION(TipoEvaluacion.class.getSimpleName(), "TpoEvlCod", TipoEvaluacion.class.getName());
    
    Objetos(){
        
    }
    
    private String namedQuery;
    private String primaryKey;
    private String className;

    Objetos(String pNombre, String pPrimaryKey, String pClassName) {
        this.namedQuery = pNombre;
        this.primaryKey = pPrimaryKey;
        this.className = pClassName;
    }

    public String getNamedQuery() {
        return namedQuery;
    }
    
    public String getPrimaryKey() {
        return primaryKey;
    }

    public String getClassName() {
        return className;
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
