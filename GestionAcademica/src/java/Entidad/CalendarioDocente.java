/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "CALENDARIO_DOCENTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CalendarioDocente.findAll",       query = "SELECT t FROM CalendarioDocente t")})

public class CalendarioDocente implements Serializable {

    private static final long serialVersionUID = 1L;
    
    //-ATRIBUTOS
    @EmbeddedId
    private final CalendarioDocentePK calDocPK;
    
    @Column(name = "ObjFchMod", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ObjFchMod;
    
    //-CONSTRUCTOR

    public CalendarioDocente() {
        this.calDocPK = new CalendarioDocentePK();
    }

    //-GETTERS Y SETTERS

    public Date getObjFchMod() {
        return ObjFchMod;
    }

    public void setObjFchMod(Date ObjFchMod) {
        this.ObjFchMod = ObjFchMod;
    }
    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (calDocPK != null ? calDocPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CalendarioDocente)) {
            return false;
        }
        CalendarioDocente other = (CalendarioDocente) object;
        if ((this.calDocPK == null && other.calDocPK != null) || (this.calDocPK != null && !this.calDocPK.equals(other.calDocPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.CalendarioDocente[ id=" + calDocPK.toString() + " ]";
    }
    
    
    
    @Embeddable
    public static class CalendarioDocentePK implements Serializable {
        @ManyToOne(targetEntity = Calendario.class, optional=false)
        @JoinColumns({
            @JoinColumn(name="CalCod", referencedColumnName = "CalCod"),
            @JoinColumn(name="EvlCod", referencedColumnName = "EvlCod")
        })
        private Calendario Cal;

        @ManyToOne(targetEntity = Persona.class, optional=false)
        @JoinColumn(name="DocPerCod", referencedColumnName = "PerCod")
        private Persona Docente;

        public Calendario getCal() {
            return Cal;
        }

        public void setCal(Calendario Cal) {
            this.Cal = Cal;
        }

        public Persona getDocente() {
            return Docente;
        }

        public void setDocente(Persona Doc) {
            this.Docente = Doc;
        }

        public CalendarioDocentePK() {
        }

        @Override
        public String toString() {
            return "CalendarioDocentePK{" + "Cal=" + Cal + ", Doc=" + Docente + '}';
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 17 * hash + Objects.hashCode(this.Cal);
            hash = 17 * hash + Objects.hashCode(this.Docente);
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
            final CalendarioDocentePK other = (CalendarioDocentePK) obj;
            if (!Objects.equals(this.Cal, other.Cal)) {
                return false;
            }
            if (!Objects.equals(this.Docente, other.Docente)) {
                return false;
            }
            return true;
        }

        

    }
}



