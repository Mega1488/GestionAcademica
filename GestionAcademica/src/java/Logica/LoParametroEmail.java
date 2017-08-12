/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.ParametroEmail;
import Persistencia.PerManejador;
import Utiles.Retorno_MsgObj;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alvar
 */
public class LoParametroEmail implements Interfaz.InParametroEmail{
    
    private static LoParametroEmail   instancia;

    private LoParametroEmail() {
        
    }
    
    public static LoParametroEmail GetInstancia(){
        if (instancia==null)
        {
            instancia   = new LoParametroEmail();
            
        }

        return instancia;
    }

    @Override
    public Object guardar(ParametroEmail pObjeto) {
        ParametroEmail eml = (ParametroEmail) pObjeto;
        
        PerManejador perManager = new PerManejador();
        Retorno_MsgObj retorno = perManager.guardar(eml);

        if(!retorno.SurgioErrorObjetoRequerido())
        {
            eml.setParEmlCod((Long) retorno.getObjeto());
            retorno.setObjeto(eml);
        }
            
        return retorno; 
        //return perParametroEmail.guardar(pObjeto);
    }

    @Override
    public void actualizar(ParametroEmail pObjeto) {
        PerManejador perManager = new PerManejador();
        perManager.actualizar(pObjeto);
    }

    @Override
    public void eliminar(ParametroEmail pObjeto) {
        PerManejador perManager = new PerManejador();
        perManager.eliminar(pObjeto);
    }

    @Override
    public ParametroEmail obtener(Object pCodigo) {
        PerManejador perManager = new PerManejador();
        Retorno_MsgObj retorno  = perManager.obtener((Long) pCodigo, ParametroEmail.class);
        
        ParametroEmail eml = null;
        
        if(!retorno.SurgioErrorObjetoRequerido())
        {
            eml = (ParametroEmail) retorno.getObjeto();
        }
        
        return eml;
    }
    

    @Override
    public List<ParametroEmail> obtenerLista() {
        
        PerManejador perManager = new PerManejador();

        Retorno_MsgObj retorno = perManager.obtenerLista("ParametroEmail.findAll", null);
        
        List<ParametroEmail> lstEml = new ArrayList<>();
        
        if(!retorno.SurgioErrorListaRequerida())
        {
            for(Object objeto : retorno.getLstObjetos())
            {
                lstEml.add((ParametroEmail) objeto);
            }
        }

        return lstEml;
    }
   
}
