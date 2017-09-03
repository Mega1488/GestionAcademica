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
public enum Extensiones {
    XLS("xls"), 
    XLSX("xlsx"),
    DOC("doc"),
    DOCX("docx");
    
    Extensiones(){
        
    }
    
    private String valor;

    Extensiones(String  pValor) {
        this.valor = pValor;
    }

    public String getValor() {
        return valor;
    }
    
    
    public static Extensiones fromCode(String pCod) {
        for (Extensiones objeto  : Extensiones.values()){
            if (objeto.getValor().equals(pCod)){
                return objeto;
            }
        }
        throw new UnsupportedOperationException(
                "El extension " + pCod + " is not supported!");
    }
    
}
