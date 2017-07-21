/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entidad.Carrera;
import Entidad.Curso;
import Entidad.Evaluacion;
import Entidad.Materia;
import Entidad.Modulo;
import Entidad.PlanEstudio;
import Entidad.TipoEvaluacion;
import Enumerado.TipoMensaje;
import Logica.LoCarrera;
import Logica.LoCurso;
import Logica.LoEvaluacion;
import Logica.LoParametro;
import Logica.LoTipoEvaluacion;
import Utiles.Mensajes;
import Utiles.Retorno_MsgObj;
import Utiles.Utilidades;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author alvar
 */
public class ABM_Evaluacion extends HttpServlet {
    private final LoParametro loParametro   = LoParametro.GetInstancia();
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
                
                case "INSERTAR":
                    retorno = this.AgregarDatos(request);
                break;
                
                case "ACTUALIZAR":
                    retorno = this.ActualizarDatos(request);
                break;
                
                case "ELIMINAR":
                    retorno = this.EliminarDatos(request);
                break;
                
                case "POPUP_LISTAR":
                    retorno = this.PopUp_ListarDatos(request);
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

            Evaluacion evaluacion = this.ValidarEvaluacion(request, null);
            System.err.println("..");
            //------------------------------------------------------------------------------------------
            //Guardar cambios
            //------------------------------------------------------------------------------------------

