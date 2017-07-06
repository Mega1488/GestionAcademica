/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import Enumerado.TipoConsulta;
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
@Table(name = "NOTIFICACION_CONSULTA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NotificacionConsulta.findAll",       query = "SELECT t FROM NotificacionConsulta t")
})

public class NotificacionConsulta implements Serializable {

    private static final long serialVersionUID = 1L;
    
    //-ATRIBUTOS
    @EmbeddedId
    private final NotificacionConsultaPK notConsPK;

    @Column(name = "NotCnsTpo")
    private TipoConsulta NotCnsTpo;    
    @Column(name = "NotCnsSQL", length = 1000)
    private String NotCnsSQL;
    
    
    //-CONSTRUCTOR
    public NotificacionConsulta() {
        this.notConsPK = new NotificacionConsultaPK();
    }
    
    
    //-GETTERS Y SETTERS

    public TipoConsulta getNotCnsTpo() {
        return NotCnsTpo;
    }

    public void setNotCnsTpo(TipoConsulta NotCnsTpo) {
        this.NotCnsTpo = NotCnsTpo;
    }

    public String getNotCnsSQL() {
        return NotCnsSQL;
    }

    public void setNotCnsSQL(String NotCnsSQL) {
        this.NotCnsSQL = NotCnsSQL;
    }

    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (notConsPK != null ? notConsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotificacionConsulta)) {
            return false;
        }
        NotificacionConsulta other = (NotificacionConsulta) object;
        if ((this.notConsPK == null && other.notConsPK != null) || (this.notConsPK != null && !this.notConsPK.equals(other.notConsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.NotificacionConsulta[ id=" + notConsPK + " ]";
    }
    
    @Embeddable
    public static class NotificacionConsultaPK implements Serializable {
       @ManyToOne(targetEntity = Notificacion.class, optional=false)
       @JoinColumn(name="NotCod", referencedColumnName="NotCod")
       private Notificacion notificacion;

       private Integer NotCnsCod;

       public NotificacionConsultaPK() {
       }

       public Notificacion getNotificacion() {
           return notificacion;
       }

       public void setNotificacion(Notificacion notificacion) {
           this.notificacion = notificacion;
       }

       public Integer getNotCnsCod() {
           return NotCnsCod;
       }

       public void setNotDstCod(Integer NotDstCod) {
           this.NotCnsCod = NotDstCod;
       }

       @Override
       public String toString() {
           return "NotificacionDestinatarioPK{" + "notificacion=" + notificacion + ", NotDstCod=" + NotCnsCod + '}';
       }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 89 * hash + Objects.hashCode(this.notificacion);
            hash = 89 * hash + Objects.hashCode(this.NotCnsCod);
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
            final NotificacionConsultaPK other = (NotificacionConsultaPK) obj;
            if (!Objects.equals(this.notificacion, other.notificacion)) {
                return false;
            }
            if (!Objects.equals(this.NotCnsCod, other.NotCnsCod)) {
                return false;
            }
            return true;
        }
       
       
   }

}

