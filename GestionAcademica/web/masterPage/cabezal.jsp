<%-- 
    Document   : cabezal
    Created on : 24-jun-2017, 11:48:48
    Author     : alvar
--%>

<%@page import="java.net.URL"%>
<%@page import="Enumerado.NombreSesiones"%>
<%@page import="Utiles.Utilidades"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%
    Utilidades utilidad = Utilidades.GetInstancia();
    String urlSistema   = utilidad.GetUrlSistema();
%>



<div id="logo" name="logo">
    <a href="<% out.print(urlSistema); %>"> <img src="<% out.print(urlSistema); %>/Imagenes/logo_ctc.png" alt="logo del instituto"/> </a>
</div>

<div id="msgError" name="msgError" class="alert alert-success div_msg" style="display: none;"> 
    <label id="txtError" name="txtError">Error</label>
</div>

 <div id="div_cargando" name="div_cargando"></div>

<%
    
    String usuario              = (String) session.getAttribute(NombreSesiones.USUARIO.getValor());
    Boolean logueado            = false;
    
    if(usuario != null)
    {
        if(!usuario.isEmpty())
        {
            logueado = true;
            %>
                <jsp:include page="/log_out.jsp" />
            <%
        }
    }

    if(!logueado)
    {
        %>
            <jsp:include page="/login.jsp" />
        <%
    }
    
%>
