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
public enum ProtocoloEmail {
    SMTP("SMTP", 1), IMAP("IMAP",2);
    
    ProtocoloEmail(){
        
    }
    
    private String vProtocoloNom;
    private int vProtocoloCod;

    ProtocoloEmail(String pProtocoloNom, int pProtocoloCod) {
        this.vProtocoloNom = pProtocoloNom;
        this.vProtocoloCod = pProtocoloCod;
   }

    public int getCod() {
        return vProtocoloCod;
    }
    
    public String getNom()
    {
        return this.vProtocoloNom;
    }
    
    public static ProtocoloEmail fromCode(int pCod) {
        for (ProtocoloEmail protocoloEmail :ProtocoloEmail.values()){
            if (protocoloEmail.getCod() == pCod){
                return protocoloEmail;
            }
        }
        throw new UnsupportedOperationException(
                "El protocolo de email " + pCod + " is not supported!");
    }
    
    public static boolean ValidarProtocoloEmail(int pCod)
    {
        for (ProtocoloEmail protocoloEmail :ProtocoloEmail.values()){
            if (protocoloEmail.getCod() == pCod){
                return true;
            }
        }
        throw new UnsupportedOperationException(
                "El protocolo de email " + pCod + " is not supported!");
        
    }
}
