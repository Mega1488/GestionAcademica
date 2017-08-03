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
public enum TipoConsulta {
    CONSULTA("Consulta", 1), 
    INC_DESTINATARIO("Destinatarios a incluir", 2),
    EXC_DESTINATARIO("Destinatarios a excluir", 3);
    
    TipoConsulta(){
        
    }
    
    private int valor;
    private String nombre;

    TipoConsulta(String pNombre, int pValor) {
        this.valor = pValor;
        this.nombre = pNombre;
    }

    public int getValor() {
        return valor;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public static TipoConsulta fromCode(int pCod) {
        for (TipoConsulta tpoCns  : TipoConsulta.values()){
            if (tpoCns.getValor() == pCod){
                return tpoCns;
            }
        }
        throw new UnsupportedOperationException(
                "El tipo de consulta " + pCod + " is not supported!");
    }
    
}
