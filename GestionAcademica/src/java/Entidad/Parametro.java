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
import javax.persistence.JoinColumn;
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
@Table(name = "PARAMETRO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Parametro.findAll",       query = "SELECT t FROM Parametro t")})

public class Parametro implements Serializable {

    private static final long serialVersionUID = 1L;

    //-ATRIBUTOS
    @Id
    @Basic(optional = false)
    @Column(name = "ParCod", nullable = false)
    private Integer ParCod;
    
    @ManyToOne(targetEntity = ParametroEmail.class, optional=true)
    @JoinColumn(name="ParEmlCod", referencedColumnName="ParEmlCod")
    private ParametroEmail parametroEmail;
    
    @Column(name = "ParFchUltSinc", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ParFchUltSinc;
    
    @Column(name = "ParSisLocal")
    private Boolean ParSisLocal;
    
    @Column(name = "ParUrlMdl", length = 500)
    private Integer ParUrlMdl;
    
    @Column(name = "ParUrlSrvSnc", length = 500)
    private Integer ParUrlSrvSnc;
    
    @Column(name = "ParDiaEvlPrv")
    private Integer ParDiaEvlPrv;
    
    @Column(name = "ParTieIna")
    private Integer ParTieIna;
    
    @Column(name = "ParSncAct")
    private Boolean ParSncAct;
    
    
    //-CONSTRUCTOR
    public Parametro() {
    }
    
    
    //-GETTERS Y SETTERS

    public Integer getParCod() {
        return ParCod;
    }

    public void setParCod(Integer ParCod) {
        this.ParCod = ParCod;
    }

    public ParametroEmail getParametroEmail() {
        return parametroEmail;
    }

    public void setParametroEmail(ParametroEmail parametroEmail) {
        this.parametroEmail = parametroEmail;
    }

    public Date getParFchUltSinc() {
        return ParFchUltSinc;
    }

    public void setParFchUltSinc(Date ParFchUltSinc) {
        this.ParFchUltSinc = ParFchUltSinc;
    }

    public Boolean getParSisLocal() {
        return ParSisLocal;
    }

    public void setParSisLocal(Boolean ParSisLocal) {
        this.ParSisLocal = ParSisLocal;
    }

    public Integer getParUrlSrvSnc() {
        return ParUrlSrvSnc;
    }

    public void setParUrlSrvSnc(Integer ParUrlSrvSnc) {
        this.ParUrlSrvSnc = ParUrlSrvSnc;
    }

    public Integer getParDiaEvlPrv() {
        return ParDiaEvlPrv;
    }

    public void setParDiaEvlPrv(Integer ParDiaEvlPrv) {
        this.ParDiaEvlPrv = ParDiaEvlPrv;
    }

    public Integer getParTieIna() {
        return ParTieIna;
    }

    public void setParTieIna(Integer ParTieIna) {
        this.ParTieIna = ParTieIna;
    }

    public Boolean getParSncAct() {
        return ParSncAct;
    }

    public void setParSncAct(Boolean ParSncAct) {
        this.ParSncAct = ParSncAct;
    }

    public Integer getParUrlMdl() {
        return ParUrlMdl;
    }

    public void setParUrlMdl(Integer ParUrlMdl) {
        this.ParUrlMdl = ParUrlMdl;
    }

    
    
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ParCod != null ? ParCod.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parametro)) {
            return false;
        }
        Parametro other = (Parametro) object;
        if ((this.ParCod == null && other.ParCod != null) || (this.ParCod != null && !this.ParCod.equals(other.ParCod))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.Parametro[ id=" + ParCod + " ]";
    }
    
}
