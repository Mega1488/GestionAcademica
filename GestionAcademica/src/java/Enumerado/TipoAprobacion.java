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
public enum TipoAprobacion{
    /*
        exonerable c/ganancia de curso ( >70 exámen > 86 exonera)
        exonerable s/ganancia de curso (<86 exámen >86 exonera)
        no exonerable c/ganancia de curso (Más de 70 a examen sino recursa)
        no exonerable s/ganancia de curso (Examen)
    */

    EXONERABLE_CON_GANANCIA("Exonerable con Ganancia", 1), EXONERABLE_SIN_GANANCIA("Exonerable sin Ganancia", 2), NO_EXONERABLE_CON_GANANCIA("No Exonerable con Ganancia", 3), NO_EXONERABLE_SIN_GANANCIA("No Exonerable sin Ganancia", 4);
    
    TipoAprobacion(){
        
    }
    
    private int vTpoAprCod;
    private String vTpoAprNom;

    TipoAprobacion(String tpoAprNom, int tpoAprCod) {
        this.vTpoAprCod = tpoAprCod;
        this.vTpoAprNom = tpoAprNom;
    }

    public int getTipoAprobacionC()
    {
        return vTpoAprCod;
    }
    
    public String getTipoAprobacionN()
    {
        return vTpoAprNom;
    }
    
    public static TipoAprobacion fromCode(int tpoAprCod)
    {
        for(TipoAprobacion tipoApr :TipoAprobacion.values())
        {
            if(tipoApr.getTipoAprobacionC() == tpoAprCod)
            {
                return tipoApr;
            }
        }
        throw new UnsupportedOperationException(
                "El tipo de Aprobación "+ tpoAprCod + " is not suported!");
    }
}
