<%-- 
    Document   : DefCalendarioTabs
    Created on : 03-jul-2017, 18:29:23
    Author     : alvar
--%>

<%@page import="Enumerado.Modo"%>
<%@page import="Utiles.Utilidades"%>
<%@page import="Logica.LoCalendario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    Utilidades utilidad = Utilidades.GetInstancia();
    String urlSistema   = utilidad.GetUrlSistema();
    
    Modo Mode           = Modo.valueOf(request.getParameter("MODO"));
    String CalCod       = request.getParameter("pCalCod");
   
    String urlActual = utilidad.GetPaginaActual(request);
    
%>
    

<ul class="nav nav-tabs">
    <li class="<% out.print((urlActual.equals("DefCalendario.jsp") ? "active" : "")); %>"><a href='<% out.print(urlSistema); %>Definiciones/DefCalendario.jsp?MODO=<% out.print(Mode); %>&pCalCod=<% out.print(CalCod); %>'>Calendario</a></li>
    <li class="<% out.print((urlActual.equals("DefCalendarioAlumnoSWW.jsp") ? "active" : "")); %>"><a href='<% out.print(urlSistema); %>Definiciones/DefCalendarioAlumnoSWW.jsp?MODO=<% out.print(Mode); %>&pCalCod=<% out.print(CalCod); %>'>Alumnos</a></li>
    <li class="<% out.print((urlActual.equals("DefCalendarioDocenteSWW.jsp") ? "active" : "")); %>"><a href='<% out.print(urlSistema); %>Definiciones/DefCalendarioDocenteSWW.jsp?MODO=<% out.print(Mode); %>&pCalCod=<% out.print(CalCod); %>'>Docentes</a></li>
</ul>