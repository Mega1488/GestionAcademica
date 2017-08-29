/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dominio;

import java.util.ArrayList;

/**
 *
 * @author alvar
 */
public class Sitios {
    private static Sitios instancia;
    
    private final ArrayList<String> lstSinSeguridad;
    private final ArrayList<String> lstDocente;
    private final ArrayList<String> lstAlumno;
   
    private Sitios() {
        lstSinSeguridad = new ArrayList<>();
        lstAlumno = new ArrayList<>();
        lstDocente = new ArrayList<>();
        
        CargarSinSeguridad();
        CargarAlumno();
        CargarDocente();
    }
    
    public static Sitios GetInstancia(){
        if (instancia==null)
        {
            instancia   = new Sitios();
        }

        return instancia;
    }
    
    private void CargarSinSeguridad(){
        lstSinSeguridad.add("index.jsp");
        lstSinSeguridad.add("Error.jsp");
    }
    
    private void CargarAlumno(){
        lstAlumno.add("Estudios.jsp");
        lstAlumno.add("Evaluaciones.jsp");
        lstAlumno.add("Solicitudes.jsp");
        lstAlumno.add("Escolaridad.jsp");
        lstAlumno.add("pswChange.jsp");
    }

    private void CargarDocente(){
        lstDocente.add("EstudiosDictados.jsp");
        lstDocente.add("EstudioDocumentos.jsp");
        lstDocente.add("EvalPendientes.jsp");
        lstDocente.add("CalificarAlumnos.jsp");
        lstDocente.add("pswChange.jsp");
    }

    public ArrayList<String> getLstSinSeguridad() {
        return lstSinSeguridad;
    }

    public ArrayList<String> getLstDocente() {
        return lstDocente;
    }


    public ArrayList<String> getLstAlumno() {
        return lstAlumno;
    }
    
    
}
