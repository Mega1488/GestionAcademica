/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import Dominio.ClaseAbstracta;
import Enumerado.TipoPeriodo;
import java.io.Serializable;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author alvar
 */

@JsonIgnoreProperties({"curso"})

@Entity
@Table(name = "MODULO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Modulo.findAll", query = "SELECT m FROM Modulo m"),
    @NamedQuery(name = "Modulo.findModAfter", query = "SELECT m FROM Modulo m  WHERE m.ObjFchMod >= :ObjFchMod"),
    @NamedQuery(name = "Modulo.findByPK", query = "SELECT m FROM Modulo m WHERE m.curso.CurCod = :CurCod and m.ModCod = :ModCod"),
    @NamedQuery(name = "Modulo.findByCurso", query = "SELECT m FROM Modulo m WHERE m.curso.CurCod = :CurCod"),
    @NamedQuery(name = "Modulo.findByPeriodo", query = "SELECT m FROM Modulo m WHERE (m.curso.CurCod =:CurCod or :CurCod IS NULL) and m.ModTpoPer = :TpoPer and m.ModPerVal =:PerVal"),
    @NamedQuery(name = "Modulo.findLast", query = "SELECT  m FROM Modulo m WHERE m.curso.CurCod = :CurCod ORDER BY m.ModCod desc")})

public class Modulo extends ClaseAbstracta implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native" )
    @Column(name = "ModCod")
    private Long ModCod;
        
    @OneToOne(targetEntity = Curso.class)
    @JoinColumn(name="CurCod", referencedColumnName = "CurCod")
    private Curso curso;    
    
    @Column(name = "ModNom", length = 100, unique = true)
    private String ModNom;
    
    @Column(name = "ModDsc", length = 500)
    private String ModDsc;
    
    
    @Column(name = "ModTpoPer")
    private TipoPeriodo ModTpoPer;
    
    @Column(name = "ModPerVal",precision=10, scale=2)
    private Double ModPerVal;
    
    @Column(name = "ModCntHor",precision=10, scale=2)
    private Double ModCntHor;
    
    @Column(name = "ModEstCod")
    private Long ModEstCod;
    
    @Column(name = "ObjFchMod", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ObjFchMod;
    
    @OneToMany(targetEntity = Evaluacion.class, cascade= CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name="ModEvlModCod", referencedColumnName="ModCod")
    @Fetch(FetchMode.SUBSELECT)
    private List<Evaluacion> lstEvaluacion;
    
    public Modulo() {
    }

    @XmlTransient
    public List<Evaluacion> getLstEvaluacion() {
        return lstEvaluacion;
    }

    public void setLstEvaluacion(List<Evaluacion> lstEvaluacion) {
        this.lstEvaluacion = lstEvaluacion;
    }
    
    public Evaluacion getEvaluacionById(Long EvlCod){
        
        Evaluacion evaluacion = new Evaluacion();
        
        for(Evaluacion evl : this.lstEvaluacion)
        {
            if(evl.getEvlCod().equals(EvlCod))
            {
                evaluacion = evl;
                break;
            }
        }
        
        return evaluacion;
    }
    
    public Long getModCod() {
        return this.ModCod;
    }

    public void setModCod(Long pModCod) {
        this.ModCod = pModCod;
    }

    public Long getModEstCod() {
        return ModEstCod;
    }

    public void setModEstCod(Long ModEstCod) {
        this.ModEstCod = ModEstCod;
    }

    
    
    @XmlTransient
    public Curso getCurso() {
        return this.curso;
    }

    public void setCurso(Curso pCurso) {
        this.curso = pCurso;
    }

    public String getModNom() {
        return ModNom;
    }

    public void setModNom(String ModNom) {
        this.ModNom = ModNom;
    }

    public String getModDsc() {
        return ModDsc;
    }

    public void setModDsc(String ModDsc) {
        this.ModDsc = ModDsc;
    }

    public TipoPeriodo getModTpoPer() {
        return ModTpoPer;
    }

    public void setModTpoPer(TipoPeriodo ModTpoPer) {
        this.ModTpoPer = ModTpoPer;
    }

    public Double getModPerVal() {
        return ModPerVal;
    }

    public void setModPerVal(Double ModPerVal) {
        this.ModPerVal = ModPerVal;
    }

    public Double getModCntHor() {
        return ModCntHor;
    }

    public void setModCntHor(Double ModCntHor) {
        this.ModCntHor = ModCntHor;
    }

    public Date getObjFchMod() {
        return ObjFchMod;
    }

    public void setObjFchMod(Date ObjFchMod) {
        this.ObjFchMod = ObjFchMod;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + Objects.hashCode(this.ModCod) + Objects.hashCode(this.curso);
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
        final Modulo other = (Modulo) obj;
        if (!Objects.equals(this.ModCod, other.ModCod)) {
            return false;
        }
        if (!Objects.equals(this.curso, other.curso)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modulo{" + "ModCod=" + ModCod + ", curso=" + curso + ", ModNom=" + ModNom + ", ModDsc=" + ModDsc + ", ModTpoPer=" + ModTpoPer + ", ModPerVal=" + ModPerVal + ", ModCntHor=" + ModCntHor + ", ModEstCod=" + ModEstCod + ", ObjFchMod=" + ObjFchMod + ", lstEvaluacion=" + lstEvaluacion + '}';
    }

    
    @Override
    public Long GetPrimaryKey() {
        return this.ModCod;
    }
    
    
}

