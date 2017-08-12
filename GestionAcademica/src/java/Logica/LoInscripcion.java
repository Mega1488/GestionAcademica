/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.Inscripcion;
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
public class LoInscripcion implements InABMGenerico{

    private static LoInscripcion instancia;

    private LoInscripcion() {
    }
    
    public static LoInscripcion GetInstancia(){
        if (instancia==null)
        {
            instancia   = new LoInscripcion();
        }

        return instancia;
    }
    

    @Override
    public Object guardar(Object pObjeto) {
        Inscripcion insc = (Inscripcion) pObjeto;
        
        PerManejador perManager = new PerManejador();
            
        insc.setObjFchMod(new Date());

        Retorno_MsgObj retorno = perManager.guardar(insc);

        if(!retorno.SurgioErrorObjetoRequerido())
        {
            insc.setInsCod((Long) retorno.getObjeto());
            retorno.setObjeto(insc);
        }
            
        return retorno;        
    }

    @Override
    public Object actualizar(Object pObjeto) {
        Inscripcion insc = (Inscripcion) pObjeto;
            
        PerManejador perManager = new PerManejador();

        insc.setObjFchMod(new Date());
        
        return perManager.actualizar(insc);
    }

    @Override
    public Object eliminar(Object pObjeto) {
        PerManejador perManager = new PerManejador();
        return perManager.eliminar(pObjeto);
    }

    @Override
    public Retorno_MsgObj obtener(Object pObjeto) {
        PerManejador perManager = new PerManejador();
        return perManager.obtener((Long) pObjeto, Inscripcion.class);
    }

    @Override
    public Retorno_MsgObj obtenerLista() {
        PerManejador perManager = new PerManejador();

        return perManager.obtenerLista("Inscripcion.findAll", null);
    }
    
    public Retorno_MsgObj obtenerInscByCurso(Long PerCod, Long CurCod) {
        
        Retorno_MsgObj retorno = this.obtenerListaByCurso(PerCod, CurCod);
        
        if(!retorno.SurgioErrorListaRequerida())
        {
            if(retorno.getLstObjetos().size() > 0) retorno.setObjeto(retorno.getLstObjetos().get(0));
        }
        retorno.setLstObjetos(null);
        return retorno;
    }
    
    public Retorno_MsgObj obtenerInscByPlan(Long PerCod, Long PlaEstCod) {
        
        Retorno_MsgObj retorno = this.obtenerListaByPlan(PerCod, PlaEstCod);
        
        if(!retorno.SurgioErrorListaRequerida())
        {
            if(retorno.getLstObjetos().size() > 0) retorno.setObjeto(retorno.getLstObjetos().get(0));
        }
        retorno.setLstObjetos(null);
        return retorno;
    }
    
    public Retorno_MsgObj obtenerListaByCurso(Long PerCod, Long CurCod) {
        
        PerManejador perManager = new PerManejador();
        
        ArrayList<SDT_Parameters> lstParametros = new ArrayList<>();
        lstParametros.add(new SDT_Parameters(CurCod, "CurCod"));
        lstParametros.add(new SDT_Parameters(PerCod, "PerCod"));
        
        return perManager.obtenerLista("Inscripcion.findByCurso", lstParametros);
    }
    
    public Retorno_MsgObj obtenerListaByPlan(Long PerCod, Long PlaEstCod) {
        PerManejador perManager = new PerManejador();

        ArrayList<SDT_Parameters> lstParametros = new ArrayList<>();
        lstParametros.add(new SDT_Parameters(PerCod, "PerCod"));
        lstParametros.add(new SDT_Parameters(PlaEstCod, "PlaEstCod"));

        return perManager.obtenerLista("Inscripcion.findByPlan", lstParametros);
        
    }
    
    public Retorno_MsgObj obtenerListaByAlumno(Long PerCod) {
        PerManejador perManager = new PerManejador();

        ArrayList<SDT_Parameters> lstParametros = new ArrayList<>();
        lstParametros.add(new SDT_Parameters(PerCod, "PerCod"));

        return perManager.obtenerLista("Inscripcion.findByAlumno", lstParametros);
    }
    
}
