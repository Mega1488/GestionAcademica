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
public enum TipoSolicitud {
    ESCOLARIDAD(1), CONSTANCIA_ESTUDIO(2), DUPLICADO_FACTURA(3);
    
    TipoSolicitud(){
        
    }
    
    private int vTpoSol;

    TipoSolicitud(int pTpoSol) {
        this.vTpoSol = pTpoSol;
    }

    public int getTipoSolicitud() {
        return vTpoSol;
    }
    
}
