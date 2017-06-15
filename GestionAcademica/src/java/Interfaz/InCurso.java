/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import Entidad.Curso;
import java.util.List;

/**
 *
 * @author alvar
 */
public interface InCurso {
    int guardar(Curso pCurso);
    void actualizar(Curso pCurso);
    void eliminar(Curso pCurso);
    Curso obtener(int pCurCod);
    List<Curso> obtenerLista();
}
