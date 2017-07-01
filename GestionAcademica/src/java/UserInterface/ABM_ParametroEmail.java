/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface;

import Entidad.Parametro;
import Entidad.ParametroEmail;
import Entidad.Version;
import Enumerado.TipoAutenticacion;
import Enumerado.TipoDato;
import Enumerado.TipoMensaje;
import Enumerado.TipoSSL;
import Logica.LoParametroEmail;
import Utiles.Mensajes;
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
public class ABM_ParametroEmail extends HttpServlet {

    private final LoParametroEmail loParamEmail = LoParametroEmail.GetInstancia();;
    private final Utilidades utilidades         = Utilidades.GetInstancia();
    
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
                
                case "OBTENER":
                    retorno = this.ObtnerDatos(request);
                break;
                
                case "INSERTAR":
                    retorno = this.AgregarDatos(request);
                break;
                
                case "ACTUALIZAR":
                    retorno = this.ActualizarDatos(request);
                break;
                
                case "ELIMINAR":
                    retorno = this.EliminarDatos(request);
                break;
                        
            }

            out.println(retorno);
            
        }
    }
    
    private String ObtnerDatos(HttpServletRequest request)
    {
        String retorno = "";
        
        String ParEmlCod                = request.getParameter("pParEmlCod");
        ParametroEmail parametroEmail   = loParamEmail.obtener(Integer.valueOf(ParEmlCod));
        
        try
        {
            retorno = utilidades.ObjetoToJson(parametroEmail);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return retorno;
    }
     
    private String AgregarDatos(HttpServletRequest request)
    {
        Mensajes mensaje    = new Mensajes("Error al guardar datos", TipoMensaje.ERROR);
        
        try
        {

            Boolean error                   = false;
            ParametroEmail parametroEmail   = new ParametroEmail();

            //String ParEmlCod    = request.getParameter("pParEmlCod");
            String ParEmlNom    = request.getParameter("pParEmlNom");
            String ParEmlPro    = request.getParameter("pParEmlPro");
            String ParEmlSrv    = request.getParameter("pParEmlSrv");
            String ParEmlPrt    = request.getParameter("pParEmlPrt");
            String ParEmlDeNom  = request.getParameter("pParEmlDeNom");
            String ParEmlDeEml  = request.getParameter("pParEmlDeEml");
            String ParEmlUtlAut = request.getParameter("pParEmlUtlAut");
            String ParEmlTpoAut = request.getParameter("pParEmlTpoAut");
            String ParEmlDom    = request.getParameter("pParEmlDom");
            String ParEmlUsr    = request.getParameter("pParEmlUsr");
            String ParEmlPsw    = request.getParameter("pParEmlPsw");
            String ParEmlSSL    = request.getParameter("pParEmlSSL");
            String ParEmlTmpEsp = request.getParameter("pParEmlTmpEsp");
            
            //------------------------------------------------------------------------------------------
            //Validaciones
            //------------------------------------------------------------------------------------------
            
            //TIPO DE DATO
            
            if(utilidades.ValidarTipoDato(TipoDato.NUMERO_ENTERO, ParEmlPro))
            {parametroEmail.setParEmlPro(Integer.valueOf(ParEmlPro)); }
            else { mensaje = new Mensajes("Tipo de dato incorrecto: ParEmlPro", TipoMensaje.ERROR); error   = true; }
            
            if(utilidades.ValidarTipoDato(TipoDato.NUMERO_ENTERO, ParEmlPrt))
            {parametroEmail.setParEmlPrt(Integer.valueOf(ParEmlPrt)); }
            else { mensaje = new Mensajes("Tipo de dato incorrecto: ParEmlPrt", TipoMensaje.ERROR); error   = true; }
            
            if(utilidades.ValidarTipoDato(TipoDato.NUMERO_ENTERO, ParEmlTmpEsp))
            {parametroEmail.setParEmlTmpEsp(Integer.valueOf(ParEmlTmpEsp)); }
            else { mensaje = new Mensajes("Tipo de dato incorrecto: ParEmlTmpEsp", TipoMensaje.ERROR); error   = true; }
            
            
            if(utilidades.ValidarTipoDato(TipoDato.BOOLEAN, ParEmlUtlAut))
            {parametroEmail.setParEmlUtlAut(Boolean.valueOf(ParEmlUtlAut)); }
            else { mensaje = new Mensajes("Tipo de dato incorrecto: ParEmlUtlAut", TipoMensaje.ERROR); error   = true; }
            
            if(!ParEmlTpoAut.isEmpty()) 
            {
                if(utilidades.ValidarTipoDato(TipoDato.TIPO_AUTENTICACION, ParEmlTpoAut))
                { 
                    parametroEmail.setParEmlTpoAut(utilidades.StringToTipoAutenticacion(ParEmlTpoAut)); 
                }
                else 
                { 
                    mensaje = new Mensajes("Tipo de dato incorrecto: ParEmlUtlAut", TipoMensaje.ERROR); 
                    error   = true; 
                }
            }
            
            if(!ParEmlSSL.isEmpty())
            {
                if(utilidades.ValidarTipoDato(TipoDato.TIPO_SSL, ParEmlSSL))
                { 
                    parametroEmail.setParEmlSSL(utilidades.StringToTipoSSL(ParEmlSSL)); 
                }
                else 
                { 
                    System.err.println("C");
                    mensaje = new Mensajes("Tipo de dato incorrecto: ParEmlSSL", TipoMensaje.ERROR); 
                    error   = true; 
                }
            }
            
            
            
            //Sin validacion
            parametroEmail.setParEmlNom(ParEmlNom);
            parametroEmail.setParEmlSrv(ParEmlSrv);
            parametroEmail.setParEmlDeNom(ParEmlDeNom);
            parametroEmail.setParEmlDeEml(ParEmlDeEml);
            parametroEmail.setParEmlDom(ParEmlDom);
            parametroEmail.setParEmlUsr(ParEmlUsr);
            parametroEmail.setParEmlPsw(ParEmlPsw);
            
            //------------------------------------------------------------------------------------------
            //Guardar cambios
            //------------------------------------------------------------------------------------------

            if(!error)
            {
                loParamEmail.guardar(parametroEmail);
                mensaje    = new Mensajes("Cambios guardados correctamente", TipoMensaje.MENSAJE);
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
        Mensajes mensaje    = new Mensajes("Error al guardar datos", TipoMensaje.ERROR);
        
        try
        {

            Boolean error                   = false;
            ParametroEmail parametroEmail   = new ParametroEmail();

            String ParEmlCod    = request.getParameter("pParEmlCod");
            String ParEmlNom    = request.getParameter("pParEmlNom");
            String ParEmlPro    = request.getParameter("pParEmlPro");
            String ParEmlSrv    = request.getParameter("pParEmlSrv");
            String ParEmlPrt    = request.getParameter("pParEmlPrt");
            String ParEmlDeNom  = request.getParameter("pParEmlDeNom");
            String ParEmlDeEml  = request.getParameter("pParEmlDeEml");
            String ParEmlUtlAut = request.getParameter("pParEmlUtlAut");
            String ParEmlTpoAut = request.getParameter("pParEmlTpoAut");
            String ParEmlDom    = request.getParameter("pParEmlDom");
            String ParEmlUsr    = request.getParameter("pParEmlUsr");
            String ParEmlPsw    = request.getParameter("pParEmlPsw");
            String ParEmlSSL    = request.getParameter("pParEmlSSL");
            String ParEmlTmpEsp = request.getParameter("pParEmlTmpEsp");
            
            //------------------------------------------------------------------------------------------
            //Validaciones
            //------------------------------------------------------------------------------------------
            if(utilidades.ValidarTipoDato(TipoDato.NUMERO_ENTERO, ParEmlCod))
            {
                parametroEmail = loParamEmail.obtener(Integer.valueOf(ParEmlCod)); 
                
                if(parametroEmail == null)
                {
                    mensaje = new Mensajes("El parametro que quiere actualizar, no existe", TipoMensaje.ERROR); 
                    error   = true;
                }
            }
            else { mensaje = new Mensajes("Tipo de dato incorrecto: ParEmlCod", TipoMensaje.ERROR); error   = true; }
            
            
            //TIPO DE DATO
            
            if(utilidades.ValidarTipoDato(TipoDato.NUMERO_ENTERO, ParEmlPro))
            {parametroEmail.setParEmlPro(Integer.valueOf(ParEmlPro)); }
            else { mensaje = new Mensajes("Tipo de dato incorrecto: ParEmlPro", TipoMensaje.ERROR); error   = true; }
            
            if(utilidades.ValidarTipoDato(TipoDato.NUMERO_ENTERO, ParEmlPrt))
            {parametroEmail.setParEmlPrt(Integer.valueOf(ParEmlPrt)); }
            else { mensaje = new Mensajes("Tipo de dato incorrecto: ParEmlPrt", TipoMensaje.ERROR); error   = true; }
            
            if(utilidades.ValidarTipoDato(TipoDato.NUMERO_ENTERO, ParEmlTmpEsp))
            {parametroEmail.setParEmlTmpEsp(Integer.valueOf(ParEmlTmpEsp)); }
            else { mensaje = new Mensajes("Tipo de dato incorrecto: ParEmlTmpEsp", TipoMensaje.ERROR); error   = true; }
            
            
            if(utilidades.ValidarTipoDato(TipoDato.BOOLEAN, ParEmlUtlAut))
            {parametroEmail.setParEmlUtlAut(Boolean.valueOf(ParEmlUtlAut)); }
            else { mensaje = new Mensajes("Tipo de dato incorrecto: ParEmlUtlAut", TipoMensaje.ERROR); error   = true; }
            
            if(!ParEmlTpoAut.isEmpty()) 
            {
                if(utilidades.ValidarTipoDato(TipoDato.TIPO_AUTENTICACION, ParEmlTpoAut))
                { 
                    parametroEmail.setParEmlTpoAut(utilidades.StringToTipoAutenticacion(ParEmlTpoAut)); 
                }
                else 
                { 
                    mensaje = new Mensajes("Tipo de dato incorrecto: ParEmlUtlAut", TipoMensaje.ERROR); 
                    error   = true; 
                }
            }
            
            if(!ParEmlSSL.isEmpty())
            {
                if(utilidades.ValidarTipoDato(TipoDato.TIPO_SSL, ParEmlSSL))
                { 
                    parametroEmail.setParEmlSSL(utilidades.StringToTipoSSL(ParEmlSSL)); 
                }
                else 
                { 
                    System.err.println("C");
                    mensaje = new Mensajes("Tipo de dato incorrecto: ParEmlSSL", TipoMensaje.ERROR); 
                    error   = true; 
                }
            }
            
            
            
            //Sin validacion
            parametroEmail.setParEmlNom(ParEmlNom);
            parametroEmail.setParEmlSrv(ParEmlSrv);
            parametroEmail.setParEmlDeNom(ParEmlDeNom);
            parametroEmail.setParEmlDeEml(ParEmlDeEml);
            parametroEmail.setParEmlDom(ParEmlDom);
            parametroEmail.setParEmlUsr(ParEmlUsr);
            parametroEmail.setParEmlPsw(ParEmlPsw);
            
            //------------------------------------------------------------------------------------------
            //Guardar cambios
            //------------------------------------------------------------------------------------------

            if(!error)
            {
                loParamEmail.actualizar(parametroEmail);
                mensaje    = new Mensajes("Cambios guardados correctamente", TipoMensaje.MENSAJE);
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
        boolean error       = false;
        Mensajes mensaje    = new Mensajes("Error al eliminar", TipoMensaje.ERROR);
        try
        {
            String ParEmlCod    = request.getParameter("pParEmlCod");

            ParametroEmail parametroEmail = new ParametroEmail();

            if(utilidades.ValidarTipoDato(TipoDato.NUMERO_ENTERO, ParEmlCod))
                {
                    parametroEmail = loParamEmail.obtener(Integer.valueOf(ParEmlCod)); 

                    if(parametroEmail == null)
                    {
                        mensaje = new Mensajes("El parametro que quiere eliminar, no existe", TipoMensaje.ERROR); 
                        error   = true;
                    }
                }
                else { mensaje = new Mensajes("Tipo de dato incorrecto: ParEmlCod", TipoMensaje.ERROR); error   = true; }


            if(!error)
            {
                loParamEmail.eliminar(parametroEmail);
                mensaje    = new Mensajes("Cambios guardados correctamente", TipoMensaje.MENSAJE);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        
                    
        return utilidades.ObjetoToJson(mensaje);
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
