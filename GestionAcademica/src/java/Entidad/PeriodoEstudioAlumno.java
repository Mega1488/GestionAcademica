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
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author alvar
 */
@JsonIgnoreProperties({"periodoEstudio"})

@Entity
@Table(
        name = "PERIODO_ESTUDIO_ALUMNO",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"PeriEstCod", "AluPerCod"})}
    )
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PeriodoEstudioAlumno.findAll",       query = "SELECT t FROM PeriodoEstudioAlumno t"),
})
public class PeriodoEstudioAlumno implements Serializable {

    private static final long serialVersionUID = 1L;
   
    //-ATRIBUTOS
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native" )
    @Column(name = "PeriEstAluCod", nullable = false)
    private Long PeriEstAluCod;
    
    @OneToOne(targetEntity = PeriodoEstudio.class)
    @JoinColumn(name="PeriEstCod", referencedColumnName="PeriEstCod")
    private PeriodoEstudio periodoEstudio;

    @ManyToOne(targetEntity = Persona.class)
    @JoinColumn(name="AluPerCod", referencedColumnName="PerCod")
    private Persona Alumno;
   
    
    @ManyToOne(targetEntity = Persona.class)
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
    }
    
    //-GETTERS Y SETTERS

    public Long getPeriEstAluCod() {
        return PeriEstAluCod;
    }

    public void setPeriEstAluCod(Long PeriEstAluCod) {
        this.PeriEstAluCod = PeriEstAluCod;
    }

    public PeriodoEstudio getPeriodoEstudio() {
        return periodoEstudio;
    }

    public void setPeriodoEstudio(PeriodoEstudio PeriodoEstudio) {
        this.periodoEstudio = PeriodoEstudio;
    }

    public Persona getAlumno() {
        return Alumno;
    }

    public void setAlumno(Persona Alumno) {
        this.Alumno = Alumno;
    }

    public Persona getInscritoPor() {
        return InscritoPor;
    }

    public void setInscritoPor(Persona InscritoPor) {
        this.InscritoPor = InscritoPor;
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
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.PeriEstAluCod);
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
        final PeriodoEstudioAlumno other = (PeriodoEstudioAlumno) obj;
        if (!Objects.equals(this.PeriEstAluCod, other.PeriEstAluCod)) {
            return false;
        }
        return true;
    }

   

    @Override
    public String toString() {
        return "PeriodoEstudioAlumno{" + "PeriEstAluCod=" + PeriEstAluCod + ", PeriodoEstudio=" + periodoEstudio + ", Alumno=" + Alumno + ", InscritoPor=" + InscritoPor + ", PerInsFchInsc=" + PerInsFchInsc + ", PerInsCalFin=" + PerInsCalFin + ", PerInsFrz=" + PerInsFrz + ", ObjFchMod=" + ObjFchMod + '}';
    }
    
    
    
    
}


