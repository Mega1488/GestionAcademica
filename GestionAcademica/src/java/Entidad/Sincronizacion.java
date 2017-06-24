/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import Enumerado.EstadoSincronizacion;
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
@Table(name = "SINCRONIZACION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sincronizacion.findAll",       query = "SELECT t FROM Sincronizacion t")})

public class Sincronizacion implements Serializable {

    private static final long serialVersionUID = 1L;
    
    //-ATRIBUTOS
    @Id
    @Basic(optional = false)
    @Column(name = "SncCod", nullable = false)
    private Integer SncCod;
    
    @Column(name = "SncFch", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date SncFch;
    
    @Column(name = "SncEst")
    private EstadoSincronizacion SncEst;

    @Column(name = "SncObjCnt")
    private Integer SncObjCnt;

    @Column(name = "SncObjDet", length = 500)
    private String SncObjDet;

    @Column(name = "SncDur", length = 100)
    private String SncDur;
    

    //-CONSTRUCTOR
    public Sincronizacion() {
    }
    
    
    //-GETTERS Y SETTERS

    public Integer getSncCod() {
        return SncCod;
    }

    public void setSncCod(Integer SncCod) {
        this.SncCod = SncCod;
    }

    public Date getSncFch() {
        return SncFch;
    }

    public void setSncFch(Date SncFch) {
        this.SncFch = SncFch;
    }

    public EstadoSincronizacion getSncEst() {
        return SncEst;
    }

    public void setSncEst(EstadoSincronizacion SncEst) {
        this.SncEst = SncEst;
    }

    public Integer getSncObjCnt() {
        return SncObjCnt;
    }

    public void setSncObjCnt(Integer SncObjCnt) {
        this.SncObjCnt = SncObjCnt;
    }

    public String getSncObjDet() {
        return SncObjDet;
    }

    public void setSncObjDet(String SncObjDet) {
        this.SncObjDet = SncObjDet;
    }

    public String getSncDur() {
        return SncDur;
    }

    public void setSncDur(String SncDur) {
        this.SncDur = SncDur;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (SncCod != null ? SncCod.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sincronizacion)) {
            return false;
        }
        Sincronizacion other = (Sincronizacion) object;
        if ((this.SncCod == null && other.SncCod != null) || (this.SncCod != null && !this.SncCod.equals(other.SncCod))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.Sincronizacion[ id=" + SncCod + " ]";
    }
    
}