/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import Entidad.Persona;
import java.util.List;

/**
 *
 * @author alvar
 */
public interface InPersona {
    Object guardar(Persona pObjeto);
    void actualizar(Persona pObjeto);
    void eliminar(Persona pObjeto);
    Persona obtener(Object pCodigo);
    Persona obtenerByMdlUsr(String pMdlUsr);
    List<Persona> obtenerLista();
}
