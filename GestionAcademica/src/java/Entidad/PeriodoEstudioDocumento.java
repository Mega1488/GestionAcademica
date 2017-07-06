/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import Enumerado.RutaArchivos;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author alvar
 */
@Entity
@Table(name = "PERIODO_ESTUDIO_DOCUMENTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PeriodoEstudioDocumento.findAll",       query = "SELECT t FROM PeriodoEstudioDocumento t"),
})
public class PeriodoEstudioDocumento implements Serializable {

    private static final long serialVersionUID = 1L;
   
    //-ATRIBUTOS
    @EmbeddedId
    private final PeriodoEstudioDocumentoPK periodoEstudioDocumentoPK;
    @Column(name = "DocFch", columnDefinition="DATE")
    @Temporal(TemporalType.DATE)
    private Date DocFch;
    @Column(name = "DocAdj")
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
        this.periodoEstudioDocumentoPK = new PeriodoEstudioDocumentoPK();
    }
    
    //-GETTERS Y SETTERS

    public Date getDocFch() {
        return DocFch;
    }

    public void setDocFch(Date DocFch) {
        this.DocFch = DocFch;
    }

    public File getArchivo(){
        //File archivo = new File(RutaArchivos.CARPETA_PRIVADA.getRuta() + File.pathSeparator + this.getDocNom() + "." + this.DocExt);
       String nombreArchivo = RutaArchivos.CARPETA_PRIVADA.getRuta() + File.pathSeparator + this.getDocNom() + "." + this.DocExt;

       try {
           FileUtils.writeByteArrayToFile(new File(nombreArchivo), this.DocAdj);
        } catch (IOException ex) {
            Logger.getLogger(PeriodoEstudioDocumento.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        File archivo = new File(nombreArchivo);
        
        return archivo;
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
        int hash = 0;
        hash += (periodoEstudioDocumentoPK != null ? periodoEstudioDocumentoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PeriodoEstudioDocumento)) {
            return false;
        }
        PeriodoEstudioDocumento other = (PeriodoEstudioDocumento) object;
        if ((this.periodoEstudioDocumentoPK == null && other.periodoEstudioDocumentoPK != null) || (this.periodoEstudioDocumentoPK != null && !this.periodoEstudioDocumentoPK.equals(other.periodoEstudioDocumentoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidad.PeriodoEstudioDocumento[ id=" + periodoEstudioDocumentoPK.toString() + " ]";
    }
    
    
    @Embeddable
    public static class PeriodoEstudioDocumentoPK implements Serializable {
        @ManyToOne(targetEntity = PeriodoEstudio.class, optional=false)
        @JoinColumns({
                @JoinColumn(name="PeriCod", referencedColumnName="PeriCod"),
                @JoinColumn(name="PeriEstCod", referencedColumnName="PeriEstCod")
            })
        private PeriodoEstudio Peri;

        private Integer DocCod;

        public PeriodoEstudioDocumentoPK() {
        }

        public PeriodoEstudio getPeri() {
            return Peri;
        }

        public void setPeri(PeriodoEstudio Peri) {
            this.Peri = Peri;
        }

        public Integer getDocCod() {
            return DocCod;
        }

        public void setDocCod(Integer DocCod) {
            this.DocCod = DocCod;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 79 * hash + Objects.hashCode(this.Peri);
            hash = 79 * hash + Objects.hashCode(this.DocCod);
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
            final PeriodoEstudioDocumentoPK other = (PeriodoEstudioDocumentoPK) obj;
            if (!Objects.equals(this.Peri, other.Peri)) {
                return false;
            }
            if (!Objects.equals(this.DocCod, other.DocCod)) {
                return false;
            }
            return true;
        }




    }
}


