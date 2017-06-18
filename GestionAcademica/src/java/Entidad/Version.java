/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alvar
 */
@Entity
@Table(name = "VERSION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Version.findAll",       query = "SELECT t FROM Version t")})

public class Version implements Serializable {

    private static final long serialVersionUID = 1L;
   
    //-ATRIBUTOS
    @Id
    @Basic(optional = false)
    @Column(name = "SisVerCod", nullable = false)
    private Integer SisVerCod;
    
    @Column(name = "SisVer", length = 50)
    private String SisVer;
    
    @Column(name = "SisCrgDat")
    private Boolean SisCrgDat;
    
    //-CONSTRUCTOR
    public Version() {
    }
    
    
    //-GETTERS Y SETTERS

    public Integer getSisVerCod() {
        return SisVerCod;
    }

    public void setSisVerCod(Integer SisVerCod) {
        this.SisVerCod = SisVerCod;
    }

    public String getSisVer() {
        return SisVer;
    }

    public void setSisVer(String SisVer) {
        this.SisVer = SisVer;
    }

    public Boolean getSisCrgDat() {
        return SisCrgDat;
    }

    public void setSisCrgDat(Boolean SisCrgDat) {
        this.SisCrgDat = SisCrgDat;
    }

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (SisVerCod != null ? SisVerCod.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Version)) {
            return false;
        }
        Version other = (Version) object;
        if ((this.SisVerCod == null && other.SisVerCod != null) || (this.SisVerCod != null && !this.SisVerCod.equals(other.SisVerCod))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.Version[ id=" + SisVerCod + " ]";
    }
    
}
