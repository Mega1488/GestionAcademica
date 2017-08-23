/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import Dominio.SincHelper;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author alvar
 */
@Entity
@Table(name = "CALENDARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Calendario.findMateriaAlumno",  query = "SELECT t FROM Calendario t where t.evaluacion.MatEvl.MatCod =:MatCod and t.CalCod in (SELECT A.calendario.CalCod FROM CalendarioAlumno A WHERE A.calendario.CalCod = t.CalCod AND A.Alumno.PerCod =:PerCod) order by t.CalFch desc"),
    @NamedQuery(name = "Calendario.findModuloAlumno",   query = "SELECT t FROM Calendario t where t.evaluacion.ModEvl.ModCod =:ModCod and t.CalCod in (SELECT A.calendario.CalCod FROM CalendarioAlumno A WHERE A.calendario.CalCod = t.CalCod AND A.Alumno.PerCod =:PerCod)  order by t.CalFch desc"),
    @NamedQuery(name = "Calendario.findCursoAlumno",    query = "SELECT t FROM Calendario t where t.evaluacion.CurEvl.CurCod =:CurCod and t.CalCod in (SELECT A.calendario.CalCod FROM CalendarioAlumno A WHERE A.calendario.CalCod = t.CalCod AND A.Alumno.PerCod =:PerCod)  order by t.CalFch desc"),
    @NamedQuery(name = "Calendario.findByAlumno",       query = "SELECT t FROM Calendario t where t.CalCod in (SELECT A.calendario.CalCod FROM t.lstAlumnos A WHERE A.Alumno.PerCod =:PerCod)  order by t.CalFch desc"),
    @NamedQuery(name = "Calendario.findByDocente",      query = "SELECT t FROM Calendario t where t.CalCod in (SELECT A.calendario.CalCod FROM CalendarioDocente A WHERE A.calendario.CalCod = t.CalCod AND A.Docente.PerCod =:PerCod)  order by t.CalFch desc"),
    @NamedQuery(name = "Calendario.findInscripcion",    query = "SELECT t FROM Calendario t WHERE t.EvlInsFchDsd = curdate() order by t.CalFch desc"),
    @NamedQuery(name = "Calendario.findModAfter",       query = "SELECT t FROM Calendario t WHERE t.ObjFchMod >= :ObjFchMod"),
    @NamedQuery(name = "Calendario.findAll",            query = "SELECT t FROM Calendario t order by t.CalFch desc")})

public class Calendario  extends SincHelper implements Serializable {

    private static final long serialVersionUID = 1L;
    
    //-ATRIBUTOS
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native" )
    @Column(name = "CalCod", nullable = false)
    private Long CalCod;
    
    @OneToOne(targetEntity = Evaluacion.class)
    @JoinColumn(name="EvlCod", referencedColumnName = "EvlCod")
    private Evaluacion evaluacion;
    
    @Column(name = "CalFch", columnDefinition="DATE")
    @Temporal(TemporalType.DATE)
    private Date CalFch;
    
    @Column(name = "EvlInsFchDsd", columnDefinition="DATE")
    @Temporal(TemporalType.DATE)
    private Date EvlInsFchDsd;
    
    @Column(name = "EvlInsFchHst", columnDefinition="DATE")
    @Temporal(TemporalType.DATE)
    private Date EvlInsFchHst;
    
    @Column(name = "ObjFchMod", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ObjFchMod;
    
    
    @OneToMany(targetEntity = CalendarioAlumno.class, cascade= CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name="CalCod")
    @Fetch(FetchMode.SUBSELECT)
    private List<CalendarioAlumno> lstAlumnos;
    
    @OneToMany(targetEntity = CalendarioDocente.class, cascade= CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name="CalCod")
    @Fetch(FetchMode.SUBSELECT)
    private List<CalendarioDocente> lstDocentes;
    

    //-CONSTRUCTOR
    public Calendario() {
        this.lstAlumnos = new ArrayList<>();
        this.lstDocentes = new ArrayList<>();
    } 

    //-GETTERS Y SETTERS

    public Long getCalCod() {
        return CalCod;
    }

    public void setCalCod(Long CalCod) {
        this.CalCod = CalCod;
    }

    public Evaluacion getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Evaluacion evaluacion) {
        this.evaluacion = evaluacion;
    }

    public Date getCalFch() {
        return CalFch;
    }

