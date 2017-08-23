/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import Dominio.SincHelper;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author alvar
 */


@Entity
@Table(name = "PLAN_ESTUDIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlanEstudio.findModAfter",  query = "SELECT t FROM PlanEstudio t WHERE t.ObjFchMod >= :ObjFchMod"),
    @NamedQuery(name = "PlanEstudio.findAll",       query = "SELECT t FROM PlanEstudio t")})

public class PlanEstudio extends SincHelper implements Serializable {

    private static final long serialVersionUID = 1L;

    //-ATRIBUTOS
    
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native" )
    @Column(name = "PlaEstCod")
    private Long PlaEstCod;

    @ManyToOne(targetEntity = Carrera.class)
    @JoinColumn(name="CarCod", referencedColumnName = "CarCod")
    private Carrera carrera;
           
    @Column(name = "PlaEstNom", length = 100)
    private String PlaEstNom;
    
    @Column(name = "PlaEstDsc", length = 500)
    private String PlaEstDsc;
    
    @Column(name = "PlaEstCreNec", precision=10, scale=2)
    private Double PlaEstCreNec;
    
    @Column(name = "PlaEstCatCod")
    private Long PlaEstCatCod;
    
    @Column(name = "ObjFchMod", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ObjFchMod;
    
    @OneToMany(targetEntity = Materia.class, cascade= CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name="PlaEstCod", referencedColumnName="PlaEstCod")
    @Fetch(FetchMode.SUBSELECT)
    private List<Materia> lstMateria;
    
    
    //-CONSTRUCTOR
    public PlanEstudio() {
        this.lstMateria = new ArrayList<>();
    }
    

    //-GETTERS Y SETTERS

    public Long getPlaEstCod() {
        return PlaEstCod;
    }

    public void setPlaEstCod(Long PlaEstCod) {
        this.PlaEstCod = PlaEstCod;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

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

    public Long getPlaEstCatCod() {
        return PlaEstCatCod;
    }

    public void setPlaEstCatCod(Long PlaEstCatCod) {
        this.PlaEstCatCod = PlaEstCatCod;
    }

    public Date getObjFchMod() {
        return ObjFchMod;
    }

    public void setObjFchMod(Date ObjFchMod) {
        this.ObjFchMod = ObjFchMod;
    }

    @JsonIgnore
    @XmlTransient
    public List<Materia> getLstMateria() {
        return lstMateria;
    }

    public void setLstMateria(List<Materia> lstMateria) {
        this.lstMateria = lstMateria;
    }
    
    public Materia getMateriaById(Long MatCod){
        
        Materia pMat = new Materia();
        
        for(Materia mat : this.lstMateria)
        {
            if(mat.getMatCod().equals(MatCod))
            {
                pMat = mat;
                break;
            }
        }
        
        return pMat;
    }
    
    public String getCarreraPlanNombre()
    {
        if(this.getCarrera() == null) return this.getPlaEstNom();
        return this.getCarrera().getCarNom() + " - " + this.getPlaEstNom();
    }
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.PlaEstCod);
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
        final PlanEstudio other = (PlanEstudio) obj;
        if (!Objects.equals(this.PlaEstCod, other.PlaEstCod)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PlanEstudio{" + "PlaEstCod=" + PlaEstCod + ", carrera=" + carrera + ", PlaEstNom=" + PlaEstNom + ", PlaEstDsc=" + PlaEstDsc + ", PlaEstCreNec=" + PlaEstCreNec + ", PlaEstCatCod=" + PlaEstCatCod + ", ObjFchMod=" + ObjFchMod + ", lstMateria=" + lstMateria + '}';
    }
   
}


 