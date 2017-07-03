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
import Moodle.MoodleRestUser;
import Moodle.MoodleRestUserEnrolment;
import Moodle.MoodleRestUserEnrolmentException;
import Moodle.MoodleUser;
import Moodle.MoodleUserEnrolment;
import Moodle.MoodleUserRoleException;
import Moodle.OptionParameter;
import Moodle.Role;
import Moodle.UserRole;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        
        LoParametro loParam = LoParametro.GetInstancia();
        Parametro parametro = loParam.obtener(1);
        MoodleRestUser mdlUser = new MoodleRestUser();
        
        
        try {
            
            
            
            
            MoodleRestEnrol mdlEnrol    = new  MoodleRestEnrol();
            MoodleRestCourse mdlCourse  = new MoodleRestCourse();
            
            MoodleCourse[] lstCurso = mdlCourse.__getAllCourses(parametro.getParUrlMdl()+ Constantes.URL_FOLDER_SERVICIO_MDL.getValor(), parametro.getParMdlTkn());
            
            System.err.println("Cursos: " + lstCurso.length);
            
            
            for(int i = 0; i<lstCurso.length; i++)
            {
                MoodleCourse curso = lstCurso[i];
                
                MoodleUser[] lstUser = mdlEnrol.__getEnrolledUsers(parametro.getParUrlMdl()+ Constantes.URL_FOLDER_SERVICIO_MDL.getValor(), parametro.getParMdlTkn(), curso.getId(), null);
               
                if(lstUser != null)
                {
                    System.err.println("Curso: " + curso.getFullname() + " ID: " + curso.getId() + " Usuarios: " + lstUser.length);
                    
                        for(int e = 0; e < lstUser.length; e++)
                        {
                            MoodleUser mdlUsr = lstUser[e];
                            System.err.println("Usuario: " + mdlUsr.getFullname() + " Roles: " + mdlUsr.getRoles().size());
                            
                            ArrayList<UserRole> roles = mdlUsr.getRoles();
                            
                            for(UserRole userRole : roles)
                            {
                                System.err.println("Rol Nombre: " + userRole.getName());
                                System.err.println("Rol ShortNombre: " + userRole.getShortName());
                                System.err.println("Rol ID: " + userRole.getRoleId());
                                System.err.println("Rol Role: " + userRole.getRole());
                                System.err.println("Rol Role name: " + userRole.getRole().name());
                            }
                            
                        }
                }
                
            }   
/*
            OptionParameter[] options = null;
            
            MoodleUser[] lstUser = mdlEnrol.__getEnrolledUsers(parametro.getParUrlMdl()+ Constantes.URL_FOLDER_SERVICIO_MDL.getValor(), parametro.getParMdlTkn(), curso.getId(), options);
            
            for(int e = 0; e < lstUser.length; e++)
            {
            MoodleUser user = lstUser[e];
            System.err.println("Usuario " + user.getFullname());
            System.err.println("Roles: " + user.getRoles().size());
            
            
            MoodleUserEnrolment enrolUser = new MoodleUserEnrolment();
            enrolUser.setCourseId(curso.getId());
            enrolUser.setRoleId(1);
            enrolUser.setSuspend(0);
            enrolUser.setUserId(user.getId());
            MoodleUserEnrolment[] usersEnrol = {enrolUser};
            
            
            //MoodleRestUserEnrolment enrol = new MoodleRestUserEnrolment();
            //enrol.__enrolUsers(parametro.getParUrlMdl()+ Constantes.URL_FOLDER_SERVICIO_MDL.getValor(), parametro.getParMdlTkn(), usersEnrol);
            
            if(user.getEnrolledCourses().size()>0)
            {
            System.err.println("Course: " + user.getEnrolledCourses().get(0).getFullName());
            System.err.println("Course: " + user.getEnrolledCourses().get(0).getShortName());
            }
            
            }
            
            }
            */
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Prueba.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MoodleRestException ex) {
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