            if(!error)
            {
                System.err.println("x");
                
                if(evaluacion.getCurEvl() != null)
                {
                    System.err.println("b");
                    Retorno_MsgObj retornoObj = (Retorno_MsgObj) LoCurso.GetInstancia().CursoEvaluacionAgregar(evaluacion);
                    
                    mensaje    = retornoObj.getMensaje();
                }
                
                if(evaluacion.getModEvl()!= null)
                {
                    System.err.println("c");
                    Retorno_MsgObj retornoObj = (Retorno_MsgObj) LoCurso.GetInstancia().ModuloEvaluacionAgregar(evaluacion);
                    
                    mensaje    = retornoObj.getMensaje();
                }
                
                if(evaluacion.getMatEvl() != null)
                {
                    Retorno_MsgObj retornoObj = (Retorno_MsgObj) LoEvaluacion.GetInstancia().guardar(evaluacion);
                    mensaje    = retornoObj.getMensaje();
                }
            }
        }
        catch(Exception ex)
        {
            mensaje = new Mensajes("Error al Agregar: " + ex.getMessage(), TipoMensaje.ERROR);
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
            
            Evaluacion evaluacion = this.ValidarEvaluacion(request, null);
            
            if(!error)
            {
                String EvlCod= request.getParameter("pEvlCod");
                evaluacion.setEvlCod(Long.valueOf(EvlCod));
                
                if(evaluacion.getCurEvl() != null)
                {
                    Retorno_MsgObj retornoObj = (Retorno_MsgObj) LoCurso.GetInstancia().CursoEvaluacionActualizar(evaluacion);
                    
                    mensaje    = retornoObj.getMensaje();
                }
                
                if(evaluacion.getModEvl() != null)
                {
                    Retorno_MsgObj retornoObj = (Retorno_MsgObj) LoCurso.GetInstancia().ModuloEvaluacionActualizar(evaluacion);
                    
                    mensaje    = retornoObj.getMensaje();
                }
                
                if(evaluacion.getMatEvl() != null)
                {
                    Retorno_MsgObj retornoObj = (Retorno_MsgObj) LoEvaluacion.GetInstancia().actualizar(evaluacion);
                    mensaje    = retornoObj.getMensaje();
                }
            }
            
        }
        catch(Exception ex)
        {
            mensaje = new Mensajes("Error al Actualizar: " + ex.getMessage(), TipoMensaje.ERROR);
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
            Evaluacion evaluacion = this.ValidarEvaluacion(request, null);
            if(!error)
            {
                String EvlCod= request.getParameter("pEvlCod");
                evaluacion.setEvlCod(Long.valueOf(EvlCod));
                
                if(evaluacion.getCurEvl() != null)
                {
                    Retorno_MsgObj retornoObj = (Retorno_MsgObj) LoCurso.GetInstancia().CursoEvaluacionEliminar(evaluacion);
                    
                    mensaje    = retornoObj.getMensaje();
                }
                
                if(evaluacion.getModEvl() != null)
                {
                    Retorno_MsgObj retornoObj = (Retorno_MsgObj) LoCurso.GetInstancia().ModuloEvaluacionEliminar(evaluacion);
                    
                    mensaje    = retornoObj.getMensaje();
                }
                
                if(evaluacion.getMatEvl() != null)
                {
                    Retorno_MsgObj retornoObj = (Retorno_MsgObj) LoEvaluacion.GetInstancia().eliminar(evaluacion);
                    mensaje    = retornoObj.getMensaje();
                }
            }
        }
        catch(Exception ex)
        {
            mensaje = new Mensajes("Error al Eliminar: " + ex.getMessage(), TipoMensaje.ERROR);
            throw ex;
        }

        String retorno = utilidades.ObjetoToJson(mensaje);

        return retorno;
    }
    
    private String PopUp_ListarDatos(HttpServletRequest request)
    {

        List<Evaluacion> lstRetorno = new ArrayList<>();
        
        String MatCod = request.getParameter("popMatCod");
        String CurCod = request.getParameter("popCurCod");
        String ModCod = request.getParameter("popModCod");
        
        if(CurCod != null)
        {
            lstRetorno = ((Curso) LoCurso.GetInstancia().obtener(Long.valueOf(CurCod)).getObjeto()).getLstEvaluacion();
        }
        
        if(ModCod  != null)
        {
            lstRetorno = ((Curso) LoCurso.GetInstancia().obtener(Long.valueOf(CurCod)).getObjeto()).getLstEvaluacion();
        }
        
        if(MatCod != null)
        {
            lstRetorno = ((Modulo) ((Curso) LoCurso.GetInstancia().obtener(Long.valueOf(CurCod)).getObjeto()).getModuloById(Long.valueOf(ModCod))).getLstEvaluacion();
        }

        if(MatCod == null && ModCod == null && CurCod == null)
        {
            return utilidades.ObjetoToJson(LoEvaluacion.GetInstancia().obtenerLista().getLstObjetos());
        }
        else
        {
            return  utilidades.ObjetoToJson(lstRetorno);
        }
                
    }

    private Evaluacion ValidarEvaluacion(HttpServletRequest request, Evaluacion evaluacion)
    {
            if(evaluacion == null)
            {
                evaluacion   = new Evaluacion();
            }

          
                String MatEvlCarCod= request.getParameter("pMatEvlCarCod");
                String MatEvlPlaEstCod= request.getParameter("pMatEvlPlaEstCod");
                String MatEvlMatCod= request.getParameter("pMatEvlMatCod");
                
                String CurEvlCurCod= request.getParameter("pCurEvlCurCod");
                
                String ModEvlCurCod= request.getParameter("pModEvlCurCod");
                String ModEvlModCod= request.getParameter("pModEvlModCod");
                
                String EvlNom= request.getParameter("pEvlNom");
                String EvlDsc= request.getParameter("pEvlDsc");
                String EvlNotTot= request.getParameter("pEvlNotTot");
                String TpoEvlCod= request.getParameter("pTpoEvlCod");
                
                //------------------------------------------------------------------------------------------
                //Validaciones
                //------------------------------------------------------------------------------------------

                //TIPO DE DATO

                


                //Sin validacion
                if(!MatEvlMatCod.equals("null"))
                {           
                    Carrera car = new Carrera();
                    car = (Carrera) LoCarrera.GetInstancia().obtener(Long.valueOf(MatEvlCarCod)).getObjeto();
                    PlanEstudio plan = new PlanEstudio();
                    plan = car.getPlanEstudioById(Long.valueOf(MatEvlPlaEstCod));
                    Materia mat = new Materia();
                    mat = plan.getMateriaById(Long.valueOf(MatEvlMatCod));
                    
                    evaluacion.setMatEvl(mat);
                }
                     
                if(!CurEvlCurCod.equals("null"))
                {
                    evaluacion.setCurEvl((Curso) LoCurso.GetInstancia().obtener(Long.valueOf(CurEvlCurCod)).getObjeto());
                }
                
                
                if(!ModEvlCurCod.equals("null"))
                {
                    Curso curso = (Curso) LoCurso.GetInstancia().obtener(Long.valueOf(ModEvlCurCod)).getObjeto();
                    evaluacion.setModEvl(curso.getModuloById(Long.valueOf(ModEvlModCod)));
                }
                
                evaluacion.setEvlNom(EvlNom);
                evaluacion.setEvlDsc(EvlDsc);
                
                evaluacion.setEvlNotTot(Double.valueOf(EvlNotTot));
                
                if(!TpoEvlCod.isEmpty())
                {
                    Retorno_MsgObj retorno = LoTipoEvaluacion.GetInstancia().obtener(Long.valueOf(TpoEvlCod));
                    if(!retorno.SurgioErrorObjetoRequerido())
                    {
                        evaluacion.setTpoEvl((TipoEvaluacion) retorno.getObjeto());
                    }
                }
        
           
                
            return evaluacion;
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
