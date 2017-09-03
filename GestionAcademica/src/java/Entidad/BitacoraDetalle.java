/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import Enumerado.TipoMensaje;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "BITACORA_DETALLE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BitacoraDetalle.findAll",       query = "SELECT t FROM BitacoraDetalle t")})

public class BitacoraDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    
    
    //-ATRIBUTOS
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native" )
    @Column(name = "BitDetCod", nullable = false)
    private Long BitDetCod;
    
    @OneToOne(targetEntity = BitacoraProceso.class)
    @JoinColumn(name="BitCod", referencedColumnName="BitCod")
    private BitacoraProceso bitacora;
    
    @Column(name = "BitDetMsg", length = 4000)
    private String BitDetMsg;
    
    @Column(name = "BitDetEst")
    private TipoMensaje BitDetEst;
    
    
    //-CONSTRUCTOR
    
    public BitacoraDetalle() {    
    }

    public BitacoraDetalle(BitacoraProceso bitacora, String BitDetMsg, TipoMensaje BitDetEst) {
        this.bitacora = bitacora;
        this.BitDetMsg = BitDetMsg;
        this.BitDetEst = BitDetEst;
    }

    
    

    //-GETTERS Y SETTERS

    public Long getBitDetCod() {
        return BitDetCod;
    }

    public void setBitDetCod(Long BitDetCod) {
        this.BitDetCod = BitDetCod;
    }

    public BitacoraProceso getBitacora() {
        return bitacora;
    }

    public void setBitacora(BitacoraProceso bitacora) {
        this.bitacora = bitacora;
    }

    public String getBitDetMsg() {
        return BitDetMsg;
    }

    public void setBitDetMsg(String BitDetMsg) {
        this.BitDetMsg = BitDetMsg;
    }

    public TipoMensaje getBitDetEst() {
        return BitDetEst;
    }

    public void setBitDetEst(TipoMensaje BitDetEst) {
        this.BitDetEst = BitDetEst;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.BitDetCod);
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
        final BitacoraDetalle other = (BitacoraDetalle) obj;
        if (!Objects.equals(this.BitDetCod, other.BitDetCod)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BitacoraDetalle{" + "BitDetCod=" + BitDetCod + ", BitDetMsg=" + BitDetMsg + ", BitDetEst=" + BitDetEst + '}';
    }

    
    
}
