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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "PERSONA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Persona.findAll",           query = "SELECT p FROM Persona p"),
    @NamedQuery(name = "Persona.findByPerCod",      query = "SELECT p FROM Persona p WHERE p.PerCod     = :PerCod"),
    @NamedQuery(name = "Persona.findLast",          query = "SELECT p FROM Persona p order by p.PerCod     desc"),
    @NamedQuery(name = "Persona.findByMdlUsr",      query = "SELECT p FROM Persona p WHERE p.PerUsrMod  = :MdlUsr"),
    @NamedQuery(name = "Persona.findByPerNom",      query = "SELECT p FROM Persona p WHERE p.PerNom     = :PerNom"),
    @NamedQuery(name = "Persona.findByEmail",       query = "SELECT p FROM Persona p WHERE p.PerEml     = :PerEml"),

/*    
    @NamedQuery(name = "Persona.findPopUp",         query = "SELECT p FROM Persona p "
            + "WHERE (p.PerEsAdm = :PerEsAdm or :PerEsAdm is null) "
            + "AND (p.PerEsAlu = :PerEsAlu or :PerEsAlu is null) "
            + "AND (p.PerEsDoc = :PerEsDoc or :PerEsDoc is null) "
            + "AND (p.PerNom like :PerNom or :PerNom is null) "
            + "AND (p.PerApe like :PerApe or :PerApe is null) "
            + "AND (p.PerCod in (SELECT e.AluPerCod FROM Inscripcion e where e.Curso.CurCod = :CurCod) or :CurCod is null)"),
  */  
    @NamedQuery(name = "Persona.findLastPersona",   query = "SELECT p FROM Persona p ORDER BY p.PerCod DESC")})

