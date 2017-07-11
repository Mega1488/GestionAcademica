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
@Table(name = "PERIODO_ESTUDIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PeriodoEstudio.findAll",       query = "SELECT t FROM PeriodoEstudio t"),
})
public class PeriodoEstudio implements Serializable {

    private static final long serialVersionUID = 1L;
    
    //-ATRIBUTOS
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native" )
    @Column(name = "PeriEstCod", nullable = false)
    private Long PeriEstCod;
    
    @OneToOne(targetEntity = Periodo.class, optional=false)        
    @JoinColumn(name="PeriCod", referencedColumnName="PeriCod")
    private Periodo Periodo;

    @OneToOne(targetEntity = Materia.class, optional=true)        
    @JoinColumn(name="MatEstMatCod", referencedColumnName="MatCod")
    private Materia Materia;
    
    @OneToOne(targetEntity = Modulo.class, optional=true)        
    @JoinColumn(name="ModEstModCod", referencedColumnName="ModCod")
    private Modulo Modulo;
    
    @Column(name = "ObjFchMod", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ObjFchMod;

    //-CONSTRUCTOR

    public PeriodoEstudio() {
    }
    
    //-GETTERS Y SETTERS

    public Long getPeriEstCod() {
        return PeriEstCod;
    }

    public void setPeriEstCod(Long PeriEstCod) {
        this.PeriEstCod = PeriEstCod;
    }

    public Periodo getPeriodo() {
        return Periodo;
    }

    public void setPeriodo(Periodo Periodo) {
        this.Periodo = Periodo;
    }

    public Materia getMateria() {
        return Materia;
    }

    public void setMateria(Materia Materia) {
        this.Materia = Materia;
    }

    public Modulo getModulo() {
        return Modulo;
    }

    public void setModulo(Modulo Modulo) {
        this.Modulo = Modulo;
    }

    public Date getObjFchMod() {
        return ObjFchMod;
    }

    public void setObjFchMod(Date ObjFchMod) {
        this.ObjFchMod = ObjFchMod;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.PeriEstCod);
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
        final PeriodoEstudio other = (PeriodoEstudio) obj;
        if (!Objects.equals(this.PeriEstCod, other.PeriEstCod)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PeriodoEstudio{" + "PeriEstCod=" + PeriEstCod + ", Periodo=" + Periodo + ", Materia=" + Materia + ", Modulo=" + Modulo + ", ObjFchMod=" + ObjFchMod + '}';
    }
    
}


