<%-- 
    Document   : head
    Created on : 24-jun-2017, 11:59:53
    Author     : alvar
--%>
<%@page import="Utiles.Utilidades"%>
<%
    
    Utilidades utilidad = Utilidades.GetInstancia();
    String urlSistema   = utilidad.GetUrlSistema();
    
    String jquery   = "'" + urlSistema + "JavaScript/jquery-3.2.1.js'";
    String css      = "'" + urlSistema + "Estilos/sga_estyle.css'";
    
%>

<script src=<% out.print(jquery); %> type="text/javascript"></script>
<link href=<% out.print(css); %>  rel="stylesheet" type="text/css"/>