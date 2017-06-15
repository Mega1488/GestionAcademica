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
@Table(name = "TIPO_EVALUACION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoEvaluacion.findAll", query = "SELECT tpoEvl FROM TipoEvaluacion tpoEvl"),
    @NamedQuery(name = "TipoEvaluacion.findByTpoEvlCod", query = "SELECT tpoEvl FROM TipoEvaluacion tpoEvl WHERE tpoEvl.TpoEvlCod = :TpoEvlCod"),
    @NamedQuery(name = "TipoEvaluacion.findByTpoEvlNom", query = "SELECT tpoEvl FROM TipoEvaluacion tpoEvl WHERE tpoEvl.TpoEvlNom = :TpoEvlNom"),
    @NamedQuery(name = "TipoEvaluacion.findLastTpoEvl", query = "SELECT  tpoEvl FROM TipoEvaluacion tpoEvl ORDER BY tpoEvl.TpoEvlCod DESC")})

 public class TipoEvaluacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "TpoEvlCod", nullable = false)
    private Integer TpoEvlCod;
    @Column(name = "TpoEvlNom")
    private String TpoEvlNom;
    @Column(name = "TpoEvlExm")
    private Boolean TpoEvlExm;
    @Column(name = "TpoEvlInsAut")
    private Boolean TpoEvlInsAut;
    @Column(name = "ObjFchMod", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ObjFchMod;

    public TipoEvaluacion() {
        this.TpoEvlCod = 0;
        this.TpoEvlNom = "";
    }

    public TipoEvaluacion(Integer pTpoEvlCod) {
        this.TpoEvlCod = pTpoEvlCod;
    }

    public Integer getTpoEvlCod() {
        return TpoEvlCod;
    }

    public void setTpoEvlCod(Integer pTpoEvlCod) {
        this.TpoEvlCod = pTpoEvlCod;
    }

    public String getTpoEvlNom() {
        return TpoEvlNom;
    }

    public void setTpoEvlNom(String pTpoEvlNom) {
        this.TpoEvlNom = pTpoEvlNom;
    }

    public Boolean getTpoEvlExm() {
        return TpoEvlExm;
    }

    public void setTpoEvlExm(Boolean TpoEvlExm) {
        this.TpoEvlExm = TpoEvlExm;
    }

    public Boolean getTpoEvlInsAut() {
        return TpoEvlInsAut;
    }

    public void setTpoEvlInsAut(Boolean TpoEvlInsAut) {
        this.TpoEvlInsAut = TpoEvlInsAut;
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
        hash += (TpoEvlCod != null ? TpoEvlCod.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoEvaluacion)) {
            return false;
        }
        TipoEvaluacion other = (TipoEvaluacion) object;
        if ((this.TpoEvlCod == null && other.TpoEvlCod != null) || (this.TpoEvlCod != null && !this.TpoEvlCod.equals(other.TpoEvlCod))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Categoria[ TpoEvlCod=" + TpoEvlCod + " ]";
    }
    
}
