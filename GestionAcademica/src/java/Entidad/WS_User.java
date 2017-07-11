/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author alvar
 */
@Entity
@Table(name = "WS_USER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WS_User.findAll",       query = "SELECT t FROM WS_User t")})

public class WS_User implements Serializable {

    private static final long serialVersionUID = 1L;
    
    //-ATRIBUTOS
    
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native" )
    @Column(name = "WsUsrCod", nullable = false)
    private Long WsUsrCod;
        
    @Column(name = "WsUsr", length = 100)
    private String WsUsr;
    
    @Column(name = "WsUsrPsw", length = 500)
    private String WsUsrPsw;
    
    //-CONSTRUCTOR
    public WS_User() {
    }
    
    
    //-GETTERS Y SETTERS

    public Long getWsUsrCod() {
        return WsUsrCod;
    }

    public void setWsUsrCod(Long WsUsrCod) {
        this.WsUsrCod = WsUsrCod;
    }

    public String getWsUsr() {
        return WsUsr;
    }

    public void setWsUsr(String WsUsr) {
        this.WsUsr = WsUsr;
    }

    public String getWsUsrPsw() {
        return WsUsrPsw;
    }

    public void setWsUsrPsw(String WsUsrPsw) {
        this.WsUsrPsw = WsUsrPsw;
    }

    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (WsUsrCod != null ? WsUsrCod.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WS_User)) {
            return false;
        }
        WS_User other = (WS_User) object;
        if ((this.WsUsrCod == null && other.WsUsrCod != null) || (this.WsUsrCod != null && !this.WsUsrCod.equals(other.WsUsrCod))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.WS_User[ id=" + WsUsrCod + " ]";
    }
    
}
