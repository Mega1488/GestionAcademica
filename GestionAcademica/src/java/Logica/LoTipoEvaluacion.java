/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.TipoEvaluacion;
import Interfaz.InTipoEvaluacion;
import Persistencia.PerTipoEvaluacion;
import Utiles.Retorno_MsgObj;
import java.util.List;

/**
 *
 * @author alvar
 */
public class LoTipoEvaluacion implements InTipoEvaluacion{

    private static LoTipoEvaluacion instancia;
    private PerTipoEvaluacion perTpoEval;

    private LoTipoEvaluacion() {
        perTpoEval  = new PerTipoEvaluacion();
    }
    
    public static LoTipoEvaluacion GetInstancia(){
        if (instancia==null)
        {
            instancia   = new LoTipoEvaluacion();
        }

        return instancia;
    }
    

    @Override
    public Object guardar(TipoEvaluacion pTipoEvaluacion) {
        return perTpoEval.guardar(pTipoEvaluacion);
    }

    @Override
    public Object actualizar(TipoEvaluacion pTipoEvaluacion) {
        return perTpoEval.actualizar(pTipoEvaluacion);
    }

    @Override
    public Object eliminar(TipoEvaluacion pTipoEvaluacion) {
        return perTpoEval.eliminar(pTipoEvaluacion);
    }

    @Override
    public Retorno_MsgObj obtener(Long pTpoEvlCod) {
        return perTpoEval.obtener(pTpoEvlCod);
    }

    @Override
    public Retorno_MsgObj obtenerLista() {
        return perTpoEval.obtenerLista();
    }
    
}
