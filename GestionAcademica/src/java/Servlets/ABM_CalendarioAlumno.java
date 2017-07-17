/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entidad.Calendario;
import Entidad.CalendarioAlumno;
import Entidad.Escolaridad;
import Entidad.Persona;
import Enumerado.EstadoCalendarioEvaluacion;
import Enumerado.Modo;
import Enumerado.NombreSesiones;
import Enumerado.TipoMensaje;
import Logica.LoCalendario;
import Logica.LoEscolaridad;
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
public class ABM_CalendarioAlumno extends HttpServlet {
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
                
                case "VALIDAR":
                    retorno = this.ValidarCalificacion(request);
                break;
                
                case "TO_VALIDAR":
                    retorno = this.toValidacion(request);
                break;
                
                case "TO_CORRECCION":
                    retorno = this.toCorreccion(request);
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
    
    private String ActualizarDatos(HttpServletRequest request){
        mensaje    = new Mensajes("Error al guardar datos", TipoMensaje.ERROR);

        try
        {

            error           = false;
            
            CalendarioAlumno calAlumno = ValidarCalendarioAlumno(request, null);
            
            if(calAlumno.getEvlCalVal() == null)
            {
                error = true;
                mensaje = new Mensajes("No se recibió una calificación", TipoMensaje.ERROR);
            }
            
            //------------------------------------------------------------------------------------------
            //Guardar cambios
            //------------------------------------------------------------------------------------------

            if(!error)
            {
                Retorno_MsgObj retornoObj = (Retorno_MsgObj) loCalendario.AlumnoActualizar(calAlumno);
                    
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
        CalendarioAlumno calAlumno = new CalendarioAlumno();
        
        try
        {
            calAlumno = this.ValidarCalendarioAlumno(request, null);
            calAlumno = calAlumno.getCalendario().getAlumnoById(calAlumno.getCalAlCod());
            
        }
        catch(Exception ex)
        {
            mensaje = new Mensajes("Error al obtener: " + ex.getMessage(), TipoMensaje.ERROR);
            throw ex;
        }

       return utilidades.ObjetoToJson(calAlumno);
    }
    
    private String ValidarCalificacion(HttpServletRequest request)
    {
        mensaje    = new Mensajes("Error al guardar datos", TipoMensaje.ERROR);

        try
        {

            error           = false;
            
            CalendarioAlumno calAlumno = ValidarCalendarioAlumno(request, null);
            
            if(calAlumno.getEvlCalVal() == null)
            {
                error = true;
                mensaje = new Mensajes("No se recibió una calificación", TipoMensaje.ERROR);
            }
            
            //------------------------------------------------------------------------------------------
            //Guardar cambios
            //------------------------------------------------------------------------------------------

            if(!error)
            {
                if(perUsuario != null) calAlumno.setEvlValPor(perUsuario);
                
                calAlumno.setEvlValFch(new java.util.Date());
                calAlumno.setEvlCalEst(EstadoCalendarioEvaluacion.VALIDADO);
                
                Retorno_MsgObj retornoObj = (Retorno_MsgObj) loCalendario.AlumnoActualizar(calAlumno);
                
                mensaje    = retornoObj.getMensaje();
                
                if(calAlumno.getCalendario().getEvaluacion().getTpoEvl().getTpoEvlExm())
                {
                    Escolaridad escolaridad = new Escolaridad();
                    
                    escolaridad.setEscFch(new java.util.Date());
                    if(perUsuario != null) escolaridad.setIngresadaPor(perUsuario);
                    if(calAlumno.getAlumno() != null) escolaridad.setAlumno(calAlumno.getAlumno());
                    if(calAlumno.getEvlCalVal() != null) escolaridad.setEscCalVal(calAlumno.getEvlCalVal());
                    if(calAlumno.getCalendario().getEvaluacion().getMatEvl() != null) escolaridad.setMateria(calAlumno.getCalendario().getEvaluacion().getMatEvl());
                    if(calAlumno.getCalendario().getEvaluacion().getModEvl() != null) escolaridad.setModulo(calAlumno.getCalendario().getEvaluacion().getModEvl());
                    if(calAlumno.getCalendario().getEvaluacion().getCurEvl() != null) escolaridad.setCurso(calAlumno.getCalendario().getEvaluacion().getCurEvl());
                    
                    retornoObj = (Retorno_MsgObj) LoEscolaridad.GetInstancia().guardar(escolaridad);
                
                    mensaje    = retornoObj.getMensaje();
                    
                }
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
    
    private String toValidacion(HttpServletRequest request)
    {
        mensaje    = new Mensajes("Error al guardar datos", TipoMensaje.ERROR);

        try
        {

            error           = false;
            
            CalendarioAlumno calAlumno = ValidarCalendarioAlumno(request, null);
            
            if(calAlumno.getEvlCalVal() == null)
            {
                error = true;
                mensaje = new Mensajes("No se recibió una calificación", TipoMensaje.ERROR);
            }
            
            //------------------------------------------------------------------------------------------
            //Guardar cambios
            //------------------------------------------------------------------------------------------

            if(!error)
            {
                if(perUsuario != null) calAlumno.setEvlCalPor(perUsuario);
                
                calAlumno.setEvlCalFch(new java.util.Date());
                calAlumno.setEvlCalEst(EstadoCalendarioEvaluacion.PENDIENTE_VALIDACION);
                
                Retorno_MsgObj retornoObj = (Retorno_MsgObj) loCalendario.AlumnoActualizar(calAlumno);
                
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
    
    private String toCorreccion(HttpServletRequest request)
    {
        mensaje    = new Mensajes("Error al guardar datos", TipoMensaje.ERROR);

        try
        {

            error           = false;
            
            CalendarioAlumno calAlumno = ValidarCalendarioAlumno(request, null);
            
            if(calAlumno.getEvlCalVal() == null)
            {
                error = true;
                mensaje = new Mensajes("No se recibió una calificación", TipoMensaje.ERROR);
            }
            
            //------------------------------------------------------------------------------------------
            //Guardar cambios
            //------------------------------------------------------------------------------------------

            if(!error)
            {
                if(perUsuario != null) calAlumno.setEvlValPor(perUsuario);
                
                calAlumno.setEvlCalEst(EstadoCalendarioEvaluacion.PENDIENTE_CORRECCION);
                
                Retorno_MsgObj retornoObj = (Retorno_MsgObj) loCalendario.AlumnoActualizar(calAlumno);
                    
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
        String EvlCalObs= request.getParameter("pEvlCalObs");
        String EvlValObs= request.getParameter("pEvlValObs");
        
        //------------------------------------------------------------------------------------------
        //Validaciones
        //------------------------------------------------------------------------------------------

        //TIPO DE DATO




        //Sin validacion
        if(CalCod != null)                  calAlumno.setCalendario((Calendario) loCalendario.obtener(Long.valueOf(CalCod)).getObjeto());
        
        if(CalAlCod != null)                calAlumno.setCalAlCod(Long.valueOf(CalAlCod)); 
        if(calAlumno.getCalAlCod() != null) calAlumno = calAlumno.getCalendario().getAlumnoById(calAlumno.getCalAlCod());
        
        if(AluPerCod != null)               calAlumno.setAlumno((Persona) LoPersona.GetInstancia().obtener(Long.valueOf(AluPerCod)).getObjeto());
        
        if(EvlCalVal != null)
        {

            if(calAlumno.getEvlCalVal() != Double.valueOf(EvlCalVal))
            {
                if(perUsuario != null) calAlumno.setEvlCalPor(perUsuario);
                
                calAlumno.setEvlCalFch(new java.util.Date());
                calAlumno.setEvlCalEst(EstadoCalendarioEvaluacion.CALIFICADO);
            }
            
            calAlumno.setEvlCalVal(Double.valueOf(EvlCalVal));
        }

        if(EvlCalObs != null)       calAlumno.setEvlCalObs(EvlCalObs);
        
        if(EvlValObs != null)       calAlumno.setEvlValObs(EvlValObs);
        

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
