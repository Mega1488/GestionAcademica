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
@Table(name = "ESCOLARIDAD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Escolaridad.findAll",       query = "SELECT t FROM Escolaridad t"),
})
public class Escolaridad implements Serializable {

    private static final long serialVersionUID = 1L;
    
    
    //-ATRIBUTOS
    @EmbeddedId
    private final EscolaridadPK escolaridadPK;    
    
    @ManyToOne(targetEntity = Materia.class, optional=true)
    @JoinColumns({
            @JoinColumn(name="CarCod", referencedColumnName="CarCod"),
            @JoinColumn(name="PlaEstCod", referencedColumnName="PlaEstCod"),
            @JoinColumn(name="EscMatCod", referencedColumnName="MatCod")
        })
    private Materia materia;
    
    @ManyToOne(targetEntity = Modulo.class, optional=true)
    @JoinColumns({
            @JoinColumn(name="CurCod", referencedColumnName="CurCod"),
            @JoinColumn(name="EscModCod", referencedColumnName="ModCod")
        })
    private Modulo modulo;   

    @Column(name = "EscCalVal", precision=10, scale=2)
    private Double EscCalVal;
    
    @Column(name = "EscFch", columnDefinition="DATE")
    @Temporal(TemporalType.DATE)
    private Date EscFch;
    
    @ManyToOne(targetEntity = Persona.class, optional=true)
    @JoinColumn(name="EscPerCod", referencedColumnName="PerCod")
    private Persona InscritoPor;
    
    @Column(name = "ObjFchMod", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ObjFchMod;

    //-CONSTRUCTOR
    public Escolaridad() {
        this.escolaridadPK = new EscolaridadPK();
    }

    //-GETTERS Y SETTERS

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public Double getEscCalVal() {
        return EscCalVal;
    }

    public void setEscCalVal(Double EscCalVal) {
        this.EscCalVal = EscCalVal;
    }

    public Date getEscFch() {
        return EscFch;
    }

    public void setEscFch(Date EscFch) {
        this.EscFch = EscFch;
    }

    public Persona getInscritoPor() {
        return InscritoPor;
    }

    public void setInscritoPor(Persona InscritoPor) {
        this.InscritoPor = InscritoPor;
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
        hash += (escolaridadPK != null ? escolaridadPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Escolaridad)) {
            return false;
        }
        Escolaridad other = (Escolaridad) object;
        if ((this.escolaridadPK == null && other.escolaridadPK != null) || (this.escolaridadPK != null && !this.escolaridadPK.equals(other.escolaridadPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.Escolaridad[ id=" + escolaridadPK + " ]";
    }
    
    
    @Embeddable
    public static class EscolaridadPK implements Serializable {
        @ManyToOne(targetEntity = Inscripcion.class, optional=false)        
        @JoinColumns({
                @JoinColumn(name="InsCod", referencedColumnName="InsCod"),
                @JoinColumn(name="AluPerCod", referencedColumnName="AluPerCod"),
            })
        private Inscripcion inscripcion;

        private Integer EscCod;

        public EscolaridadPK() {
        }

        public Inscripcion getInscripcion() {
            return inscripcion;
        }

        public void setInscripcion(Inscripcion inscripcion) {
            this.inscripcion = inscripcion;
        }

        public Integer getEscCod() {
            return EscCod;
        }

        public void setEscCod(Integer EscCod) {
            this.EscCod = EscCod;
        }

        @Override
        public String toString() {
            return "EscolaridadPK{" + "inscripcion=" + inscripcion + ", EscCod=" + EscCod + '}';
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 97 * hash + Objects.hashCode(this.inscripcion);
            hash = 97 * hash + Objects.hashCode(this.EscCod);
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
            final EscolaridadPK other = (EscolaridadPK) obj;
            if (!Objects.equals(this.inscripcion, other.inscripcion)) {
                return false;
            }
            if (!Objects.equals(this.EscCod, other.EscCod)) {
                return false;
            }
            return true;
        }



    }
}


