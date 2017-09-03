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
public enum TipoArchivo {
    PERIODO_DOCUMENTO("Periodo estudio - Documento"),
    FOTO_PERFIL("Foto de perfil");
    
    TipoArchivo(){
        
    }
    
    private String valor;

    TipoArchivo(String  pValor) {
        this.valor = pValor;
    }

    public String getValor() {
        return valor;
    }
    
    
    public static TipoArchivo fromCode(String pCod) {
        for (TipoArchivo objeto  : TipoArchivo.values()){
            if (objeto.getValor().equals(pCod)){
                return objeto;
            }
        }
        throw new UnsupportedOperationException(
                "El tipo archivo " + pCod + " is not supported!");
    }
    
}
