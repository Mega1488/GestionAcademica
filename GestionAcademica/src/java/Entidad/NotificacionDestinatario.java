/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
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
@Table(name = "NOTIFICACION_DESTINATARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NotificacionDestinatario.findAll",       query = "SELECT t FROM NotificacionDestinatario t")
})

public class NotificacionDestinatario implements Serializable {

    private static final long serialVersionUID = 1L;
   
    //-ATRIBUTOS
    @EmbeddedId
    private final NotificacionDestinatarioPK notDestPK;
    
    @ManyToOne(targetEntity = Persona.class, optional=true)
    @JoinColumn(name="NotPerCod", referencedColumnName="PerCod")
    private Persona persona;
    
    @Column(name = "NotEmail", length = 100)
    private String NotEmail;
    
    
    //-CONSTRUCTOR
    public NotificacionDestinatario() {
        this.notDestPK = new NotificacionDestinatarioPK();
    }
    
    
    //-GETTERS Y SETTERS

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public String getNotEmail() {
        return NotEmail;
    }

    public void setNotEmail(String NotEmail) {
        this.NotEmail = NotEmail;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (notDestPK != null ? notDestPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotificacionDestinatario)) {
            return false;
        }
        NotificacionDestinatario other = (NotificacionDestinatario) object;
        if ((this.notDestPK == null && other.notDestPK != null) || (this.notDestPK != null && !this.notDestPK.equals(other.notDestPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.NotificacionDestinatario[ id=" + notDestPK + " ]";
    }
    
    
    
    @Embeddable
    public static class NotificacionDestinatarioPK implements Serializable {
       @ManyToOne(targetEntity = Notificacion.class, optional=false)
       @JoinColumn(name="NotCod", referencedColumnName="NotCod")
       private Notificacion notificacion;

       private Integer NotDstCod;

       public NotificacionDestinatarioPK() {
       }

       public Notificacion getNotificacion() {
           return notificacion;
       }

       public void setNotificacion(Notificacion notificacion) {
           this.notificacion = notificacion;
       }

       public Integer getNotDstCod() {
           return NotDstCod;
       }

       public void setNotDstCod(Integer NotDstCod) {
           this.NotDstCod = NotDstCod;
       }

       @Override
       public String toString() {
           return "NotificacionDestinatarioPK{" + "notificacion=" + notificacion + ", NotDstCod=" + NotDstCod + '}';
       }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 71 * hash + Objects.hashCode(this.notificacion);
            hash = 71 * hash + Objects.hashCode(this.NotDstCod);
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
            final NotificacionDestinatarioPK other = (NotificacionDestinatarioPK) obj;
            if (!Objects.equals(this.notificacion, other.notificacion)) {
                return false;
            }
            if (!Objects.equals(this.NotDstCod, other.NotDstCod)) {
                return false;
            }
            return true;
        }

       

    }

}

