/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import Enumerado.EstadoInconsistencia;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "SINC_INCONSITENCIA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SincronizacionInconsistencia.findAll",       query = "SELECT t FROM SincronizacionInconsistencia t")})

public class SincronizacionInconsistencia implements Serializable {

    private static final long serialVersionUID = 1L;
    
    //-ATRIBUTOS
    @EmbeddedId
    private final SincInconsistenciaPK sincIncPK;
    
    @ManyToOne(targetEntity = SincInconsistenciaDatos.class, optional=false)
    @JoinColumns({
        @JoinColumn(name="IncObjValSncCod", referencedColumnName="SncCod"),
        @JoinColumn(name="IncObjValIncCod", referencedColumnName="IncCod"),
        @JoinColumn(name="IncObjValObjCod", referencedColumnName="IncObjCod")            
    })
    private SincInconsistenciaDatos objetoSeleccionado;
    
    @Column(name = "IncEst")
    private EstadoInconsistencia IncEst;
    
    //-CONSTRUCTOR
    public SincronizacionInconsistencia() {
        this.sincIncPK = new SincInconsistenciaPK();
    }
    
    
    //-GETTERS Y SETTERS

    public SincInconsistenciaDatos getObjetoSeleccionado() {
        return objetoSeleccionado;
    }

    public void setObjetoSeleccionado(SincInconsistenciaDatos objetoSeleccionado) {
        this.objetoSeleccionado = objetoSeleccionado;
    }

    public EstadoInconsistencia getIncEst() {
        return IncEst;
    }

    public void setIncEst(EstadoInconsistencia IncEst) {
        this.IncEst = IncEst;
    }

    
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sincIncPK != null ? sincIncPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SincronizacionInconsistencia)) {
            return false;
        }
        SincronizacionInconsistencia other = (SincronizacionInconsistencia) object;
        if ((this.sincIncPK == null && other.sincIncPK != null) || (this.sincIncPK != null && !this.sincIncPK.equals(other.sincIncPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.SincronizacionInconsistencia[ id=" + sincIncPK + " ]";
    }
    
}

@Embeddable
class SincInconsistenciaPK implements Serializable {
    @ManyToOne(targetEntity = Sincronizacion.class, optional=false)
    @JoinColumn(name="SncCod", referencedColumnName="SncCod")
    private Sincronizacion sincronizacion;
    
    private Integer IncCod;

    public SincInconsistenciaPK() {
    }

    public Sincronizacion getSincronizacion() {
        return sincronizacion;
    }

    public void setSincronizacion(Sincronizacion sincronizacion) {
        this.sincronizacion = sincronizacion;
    }

    public Integer getIncCod() {
        return IncCod;
    }

    public void setIncCod(Integer IncCod) {
        this.IncCod = IncCod;
    }

    @Override
    public String toString() {
        return "SincInconsistenciaPK{" + "sincronizacion=" + sincronizacion + ", IncCod=" + IncCod + '}';
    }
    
    
}
