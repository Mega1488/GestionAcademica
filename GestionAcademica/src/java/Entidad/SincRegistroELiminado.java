/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
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
@Table(name = "SINC_REGISTRO_ELIMINADO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SincRegistroELiminado.findAll",       query = "SELECT t FROM SincRegistroELiminado t")})

public class SincRegistroELiminado implements Serializable {

    private static final long serialVersionUID = 1L;
    
    //-ATRIBUTOS
    @EmbeddedId
    private final ObjEliminadoPK objEliminadoPK;
    
    @Column(name = "SncObjElimFch", columnDefinition="DATE")
    @Temporal(TemporalType.DATE)
    private Date SncObjElimFch;
    
    @Column(name = "ObjCmpVal", length = 4000)
    private String ObjCmpVal;
    
    //-CONSTRUCTOR
    public SincRegistroELiminado() {
        this.objEliminadoPK = new ObjEliminadoPK();
    }
        
    //-GETTERS Y SETTERS

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
        int hash = 0;
        hash += (objEliminadoPK != null ? objEliminadoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SincRegistroELiminado)) {
            return false;
        }
        SincRegistroELiminado other = (SincRegistroELiminado) object;
        if ((this.objEliminadoPK == null && other.objEliminadoPK != null) || (this.objEliminadoPK != null && !this.objEliminadoPK.equals(other.objEliminadoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.SincRegistroELiminado[ id=" + objEliminadoPK + " ]";
    }
    
    
    @Embeddable
    public static class ObjEliminadoPK implements Serializable {
        private Integer SncObjElimCod;

        @ManyToOne(targetEntity = ObjetoCampo.class, optional=false)
        @JoinColumns({
           @JoinColumn(name="ObjCod", referencedColumnName="ObjCod"),
           @JoinColumn(name="ObjCmpCod", referencedColumnName="ObjCmpCod")
        }) 
        private ObjetoCampo objetoCampo;

        public ObjEliminadoPK() {
        }

        public Integer getSncObjElimCod() {
            return SncObjElimCod;
        }

        public void setSncObjElimCod(Integer SncObjElimCod) {
            this.SncObjElimCod = SncObjElimCod;
        }

        public ObjetoCampo getObjetoCampo() {
            return objetoCampo;
        }

        public void setObjetoCampo(ObjetoCampo objetoCampo) {
            this.objetoCampo = objetoCampo;
        }

        @Override
        public String toString() {
            return "ObjEliminadoPK{" + "SncObjElimCod=" + SncObjElimCod + ", objetoCampo=" + objetoCampo + '}';
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 53 * hash + Objects.hashCode(this.SncObjElimCod);
            hash = 53 * hash + Objects.hashCode(this.objetoCampo);
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
            final ObjEliminadoPK other = (ObjEliminadoPK) obj;
            if (!Objects.equals(this.SncObjElimCod, other.SncObjElimCod)) {
                return false;
            }
            if (!Objects.equals(this.objetoCampo, other.objetoCampo)) {
                return false;
            }
            return true;
        }



    }

    
}


