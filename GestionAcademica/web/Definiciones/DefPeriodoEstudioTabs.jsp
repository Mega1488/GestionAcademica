<%-- 
    Document   : DefCalendarioTabs
    Created on : 03-jul-2017, 18:29:23
    Author     : alvar
--%>

<%@page import="Enumerado.NombreSesiones"%>
<%@page import="Enumerado.Modo"%>
<%@page import="Utiles.Utilidades"%>
<%@page import="Logica.LoCalendario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    Utilidades utilidad = Utilidades.GetInstancia();
    String urlSistema   = (String) session.getAttribute(NombreSesiones.URL_SISTEMA.getValor());
    
    Modo Mode           = Modo.valueOf(request.getParameter("MODO"));
    String PeriEstCod      = request.getParameter("pPeriEstCod");
   
    String urlActual = utilidad.GetPaginaActual(request);
    
    out.println("<ul class='nav nav-tabs'>");
    
    if(!Mode.equals(Modo.INSERT)) out.println("<li class='" + (urlActual.equals("DefPeriodoAlumnoSWW.jsp") ? "active" : "")+"'><a href='" + urlSistema + "Definiciones/DefPeriodoAlumnoSWW.jsp?MODO=" +Mode + "&pPeriEstCod=" + PeriEstCod + "'>Alumnos</a></li>");
    if(!Mode.equals(Modo.INSERT)) out.println("<li class='" + (urlActual.equals("DefPeriodoDocenteSWW.jsp") ? "active" : "")+"'><a href='" + urlSistema + "Definiciones/DefPeriodoDocenteSWW.jsp?MODO=" +Mode + "&pPeriEstCod=" + PeriEstCod + "'>Docentes</a></li>");
    if(!Mode.equals(Modo.INSERT)) out.println("<li class='" + (urlActual.equals("DefPeriodoDocumentoSWW.jsp") ? "active" : "")+"'><a href='" + urlSistema + "Definiciones/DefPeriodoDocumentoSWW.jsp?MODO=" +Mode + "&pPeriEstCod=" + PeriEstCod + "'>Documentos</a></li>");
    out.println("</ul>");
%>
    
