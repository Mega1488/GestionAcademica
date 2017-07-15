<%-- 
    Document   : DefPersonaTabs
    Created on : 30-jun-2017, 20:44:43
    Author     : alvar
--%>

<%@page import="Entidad.Persona"%>
<%@page import="Logica.LoPersona"%>
<%@page import="Enumerado.Modo"%>
<%@page import="Utiles.Utilidades"%>


<%
    Utilidades utilidad = Utilidades.GetInstancia();
    String urlSistema   = utilidad.GetUrlSistema();

    String urlActual = utilidad.GetPaginaActual(request);
%>

<ul class="nav nav-tabs">
    <li class="<% out.print((urlActual.equals("DefCalendarioGrid.jsp") ? "active" : "")); %>"><a href='<% out.print(urlSistema); %>Definiciones/DefCalendarioGrid.jsp'>Calendario</a></li>
    <li class="<% out.print((urlActual.equals("DefCalendarioWW.jsp") ? "active" : "")); %>"><a href='<% out.print(urlSistema); %>Definiciones/DefCalendarioWW.jsp'>Listado</a></li>
</ul>
    