/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;


import Entidad.Parametro;
import Entidad.Persona;
import Enumerado.Filial;
import Enumerado.TipoMensaje;
import Logica.LoParametro;
import Logica.LoPersona;
import Logica.Seguridad;
import Utiles.Mensajes;
import Utiles.Retorno_MsgObj;
import Utiles.Utilidades;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author alvar
 */
public class ABM_Persona extends HttpServlet {
    private final Parametro parametro       = LoParametro.GetInstancia().obtener();
    private final Seguridad seguridad       = Seguridad.GetInstancia();
    private final LoPersona loPersona       = LoPersona.GetInstancia();
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
                
                case "POPUP_OBTENER":
                    retorno = this.POPUP_ObtenerDatos();
                break;
                
                case "POPUP_OBTENER_ESTUDIOS":
                    retorno = this.POPUP_ObtenerEstudiosDatos(request);
                break;
                
                case "CAMBIAR_PSW":
                    retorno = this.CambiarPsw(request);
                break;
                
                case "SOL_PSW_RECOVERY":
                    retorno = this.SolRecoveryPsw(request);
                break;
                
                case "PSW_RECOVERY":
                    retorno = this.RecoveryPsw(request);
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

            Persona persona = this.ValidarPersona(request, null);

            //------------------------------------------------------------------------------------------
            //Guardar cambios
            //------------------------------------------------------------------------------------------

            if(!error)
            {
                Retorno_MsgObj retornoObj = (Retorno_MsgObj) loPersona.guardar(persona);
                mensaje    = retornoObj.getMensaje();
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
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
            String PerCod   = request.getParameter("pPerCod");

            Persona persona = (Persona) loPersona.obtener(Long.valueOf(PerCod)).getObjeto();

            if(persona != null)
            {
                persona = this.ValidarPersona(request, persona);
                persona.setPerCod(Long.valueOf(PerCod));
            }
           else
            {
                mensaje = new Mensajes("La persona que quiere actualizar, no existe", TipoMensaje.ERROR); 
                error   = true;
            }

            //------------------------------------------------------------------------------------------
            //Guardar cambios
            //------------------------------------------------------------------------------------------

            if(!error)
            {
                Retorno_MsgObj retornoObj = (Retorno_MsgObj) loPersona.actualizar(persona);
                mensaje    = retornoObj.getMensaje();
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
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
            String PerCod    = request.getParameter("pPerCod");
            Persona persona = (Persona) loPersona.obtener(Long.valueOf(PerCod)).getObjeto();

            if(persona == null)
            {
                mensaje = new Mensajes("La persona que quiere eliminar, no existe", TipoMensaje.ERROR); 
                error   = true;
            }

            if(!error)
            {
                Retorno_MsgObj retornoObj = (Retorno_MsgObj) loPersona.eliminar(persona);
                mensaje    = retornoObj.getMensaje();
            }


        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }



        return utilidades.ObjetoToJson(mensaje);
    }
    
    private String POPUP_ObtenerDatos()
    {
        List<Object> lstPersona;
        
        
        lstPersona = loPersona.obtenerPopUp().getLstObjetos();

        return utilidades.ObjetoToJson(lstPersona);
    }
    
    private String POPUP_ObtenerEstudiosDatos(HttpServletRequest request)
    {
        Persona persona = this.ValidarPersona(request, null);

        return utilidades.ObjetoToJson(persona.getLstEstudios());
    }
    
    private String CambiarPsw(HttpServletRequest request){
        String usuario    = request.getParameter("usuario");
        String pswActual  = request.getParameter("pswActual");
        String pswNueva   = request.getParameter("pswNueva");
        String pswConf    = request.getParameter("pswConfirmacion");
        
        Retorno_MsgObj retorno = loPersona.CambiarPassword(usuario, pswActual, pswNueva, pswConf);
        
        return utilidades.ObjetoToJson(retorno.getMensaje());
    }
    
