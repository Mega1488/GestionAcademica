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
    String CalCod       = request.getParameter("pCalCod");
   
    String urlActual = utilidad.GetPaginaActual(request);
    
    out.println("<ul class='nav nav-tabs'>");
    
    out.println("<li class='" + (urlActual.equals("DefCalendario.jsp") ? "active" : "") + "'><a href='" + urlSistema + "Definiciones/DefCalendario.jsp?MODO="+Mode+"&pCalCod=" +CalCod+"'>Calendario</a></li>");
    if(!Mode.equals(Modo.INSERT)) out.println("<li class='" + (urlActual.equals("DefCalendarioAlumnoSWW.jsp") ? "active" : "")+"'><a href='" + urlSistema + "Definiciones/DefCalendarioAlumnoSWW.jsp?MODO=" +Mode + "&pCalCod=" + CalCod + "'>Alumnos</a></li>");
    if(!Mode.equals(Modo.INSERT)) out.println("<li class='" + (urlActual.equals("DefCalendarioDocenteSWW.jsp") ? "active" : "")+"'><a href='" + urlSistema + "Definiciones/DefCalendarioDocenteSWW.jsp?MODO=" +Mode + "&pCalCod=" + CalCod + "'>Docentes</a></li>");
    
    out.println("</ul>");
%>
    
