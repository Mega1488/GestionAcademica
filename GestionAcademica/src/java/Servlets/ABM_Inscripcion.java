/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;


import Entidad.Carrera;
import Entidad.Curso;
import Entidad.Parametro;
import Entidad.Inscripcion;
import Entidad.Evaluacion;
import Entidad.Persona;
import Enumerado.Modo;
import Enumerado.NombreSesiones;
import Enumerado.TipoMensaje;
import Logica.LoCarrera;
import Logica.LoCurso;
import Logica.LoParametro;
import Logica.LoInscripcion;
import Logica.LoEvaluacion;
import Logica.LoPersona;
import Logica.Seguridad;
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
public class ABM_Inscripcion extends HttpServlet {
    private final LoParametro loParametro   = LoParametro.GetInstancia();
    private final Parametro parametro       = loParametro.obtener(1);
    private final Seguridad seguridad       = Seguridad.GetInstancia();
    private final LoInscripcion loInscripcion           = LoInscripcion.GetInstancia();
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
            
            HttpSession session=request.getSession(); 
            String usuario  = (String) session.getAttribute(NombreSesiones.USUARIO.getValor());            
            if(usuario != null)  perUsuario = (Persona) LoPersona.GetInstancia().obtenerByMdlUsr(usuario).getObjeto();
            
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
                
                Inscripcion inscripcion = this.ValidarInscripcion(request, null);

                //------------------------------------------------------------------------------------------
                //Guardar cambios
                //------------------------------------------------------------------------------------------

                if(!error)
                {
                    if(perUsuario != null) inscripcion.setPersonaInscribe(perUsuario);
                    inscripcion.setAluFchInsc(new java.util.Date());
                    Retorno_MsgObj retornoObj = (Retorno_MsgObj) loInscripcion.guardar(inscripcion);
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
            
            Inscripcion inscripcion = this.ValidarInscripcion(request, null);

            //------------------------------------------------------------------------------------------
            //Guardar cambios
            //------------------------------------------------------------------------------------------

            Retorno_MsgObj retorno = new Retorno_MsgObj();
            
            if(!error)
            {
                retorno     = (Retorno_MsgObj) loInscripcion.actualizar(inscripcion);
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

            Inscripcion inscripcion = this.ValidarInscripcion(request, null);

            if(!error)
            {
                mensaje = ((Retorno_MsgObj) loInscripcion.eliminar(inscripcion)).getMensaje();
            }
            
        }
        catch(Exception ex)
        {
            mensaje = new Mensajes("Error al Eliminar: " + ex.getMessage(), TipoMensaje.ERROR);
            throw ex;
        }



        return utilidades.ObjetoToJson(mensaje);
    }
        
    private Inscripcion ValidarInscripcion(HttpServletRequest request, Inscripcion inscripcion)
    {
        
        if(inscripcion == null)
        {
            inscripcion   = new Inscripcion();
        }

            
                String 	InsCod          = request.getParameter("pInsCod");
                String 	PerCod          = request.getParameter("pPerCod");
                String 	CarCod          = request.getParameter("pCarCod");
                
                String 	EstudioCodigo   = request.getParameter("pCodigoEstudio");
                String 	TipoEstudio     = request.getParameter("pTipoEstudio");
                String  AluFchCert       = request.getParameter("pAluFchCert");
                
                String  InsGenAnio       = request.getParameter("pInsGenAnio");
                
                
                //------------------------------------------------------------------------------------------
                //Validaciones
                //------------------------------------------------------------------------------------------

                //TIPO DE DATO

                


                //Sin validacion
                
                if(InsCod != null) inscripcion = ((Inscripcion) loInscripcion.obtener(Long.valueOf(InsCod)).getObjeto());
                
                if(PerCod != null) inscripcion.setAlumno((Persona) LoPersona.GetInstancia().obtener(Long.valueOf(PerCod)).getObjeto());
                
                if(TipoEstudio != null)
                {
                    if(TipoEstudio.equals("CARRERA") && EstudioCodigo != null)
                    {
                        Carrera carrera = (Carrera) LoCarrera.GetInstancia().obtener(Long.valueOf(CarCod)).getObjeto();
                        inscripcion.setPlanEstudio(carrera.getPlanEstudioById(Long.valueOf(EstudioCodigo)));
                    }
                    
                    if(TipoEstudio.equals("CURSO") && EstudioCodigo != null) inscripcion.setCurso((Curso) LoCurso.GetInstancia().obtener(Long.valueOf(EstudioCodigo)).getObjeto());
                }
                
                if(AluFchCert != null) inscripcion.setAluFchCert(Date.valueOf(AluFchCert));
                
                if(InsGenAnio != null) inscripcion.setInsGenAnio(Integer.valueOf(InsGenAnio));
                
                return inscripcion;
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
