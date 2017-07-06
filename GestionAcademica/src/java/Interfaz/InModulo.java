/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import Entidad.Modulo;
import java.util.List;

/**
 *
 * @author alvar
 */
public interface InModulo {
    Object guardar(Modulo pObjeto);
    Object actualizar(Modulo pObjeto);
    Object eliminar(Modulo pObjeto);
    Modulo obtener(Object pCodigo);
    List<Modulo> obtenerLista();
}
