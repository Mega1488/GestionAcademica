/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

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
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author alvar
 */


@Entity
@Table(
        name = "PERIODO_ESTUDIO",
        uniqueConstraints = {
                                @UniqueConstraint(columnNames = {"PeriCod", "MatEstMatCod"}),
                                @UniqueConstraint(columnNames = {"PeriCod", "ModEstModCod"})
                            }
    )
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PeriodoEstudio.findAll",       query = "SELECT t FROM PeriodoEstudio t order by t.periodo.PerFchIni desc"),
})
public class PeriodoEstudio implements Serializable {

    private static final long serialVersionUID = 1L;
    
    //-ATRIBUTOS
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native" )
    @Column(name = "PeriEstCod", nullable = false)
    private Long PeriEstCod;
    
    @OneToOne(targetEntity = Periodo.class)        
    @JoinColumn(name="PeriCod", referencedColumnName="PeriCod")
    private Periodo periodo;

    @OneToOne(targetEntity = Materia.class)        
    @JoinColumn(name="MatEstMatCod", referencedColumnName="MatCod")
    private Materia Materia;
    
    @OneToOne(targetEntity = Modulo.class)        
    @JoinColumn(name="ModEstModCod", referencedColumnName="ModCod")
    private Modulo Modulo;
    
    @Column(name = "ObjFchMod", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ObjFchMod;
    
    
    //----------------------------------------------------------------------
    @OneToMany(targetEntity = PeriodoEstudioAlumno.class, cascade= CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name="PeriEstCod")
    @Fetch(FetchMode.SUBSELECT)
    private List<PeriodoEstudioAlumno> lstAlumno;
    
    @OneToMany(targetEntity = PeriodoEstudioDocente.class, cascade= CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name="PeriEstCod")
    @Fetch(FetchMode.SUBSELECT)
    private List<PeriodoEstudioDocente> lstDocente;
    
    @OneToMany(targetEntity = PeriodoEstudioDocumento.class, cascade= CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name="PeriEstCod")
    @Fetch(FetchMode.SUBSELECT)
    private List<PeriodoEstudioDocumento> lstDocumento;
    //----------------------------------------------------------------------
    

    //-CONSTRUCTOR

    public PeriodoEstudio() {
        this.lstAlumno = new ArrayList<>();
        this.lstDocente = new ArrayList<>();
        this.lstDocumento = new ArrayList<>();
    }
    
    //-GETTERS Y SETTERS

    public Long getPeriEstCod() {
        return PeriEstCod;
    }

    public void setPeriEstCod(Long PeriEstCod) {
        this.PeriEstCod = PeriEstCod;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo Periodo) {
        this.periodo = Periodo;
    }

    public Materia getMateria() {
        return Materia;
    }

    public void setMateria(Materia Materia) {
        this.Materia = Materia;
    }

    public Modulo getModulo() {
        return Modulo;
    }

    public void setModulo(Modulo Modulo) {
        this.Modulo = Modulo;
    }

    public Date getObjFchMod() {
        return ObjFchMod;
    }

    public void setObjFchMod(Date ObjFchMod) {
        this.ObjFchMod = ObjFchMod;
    }

    public List<PeriodoEstudioAlumno> getLstAlumno() {
        return lstAlumno;
    }

    public void setLstAlumno(List<PeriodoEstudioAlumno> lstAlumno) {
        this.lstAlumno = lstAlumno;
    }

    public List<PeriodoEstudioDocente> getLstDocente() {
        return lstDocente;
    }

    public void setLstDocente(List<PeriodoEstudioDocente> lstDocente) {
        this.lstDocente = lstDocente;
    }

    public List<PeriodoEstudioDocumento> getLstDocumento() {
        return lstDocumento;
    }

    public void setLstDocumento(List<PeriodoEstudioDocumento> lstDocumento) {
        this.lstDocumento = lstDocumento;
    }

    public String getEstudioNombre()
    {
        if(this.Materia != null) return this.Materia.getMatNom();
        
        if(this.Modulo != null) return this.Modulo.getModNom();
        
        return "";
    }
    
    public int getCantidadAlumnos()
    {
        if(this.lstAlumno != null) return this.lstAlumno.size();
        
        return 0;
    }
    
    public int getCantidadDocente()
    {
        if(this.lstDocente != null) return this.lstDocente.size();
        
        return 0;
    }
    
    
     public PeriodoEstudioAlumno getAlumnoById(Long PeriEstAluCod){
        
        PeriodoEstudioAlumno pAlumno = new PeriodoEstudioAlumno();
        
        for(PeriodoEstudioAlumno alumn : this.lstAlumno)
        {
            if(alumn.getPeriEstAluCod().equals(PeriEstAluCod))
            {
                pAlumno = alumn;
                break;
            }
        }
        
        return pAlumno;
    }
    
    public PeriodoEstudioDocente getDocenteById(Long PeriEstDocCod){
        
        PeriodoEstudioDocente pDocente = new PeriodoEstudioDocente();
        
        for(PeriodoEstudioDocente docente : this.lstDocente)
        {
            if(docente.getPeriEstDocCod().equals(PeriEstDocCod))
            {
                pDocente = docente;
                break;
            }
        }
        
        return pDocente;
    }
    
    public PeriodoEstudioDocumento getDocumentoById(Long DocCod){
        
        PeriodoEstudioDocumento pDocumento = new PeriodoEstudioDocumento();
        
        for(PeriodoEstudioDocumento documento : this.lstDocumento)
        {
            if(documento.getDocCod().equals(DocCod))
            {
                pDocumento = documento;
                break;
            }
        }
        
        return pDocumento;
    }
    
    public String getCarreraCursoNombre()
    {
        if(this.getModulo() != null)
        {
            return this.getModulo().getCurso().getCurNom();
        }
        
        if(this.getMateria() != null)
        {
            return this.getMateria().getPlan().getCarreraPlanNombre();
        }
        
        return "";
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.PeriEstCod);
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
        final PeriodoEstudio other = (PeriodoEstudio) obj;
        if (!Objects.equals(this.PeriEstCod, other.PeriEstCod)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PeriodoEstudio{" + "PeriEstCod=" + PeriEstCod + ", Periodo=" + periodo + ", Materia=" + Materia + ", Modulo=" + Modulo + ", ObjFchMod=" + ObjFchMod + '}';
    }
    
}


