/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.TipoEvaluacion;
import Interfaz.InTipoEvaluacion;
import Persistencia.PerTipoEvaluacion;
import java.util.List;

/**
 *
 * @author alvar
 */
public class LogTipoEvaluacion implements InTipoEvaluacion{

    private static LogTipoEvaluacion instancia;
    private static PerTipoEvaluacion perTpoEval;

    private LogTipoEvaluacion() {
    }
    
    public static LogTipoEvaluacion GetInstancia(){
        if (instancia==null)
        {
            instancia   = new LogTipoEvaluacion();
            perTpoEval  = new PerTipoEvaluacion();
        }

        return instancia;
    }
    

    @Override
    public int guardar(TipoEvaluacion pTipoEvaluacion) {
        return perTpoEval.guardar(pTipoEvaluacion);
    }

    @Override
    public void actualizar(TipoEvaluacion pTipoEvaluacion) {
        perTpoEval.actualizar(pTipoEvaluacion);
    }

    @Override
    public void eliminar(TipoEvaluacion pTipoEvaluacion) {
        perTpoEval.eliminar(pTipoEvaluacion);
    }

    @Override
    public TipoEvaluacion obtener(int pTpoEvlCod) {
        return perTpoEval.obtener(pTpoEvlCod);
    }

    @Override
    public List<TipoEvaluacion> obtenerLista() {
        return perTpoEval.obtenerLista();
    }
    
}
