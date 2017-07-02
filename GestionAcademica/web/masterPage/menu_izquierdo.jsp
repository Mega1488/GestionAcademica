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
    
    String version      = "'" + urlSistema + "Definiciones/DefVersion.jsp'";
    String parametro    = "'" + urlSistema + "Definiciones/DefParametro.jsp'";
    String parametroEml = "'" + urlSistema + "Definiciones/DefParametroEmailWW.jsp'";
    String persona      = "'" + urlSistema + "Definiciones/DefPersonaWW.jsp'";
%>
    
<div >
<list>
<ul>
    <li>
        <a href=<% out.print(version); %>>Versión</a>
    </li>
    <li>
        <a href=<% out.print(parametro); %>>Parametros</a>
    </li>
    <li>
        <a href=<% out.print(parametroEml); %>>Parametros Email</a>
    </li>
    <li>
        <a href=<% out.print(persona); %>>Persona</a>
    </li>
    <li>
        <a href="#"> Menu tres </a>
    </li>
</ul>

</div>