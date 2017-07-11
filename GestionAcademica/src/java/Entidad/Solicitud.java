/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import Enumerado.TipoSolicitud;
import Enumerado.EstadoSolicitud;
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
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author alvar
 */
@Entity
@Table(name = "SOLICITUD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Solicitud.findAll",       query = "SELECT t FROM Solicitud t")})

public class Solicitud implements Serializable {

    private static final long serialVersionUID = 1L;


    //-ATRIBUTOS
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native" )
    @Column(name = "SolCod", nullable = false)
    private Long  SolCod;

    @ManyToOne(targetEntity = Persona.class, optional=false)
    @JoinColumn(name="AluPerCod", referencedColumnName = "PerCod")
    private Persona Alumno;
    
    @ManyToOne(targetEntity = Persona.class, optional=false)
    @JoinColumn(name="FunPerCod", referencedColumnName = "PerCod")
    private Persona Funcionario;
    
    @Column(name = "SolTpo")
    private TipoSolicitud SolTpo;
    
    @Column(name = "SolEst")
    private EstadoSolicitud SolEst;
    
    @Column(name = "SolFchIng", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date SolFchIng;
    
    @Column(name = "SolFchPrc", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date SolFchPrc;
    
    @Column(name = "SolFchFin", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date SolFchFin;
    
    @Column(name = "ObjFchMod", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ObjFchMod;
    
    //-CONSTRUCTOR

    public Solicitud() {
    }

  
    //-GETTERS Y SETTERS
  public Long getSolCod() {
        return SolCod;
    }

    public void setSolCod(Long SolCod) {
        this.SolCod = SolCod;
    }

    public Persona getAlumno() {
        return Alumno;
    }

    public void setAlumno(Persona Alumno) {
        this.Alumno = Alumno;
    }

    public Persona getFuncionario() {
        return Funcionario;
    }

    public void setFuncionario(Persona Funcionario) {
        this.Funcionario = Funcionario;
    }

    public TipoSolicitud getSolTpo() {
        return SolTpo;
    }

    public void setSolTpo(TipoSolicitud SolTpo) {
        this.SolTpo = SolTpo;
    }

    public EstadoSolicitud getSolEst() {
        return SolEst;
    }

    public void setSolEst(EstadoSolicitud SolEst) {
        this.SolEst = SolEst;
    }

    public Date getSolFchIng() {
        return SolFchIng;
    }

    public void setSolFchIng(Date SolFchIng) {
        this.SolFchIng = SolFchIng;
    }

    public Date getSolFchPrc() {
        return SolFchPrc;
    }

    public void setSolFchPrc(Date SolFchPrc) {
        this.SolFchPrc = SolFchPrc;
    }

    public Date getSolFchFin() {
        return SolFchFin;
    }

    public void setSolFchFin(Date SolFchFin) {
        this.SolFchFin = SolFchFin;
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
        hash += (SolCod != null ? SolCod.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Solicitud)) {
            return false;
        }
        Solicitud other = (Solicitud) object;
        if ((this.SolCod == null && other.SolCod != null) || (this.SolCod != null && !this.SolCod.equals(other.SolCod))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Solicitud{" + "SolCod=" + SolCod + ", Alumno=" + Alumno + ", Funcionario=" + Funcionario + ", SolTpo=" + SolTpo + ", SolEst=" + SolEst + ", SolFchIng=" + SolFchIng + ", SolFchPrc=" + SolFchPrc + ", SolFchFin=" + SolFchFin + ", ObjFchMod=" + ObjFchMod + '}';
    }

    
    
}
