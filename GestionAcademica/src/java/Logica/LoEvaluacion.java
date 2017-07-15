/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.Calendario;
import Entidad.CalendarioAlumno;
import Entidad.CalendarioDocente;
import Enumerado.TipoMensaje;
import Interfaz.InABMGenerico;
import Persistencia.PerEvaluacion;
import Utiles.Mensajes;
import Utiles.Retorno_MsgObj;
import java.util.Date;

/**
 *
 * @author alvar
 */
public class LoEvaluacion implements InABMGenerico{

    private static LoEvaluacion instancia;
    private final PerEvaluacion perEvaluacion;

    private LoEvaluacion() {
        perEvaluacion  = new PerEvaluacion();
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
        return perEvaluacion.guardar(pObjeto);
    }

    @Override
    public Object actualizar(Object pObjeto) {
        return perEvaluacion.actualizar(pObjeto);
    }

    @Override
    public Object eliminar(Object pObjeto) {
        return perEvaluacion.eliminar(pObjeto);
    }

    @Override
    public Retorno_MsgObj obtener(Object pObjeto) {
        return perEvaluacion.obtener(pObjeto);
    }

    @Override
    public Retorno_MsgObj obtenerLista() {
        return perEvaluacion.obtenerLista();
    }
    
    
}
