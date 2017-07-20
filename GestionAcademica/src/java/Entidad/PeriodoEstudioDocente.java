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
        name = "PERIODO_ESTUDIO_DOCENTE",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"PeriEstCod", "DocPerCod"})}
    )
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PeriodoEstudioDocente.findAll",       query = "SELECT t FROM PeriodoEstudioDocente t"),
})
public class PeriodoEstudioDocente implements Serializable {

    private static final long serialVersionUID = 1L;
   
    //-ATRIBUTOS
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native" )
    @Column(name = "PeriEstDocCod", nullable = false)
    private Long PeriEstDocCod;
    
    @OneToOne(targetEntity = PeriodoEstudio.class)
    @JoinColumn(name="PeriEstCod", referencedColumnName="PeriEstCod")
    private PeriodoEstudio periodoEstudio;

    @ManyToOne(targetEntity = Persona.class)
    @JoinColumn(name="DocPerCod", referencedColumnName="PerCod")
    private Persona Docente;
    
    @ManyToOne(targetEntity = Persona.class)
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
    }
    
    //-GETTERS Y SETTERS

    public Long getPeriEstDocCod() {
        return PeriEstDocCod;
    }

    public void setPeriEstDocCod(Long PeriEstDocCod) {
        this.PeriEstDocCod = PeriEstDocCod;
    }

    public PeriodoEstudio getPeriodoEstudio() {
        return periodoEstudio;
    }

    public void setPeriodoEstudio(PeriodoEstudio periodoEstudio) {
        this.periodoEstudio = periodoEstudio;
    }

    public Persona getDocente() {
        return Docente;
    }

    public void setDocente(Persona Docente) {
        this.Docente = Docente;
    }

    public Persona getInscritoPor() {
        return InscritoPor;
    }

    public void setInscritoPor(Persona InscritoPor) {
        this.InscritoPor = InscritoPor;
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
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.PeriEstDocCod);
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
        final PeriodoEstudioDocente other = (PeriodoEstudioDocente) obj;
        if (!Objects.equals(this.PeriEstDocCod, other.PeriEstDocCod)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PeriodoEstudioDocente{" + "PeriEstDocCod=" + PeriEstDocCod + ", periodoEstudio=" + periodoEstudio + ", Docente=" + Docente + ", InscritoPor=" + InscritoPor + ", DocFchInsc=" + DocFchInsc + ", ObjFchMod=" + ObjFchMod + '}';
    }

    
    
    
       
}


