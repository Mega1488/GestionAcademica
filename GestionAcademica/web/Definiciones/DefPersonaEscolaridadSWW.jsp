<%-- 
    Document   : DefPersonaEscolaridad
    Created on : 30-jun-2017, 20:51:03
    Author     : alvar
--%>

<%@page import="Entidad.Escolaridad"%>
<%@page import="SDT.SDT_PersonaEstudio"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Logica.LoPersona"%>
<%@page import="Entidad.Persona"%>
<%@page import="Enumerado.Modo"%>
<%@page import="Utiles.Utilidades"%>
<%@page import="Utiles.Retorno_MsgObj"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    Utilidades utilidad    = Utilidades.GetInstancia();
    String urlSistema           = (String) session.getAttribute(Enumerado.NombreSesiones.URL_SISTEMA.getValor());
    
    //----------------------------------------------------------------------------------------------------
    //CONTROL DE ACCESO
    //----------------------------------------------------------------------------------------------------
    
    String  usuario = (String) session.getAttribute(Enumerado.NombreSesiones.USUARIO.getValor());
    Boolean esAdm   = (Boolean) session.getAttribute(Enumerado.NombreSesiones.USUARIO_ADM.getValor());
    Boolean esAlu   = (Boolean) session.getAttribute(Enumerado.NombreSesiones.USUARIO_ALU.getValor());
    Boolean esDoc   = (Boolean) session.getAttribute(Enumerado.NombreSesiones.USUARIO_DOC.getValor());
    Retorno_MsgObj acceso = Logica.Seguridad.GetInstancia().ControlarAcceso(usuario, esAdm, esDoc, esAlu, utilidad.GetPaginaActual(request));
    
    if(acceso.SurgioError()) response.sendRedirect((String) acceso.getObjeto());
            
    //----------------------------------------------------------------------------------------------------
    
    Modo Mode           = Modo.valueOf(request.getParameter("MODO"));
    String PerCod       = request.getParameter("pPerCod");
    
    ArrayList<SDT_PersonaEstudio> lstEstudio = new ArrayList<>();
    if(Mode.equals(Modo.UPDATE) || Mode.equals(Modo.DISPLAY) || Mode.equals(Modo.DELETE))
    {
        lstEstudio = LoPersona.GetInstancia().ObtenerEstudios(Long.valueOf(PerCod));
    }

    String tblVisible = (lstEstudio.size() > 0 ? "" : "display: none;");
    
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gestión Académica - Persona | Escolaridad</title>
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
                        
                        <div id="tabs" name="tabs" class="contenedor-tabs">
                            <jsp:include page="/Definiciones/DefPersonaTabs.jsp"/>
                        </div>
                        
                        <div class=""> 
                            <div style="text-align: right;"><a href="<% out.print(urlSistema); %>Definiciones/DefPersonaWW.jsp">Regresar</a></div>
                        </div>

                        <div name="cont_estudio" style=' <% out.print(tblVisible); %>'>
                            <%
                                for(SDT_PersonaEstudio est : lstEstudio)
                                {

                                    if(est.getInscripcion().getInsCod() == Long.valueOf("0"))
                                    {
                                        out.println("<div><label>Sin inscripción</label></div>");
                                    }
                                    else
                                    {
                                        if(est.getInscripcion().getCurso() != null){
                                            out.println("<div><label>Inscripto a: " + est.getInscripcion().getCurso().getCurNom() + "</label></div>");
                                        }

                                        if(est.getInscripcion().getPlanEstudio() != null){
                                            out.println("<div><label>Inscripto a: " + est.getInscripcion().getPlanEstudio().getPlaEstNom() + "</label></div>");
                                        }                                   

                                    }
                                    
                                    out.println("<table class='table table-hover'>");
                                    out.println("<thead><tr>");
                                    out.println("<th>Materia</th>");
                                    out.println("<th>Calificación</th>");
                                    out.println("</tr>");
                                    out.println("</thead>");
                                    out.println("<tbody>");

                                    for(Escolaridad esc : est.getEscolaridad())
                                    {
                                            out.println("<tr>");

                                            out.println("<td>");
                                            if(esc.getModulo() != null) out.println(esc.getModulo().getModNom() );
                                            if(esc.getCurso() != null) out.println(esc.getCurso().getCurNom() );
                                            if(esc.getMateria() != null) out.println(esc.getMateria().getMatNom() );
                                            out.println("</td>");

                                            out.println("<td>");
                                            out.println("<label>" + esc.getEscCalVal() + "</label>");
                                            out.println("</td>");

                                            out.println("</tr>");
                                    }
                                    
                                    out.println("</tbody>");
                                    out.println("</table>");
                                }
                            %>
                        </div>

                    </div>
                </div>
            </div>
        </div>
                                     
    </body>
</html>