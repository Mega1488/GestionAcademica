/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import Enumerado.ProtocoloEmail;
import Enumerado.TipoAutenticacion;
import Enumerado.TipoSSL;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "PARAMETRO_EMAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParametroEmail.findAll",       query = "SELECT t FROM ParametroEmail t"),
    @NamedQuery(name = "ParametroEmail.findLast",      query = "SELECT t FROM ParametroEmail t ORDER BY t.ParEmlCod DESC")})

public class ParametroEmail implements Serializable {

    private static final long serialVersionUID = 1L;
    
    //-ATRIBUTOS
    @Id
    @Basic(optional = false)
    @Column(name = "ParEmlCod", nullable = false)
    private Integer ParEmlCod;
    
    @Column(name = "ParEmlNom", length = 100)
    private String ParEmlNom;
    
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "ParEmlPro")
    private ProtocoloEmail ParEmlPro;
    
    @Column(name = "ParEmlSrv", length = 255)
    private String ParEmlSrv;
    
    @Column(name = "ParEmlPrt")
    private Integer ParEmlPrt;

    @Column(name = "ParEmlDeNom", length = 100)
    private String ParEmlDeNom;

    @Column(name = "ParEmlDeEml", length = 255)
    private String ParEmlDeEml;

    @Column(name = "ParEmlUtlAut")
    private Boolean ParEmlUtlAut;
    
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "ParEmlTpoAut")
    private TipoAutenticacion ParEmlTpoAut;  

    @Column(name = "ParEmlDom", length = 255)
    private String ParEmlDom;

    @Column(name = "ParEmlUsr", length = 255)
    private String ParEmlUsr;

    @Column(name = "ParEmlPsw", length = 500)
    private String ParEmlPsw;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "ParEmlSSL")
    private TipoSSL ParEmlSSL;  

    @Column(name = "ParEmlTmpEsp")
    private Integer ParEmlTmpEsp;  


    //-CONSTRUCTOR
    public ParametroEmail() {
        this.ParEmlTpoAut   = TipoAutenticacion.NORMAL;
        this.ParEmlSSL      = TipoSSL.SSL;
    }

    //-GETTERS Y SETTERS

    public Integer getParEmlCod() {
        return ParEmlCod;
    }

    public void setParEmlCod(Integer ParEmlCod) {
        this.ParEmlCod = ParEmlCod;
    }

    public String getParEmlNom() {
        return ParEmlNom;
    }

    public void setParEmlNom(String ParEmlNom) {
        this.ParEmlNom = ParEmlNom;
    }

    public ProtocoloEmail getParEmlPro() {
        return ParEmlPro;
    }

    public void setParEmlPro(ProtocoloEmail ParEmlPro) {
        this.ParEmlPro = ParEmlPro;
    }

    

    public String getParEmlSrv() {
        return ParEmlSrv;
    }

    public void setParEmlSrv(String ParEmlSrv) {
        this.ParEmlSrv = ParEmlSrv;
    }

    public Integer getParEmlPrt() {
        return ParEmlPrt;
    }

    public void setParEmlPrt(Integer ParEmlPrt) {
        this.ParEmlPrt = ParEmlPrt;
    }

    public String getParEmlDeNom() {
        return ParEmlDeNom;
    }

    public void setParEmlDeNom(String ParEmlDeNom) {
        this.ParEmlDeNom = ParEmlDeNom;
    }

    public String getParEmlDeEml() {
        return ParEmlDeEml;
    }

    public void setParEmlDeEml(String ParEmlDeEml) {
        this.ParEmlDeEml = ParEmlDeEml;
    }

    public Boolean getParEmlUtlAut() {
        return ParEmlUtlAut;
    }

    public void setParEmlUtlAut(Boolean ParEmlUtlAut) {
        this.ParEmlUtlAut = ParEmlUtlAut;
    }

    public TipoAutenticacion getParEmlTpoAut() {
        return ParEmlTpoAut;
    }

    public void setParEmlTpoAut(TipoAutenticacion ParEmlTpoAut) {
        this.ParEmlTpoAut = ParEmlTpoAut;
    }

    public String getParEmlDom() {
        return ParEmlDom;
    }

    public void setParEmlDom(String ParEmlDom) {
        this.ParEmlDom = ParEmlDom;
    }

    public String getParEmlUsr() {
        return ParEmlUsr;
    }

    public void setParEmlUsr(String ParEmlUsr) {
        this.ParEmlUsr = ParEmlUsr;
    }

    public String getParEmlPsw() {
        return ParEmlPsw;
    }

    public void setParEmlPsw(String ParEmlPsw) {
        this.ParEmlPsw = ParEmlPsw;
    }

    public TipoSSL getParEmlSSL() {
        return ParEmlSSL;
    }

    public void setParEmlSSL(TipoSSL ParEmlSSL) {
        this.ParEmlSSL = ParEmlSSL;
    }

    public Integer getParEmlTmpEsp() {
        return ParEmlTmpEsp;
    }

    public void setParEmlTmpEsp(Integer ParEmlTmpEsp) {
        this.ParEmlTmpEsp = ParEmlTmpEsp;
    }
    
    
    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ParEmlCod != null ? ParEmlCod.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParametroEmail)) {
            return false;
        }
        ParametroEmail other = (ParametroEmail) object;
        if ((this.ParEmlCod == null && other.ParEmlCod != null) || (this.ParEmlCod != null && !this.ParEmlCod.equals(other.ParEmlCod))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.ParametroEmail[ id=" + ParEmlCod + " ]";
    }
    
}
