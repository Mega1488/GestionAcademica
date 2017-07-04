/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alvar
 */
@Entity
@Table(name = "CARRERA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Carrera.findAll",           query = "SELECT c FROM Carrera c"),
    @NamedQuery(name = "Carrera.findByCarCod",      query = "SELECT c FROM Carrera c WHERE c.CarCod = :CarCod"),
    @NamedQuery(name = "Carrera.findByCarNom",      query = "SELECT c FROM Carrera c WHERE c.CarNom = :CarNom"),
    @NamedQuery(name = "Carrera.findLastCarrera",   query = "SELECT c FROM Carrera c ORDER BY c.CarCod DESC")})

public class Carrera implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CarCod", nullable = false)
    private Integer CarCod;
    @Column(name = "CarNom", length = 100)
    private String CarNom;
    @Column(name = "CarDsc", length = 500)
    private String CarDsc;
    @Column(name = "CarFac", length = 255)
    private String CarFac;
    @Column(name = "CarCrt", length = 255)
    private String CarCrt;
    @Column(name = "CarCatCod")
    private Long CarCatCod;
    @Column(name = "ObjFchMod")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ObjFchMod;
    @OneToMany(targetEntity = PlanEstudio.class, cascade= CascadeType.ALL)
    @JoinColumn(name="CarCod")
    private List<PlanEstudio> lstPlanes;

    
    
    public Carrera() {
    }

    public Carrera(Integer CarCod, String CarNom, String CarDsc, String CarFac, String CarCrt, Long CarCatCod, Date ObjFchMod) {
        this.CarCod = CarCod;
        this.CarNom = CarNom;
        this.CarDsc = CarDsc;
        this.CarFac = CarFac;
        this.CarCrt = CarCrt;
        this.CarCatCod = CarCatCod;
        this.ObjFchMod = ObjFchMod;
    }
    
    
    
    
    public Integer getCarCod() {
        return CarCod;
    }

    public void setCarCod(Integer CarCod) {
        this.CarCod = CarCod;
    }

    public String getCarNom() {
        return CarNom;
    }

    public void setCarNom(String CarNom) {
        this.CarNom = CarNom;
    }

    public String getCarDsc() {
        return CarDsc;
    }

    public void setCarDsc(String CarDsc) {
        this.CarDsc = CarDsc;
    }

    public String getCarFac() {
        return CarFac;
    }

    public void setCarFac(String CarFac) {
        this.CarFac = CarFac;
    }

    public String getCarCrt() {
        return CarCrt;
    }

    public void setCarCrt(String CarCrt) {
        this.CarCrt = CarCrt;
    }

    public Long getCarCatCod() {
        return CarCatCod;
    }

    public void setCarCatCod(Long CarCatCod) {
        this.CarCatCod = CarCatCod;
    }

    public Date getObjFchMod() {
        return ObjFchMod;
    }

    public void setObjFchMod(Date ObjFchMod) {
        this.ObjFchMod = ObjFchMod;
    }

    public List<PlanEstudio> getLstPlanes() {
        return lstPlanes;
    }

    public void setLstPlanes(List<PlanEstudio> lstPlanes) {
        this.lstPlanes = lstPlanes;
    }
   
    
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (CarCod != null ? CarCod.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Carrera)) {
            return false;
        }
        Carrera other = (Carrera) object;
        if ((this.CarCod == null && other.CarCod != null) || (this.CarCod != null && !this.CarCod.equals(other.CarCod))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.Carrera[ id=" + CarCod + " ]";
    }
    
}