public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native" )
    @Column(name = "PerCod", nullable = false)
    private Long PerCod;
    
    @Column(name = "PerNom", length = 100)
    private String PerNom;
    
    @Column(name = "PerApe", length = 100)
    private String PerApe;
    
    @Column(name = "PerUsrMod", length = 255, unique = true)
    private String PerUsrMod;
    
    @Column(name = "PerUsrModID")
    private Long PerUsrModID;
    
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
    
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "PerFil")
    private Filial PerFil;
    
    @Column(name = "PerEml", length = 255, unique = true)
    private String PerEml;
    
    @Column(name = "PerNotEml")
    private Boolean PerNotEml;
    
    @Column(name = "PerNotApp")
    private Boolean PerNotApp;
    
    @Column(name = "PerPass", length = 500)
    private String PerPass;
    
    @Column(name = "PerCntIntLgn")
    private Integer PerCntIntLgn;
    
    @Column(name = "PerFchLog", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date PerFchLog;
    
    @Column(name = "ObjFchMod", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ObjFchMod;


    public Persona() {
        PerCntIntLgn    = 0;
        PerFil          = Filial.COLONIA;
        PerEsAdm        = Boolean.FALSE;
        PerEsAlu        = Boolean.FALSE;
        PerEsDoc        = Boolean.FALSE;
        PerNotApp       = Boolean.TRUE;
        PerNotEml       = Boolean.TRUE;
        PerPass         = "";
    }

    public Persona(Long PerCod, String PerNom, String PerApe, String PerUsrMod, Boolean PerEsDoc, Boolean PerEsAdm, Boolean PerEsAlu, Integer PerNroLib, Integer PerNroEstOrt, Filial PerFil, String PerEml, Boolean PerNotEml, Boolean PerNotApp, Date ObjFchMod) {
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
 
    
    /** 
    * @return Retorna el código de la persona
    */
    public Long getPerCod() {
        return PerCod;
    }

    /**
    *@param PerCod Código de una persona
    */
    public void setPerCod(Long PerCod) {
        this.PerCod = PerCod;
    }

    /**
     *
     * @return Nombre de persona
     */
    public String getPerNom() {
        return PerNom;
    }

    /**
     *
     * @param PerNom Nombre de persona
     */
    public void setPerNom(String PerNom) {
        this.PerNom = PerNom;
    }

    /**
     *
     * @return Apellido
     */
    public String getPerApe() {
        return PerApe;
    }

    /**
     *
     * @param PerApe Apellido
     */
    public void setPerApe(String PerApe) {
        this.PerApe = PerApe;
    }

    /**
     *
     * @return Usuario en moodle
     */
    public String getPerUsrMod() {
        return PerUsrMod;
    }

    /**
     *
     * @param PerUsrMod Usuario en moodle
     */
    public void setPerUsrMod(String PerUsrMod) {
        this.PerUsrMod = PerUsrMod;
    }

    /**
     *
     * @return Es docente
     */
    public Boolean getPerEsDoc() {
        return PerEsDoc;
    }

    /**
     *
     * @param PerEsDoc Es docente
     */
    public void setPerEsDoc(Boolean PerEsDoc) {
        this.PerEsDoc = PerEsDoc;
    }

    /**
     *
     * @return Es administrador
     */
    public Boolean getPerEsAdm() {
        return PerEsAdm;
    }

    /**
     *
     * @param PerEsAdm Es administrador
     */
    public void setPerEsAdm(Boolean PerEsAdm) {
        this.PerEsAdm = PerEsAdm;
    }

    /**
     *
     * @return Es alumno
     */
    public Boolean getPerEsAlu() {
        return PerEsAlu;
    }

    /**
     *
     * @param PerEsAlu Es alumno
     */
    public void setPerEsAlu(Boolean PerEsAlu) {
        this.PerEsAlu = PerEsAlu;
    }

    /**
     *
     * @return Número en libra
     */
    public Integer getPerNroLib() {
        return PerNroLib;
    }

    /**
     *
     * @param PerNroLib Número en libra
     */
    public void setPerNroLib(Integer PerNroLib) {
        this.PerNroLib = PerNroLib;
    }

    /**
     *
     * @return Número estudiante ORT
     */
    public Integer getPerNroEstOrt() {
        return PerNroEstOrt;
    }

    /**
     *
     * @param PerNroEstOrt Número estudiante ORT
     */
    public void setPerNroEstOrt(Integer PerNroEstOrt) {
        this.PerNroEstOrt = PerNroEstOrt;
    }

    /**
     *
     * @return Filial
     */
    public Filial getPerFil() {
        return PerFil;
    }

    /**
     *
     * @param PerFil Filial
     */
    public void setPerFil(Filial PerFil) {
        this.PerFil = PerFil;
    }

    /**
     *
     * @return Email
     */
    public String getPerEml() {
        return PerEml;
    }

    /**
     *
     * @param PerEml Email
     */
    public void setPerEml(String PerEml) {
        this.PerEml = PerEml;
    }

    /**
     *
     * @return Notificar por Email
     */
    public Boolean getPerNotEml() {
        return PerNotEml;
    }

    /**
     *
     * @param PerNotEml Notificar por EMail
     */
    public void setPerNotEml(Boolean PerNotEml) {
        this.PerNotEml = PerNotEml;
    }

    /**
     *
     * @return Notificar por app
     */
    public Boolean getPerNotApp() {
        return PerNotApp;
    }

    /**
     *
     * @param PerNotApp Notificar por APP
     */
    public void setPerNotApp(Boolean PerNotApp) {
        this.PerNotApp = PerNotApp;
    }

    /**
     *
     * @return Fecha de modificado
     */
    public Date getObjFchMod() {
        return ObjFchMod;
    }

    /**
     *
     * @param ObjFchMod Fecha de modificado
     */
    public void setObjFchMod(Date ObjFchMod) {
        this.ObjFchMod = ObjFchMod;
    }

    /**
     *
     * @return Password
     */
    public String getPerPass() {
        return PerPass;
    }

    /**
     *
     * @param PerPass Password
     */
    public void setPerPass(String PerPass) {
        this.PerPass = PerPass;
    }

    /**
     *
     * @return ID de usuario en moodle
     */
    public Long getPerUsrModID() {
        return PerUsrModID;
    }

    /**
     *
     * @param PerUsrModID ID de usuario en moodle
     */
    public void setPerUsrModID(Long PerUsrModID) {
        this.PerUsrModID = PerUsrModID;
    }

    /**
     *
     * @return Fecha de login
     */
    public Date getPerFchLog() { 
        return PerFchLog;
    }

    /**
     *
     * @param PerFchLog Fecha de login
     */
    public void setPerFchLog(Date PerFchLog) {
        this.PerFchLog = PerFchLog;
    }

    /**
     *
     * @return Intentos de login
     */
    public Integer getPerCntIntLgn() {
        return PerCntIntLgn;
    }

    /**
     *
     * @param PerCntIntLgn Intentos de login
     */
    public void setPerCntIntLgn(Integer PerCntIntLgn) {
        this.PerCntIntLgn = PerCntIntLgn;
    }
 
    
    
    public String getNombreCompleto()
    {
        return this.PerNom + " " + this.PerApe;
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
