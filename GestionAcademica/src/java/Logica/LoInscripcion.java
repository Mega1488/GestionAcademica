/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Interfaz.InABMGenerico;
import Persistencia.PerInscripcion;
import Utiles.Retorno_MsgObj;

/**
 *
 * @author alvar
 */
public class LoInscripcion implements InABMGenerico{

    private static LoInscripcion instancia;
    private final PerInscripcion perInscripcion;

    private LoInscripcion() {
        perInscripcion  = new PerInscripcion();
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
        return perInscripcion.guardar(pObjeto);
    }

    @Override
    public Object actualizar(Object pObjeto) {
        return perInscripcion.actualizar(pObjeto);
    }

    @Override
    public Object eliminar(Object pObjeto) {
        return perInscripcion.eliminar(pObjeto);
    }

    @Override
    public Retorno_MsgObj obtener(Object pObjeto) {
        return perInscripcion.obtener(pObjeto);
    }

    @Override
    public Retorno_MsgObj obtenerLista() {
        return perInscripcion.obtenerLista();
    }
    
    public Retorno_MsgObj obtenerListaByCurso(Long PerCod, Long CurCod) {
        return perInscripcion.obtenerListaByCurso(PerCod, CurCod);
    }
    
    public Retorno_MsgObj obtenerListaByPlan(Long PerCod, Long PlaEstCod) {
        return perInscripcion.obtenerListaByPlan(PerCod, PlaEstCod);
    }
    
    public Retorno_MsgObj obtenerListaByAlumno(Long PerCod) {
        return perInscripcion.obtenerListaByAlumno(PerCod);
    }
    
}
