/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;


import Entidad.Periodo;
import Enumerado.Modo;
import Enumerado.TipoMensaje;
import Enumerado.TipoPeriodo;
import Logica.LoPeriodo;
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

/**
 *
 * @author alvar
 */
public class ABM_Periodo extends HttpServlet {
    private final LoPeriodo loPeriodo           = LoPeriodo.GetInstancia();
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
            
            HttpSession session=request.getSession(); 
            
            String action   = request.getParameter("pAction");
            Modo mode = Modo.valueOf(action);
            String retorno  = "";

        
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
    
    private String AgregarDatos(HttpServletRequest request)
        {
        
            mensaje    = new Mensajes("Error al guardar datos", TipoMensaje.ERROR);

            try
            {

                error           = false;
                
                Periodo periodo = this.ValidarPeriodo(request, null);

                //------------------------------------------------------------------------------------------
                //Guardar cambios
                //------------------------------------------------------------------------------------------

                if(!error)
                {
                    Retorno_MsgObj retornoObj = (Retorno_MsgObj) loPeriodo.guardar(periodo);
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

    private String ActualizarDatos(HttpServletRequest request)
    {
        mensaje    = new Mensajes("Error al guardar datos", TipoMensaje.ERROR);
        try
        {

            error           = false;
            
            Periodo periodo = this.ValidarPeriodo(request, null);

            //------------------------------------------------------------------------------------------
            //Guardar cambios
            //------------------------------------------------------------------------------------------

            Retorno_MsgObj retorno = new Retorno_MsgObj();
            
            if(!error)
            {
                retorno     = (Retorno_MsgObj) loPeriodo.actualizar(periodo);
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

    private String EliminarDatos(HttpServletRequest request)
    {
        error       = false;
        mensaje    = new Mensajes("Error al eliminar", TipoMensaje.ERROR);
        try
        {

            Periodo periodo = this.ValidarPeriodo(request, null);

            if(!error)
            {
                mensaje = ((Retorno_MsgObj) loPeriodo.eliminar(periodo)).getMensaje();
            }
            
        }
        catch(Exception ex)
        {
            mensaje = new Mensajes("Error al Eliminar: " + ex.getMessage(), TipoMensaje.ERROR);
            throw ex;
        }



        return utilidades.ObjetoToJson(mensaje);
    }
        
    private Periodo ValidarPeriodo(HttpServletRequest request, Periodo periodo)
    {
        
        if(periodo == null)
        {
            periodo   = new Periodo();
        }

            
                String 	PerCod      = request.getParameter("pPerCod");
                String 	PerTpo      = request.getParameter("pPerTpo");
                String 	PerVal      = request.getParameter("pPerVal");
                String 	PerFchIni   = request.getParameter("pPerFchIni");
                
                
                //------------------------------------------------------------------------------------------
                //Validaciones
                //------------------------------------------------------------------------------------------

                //TIPO DE DATO

                


                //Sin validacion
                
                if(PerCod != null) periodo = ((Periodo) loPeriodo.obtener(Long.valueOf(PerCod)).getObjeto());
                
                if(PerTpo != null) periodo.setPerTpo(TipoPeriodo.fromCode(Integer.valueOf(PerTpo)));
                
                if(PerVal != null) periodo.setPerVal(Double.valueOf(PerVal));
                
                if(PerFchIni != null) periodo.setPerFchIni(Date.valueOf(PerFchIni));
                
                return periodo;
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
