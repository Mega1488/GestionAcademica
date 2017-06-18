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
@Table(name = "OBJETO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Objeto.findAll",       query = "SELECT t FROM Objeto t")})

public class Objeto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    //-ATRIBUTOS
    @Id
    @Basic(optional = false)
    @Column(name = "ObjCod", nullable = false)
    private Integer ObjCod;
    
    @Column(name = "ObjNom", length = 100)
    private String ObjNom;
    
    @Column(name = "ObjFchMod", columnDefinition="DATE")
    @Temporal(TemporalType.DATE)
    private Date ObjFchMod;
    
    //-CONSTRUCTOR

    public Objeto() {
    }
    
    
    

    //-GETTERS Y SETTERS
    public Integer getObjCod() {
        return ObjCod;
    }

    public void setObjCod(Integer ObjCod) {
        this.ObjCod = ObjCod;
    }

    public String getObjNom() {
        return ObjNom;
    }

    public void setObjNom(String ObjNom) {
        this.ObjNom = ObjNom;
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
        hash += (ObjCod != null ? ObjCod.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Objeto)) {
            return false;
        }
        Objeto other = (Objeto) object;
        if ((this.ObjCod == null && other.ObjCod != null) || (this.ObjCod != null && !this.ObjCod.equals(other.ObjCod))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.Objeto[ id=" + ObjCod + " ]";
    }
    
}
