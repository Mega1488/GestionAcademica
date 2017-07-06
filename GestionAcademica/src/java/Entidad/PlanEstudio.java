/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
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
@Table(name = "PLAN_ESTUDIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlanEstudio.findAll",       query = "SELECT t FROM PlanEstudio t"),
    @NamedQuery(name = "PlanEstudio.findByPK",      query = "SELECT t FROM PlanEstudio t WHERE t.planPK.PlaEstCod =:PlaEstCod and t.planPK.carrera.CarCod =:CarCod"),
    @NamedQuery(name = "PlanEstudio.findByCarrera", query = "SELECT t FROM PlanEstudio t WHERE t.planPK.carrera.CarCod = :CarCod"),
    @NamedQuery(name = "PlanEstudio.findLast",      query = "SELECT t FROM PlanEstudio t WHERE t.planPK.carrera.CarCod = :CarCod ORDER BY t.planPK.PlaEstCod DESC")})

public class PlanEstudio implements Serializable {

    private static final long serialVersionUID = 1L;
   
    //-ATRIBUTOS
    @EmbeddedId
    private final PlanEstudioPK planPK;
    @Column(name = "PlaEstNom", length = 100)
    private String PlaEstNom;
    @Column(name = "PlaEstDsc", length = 500)
    private String PlaEstDsc;
    @Column(name = "PlaEstCreNec", precision=10, scale=2)
    private Double PlaEstCreNec;
    @Column(name = "PlaEstCatCod")
    private Integer PlaEstCatCod;
    @Column(name = "ObjFchMod", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ObjFchMod;
    @OneToMany(targetEntity = Materia.class, cascade= CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name="CarCod", referencedColumnName="CarCod"),
            @JoinColumn(name="PlaEstCod", referencedColumnName="PlaEstCod")
        })
    private List<Materia> lstMateria;
    
    
    //-CONSTRUCTOR
    public PlanEstudio() {
        this.planPK = new PlanEstudioPK();
    }
    

    //-GETTERS Y SETTERS

    public String getPlaEstNom() {
        return PlaEstNom;
    }

    public void setPlaEstNom(String PlaEstNom) {
        this.PlaEstNom = PlaEstNom;
    }

    public String getPlaEstDsc() {
        return PlaEstDsc;
    }

    public void setPlaEstDsc(String PlaEstDsc) {
        this.PlaEstDsc = PlaEstDsc;
    }

    public Double getPlaEstCreNec() {
        return PlaEstCreNec;
    }

    public void setPlaEstCreNec(Double PlaEstCreNec) {
        this.PlaEstCreNec = PlaEstCreNec;
    }

    public Integer getPlaEstCatCod() {
        return PlaEstCatCod;
    }

    public void setPlaEstCatCod(Integer PlaEstCatCod) {
        this.PlaEstCatCod = PlaEstCatCod;
    }

    public Date getObjFchMod() {
        return ObjFchMod;
    }

    public void setObjFchMod(Date ObjFchMod) {
        this.ObjFchMod = ObjFchMod;
    }

    public List<Materia> getLstMateria() {
        return lstMateria;
    }

    public void setLstMateria(List<Materia> lstMateria) {
        this.lstMateria = lstMateria;
    }
    
    
    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (planPK.PlaEstCod() != null ? planPK.PlaEstCod().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanEstudio)) {
            return false;
        }
        PlanEstudio other = (PlanEstudio) object;
        if ((this.planPK.PlaEstCod() == null && other.planPK.PlaEstCod() != null) || (this.planPK.PlaEstCod() != null && !this.planPK.PlaEstCod().equals(other.planPK.PlaEstCod()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.PlanEstudio[ id=" + planPK.PlaEstCod() + " ]";
    }
    
    
    @Embeddable
    public static class PlanEstudioPK implements Serializable {
           private Integer PlaEstCod;

           @ManyToOne(targetEntity = Carrera.class, optional=false)
           @JoinColumn(name="CarCod", referencedColumnName = "CarCod")
           private Carrera carrera;


           public Integer PlaEstCod() {
               return PlaEstCod;
           }

           public void setPlaEstCod(Integer PlaEstCod) {
               this.PlaEstCod = PlaEstCod;
           }

           public Carrera getCarrera() {
               return carrera;
           }

           public void setCarrera(Carrera carrera) {
               this.carrera = carrera;
           }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 19 * hash + Objects.hashCode(this.PlaEstCod);
            hash = 19 * hash + Objects.hashCode(this.carrera);
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
            final PlanEstudioPK other = (PlanEstudioPK) obj;
            if (!Objects.equals(this.PlaEstCod, other.PlaEstCod)) {
                return false;
            }
            if (!Objects.equals(this.carrera, other.carrera)) {
                return false;
            }
            return true;
        }
           
           
       }
    
    

}


 