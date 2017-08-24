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
import Utiles.Retorno_MsgObj;
import Utiles.Utilidades;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author aa
 */
public class ABM_Carrera extends HttpServlet {

    Mensajes mensaje    = new Mensajes("Error", TipoMensaje.ERROR);
    boolean error       = false; 
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
                
                case "POPUP_OBTENER":
                    retorno = this.PopUp_ObtenerDatos();
                break;

                case "POPUP_OBTENER_PLANES":
                    retorno = this.PopUp_ObtenerPlanesDatos(request);
                break;

                default:
                    break;
            }
            out.println(retorno);
        }
    }
    
    private String IngresarCarrera(HttpServletRequest request)
    {
        mensaje    = new Mensajes("Error al guardar datos", TipoMensaje.ERROR);

        try
        {

            error           = false;

            Carrera car = this.ValidarCarrera(request, null);

            //------------------------------------------------------------------------------------------
            //Guardar cambios
            //------------------------------------------------------------------------------------------

            if(!error)
            {
                Retorno_MsgObj retornoObj = (Retorno_MsgObj) loCarrera.guardar(car);
                mensaje    = retornoObj.getMensaje();
            }
        }
        catch(Exception ex)
        {
            mensaje = new Mensajes("Error al guardar: " + ex.getMessage(), TipoMensaje.ERROR);
            throw ex;
        }

        return utiles.ObjetoToJson(mensaje);
    } 
    
    private String ModificarCarrera(HttpServletRequest request)
    {
        mensaje    = new Mensajes("Error al guardar datos", TipoMensaje.ERROR);
        try
        {

            error           = false;
            
            Carrera car = this.ValidarCarrera(request, null);

            //------------------------------------------------------------------------------------------
            //Guardar cambios
            //------------------------------------------------------------------------------------------

            if(!error)
            {
                Retorno_MsgObj ret     = (Retorno_MsgObj) loCarrera.actualizar(car);
                mensaje = ret.getMensaje();
            }

             
            
        }
        catch(Exception ex)
        {
            mensaje = new Mensajes("Error al actualizar: " + ex.getMessage(), TipoMensaje.ERROR);
            throw ex;
        }

       
        return utiles.ObjetoToJson(mensaje);
       
    } 
    
    private String EliminarCarrera(HttpServletRequest request)
    {   
        error       = false;
        mensaje    = new Mensajes("Error al eliminar", TipoMensaje.ERROR);
        
        Carrera car = this.ValidarCarrera(request, null);
        
        if(!error)
        {
            Retorno_MsgObj ret  = (Retorno_MsgObj) loCarrera.eliminar(car);
            mensaje             = ret.getMensaje();
        }

        return utiles.ObjetoToJson(mensaje);
    }
    
    private String PopUp_ObtenerDatos()
    {
        String retorno = "";
        
        List<Object> lstCarrera   = loCarrera.obtenerLista().getLstObjetos();
        
        try
        {
            retorno = utiles.ObjetoToJson(lstCarrera);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return retorno;
    }
    
    private String PopUp_ObtenerPlanesDatos(HttpServletRequest request)
    {
        Carrera car = this.ValidarCarrera(request, null);
        return utiles.ObjetoToJson(car.getPlan());
    }
    
    private Carrera ValidarCarrera(HttpServletRequest request, Carrera car)
    {
        if(car == null)
        {
            car   = new Carrera();
        }

            
                String cod  = request.getParameter("pCod");
                String nom  = request.getParameter("pNom");
                String Dsc  = request.getParameter("pDsc");
                String Fac  = request.getParameter("pfac");
                String Crt  = request.getParameter("pCrt");
                
                
                //------------------------------------------------------------------------------------------
                //Validaciones
                //------------------------------------------------------------------------------------------

                //TIPO DE DATO

                


                //Sin validacion
                if(cod != null) if(!cod.isEmpty()) car = (Carrera) loCarrera.obtener(Long.valueOf(cod)).getObjeto();
                
                if(nom != null) if(!nom.isEmpty()) car.setCarNom(nom);
                if(Dsc != null) if(!Dsc.isEmpty()) car.setCarDsc(Dsc);
                if(Fac != null) if(!Fac.isEmpty()) car.setCarFac(Fac);
                if(Crt != null) if(!Crt.isEmpty()) car.setCarCrt(Crt);

                
                
        return car;
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
