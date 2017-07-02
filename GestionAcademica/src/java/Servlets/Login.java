/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Enumerado.NombreSesiones;
import Enumerado.TipoMensaje;
import Logica.LoPersona;
import Logica.Seguridad;
import Utiles.Mensajes;
import Utiles.Utilidades;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author alvar
 */
public class Login extends HttpServlet {
    
    private Utilidades utiles;

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
            
            utiles = Utilidades.GetInstancia();

            String retorno  = "";
            
            String action      = request.getParameter("pAction");
            
            if(action.equals("INICIAR"))
            {
                retorno = this.IniciarSesion(request);
            }
            
            if(action.equals("FINALIZAR"))
            {
                retorno = this.CerrarSesion(request);
            }
            
            out.println(retorno);
            
        }
    }
    
    
    private String IniciarSesion(HttpServletRequest request){
        String retorno  = "";
        String usr      = request.getParameter("pUser");
        String psw      = request.getParameter("pPass"); 

        Mensajes mensaje    = new Mensajes("Error al iniciar sesión", TipoMensaje.ERROR);
        LoPersona loPersona = LoPersona.GetInstancia();
        Seguridad seguridad = Seguridad.GetInstancia();

        System.err.println("B");


        if(loPersona.IniciarSesion(usr, seguridad.cryptWithMD5(psw)))
        {
            //Inicio correctamente
            HttpSession session=request.getSession(); 
            session.setAttribute(NombreSesiones.USUARIO.getValor(), usr);

            mensaje = new Mensajes("OK", TipoMensaje.MENSAJE);
        }
        else
        {
            System.err.println("D");

            //No inicio correctamente
            mensaje = new Mensajes("Usuario o contraseña incorrectos", TipoMensaje.ERROR);
        }

        System.err.println("F");

        retorno = utiles.ObjetoToJson(mensaje);

        System.err.println("Este es el retorno " + retorno);
        return retorno;
    }
    
    private String CerrarSesion(HttpServletRequest request){
        String retorno  = "";
        
        HttpSession session = request.getSession(); 
        String usuario      = (String) session.getAttribute(NombreSesiones.USUARIO.getValor());
        Mensajes mensaje    = new Mensajes("Error al cerrar sesión", TipoMensaje.ERROR);
        boolean resultado   = false;
        if(usuario != null)
        {
            if(!usuario.isEmpty())
            {
                session.removeAttribute(NombreSesiones.USUARIO.getValor());
                mensaje     = new Mensajes("OK", TipoMensaje.MENSAJE);
                resultado   = true;
            }
        }
        
        if(!resultado)
        {
            mensaje = new Mensajes("No se encontro una sesión iniciada", TipoMensaje.ERROR);
        }
        
        retorno = utiles.ObjetoToJson(mensaje);
        
        return retorno;
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