    private String SolRecoveryPsw(HttpServletRequest request){
        String usuario    = request.getParameter("usuario");
        
        Retorno_MsgObj retorno = loPersona.SolicitaRestablecerPassword(usuario);
        
        return utilidades.ObjetoToJson(retorno.getMensaje());
    }
    
    private String RecoveryPsw(HttpServletRequest request){
        String PerCod       = request.getParameter("PerCod");
        String tkn          = request.getParameter("tkn");
        String pswNueva     = request.getParameter("pswNueva");
        String pswConf      = request.getParameter("pswConfirmacion");
        
        Retorno_MsgObj retorno = loPersona.RestablecerPassword(Long.valueOf(PerCod), tkn, pswNueva, pswConf);
        
        return utilidades.ObjetoToJson(retorno.getMensaje());
    }

    private Persona ValidarPersona(HttpServletRequest request, Persona persona)
    {
        if(persona == null)
        {
            persona   = new Persona();
        }

            String PerCod   = request.getParameter("pPerCod");
            String PerNom= request.getParameter("pPerNom");
            String PerApe= request.getParameter("pPerApe");
            String PerDoc= request.getParameter("pPerDoc");
            String PerUsrMod= request.getParameter("pPerUsrMod");
            String PerEsDoc= request.getParameter("pPerEsDoc");
            String PerEsAdm= request.getParameter("pPerEsAdm");
            String PerEsAlu= request.getParameter("pPerEsAlu");
            String PerNroLib= request.getParameter("pPerNroLib");
            String PerNroEstOrt= request.getParameter("pPerNroEstOrt");
            String PerFil= request.getParameter("pPerFil");
            String PerEml= request.getParameter("pPerEml");
            String PerNotEml= request.getParameter("pPerNotEml");
            String PerNotApp= request.getParameter("pPerNotApp");
            String PerPass = request.getParameter("pPerPass");

            //------------------------------------------------------------------------------------------
            //Validaciones
            //------------------------------------------------------------------------------------------

            //TIPO DE DATO




            //Sin validacion
            
            

            if(PerCod != null) if(!PerCod.isEmpty()) persona = (Persona) loPersona.obtener(Long.valueOf(PerCod)).getObjeto();
            
            if(PerNom != null) persona.setPerNom(PerNom);
            if(PerApe != null) persona.setPerApe(PerApe);
            if(PerUsrMod != null) persona.setPerUsrMod(PerUsrMod);
            if(PerEsDoc != null) persona.setPerEsDoc(Boolean.valueOf(PerEsDoc));
            if(PerEsAdm != null) persona.setPerEsAdm(Boolean.valueOf(PerEsAdm));
            if(PerEsAlu != null) persona.setPerEsAlu(Boolean.valueOf(PerEsAlu));
            if(PerNroLib != null) persona.setPerNroLib(Integer.valueOf(PerNroLib));
            if(PerNroEstOrt != null) persona.setPerNroEstOrt(Integer.valueOf(PerNroEstOrt));
            if(PerFil != null) persona.setPerFil(Filial.fromCode(Integer.valueOf(PerFil)));
            if(PerEml != null) persona.setPerEml(PerEml);
            if(PerNotEml != null) persona.setPerNotEml(Boolean.valueOf(PerNotEml));
            if(PerNotApp != null) persona.setPerNotApp(Boolean.valueOf(PerNotApp));
            
            if(PerDoc != null) persona.setPerDoc(PerDoc);

            if(!PerPass.isEmpty())
            {
                if(parametro.getParPswValExp() != null)
                {
                    if(!PerPass.matches(parametro.getParPswValExp()))
                    {
                        error = true;
                        mensaje = new Mensajes(parametro.getParPswValMsg(), TipoMensaje.ERROR);
                    }
                }    

                if(!error)
                {
                    persona.setPerPass(seguridad.cryptWithMD5(PerPass));
                }
            }

        return persona;
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
