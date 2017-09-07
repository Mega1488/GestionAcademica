<%-- 
    Document   : DefCarreraTabs
    Created on : jul 21, 2017, 1:41:07 a.m.
    Author     : aa
--%>

<%@page import="Enumerado.NombreSesiones"%>
<%@page import="Utiles.Utilidades"%>
<%@page import="Enumerado.Modo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String urlSistema   = (String) session.getAttribute(NombreSesiones.URL_SISTEMA.getValor());
    String urlActual    = Utilidades.GetInstancia().GetPaginaActual(request);
    
    Modo Mode           = Modo.valueOf(request.getParameter("MODO"));
    String CarCod       = request.getParameter("pCarCod");

%>

<header class="panel-heading tab-bg-dark-navy-blue ">
    <ul class="nav nav-tabs">
        <%
            out.println("<li class='" + (urlActual.equals("DefCarrera.jsp") ? "active" : "") + "'><a href='"+ urlSistema + "Definiciones/DefCarrera.jsp?MODO=" + Mode + "&pCarCod=" + CarCod + "'>Carrera</a></li>");

            if(Mode.equals(Mode.UPDATE) || Mode.equals(Modo.DISPLAY)) out.println("<li class='" + (urlActual.equals("DefPlanEstudioSWW.jsp") ? "active" : "") + "'><a href='"+ urlSistema + "Definiciones/DefPlanEstudioSWW.jsp?MODO=" + Mode + "&pCarCod=" + CarCod + "'>Planes</a></li>");
        %>
    </ul>
    
    <span class="tools pull-right">
        <div class="hidden-xs">
            <a class="tabs_regresar" href="<% out.print(urlSistema); %>Definiciones/DefCarreraWW.jsp?MODE=<%out.print(Enumerado.Modo.DISPLAY);%>">Regresar</a>
        </div>
    </span>
</header>
