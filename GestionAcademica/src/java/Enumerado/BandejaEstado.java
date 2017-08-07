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
public enum BandejaEstado {
    SIN_LEER("Sin leer",1),
    LEIDA("Leida",2),
    ERROR("Error",3);
    
    private int vCod;
    private String vNom;

    BandejaEstado(String pNom, int pCod) {
        this.vCod = pCod;
        this.vNom = pNom;
    }

    private BandejaEstado() {
    }
    
    public int getCod() {
        return vCod;
    }
    
    public String getNom() {
        return vNom;
    }
    
    public static BandejaEstado fromCode(int pCod) {
        for (BandejaEstado objeto :BandejaEstado.values()){
            if (objeto.getCod() == pCod){
                return objeto;
            }
        }
        throw new UnsupportedOperationException(
                "El objeto " + pCod + " is not supported!");
    }
    
}
