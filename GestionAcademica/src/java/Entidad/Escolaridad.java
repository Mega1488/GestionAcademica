/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.GenericGenerator;

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
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native" )
    @Column(name = "EscCod", nullable = false)
    private Long EscCod;
    
    @OneToOne(targetEntity = Inscripcion.class, optional=false)        
    @JoinColumn(name="InsCod", referencedColumnName="InsCod")
    private Inscripcion inscripcion;

    @ManyToOne(targetEntity = Materia.class, optional=true)
    @JoinColumn(name="EscMatCod", referencedColumnName="MatCod")
    private Materia materia;
    
    @ManyToOne(targetEntity = Modulo.class, optional=true)
    @JoinColumn(name="EscModCod", referencedColumnName="ModCod")
    private Modulo modulo;   
    
    @ManyToOne(targetEntity = Persona.class, optional=true)
    @JoinColumn(name="EscPerCod", referencedColumnName="PerCod")
    private Persona InscritoPor;

    @Column(name = "EscCalVal", precision=10, scale=2)
    private Double EscCalVal;
    
    @Column(name = "EscFch", columnDefinition="DATE")
    @Temporal(TemporalType.DATE)
    private Date EscFch;
    
    @Column(name = "ObjFchMod", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ObjFchMod;

    //-CONSTRUCTOR
    public Escolaridad() {
    }

    //-GETTERS Y SETTERS

    public Long getEscCod() {
        return EscCod;
    }

    public void setEscCod(Long EscCod) {
        this.EscCod = EscCod;
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

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public Persona getInscritoPor() {
        return InscritoPor;
    }

    public void setInscritoPor(Persona InscritoPor) {
        this.InscritoPor = InscritoPor;
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

    public Date getObjFchMod() {
        return ObjFchMod;
    }

    public void setObjFchMod(Date ObjFchMod) {
        this.ObjFchMod = ObjFchMod;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.EscCod);
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
        final Escolaridad other = (Escolaridad) obj;
        if (!Objects.equals(this.EscCod, other.EscCod)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Escolaridad{" + "EscCod=" + EscCod + ", inscripcion=" + inscripcion + ", materia=" + materia + ", modulo=" + modulo + ", InscritoPor=" + InscritoPor + ", EscCalVal=" + EscCalVal + ", EscFch=" + EscFch + ", ObjFchMod=" + ObjFchMod + '}';
    }

    
  
}


