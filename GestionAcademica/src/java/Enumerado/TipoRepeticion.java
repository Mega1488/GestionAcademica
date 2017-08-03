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
public enum TipoRepeticion {
    SIN_REPETICION("Sin repetición", 0), 
    MINUTOS("Minutos", 1), 
    HORAS("Horas", 2), 
    DIAS("Dias", 3), 
    SEMANAS("Semanas", 4), 
    MESES("Meses", 5), 
    ANIOS("Años", 6);
    
    TipoRepeticion(){
        
    }
    
    private int valor;
    private String nombre;

    TipoRepeticion(String pNombre, int pValor) {
        this.valor  = pValor;
        this.nombre = pNombre;
    }

    public int getValor() {
        return valor;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public static TipoRepeticion fromCode(int pCod) {
        for (TipoRepeticion tpoRep  : TipoRepeticion.values()){
            if (tpoRep.getValor() == pCod){
                return tpoRep;
            }
        }
        throw new UnsupportedOperationException(
                "El tipo de Repeticion " + pCod + " is not supported!");
    }
}
