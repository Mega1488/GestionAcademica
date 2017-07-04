<%-- 
    Document   : DefCursoTabs
    Created on : 03-jul-2017, 18:29:23
    Author     : alvar
--%>

<%@page import="Enumerado.Modo"%>
<%@page import="Utiles.Utilidades"%>
<%@page import="Logica.LoCurso"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    LoCurso loCurso     = LoCurso.GetInstancia();
    Utilidades utilidad = Utilidades.GetInstancia();
    String urlSistema   = utilidad.GetUrlSistema();
    
    Modo Mode           = Modo.valueOf(request.getParameter("MODO"));
    String CurCod       = request.getParameter("pCurCod");
    
    out.println("<div class='div_tabs'><a href='" + urlSistema + "Definiciones/DefCurso.jsp?MODO=" + Mode + "&pCurCod=" + CurCod + "'>Curso</a></div>");
    
    if(!Mode.equals(Mode.INSERT))
    {  
        out.println("<div class='div_tabs'><a href='" + urlSistema + "Definiciones/DefCursoModuloSWW.jsp?MODO=" + Mode + "&pCurCod=" + CurCod + "'>Modulos</a></div>");
    }
%>
    