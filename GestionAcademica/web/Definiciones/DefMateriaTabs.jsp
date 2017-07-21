<%-- 
    Document   : DefMateriaTabs
    Created on : jul 20, 2017, 3:18:00 p.m.
    Author     : aa
--%>

<%@page import="Utiles.Utilidades"%>
<%@page import="Enumerado.Modo"%>
<%@page import="Enumerado.NombreSesiones"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String urlSistema   = (String) session.getAttribute(NombreSesiones.URL_SISTEMA.getValor());
    String urlActual    = Utilidades.GetInstancia().GetPaginaActual(request);
    Modo Mode           = Modo.valueOf(request.getParameter("MODO"));
    String CarCod       = request.getParameter("pCarCod");
    String PlaEstCod    = request.getParameter("pPlaEstCod");
    String MatCod       = request.getParameter("pMatCod");
    
    
%>
    
<ul class="nav nav-tabs">
    <% 
        out.println("<li class='" + (urlActual.equals("DefMateria.jsp") ? "active" : "") + "'><a href='"+ urlSistema + "Definiciones/DefMateria.jsp?MODO=" + Mode + "&pCarCod=" + CarCod + "&pPlaEstCod=" + PlaEstCod + "&pMatCod=" + MatCod + "'>Materia</a></li>");
        
        if(Mode.equals(Mode.UPDATE) || Mode.equals(Modo.DISPLAY)) out.println("<li class='" + (urlActual.equals("DefMateriaEvaluacionSWW.jsp") ? "active" : "") + "'><a href='"+ urlSistema + "Definiciones/DefMateriaEvaluacionSWW.jsp?MODO=" + Mode + "&pCarCod=" + CarCod + "&pPlaEstCod=" + PlaEstCod + "&pMatCod=" + MatCod + "'>Evaluaciones</a></li>");
    %>
</ul>