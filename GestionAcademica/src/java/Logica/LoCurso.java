/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.Curso;
import Persistencia.PerCurso;
import java.util.List;

/**
 *
 * @author alvar
 */
public class LoCurso implements Interfaz.InCurso{
    
    private static LoCurso instancia;
    private PerCurso perCurso;

    private LoCurso() {
        perCurso  = new PerCurso();
    }
    
    public static LoCurso GetInstancia(){
        if (instancia==null)
        {
            instancia   = new LoCurso();
            
        }

        return instancia;
    }

    @Override
    public int guardar(Curso pCurso) {
        return perCurso.guardar(pCurso);
    }

    @Override
    public void actualizar(Curso pCurso) {
        perCurso.actualizar(pCurso);
    }

    @Override
    public void eliminar(Curso pCurso) {
        perCurso.eliminar(pCurso);
    }

    @Override
    public Curso obtener(int pCurCod) {
        return perCurso.obtener(pCurCod);
    }

    @Override
    public List<Curso> obtenerLista() {
        return perCurso.obtenerLista();
    }
    
    
}
