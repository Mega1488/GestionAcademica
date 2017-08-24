/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import Dominio.SincHelper;
import Enumerado.RutaArchivos;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.xml.bind.annotation.XmlInlineBinaryData;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author alvar
 */



@Entity
@Table(name = "PERIODO_ESTUDIO_DOCUMENTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PeriodoEstudioDocumento.findModAfter",  query = "SELECT t FROM PeriodoEstudioDocumento t  WHERE t.ObjFchMod >= :ObjFchMod"),
    @NamedQuery(name = "PeriodoEstudioDocumento.findAll",       query = "SELECT t FROM PeriodoEstudioDocumento t")
})
public class PeriodoEstudioDocumento extends SincHelper implements Serializable {

    private static final long serialVersionUID = 1L;
   
    //-ATRIBUTOS
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native" )
    @Column(name = "DocCod", nullable = false)
    private Long DocCod;
    
    @OneToOne(targetEntity = PeriodoEstudio.class)
    @JoinColumn(name="PeriEstCod", referencedColumnName="PeriEstCod")
    private PeriodoEstudio periodoEstudio;
    
    @Column(name = "DocFch", columnDefinition="DATE")
    @Temporal(TemporalType.DATE)
    private Date DocFch;
    
    @Column(name = "DocAdj", columnDefinition = "LONGBLOB")
    private byte[] DocAdj;
    
    @Column(name = "DocNom", length = 100)
    private String DocNom;
    @Column(name = "DocExt", length = 10)
    private String DocExt;
  
    @Column(name = "ObjFchMod", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ObjFchMod;


    //-CONSTRUCTOR
    public PeriodoEstudioDocumento() {
    }
    
    //-GETTERS Y SETTERS

    public Date getDocFch() {
        return DocFch;
    }

    public void setDocFch(Date DocFch) {
        this.DocFch = DocFch;
    }

    @JsonIgnore
    @XmlTransient
    public File getArchivo(){
       if(this.DocAdj != null)
       {
           String nombreArchivo = Utiles.Utilidades.GetInstancia().getPrivateTempStorage() + "/" + this.getDocNom() + "." + this.DocExt;
      
           System.err.println("Descargando archivo: " + nombreArchivo);
        try {
            FileUtils.writeByteArrayToFile(new File(nombreArchivo), this.DocAdj);
         } catch (IOException ex) {
             Logger.getLogger(PeriodoEstudioDocumento.class.getName()).log(Level.SEVERE, null, ex);
         }

         File archivo = new File(nombreArchivo);

         return archivo;
       }
       
       return null;
    }

    public void setArchivo(File pArchivo) {
        try{
            this.DocAdj = Files.readAllBytes(pArchivo.toPath());
            this.DocExt = FilenameUtils.getExtension(pArchivo.getName());
            this.DocNom = FilenameUtils.getBaseName(pArchivo.getName());
        }
        catch(IOException ex) {
            Logger.getLogger(PeriodoEstudioDocumento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @XmlInlineBinaryData
    public byte[] getDocAdj() {
        return DocAdj;
    }

    public void setDocAdj(byte[] DocAdj) {
        this.DocAdj = DocAdj;
    }
    
    public Long getDocCod() {
        return DocCod;
    }

    public void setDocCod(Long DocCod) {
        this.DocCod = DocCod;
    }

    public PeriodoEstudio getPeriodo() {
        return periodoEstudio;
    }

    public void setPeriodo(PeriodoEstudio periodo) {
        this.periodoEstudio = periodo;
    }

    public String getDocNom() {
        return DocNom;
    }

    public void setDocNom(String DocNom) {
        this.DocNom = DocNom;
    }

    public String getDocExt() {
        return DocExt;
    }

    public void setDocExt(String DocExt) {
        this.DocExt = DocExt;
    }

    public Date getObjFchMod() {
        return ObjFchMod;
    }

    public void setObjFchMod(Date ObjFchMod) {
        this.ObjFchMod = ObjFchMod;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.DocCod);
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
        final PeriodoEstudioDocumento other = (PeriodoEstudioDocumento) obj;
        if (!Objects.equals(this.DocCod, other.DocCod)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PeriodoEstudioDocumento{" + "DocCod=" + DocCod + ", periodo=" + periodoEstudio + ", DocFch=" + DocFch + ", DocAdj=" + DocAdj + ", DocNom=" + DocNom + ", DocExt=" + DocExt + ", ObjFchMod=" + ObjFchMod + '}';
    }
    
    
  
}


