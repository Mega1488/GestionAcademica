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
        
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al ingresar la Carrera", TipoMensaje.ERROR));
        
        if (nom != "")
        {
            Carrera pC = new Carrera();
            pC.setCarNom(nom);
            pC.setCarDsc(Dsc);
            pC.setCarFac(Fac);
            pC.setCarCrt(Crt);
            
            retorno = (Retorno_MsgObj)loCarrera.guardar(pC);
            
            mensaje = retorno.getMensaje();
        }
        else
        {
            mensaje = retorno.getMensaje();
        }
        return utiles.ObjetoToJson(mensaje);
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
            pC.setCarCod(Long.valueOf(cod));
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
        retorno = utiles.ObjetoToJson(mensaje);
        
        return retorno;
    } 
    
    private String EliminarCarrera(HttpServletRequest request)
    {   
        String cod              = request.getParameter("pCod");
        
        Retorno_MsgObj retorno = loCarrera.obtener(Long.valueOf(cod));
        error = retorno.getMensaje().getTipoMensaje() == TipoMensaje.ERROR || retorno.getObjeto() == null;
        
        if (cod != "")
        {
            if(!error)
            {
                retorno = (Retorno_MsgObj)loCarrera.eliminar((Carrera)retorno.getObjeto());
            }
            mensaje = retorno.getMensaje();
        }
        else
        {
            mensaje = retorno.getMensaje();
//            mensaje = new Mensajes("No se logró eliminar la carrera", TipoMensaje.ERROR);
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
