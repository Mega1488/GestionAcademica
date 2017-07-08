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
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author alvar
 */
@Entity
@Table(name = "EVALUACION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Evaluacion.findAll",            query = "SELECT t FROM Evaluacion t"),
    @NamedQuery(name = "Evaluacion.findByPK",           query = "SELECT t FROM Evaluacion t WHERE t.EvlCod =:EvlCod"),
    @NamedQuery(name = "Evaluacion.findByCurso",        query = "SELECT t FROM Evaluacion t WHERE t.CurEvl.CurCod =:CurCod"),
    @NamedQuery(name = "Evaluacion.findByModulo",       query = "SELECT t FROM Evaluacion t WHERE t.ModEvl.curso.CurCod =:CurCod and t.ModEvl.ModCod =:ModCod"),
    @NamedQuery(name = "Evaluacion.findByMateria",      query = "SELECT t FROM Evaluacion t WHERE t.MatEvl.materiaPK.plan.planPK.carrera.CarCod =:CarCod and t.MatEvl.materiaPK.plan.planPK.PlaEstCod =:PlaEstCod and t.MatEvl.materiaPK.MatCod =:MatCod"),
        
    @NamedQuery(name = "Evaluacion.findLast",      query = "SELECT t FROM Evaluacion t ORDER BY t.EvlCod DESC")})

public class Evaluacion implements Serializable {

    private static final long serialVersionUID = 1L;

    //-ATRIBUTOS
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native" )
    @Column(name = "EvlCod", nullable = false)
    private Long EvlCod;
    
    
    @OneToOne(targetEntity = Materia.class, optional=true)
    @JoinColumns({
            @JoinColumn(name="MatEvlCarCod", referencedColumnName="CarCod"),
            @JoinColumn(name="MatEvlPlaEstCod", referencedColumnName="PlaEstCod"),
            @JoinColumn(name="MatEvlMatCod", referencedColumnName="MatCod")
        })
    private Materia MatEvl;
    
    @OneToOne(targetEntity = Curso.class, optional=true)
    @JoinColumn(name="CurEvlCurCod", referencedColumnName="CurCod")
    private Curso CurEvl;
    
    @OneToOne(targetEntity = Modulo.class, optional=true)
    @JoinColumns({
            @JoinColumn(name="ModEvlCurCod", referencedColumnName="CurCod"),
            @JoinColumn(name="ModEvlModCod", referencedColumnName="ModCod")
        })
    private Modulo ModEvl;
    
    @Column(name = "EvlNom", length = 100)
    private String EvlNom;
    @Column(name = "EvlDsc", length = 500)
    private String EvlDsc;
    @Column(name = "EvlNotTot", precision=10, scale=2)
    private Double EvlNotTot;
    @ManyToOne(targetEntity = TipoEvaluacion.class, optional=true)
    @JoinColumn(name="TpoEvlCod", referencedColumnName="TpoEvlCod")
    private TipoEvaluacion tipoEvaluacion;
    @Column(name = "ObjFchMod", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ObjFchMod;

    //-CONSTRUCTOR

    public Evaluacion() {
    }
    
    
    //-GETTERS Y SETTERS
    
    public Long getEvlCod() {
        return EvlCod;
    }

    public void setEvlCod(Long EvlCod) {
        this.EvlCod = EvlCod;
    }

    public Materia getMatEvl() {
        return MatEvl;
    }

    public void setMatEvl(Materia MatEvl) {
        this.MatEvl = MatEvl;
    }

    public Modulo getModEvl() {
        return ModEvl;
    }

    public void setModEvl(Modulo ModEvl) {
        this.ModEvl = ModEvl;
    }

    public String getEvlNom() {
        return EvlNom;
    }

    public void setEvlNom(String EvlNom) {
        this.EvlNom = EvlNom;
    }

    public String getEvlDsc() {
        return EvlDsc;
    }

    public void setEvlDsc(String EvlDsc) {
        this.EvlDsc = EvlDsc;
    }

    public Double getEvlNotTot() {
        return EvlNotTot;
    }

    public void setEvlNotTot(Double EvlNotTot) {
        this.EvlNotTot = EvlNotTot;
    }

    public TipoEvaluacion getTpoEvl() {
        return tipoEvaluacion;
    }

    public void setTpoEvl(TipoEvaluacion TpoEvl) {
        this.tipoEvaluacion = TpoEvl;
    }

    public Date getObjFchMod() {
        return ObjFchMod;
    }

    public Curso getCurEvl() {
        return CurEvl;
    }

    public void setCurEvl(Curso CurEvl) {
        this.CurEvl = CurEvl;
    }

    
    public void setObjFchMod(Date ObjFchMod) {
        this.ObjFchMod = ObjFchMod;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (EvlCod != null ? EvlCod.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Evaluacion)) {
            return false;
        }
        Evaluacion other = (Evaluacion) object;
        if ((this.EvlCod == null && other.EvlCod != null) || (this.EvlCod != null && !this.EvlCod.equals(other.EvlCod))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.Evaluacion[ id=" + EvlCod + " ]";
    }
    
}
