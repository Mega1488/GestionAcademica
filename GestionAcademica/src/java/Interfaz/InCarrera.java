/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import Entidad.Carrera;
import java.util.List;

/**
 *
 * @author aa
 */
public interface InCarrera {
    Object guardar(Carrera pObjeto);
    Object actualizar(Carrera pObjeto);
    Object eliminar(Carrera pObjeto);
    Carrera obtener(Carrera pCodigo);
    Carrera obtenerByMdlUsr(String pMdlUsr);
    List<Carrera> obtenerLista();
}
