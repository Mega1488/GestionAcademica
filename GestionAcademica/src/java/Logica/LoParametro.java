/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.Parametro;
import Persistencia.PerParametro;
import java.util.List;

/**
 *
 * @author alvar
 */
public class LoParametro implements Interfaz.InParametro{
    
    private static LoParametro instancia;
    private PerParametro perParametro;

    private LoParametro() {
        perParametro  = new PerParametro();
    }
    
    public static LoParametro GetInstancia(){
        if (instancia==null)
        {
            instancia   = new LoParametro();
            
        }

        return instancia;
    }

    @Override
    public Object guardar(Parametro pObjeto) {
        return perParametro.guardar(pObjeto);
    }

    @Override
    public void actualizar(Parametro pObjeto) {
        perParametro.actualizar(pObjeto);
    }

    @Override
    public Parametro obtener(Object pCodigo) {
        return perParametro.obtener(pCodigo);
    }
    
    
}
