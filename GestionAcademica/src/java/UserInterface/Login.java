/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface;

import Enumerado.TipoMensaje;
import Logica.LoPersona;
import Logica.Seguridad;
import Utiles.Mensajes;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author alvar
 */
public class Login extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */

            String retorno  = "";
            String usr      = request.getParameter("pUser");
            String psw      = request.getParameter("pPass"); 
            
            Mensajes mensaje    = new Mensajes();
            LoPersona loPersona = LoPersona.GetInstancia();
            Seguridad seguridad = Seguridad.GetInstancia();
            
            System.err.println("B");
            
            
            if(loPersona.IniciarSesion(usr, seguridad.cryptWithMD5(psw)))
            {
                System.err.println("C");
            
                //Inicio correctamente
                mensaje = new Mensajes("OK", TipoMensaje.MENSAJE);
            }
            else
            {
                System.err.println("D");
            
                //No inicio correctamente
                mensaje = new Mensajes("Usuario o contraseña incorrectos", TipoMensaje.ERROR);
            }
            
            System.err.println("F");
            
           ObjectMapper mapper = new ObjectMapper();

            try {
                // convert user object to json string and return it 
                retorno = mapper.writeValueAsString(mensaje);
            }

              // catch various errors
              catch (JsonGenerationException e) {
                e.printStackTrace();
            } 
              catch (JsonMappingException e) {
                e.printStackTrace();
            }
        
            System.err.println("G");
            
            System.err.println("Este es el retorno " + retorno);
            out.println(retorno);
            
        }
    }

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
