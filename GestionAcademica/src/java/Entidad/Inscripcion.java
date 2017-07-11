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
@Table(name = "INSCRIPCION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Inscripcion.findAll",       query = "SELECT t FROM Inscripcion t")})

public class Inscripcion implements Serializable {

    private static final long serialVersionUID = 1L;
    
    //-ATRIBUTOS
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native" )
    @Column(name = "InsCod", nullable = false)
    private Long InsCod; 
    
    
    @OneToOne(targetEntity = Persona.class, optional=false)        
    @JoinColumn(name="AluPerCod", referencedColumnName="PerCod")
    private Persona Persona;

    @OneToOne(targetEntity = Persona.class, optional=true)        
    @JoinColumn(name="InsPerCod", referencedColumnName="PerCod")
    private Persona PersonaInscribe;
    
    @ManyToOne(targetEntity = PlanEstudio.class, optional=true)        
    @JoinColumn(name="CarInsPlaEstCod", referencedColumnName="PlaEstCod")
    private PlanEstudio PlanEstudio;
    
    @OneToOne(targetEntity = Curso.class, optional=true)        
    @JoinColumn(name="CurInsCurCod", referencedColumnName="CurCod")
    private Curso Curso;
          
    @Column(name = "AluFchCert", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date AluFchCert;   
    @Column(name = "AluFchInsc", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date AluFchInsc;
    @Column(name = "ObjFchMod", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ObjFchMod;
    
    //-CONSTRUCTOR

    public Inscripcion() {
    }
    
    //-GETTERS Y SETTERS

    public Long getInsCod() {
        return InsCod;
    }

    public void setInsCod(Long InsCod) {
        this.InsCod = InsCod;
    }

    public Persona getPersona() {
        return Persona;
    }

    public void setPersona(Persona Persona) {
        this.Persona = Persona;
    }

    public Persona getPersonaInscribe() {
        return PersonaInscribe;
    }

    public void setPersonaInscribe(Persona PersonaInscribe) {
        this.PersonaInscribe = PersonaInscribe;
    }

    public PlanEstudio getPlanEstudio() {
        return PlanEstudio;
    }

    public void setPlanEstudio(PlanEstudio PlanEstudio) {
        this.PlanEstudio = PlanEstudio;
    }

    public Curso getCurso() {
        return Curso;
    }

    public void setCurso(Curso Curso) {
        this.Curso = Curso;
    }

    public Date getAluFchCert() {
        return AluFchCert;
    }

    public void setAluFchCert(Date AluFchCert) {
        this.AluFchCert = AluFchCert;
    }

    public Date getAluFchInsc() {
        return AluFchInsc;
    }

    public void setAluFchInsc(Date AluFchInsc) {
        this.AluFchInsc = AluFchInsc;
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
        hash = 37 * hash + Objects.hashCode(this.InsCod);
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
        final Inscripcion other = (Inscripcion) obj;
        if (!Objects.equals(this.InsCod, other.InsCod)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Inscripcion{" + "InsCod=" + InsCod + ", Persona=" + Persona + ", PersonaInscribe=" + PersonaInscribe + ", PlanEstudio=" + PlanEstudio + ", Curso=" + Curso + ", AluFchCert=" + AluFchCert + ", AluFchInsc=" + AluFchInsc + ", ObjFchMod=" + ObjFchMod + '}';
    }

    
    
    
}



