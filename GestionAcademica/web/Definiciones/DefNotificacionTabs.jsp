<%-- 
    Document   : DefCursoTabs
    Created on : 03-jul-2017, 18:29:23
    Author     : alvar
--%>

<%@page import="Enumerado.NombreSesiones"%>
<%@page import="Enumerado.Modo"%>
<%@page import="Utiles.Utilidades"%>
<%@page import="Logica.LoCurso"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    String urlSistema   = (String) session.getAttribute(NombreSesiones.URL_SISTEMA.getValor());
    String urlActual    = Utilidades.GetInstancia().GetPaginaActual(request);
    Modo Mode           = Modo.valueOf(request.getParameter("MODO"));
    String NotCod       = request.getParameter("pNotCod");
    
    
%>
    
<ul class="nav nav-tabs">
    <% 
        out.println("<li class='" + (urlActual.equals("DefNotificacion.jsp") ? "active" : "") + "'><a href='"+ urlSistema + "Definiciones/DefNotificacion.jsp?MODO=" + Mode + "&pNotCod=" + NotCod + "'>Notificación</a></li>");
        
        if(!Mode.equals(Mode.INSERT)) out.println("<li class='" + (urlActual.equals("DefNotificacionDestinatarioSWW.jsp") ? "active" : "") + "'><a href='"+ urlSistema + "Definiciones/DefNotificacionDestinatarioSWW.jsp?MODO=" + Mode + "&pNotCod=" + NotCod + "'>Destinatarios</a></li>");
        if(!Mode.equals(Mode.INSERT)) out.println("<li class='" + (urlActual.equals("DefNotificacionConsultaSWW.jsp") ? "active" : "") + "'><a href='"+ urlSistema + "Definiciones/DefNotificacionConsultaSWW.jsp?MODO=" + Mode + "&pNotCod=" + NotCod + "'>Consultas</a></li>");
        if(!Mode.equals(Mode.INSERT)) out.println("<li class='" + (urlActual.equals("DefNotificacionBitacoraSWW.jsp") ? "active" : "") + "'><a href='"+ urlSistema + "Definiciones/DefNotificacionBitacoraSWW.jsp?MODO=" + Mode + "&pNotCod=" + NotCod + "'>Bitácora</a></li>");
    
    %>
</ul>