/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import Enumerado.EstadoCalendarioEvaluacion;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
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
@Table(name = "CALENDARIO_ALUMNO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CalendarioAlumno.findAll",       query = "SELECT t FROM CalendarioAlumno t")})

public class CalendarioAlumno implements Serializable {

    private static final long serialVersionUID = 1L;
    
    //-ATRIBUTOS
    @EmbeddedId
    private final CalendarioAlumnoPK calAluPK;
    
    @Column(name = "EvlCalVal", precision=10, scale=2)
    private Double EvlCalVal;
    
    @ManyToOne(targetEntity = Persona.class, optional=true)
    @JoinColumn(name="EvlCalPerCod", referencedColumnName = "PerCod")
    private Persona EvlCalPor;
    
    @Column(name = "EvlCalEst", precision=10, scale=2)
    private EstadoCalendarioEvaluacion EvlCalEst;
    
    @ManyToOne(targetEntity = Persona.class, optional=true)
    @JoinColumn(name="EvlValPerCod", referencedColumnName = "PerCod")
    private Persona EvlValPor;

    @Column(name = "EvlCalObs", length = 500)
    private String EvlCalObs;
    
    @Column(name = "EvlValObs", length = 500)
    private String EvlValObs;
    
    @Column(name = "EvlCalFch", columnDefinition="DATE")
    @Temporal(TemporalType.DATE)
    private Date EvlCalFch;
    
    @Column(name = "EvlValFch", columnDefinition="DATE")
    @Temporal(TemporalType.DATE)
    private Date EvlValFch;
    
    @Column(name = "ObjFchMod", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ObjFchMod;
    

    //-CONSTRUCTOR

    public CalendarioAlumno() {
        this.calAluPK = new CalendarioAlumnoPK();
    }

    //-GETTERS Y SETTERS

    public Date getObjFchMod() {
        return ObjFchMod;
    }

    public void setObjFchMod(Date ObjFchMod) {
        this.ObjFchMod = ObjFchMod;
    }

    public Double getEvlCalVal() {
        return EvlCalVal;
    }

    public void setEvlCalVal(Double EvlCalVal) {
        this.EvlCalVal = EvlCalVal;
    }

    public Persona getEvlCalPor() {
        return EvlCalPor;
    }

    public void setEvlCalPor(Persona EvlCalPor) {
        this.EvlCalPor = EvlCalPor;
    }

    public EstadoCalendarioEvaluacion getEvlCalEst() {
        return EvlCalEst;
    }

    public void setEvlCalEst(EstadoCalendarioEvaluacion EvlCalEst) {
        this.EvlCalEst = EvlCalEst;
    }

    public Persona getEvlValPor() {
        return EvlValPor;
    }

    public void setEvlValPor(Persona EvlValPor) {
        this.EvlValPor = EvlValPor;
    }

    public String getEvlCalObs() {
        return EvlCalObs;
    }

    public void setEvlCalObs(String EvlCalObs) {
        this.EvlCalObs = EvlCalObs;
    }

    public String getEvlValObs() {
        return EvlValObs;
    }

    public void setEvlValObs(String EvlValObs) {
        this.EvlValObs = EvlValObs;
    }

    public Date getEvlCalFch() {
        return EvlCalFch;
    }

    public void setEvlCalFch(Date EvlCalFch) {
        this.EvlCalFch = EvlCalFch;
    }

    public Date getEvlValFch() {
        return EvlValFch;
    }

    public void setEvlValFch(Date EvlValFch) {
        this.EvlValFch = EvlValFch;
    }
    
    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (calAluPK != null ? calAluPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CalendarioAlumno)) {
            return false;
        }
        CalendarioAlumno other = (CalendarioAlumno) object;
        if ((this.calAluPK == null && other.calAluPK != null) || (this.calAluPK != null && !this.calAluPK.equals(other.calAluPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.CalendarioAlumno[ id=" + calAluPK.toString() + " ]";
    }
    
}

@Embeddable
class CalendarioAlumnoPK implements Serializable {
    @ManyToOne(targetEntity = Calendario.class, optional=false)
    @JoinColumns({
        @JoinColumn(name="CalCod", referencedColumnName = "CalCod"),
        @JoinColumn(name="EvlCod", referencedColumnName = "EvlCod")
    })
    private Calendario Cal;
    
    @ManyToOne(targetEntity = Persona.class, optional=false)
    @JoinColumn(name="AluPerCod", referencedColumnName = "PerCod")
    private Persona Alumno;

    public Calendario getCal() {
        return Cal;
    }

    public void setCal(Calendario Cal) {
        this.Cal = Cal;
    }

    public Persona getAlumno() {
        return Alumno;
    }

    public void setAlumno(Persona Alumno) {
        this.Alumno = Alumno;
    }

    public CalendarioAlumnoPK() {
    }

    @Override
    public String toString() {
        return "CalendarioAlumnoPK{" + "Cal=" + Cal + ", Alumno=" + Alumno + '}';
    }
    
    
}

