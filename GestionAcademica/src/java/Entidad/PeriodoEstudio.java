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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alvar
 */
@Entity
@Table(name = "PERIODO_ESTUDIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PeriodoEstudio.findAll",       query = "SELECT t FROM PeriodoEstudio t"),
})
public class PeriodoEstudio implements Serializable {

    private static final long serialVersionUID = 1L;
    
    //-ATRIBUTOS
    @EmbeddedId
    private final PeriodoEstudioPK periodoEstudioPK;
    
    @ManyToOne(targetEntity = Materia.class, optional=true)        
    @JoinColumn(name="MatEstMatCod", referencedColumnName="MatCod")
    private Materia Materia;
    
    @ManyToOne(targetEntity = Modulo.class, optional=true)        
    @JoinColumn(name="ModEstModCod", referencedColumnName="ModCod")
    private Modulo Modulo;
    
    @Column(name = "ObjFchMod", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ObjFchMod;

    //-CONSTRUCTOR

    public PeriodoEstudio() {
        this.periodoEstudioPK = new PeriodoEstudioPK();
    }
    
    //-GETTERS Y SETTERS

    public Materia getMateria() {
        return Materia;
    }

    public void setMateria(Materia materia) {
        this.Materia = materia;
    }

    public Modulo getModulo() {
        return Modulo;
    }

    public void setModulo(Modulo modulo) {
        this.Modulo = modulo;
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
        hash += (periodoEstudioPK != null ? periodoEstudioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PeriodoEstudio)) {
            return false;
        }
        PeriodoEstudio other = (PeriodoEstudio) object;
        if ((this.periodoEstudioPK == null && other.periodoEstudioPK != null) || (this.periodoEstudioPK != null && !this.periodoEstudioPK.equals(other.periodoEstudioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.PeriodoEstudio[ id=" + periodoEstudioPK + " ]";
    }
    
    
    @Embeddable
    public static class PeriodoEstudioPK implements Serializable {
        @OneToOne(targetEntity = Periodo.class, optional=false)        
        @JoinColumn(name="PeriCod", referencedColumnName="PeriCod")
        private Periodo Periodo;

        private Integer PeriEstCod;

        public PeriodoEstudioPK() {
        }

        public Periodo getPeriodo() {
            return Periodo;
        }

        public void setPeriodo(Periodo periodo) {
            this.Periodo = periodo;
        }

        public Integer getPeriEstCod() {
            return PeriEstCod;
        }

        public void setPeriEstCod(Integer PeriEstCod) {
            this.PeriEstCod = PeriEstCod;
        }

        @Override
        public String toString() {
            return "PeriodoEstudioPK{" + "periodo=" + Periodo + ", PeriEstCod=" + PeriEstCod + '}';
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 71 * hash + Objects.hashCode(this.Periodo);
            hash = 71 * hash + Objects.hashCode(this.PeriEstCod);
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
            final PeriodoEstudioPK other = (PeriodoEstudioPK) obj;
            if (!Objects.equals(this.Periodo, other.Periodo)) {
                return false;
            }
            if (!Objects.equals(this.PeriEstCod, other.PeriEstCod)) {
                return false;
            }
            return true;
        }





    }
}


