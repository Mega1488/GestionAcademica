/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import Enumerado.TipoPeriodo;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "PERIODO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Periodo.findAll",       query = "SELECT t FROM Periodo t"),
    @NamedQuery(name = "Periodo.findByPK",      query = "SELECT t FROM Periodo t WHERE t.PeriCod =:PeriCod"),
    @NamedQuery(name = "Periodo.findLast",      query = "SELECT t FROM Periodo t ORDER BY t.PeriCod DESC")})

public class Periodo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    //-ATRIBUTOS
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native" )
    @Column(name = "PeriCod", nullable = false)
    private Long PeriCod;
    
    @Column(name = "PerTpo")
    private TipoPeriodo PerTpo;
    @Column(name = "PerVal", precision=10, scale=2)
    private Double PerVal;
    @Column(name = "PerFchIni", columnDefinition="DATE")
    @Temporal(TemporalType.DATE)
    private Date PerFchIni;
    @Column(name = "ObjFchMod", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ObjFchMod;
    
    //-CONSTRUCTOR
    public Periodo() {
    }
    
    //-GETTERS Y SETTERS

    public Long getPeriCod() {
        return PeriCod;
    }

    public void setPeriCod(Long PeriCod) {
        this.PeriCod = PeriCod;
    }

    public TipoPeriodo getPerTpo() {
        return PerTpo;
    }

    public void setPerTpo(TipoPeriodo PerTpo) {
        this.PerTpo = PerTpo;
    }

    public Double getPerVal() {
        return PerVal;
    }

    public void setPerVal(Double PerVal) {
        this.PerVal = PerVal;
    }

    public Date getPerFchIni() {
        return PerFchIni;
    }

    public void setPerFchIni(Date PerFchIni) {
        this.PerFchIni = PerFchIni;
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
        hash += (PeriCod != null ? PeriCod.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Periodo)) {
            return false;
        }
        Periodo other = (Periodo) object;
        if ((this.PeriCod == null && other.PeriCod != null) || (this.PeriCod != null && !this.PeriCod.equals(other.PeriCod))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.Periodo[ id=" + PeriCod + " ]";
    }
    
}
