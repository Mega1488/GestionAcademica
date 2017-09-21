/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import Dominio.SincHelper;
import Enumerado.TipoArchivo;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlInlineBinaryData;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author alvar
 */
@Entity
@Table(name = "ARCHIVOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Archivo.findAll",       query = "SELECT t FROM Archivo t "),
    @NamedQuery(name = "Archivo.findModAfter",      query = "SELECT t FROM Archivo t WHERE t.ObjFchMod >= :ObjFchMod"),
    @NamedQuery(name = "Archivo.findByTipo",    query = "SELECT t FROM Archivo t WHERE t.ArcTpo =:ArcTpo ")})

public class Archivo extends SincHelper  implements Serializable {

    private static final long serialVersionUID = 1L;
    
    
    //-ATRIBUTOS
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native" )
    @Column(name = "ArcCod", nullable = false)
    private Long ArcCod;
    
    @Column(name = "ArcFch", columnDefinition="DATE")
    @Temporal(TemporalType.DATE)
    private Date ArcFch;
    
    @Column(name = "ArcAdj", columnDefinition = "LONGBLOB")
    private byte[] ArcAdj;
    
    @Column(name = "ArcNom", length = 100)
    private String ArcNom;

    @Column(name = "ArcExt", length = 10)
    private String ArcExt;
  
    @Column(name = "ArcTpo")
    private TipoArchivo ArcTpo;
    
    @Column(name = "ObjFchMod", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ObjFchMod;
    
    
    //-CONSTRUCTOR
    
    public Archivo() {
    }

    
    
    //-GETTERS Y SETTERS
    
    public Date getArcFch() {
        return ArcFch;
    }

    public void setArcFch(Date ArcFch) {
        this.ArcFch = ArcFch;
    }

    @JsonIgnore
    @XmlTransient
    public File getArchivo(){
       if(this.ArcAdj != null)
       {
           String nombreArchivo = Utiles.Utilidades.GetInstancia().getPublicTempStorage() + "/" + this.getArcNom() + "." + this.ArcExt;
      
           System.err.println("Descargando archivo: " + nombreArchivo);
        try {
            FileUtils.writeByteArrayToFile(new File(nombreArchivo), this.ArcAdj);
         } catch (IOException ex) {
             Logger.getLogger(PeriodoEstudioDocumento.class.getName()).log(Level.SEVERE, null, ex);
         }

         File archivo = new File(nombreArchivo);

         return archivo;
       }
       
       return null;
    }

    public void setArchivo(File pArchivo, TipoArchivo tpoArchivo) {
        try{
            this.ArcAdj = Files.readAllBytes(pArchivo.toPath());
            this.ArcExt = FilenameUtils.getExtension(pArchivo.getName());
            this.ArcNom = FilenameUtils.getBaseName(pArchivo.getName());
            this.ArcTpo = tpoArchivo;
        }
        catch(IOException ex) {
            Logger.getLogger(PeriodoEstudioDocumento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @XmlInlineBinaryData
    public byte[] getDocAdj() {
        return ArcAdj;
    }

    public void setArcAdj(byte[] ArcAdj) {
        this.ArcAdj = ArcAdj;
    }
    
    public Long getArcCod() {
        return ArcCod;
    }

    public void setArcCod(Long ArcCod) {
        this.ArcCod = ArcCod;
    }

    public String getArcNom() {
        return ArcNom;
    }

    public void setArcNom(String ArcNom) {
        this.ArcNom = ArcNom;
    }

    public String getArcExt() {
        return ArcExt;
    }

    public void setArcExt(String ArcExt) {
        this.ArcExt = ArcExt;
    }

    public Date getObjFchMod() {
        return ObjFchMod;
    }

    public void setObjFchMod(Date ObjFchMod) {
        this.ObjFchMod = ObjFchMod;
    }

    public String getFileBase64(){
        if(this.ArcAdj != null)
        {
            return new String(Base64.encodeBase64(this.ArcAdj), StandardCharsets.UTF_8);
        }
        return null;
    }

    public TipoArchivo getArcTpo() {
        return ArcTpo;
    }

    public void setArcTpo(TipoArchivo ArcTpo) {
        this.ArcTpo = ArcTpo;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.ArcCod);
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
        final Archivo other = (Archivo) obj;
        if (!Objects.equals(this.ArcCod, other.ArcCod)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Archivos{" + "ArcCod=" + ArcCod + ", ArcFch=" + ArcFch + ", ArcNom=" + ArcNom + ", ArcExt=" + ArcExt + ", ObjFchMod=" + ObjFchMod + '}';
    }

    
    
  
    
}
