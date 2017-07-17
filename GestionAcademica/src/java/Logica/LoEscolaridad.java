/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Interfaz.InABMGenerico;
import Persistencia.PerEscolaridad;
import Utiles.Retorno_MsgObj;

/**
 *
 * @author alvar
 */
public class LoEscolaridad implements InABMGenerico{

    private static LoEscolaridad instancia;
    private final PerEscolaridad perEscolaridad;

    private LoEscolaridad() {
        perEscolaridad  = new PerEscolaridad();
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
        return perEscolaridad.guardar(pObjeto);
    }

    @Override
    public Object actualizar(Object pObjeto) {
        return perEscolaridad.actualizar(pObjeto);
    }

    @Override
    public Object eliminar(Object pObjeto) {
        return perEscolaridad.eliminar(pObjeto);
    }

    @Override
    public Retorno_MsgObj obtener(Object pObjeto) {
        return perEscolaridad.obtener(pObjeto);
    }

    @Override
    public Retorno_MsgObj obtenerLista() {
        return perEscolaridad.obtenerLista();
    }
    
    public Retorno_MsgObj obtenerListaByAlumno(Long PerCod) {
        return perEscolaridad.obtenerListaByAlumno(PerCod);
    }
    
}
