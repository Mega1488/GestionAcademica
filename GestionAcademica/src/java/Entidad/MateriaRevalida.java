/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
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
import javax.persistence.OneToOne;
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
@Table(name = "MATERIA_REVALIDA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MateriaRevalida.findAll",       query = "SELECT t FROM MateriaRevalida t")})

public class MateriaRevalida implements Serializable {

    private static final long serialVersionUID = 1L;
    
    //-ATRIBUTOS
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native" )
    @Column(name = "MatRvlCod", nullable = false)
    private Long MatRvlCod; 
    
    @OneToOne(targetEntity = Inscripcion.class, optional=false)        
    @JoinColumn(name="InsCod", referencedColumnName="InsCod")
    private Inscripcion inscripcion;

    @ManyToOne(targetEntity = Materia.class, optional=false)        
    @JoinColumn(name="MatCod", referencedColumnName="MatCod")
    private Materia materia;



    @Column(name = "ObjFchMod", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ObjFchMod;

    //-CONSTRUCTOR
    public MateriaRevalida() {
    }   
    
    //-GETTERS Y SETTERS

    public Long getMatRvlCod() {
        return MatRvlCod;
    }

    public void setMatRvlCod(Long MatRvlCod) {
        this.MatRvlCod = MatRvlCod;
    }

    public Inscripcion getInscripcion() {
        return inscripcion;
    }

    public void setInscripcion(Inscripcion inscripcion) {
        this.inscripcion = inscripcion;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public Date getObjFchMod() {
        return ObjFchMod;
    }

    public void setObjFchMod(Date ObjFchMod) {
        this.ObjFchMod = ObjFchMod;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.MatRvlCod);
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
        final MateriaRevalida other = (MateriaRevalida) obj;
        if (!Objects.equals(this.MatRvlCod, other.MatRvlCod)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MateriaRevalida{" + "MatRvlCod=" + MatRvlCod + ", inscripcion=" + inscripcion + ", materia=" + materia + ", ObjFchMod=" + ObjFchMod + '}';
    }

    
    
    
    
}

