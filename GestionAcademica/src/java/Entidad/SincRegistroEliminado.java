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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "SINC_REGISTRO_ELIMINADO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SincRegistroELiminado.findModAfter",  query = "SELECT t FROM SincRegistroEliminado t where t.SncObjElimFch >= :ObjFchMod"),
    @NamedQuery(name = "SincRegistroELiminado.findAll",       query = "SELECT t FROM SincRegistroEliminado t")})

public class SincRegistroEliminado implements Serializable {

    private static final long serialVersionUID = 1L;
    
    //-ATRIBUTOS
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native" )
    @Column(name = "SncObjElimCod", nullable = false)
    private Long SncObjElimCod;

    @OneToOne(targetEntity = Objeto.class)
    @JoinColumn(name="ObjNom", referencedColumnName="ObjNom")
    private Objeto objeto;
    
    @Column(name = "SncObjElimFch", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date SncObjElimFch;
    
    @Column(name = "ObjDet", length = 4000)
    private String ObjDet;
   
    
    //-CONSTRUCTOR
    public SincRegistroEliminado() {
    }
        
    //-GETTERS Y SETTERS

    public Long getSncObjElimCod() {
        return SncObjElimCod;
    }

    public void setSncObjElimCod(Long SncObjElimCod) {
        this.SncObjElimCod = SncObjElimCod;
    }

    public Objeto getObjeto() {
        return objeto;
    }

    public void setObjeto(Objeto objeto) {
        this.objeto = objeto;
    }

    public Date getSncObjElimFch() {
        return SncObjElimFch;
    }

    public void setSncObjElimFch(Date SncObjElimFch) {
        this.SncObjElimFch = SncObjElimFch;
    }

    public String getObjDet() {
        return ObjDet;
    }

    public void setObjDet(String ObjDet) {
        this.ObjDet = ObjDet;
    }

      

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.SncObjElimCod);
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
        final SincRegistroEliminado other = (SincRegistroEliminado) obj;
        if (!Objects.equals(this.SncObjElimCod, other.SncObjElimCod)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SincRegistroEliminado{" + "SncObjElimCod=" + SncObjElimCod + ", objeto=" + objeto + ", SncObjElimFch=" + SncObjElimFch + ", ObjDet=" + ObjDet + '}';
    }

   
    
}


