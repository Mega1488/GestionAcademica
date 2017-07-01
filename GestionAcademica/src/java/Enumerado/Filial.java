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
public enum Filial {
    COLONIA("Colonia", 1), ROSARIO("Rosario",2);
    
    Filial(){
        
    }
    
    private String vFilialNom;
    private int vFilialCod;

    Filial(String pFilNom, int pFil) {
        this.vFilialNom = pFilNom;
        this.vFilialCod = pFil;
   }

    public int getFilial() {
        return vFilialCod;
    }
    
    public String getFilialNom()
    {
        return this.vFilialNom;
    }
    
    public static Filial fromCode(int filCod) {
        for (Filial filial :Filial.values()){
            if (filial.getFilial() == filCod){
                return filial;
            }
        }
        throw new UnsupportedOperationException(
                "La filial " + filCod + " is not supported!");
    }
    
}
