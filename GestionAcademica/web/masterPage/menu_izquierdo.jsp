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
    String carrera      = "'" + urlSistema + "Definiciones/DefCarreraWW.jsp'";
    
    String curso      = "'" + urlSistema + "Definiciones/DefCursoWW.jsp'";
    
    String prueba      = "'" + urlSistema + "Prueba'";
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
        <a href=<% out.print(carrera); %>> Carrera </a>
        <a href=<% out.print(curso); %>>Curso</a>
    </li>
    <li>
        <a href=<% out.print(prueba); %>> Prueba </a>
    </li>
</ul>

</div>
