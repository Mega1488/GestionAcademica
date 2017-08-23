/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;


import Entidad.Sincronizacion;
import Enumerado.TipoMensaje;
import Logica.LoSincronizacion;
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
public class ABM_Sincronizacion extends HttpServlet {
    private final LoSincronizacion loSincronizacion           = LoSincronizacion.GetInstancia();
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

            switch(action)
            {

                case "DEPURAR":
                    retorno = this.Depurar();
                break;

                case "EJECUTAR":
                    retorno = this.Ejecutar();
                break;

                case "DELETE":
                    retorno = this.EliminarDatos(request);
                break;
                
                case "INC_SELECCIONAR":
                    retorno = this.IncSeleccionObjeto(request);
                break;
                
                case "INC_PROCESAR":
                    retorno = this.IncProcesar(request);
                break;

            }

            out.println(retorno);
            
        }
        
    }
    
    private String Depurar(){
        Retorno_MsgObj retornoObj = (Retorno_MsgObj) loSincronizacion.Depurar();
        if(!retornoObj.SurgioError())
        {
            mensaje    = new Mensajes("Depuración finalizada", TipoMensaje.MENSAJE);
        }
        else
        {
            mensaje    = retornoObj.getMensaje();
        }
        

        return utilidades.ObjetoToJson(mensaje);
    }
    
    private String Ejecutar(){
        loSincronizacion.Sincronizar();
        mensaje    = new Mensajes("Se inició la sincronización", TipoMensaje.MENSAJE);
        return utilidades.ObjetoToJson(mensaje);
    }
    
    private String IncSeleccionObjeto(HttpServletRequest request){
        mensaje    = new Mensajes("Error al seleccionar objeto", TipoMensaje.ERROR);
        try
        {

            error           = false;

            Sincronizacion sincronizacion = this.ValidarSincronizacion(request, null);

            //------------------------------------------------------------------------------------------
            //Guardar cambios
            //------------------------------------------------------------------------------------------

            if(!error)
            {
                String IncCod       = request.getParameter("pIncCod");
                String IncObjCod    = request.getParameter("pIncObjCod");
                
                Retorno_MsgObj retornoObj = (Retorno_MsgObj) loSincronizacion.InconsistenciaSeleccionarObjeto(sincronizacion, Long.valueOf(IncCod), Long.valueOf(IncObjCod));
                mensaje    = retornoObj.getMensaje();
            }
        }
        catch(Exception ex)
        {
            mensaje = new Mensajes("Error al eliminar: " + ex.getMessage(), TipoMensaje.ERROR);
            throw ex;
        }

        return utilidades.ObjetoToJson(mensaje);
    }
    
    private String EliminarDatos(HttpServletRequest request){
        mensaje    = new Mensajes("Error al eliminar datos", TipoMensaje.ERROR);

        try
        {

            error           = false;

            Sincronizacion sincronizacion = this.ValidarSincronizacion(request, null);

            //------------------------------------------------------------------------------------------
            //Guardar cambios
            //------------------------------------------------------------------------------------------

            if(!error)
            {
                Retorno_MsgObj retornoObj = (Retorno_MsgObj) loSincronizacion.eliminar(sincronizacion);
                mensaje    = retornoObj.getMensaje();
            }
        }
        catch(Exception ex)
        {
            mensaje = new Mensajes("Error al eliminar: " + ex.getMessage(), TipoMensaje.ERROR);
            throw ex;
        }

        String retorno = utilidades.ObjetoToJson(mensaje);

        return retorno;
    }
    
    private String IncProcesar(HttpServletRequest request){
        mensaje    = new Mensajes("Error al procesar datos", TipoMensaje.ERROR);

        try
        {

            error           = false;

            Sincronizacion sincronizacion = this.ValidarSincronizacion(request, null);

            //------------------------------------------------------------------------------------------
            //Guardar cambios
            //------------------------------------------------------------------------------------------

            if(!error)
            {
                Retorno_MsgObj retornoObj = (Retorno_MsgObj) loSincronizacion.ProcesarInconsistencia(sincronizacion);
                mensaje    = retornoObj.getMensaje();
            }
        }
        catch(Exception ex)
        {
            mensaje = new Mensajes("Error al procesar: " + ex.getMessage(), TipoMensaje.ERROR);
            throw ex;
        }

        String retorno = utilidades.ObjetoToJson(mensaje);

        return retorno;
    }

    private Sincronizacion ValidarSincronizacion(HttpServletRequest request, Sincronizacion sincronizacion){
        
        if(sincronizacion == null)
        {
            sincronizacion   = new Sincronizacion();
        }
            
                String SncCod= request.getParameter("pSncCod");
                
                //------------------------------------------------------------------------------------------
                //Validaciones
                //------------------------------------------------------------------------------------------

                //TIPO DE DATO

                //Sin validacion
                
                if(SncCod != null) if(!SncCod.isEmpty()) sincronizacion = (Sincronizacion) loSincronizacion.obtener(Long.valueOf(SncCod)).getObjeto();
                
                return sincronizacion;
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