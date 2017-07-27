<%-- 
    Document   : Evaluaciones
    Created on : 26-jul-2017, 16:38:15
    Author     : alvar
--%>

<%@page import="Entidad.Escolaridad"%>
<%@page import="Entidad.Persona"%>
<%@page import="Logica.LoPersona"%>
<%@page import="SDT.SDT_PersonaEstudio"%>
<%@page import="Enumerado.Modo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Utiles.Retorno_MsgObj"%>
<%@page import="Logica.Seguridad"%>
<%@page import="Enumerado.NombreSesiones"%>
<%@page import="Utiles.Utilidades"%>
<%@page import="Logica.LoCalendario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    LoCalendario loCalendario   = LoCalendario.GetInstancia();
    Utilidades utilidad         = Utilidades.GetInstancia();
    String urlSistema           = (String) session.getAttribute(NombreSesiones.URL_SISTEMA.getValor());
    
    //----------------------------------------------------------------------------------------------------
    //CONTROL DE ACCESO
    //----------------------------------------------------------------------------------------------------
    
    String  usuario = (String) session.getAttribute(NombreSesiones.USUARIO.getValor());
    Boolean esAdm   = (Boolean) session.getAttribute(NombreSesiones.USUARIO_ADM.getValor());
    Boolean esAlu   = (Boolean) session.getAttribute(NombreSesiones.USUARIO_ALU.getValor());
    Boolean esDoc   = (Boolean) session.getAttribute(NombreSesiones.USUARIO_DOC.getValor());
    Retorno_MsgObj acceso = Seguridad.GetInstancia().ControlarAcceso(usuario, esAdm, esDoc, esAlu, utilidad.GetPaginaActual(request));
    
    if(acceso.SurgioError()) response.sendRedirect((String) acceso.getObjeto());
            
    //----------------------------------------------------------------------------------------------------
    
    ArrayList<SDT_PersonaEstudio> lstEstudio = new ArrayList<>();
    Persona persona = (Persona) LoPersona.GetInstancia().obtenerByMdlUsr(usuario).getObjeto();
    lstEstudio      = LoPersona.GetInstancia().ObtenerEstudios(persona.getPerCod());
    
    String tblVisible = (lstEstudio.size() > 0 ? "" : "display: none;");
    
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gestión Académica - Escolaridad</title>
        <jsp:include page="/masterPage/head.jsp"/>
    </head>
    <body>
        <jsp:include page="/masterPage/NotificacionError.jsp"/>
        <div class="wrapper">
            <jsp:include page="/masterPage/menu_izquierdo.jsp" />
            
            <div id="contenido" name="contenido" class="main-panel">
                
                <div class="contenedor-cabezal">
                    <jsp:include page="/masterPage/cabezal.jsp"/>
                </div>
                
                <div class="contenedor-principal">
                    <div class="col-sm-11 contenedor-texto-titulo-flotante">
                        <div class="contenedor-titulo">    
                            <p>Escolaridad</p>
                        </div>
                        
                        <div name="cont_estudio" class="col-sm-8" style=' <% out.print(tblVisible); %>'>
                            <%
                                for(SDT_PersonaEstudio est : lstEstudio)
                                {

                                    if(est.getInscripcion().getInsCod() == Long.valueOf("0"))
                                    {
                                        out.println("<div class='contenedor_titulo_escolaridad'><label>Sin inscripción</label></div>");
                                    }
                                    else
                                    {
                                        out.println("<div class='contenedor_titulo_escolaridad'><label>Inscripto a: " + est.getInscripcion().getNombreEstudio()+ "</label></div>");
                                    }
                                    
                                    out.println("<div class='contenedor_tabla_escolaridad'>");
                                    out.println("<table class='table table-hover eliminar_margen_tabla'>");
                                    out.println("<thead><tr>");
                                    out.println("<th>Materia</th>");
                                    out.println("<th class='texto_derecha'>Calificación</th>");
                                    out.println("<th class='texto_derecha'>Estado</th>");
                                    out.println("</tr>");
                                    out.println("</thead>");
                                    out.println("<tbody>");

                                    for(Escolaridad esc : est.getEscolaridad())
                                    {
                                            out.println("<tr>");

                                            out.println("<td>");
                                            out.println(esc.getNombreEstudio());
                                            out.println("</td>");

                                            out.println("<td class='texto_derecha'>");
                                            out.println("<label>" + (esc.getEscCalVal().equals(Double.NaN) ? "0" : esc.getEscCalVal()) + "</label>");
                                            out.println("</td>");
                                            
                                            out.println("<td class='texto_derecha'>");
                                            out.println("<label>" + esc.getAprobacion() + "</label>");
                                            out.println("</td>");

                                            out.println("</tr>");
                                    }
                                    
                                    out.println("</tbody>");
                                    out.println("</table>");
                                    
                                    out.println("</div>");
                                }
                            %>
                        </div>
                        
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>