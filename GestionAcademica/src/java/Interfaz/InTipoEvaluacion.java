/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import Entidad.TipoEvaluacion;
import java.util.List;

/**
 *
 * @author alvar
 */
public interface InTipoEvaluacion {
    int guardar(TipoEvaluacion pTipoEvaluacion);
    void actualizar(TipoEvaluacion pTipoEvaluacion);
    void eliminar(TipoEvaluacion pTipoEvaluacion);
    TipoEvaluacion obtener(int pTpoEvlCod);
    List<TipoEvaluacion> obtenerLista();
}
