<%-- 
    Document   : DefPersonaTabs
    Created on : 30-jun-2017, 20:44:43
    Author     : alvar
--%>

<%@page import="Enumerado.NombreSesiones"%>
<%@page import="Entidad.Persona"%>
<%@page import="Logica.LoPersona"%>
<%@page import="Enumerado.Modo"%>
<%@page import="Utiles.Utilidades"%>


<%
    String urlSistema   = (String) session.getAttribute(NombreSesiones.URL_SISTEMA.getValor());
    String urlActual    = Utilidades.GetInstancia().GetPaginaActual(request);
    
    Modo Mode           = Modo.valueOf(request.getParameter("MODO"));
    String CurCod       = request.getParameter("pCurCod");
    String ModCod       = request.getParameter("pModCod");
    
    out.println("<ul class='nav nav-tabs'>");
    
    out.println("<li class='" + (urlActual.equals("DefModulo.jsp") ? "active" : "") + "'><a href='" + urlSistema + "Definiciones/DefModulo.jsp?MODO=" + Mode + "&pCurCod=" + CurCod + "&pModCod=" + ModCod + "'>Modulo</a></li>");
    if(!Mode.equals(Modo.INSERT)) out.println("<li class='" + (urlActual.equals("DefModuloEvaluacionSWW.jsp") ? "active" : "")+"'><a href='" + urlSistema + "Definiciones/DefModuloEvaluacionSWW.jsp?MODO=" + Mode + "&pCurCod=" + CurCod + "&pModCod=" + ModCod + "'>Evaluaciones</a></li>");
    
    out.println("</ul>");
%>
    
