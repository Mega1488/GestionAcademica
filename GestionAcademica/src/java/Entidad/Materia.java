/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import Enumerado.TipoAprobacion;
import Enumerado.TipoPeriodo;
import Utiles.Utilidades;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author alvar
 */

@JsonIgnoreProperties({"plan"})

@Entity
@Table(name = "MATERIA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Materia.findAll",       query = "SELECT t FROM Materia t"),
    @NamedQuery(name = "Materia.findByPeriodo", query = "SELECT m FROM Materia m WHERE (m.plan.PlaEstCod =:PlaEstCod or :PlaEstCod IS NULL) and m.MatTpoPer = :TpoPer and m.MatPerVal =:PerVal")
})

public class Materia implements Serializable {

    private static final long serialVersionUID = 1L;
    
    //-ATRIBUTOS
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native" )
    @Column(name = "MatCod")
    private Long MatCod;

    @ManyToOne(targetEntity = PlanEstudio.class)
    @JoinColumn(name="PlaEstCod", referencedColumnName="PlaEstCod")
    private PlanEstudio plan;

           
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
    @Column(name = "ObjFchMod", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ObjFchMod;
    
    @OneToMany(targetEntity = MateriaPrevia.class, cascade= CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name="MatCod", referencedColumnName="MatCod")
    @Fetch(FetchMode.SUBSELECT)
    private List<MateriaPrevia> lstPrevias;
    
    @OneToMany(targetEntity = Evaluacion.class, cascade= CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name="MatEvlMatCod", referencedColumnName="MatCod")
    @Fetch(FetchMode.SUBSELECT)
    private List<Evaluacion> lstEvaluacion;
    
   
    //-CONSTRUCTOR

    public Materia() {
        lstPrevias = new ArrayList<>();
    }
    
    
    //-GETTERS Y SETTERS

    public Long getMatCod() {
        return MatCod;
    }

    public void setMatCod(Long MatCod) {
        this.MatCod = MatCod;
    }

    public PlanEstudio getPlan() {
        return plan;
    }

    public void setPlan(PlanEstudio plan) {
        this.plan = plan;
    }

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

    public List<MateriaPrevia> getLstPrevias() {
        return lstPrevias;
    }

    public void setLstPrevias(List<MateriaPrevia> lstPrevias) {
        this.lstPrevias = lstPrevias;
    }

    public List<Evaluacion> getLstEvaluacion() {
        return lstEvaluacion;
    }

    public void setLstEvaluacion(List<Evaluacion> lstEvaluacion) {
        this.lstEvaluacion = lstEvaluacion;
    }

    public Evaluacion getEvaluacionById(Long EvaCod){
        
        Evaluacion pEva = new Evaluacion();
        
        for(Evaluacion eva : this.lstEvaluacion)
        {
            if(eva.getEvlCod().equals(EvaCod))
            {
                pEva = eva;
                break;
            }
        }
        
        return pEva;
    }
    
    public MateriaPrevia getPreviaById(Long MatPreCod){
        
        
        for(MateriaPrevia mat : this.lstPrevias)
        {
            if(mat.getMatPreCod().equals(MatPreCod)) return mat;
        }
        
        return null;
    }
    
    public boolean MateriaPuedeDarExamen(Double calificacion){
        boolean puede = false;
        switch(this.MatTpoApr)
        {
            case EXONERABLE_CON_GANANCIA:
                if(calificacion < 86 && calificacion >= 70) puede =  true;
                break;
            case EXONERABLE_SIN_GANANCIA:
                if(calificacion < 86) puede =  true;
                break;
            case NO_EXONERABLE_CON_GANANCIA: 
                if(calificacion >= 70) puede =  true;
                break;
            case NO_EXONERABLE_SIN_GANANCIA:
                puede =  true;
                break;
        }
        
        return puede;
    }
    
    public boolean MateriaExonera(Double calificacion){
        boolean puede = false;
        switch(this.MatTpoApr)
        {
            case EXONERABLE_CON_GANANCIA:
                if(calificacion >= 86) puede =  true;
                break;
            case EXONERABLE_SIN_GANANCIA:
                if(calificacion >= 86) puede =  true;
                break;
        }
        
        return puede;
    }
    
    public String ObtenerPreviasCodigos(){
        List<Long> lstRetorno = new ArrayList<>();

        if(this.lstPrevias !=null)
        {
            for(MateriaPrevia matPrevia : this.lstPrevias)
            {
                lstRetorno.add(matPrevia.getMateriaPrevia().getMatCod());
            }
        }
        
        return Utilidades.GetInstancia().ObjetoToJson(lstRetorno);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.MatCod);
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
        final Materia other = (Materia) obj;
        if (!Objects.equals(this.MatCod, other.MatCod)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Materia{" + "MatCod=" + MatCod + '}';
    } 
}


