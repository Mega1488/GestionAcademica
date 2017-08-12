/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.Escolaridad;
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
public class LoEscolaridad implements InABMGenerico{

    private static LoEscolaridad instancia;

    private LoEscolaridad() {
    }
    
    public static LoEscolaridad GetInstancia(){
        if (instancia==null)
        {
            instancia   = new LoEscolaridad();
        }

        return instancia;
    }
    

    @Override
    public Object guardar(Object pObjeto) {
        
        Escolaridad escolaridad = (Escolaridad) pObjeto;
        
        PerManejador perManager = new PerManejador();
            
        escolaridad.setObjFchMod(new Date());

        Retorno_MsgObj retorno = perManager.guardar(escolaridad);

        if(!retorno.SurgioErrorObjetoRequerido())
        {
            escolaridad.setEscCod((Long) retorno.getObjeto());
            retorno.setObjeto(escolaridad);
        }
            
        return retorno;
    }

    @Override
    public Object actualizar(Object pObjeto) {
        
        Escolaridad esc  = (Escolaridad) pObjeto;
            
        PerManejador perManager = new PerManejador();

        esc.setObjFchMod(new Date());
        
        return perManager.actualizar(esc);
    }

    @Override
    public Object eliminar(Object pObjeto) {
        PerManejador perManager = new PerManejador();
        return perManager.eliminar(pObjeto);
    }

    @Override
    public Retorno_MsgObj obtener(Object pObjeto) {
        PerManejador perManager = new PerManejador();
        return perManager.obtener((Long) pObjeto, Escolaridad.class);
    }

    @Override
    public Retorno_MsgObj obtenerLista() {
        PerManejador perManager = new PerManejador();

        return perManager.obtenerLista("Escolaridad.findAll", null);
    }
    
    public Retorno_MsgObj obtenerListaByAlumno(Long PerCod) {
        
        PerManejador perManager = new PerManejador();

        ArrayList<SDT_Parameters> lstParametros = new ArrayList<>();
        lstParametros.add(new SDT_Parameters(PerCod, "PerCod"));

        return perManager.obtenerLista("Escolaridad.findByAlumno", lstParametros);
        
    }
    
}
