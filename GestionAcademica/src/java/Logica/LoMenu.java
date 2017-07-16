/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Interfaz.InABMGenerico;
import Persistencia.PerMenu;
import Utiles.Retorno_MsgObj;

/**
 *
 * @author alvar
 */
public class LoMenu implements InABMGenerico{

    private static LoMenu instancia;
    private PerMenu perMenu;

    private LoMenu() {
        perMenu  = new PerMenu();
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
        return perMenu.guardar(pObjeto);
    }

    @Override
    public Object actualizar(Object pObjeto) {
        return perMenu.actualizar(pObjeto);
    }

    @Override
    public Object eliminar(Object pObjeto) {
        return perMenu.eliminar(pObjeto);
    }

    @Override
    public Retorno_MsgObj obtener(Object pObjeto) {
        return perMenu.obtener(pObjeto);
    }

    @Override
    public Retorno_MsgObj obtenerLista() {
        return perMenu.obtenerLista();
    }
    
    
    
}
