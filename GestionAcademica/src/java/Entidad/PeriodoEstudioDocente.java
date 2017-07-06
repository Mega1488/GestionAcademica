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
@Table(name = "PERIODO_ESTUDIO_DOCENTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PeriodoEstudioDocente.findAll",       query = "SELECT t FROM PeriodoEstudioDocente t"),
})
public class PeriodoEstudioDocente implements Serializable {

    private static final long serialVersionUID = 1L;
   
    //-ATRIBUTOS
    @EmbeddedId
    private final PeriodoEstudioDocentePK periodoEstudioDocentePK;
    @ManyToOne(targetEntity = Persona.class, optional=false)
    @JoinColumn(name="PerInsPerCod", referencedColumnName="PerCod")
    private Persona InscritoPor;
    @Column(name = "DocFchInsc", columnDefinition="DATE")
    @Temporal(TemporalType.DATE)
    private Date DocFchInsc;
    @Column(name = "ObjFchMod", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ObjFchMod;
    
    //-CONSTRUCTOR
    public PeriodoEstudioDocente() {
        this.periodoEstudioDocentePK = new PeriodoEstudioDocentePK();
    }
    
    //-GETTERS Y SETTERS

    public Persona getInscritoPor() {
        return InscritoPor;
    }

    public void setInscritoPor(Persona InsPor) {
        this.InscritoPor = InsPor;
    }

    public Date getDocFchInsc() {
        return DocFchInsc;
    }

    public void setDocFchInsc(Date DocFchInsc) {
        this.DocFchInsc = DocFchInsc;
    }

    public Date getObjFchMod() {
        return ObjFchMod;
    }

    public void setObjFchMod(Date ObjFchMod) {
        this.ObjFchMod = ObjFchMod;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (periodoEstudioDocentePK != null ? periodoEstudioDocentePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PeriodoEstudioDocente)) {
            return false;
        }
        PeriodoEstudioDocente other = (PeriodoEstudioDocente) object;
        if ((this.periodoEstudioDocentePK == null && other.periodoEstudioDocentePK != null) || (this.periodoEstudioDocentePK != null && !this.periodoEstudioDocentePK.equals(other.periodoEstudioDocentePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.PeriodoEstudioDocente[ id=" + periodoEstudioDocentePK.toString() + " ]";
    }
    
    
    @Embeddable
    public static class PeriodoEstudioDocentePK implements Serializable {
        @ManyToOne(targetEntity = PeriodoEstudio.class, optional=false)
        @JoinColumns({
                @JoinColumn(name="PeriCod", referencedColumnName="PeriCod"),
                @JoinColumn(name="PeriEstCod", referencedColumnName="PeriEstCod")
            })
        private PeriodoEstudio periodoEstudio;

        @ManyToOne(targetEntity = Persona.class, optional=false)
        @JoinColumn(name="DocPerCod", referencedColumnName="PerCod")
        private Persona Docente;

        public PeriodoEstudioDocentePK() {
        }

        public PeriodoEstudio getPeriodoEstudio() {
            return periodoEstudio;
        }

        public void setPeriodoEstudio(PeriodoEstudio pPeriodoEstudio) {
            this.periodoEstudio = pPeriodoEstudio;
        }

        public Persona getDocente() {
            return Docente;
        }

        public void setDocente(Persona pDocente) {
            this.Docente = pDocente;
        }

        @Override
        public String toString() {
            return "PeriodoEstudioDocentePK{" + "Peri=" + periodoEstudio + ", Doc=" + Docente + '}';
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 61 * hash + Objects.hashCode(this.periodoEstudio);
            hash = 61 * hash + Objects.hashCode(this.Docente);
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
            final PeriodoEstudioDocentePK other = (PeriodoEstudioDocentePK) obj;
            if (!Objects.equals(this.periodoEstudio, other.periodoEstudio)) {
                return false;
            }
            if (!Objects.equals(this.Docente, other.Docente)) {
                return false;
            }
            return true;
        }

        

    }

    
}