    public void setCalFch(Date CalFch) {
        this.CalFch = CalFch;
    }

    public Date getEvlInsFchDsd() {
        return EvlInsFchDsd;
    }

    public void setEvlInsFchDsd(Date EvlInsFchDsd) {
        this.EvlInsFchDsd = EvlInsFchDsd;
    }

    public Date getEvlInsFchHst() {
        return EvlInsFchHst;
    }

    public void setEvlInsFchHst(Date EvlInsFchHst) {
        this.EvlInsFchHst = EvlInsFchHst;
    }

    public Date getObjFchMod() {
        return ObjFchMod;
    }

    public void setObjFchMod(Date ObjFchMod) {
        this.ObjFchMod = ObjFchMod;
    }

    @JsonIgnore
    @XmlTransient
    public List<CalendarioAlumno> getLstAlumnos() {
        return lstAlumnos;
    }

    public void setLstAlumnos(List<CalendarioAlumno> lstAlumnos) {
        this.lstAlumnos = lstAlumnos;
    }

    @JsonIgnore
    @XmlTransient
    public List<CalendarioDocente> getLstDocentes() {
        return lstDocentes;
    }

    public void setLstDocentes(List<CalendarioDocente> lstDocentes) {
        this.lstDocentes = lstDocentes;
    }
    
    public CalendarioAlumno getAlumnoById(Long CalAlCod){
        
        CalendarioAlumno pAlumno = new CalendarioAlumno();
        
        for(CalendarioAlumno alumn : this.lstAlumnos)
        {
            if(alumn.getCalAlCod().equals(CalAlCod))
            {
                pAlumno = alumn;
                break;
            }
        }
        
        return pAlumno;
    }
    
    public CalendarioAlumno getAlumnoByPersona(Long PerCod){
        
        CalendarioAlumno pAlumno = new CalendarioAlumno();
        
        for(CalendarioAlumno alumn : this.lstAlumnos)
        {
            if(alumn.getAlumno().getPerCod().equals(PerCod))
            {
                pAlumno = alumn;
                break;
            }
        }
        
        return pAlumno;
    }
    
    public CalendarioDocente getDocenteById(Long CalDocCod){
        
        CalendarioDocente pDocente = new CalendarioDocente();
        
        for(CalendarioDocente docente : this.lstDocentes)
        {
            if(docente.getCalDocCod().equals(CalDocCod))
            {
                pDocente = docente;
                break;
            }
        }
        
        return pDocente;
    }
    
    public CalendarioDocente getDocenteByPersona(Long PerCod){
        
        CalendarioDocente pDocente = new CalendarioDocente();
        
        for(CalendarioDocente docente : this.lstDocentes)
        {
            if(docente.getDocente().getPerCod().equals(PerCod))
            {
                pDocente = docente;
                break;
            }
        }
        
        return pDocente;
    }
    
    public Boolean existeAlumno(Long PerCod){
        for(CalendarioAlumno calAlumno : this.lstAlumnos)
        {
            if(calAlumno.getAlumno().getPerCod().equals(PerCod))
            {
                return true;
            }
        }    
            
        return false;
    }
    
    public Boolean existeDocente(Long PerCod){
        for(CalendarioDocente calDocente : this.lstDocentes)
        {
            if(calDocente.getDocente().getPerCod().equals(PerCod))
            {
                return true;
            }
        }
        return false;
    }
    
    public Double getAlumnoCalificacion(Long PerCod)
    {
        Double retorno = 0.0;
        
        for(CalendarioAlumno calAlumno : this.lstAlumnos)
        {
            if(calAlumno.getAlumno().getPerCod().equals(PerCod))
            {
                retorno = calAlumno.getEvlCalVal();
                return retorno;
            }
        }
        
        return retorno;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.CalCod);
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
        final Calendario other = (Calendario) obj;
        if (!Objects.equals(this.CalCod, other.CalCod)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Calendario{" + "CalCod=" + CalCod + ", evaluacion=" + evaluacion + ", CalFch=" + CalFch + ", EvlInsFchDsd=" + EvlInsFchDsd + ", EvlInsFchHst=" + EvlInsFchHst + ", ObjFchMod=" + ObjFchMod + '}';
    }


    @Override
    public Long GetPrimaryKey() {
        return this.CalCod;
    }
    

    
}

