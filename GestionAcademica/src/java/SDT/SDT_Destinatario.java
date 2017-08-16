/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SDT;

import Enumerado.TipoDestinatario;

/**
 *
 * @author alvar
 */
public class SDT_Destinatario {
    
    private Long PerCod;
    private String Email;
    private TipoDestinatario tipoDestinatario;

    public SDT_Destinatario() {
    }

    public Long getPerCod() {
        return PerCod;
    }

    public void setPerCod(Long PerCod) {
        this.PerCod = PerCod;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public TipoDestinatario getTipoDestinatario() {
        return tipoDestinatario;
    }

    public void setTipoDestinatario(TipoDestinatario tipoDestinatario) {
        this.tipoDestinatario = tipoDestinatario;
    }

    
}
