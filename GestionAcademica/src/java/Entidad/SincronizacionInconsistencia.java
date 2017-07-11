/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import Enumerado.EstadoInconsistencia;
import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.GenericGenerator;

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
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native" )
    @Column(name = "IncCod", nullable = false)
    private Long  IncCod;
           
    @OneToOne(targetEntity = Sincronizacion.class, optional=false)
    @JoinColumn(name="SncCod", referencedColumnName="SncCod")
    private Sincronizacion sincronizacion;

 
    
    @OneToOne(targetEntity = SincInconsistenciaDatos.class, optional=false)
    @JoinColumn(name="IncObjValObjCod", referencedColumnName="IncObjCod")            
    private SincInconsistenciaDatos objetoSeleccionado;
    
    @Column(name = "IncEst")
    private EstadoInconsistencia IncEst;
    
    //-CONSTRUCTOR
    public SincronizacionInconsistencia() {
    }
    
    
    //-GETTERS Y SETTERS

    public Long getIncCod() {
        return IncCod;
    }

    public void setIncCod(Long IncCod) {
        this.IncCod = IncCod;
    }

    public Sincronizacion getSincronizacion() {
        return sincronizacion;
    }

    public void setSincronizacion(Sincronizacion sincronizacion) {
        this.sincronizacion = sincronizacion;
    }

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
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.IncCod);
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
        final SincronizacionInconsistencia other = (SincronizacionInconsistencia) obj;
        if (!Objects.equals(this.IncCod, other.IncCod)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SincronizacionInconsistencia{" + "IncCod=" + IncCod + ", sincronizacion=" + sincronizacion + ", objetoSeleccionado=" + objetoSeleccionado + ", IncEst=" + IncEst + '}';
    }

    
    
    
 

    
}

