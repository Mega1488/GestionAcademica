<%-- 
    Document   : DefPlanEstudioTabs
    Created on : jul 21, 2017, 2:55:15 a.m.
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
    String PlaEstCod    = request.getParameter("pPlaEstCod");
    
    
%>
    
<ul class="nav nav-tabs">
    <% 
        out.println("<li class='" + (urlActual.equals("DefPlanEstudio.jsp") ? "active" : "") + "'><a href='"+ urlSistema + "Definiciones/DefPlanEstudio.jsp?MODO=" + Mode + "&pCarCod=" + CarCod + "&pPlaEstCod=" + PlaEstCod + "'>Plan</a></li>");
        
        if(Mode.equals(Mode.UPDATE) || Mode.equals(Modo.DISPLAY)) out.println("<li class='" + (urlActual.equals("DefMateriaSWW.jsp") ? "active" : "") + "'><a href='"+ urlSistema + "Definiciones/DefMateriaSWW.jsp?MODO=" + Mode + "&pCarCod=" + CarCod + "&pPlaEstCod=" + PlaEstCod + "'>Materias</a></li>");
    %>
</ul>