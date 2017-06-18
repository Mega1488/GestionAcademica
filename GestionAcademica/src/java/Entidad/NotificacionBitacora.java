/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import Enumerado.NotificacionEstado;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

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
    @EmbeddedId
    private final NotificacionBitacoraPK notBitPK;
    
    @ManyToOne(targetEntity = Persona.class, optional=true)
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
    
    @Column(name = "NotBitEst")
    private NotificacionEstado NotBitEst;
    
    @Column(name = "NotBitFch", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date NotBitFch;
    
    
    //-CONSTRUCTOR
    public NotificacionBitacora() {
        this.notBitPK = new NotificacionBitacoraPK();
    }
    
    
    //-GETTERS Y SETTERS

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
        int hash = 0;
        hash += (notBitPK != null ? notBitPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotificacionBitacora)) {
            return false;
        }
        NotificacionBitacora other = (NotificacionBitacora) object;
        if ((this.notBitPK == null && other.notBitPK != null) || (this.notBitPK != null && !this.notBitPK.equals(other.notBitPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.NotificacionDestinatario[ id=" + notBitPK + " ]";
    }
    
}


 @Embeddable
 class NotificacionBitacoraPK implements Serializable {
    @ManyToOne(targetEntity = Notificacion.class, optional=false)
    @JoinColumn(name="NotCod", referencedColumnName="NotCod")
    private Notificacion notificacion;
     
    private Integer NotBitCod;

    public NotificacionBitacoraPK() {
    }

    public Notificacion getNotificacion() {
        return notificacion;
    }

    public void setNotificacion(Notificacion notificacion) {
        this.notificacion = notificacion;
    }

    public Integer getNotBitCod() {
        return NotBitCod;
    }

    public void setNotBitCod(Integer NotBitCod) {
        this.NotBitCod = NotBitCod;
    }

    @Override
    public String toString() {
        return "NotificacionDestinatarioPK{" + "notificacion=" + notificacion + ", NotBitCod=" + NotBitCod + '}';
    }
    
    
 }
