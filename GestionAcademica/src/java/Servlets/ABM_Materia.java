/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entidad.Carrera;
import Entidad.Materia;
import Entidad.PlanEstudio;
import Enumerado.TipoAprobacion;
import Enumerado.TipoMensaje;
import Enumerado.TipoPeriodo;
import Logica.LoCarrera;
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
 * @author aa
 */
public class ABM_Materia extends HttpServlet {

    private final Utilidades utilidades = Utilidades.GetInstancia();
    private final LoCarrera loCarrera   = LoCarrera.GetInstancia();
    private Mensajes mensaje            = new Mensajes("Error", TipoMensaje.ERROR);
    private Boolean error               = false;             
    
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
                    retorno = this.AgregarDatos(request);
                break;   
                case "UPDATE":
                    retorno = this.ActualizarDatos(request);
                break;   
                case "DELETE":
                    retorno = this.EliminarDatos(request);
                break;
                case "POPUP_OBTENER":
                    retorno = this.POPUP_ObtenerDatos(request);
                break;
            }
            out.println(retorno);
        }
    }
    
    public String AgregarDatos(HttpServletRequest request)
    {
        mensaje    = new Mensajes("Error al guardar datos", TipoMensaje.ERROR);

        try
        {
            error         = false;
            Materia materia = this.ValidarMateria(request, null);

            //------------------------------------------------------------------------------------------
            //Guardar cambios
            //------------------------------------------------------------------------------------------

            if(!error)
            {
                Retorno_MsgObj retornoObj = (Retorno_MsgObj) LoCarrera.GetInstancia().MateriaAgregar(materia);
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
    
    public String ActualizarDatos(HttpServletRequest request)
    {
        mensaje    = new Mensajes("Error al guardar datos", TipoMensaje.ERROR);
        try
        {
            error           = false;
            String CarCod       = request.getParameter("pCarCod");
            String PlaEstCod    = request.getParameter("pPlaEstCod");
            String MatCod       = request.getParameter("pMatCod");

            Materia mat = new Materia();
            PlanEstudio plan = new PlanEstudio();

            Retorno_MsgObj retorno = LoCarrera.GetInstancia().obtener(Long.valueOf(CarCod));
            error = retorno.getMensaje().getTipoMensaje() == TipoMensaje.ERROR || retorno.getObjeto() == null;
            
            if(!error)
            {
                plan.setPlaEstCod(Long.valueOf(PlaEstCod));
                plan.setCarrera((Carrera) retorno.getObjeto());
                
                plan = plan.getCarrera().getPlanEstudioById(plan.getPlaEstCod());
                
                mat.setMatCod(Long.valueOf(MatCod));
                mat.setPlan((PlanEstudio) plan);
                
                mat = mat.getPlan().getMateriaById(mat.getMatCod());
                
                if(mat != null)
                {
                    mat = this.ValidarMateria(request, mat);
                    mat.setMatCod(Long.valueOf(MatCod));
                }
               else
                {
                    mensaje = new Mensajes("La materia que quiere actualizar, no existe", TipoMensaje.ERROR); 
                    error   = true;
                }
            }
            
            //------------------------------------------------------------------------------------------
            //Guardar cambios
            //------------------------------------------------------------------------------------------

            if(!error)
            {
                Retorno_MsgObj retornoObj = (Retorno_MsgObj) LoCarrera.GetInstancia().MateriaActualizar(mat);
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
    
    public String EliminarDatos(HttpServletRequest request)
    {
        error      = false;
        mensaje    = new Mensajes("Error al eliminar", TipoMensaje.ERROR);

        try
        {
            String MatCod       = request.getParameter("pMatCod");
            String CarCod       = request.getParameter("pCarCod");
            String PlaEstCod    = request.getParameter("pPlaEstCod");

            Materia mat = new Materia();
            PlanEstudio plan = new PlanEstudio();
            
            Retorno_MsgObj retorno = LoCarrera.GetInstancia().obtener(Long.valueOf(CarCod));
            error = retorno.getMensaje().getTipoMensaje() == TipoMensaje.ERROR || retorno.getObjeto() == null;
            
            if(!error)
            {
                plan = ((Carrera) retorno.getObjeto()).getPlanEstudioById(Long.valueOf(PlaEstCod));
                mat = (Materia) plan.getMateriaById(Long.valueOf(MatCod));

                if(mat == null)
                {
                    retorno.setMensaje( new Mensajes("La materia que quiere eliminar, no existe", TipoMensaje.ERROR)); 
                    error   = true;
                }

                if(!error)
                {
                    retorno = (Retorno_MsgObj) LoCarrera.GetInstancia().MateriaEliminar(mat);
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
    
    private String POPUP_ObtenerDatos(HttpServletRequest request)
    {
        List<Object> lstMateria;
        
        String MatCod       = request.getParameter("popMatCod");
        String PlaEstCod    = request.getParameter("popPlaEstCod");
        
        lstMateria  = loCarrera.obtenerPopUp(Long.valueOf(PlaEstCod)).getLstObjetos();
        
        return utilidades.ObjetoToJson(lstMateria);
    }
    
    private Materia ValidarMateria(HttpServletRequest request, Materia materia)
    {
        if(materia == null)
        {
            materia   = new Materia();
        }
            String CarCod       = request.getParameter("pCarCod");
            String PlaEstCod    = request.getParameter("pPlaEstCod");
            String MatCod       = request.getParameter("pMatCod");
            String MatNom       = request.getParameter("pMatNom");
            String MatCntHor    = request.getParameter("pMatCntHor");
            String MatTpoApr    = request.getParameter("pMatTpoApr");
            String MatTpoPer    = request.getParameter("pMatTpoPer");
            String MatPerVal    = request.getParameter("pMatPerVal");
            String PreMatCod    = request.getParameter("pPreMatCod");

            //------------------------------------------------------------------------------------------
            //Validaciones
            //------------------------------------------------------------------------------------------

            //TIPO DE DATO

            //Sin validacion
            if(MatCod != null)
            {
                if(!MatCod.isEmpty())
                {
                    materia.setMatCod(Long.valueOf(MatCod));
                }
            }
            materia.setMatNom(MatNom);
            materia.setMatCntHor(Double.valueOf(MatCntHor).doubleValue());
            materia.setMatTpoApr(TipoAprobacion.fromCode(Integer.valueOf(MatTpoApr)));
            materia.setMatTpoPer(TipoPeriodo.fromCode(Integer.valueOf(MatTpoPer)));
            materia.setMatPerVal(Double.valueOf(MatPerVal).doubleValue());
            //Objetos
            
            Carrera car = (Carrera) loCarrera.obtener(Long.valueOf(CarCod)).getObjeto();
            
            PlanEstudio plan = new PlanEstudio();
            plan = car.getPlanEstudioById(Long.valueOf(PlaEstCod));
            
            materia.setPlan(plan);
            
            if(!PreMatCod.isEmpty())
            {
                Materia mat = new Materia();
                mat.setMatCod(Long.valueOf(PreMatCod));
                materia.setMateriaPrevia(mat);
            }
            
        return materia;
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
