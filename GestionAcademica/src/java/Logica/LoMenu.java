/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.Menu;
import Interfaz.InABMGenerico;
import Persistencia.PerManejador;
import Utiles.Retorno_MsgObj;

/**
 *
 * @author alvar
 */
public class LoMenu implements InABMGenerico{

    private static LoMenu instancia;

    private LoMenu() {
    }
    
    public static LoMenu GetInstancia(){
        if (instancia==null)
        {
            instancia   = new LoMenu();
        }

        return instancia;
    }
    

    @Override
    public Object guardar(Object pObjeto) {
        
        Menu menu = (Menu) pObjeto;
        
        PerManejador perManejador   = new PerManejador();
        Retorno_MsgObj retorno      = perManejador.guardar(menu);

        if(!retorno.SurgioError())
        {
            menu.setMenCod((Long) retorno.getObjeto());
            retorno.setObjeto(menu);
        }
        
        return retorno;
    }

    @Override
    public Object actualizar(Object pObjeto) {
        
        PerManejador perManejador   = new PerManejador();
        
        return perManejador.actualizar(pObjeto);
    }

    @Override
    public Object eliminar(Object pObjeto) {
        PerManejador perManejador   = new PerManejador();
        
        return perManejador.eliminar(pObjeto);
    }

    @Override
    public Retorno_MsgObj obtener(Object pObjeto) {
        PerManejador perManejador   = new PerManejador();
        
        return perManejador.obtener((Long) pObjeto, Menu.class);
    }

    @Override
    public Retorno_MsgObj obtenerLista() {
        
        PerManejador perManejador   = new PerManejador();
        
        return perManejador.obtenerLista("Menu.findAll", null);
    }
    
    
    
}
