/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entidad.Calendario;
import Entidad.CalendarioDocente;
import Entidad.Persona;
import Enumerado.EstadoCalendarioEvaluacion;
import Enumerado.Modo;
import Enumerado.NombreSesiones;
import Enumerado.TipoMensaje;
import Logica.LoCalendario;
import Logica.LoPersona;
import Utiles.Mensajes;
import Utiles.Retorno_MsgObj;
import Utiles.Utilidades;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.jasper.Constants;

/**
 *
 * @author alvar
 */
public class ABM_CalendarioDocente extends HttpServlet {
    LoCalendario loCalendario               = LoCalendario.GetInstancia();
    private final Utilidades utilidades     = Utilidades.GetInstancia();
    private Mensajes mensaje                = new Mensajes("Error", TipoMensaje.ERROR);
    private Boolean error                   = false;
    private Persona perUsuario;


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
            
            
            String action   = request.getParameter("pAction");
            String retorno  = "";
            
            HttpSession session=request.getSession(); 
            String usuario  = (String) session.getAttribute(NombreSesiones.USUARIO.getValor());            
            if(usuario != null)  perUsuario = (Persona) LoPersona.GetInstancia().obtenerByMdlUsr(usuario).getObjeto();

            
            switch(action)
            {
                
                case "INSERT":
                    retorno = this.AgregarDatos(request);
                break;
                
                case "UPDATE":
                    retorno = this.ActualizarDatos(request);
                break;
                
                case "DELETE":
                    retorno = this.EliminarDatos(request);
                break;
                
                case "OBTENER":
                    retorno = this.ObtenerDatos(request);
                break;
                
            }

            out.println(retorno);
            
        }
    }
    
    
    
     private String AgregarDatos(HttpServletRequest request)
    {
        mensaje    = new Mensajes("Error al guardar datos", TipoMensaje.ERROR);

        try
        {

            error           = false;

            CalendarioDocente calDocente = this.ValidarCalendarioDocente(request, null);
            
            

            //------------------------------------------------------------------------------------------
            //Guardar cambios
            //------------------------------------------------------------------------------------------

            if(!error)
            {
                Retorno_MsgObj retornoObj = (Retorno_MsgObj) loCalendario.DocenteAgregar(calDocente);
                mensaje    = retornoObj.getMensaje();
            }
        }
        catch(Exception ex)
        {
            mensaje = new Mensajes("Error al guardar: " + ex.getMessage(), TipoMensaje.ERROR);
            throw ex;
        }

        String retorno = utilidades.ObjetoToJson(mensaje);

        return retorno;
    }
    
    private String EliminarDatos(HttpServletRequest request)
    {
        error       = false;
        mensaje    = new Mensajes("Error al eliminar", TipoMensaje.ERROR);
        try
        {
            CalendarioDocente calDocente = this.ValidarCalendarioDocente(request, null);
            
            Retorno_MsgObj retorno = (Retorno_MsgObj) loCalendario.DocenteEliminar(calDocente);
            
            mensaje = retorno.getMensaje();

        }
        catch(Exception ex)
        {
            mensaje = new Mensajes("Error al Eliminar: " + ex.getMessage(), TipoMensaje.ERROR);
            throw ex;
        }

       return utilidades.ObjetoToJson(mensaje);
    }
    
    private String ActualizarDatos(HttpServletRequest request){
        mensaje    = new Mensajes("Error al guardar datos", TipoMensaje.ERROR);

        try
        {

            error           = false;
            
            CalendarioDocente calDocente = ValidarCalendarioDocente(request, null);
            
            //------------------------------------------------------------------------------------------
            //Guardar cambios
            //------------------------------------------------------------------------------------------

            if(!error)
            {
                Retorno_MsgObj retornoObj = (Retorno_MsgObj) loCalendario.DocenteActualizar(calDocente);
                    
                mensaje    = retornoObj.getMensaje();
            }
        }
        catch(Exception ex)
        {
            mensaje = new Mensajes("Error al actualizar: " + ex.getMessage(), TipoMensaje.ERROR);
            throw ex;
        }

        String retorno = utilidades.ObjetoToJson(mensaje);

        return retorno;
    }
    
    private String ObtenerDatos(HttpServletRequest request)
    {
        error       = false;
        CalendarioDocente calDocente = new CalendarioDocente();
        
        try
        {
            calDocente = this.ValidarCalendarioDocente(request, null);
            calDocente = calDocente.getCalendario().getDocenteById(calDocente.getCalDocCod());
            
        }
        catch(Exception ex)
        {
            mensaje = new Mensajes("Error al obtener: " + ex.getMessage(), TipoMensaje.ERROR);
            throw ex;
        }

       return utilidades.ObjetoToJson(calDocente);
    }
    
    private CalendarioDocente ValidarCalendarioDocente(HttpServletRequest request, CalendarioDocente calDocente)
    {
        if(calDocente == null)
        {
            calDocente   = new CalendarioDocente();
        }

        String CalDocCod    = request.getParameter("pCalDocCod");
        String DocPerCod    = request.getParameter("pDocPerCod");
        String CalCod       = request.getParameter("pCalCod");
        
        //------------------------------------------------------------------------------------------
        //Validaciones
        //------------------------------------------------------------------------------------------

        //TIPO DE DATO



        //Sin validacion
        if(CalCod != null) calDocente.setCalendario((Calendario) loCalendario.obtener(Long.valueOf(CalCod)).getObjeto());
        
        if(CalDocCod != null)                calDocente.setCalDocCod(Long.valueOf(CalDocCod)); 
        if(calDocente.getCalDocCod() != null) calDocente = calDocente.getCalendario().getDocenteById(calDocente.getCalDocCod());
        
        if(DocPerCod != null) calDocente.setDocente((Persona) LoPersona.GetInstancia().obtener(Long.valueOf(DocPerCod)).getObjeto());;
        
        return calDocente;
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