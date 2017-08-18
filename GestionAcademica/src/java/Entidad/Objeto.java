/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import Enumerado.TipoCampo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author alvar
 */
@Entity
@Table(name = "OBJETO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Objeto.findAll",       query = "SELECT t FROM Objeto t"),
    @NamedQuery(name = "Objeto.findByObjeto",  query = "SELECT t FROM Objeto t WHERE t.ObjNom =:ObjNom")})

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
    
    @Column(name = "ObjNmdQry", length = 100)
    private String ObjNmdQry;
    
    @Column(name = "ObjClsNom", length = 500)
    private String ObjClsNom;
    
    @Column(name = "ObjFchMod", columnDefinition="DATE")
    @Temporal(TemporalType.DATE)
    private Date ObjFchMod;
    
    @OneToMany(targetEntity = ObjetoCampo.class, cascade= CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name="ObjCod", referencedColumnName="ObjCod")
    @Fetch(FetchMode.SUBSELECT)
    private List<ObjetoCampo> lstCampo;
    
    //-CONSTRUCTOR

    public Objeto() {
        this.lstCampo = new ArrayList<>();
    }

    public Objeto(String ObjNom, List<ObjetoCampo> lstCampo) {
        this.ObjNom = ObjNom;
        this.lstCampo = lstCampo;
    }

    public Objeto(String ObjNom, String ObjNmdQry, String PrimaryKey, String ObjClsNom) {
        this.ObjNom = ObjNom;
        this.ObjNmdQry = ObjNmdQry;
        this.ObjClsNom = ObjClsNom;
        this.lstCampo = new ArrayList<>();
        
        this.lstCampo.add(new ObjetoCampo(this, PrimaryKey, TipoCampo.LONG, Boolean.TRUE));
        
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

    public List<ObjetoCampo> getLstCampo() {
        return lstCampo;
    }

    public void setLstCampo(List<ObjetoCampo> lstCampo) {
        this.lstCampo = lstCampo;
    }

    public String getObjNmdQry() {
        return ObjNmdQry;
    }

    public void setObjNmdQry(String ObjNmdQry) {
        this.ObjNmdQry = ObjNmdQry;
    }

    public ObjetoCampo getPrimaryKey(){
        for(ObjetoCampo objCmp : this.getLstCampo())
        {
            if(objCmp.getObjCmpPK())
            {
                return objCmp;
            }
        }
        
        return null;
    }

    public String getObjClsNom() {
        return ObjClsNom;
    }

    public void setObjClsNom(String ObjClsNom) {
        this.ObjClsNom = ObjClsNom;
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
