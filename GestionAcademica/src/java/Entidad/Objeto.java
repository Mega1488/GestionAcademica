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
import org.hibernate.annotations.GenericGenerator;

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
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native" )
    @Column(name = "ObjCod", nullable = false)
    private Long ObjCod;
    
    @Column(name = "ObjNom", length = 100)
    private String ObjNom;
    
    @Column(name = "ObjFchMod", columnDefinition="DATE")
    @Temporal(TemporalType.DATE)
    private Date ObjFchMod;
    
    //-CONSTRUCTOR

    public Objeto() {
    }
    
    
    

    //-GETTERS Y SETTERS

    public Long getObjCod() {
        return ObjCod;
    }

    public void setObjCod(Long ObjCod) {
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
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.ObjCod);
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
        final Objeto other = (Objeto) obj;
        if (!Objects.equals(this.ObjCod, other.ObjCod)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Objeto{" + "ObjCod=" + ObjCod + ", ObjNom=" + ObjNom + ", ObjFchMod=" + ObjFchMod + '}';
    }
    
    
    
}
