/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utiles;

import Entidad.*;
import Enumerado.TipoMensaje;
import SDT.SDT_PersonaEstudio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author alvar
 */
@XmlRootElement
@XmlSeeAlso({Persona.class, Calendario.class, CalendarioAlumno.class, CalendarioDocente.class, Carrera.class, Curso.class, Escolaridad.class, Evaluacion.class, Inscripcion.class, 
    Materia.class, MateriaPrevia.class, MateriaRevalida.class, Modulo.class, Periodo.class, PeriodoEstudio.class, PeriodoEstudioAlumno.class, PeriodoEstudioDocente.class, 
    PlanEstudio.class, Solicitud.class, TipoEvaluacion.class, SDT_PersonaEstudio.class, Objeto.class, ObjetoCampo.class, SincRegistroEliminado.class, Sincronizacion.class})
public class Retorno_MsgObj implements Serializable{

    private Mensajes mensaje;
    private ArrayList<Mensajes> lstMensajes;
    private Object   objeto;
    private List<Object> lstObjetos;
    
    public Retorno_MsgObj() {
    }

    public Retorno_MsgObj(Mensajes mensaje, Object objeto) {
        this.mensaje = mensaje;
        this.objeto = objeto;
    }

    public Retorno_MsgObj(ArrayList<Mensajes> lstMensajes, Object objeto) {
        this.lstMensajes = lstMensajes;
        this.objeto = objeto;
    }
    
    public Retorno_MsgObj(Mensajes mensaje, ArrayList<Object> lstObjetos) {
        this.mensaje = mensaje;
        this.lstObjetos = lstObjetos;
    }

    public Retorno_MsgObj(Mensajes mensaje) {
        this.mensaje = mensaje;
    }
    
    

    public ArrayList<Mensajes> getLstMensajes() {
        if (lstMensajes == null) {
            lstMensajes = new ArrayList<Mensajes>();
        }
        return lstMensajes;
    }

    public void setLstMensajes(ArrayList<Mensajes> lstMensajes) {
        this.lstMensajes = lstMensajes;
    }
    
    public void addMensaje(Mensajes mensaje)
    {
        this.lstMensajes.add(mensaje);
    }
    

    public Mensajes getMensaje() {
        return mensaje;
    }

    public void setMensaje(Mensajes mensaje) {
        this.mensaje = mensaje;
    }

    public Object getObjeto() {
        return objeto;
    }

    public void setObjeto(Object objeto) {
        this.objeto = objeto;
    }

    public List<Object> getLstObjetos() {
        if (lstObjetos == null) {
            lstObjetos = new ArrayList<Object>();
        }
        return lstObjetos;
    }

    public void setLstObjetos(List<Object> lstObjetos) {
        this.lstObjetos = lstObjetos;
    }

    public boolean SurgioError()
    {
        if(this.getMensaje() == null) return true;
        return this.getMensaje().getTipoMensaje() == TipoMensaje.ERROR;
    }
    
    public boolean SurgioErrorObjetoRequerido()
    {
        if(this.getMensaje() == null) return true;
        return this.getMensaje().getTipoMensaje() == TipoMensaje.ERROR || this.getObjeto() == null;
    }
 
    public boolean SurgioErrorListaRequerida()
    {
        if(this.getMensaje() == null) return true;
        
        return this.getMensaje().getTipoMensaje() == TipoMensaje.ERROR || this.getLstObjetos() == null;
    }

    @Override
    public String toString() {
        return "Retorno_MsgObj{" + "mensaje=" + mensaje + ", lstMensajes=" + lstMensajes + ", objeto=" + objeto + ", lstObjetos=" + lstObjetos + '}';
    }
    
    
    
    
}
