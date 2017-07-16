/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import Enumerado.TipoMenu;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "MENU")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Menu.findAll", query = "SELECT m FROM Menu m")})
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "MenCod", nullable = false)
    private Integer MenCod;
    
    @Column(name = "MenTpo")
    private TipoMenu MenTpo;
    
    @Column(name = "MenUrl", length = 500)
    private String MenUrl;
    
    @Column(name = "MenNom", length = 100)
    private String MenNom;
    
    @Column(name = "MenOrd")
    private Integer MenOrd;

    public Integer getMenCod() {
        return MenCod;
    }

    public void setMenCod(Integer menCod) {
        this.MenCod = menCod;
    }

    public TipoMenu getMenTpo() {
        return MenTpo;
    }

    public void setMenTpo(TipoMenu MenTpo) {
        this.MenTpo = MenTpo;
    }

    public String getMenUrl() {
        return MenUrl;
    }

    public void setMenUrl(String MenUrl) {
        this.MenUrl = MenUrl;
    }

    public String getMenNom() {
        return MenNom;
    }

    public void setMenNom(String MenNom) {
        this.MenNom = MenNom;
    }

    public Integer getMenOrd() {
        return MenOrd;
    }

    public void setMenOrd(Integer MenOrd) {
        this.MenOrd = MenOrd;
    }

    public Menu() {
    }

    public Menu(Integer MenCod, TipoMenu MenTpo, String MenUrl, String MenNom, Integer MenOrd) {
        this.MenCod = MenCod;
        this.MenTpo = MenTpo;
        this.MenUrl = MenUrl;
        this.MenNom = MenNom;
        this.MenOrd = MenOrd;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (MenCod != null ? MenCod.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Menu)) {
            return false;
        }
        Menu other = (Menu) object;
        if ((this.MenCod == null && other.MenCod != null) || (this.MenCod != null && !this.MenCod.equals(other.MenCod))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.Menu[ MenCod=" + MenCod + " ]";
    }
    
}
