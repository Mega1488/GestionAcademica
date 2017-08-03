/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import Enumerado.NotificacionEstado;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author alvar
 */
@Entity
@Table(name = "NOTIFICACION_BITACORA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NotificacionBitacora.findAll",       query = "SELECT t FROM NotificacionBitacora t")
})

public class NotificacionBitacora implements Serializable {

    private static final long serialVersionUID = 1L;
   
    //-ATRIBUTOS
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native" )
    @Column(name = "NotBitCod", nullable = false)
    private Long NotBitCod;

    @OneToOne(targetEntity = Notificacion.class)
    @JoinColumn(name="NotCod", referencedColumnName="NotCod")
    private Notificacion notificacion;

    @ManyToOne(targetEntity = Persona.class)
    @JoinColumn(name="NotPerCod", referencedColumnName="PerCod")
    private Persona persona;
    
    @Column(name = "NotBitAsu", length = 100)
    private String NotBitAsu;

    @Column(name = "NotBitCon", length = 4000)
    private String NotBitCon;

    @Column(name = "NotBitDst", length = 500)
    private String NotBitDst;
    
    @Column(name = "NotBitDet", length = 1000)
    private String NotBitDet;
    
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "NotBitEst")
    private NotificacionEstado NotBitEst;
    
    @Column(name = "NotBitFch", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date NotBitFch;
    
    
    //-CONSTRUCTOR
    public NotificacionBitacora() {
    }
    
    
    //-GETTERS Y SETTERS

    public Long getNotBitCod() {
        return NotBitCod;
    }

    public void setNotBitCod(Long NotBitCod) {
        this.NotBitCod = NotBitCod;
    }

    public Notificacion getNotificacion() {
        return notificacion;
    }

    public void setNotificacion(Notificacion notificacion) {
        this.notificacion = notificacion;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public String getNotBitAsu() {
        return NotBitAsu;
    }

    public void setNotBitAsu(String NotBitAsu) {
        this.NotBitAsu = NotBitAsu;
    }

    public String getNotBitCon() {
        return NotBitCon;
    }

    public void setNotBitCon(String NotBitCon) {
        this.NotBitCon = NotBitCon;
    }

    public String getNotBitDst() {
        return NotBitDst;
    }

    public void setNotBitDst(String NotBitDst) {
        this.NotBitDst = NotBitDst;
    }

    public String getNotBitDet() {
        return NotBitDet;
    }

    public void setNotBitDet(String NotBitDet) {
        this.NotBitDet = NotBitDet;
    }

    public NotificacionEstado getNotBitEst() {
        return NotBitEst;
    }

    public void setNotBitEst(NotificacionEstado NotBitEst) {
        this.NotBitEst = NotBitEst;
    }

    public Date getNotBitFch() {
        return NotBitFch;
    }

    public void setNotBitFch(Date NotBitFch) {
        this.NotBitFch = NotBitFch;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.NotBitCod);
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
        final NotificacionBitacora other = (NotificacionBitacora) obj;
        if (!Objects.equals(this.NotBitCod, other.NotBitCod)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "NotificacionBitacora{" + "NotBitCod=" + NotBitCod + ", notificacion=" + notificacion + ", persona=" + persona + ", NotBitAsu=" + NotBitAsu + ", NotBitCon=" + NotBitCon + ", NotBitDst=" + NotBitDst + ", NotBitDet=" + NotBitDet + ", NotBitEst=" + NotBitEst + ", NotBitFch=" + NotBitFch + '}';
    }

   
    

}


 