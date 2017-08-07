/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author alvar
 */
@Entity
@Table(name = "NOTIFICACION_DESTINATARIO",
        uniqueConstraints = {
                                @UniqueConstraint(columnNames = {"NotCod", "NotPerCod"})
                            })
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NotificacionDestinatario.findAll",       query = "SELECT t FROM NotificacionDestinatario t")
})

public class NotificacionDestinatario implements Serializable {

    private static final long serialVersionUID = 1L;
   
    //-ATRIBUTOS
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native" )
    @Column(name = "NotDstCod", nullable = false)
    private Long NotDstCod;
    
    @OneToOne(targetEntity = Notificacion.class)
    @JoinColumn(name="NotCod", referencedColumnName="NotCod")
    private Notificacion notificacion;
    
    @ManyToOne(targetEntity = Persona.class)
    @JoinColumn(name="NotPerCod", referencedColumnName="PerCod")
    private Persona persona;
    
    @Column(name = "NotEmail", length = 100)
    private String NotEmail;
    
    
    //-CONSTRUCTOR
    public NotificacionDestinatario() {
    }
    
    //-GETTERS Y SETTERS

    public Long getNotDstCod() {
        return NotDstCod;
    }

    public void setNotDstCod(Long NotDstCod) {
        this.NotDstCod = NotDstCod;
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

    public String getNotEmail() {
        return NotEmail;
    }

    public void setNotEmail(String NotEmail) {
        this.NotEmail = NotEmail;
    }
    
    public String GetTextoDestinatario(){
        String retorno = "";
        
        if(this.NotEmail != null) retorno = this.NotEmail;
        
        if(this.persona != null) retorno = this.persona.getNombreCompleto();
        
        return retorno;
    }
    
    public String getEmail(){
        if(this.NotEmail != null) return this.NotEmail;
        if(this.persona != null) return this.persona.getPerEml();
        
        return null;
    }
    
    public String getNombre(){
        if(this.NotEmail != null) return this.NotEmail;
        if(this.persona != null) return this.persona.getNombreCompleto();
        
        return null;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + Objects.hashCode(this.NotDstCod);
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
        final NotificacionDestinatario other = (NotificacionDestinatario) obj;
        if (!Objects.equals(this.NotDstCod, other.NotDstCod)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "NotificacionDestinatario{" + "NotDstCod=" + NotDstCod + ", notificacion=" + notificacion + ", persona=" + persona + ", NotEmail=" + NotEmail + '}';
    }

}

