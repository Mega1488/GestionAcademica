/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.Evaluacion;
import Interfaz.InABMGenerico;
import Persistencia.PerManejador;
import Utiles.Retorno_MsgObj;
import java.util.Date;

/**
 *
 * @author alvar
 */
public class LoEvaluacion implements InABMGenerico{

    private static LoEvaluacion instancia;

    private LoEvaluacion() {
    }
    
    public static LoEvaluacion GetInstancia(){
        if (instancia==null)
        {
            instancia   = new LoEvaluacion();
        }

        return instancia;
    }
    

    @Override
    public Object guardar(Object pObjeto) {
        
        Evaluacion evl = (Evaluacion) pObjeto;
        
        PerManejador perManager = new PerManejador();
            
        evl.setObjFchMod(new Date());

        Retorno_MsgObj retorno = perManager.guardar(evl);

        if(!retorno.SurgioErrorObjetoRequerido())
        {
            evl.setEvlCod((Long) retorno.getObjeto());
            retorno.setObjeto(evl);
        }
            
        return retorno;
    }

    @Override
    public Object actualizar(Object pObjeto) {
        Evaluacion evl  = (Evaluacion) pObjeto;
            
        PerManejador perManager = new PerManejador();

        evl.setObjFchMod(new Date());
        
        return perManager.actualizar(evl);
    }

    @Override
    public Object eliminar(Object pObjeto) {
        PerManejador perManager = new PerManejador();
        return perManager.eliminar(pObjeto);
    }

    @Override
    public Retorno_MsgObj obtener(Object pObjeto) {
        PerManejador perManager = new PerManejador();
        return perManager.obtener((Long) pObjeto, Evaluacion.class);
    }

    @Override
    public Retorno_MsgObj obtenerLista() {
        PerManejador perManager = new PerManejador();

        return perManager.obtenerLista("Evaluacion.findAll", null);
    }
    
    
}
