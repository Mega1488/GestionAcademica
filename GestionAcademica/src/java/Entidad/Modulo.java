/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import Enumerado.TipoPeriodo;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
    
    @EmbeddedId
    private final ModuloPK modPK;
    
    
    @Column(name = "ModNom", length = 100)
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
   
    /*
    @OneToMany(targetEntity = Evaluacion.class, cascade= CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name="ModEvlCurCod", referencedColumnName="CurCod"),
            @JoinColumn(name="ModEvlModCod", referencedColumnName="ModCod"),
        })
    private List<Evaluacion> lstEvaluacion;
*/
    
    public Modulo() {
        this.modPK = new ModuloPK();
    }
    
    
    
    public Integer getModCod() {
        return modPK.getModCod();
    }

    public void setModCod(Integer ModCod) {
        modPK.setModCod(ModCod);
    }

    public Long getModEstCod() {
        return ModEstCod;
    }

    public void setModEstCod(Long ModEstCod) {
        this.ModEstCod = ModEstCod;
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
    
    
    @Embeddable
    public static class ModuloPK implements Serializable {
        private Integer ModCod;

        
        @ManyToOne(targetEntity = Curso.class, optional=false)
        @JoinColumn(name="CurCod", referencedColumnName = "CurCod")
        private Curso curso;

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

        public ModuloPK(Integer ModCod, Curso curso) {
            this.ModCod = ModCod;
            this.curso = curso;
        }

        public ModuloPK() {
        }

        @Override
        public String toString() {
            return "ModuloPK{" + "ModCod=" + ModCod + ", curso=" + curso + '}';
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 79 * hash + Objects.hashCode(this.ModCod);
            hash = 79 * hash + Objects.hashCode(this.curso);
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
            final ModuloPK other = (ModuloPK) obj;
            if (!Objects.equals(this.ModCod, other.ModCod)) {
                return false;
            }
            if (!Objects.equals(this.curso, other.curso)) {
                return false;
            }
            return true;
        }

        



    }
    
}

