/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import Enumerado.TipoCampo;
import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alvar
 */
@Entity
@Table(name = "OBJETO_CAMPO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ObjetoCampo.findAll",       query = "SELECT t FROM ObjetoCampo t")})

public class ObjetoCampo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    //-ATRIBUTOS
    @EmbeddedId
    private final ObjetoCampoPK objCmpPK;
    
    @Column(name = "ObjCmpNom", length = 100)
    private String ObjCmpNom;
    
    @Column(name = "ObjCmpTpoDat")
    private TipoCampo ObjCmpTpoDat;
    
    @Column(name = "ObjCmpPK")
    private Boolean ObjCmpPK;
    
    //-CONSTRUCTOR
    public ObjetoCampo() {
        this.objCmpPK = new ObjetoCampoPK();
    }
    
    //-GETTERS Y SETTERS

    public String getObjCmpNom() {
        return ObjCmpNom;
    }

    public void setObjCmpNom(String ObjCmpNom) {
        this.ObjCmpNom = ObjCmpNom;
    }

    public TipoCampo getObjCmpTpoDat() {
        return ObjCmpTpoDat;
    }

    public void setObjCmpTpoDat(TipoCampo ObjCmpTpoDat) {
        this.ObjCmpTpoDat = ObjCmpTpoDat;
    }

    public Boolean getObjCmpPK() {
        return ObjCmpPK;
    }

    public void setObjCmpPK(Boolean ObjCmpPK) {
        this.ObjCmpPK = ObjCmpPK;
    }
    
    

    
    
    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (objCmpPK != null ? objCmpPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ObjetoCampo)) {
            return false;
        }
        ObjetoCampo other = (ObjetoCampo) object;
        if ((this.objCmpPK == null && other.objCmpPK != null) || (this.objCmpPK != null && !this.objCmpPK.equals(other.objCmpPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.ObjetoCampo[ id=" + objCmpPK + " ]";
    }
    


    @Embeddable
    public static class ObjetoCampoPK implements Serializable {
        @ManyToOne(targetEntity = Objeto.class, optional=false)
        @JoinColumn(name="ObjCod", referencedColumnName="ObjCod")
        private Objeto objeto;

        private Integer ObjCmpCod;

        public ObjetoCampoPK() {
        }

        public Objeto getObjeto() {
            return objeto;
        }

        public void setObjeto(Objeto objeto) {
            this.objeto = objeto;
        }

        public Integer getObjCmpCod() {
            return ObjCmpCod;
        }

        public void setObjCmpCod(Integer ObjCmpCod) {
            this.ObjCmpCod = ObjCmpCod;
        }

        @Override
        public String toString() {
            return "ObjetoCampoPK{" + "objeto=" + objeto + ", ObjCmpCod=" + ObjCmpCod + '}';
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 29 * hash + Objects.hashCode(this.objeto);
            hash = 29 * hash + Objects.hashCode(this.ObjCmpCod);
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
            final ObjetoCampoPK other = (ObjetoCampoPK) obj;
            if (!Objects.equals(this.objeto, other.objeto)) {
                return false;
            }
            if (!Objects.equals(this.ObjCmpCod, other.ObjCmpCod)) {
                return false;
            }
            return true;
        }




    }
}



