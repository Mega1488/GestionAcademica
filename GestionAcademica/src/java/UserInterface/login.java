/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author alvar
 */
public class login extends HttpServlet {

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
             
            //String urlstring = "http://192.168.0.106/alvaro_ws/prueba.php";
            String urlstring = "http://192.168.0.106/login/index.php";
            
            String usuario  = request.getParameter("username");
            String psw      = request.getParameter("password");
            String error    = request.getParameter("errorcode");
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet login</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet login at " + request.getContextPath() + "</h1>");
            
        
                       out.println("<form id='myForm' action='" + urlstring + "' method='post'>");
                       
                       out.println("<input type='hidden' name='username' value='"+usuario+"'>");
                       out.println("<input type='hidden' name='password' value='"+psw+"'>");
                        
                       out.println("</form>");
                       out.println("<script type='text/javascript'>");
                       out.println("     document.getElementById('myForm').submit();");
                       out.println("</script>");
        

            out.println("</body>");
            out.println("</html>");
            
            try{
                
                
                System.err.println("Usuario: " + usuario);
                System.err.println("psw: " + psw);
                System.err.println("error: " + error);
                
                
                //RequestDispatcher dispatcher=getServletContext().getRequestDispatcher( "http://192.168.0.106/login/index.php" );
                //response.sendRedirect("http://192.168.0.106/login/index.php");
                
                //response.set
              //  response.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
               // response.setHeader("username", "username");
               // response.sendRedirect("http://192.168.0.106/alvaro_ws/prueba.php");
                

                //dispatcher.forward( request, response );
                
                
                // make the connection
                   
                
                
                
                
                //String urlstring = "http://192.168.0.106/alvaro_ws/prueba.php";
                
  /*              
                    String parameters = "username="+usuario+"&password="+psw;
                    URL url = new URL(urlstring);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                    // post the parameters
                    conn.setDoOutput(true);
                    OutputStreamWriter wr;
                    wr = new OutputStreamWriter(conn.getOutputStream()); 
                    wr.write(parameters);
                    wr.flush();
                    wr.close();

                    // now let's get the results
                    conn.connect(); // throws IOException
                    int responseCode = conn.getResponseCode();  // 200, 404, etc
                    String responseMsg = conn.getResponseMessage(); // OK, Forbidden, etc
                    BufferedReader br = new BufferedReader(
                       new InputStreamReader(conn.getInputStream()));
                    StringBuffer results = new StringBuffer();
                    String oneline;
                    while ( (oneline = br.readLine()) != null) {
                      results.append(oneline);
                      System.err.println("cc" + oneline.toString());
                    
                    }
                    br.close();
                    
                    
                    System.err.println("aa" + responseCode);
                    System.err.println("bb" + responseMsg);
                   
*/                    
         
                

            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
            
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
