<%-- 
    Document   : cabezal
    Created on : 24-jun-2017, 11:48:48
    Author     : alvar
--%>

<%@page import="java.net.URL"%>
<%@page import="Enumerado.NombreSesiones"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    String usuario = (String) session.getAttribute(NombreSesiones.USUARIO.getValor());
    Boolean logueado = false;
    
    String file = request.getRequestURI();
    if (request.getQueryString() != null) {
       file += '?' + request.getQueryString();
    }
    URL reconstructedURL = new URL(request.getScheme(),
                                   request.getServerName(),
                                   request.getServerPort(),
                                   file);
    String url_sistema = reconstructedURL.toString();
    
    session.setAttribute(NombreSesiones.URL_SISTEMA.getValor(), url_sistema);

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
