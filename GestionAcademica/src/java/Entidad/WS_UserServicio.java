/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import Enumerado.ServicioWeb;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alvar
 */
@Entity
@Table(name = "WS_USERS_SERVICIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WS_UserServicio.findAll",       query = "SELECT t FROM WS_UserServicio t")})

public class WS_UserServicio implements Serializable {

    private static final long serialVersionUID = 1L;
    
    //-ATRIBUTOS 
    @EmbeddedId
    private final UserServicioPK usrServPK;

    //-CONSTRUCTOR
    public WS_UserServicio() {
        this.usrServPK = new UserServicioPK();
    }
    
    
    //-GETTERS Y SETTERS

    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usrServPK != null ? usrServPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WS_UserServicio)) {
            return false;
        }
        WS_UserServicio other = (WS_UserServicio) object;
        if ((this.usrServPK == null && other.usrServPK != null) || (this.usrServPK != null && !this.usrServPK.equals(other.usrServPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.WS_UserServicio[ id=" + usrServPK + " ]";
    }
    
    
    @Embeddable
    public static class UserServicioPK implements Serializable {
       @ManyToOne(targetEntity = WS_User.class, optional=false)
       @JoinColumn(name="WsUsrCod", referencedColumnName="WsUsrCod")
       private WS_User usuario;

       private ServicioWeb WsSrv;

       public UserServicioPK() {
       }

       public WS_User getUsuario() {
           return usuario;
       }

       public void setUsuario(WS_User usuario) {
           this.usuario = usuario;
       }

       public ServicioWeb getWsSrv() {
           return WsSrv;
       }

       public void setWsSrv(ServicioWeb WsSrv) {
           this.WsSrv = WsSrv;
       }

       @Override
       public String toString() {
           return "UserServicioPK{" + "usuario=" + usuario + ", WsSrv=" + WsSrv + '}';
       }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 53 * hash + Objects.hashCode(this.usuario);
            hash = 53 * hash + Objects.hashCode(this.WsSrv);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final UserServicioPK other = (UserServicioPK) obj;
            if (!Objects.equals(this.usuario, other.usuario)) {
                return false;
            }
            if (this.WsSrv != other.WsSrv) {
                return false;
            }
            return true;
        }

       


   }
}



