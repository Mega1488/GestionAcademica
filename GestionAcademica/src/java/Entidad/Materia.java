/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import Enumerado.TipoAprobacion;
import Enumerado.TipoPeriodo;
import java.util.Date;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alvar
 */
@Entity
@Table(name = "MATERIA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Materia.findAll",       query = "SELECT t FROM Materia t"),
    @NamedQuery(name = "Materia.findByPK",      query = "SELECT t FROM Materia t WHERE t.materiaPK.MatCod =:MatCod and t.materiaPK.plan.planPK.PlaEstCod =:PlaEstCod and t.materiaPK.plan.planPK.carrera.CarCod =:CarCod"),
    @NamedQuery(name = "Materia.findLast",      query = "SELECT t FROM Materia t WHERE t.materiaPK.plan.planPK.carrera.CarCod = :CarCod and t.materiaPK.plan.planPK.PlaEstCod =:PlaEstCod ORDER BY t.materiaPK.MatCod DESC")})

public class Materia implements Serializable {

    private static final long serialVersionUID = 1L;
    
    //-ATRIBUTOS
    
    @EmbeddedId
    private final MateriaPK materiaPK;
    @Column(name = "MatNom", length = 100)
    private String MatNom;
    @Column(name = "MatCntHor", precision=10, scale=2)
    private Double MatCntHor;    
    @Column(name = "MatTpoApr")
    private TipoAprobacion MatTpoApr;
    @Column(name = "MatTpoPer")
    private TipoPeriodo MatTpoPer;    
    @Column(name = "MatPerVal", precision=10, scale=2)
    private Double MatPerVal;
    @ManyToOne(targetEntity = Materia.class, optional=true)
    @PrimaryKeyJoinColumn(name="PreMatCod", referencedColumnName="MatCod")
    private Materia PreMateria;
    @Column(name = "ObjFchMod", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ObjFchMod;
    
   
    //-CONSTRUCTOR

    public Materia() {
        this.materiaPK = new MateriaPK();
    }
    
    
    
    //-GETTERS Y SETTERS

    public String getMatNom() {
        return MatNom;
    }

    public void setMatNom(String MatNom) {
        this.MatNom = MatNom;
    }

    public Double getMatCntHor() {
        return MatCntHor;
    }

    public void setMatCntHor(Double MatCntHor) {
        this.MatCntHor = MatCntHor;
    }

    public TipoAprobacion getMatTpoApr() {
        return MatTpoApr;
    }

    public void setMatTpoApr(TipoAprobacion MatTpoApr) {
        this.MatTpoApr = MatTpoApr;
    }

    public TipoPeriodo getMatTpoPer() {
        return MatTpoPer;
    }

    public void setMatTpoPer(TipoPeriodo MatTpoPer) {
        this.MatTpoPer = MatTpoPer;
    }

    public Double getMatPerVal() {
        return MatPerVal;
    }

    public void setMatPerVal(Double MatPerVal) {
        this.MatPerVal = MatPerVal;
    }


    public Date getObjFchMod() {
        return ObjFchMod;
    }

    public void setObjFchMod(Date ObjFchMod) {
        this.ObjFchMod = ObjFchMod;
    }

    public Materia getPreMateria() {
        return PreMateria;
    }

    public void setPreMateria(Materia PreMateria) {
        this.PreMateria = PreMateria;
    }

    
    
    
    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (materiaPK.MatCod() != null ? materiaPK.MatCod().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Materia)) {
            return false;
        }
        Materia other = (Materia) object;
        if ((this.materiaPK.MatCod() == null && other.materiaPK.MatCod() != null) || (this.materiaPK.MatCod() != null && !this.materiaPK.MatCod().equals(other.materiaPK.MatCod()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.Materia[ id=" + materiaPK.MatCod() + " ]";
    }
    
}


 @Embeddable
 class MateriaPK implements Serializable {
        private Integer MatCod;

        @ManyToOne(targetEntity = PlanEstudio.class, optional=false)
        @JoinColumns({
            @JoinColumn(name="CarCod", referencedColumnName="CarCod"),
            @JoinColumn(name="PlaEstCod", referencedColumnName="PlaEstCod")
        })
        private PlanEstudio plan;
    

        public Integer MatCod() {
            return MatCod;
        }

        public void setMatCod(Integer MatCod) {
            this.MatCod = MatCod;
        }

        public PlanEstudio getPlanEstudio() {
            return plan;
        }

        public void setPlanEstudio(PlanEstudio plan) {
            this.plan = plan;
        }
    }