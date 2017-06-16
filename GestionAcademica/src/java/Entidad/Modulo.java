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
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alvar
 */



@Entity
@Table(name = "MODULO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Modulo.findAll", query = "SELECT m FROM Modulo m"),
    @NamedQuery(name = "Modulo.findByPK", query = "SELECT m FROM Modulo m WHERE m.modPK.curso.CurCod = :CurCod and m.modPK.ModCod = :ModCod"),
    @NamedQuery(name = "Modulo.findByCurso", query = "SELECT m FROM Modulo m WHERE m.modPK.curso.CurCod = :CurCod"),
    @NamedQuery(name = "Modulo.findLast", query = "SELECT  m FROM Modulo m WHERE m.modPK.curso.CurCod = :CurCod ORDER BY m.modPK.ModCod desc")})

public class Modulo implements Serializable {

    private static final long serialVersionUID = 1L;
   // @Id
   // @Basic(optional = false)
   // @Column(name = "ModCod")
   // private Integer ModCod;
    
    @EmbeddedId
    private final ModuloPK modPK;
    
    
    @Column(name = "ModNom", length = 100)
    private String ModNom;
    
    @Column(name = "ModDsc", length = 500)
    private String ModDsc;
    
    @Column(name = "ModTpoPer")
    private TipoPeriodo ModTpoPer;
    
    @Column(name = "ModPerVal")
    private Double ModPerVal;
    
    @Column(name = "ModCntHor")
    private Double ModCntHor;
    
    @Column(name = "ObjFchMod", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ObjFchMod;

    
    public Modulo() {
        this.modPK = new ModuloPK();
        this.ModNom = "";
        this.ModDsc = "";
        this.ModTpoPer = TipoPeriodo.MODULAR;
        this.ModPerVal = Double.MIN_NORMAL;
        this.ModCntHor = Double.MIN_NORMAL;
        
       // this.modPK = new ModuloPK();
    }
    
    
  /*  
    @ManyToOne
    @JoinColumn(name="CurCod")
    private Curso curso;
*/
/*    @MapsId("CurCod")
    @JoinColumns({
      @JoinColumn(name="CurCod_fk", referencedColumnName="CurCod")
    })
    @OneToMany Curso curso;
    
  */  

    
//    private Curso curso;

    public Integer getModCod() {
        return modPK.getModCod();
    }

    public void setModCod(Integer ModCod) {
        modPK.setModCod(ModCod);
    }

    
    
    
    public Curso getCurso() {
        return modPK.getCurso();
    }

    public void setCurso(Curso curso) {
        modPK.setCurso(curso);
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
        int hash = 0;
        hash += (modPK.getModCod() != null ? modPK.getModCod().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Modulo)) {
            return false;
        }
        Modulo other = (Modulo) object;
        if ((modPK.getModCod() == null && other.modPK.getModCod() != null) || (modPK.getModCod() != null && !modPK.getModCod().equals(other.modPK.getModCod()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.Modulo[ id=" + modPK.getModCod().toString() + " ]";
    }
    
    
    
}




    @Embeddable
     class ModuloPK implements Serializable {
        private Integer ModCod;
        //private Integer CurCod;

        

        @ManyToOne(targetEntity = Curso.class, optional=false)
        @JoinColumn(name="CurCod", referencedColumnName = "CurCod")
        private Curso curso;
    
        
        
        
    // getters and setters

    public Integer getModCod() {
        return ModCod;
    }

    public void setModCod(Integer ModCod) {
        this.ModCod = ModCod;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
    }