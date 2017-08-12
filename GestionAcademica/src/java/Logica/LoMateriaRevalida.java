/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.MateriaRevalida;
import Persistencia.PerManejador;
import Utiles.Retorno_MsgObj;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author alvar
 */
public class LoMateriaRevalida implements Interfaz.InMateriaRevalida{
    
    private static LoMateriaRevalida instancia;

    private LoMateriaRevalida() {
    }
    
    public static LoMateriaRevalida GetInstancia(){
        if (instancia==null)
        {
            instancia   = new LoMateriaRevalida();
            
        }

        return instancia;
    }

    @Override
    public Object guardar(MateriaRevalida pObjeto) {
        MateriaRevalida mat = (MateriaRevalida) pObjeto;
        
        PerManejador perManager = new PerManejador();
            
        mat.setObjFchMod(new Date());

        Retorno_MsgObj retorno = perManager.guardar(mat);

        if(!retorno.SurgioErrorObjetoRequerido())
        {
            mat.setMatRvlCod((Long) retorno.getObjeto());
            retorno.setObjeto(mat);
        }
            
        return retorno;
    }

    @Override
    public void actualizar(MateriaRevalida pObjeto) {
        MateriaRevalida mat = (MateriaRevalida) pObjeto;
            
        PerManejador perManager = new PerManejador();

        mat.setObjFchMod(new Date());
        
        perManager.actualizar(mat);
    }

    @Override
    public void eliminar(MateriaRevalida pObjeto) {
        PerManejador perManager = new PerManejador();
        perManager.eliminar(pObjeto);
    }

    @Override
    public MateriaRevalida obtener(Object pCodigo) {
        PerManejador perManager = new PerManejador();
        Retorno_MsgObj retorno = perManager.obtener((Long) pCodigo, MateriaRevalida.class);
        
        MateriaRevalida mat = null;
        
        if(!retorno.SurgioErrorObjetoRequerido())
        {
            mat = (MateriaRevalida) retorno.getObjeto();
        }
        
        return mat;
    }

    @Override
    public List<MateriaRevalida> obtenerLista() {
        PerManejador perManager = new PerManejador();

        Retorno_MsgObj retorno = perManager.obtenerLista("MateriaRevalida.findAll", null);
        
        List<MateriaRevalida> lstMat = new ArrayList<>();
        
        if(!retorno.SurgioErrorListaRequerida())
        {
            for(Object objeto : retorno.getLstObjetos())
            {
                lstMat.add((MateriaRevalida) objeto);
            }
        }

        return lstMat;
    }
    
}
