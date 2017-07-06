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
@Table(name = "MATERIA_REVALIDA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MateriaRevalida.findAll",       query = "SELECT t FROM MateriaRevalida t"),
    @NamedQuery(name = "MateriaRevalida.findByPK",      query = "SELECT t FROM MateriaRevalida t WHERE t.materiaRevalidaPK =:revalidaPK")})

public class MateriaRevalida implements Serializable {

    private static final long serialVersionUID = 1L;
    
    //-ATRIBUTOS
    @EmbeddedId
    private final MateriaRevalidaPK materiaRevalidaPK;
    @Column(name = "ObjFchMod", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ObjFchMod;

    //-CONSTRUCTOR
    public MateriaRevalida() {
        this.materiaRevalidaPK = new MateriaRevalidaPK();
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
        hash += (materiaRevalidaPK != null ? materiaRevalidaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MateriaRevalida)) {
            return false;
        }
        MateriaRevalida other = (MateriaRevalida) object;
        if ((this.materiaRevalidaPK == null && other.materiaRevalidaPK != null) || (this.materiaRevalidaPK != null && !this.materiaRevalidaPK.equals(other.materiaRevalidaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.MateriaRevalida[ id=" + materiaRevalidaPK.toString() + " ]";
    }
    
    
    @Embeddable
    public static class MateriaRevalidaPK implements Serializable {
        @ManyToOne(targetEntity = Inscripcion.class, optional=false)        
        @JoinColumns({
                @JoinColumn(name="InsCod", referencedColumnName="InsCod"),
                @JoinColumn(name="AluPerCod", referencedColumnName="AluPerCod"),
            })
        private Inscripcion inscripcion;

        @ManyToOne(targetEntity = Materia.class, optional=false)        
        @JoinColumns({
                @JoinColumn(name="CarCod", referencedColumnName="CarCod"),
                @JoinColumn(name="PlaEstCod", referencedColumnName="PlaEstCod"),
                @JoinColumn(name="MatCod", referencedColumnName="MatCod")
            })
        private Materia materia;

        public MateriaRevalidaPK() {
        }

        public Inscripcion getInscripcion() {
            return inscripcion;
        }

        public void setInscripcion(Inscripcion inscripcion) {
            this.inscripcion = inscripcion;
        }

        public Materia getMateria() {
            return materia;
        }

        public void setMateria(Materia materia) {
            this.materia = materia;
        }

        @Override
        public String toString() {
            return "MateriaRevalidaPK{" + "inscripcion=" + inscripcion + ", materia=" + materia + '}';
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 67 * hash + Objects.hashCode(this.inscripcion);
            hash = 67 * hash + Objects.hashCode(this.materia);
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
            final MateriaRevalidaPK other = (MateriaRevalidaPK) obj;
            if (!Objects.equals(this.inscripcion, other.inscripcion)) {
                return false;
            }
            if (!Objects.equals(this.materia, other.materia)) {
                return false;
            }
            return true;
        }



    }

    
}

