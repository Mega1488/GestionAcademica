/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import java.io.Serializable;
import java.util.Date;
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

/**
 *
 * @author alvar
 */
@Entity
@Table(name = "INSCRIPCION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Inscripcion.findAll",       query = "SELECT t FROM Inscripcion t"),
    @NamedQuery(name = "Inscripcion.findByPK",      query = "SELECT t FROM Inscripcion t WHERE t.inscripcionPK.InsCod =:InsCod and t.inscripcionPK.Persona.PerCod =:PerCod "),
    @NamedQuery(name = "Inscripcion.findLast",      query = "SELECT t FROM Inscripcion t WHERE t.inscripcionPK.Persona.PerCod = :PerCod ORDER BY t.inscripcionPK.InsCod DESC")})

public class Inscripcion implements Serializable {

    private static final long serialVersionUID = 1L;
    
    //-ATRIBUTOS
    @EmbeddedId
    private final InscripcionPK inscripcionPK;
    @Column(name = "AluFchCert", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date AluFchCert;   
    @Column(name = "AluFchInsc", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date AluFchInsc;
    @OneToOne(targetEntity = Persona.class, optional=true)        
    @JoinColumn(name="InsPerCod", referencedColumnName="PerCod")
    private Persona PersonaInscribe;
    @ManyToOne(targetEntity = PlanEstudio.class, optional=true)        
    @JoinColumns({
        @JoinColumn(name="CarInsCarCod", referencedColumnName="CarCod"),
        @JoinColumn(name="CarInsPlaEstCod", referencedColumnName="PlaEstCod")
    })
    private PlanEstudio PlanEstudio;
    @OneToOne(targetEntity = Curso.class, optional=true)        
    @JoinColumn(name="CurInsCurCod", referencedColumnName="CurCod")
    private Curso Curso;
    @Column(name = "ObjFchMod", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ObjFchMod;
    
    //-CONSTRUCTOR

    public Inscripcion() {
        this.inscripcionPK = new InscripcionPK();
    }
    
    //-GETTERS Y SETTERS

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

    public Date getObjFchMod() {
        return ObjFchMod;
    }

    public void setObjFchMod(Date ObjFchMod) {
        this.ObjFchMod = ObjFchMod;
    }
    
    


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (inscripcionPK != null ? inscripcionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inscripcion)) {
            return false;
        }
        Inscripcion other = (Inscripcion) object;
        if ((this.inscripcionPK == null && other.inscripcionPK != null) || (this.inscripcionPK != null && !this.inscripcionPK.equals(other.inscripcionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.Inscripcion[ id=" + inscripcionPK.toString() + " ]";
    }
    
}


@Embeddable
 class InscripcionPK implements Serializable {
    @OneToOne(targetEntity = Persona.class, optional=false)        
    @JoinColumn(name="AluPerCod", referencedColumnName="PerCod")
    private Persona Persona;
    
    private Integer InsCod;    

    public InscripcionPK() {
    }

    public Persona getPersona() {
        return Persona;
    }

    public void setPersona(Persona Persona) {
        this.Persona = Persona;
    }

    public Integer getInsCod() {
        return InsCod;
    }

    public void setInsCod(Integer InsCod) {
        this.InsCod = InsCod;
    }

    @Override
    public String toString() {
        return "InscripcionPK{" + "Persona=" + Persona + ", InsCod=" + InsCod + '}';
    }
}
