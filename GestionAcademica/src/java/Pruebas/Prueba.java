/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pruebas;

import Entidad.Parametro;
import Enumerado.Constantes;
import Logica.LoConsumirServicioMdl;
import Logica.LoIniciar;
import Logica.LoParametro;
import Moodle.MoodleCourse;
import Moodle.MoodleRestCourse;
import Moodle.MoodleRestEnrol;
import Moodle.MoodleRestEnrolException;
import Moodle.MoodleRestException;
import Moodle.MoodleUser;
import Moodle.MoodleUserRoleException;
import Moodle.OptionParameter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author alvar
 */
public class Prueba extends HttpServlet {

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
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Prueba</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Prueba at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
            
            this.Probar();
        }
    }
    
    public void Probar(){
        
        try {
            LoParametro loParam = LoParametro.GetInstancia();
            Parametro parametro = loParam.obtener(1);
            
            MoodleRestEnrol mdlEnrol = new  MoodleRestEnrol();
            MoodleRestCourse mdlCourse = new MoodleRestCourse();
            
            MoodleCourse[] lstCurso = mdlCourse.__getAllCourses(parametro.getParUrlMdl()+ Constantes.URL_FOLDER_SERVICIO_MDL.getValor(), parametro.getParMdlTkn());
            
            for(int i = 0; i<lstCurso.length; i++)
            {
                MoodleCourse curso = lstCurso[i];
                System.err.println("curso: " + curso.getIdNumber() + " - "  + curso.getId() + " - " + curso.getFullname());
                
                OptionParameter[] options = null;
                
                MoodleUser[] lstUser = mdlEnrol.__getEnrolledUsers(parametro.getParUrlMdl()+ Constantes.URL_FOLDER_SERVICIO_MDL.getValor(), parametro.getParMdlTkn(), curso.getId(), options);
                
                for(int e = 0; e < lstUser.length; e++)
                {
                    MoodleUser user = lstUser[e];
                    System.err.println("Usuario " + user.getFullname());
                    System.err.println("Tamaño: " + user.getRoles().size());
                }
                
            }
            
        } catch (MoodleRestException ex) {
            Logger.getLogger(Prueba.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Prueba.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MoodleUserRoleException ex) {
            Logger.getLogger(Prueba.class.getName()).log(Level.SEVERE, null, ex);
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
