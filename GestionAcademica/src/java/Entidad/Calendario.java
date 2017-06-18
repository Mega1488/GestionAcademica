/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "CALENDARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Calendario.findAll",       query = "SELECT t FROM Calendario t")})

public class Calendario implements Serializable {

    private static final long serialVersionUID = 1L;
    
    //-ATRIBUTOS
    @EmbeddedId
    private final CalendarioPK calPK;
    
    
    @Column(name = "CalFch", columnDefinition="DATE")
    @Temporal(TemporalType.DATE)
    private Date CalFch;
    
    @Column(name = "EvlInsFchDsd", columnDefinition="DATE")
    @Temporal(TemporalType.DATE)
    private Date EvlInsFchDsd;
    
    @Column(name = "EvlInsFchHst", columnDefinition="DATE")
    @Temporal(TemporalType.DATE)
    private Date EvlInsFchHst;
    
    @Column(name = "ObjFchMod", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ObjFchMod;

    //-CONSTRUCTOR
    public Calendario() {
        this.calPK = new CalendarioPK();
    } 

    //-GETTERS Y SETTERS

    public Date getCalFch() {
        return CalFch;
    }

    public void setCalFch(Date CalFch) {
        this.CalFch = CalFch;
    }

    public Date getEvlInsFchDsd() {
        return EvlInsFchDsd;
    }

    public void setEvlInsFchDsd(Date EvlInsFchDsd) {
        this.EvlInsFchDsd = EvlInsFchDsd;
    }

    public Date getEvlInsFchHst() {
        return EvlInsFchHst;
    }

    public void setEvlInsFchHst(Date EvlInsFchHst) {
        this.EvlInsFchHst = EvlInsFchHst;
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
        hash += (calPK != null ? calPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Calendario)) {
            return false;
        }
        Calendario other = (Calendario) object;
        if ((this.calPK == null && other.calPK != null) || (this.calPK != null && !this.calPK.equals(other.calPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.Calendario[ id=" + calPK.toString() + " ]";
    }
    
}

@Embeddable
class CalendarioPK implements Serializable {
    @ManyToOne(targetEntity = Evaluacion.class, optional=false)
    @JoinColumn(name="EvlCod", referencedColumnName = "EvlCod")
    private Evaluacion Evl;
    
    private Integer CalCod;

    public CalendarioPK() {
    }

    public Evaluacion getEvl() {
        return Evl;
    }

    public void setEvl(Evaluacion Evl) {
        this.Evl = Evl;
    }

    public Integer getCalCod() {
        return CalCod;
    }

    public void setCalCod(Integer CalCod) {
        this.CalCod = CalCod;
    }

    @Override
    public String toString() {
        return "CalendarioPK{" + "Evl=" + Evl + ", CalCod=" + CalCod + '}';
    }
    
    
}
