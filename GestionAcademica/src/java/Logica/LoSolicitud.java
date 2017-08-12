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
import Persistencia.PerManejador;
import SDT.SDT_Parameters;
import Utiles.Retorno_MsgObj;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author alvar
 */
public class LoSolicitud implements InABMGenerico{

    private static LoSolicitud instancia;

    private LoSolicitud() {
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
        
        PerManejador perManager = new PerManejador();
            
        solicitud.setObjFchMod(new Date());

        Retorno_MsgObj retorno = perManager.guardar(solicitud);

        if(!retorno.SurgioErrorObjetoRequerido())
        {
            solicitud.setSolCod((Long) retorno.getObjeto());
            retorno.setObjeto(solicitud);
        }
            
        return retorno; 
    }

    @Override
    public Object actualizar(Object pObjeto) {
        
        Solicitud sol  = (Solicitud) pObjeto;
            
        PerManejador perManager = new PerManejador();

        sol.setObjFchMod(new Date());
        
        return perManager.actualizar(sol);
        
    }

    @Override
    public Object eliminar(Object pObjeto) {
        PerManejador perManager = new PerManejador();
        return perManager.eliminar(pObjeto);
    }

    @Override
    public Retorno_MsgObj obtener(Object pObjeto) {
        PerManejador perManager = new PerManejador();
        return perManager.obtener((Long) pObjeto, Solicitud.class);        
    }

    @Override
    public Retorno_MsgObj obtenerLista() {
        
        PerManejador perManager = new PerManejador();

        return perManager.obtenerLista("Solicitud.findAll", null);
    }
    
    public Retorno_MsgObj obtenerListaByAlumno(Long PerCod) {
        
        PerManejador perManager = new PerManejador();

        ArrayList<SDT_Parameters> lstParametros = new ArrayList<>();
        lstParametros.add(new SDT_Parameters(PerCod, "PerCod"));

        return perManager.obtenerLista("Solicitud.findByAlumno", lstParametros);
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
