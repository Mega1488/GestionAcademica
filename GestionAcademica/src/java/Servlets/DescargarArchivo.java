/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entidad.PeriodoEstudio;
import Entidad.PeriodoEstudioDocumento;
import Logica.LoPeriodo;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author alvar
 */
@WebServlet(name = "DescargarArchivo", urlPatterns = {"/DescargarArchivo"})
public class DescargarArchivo extends HttpServlet {

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
       try
       {
            PeriodoEstudioDocumento  periDocumento   = new PeriodoEstudioDocumento();
            LoPeriodo loPeriodo = LoPeriodo.GetInstancia();


            String 	PeriEstCod  = request.getParameter("pPeriEstCod");
            String 	DocCod      = request.getParameter("pDocCod");

            if(PeriEstCod != null) periDocumento.setPeriodo(((PeriodoEstudio) loPeriodo.EstudioObtener(Long.valueOf(PeriEstCod)).getObjeto()));

            if(DocCod != null) periDocumento = periDocumento.getPeriodo().getDocumentoById(Long.valueOf(DocCod));
        
            
            //------------------------------------------------------------------------------------------
            //Guardar cambios
            //------------------------------------------------------------------------------------------

            if(periDocumento != null)
            {
                        
                File fileToDownload = periDocumento.getArchivo();
                FileInputStream fileInputStream = new FileInputStream(fileToDownload);

                ServletOutputStream out = response.getOutputStream();   
                String mimeType =  new MimetypesFileTypeMap().getContentType(fileToDownload.getAbsolutePath()); 

                response.setContentType(mimeType); 
                response.setContentLength(fileInputStream.available());
                response.setHeader( "Content-Disposition", "attachment; filename=\""+ periDocumento.getDocNom() + "." + periDocumento.getDocExt() + "\"" );

                int c;
                while((c=fileInputStream.read()) != -1){
                 out.write(c);
                }
                out.flush();
                out.close();
                fileInputStream.close();
                
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
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
