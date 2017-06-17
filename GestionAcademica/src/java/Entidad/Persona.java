/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import Enumerado.Filial;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "PERSONA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Persona.findAll",           query = "SELECT p FROM Persona p"),
    @NamedQuery(name = "Persona.findByPerCod",      query = "SELECT p FROM Persona p WHERE p.PerCod = :PerCod"),
    @NamedQuery(name = "Persona.findByPerNom",      query = "SELECT p FROM Persona p WHERE p.PerNom = :PerNom"),
    @NamedQuery(name = "Persona.findLastPersona",   query = "SELECT p FROM Persona p ORDER BY p.PerCod DESC")})

public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "PerCod", nullable = false)
    private Integer PerCod;
    
    @Column(name = "PerNom", length = 100)
    private String PerNom;
    
    @Column(name = "PerApe", length = 100)
    private String PerApe;
    
    @Column(name = "PerUsrMod", length = 255)
    private String PerUsrMod;
    
    @Column(name = "PerEsDoc")
    private Boolean PerEsDoc;
    
    @Column(name = "PerEsAdm")
    private Boolean PerEsAdm;
    
    @Column(name = "PerEsAlu")
    private Boolean PerEsAlu;
    
    @Column(name = "PerNroLib")
    private Integer PerNroLib;
    
    @Column(name = "PerNroEstOrt")
    private Integer PerNroEstOrt;
    
    @Column(name = "PerFil")
    private Filial PerFil;
    
    @Column(name = "PerEml", length = 255)
    private String PerEml;
    
    @Column(name = "PerNotEml")
    private Boolean PerNotEml;
    
    @Column(name = "PerNotApp")
    private Boolean PerNotApp;
    
    @Column(name = "ObjFchMod", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ObjFchMod;


    public Persona() {
    }

    public Persona(Integer PerCod, String PerNom, String PerApe, String PerUsrMod, Boolean PerEsDoc, Boolean PerEsAdm, Boolean PerEsAlu, Integer PerNroLib, Integer PerNroEstOrt, Filial PerFil, String PerEml, Boolean PerNotEml, Boolean PerNotApp, Date ObjFchMod) {
        this.PerCod = PerCod;
        this.PerNom = PerNom;
        this.PerApe = PerApe;
        this.PerUsrMod = PerUsrMod;
        this.PerEsDoc = PerEsDoc;
        this.PerEsAdm = PerEsAdm;
        this.PerEsAlu = PerEsAlu;
        this.PerNroLib = PerNroLib;
        this.PerNroEstOrt = PerNroEstOrt;
        this.PerFil = PerFil;
        this.PerEml = PerEml;
        this.PerNotEml = PerNotEml;
        this.PerNotApp = PerNotApp;
        this.ObjFchMod = ObjFchMod;
    }
 
    
    
    
    

    public Integer getPerCod() {
        return PerCod;
    }

    public void setPerCod(Integer PerCod) {
        this.PerCod = PerCod;
    }

    public String getPerNom() {
        return PerNom;
    }

    public void setPerNom(String PerNom) {
        this.PerNom = PerNom;
    }

    public String getPerApe() {
        return PerApe;
    }

    public void setPerApe(String PerApe) {
        this.PerApe = PerApe;
    }

    public String getPerUsrMod() {
        return PerUsrMod;
    }

    public void setPerUsrMod(String PerUsrMod) {
        this.PerUsrMod = PerUsrMod;
    }

    public Boolean getPerEsDoc() {
        return PerEsDoc;
    }

    public void setPerEsDoc(Boolean PerEsDoc) {
        this.PerEsDoc = PerEsDoc;
    }

    public Boolean getPerEsAdm() {
        return PerEsAdm;
    }

    public void setPerEsAdm(Boolean PerEsAdm) {
        this.PerEsAdm = PerEsAdm;
    }

    public Boolean getPerEsAlu() {
        return PerEsAlu;
    }

    public void setPerEsAlu(Boolean PerEsAlu) {
        this.PerEsAlu = PerEsAlu;
    }

    public Integer getPerNroLib() {
        return PerNroLib;
    }

    public void setPerNroLib(Integer PerNroLib) {
        this.PerNroLib = PerNroLib;
    }

    public Integer getPerNroEstOrt() {
        return PerNroEstOrt;
    }

    public void setPerNroEstOrt(Integer PerNroEstOrt) {
        this.PerNroEstOrt = PerNroEstOrt;
    }

    public Filial getPerFil() {
        return PerFil;
    }

    public void setPerFil(Filial PerFil) {
        this.PerFil = PerFil;
    }

    public String getPerEml() {
        return PerEml;
    }

    public void setPerEml(String PerEml) {
        this.PerEml = PerEml;
    }

    public Boolean getPerNotEml() {
        return PerNotEml;
    }

    public void setPerNotEml(Boolean PerNotEml) {
        this.PerNotEml = PerNotEml;
    }

    public Boolean getPerNotApp() {
        return PerNotApp;
    }

    public void setPerNotApp(Boolean PerNotApp) {
        this.PerNotApp = PerNotApp;
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
        hash += (PerCod != null ? PerCod.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Persona)) {
            return false;
        }
        Persona other = (Persona) object;
        if ((this.PerCod == null && other.PerCod != null) || (this.PerCod != null && !this.PerCod.equals(other.PerCod))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.Persona[ id=" + PerCod + " ]";
    }
    
}
