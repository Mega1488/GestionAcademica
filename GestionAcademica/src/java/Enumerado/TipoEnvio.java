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
public enum TipoEnvio {
    COMUN("Común", 1), 
    AGRUPADO("Agrupado", 2);
    
    TipoEnvio(){
        
    }
    
    private int valor;
    private String nombre;

    TipoEnvio(String pNom, int pValor) {
        this.valor = pValor;
        this.nombre = pNom;
    }

    public int getValor() {
        return valor;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public static TipoEnvio fromCode(int pCod) {
        for (TipoEnvio tpoEnv  : TipoEnvio.values()){
            if (tpoEnv.getValor() == pCod){
                return tpoEnv;
            }
        }
        throw new UnsupportedOperationException(
                "El tipo de envio " + pCod + " is not supported!");
    }
    
}
