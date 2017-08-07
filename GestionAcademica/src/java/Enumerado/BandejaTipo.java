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
public enum BandejaTipo {
    WEB("Web",1),
    APP("APP",2);
    
    private int vCod;
    private String vNom;

    BandejaTipo(String pNom, int pCod) {
        this.vCod = pCod;
        this.vNom = pNom;
    }

    private BandejaTipo() {
    }
    
    public int getCod() {
        return vCod;
    }
    
    public String getNom() {
        return vNom;
    }
    
    public static BandejaTipo fromCode(int pCod) {
        for (BandejaTipo objeto :BandejaTipo.values()){
            if (objeto.getCod() == pCod){
                return objeto;
            }
        }
        throw new UnsupportedOperationException(
                "El objeto " + pCod + " is not supported!");
    }
    
}
