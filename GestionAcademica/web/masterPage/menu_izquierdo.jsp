<%-- 
    Document   : MasterPage
    Created on : 24-jun-2017, 11:43:00
    Author     : alvar
--%>

<%@page import="Utiles.Utilidades"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Utilidades utilidad = Utilidades.GetInstancia();
    String urlSistema   = utilidad.GetUrlSistema();
    
    String version = "'" + urlSistema + "Definiciones/DefVersion.jsp'";
%>
    
<div >
<list>
<ul>
    <li>
        <a href=<% out.print(version); %>>Versión</a>
    </li>
    <li>
        <a href="#"> Menu dos </a>
    </li>
    <li>
        <a href="#"> Menu tres </a>
    </li>
</ul>

</div>