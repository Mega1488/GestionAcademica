/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pruebas;

import Entidad.Curso;
import Entidad.Evaluacion;
import Enumerado.TipoMensaje;
import Logica.LoCurso;
import SDT.SDT_Notificacion;
import SDT.SDT_NotificacionDato;
import SDT.SDT_NotificacionNotification;
import Utiles.Retorno_MsgObj;
import Utiles.Utilidades;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sun.net.www.http.HttpClient;

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
           // final String apiKey = "AIzaSyA8YSrq0BeiiJzNS24VlBKvAR1c03rBi0c";
            URL url = new URL("https://fcm.googleapis.com/fcm/send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "key=AAAAciD0DaY:APA91bEvsL7m7l18MHBMXli8HQQUf-Hje7li6xa8SSwO-5XVxK5HAXKe5QBhiTCI0qArn-WsNG0-xqyjZjbR6xsHz-dzXuKLKcHrR629fT5iQegnGBm0Jqy08TKOsfdC7VDa1JIRFUgF");
            
            conn.setDoOutput(true);
            
            SDT_Notificacion notificacion = new SDT_Notificacion();
            notificacion.setTo("cluNmdH0708:APA91bHUZUrgE5ia18UIDxawwt_jnPwsP7bxbuyrAn7PT48x9eP3JmSUkavKe3q5yQq9PQOdqjePl0rcf47jxRtz2vLM50YUht5iEoz09V6idLX72oXIPhIxewQOwHSYvCvooOfILCTB");
            //notificacion.setData(new SDT_NotificacionDato("Esto es un mensaje"));

            notificacion.setNotification(new SDT_NotificacionNotification("adasd", "titulo", "icon"));
            
          //  System.err.println(Utiles.Utilidades.GetInstancia().ObjetoToJson(notificacion));
                
            String input = Utiles.Utilidades.GetInstancia().ObjetoToJson(notificacion);
            
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();
            os.close();
            
            int responseCode = conn.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + input);
            System.out.println("Response Code : " + responseCode);
            
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            
            // print result
            System.out.println(response.toString());
        } catch (MalformedURLException ex) {
            Logger.getLogger(Prueba.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(Prueba.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
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
