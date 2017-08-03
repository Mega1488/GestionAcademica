/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;


import Entidad.Notificacion;
import Entidad.NotificacionConsulta;
import Entidad.Persona;
import Enumerado.Modo;
import Enumerado.TipoConsulta;
import Enumerado.TipoMensaje;
import Logica.LoNotificacion;
import Logica.LoPersona;
import Utiles.Mensajes;
import Utiles.Retorno_MsgObj;
import Utiles.Utilidades;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author alvar
 */
public class ABM_NotificacionConsulta extends HttpServlet {
    private final LoNotificacion loNotificacion           = LoNotificacion.GetInstancia();
    private final Utilidades utilidades     = Utilidades.GetInstancia();
    private Mensajes mensaje                = new Mensajes("Error", TipoMensaje.ERROR);
    private Boolean error                   = false;
    
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

            
            Modo mode = Modo.valueOf(action);


            switch(mode)
            {

                case INSERT:
                    retorno = this.AgregarDatos(request);
                break;

                case UPDATE:
                    retorno = this.ActualizarDatos(request);
                break;

                case DELETE:
                    retorno = this.EliminarDatos(request);
                break;

            }


            out.println(retorno);
            
        }
        
    }
    
    private String AgregarDatos(HttpServletRequest request){
        mensaje    = new Mensajes("Error al guardar datos", TipoMensaje.ERROR);

        try
        {

            error           = false;

            NotificacionConsulta consulta = this.ValidarNotificacionConsulta(request, null);

            //------------------------------------------------------------------------------------------
            //Guardar cambios
            //------------------------------------------------------------------------------------------

            if(!error)
            {
                Retorno_MsgObj retornoObj = (Retorno_MsgObj) loNotificacion.ConsultaAgregar(consulta);
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

    private String ActualizarDatos(HttpServletRequest request){
        mensaje    = new Mensajes("Error al guardar datos", TipoMensaje.ERROR);
        try
        {

            error           = false;
            
            NotificacionConsulta consulta = this.ValidarNotificacionConsulta(request, null);

            //------------------------------------------------------------------------------------------
            //Guardar cambios
            //------------------------------------------------------------------------------------------

            Retorno_MsgObj retorno = new Retorno_MsgObj();
            
            if(!error)
            {
                retorno     = (Retorno_MsgObj) loNotificacion.ConsultaActualizar(consulta);
            }

            mensaje = retorno.getMensaje(); 
            
        }
        catch(Exception ex)
        {
            mensaje = new Mensajes("Error al actualizar: " + ex.getMessage(), TipoMensaje.ERROR);
            throw ex;
        }

        String retorno = utilidades.ObjetoToJson(mensaje);

        return retorno;
    }

    private String EliminarDatos(HttpServletRequest request){
        error       = false;
        mensaje    = new Mensajes("Error al eliminar", TipoMensaje.ERROR);
        try
        {

            NotificacionConsulta consulta = this.ValidarNotificacionConsulta(request, null);

            if(!error)
            {
                mensaje = ((Retorno_MsgObj) loNotificacion.ConsultaEliminar(consulta)).getMensaje();
            }
            
        }
        catch(Exception ex)
        {
            mensaje = new Mensajes("Error al Eliminar: " + ex.getMessage(), TipoMensaje.ERROR);
            throw ex;
        }



        return utilidades.ObjetoToJson(mensaje);
    }
        
    private NotificacionConsulta ValidarNotificacionConsulta(HttpServletRequest request, NotificacionConsulta consulta){
        
        if(consulta == null)
        {
            consulta   = new NotificacionConsulta();
        }
            
                String NotCnsCod= request.getParameter("pNotCnsCod");
                String NotCnsSQL= request.getParameter("pNotCnsSQL");
                String NotCnsTpo= request.getParameter("pNotCnsTpo");
                String NotCod= request.getParameter("pNotCod");
                
                
                //------------------------------------------------------------------------------------------
                //Validaciones
                //------------------------------------------------------------------------------------------

                //TIPO DE DATO

                


                //Sin validacion
                
                if(NotCod != null) if(!NotCod.isEmpty()) consulta.setNotificacion((Notificacion) loNotificacion.obtener(Long.valueOf(NotCod)).getObjeto());
                
                if(NotCnsCod != null) if(!NotCnsCod.isEmpty() && consulta.getNotificacion() != null) consulta = consulta.getNotificacion().ObtenerConsultaByCod(Long.valueOf(NotCnsCod));
                
                
                if(NotCnsSQL != null) if(!NotCnsSQL.isEmpty()) consulta.setNotCnsSQL(NotCnsSQL);
                if(NotCnsTpo != null) if(!NotCnsTpo.isEmpty()) consulta.setNotCnsTpo(TipoConsulta.fromCode(Integer.valueOf(NotCnsTpo)));
                
                return consulta;
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
