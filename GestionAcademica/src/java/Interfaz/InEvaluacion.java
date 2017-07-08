/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import Entidad.Evaluacion;
import java.util.List;

/**
 *
 * @author alvar
 */
public interface InEvaluacion {
    Object guardar(Evaluacion pObjeto);
    Object actualizar(Evaluacion pObjeto);
    Object eliminar(Evaluacion pObjeto);
    Evaluacion obtener(Object pCodigo);
    List<Evaluacion> obtenerLista();
}
