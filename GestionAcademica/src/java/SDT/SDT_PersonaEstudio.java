/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SDT;

import Entidad.Escolaridad;
import Entidad.Inscripcion;
import java.util.ArrayList;

/**
 *
 * @author alvar
 */
public class SDT_PersonaEstudio {
    private Inscripcion inscripcion;
    private ArrayList<Escolaridad> escolaridad;

    public SDT_PersonaEstudio() {
        this.escolaridad = new ArrayList<>();
    }


    public Inscripcion getInscripcion() {
        return inscripcion;
    }

    public void setInscripcion(Inscripcion inscripcion) {
        this.inscripcion = inscripcion;
    }

    public ArrayList<Escolaridad> getEscolaridad() {
        return escolaridad;
    }

    public void setEscolaridad(ArrayList<Escolaridad> escolaridad) {
        this.escolaridad = escolaridad;
    }

    
    
}
