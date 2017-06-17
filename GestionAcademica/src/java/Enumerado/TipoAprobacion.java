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

    EXONERABLE_CON_GANANCIA(1), EXONERABLE_SIN_GANANCIA(2), NO_EXONERABLE_CON_GANANCIA(3), NO_EXONERABLE_SIN_GANANCIA(4);
    
    TipoAprobacion(){
        
    }
    
    private int vTipoAprobacion;

    TipoAprobacion(int tpoAp) {
        this.vTipoAprobacion = tpoAp;
    }

    public int getTipoAprobacion() {
        return vTipoAprobacion;
    }
    
}
