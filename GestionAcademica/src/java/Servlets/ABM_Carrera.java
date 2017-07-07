/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entidad.Carrera;
import Enumerado.TipoMensaje;
import Logica.LoCarrera;
import Utiles.Mensajes;
import Utiles.Utilidades;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author aa
 */
public class ABM_Carrera extends HttpServlet {

    Mensajes mensaje    = new Mensajes("Error", TipoMensaje.ERROR);
    String retorno;
    Date fecha = new Date();
    LoCarrera loCarrera = LoCarrera.GetInstancia();
    
    private final Utilidades utiles = Utilidades.GetInstancia();
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            String action   = request.getParameter("pAccion");
            String retorno  = "";
           
            switch(action)
            {
                case "INGRESAR":
                    retorno = this.IngresarCarrera(request);
                break;

                case "MODIFICAR":
                    retorno = this.ModificarCarrera(request);
                break;

                case "ELIMINAR":
                    retorno = this.EliminarCarrera(request);
                break;

                default:
                    break;
            }
            out.println(retorno);
        }
    }
    
    private String IngresarCarrera(HttpServletRequest request)
    {
        String nom          = request.getParameter("pNom");
        String Dsc          = request.getParameter("pDsc");
        String Fac          = request.getParameter("pfac");
        String Crt          = request.getParameter("pCrt");
        
        if (nom != "")
        {
            Carrera pC = new Carrera();
            pC.setCarNom(nom);
            pC.setCarDsc(Dsc);
            pC.setCarFac(Fac);
            pC.setCarCrt(Crt);
            
            loCarrera.guardar(pC);
            
            mensaje = new Mensajes("Se ingresó correctametne la Carrera ", TipoMensaje.MENSAJE);
        }
        else
        {
            mensaje = new Mensajes("Deberá ingresar un nombre a la Carrera", TipoMensaje.ERROR);
        }
        retorno = utiles.ObjetoToJson(mensaje);
        
        return retorno;
    } 
    
    private String ModificarCarrera(HttpServletRequest request)
    {
        String cod  = request.getParameter("pCod");
        String nom  = request.getParameter("pNom");
        String Dsc  = request.getParameter("pDsc");
        String Fac  = request.getParameter("pfac");
        String Crt  = request.getParameter("pCrt");
        
        if (nom != "")
        {
            Carrera pC = new Carrera();
            pC.setCarCod(Integer.valueOf(cod));
            pC.setCarNom(nom);
            pC.setCarDsc(Dsc);
            pC.setCarFac(Fac);
            pC.setCarCrt(Crt);
            
            loCarrera.actualizar(pC);
            
            mensaje = new Mensajes("Los nuevos datos de la Carrera fueron guardados", TipoMensaje.MENSAJE);
        }
        else
        {
            mensaje = new Mensajes("La carrera debe tener un Nombre", TipoMensaje.ERROR);
        }
        return retorno;
    } 
    
    private String EliminarCarrera(HttpServletRequest request)
    {   
        String cod  = request.getParameter("pCod");
        System.out.println("CARRERA: "+ cod);
        if (cod != "")
        {
            Carrera pC = new Carrera();
            pC.setCarCod(Integer.valueOf(cod));
            
            loCarrera.eliminar(pC);
            
            mensaje = new Mensajes("La carrera fue Eliminada", TipoMensaje.MENSAJE);
        }
        else
        {
            mensaje = new Mensajes("Hubieron problemas que impidieron eliminar la carrera", TipoMensaje.ERROR);
        }
        return retorno;
    }
    
//    private Carrera ValidarCarrera(HttpServletRequest request, Carrera car)
//    {
//        if(!car.getCarNom().isEmpty())
//        {
//            
//        }
//        else
//        {
//            
//        }        
//        return car;
//    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
