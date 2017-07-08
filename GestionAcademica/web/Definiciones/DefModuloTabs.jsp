<%-- 
    Document   : DefPersonaTabs
    Created on : 30-jun-2017, 20:44:43
    Author     : alvar
--%>

<%@page import="Entidad.Persona"%>
<%@page import="Logica.LoPersona"%>
<%@page import="Enumerado.Modo"%>
<%@page import="Utiles.Utilidades"%>


<%
    Utilidades utilidad = Utilidades.GetInstancia();
    String urlSistema   = utilidad.GetUrlSistema();
    
    Modo Mode           = Modo.valueOf(request.getParameter("MODO"));
    String CurCod       = request.getParameter("pCurCod");
    String ModCod       = request.getParameter("pModCod");
    
    out.println("<div class='div_tabs'><a href='" + urlSistema + "Definiciones/DefModulo.jsp?MODO=" + Mode + "&pCurCod=" + CurCod + "&pModCod=" + ModCod + "'>Modulo</a></div>");
    
    if(!Mode.equals(Mode.INSERT))
    {  
        out.println("<div class='div_tabs'><a href='" + urlSistema + "Definiciones/DefModuloEvaluacionSWW.jsp?MODO=" + Mode + "&pCurCod=" + CurCod + "&pModCod=" + ModCod + "'>Evaluación</a></div>");
    }
%>
    