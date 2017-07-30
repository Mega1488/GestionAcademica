/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import Enumerado.TipoAprobacion;
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
    @NamedQuery(name = "Escolaridad.findByAlumno",  query = "SELECT t FROM Escolaridad t WHERE t.alumno.PerCod = :PerCod")
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
    
//    @ManyToOne(targetEntity = Inscripcion.class)        
//    @JoinColumn(name="InsCod", referencedColumnName="InsCod")
//    private Inscripcion inscripcion;
    
    @ManyToOne(targetEntity = Persona.class)
    @JoinColumn(name="EscAluPerCod", referencedColumnName="PerCod")
    private Persona alumno;

    @ManyToOne(targetEntity = Materia.class)
    @JoinColumn(name="EscMatCod", referencedColumnName="MatCod")
    private Materia materia;
    
    @ManyToOne(targetEntity = Modulo.class)
    @JoinColumn(name="EscModCod", referencedColumnName="ModCod")
    private Modulo modulo;
    
    @ManyToOne(targetEntity = Curso.class)
    @JoinColumn(name="EscCurCod", referencedColumnName="CurCod")
    private Curso curso;
    
    @ManyToOne(targetEntity = Persona.class)
    @JoinColumn(name="EscPerCod", referencedColumnName="PerCod")
    private Persona IngresadaPor;

    @Column(name = "EscCalVal", precision=10, scale=2)
    private Double EscCalVal;
    
    @Column(name = "EscCurVal", precision=10, scale=2)
    private Double EscCurVal;
    
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

    public Persona getIngresadaPor() {
        return IngresadaPor;
    }

    public void setIngresadaPor(Persona IngresadaPor) {
        this.IngresadaPor = IngresadaPor;
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

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Persona getAlumno() {
        return alumno;
    }

    public void setAlumno(Persona alumno) {
        this.alumno = alumno;
    }

    public Double getEscCurVal() {
        return EscCurVal;
    }

    public void setEscCurVal(Double EscCurVal) {
        this.EscCurVal = EscCurVal;
    }
    
    

    public String getAprobacion() {
        if(this.Revalida()) return "Revalida";
        
        if(this.materia.MateriaExonera(EscCurVal)) return "Exonera";
        
        if(this.EscCalVal >= 70) return "Aprobado";
        if(this.EscCalVal < 70) return "Eliminado";
        return "";
    }
    
    public Boolean getAprobado() {
        if(this.EscCalVal >= 70) return true;
        return false;
    }
    
    public Boolean Revalida(){
        if(this.EscCalVal == null) return false;
        return this.EscCalVal.equals(Double.NaN);
    }
    
    public String getNombreEstudio()
    {
        
        if(this.getModulo() != null) return this.getModulo().getModNom();
        if(this.getCurso() != null) return this.getCurso().getCurNom();
        if(this.getMateria() != null) return this.getMateria().getMatNom();

        return "";
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
        return "Escolaridad{" + "EscCod=" + EscCod + ", materia=" + materia + ", modulo=" + modulo + ", IngresadaPor=" + IngresadaPor + ", EscCalVal=" + EscCalVal + ", EscFch=" + EscFch + ", ObjFchMod=" + ObjFchMod + '}';
    }

    
  
}


