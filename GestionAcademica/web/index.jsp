<%-- 
    Document   : index
    Created on : 24-jun-2017, 12:34:51
    Author     : alvar
--%>

<%@page import="Enumerado.NombreSesiones"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Utiles.Utilidades"%>
<%@page import="Logica.LoIniciar"%>

<%
    String usuario = (String) session.getAttribute(NombreSesiones.USUARIO.getValor());
    RequestDispatcher dispatch = request.getRequestDispatcher("login.jsp");
    
    if(usuario == null)
    {
        //response.sendRedirect("login.jsp");
        dispatch.forward(request, response);
    }
    else
    {
        Boolean esAdm = (Boolean) session.getAttribute(NombreSesiones.USUARIO_ADM.getValor());
        Boolean esAlu = (Boolean) session.getAttribute(NombreSesiones.USUARIO_ALU.getValor());
        Boolean esDoc = (Boolean) session.getAttribute(NombreSesiones.USUARIO_DOC.getValor());
        
        
        if(esAdm) request.getRequestDispatcher("Definiciones/DefCalendarioGrid.jsp").forward(request, response);//response.sendRedirect("Definiciones/DefCalendarioGrid.jsp");
        if(esDoc) request.getRequestDispatcher("Docente/EstudiosDictados.jsp").forward(request, response);//response.sendRedirect("Docente/EstudiosDictados.jsp");
        if(esAlu) request.getRequestDispatcher("Alumno/Evaluaciones.jsp").forward(request, response);//response.sendRedirect("Alumno/Evaluaciones.jsp");
        
        //dispatch.forward(request, response);
        
    }
    
    
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gestión Académica</title>
        <jsp:include page="/masterPage/head.jsp"/>
    </head>
    <body>
        
    </body>
</html>
