/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entidad.Carrera;
import Entidad.Parametro;
import Entidad.PlanEstudio;
import Enumerado.TipoMensaje;
import Logica.LoCarrera;
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
 * @author aa
 */
public class ABM_PlanEstudio extends HttpServlet {

    boolean error;
    Mensajes mensaje;
    Utilidades utilidades   = Utilidades.GetInstancia();
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
            
            String action   = request.getParameter("pAccion");
            String retorno  = "";
            
            switch(action)
            {
                case "INSERT":
                    retorno = this.IngresarPlanEstudio(request);
                break;

                case "UPDATE":
                    retorno = this.ModificarPlanEstudio(request);
                break;

                case "DELETE":
                    retorno = this.EliminarPlanEstudio(request);
                break;

                default:
                    break;
            }
            out.println(retorno);
        }
    }
    
    private String IngresarPlanEstudio(HttpServletRequest request)
    {
        mensaje    = new Mensajes("Error al guardar datos", TipoMensaje.ERROR);
       
        try
        {
            error         = false;
            PlanEstudio plan = this.ValidarPlanEstudio(request, null);

            //------------------------------------------------------------------------------------------
            //Guardar cambios
            //------------------------------------------------------------------------------------------

            if(!error)
            {
                System.out.println("1");
                Retorno_MsgObj retornoObj = (Retorno_MsgObj) LoCarrera.GetInstancia().PlanEstudioAgregar(plan);
                mensaje    = retornoObj.getMensaje();
            }
        }
        catch(Exception ex)
        {
            mensaje = new Mensajes("Error al guardar: " + ex.getMessage(), TipoMensaje.ERROR);
            throw ex;
        }
        System.out.println("RETORNO EN ABM_PlanEstudio: "+mensaje);
        String retorno = utilidades.ObjetoToJson(mensaje);
        return retorno;
    }
    
    private String ModificarPlanEstudio(HttpServletRequest request)
    {
        mensaje    = new Mensajes("Error al guardar datos", TipoMensaje.ERROR);

        try
        {
            error               = false;
            String PlaEstCod    = request.getParameter("pPlaEstCod");
            String CarCod       = request.getParameter("pCarCod");

            PlanEstudio plan = new PlanEstudio();
            
            Retorno_MsgObj retorno = LoCarrera.GetInstancia().obtener(Long.valueOf(CarCod));
            error = retorno.getMensaje().getTipoMensaje() == TipoMensaje.ERROR || retorno.getObjeto() == null;
            
            if(!error)
            {
                plan.setPlaEstCod(Long.valueOf(PlaEstCod));
                plan.setCarrera((Carrera) retorno.getObjeto());

                plan = plan.getCarrera().getPlanEstudioById(plan.getPlaEstCod());

                if(plan != null)
                {
                    plan = this.ValidarPlanEstudio(request, plan);
                    plan.setPlaEstCod(Long.valueOf(PlaEstCod));
                }
               else
                {
                    mensaje = new Mensajes("El Plan de Estudio que quiere actualizar, no existe", TipoMensaje.ERROR); 
                    error   = true;
                }
            }
            
            //------------------------------------------------------------------------------------------
            //Guardar cambios
            //------------------------------------------------------------------------------------------

            if(!error)
            {
                Retorno_MsgObj retornoObj = (Retorno_MsgObj) LoCarrera.GetInstancia().PlanEstudioActualizar(plan);
                mensaje    = retornoObj.getMensaje();
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
    
    private String EliminarPlanEstudio(HttpServletRequest request)
    {
        error      = false;
        mensaje    = new Mensajes("Error al eliminar", TipoMensaje.ERROR);

        try
        {
            String PlaEstCod    = request.getParameter("pPlaEstCod");
            String CarCod       = request.getParameter("pCarCod");

            Retorno_MsgObj retorno = LoCarrera.GetInstancia().obtener(Long.valueOf(CarCod));
            error = retorno.getMensaje().getTipoMensaje() == TipoMensaje.ERROR || retorno.getObjeto() == null;
            
            if(!error)
            {
            
                PlanEstudio plan = ((Carrera) retorno.getObjeto()).getPlanEstudioById(Long.valueOf(PlaEstCod));

                if(plan == null)
                {
                    retorno.setMensaje( new Mensajes("El Plan de Estudio que quiere eliminar, no existe", TipoMensaje.ERROR)); 
                    error   = true;
                }

                if(!error)
                {
                    retorno = (Retorno_MsgObj) LoCarrera.GetInstancia().PlanEstudioEliminar(plan);
                }
            }
            mensaje    = retorno.getMensaje();
        }
        catch(Exception ex)
        {
            mensaje = new Mensajes("Error al Eliminar: " + ex.getMessage(), TipoMensaje.ERROR);
            throw ex;
        }
        return utilidades.ObjetoToJson(mensaje);
    }
    
    private PlanEstudio ValidarPlanEstudio(HttpServletRequest request, PlanEstudio plan)
    {
        if(plan == null)
        {
            plan = new PlanEstudio();
        }
            String PlaEstNom    = request.getParameter("pPlaEstNom");
            String PlaEstDsc    = request.getParameter("pPlaEstDsc");
            String PlaEstCreNec = request.getParameter("pPlaEstCreNec");
            String CarCod       = request.getParameter("pCarCod");
            
            //Validaciones
            
            //Tipos de Datos (Expresiones regulares, etc)
            
            plan.setPlaEstNom(PlaEstNom);
            plan.setPlaEstDsc(PlaEstDsc);
            plan.setPlaEstCreNec(Double.valueOf(PlaEstCreNec).doubleValue());
            plan.setCarrera((Carrera) LoCarrera.GetInstancia().obtener(Long.valueOf(CarCod)).getObjeto());
            
            return plan;
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
