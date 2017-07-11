/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "SINC_REGISTRO_ELIMINADO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SincRegistroELiminado.findAll",       query = "SELECT t FROM SincRegistroELiminado t")})

public class SincRegistroELiminado implements Serializable {

    private static final long serialVersionUID = 1L;
    
    //-ATRIBUTOS
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native" )
    @Column(name = "SncObjElimCod", nullable = false)
    private Long SncObjElimCod;

    @ManyToOne(targetEntity = ObjetoCampo.class, optional=false)
    @JoinColumn(name="ObjCmpCod", referencedColumnName="ObjCmpCod")
    private ObjetoCampo objetoCampo;
    
    @Column(name = "SncObjElimFch", columnDefinition="DATE")
    @Temporal(TemporalType.DATE)
    private Date SncObjElimFch;
    
    @Column(name = "ObjCmpVal", length = 4000)
    private String ObjCmpVal;
    
    //-CONSTRUCTOR
    public SincRegistroELiminado() {
    }
        
    //-GETTERS Y SETTERS

    public Long getSncObjElimCod() {
        return SncObjElimCod;
    }

    public void setSncObjElimCod(Long SncObjElimCod) {
        this.SncObjElimCod = SncObjElimCod;
    }

    public ObjetoCampo getObjetoCampo() {
        return objetoCampo;
    }

    public void setObjetoCampo(ObjetoCampo objetoCampo) {
        this.objetoCampo = objetoCampo;
    }

    public Date getSncObjElimFch() {
        return SncObjElimFch;
    }

    public void setSncObjElimFch(Date SncObjElimFch) {
        this.SncObjElimFch = SncObjElimFch;
    }

    public String getObjCmpVal() {
        return ObjCmpVal;
    }

    public void setObjCmpVal(String ObjCmpVal) {
        this.ObjCmpVal = ObjCmpVal;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.SncObjElimCod);
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
        final SincRegistroELiminado other = (SincRegistroELiminado) obj;
        if (!Objects.equals(this.SncObjElimCod, other.SncObjElimCod)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SincRegistroELiminado{" + "SncObjElimCod=" + SncObjElimCod + ", objetoCampo=" + objetoCampo + ", SncObjElimFch=" + SncObjElimFch + ", ObjCmpVal=" + ObjCmpVal + '}';
    }

    
    
    
    
   

    
}


