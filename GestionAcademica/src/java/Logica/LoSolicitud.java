/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.Persona;
import Entidad.Solicitud;
import Enumerado.EstadoSolicitud;
import Interfaz.InABMGenerico;
import Persistencia.PerSolicitud;
import Utiles.Retorno_MsgObj;
import java.util.Date;

/**
 *
 * @author alvar
 */
public class LoSolicitud implements InABMGenerico{

    private static LoSolicitud instancia;
    private final PerSolicitud perSolicitud;

    private LoSolicitud() {
        perSolicitud  = new PerSolicitud();
    }
    
    public static LoSolicitud GetInstancia(){
        if (instancia==null)
        {
            instancia   = new LoSolicitud();
        }

        return instancia;
    }
    

    @Override
    public Object guardar(Object pObjeto) {
        Solicitud solicitud = (Solicitud) pObjeto;
        solicitud.setSolFchIng(new Date());
        solicitud.setSolEst(EstadoSolicitud.SIN_TOMAR);
        return perSolicitud.guardar(pObjeto);
    }

    @Override
    public Object actualizar(Object pObjeto) {
        return perSolicitud.actualizar(pObjeto);
    }

    @Override
    public Object eliminar(Object pObjeto) {
        return perSolicitud.eliminar(pObjeto);
    }

    @Override
    public Retorno_MsgObj obtener(Object pObjeto) {
        return perSolicitud.obtener(pObjeto);
    }

    @Override
    public Retorno_MsgObj obtenerLista() {
        return perSolicitud.obtenerLista();
    }
    
    public Retorno_MsgObj obtenerListaByAlumno(Long PerCod) {
        return perSolicitud.obtenerListaByAlumno(PerCod);
    }
    
    public Retorno_MsgObj tomarSolicitud(Solicitud solicitud, Persona persona){
        if(persona != null) solicitud.setFuncionario(persona); solicitud.setSolEst(EstadoSolicitud.TOMADA);
        solicitud.setSolFchPrc(new Date());
        return (Retorno_MsgObj) this.actualizar(solicitud);
    }
    
    public Retorno_MsgObj liberarSolicitud(Solicitud solicitud){
        solicitud.setFuncionario(null); 
        solicitud.setSolEst(EstadoSolicitud.SIN_TOMAR);
        
        return (Retorno_MsgObj) this.actualizar(solicitud);
    }
    
    public Retorno_MsgObj finalizarSolicitud(Solicitud solicitud){
        solicitud.setSolFchFin(new Date()); 
        solicitud.setSolEst(EstadoSolicitud.FINALIZADA);
        
        return (Retorno_MsgObj) this.actualizar(solicitud);
    }
    
}
