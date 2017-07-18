/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;


import Entidad.Parametro;
import Entidad.Calendario;
import Entidad.Evaluacion;
import Enumerado.Modo;
import Enumerado.TipoMensaje;
import Logica.LoParametro;
import Logica.LoCalendario;
import Logica.LoEvaluacion;
import Logica.Seguridad;
import Utiles.Mensajes;
import Utiles.Retorno_MsgObj;
import Utiles.Utilidades;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author alvar
 */
public class ABM_Calendario extends HttpServlet {
    private final LoCalendario loCalendario = LoCalendario.GetInstancia();
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
            
            String retorno  = "";
            String action   = request.getParameter("pAction");

            if(action.equals("INSERT_LIST"))
            {
                retorno = this.GuardarLista(request);
            }
            else
            {
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
            }
            
            out.println(retorno);
            
        }
        
    }
    
    
    private String GuardarLista(HttpServletRequest request)
    {
        mensaje    = new Mensajes("Error al guardar datos", TipoMensaje.ERROR);

        List<Object> lstCalendario = new ArrayList<>();
        String listaCala    = request.getParameter("pLstCalendario");
        
        if(listaCala != null)
        {
            if(!listaCala.isEmpty())
            {
                lstCalendario       = utilidades.JsonToListObject(listaCala, Calendario.class);
                
                for(Object objeto : lstCalendario)
                {
                    Calendario calendario = (Calendario) objeto;
                    
                    
                    if(calendario.getEvaluacion().getEvlCod() !=null) calendario.setEvaluacion((Evaluacion) LoEvaluacion.GetInstancia().obtener(calendario.getEvaluacion().getEvlCod()).getObjeto());
                    
                    System.err.println("Inicio ABM Guardar");
                    Retorno_MsgObj retornoObj = (Retorno_MsgObj) loCalendario.guardar(calendario);
                    mensaje    = retornoObj.getMensaje();
                    System.err.println("Fin ABM Guardar");
                    
                    if(retornoObj.SurgioError())
                    {
                        break;
                    }
                    

                }
            }
        }
        
        return  utilidades.ObjetoToJson(mensaje);
    }
    
    private String AgregarDatos(HttpServletRequest request)
        {
            mensaje    = new Mensajes("Error al guardar datos", TipoMensaje.ERROR);

            try
            {

                error           = false;
                
                Calendario calendario = this.ValidarCalendario(request, null);
                
               
                //------------------------------------------------------------------------------------------
                //Guardar cambios
                //------------------------------------------------------------------------------------------

                if(!error)
                {
                    System.err.println("Inicio ABM Guardar");
                    Retorno_MsgObj retornoObj = (Retorno_MsgObj) loCalendario.guardar(calendario);
                    mensaje    = retornoObj.getMensaje();
                    System.err.println("Fin ABM Guardar");
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
            String CalCod= request.getParameter("pCalCod");


            Retorno_MsgObj retorno = loCalendario.obtener(Long.valueOf(CalCod));
            error = retorno.getMensaje().getTipoMensaje() == TipoMensaje.ERROR || retorno.getObjeto() == null;

            if(!error)
            {
                Calendario calendario = (Calendario) retorno.getObjeto();
                calendario = this.ValidarCalendario(request, calendario);

                //------------------------------------------------------------------------------------------
                //Guardar cambios
                //------------------------------------------------------------------------------------------

                if(!error)
                {
                    retorno     = (Retorno_MsgObj) loCalendario.actualizar(calendario);
                }
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
            String CalCod= request.getParameter("pCalCod");

            Retorno_MsgObj retorno  =  loCalendario.obtener(Long.valueOf(CalCod));
            
            error = retorno.getMensaje().getTipoMensaje() == TipoMensaje.ERROR || retorno.getObjeto() == null;

            if(!error)
            {
                retorno = (Retorno_MsgObj) loCalendario.eliminar((Calendario) retorno.getObjeto());
            }
            
            mensaje = retorno.getMensaje();

        }
        catch(Exception ex)
        {
            mensaje = new Mensajes("Error al Eliminar: " + ex.getMessage(), TipoMensaje.ERROR);
            throw ex;
        }



        return utilidades.ObjetoToJson(mensaje);
    }
        
        private Calendario ValidarCalendario(HttpServletRequest request, Calendario calendario)
        {
            if(calendario == null)
            {
                calendario   = new Calendario();
            }

                String EvlCod= request.getParameter("pEvlCod");
                String CalFch= request.getParameter("pCalFch");
                String EvlInsFchDsd= request.getParameter("pEvlInsFchDsd");
                String EvlInsFchHst= request.getParameter("pEvlInsFchHst");
                
                //------------------------------------------------------------------------------------------
                //Validaciones
                //------------------------------------------------------------------------------------------

                //TIPO DE DATO

                


                //Sin validacion
                Evaluacion evaluacion = new Evaluacion();
                if(EvlCod !=null) evaluacion  = (Evaluacion) LoEvaluacion.GetInstancia().obtener(Long.valueOf(EvlCod)).getObjeto();
                
                if(EvlCod !=null) calendario.setEvaluacion(evaluacion);

                if(CalFch !=null) if(!CalFch.isEmpty()) calendario.setCalFch(Date.valueOf(CalFch));
                if(EvlInsFchDsd !=null) if(!EvlInsFchDsd.isEmpty()) calendario.setEvlInsFchDsd(Date.valueOf(EvlInsFchDsd));
                if(EvlInsFchHst !=null) if(!EvlInsFchHst.isEmpty()) calendario.setEvlInsFchHst(Date.valueOf(EvlInsFchHst));
                
                
            return calendario;
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
