/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;


import Entidad.Parametro;
import Entidad.Curso;
import Enumerado.Filial;
import Enumerado.TipoDato;
import Enumerado.TipoMensaje;
import Logica.LoCurso;
import Logica.LoParametro;
import Logica.LoCurso;
import Logica.Seguridad;
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
 * @author alvar
 */
public class ABM_Curso extends HttpServlet {
    private final LoParametro loParametro   = LoParametro.GetInstancia();
    private final Parametro parametro       = loParametro.obtener(1);
    private final Seguridad seguridad       = Seguridad.GetInstancia();
    private final LoCurso loCurso           = LoCurso.GetInstancia();
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
                
                Curso curso = this.ValidarCurso(request, null);

                //------------------------------------------------------------------------------------------
                //Guardar cambios
                //------------------------------------------------------------------------------------------

                if(!error)
                {
                    Retorno_MsgObj retornoObj = (Retorno_MsgObj) loCurso.guardar(curso);
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
                String CurCod   = request.getParameter("pCurCod");
                
                Curso curso = new Curso();
                curso.setCurCod(Integer.valueOf(CurCod));
                
                curso = loCurso.obtener(curso.getCurCod());
                
                if(curso != null)
                {
                    curso = this.ValidarCurso(request, curso);
                    curso.setCurCod(Integer.valueOf(CurCod));
                }
               else
                {
                    mensaje = new Mensajes("El curso que quiere actualizar, no existe", TipoMensaje.ERROR); 
                    error   = true;
                }
                
                //------------------------------------------------------------------------------------------
                //Guardar cambios
                //------------------------------------------------------------------------------------------

                if(!error)
                {
                    Retorno_MsgObj retornoObj = (Retorno_MsgObj) loCurso.actualizar(curso);
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
                String CurCod    = request.getParameter("pCurCod");
                Curso curso = new Curso();
                curso.setCurCod(Integer.valueOf(CurCod));
                
                curso = loCurso.obtener(curso.getCurCod());
                
                if(curso == null)
                {
                    mensaje = new Mensajes("La curso que quiere eliminar, no existe", TipoMensaje.ERROR); 
                    error   = true;
                }
                
                if(!error)
                {
                    Retorno_MsgObj retornoObj = (Retorno_MsgObj) loCurso.eliminar(curso);
                    mensaje    = retornoObj.getMensaje();
                }

                
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }



            return utilidades.ObjetoToJson(mensaje);
        }
        
        private Curso ValidarCurso(HttpServletRequest request, Curso curso)
        {
            if(curso == null)
            {
                curso   = new Curso();
            }

                String CurNom= request.getParameter("pCurNom");
                String CurDsc= request.getParameter("pCurDsc");
                String CurFac= request.getParameter("pCurFac");
                String CurCrt= request.getParameter("pCurCrt");
                String CurCatCod= request.getParameter("pCurCatCod");
                
                //------------------------------------------------------------------------------------------
                //Validaciones
                //------------------------------------------------------------------------------------------

                //TIPO DE DATO

                


                //Sin validacion
                curso.setCurNom(CurNom);
                curso.setCurDsc(CurDsc);
                curso.setCurFac(CurFac);
                curso.setCurCrt(CurCrt);
                
                if(parametro.getParUtlMdl())
                {
                    if(!CurCatCod.isEmpty())
                    {
                        curso.setCurCatCod(Long.valueOf(CurCatCod));
                    }
                }
                
                
                
                
            return curso;
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
