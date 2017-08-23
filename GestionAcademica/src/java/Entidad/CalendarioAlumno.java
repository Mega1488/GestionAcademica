/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import Dominio.SincHelper;
import Enumerado.EstadoCalendarioEvaluacion;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author alvar
 */


@Entity
@Table(
        name = "CALENDARIO_ALUMNO",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"CalCod", "AluPerCod"})}
    )
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CalendarioAlumno.findModAfter",  query = "SELECT t FROM CalendarioAlumno t where t.ObjFchMod >= :ObjFchMod"),
    @NamedQuery(name = "CalendarioAlumno.findAll",       query = "SELECT t FROM CalendarioAlumno t")})

public class CalendarioAlumno  extends SincHelper implements Serializable {

    private static final long serialVersionUID = 1L;
    
    //-ATRIBUTOS
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native" )
    @Column(name = "CalAlCod", nullable = false)
    private Long CalAlCod;
    
    @ManyToOne(targetEntity = Calendario.class)
    @JoinColumn(name="CalCod", referencedColumnName = "CalCod")
    private Calendario calendario;

    @ManyToOne(targetEntity = Persona.class)
    @JoinColumn(name="AluPerCod", referencedColumnName = "PerCod")
    private Persona Alumno;

    @Column(name = "EvlCalVal", precision=10, scale=2)
    private Double EvlCalVal;
    
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "EvlCalEst", precision=10, scale=2)
    private EstadoCalendarioEvaluacion EvlCalEst;

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

    @ManyToOne(targetEntity = Persona.class, optional=true)
    @JoinColumn(name="EvlCalPerCod", referencedColumnName = "PerCod")
    private Persona EvlCalPor;
    
    @ManyToOne(targetEntity = Persona.class, optional=true)
    @JoinColumn(name="EvlValPerCod", referencedColumnName = "PerCod")
    private Persona EvlValPor;

    

    //-CONSTRUCTOR

    public CalendarioAlumno() {
    }

    //-GETTERS Y SETTERS

    public Long getCalAlCod() {
        return CalAlCod;
    }

    public void setCalAlCod(Long CalAlCod) {
        this.CalAlCod = CalAlCod;
    }
    
    public Calendario getCalendario() {
        return calendario;
    }

    public void setCalendario(Calendario calendario) {
        this.calendario = calendario;
    }

    public Persona getAlumno() {
        return Alumno;
    }

    public void setAlumno(Persona Alumno) {
        this.Alumno = Alumno;
    }

    public Double getEvlCalVal() {
        return EvlCalVal;
    }

    public void setEvlCalVal(Double EvlCalVal) {
        this.EvlCalVal = EvlCalVal;
    }

    public EstadoCalendarioEvaluacion getEvlCalEst() {
        return EvlCalEst;
    }

    public void setEvlCalEst(EstadoCalendarioEvaluacion EvlCalEst) {
        this.EvlCalEst = EvlCalEst;
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

    public Date getObjFchMod() {
        return ObjFchMod;
    }

    public void setObjFchMod(Date ObjFchMod) {
        this.ObjFchMod = ObjFchMod;
    }

    public Persona getEvlCalPor() {
        return EvlCalPor;
    }

    public void setEvlCalPor(Persona EvlCalPor) {
        this.EvlCalPor = EvlCalPor;
    }

    public Persona getEvlValPor() {
        return EvlValPor;
    }

    public void setEvlValPor(Persona EvlValPor) {
        this.EvlValPor = EvlValPor;
    }
    
    public boolean puedeCalificarse()
    {
        return (this.EvlCalEst.equals(EstadoCalendarioEvaluacion.CALIFICADO) || this.EvlCalEst.equals(EstadoCalendarioEvaluacion.PENDIENTE_CORRECCION) || this.EvlCalEst.equals(EstadoCalendarioEvaluacion.SIN_CALIFICAR));
    }
    
    public boolean puedeEnviarToValidar()
    {
        return (this.EvlCalEst.equals(EstadoCalendarioEvaluacion.CALIFICADO));
    }
    
    public boolean puedeValidarse()
    {
        return (this.EvlCalEst.equals(EstadoCalendarioEvaluacion.PENDIENTE_VALIDACION));
    }
    
    public boolean puedeEditarlo()
    {
        return (!this.EvlCalEst.equals(EstadoCalendarioEvaluacion.VALIDADO));
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.CalAlCod);
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
        final CalendarioAlumno other = (CalendarioAlumno) obj;
        if (!Objects.equals(this.CalAlCod, other.CalAlCod)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CalendarioAlumno{" + "CalAlCod=" + CalAlCod + ", Alumno=" + Alumno + ", EvlCalVal=" + EvlCalVal + ", EvlCalEst=" + EvlCalEst + ", EvlCalObs=" + EvlCalObs + ", EvlValObs=" + EvlValObs + ", EvlCalFch=" + EvlCalFch + ", EvlValFch=" + EvlValFch + ", ObjFchMod=" + ObjFchMod + ", EvlCalPor=" + EvlCalPor + ", EvlValPor=" + EvlValPor + '}';
    }

    @Override
    public Long GetPrimaryKey() {
        return this.CalAlCod;
    }
    
}



