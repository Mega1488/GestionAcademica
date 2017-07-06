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
@Table(name = "PERIODO_ESTUDIO_ALUMNO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PeriodoEstudioAlumno.findAll",       query = "SELECT t FROM PeriodoEstudioAlumno t"),
})
public class PeriodoEstudioAlumno implements Serializable {

    private static final long serialVersionUID = 1L;
   
    //-ATRIBUTOS
    @EmbeddedId
    private final PeriodoEstudioAlumnoPK periodoEstudioAlumnoPK;
    @ManyToOne(targetEntity = Persona.class, optional=false)
    @JoinColumn(name="PerInsPerCod", referencedColumnName="PerCod")
    private Persona InscritoPor;
    @Column(name = "PerInsFchInsc", columnDefinition="DATE")
    @Temporal(TemporalType.DATE)
    private Date PerInsFchInsc;
    @Column(name = "PerInsCalFin", precision=10, scale=2)
    private Double PerInsCalFin;
    @Column(name = "PerInsFrz")
    private Boolean PerInsFrz;
    
    @Column(name = "ObjFchMod", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ObjFchMod;


    //-CONSTRUCTOR
    public PeriodoEstudioAlumno() {
        this.periodoEstudioAlumnoPK = new PeriodoEstudioAlumnoPK();
    }
    
    //-GETTERS Y SETTERS

    public Persona getInscritoPor() {
        return InscritoPor;
    }

    public void setInscritoPor(Persona Ins) {
        this.InscritoPor = Ins;
    }

    public Date getPerInsFchInsc() {
        return PerInsFchInsc;
    }

    public void setPerInsFchInsc(Date PerInsFchInsc) {
        this.PerInsFchInsc = PerInsFchInsc;
    }

    public Double getPerInsCalFin() {
        return PerInsCalFin;
    }

    public void setPerInsCalFin(Double PerInsCalFin) {
        this.PerInsCalFin = PerInsCalFin;
    }

    public Boolean getPerInsFrz() {
        return PerInsFrz;
    }

    public void setPerInsFrz(Boolean PerInsFrz) {
        this.PerInsFrz = PerInsFrz;
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
        hash += (periodoEstudioAlumnoPK != null ? periodoEstudioAlumnoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PeriodoEstudioAlumno)) {
            return false;
        }
        PeriodoEstudioAlumno other = (PeriodoEstudioAlumno) object;
        if ((this.periodoEstudioAlumnoPK == null && other.periodoEstudioAlumnoPK != null) || (this.periodoEstudioAlumnoPK != null && !this.periodoEstudioAlumnoPK.equals(other.periodoEstudioAlumnoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.PeriodoEstudioAlumno[ id=" + periodoEstudioAlumnoPK.toString() + " ]";
    }
    
    
    @Embeddable
    public static class PeriodoEstudioAlumnoPK implements Serializable {
        @ManyToOne(targetEntity = PeriodoEstudio.class, optional=false)
        @JoinColumns({
                @JoinColumn(name="PeriCod", referencedColumnName="PeriCod"),
                @JoinColumn(name="PeriEstCod", referencedColumnName="PeriEstCod")
            })
        private PeriodoEstudio PeriodoEstudio;

        @ManyToOne(targetEntity = Persona.class, optional=false)
        @JoinColumn(name="AluPerCod", referencedColumnName="PerCod")
        private Persona Alumno;

        public PeriodoEstudioAlumnoPK() {
        }

        public PeriodoEstudio getPeriodoEstudio() {
            return PeriodoEstudio;
        }

        public void setPeriodoEstudio(PeriodoEstudio Peri) {
            this.PeriodoEstudio = Peri;
        }

        public Persona getAlu() {
            return Alumno;
        }

        public void setDoc(Persona Alu) {
            this.Alumno = Alu;
        }

        @Override
        public String toString() {
            return "PeriodoEstudioAlumnoPK{" + "Peri=" + PeriodoEstudio + ", Alu=" + Alumno + '}';
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 59 * hash + Objects.hashCode(this.PeriodoEstudio);
            hash = 59 * hash + Objects.hashCode(this.Alumno);
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
            final PeriodoEstudioAlumnoPK other = (PeriodoEstudioAlumnoPK) obj;
            if (!Objects.equals(this.PeriodoEstudio, other.PeriodoEstudio)) {
                return false;
            }
            if (!Objects.equals(this.Alumno, other.Alumno)) {
                return false;
            }
            return true;
        }

        


    }
    
}


