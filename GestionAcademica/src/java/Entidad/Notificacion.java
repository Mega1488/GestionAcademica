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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "NOTIFICACION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Notificacion.findAll",       query = "SELECT t FROM Notificacion t"),
    @NamedQuery(name = "Notificacion.findAutoActiva",       query = "SELECT t FROM Notificacion t WHERE t.NotAct =:NotAct AND t.NotTpo =:NotTpo")

})

public class Notificacion implements Serializable {

    private static final long serialVersionUID = 1L;
    
    //-ATRIBUTOS
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native" )
    @Column(name = "NotCod", nullable = false)
    private Long NotCod;
    
    @Column(name = "NotNom", length = 100)
    private String NotNom;
    @Column(name = "NotDsc", length = 500)
    private String NotDsc;
    @Column(name = "NotCon", length = 4000)
    private String NotCon;
    @Column(name = "NotAsu", length = 100)
    private String NotAsu;
    
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "NotTpo")
    private TipoNotificacion NotTpo;
    
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "NotTpoEnv")
    private TipoEnvio NotTpoEnv;
    
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "NotObtDest")
    private ObtenerDestinatario NotObtDest;
    
    @Enumerated(EnumType.ORDINAL)
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
    
    
    @OneToMany(targetEntity = NotificacionBitacora.class, cascade= CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name="NotCod", referencedColumnName="NotCod")
    @Fetch(FetchMode.SUBSELECT)
    private List<NotificacionBitacora> lstBitacora;
    
    @OneToMany(targetEntity = NotificacionConsulta.class, cascade= CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name="NotCod", referencedColumnName="NotCod")
    @Fetch(FetchMode.SUBSELECT)
    private List<NotificacionConsulta> lstConsulta;
    
    @OneToMany(targetEntity = NotificacionDestinatario.class, cascade= CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name="NotCod", referencedColumnName="NotCod")
    @Fetch(FetchMode.SUBSELECT)
    private List<NotificacionDestinatario> lstDestinatario;
    
        
    //-CONSTRUCTOR
    public Notificacion() {
        this.lstDestinatario = new ArrayList<>();
        this.lstBitacora = new ArrayList<>();
        this.lstConsulta = new ArrayList<>();
        
        this.NotAct     = false;
        
        this.NotWeb     = false;
        this.NotApp     = false;
        this.NotEmail   = false;
       
        
        this.NotAsu = "";
        this.NotCon = "";
        this.NotDsc = "";
        this.NotNom = "";
        
        this.NotObtDest = ObtenerDestinatario.UNICA_VEZ;
        this.NotRepTpo  = TipoRepeticion.SIN_REPETICION;
        this.NotTpo     = TipoNotificacion.A_DEMANDA;
        this.NotTpoEnv  = TipoEnvio.COMUN;
        
        
    }
    
    //-GETTERS Y SETTERS

    public Long getNotCod() {
        return NotCod;
    }

    public void setNotCod(Long NotCod) {
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

    public List<NotificacionBitacora> getLstBitacora() {
        return lstBitacora;
    }

    public void setLstBitacora(List<NotificacionBitacora> lstBitacora) {
        this.lstBitacora = lstBitacora;
    }

    public List<NotificacionConsulta> getLstConsulta() {
        return lstConsulta;
    }

    public void setLstConsulta(List<NotificacionConsulta> lstConsulta) {
        this.lstConsulta = lstConsulta;
    }

    public List<NotificacionDestinatario> getLstDestinatario() {
        return lstDestinatario;
    }

    public void setLstDestinatario(List<NotificacionDestinatario> lstDestinatario) {
        this.lstDestinatario = lstDestinatario;
    }

    public String getMedio(){
        String medio = "";
        
        if(this.NotApp) medio += (medio.equals("") ? "Aplicación" : ", Aplicación");
        if(this.NotEmail) medio += (medio.equals("") ? "Email" : ", Email");
        if(this.NotWeb) medio += (medio.equals("") ? "Web" : ", Web");
        
        return medio;
    }
    
    public String ObtenerDestinatariosAgrupados(){
        String destinatarios = "";
        
        for(NotificacionDestinatario destino : this.lstDestinatario)
        {
            if(!destinatarios.equals("")) destinatarios += "\n";
            destinatarios += destino.getNotDstCod() + ": ";
            
            if(destino.getNotEmail() != null) destinatarios += destino.getNotEmail();
            if(destino.getPersona() != null) destinatarios += destino.getPersona().getNombreCompleto();
            
        }
        
        return destinatarios;
    }
    
    public NotificacionDestinatario ObtenerDestinatarioByCod(Long NotDstCod){
        for(NotificacionDestinatario destinatario : this.lstDestinatario)
        {
            if(destinatario.getNotDstCod().equals(NotDstCod)) return destinatario;
        }
        
        return null;
    }
    
    public NotificacionBitacora ObtenerBitacoraByCod(Long NotBitCod){
        for(NotificacionBitacora bitacora : this.lstBitacora)
        {
            if(bitacora.getNotBitCod().equals(NotBitCod)) return bitacora;
        }
        
        return null;
    }
    
    public NotificacionConsulta ObtenerConsultaByCod(Long NotCnsCod){
        for(NotificacionConsulta consulta : this.lstConsulta)
        {
            if(consulta.getNotCnsCod().equals(NotCnsCod)) return consulta;
        }
        
        return null;
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
