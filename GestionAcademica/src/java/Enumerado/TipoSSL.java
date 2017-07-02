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
public enum TipoSSL {
    SSL("SSL",1), 
    STAR_SSL("STAR SSL",2);
    
    private int vCod;
    private String vNom;

    TipoSSL(String pNom, int pCod) {
        this.vCod = pCod;
        this.vNom = pNom;
    }

    public int getCod() {
        return vCod;
    }
    
    public String getNom() {
        return vNom;
    }
    
    public static TipoSSL fromCode(int pCod) {
        for (TipoSSL tpoSSL :TipoSSL.values()){
            if (tpoSSL.getCod() == pCod){
                return tpoSSL;
            }
        }
        throw new UnsupportedOperationException(
                "El tipo de ssl " + pCod + " is not supported!");
    }
    
}
