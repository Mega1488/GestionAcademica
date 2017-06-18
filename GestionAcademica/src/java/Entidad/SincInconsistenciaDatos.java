/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alvar
 */
@Entity
@Table(name = "SINC_INCONSITENCIA_DATOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SincInconsistenciaDatos.findAll",       query = "SELECT t FROM SincInconsistenciaDatos t")})

public class SincInconsistenciaDatos implements Serializable {

    private static final long serialVersionUID = 1L;
    //-ATRIBUTOS
    @EmbeddedId
    private final IncDatosPK incDatosPK;
    
    @ManyToOne(targetEntity = ObjetoCampo.class, optional=false)
    @JoinColumns({
       @JoinColumn(name="ObjCod", referencedColumnName="ObjCod"),
       @JoinColumn(name="ObjCmpCod", referencedColumnName="ObjCmpCod")
    }) 
    private ObjetoCampo objetoCampo;
    
    @Column(name = "ObjCmpVal", length = 4000)
    private String ObjCmpVal;
    
    //-CONSTRUCTOR
    public SincInconsistenciaDatos() {
        this.incDatosPK = new IncDatosPK();
    }
    
    
    //-GETTERS Y SETTERS

    public ObjetoCampo getObjetoCampo() {
        return objetoCampo;
    }

    public void setObjetoCampo(ObjetoCampo objetoCampo) {
        this.objetoCampo = objetoCampo;
    }

    public String getObjCmpVal() {
        return ObjCmpVal;
    }

    public void setObjCmpVal(String ObjCmpVal) {
        this.ObjCmpVal = ObjCmpVal;
    }

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (incDatosPK != null ? incDatosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SincInconsistenciaDatos)) {
            return false;
        }
        SincInconsistenciaDatos other = (SincInconsistenciaDatos) object;
        if ((this.incDatosPK == null && other.incDatosPK != null) || (this.incDatosPK != null && !this.incDatosPK.equals(other.incDatosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.SincInconsistenciaDatos[ id=" + incDatosPK + " ]";
    }
    
}

@Embeddable
class IncDatosPK implements Serializable {
    @ManyToOne(targetEntity = SincronizacionInconsistencia.class, optional=false)
    @JoinColumns({
       @JoinColumn(name="SncCod", referencedColumnName="SncCod"),
       @JoinColumn(name="IncCod", referencedColumnName="IncCod")
    }) 
    private SincronizacionInconsistencia inconsistencia;
    
    private Integer IncObjCod;

    public IncDatosPK() {
    }

    public SincronizacionInconsistencia getInconsistencia() {
        return inconsistencia;
    }

    public void setInconsistencia(SincronizacionInconsistencia inconsistencia) {
        this.inconsistencia = inconsistencia;
    }

    public Integer getIncObjCod() {
        return IncObjCod;
    }

    public void setIncObjCod(Integer IncObjCod) {
        this.IncObjCod = IncObjCod;
    }

    @Override
    public String toString() {
        return "IncDatosPK{" + "inconsistencia=" + inconsistencia + ", IncObjCod=" + IncObjCod + '}';
    }
    
    
    
}