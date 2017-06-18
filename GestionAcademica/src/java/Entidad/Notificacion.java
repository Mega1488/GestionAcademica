/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import Enumerado.ObtenerDestinatario;
import Enumerado.TipoNotificacion;
import Enumerado.TipoEnvio;
import Enumerado.TipoRepeticion;
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
@Table(name = "NOTIFICACION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Notificacion.findAll",       query = "SELECT t FROM Notificacion t")})

public class Notificacion implements Serializable {

    private static final long serialVersionUID = 1L;
    
    //-ATRIBUTOS
    @Id
    @Basic(optional = false)
    @Column(name = "NotCod", nullable = false)
    private Integer NotCod;
    
    @Column(name = "NotNom", length = 100)
    private String NotNom;
    @Column(name = "NotDsc", length = 500)
    private String NotDsc;
    @Column(name = "NotCon", length = 4000)
    private String NotCon;
    @Column(name = "NotAsu", length = 100)
    private String NotAsu;
    
    @Column(name = "NotTpo")
    private TipoNotificacion NotTpo;
    @Column(name = "NotTpoEnv")
    private TipoEnvio NotTpoEnv;
    @Column(name = "NotObtDest")
    private ObtenerDestinatario NotObtDest;
    @Column(name = "NotRepTpo")
    private TipoRepeticion NotRepTpo;

    @Column(name = "NotRepVal")
    private Integer NotRepVal;

    @Column(name = "NotRepHst", columnDefinition="DATE")
    @Temporal(TemporalType.DATE)
    private Date NotRepHst;

    @Column(name = "NotEmail")
    private Boolean NotEmail;
    @Column(name = "NotApp")
    private Boolean NotApp;
    @Column(name = "NotWeb")
    private Boolean NotWeb;
    @Column(name = "NotAct")
    private Boolean NotAct;
    
        
    //-CONSTRUCTOR
    public Notificacion() {
    }
    
    //-GETTERS Y SETTERS

    public Integer getNotCod() {
        return NotCod;
    }

    public void setNotCod(Integer NotCod) {
        this.NotCod = NotCod;
    }

    public String getNotNom() {
        return NotNom;
    }

    public void setNotNom(String NotNom) {
        this.NotNom = NotNom;
    }

    public String getNotDsc() {
        return NotDsc;
    }

    public void setNotDsc(String NotDsc) {
        this.NotDsc = NotDsc;
    }

    public String getNotCon() {
        return NotCon;
    }

    public void setNotCon(String NotCon) {
        this.NotCon = NotCon;
    }

    public String getNotAsu() {
        return NotAsu;
    }

    public void setNotAsu(String NotAsu) {
        this.NotAsu = NotAsu;
    }

    public TipoNotificacion getNotTpo() {
        return NotTpo;
    }

    public void setNotTpo(TipoNotificacion NotTpo) {
        this.NotTpo = NotTpo;
    }

    public TipoEnvio getNotTpoEnv() {
        return NotTpoEnv;
    }

    public void setNotTpoEnv(TipoEnvio NotTpoEnv) {
        this.NotTpoEnv = NotTpoEnv;
    }

    public ObtenerDestinatario getNotObtDest() {
        return NotObtDest;
    }

    public void setNotObtDest(ObtenerDestinatario NotObtDest) {
        this.NotObtDest = NotObtDest;
    }

    public TipoRepeticion getNotRepTpo() {
        return NotRepTpo;
    }

    public void setNotRepTpo(TipoRepeticion NotRepTpo) {
        this.NotRepTpo = NotRepTpo;
    }

    public Integer getNotRepVal() {
        return NotRepVal;
    }

    public void setNotRepVal(Integer NotRepVal) {
        this.NotRepVal = NotRepVal;
    }

    public Date getNotRepHst() {
        return NotRepHst;
    }

    public void setNotRepHst(Date NotRepHst) {
        this.NotRepHst = NotRepHst;
    }

    public Boolean getNotEmail() {
        return NotEmail;
    }

    public void setNotEmail(Boolean NotEmail) {
        this.NotEmail = NotEmail;
    }

    public Boolean getNotApp() {
        return NotApp;
    }

    public void setNotApp(Boolean NotApp) {
        this.NotApp = NotApp;
    }

    public Boolean getNotWeb() {
        return NotWeb;
    }

    public void setNotWeb(Boolean NotWeb) {
        this.NotWeb = NotWeb;
    }

    public Boolean getNotAct() {
        return NotAct;
    }

    public void setNotAct(Boolean NotAct) {
        this.NotAct = NotAct;
    }

    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (NotCod != null ? NotCod.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notificacion)) {
            return false;
        }
        Notificacion other = (Notificacion) object;
        if ((this.NotCod == null && other.NotCod != null) || (this.NotCod != null && !this.NotCod.equals(other.NotCod))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.Notificacion[ id=" + NotCod + " ]";
    }
    
}
