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
@Table(name = "CURSO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Curso.findAll", query = "SELECT c FROM Curso c"),
    @NamedQuery(name = "Curso.findByCurCod", query = "SELECT c FROM Curso c WHERE c.CurCod = :CurCod"),
    @NamedQuery(name = "Curso.findByCurNom", query = "SELECT c FROM Curso c WHERE c.CurNom = :CurNom"),
    @NamedQuery(name = "Curso.findLastCurso", query = "SELECT  c FROM Curso c ORDER BY c.CurCod DESC")})

public class Curso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CurCod", nullable = false)
    private Integer CurCod;
    
    @Column(name = "CurNom", length = 100)
    private String CurNom;
    
    @Column(name = "CurDsc", length = 500)
    private String CurDsc;
    
    @Column(name = "CurFac", length = 255)
    private String CurFac;
    
    @Column(name = "CurCrt", length = 255)
    private String CurCrt;
    
    @Column(name = "CurCatCod")
    private Integer CurCatCod;
    
    @Column(name = "ObjFchMod", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ObjFchMod;

    public Integer getCurCod() {
        return CurCod;
    }

    public void setCurCod(Integer CurCod) {
        this.CurCod = CurCod;
    }

    public String getCurNom() {
        return CurNom;
    }

    public void setCurNom(String CurNom) {
        this.CurNom = CurNom;
    }

    public String getCurDsc() {
        return CurDsc;
    }

    public void setCurDsc(String CurDsc) {
        this.CurDsc = CurDsc;
    }

    public String getCurFac() {
        return CurFac;
    }

    public void setCurFac(String CurFac) {
        this.CurFac = CurFac;
    }

    public String getCurCrt() {
        return CurCrt;
    }

    public void setCurCrt(String CurCrt) {
        this.CurCrt = CurCrt;
    }

    public Integer getCurCatCod() {
        return CurCatCod;
    }

    public void setCurCatCod(Integer CurCatCod) {
        this.CurCatCod = CurCatCod;
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
        hash += (CurCod != null ? CurCod.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Curso)) {
            return false;
        }
        Curso other = (Curso) object;
        if ((this.CurCod == null && other.CurCod != null) || (this.CurCod != null && !this.CurCod.equals(other.CurCod))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.Curso[ id=" + CurCod + " ]";
    }
    
}
