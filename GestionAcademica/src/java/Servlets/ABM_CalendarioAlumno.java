/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entidad.Calendario;
import Entidad.CalendarioAlumno;
import Entidad.Persona;
import Enumerado.EstadoCalendarioEvaluacion;
import Enumerado.Modo;
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

/**
 *
 * @author alvar
 */
public class ABM_CalendarioAlumno extends HttpServlet {
    LoCalendario loCalendario               = LoCalendario.GetInstancia();
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
            Modo mode = Modo.valueOf(action);
            String retorno  = "";

            
            switch(mode)
            {
                
                case INSERT:
                    retorno = this.AgregarDatos(request);
                break;
             /*   
                case UPDATE:
                    retorno = this.ActualizarDatos(request);
                break;
               */ 
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

            CalendarioAlumno calAlumno = this.ValidarCalendarioAlumno(request, null);

            //------------------------------------------------------------------------------------------
            //Guardar cambios
            //------------------------------------------------------------------------------------------

            if(!error)
            {
                Retorno_MsgObj retornoObj = (Retorno_MsgObj) loCalendario.AlumnoAgregar(calAlumno);
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
            CalendarioAlumno calAlumno = this.ValidarCalendarioAlumno(request, null);
            
            Retorno_MsgObj retorno = (Retorno_MsgObj) loCalendario.AlumnoEliminar(calAlumno);
            
            mensaje = retorno.getMensaje();

        }
        catch(Exception ex)
        {
            mensaje = new Mensajes("Error al Eliminar: " + ex.getMessage(), TipoMensaje.ERROR);
            throw ex;
        }

       return utilidades.ObjetoToJson(mensaje);
    }
     
    private CalendarioAlumno ValidarCalendarioAlumno(HttpServletRequest request, CalendarioAlumno calAlumno)
    {
        if(calAlumno == null)
        {
            calAlumno   = new CalendarioAlumno();
        }

        String CalAlCod= request.getParameter("pCalAlCod");
        String CalCod= request.getParameter("pCalCod");
        String AluPerCod= request.getParameter("pAluPerCod");
        String EvlCalVal= request.getParameter("pEvlCalVal");
        String EvlCalPerCod= request.getParameter("pEvlCalPerCod");
        String EvlCalEst= request.getParameter("pEvlCalEst");
        String EvlValPerCod= request.getParameter("pEvlValPerCod");
        String EvlCalObs= request.getParameter("pEvlCalObs");
        String EvlValObs= request.getParameter("pEvlValObs");
        String EvlCalFch= request.getParameter("pEvlCalFch");
        String EvlValFch= request.getParameter("pEvlValFch");

        //------------------------------------------------------------------------------------------
        //Validaciones
        //------------------------------------------------------------------------------------------

        //TIPO DE DATO




        //Sin validacion
        if(CalAlCod != null)        calAlumno.setCalAlCod(Long.valueOf(CalAlCod));
        if(CalCod != null)          calAlumno.setCalendario((Calendario) loCalendario.obtener(Long.valueOf(CalCod)).getObjeto());
        if(AluPerCod != null)       calAlumno.setAlumno((Persona) LoPersona.GetInstancia().obtener(Long.valueOf(AluPerCod)).getObjeto());
        if(EvlCalVal != null)       calAlumno.setEvlCalVal(Double.valueOf(EvlCalVal));
        if(EvlCalPerCod != null)    calAlumno.setEvlCalPor((Persona) LoPersona.GetInstancia().obtener(Long.valueOf(EvlCalPerCod)).getObjeto());
        if(EvlCalEst != null)       calAlumno.setEvlCalEst(EstadoCalendarioEvaluacion.fromCode(Integer.valueOf(EvlCalEst)));
        if(EvlValPerCod != null)    calAlumno.setEvlValPor((Persona) LoPersona.GetInstancia().obtener(Long.valueOf(EvlValPerCod)).getObjeto());
        if(EvlCalObs != null)       calAlumno.setEvlCalObs(EvlCalObs);
        if(EvlValObs != null)       calAlumno.setEvlValObs(EvlValObs);
        if(EvlCalFch != null)       calAlumno.setEvlCalFch(Date.valueOf(EvlCalFch));
        if(EvlValFch != null)       calAlumno.setEvlValFch(Date.valueOf(EvlValFch));


        return calAlumno;
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
