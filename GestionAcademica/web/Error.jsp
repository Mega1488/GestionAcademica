<%-- 
    Document   : Error
    Created on : 15-jul-2017, 18:01:46
    Author     : alvar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String mensaje = request.getParameter("pMensaje");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gestión Académica - Error</title>
        <jsp:include page="/masterPage/head.jsp"/>
    </head>
    <body>
        

        <div class="container-fluid">
            
            <div id="cabezal" name="cabezal" class="row">
            <jsp:include page="/masterPage/cabezal.jsp"/>
        </div>
        
        
                <div class="col-sm-2">
                    <jsp:include page="/masterPage/menu_izquierdo.jsp" />
                </div>

                <div id="contenido" name="contenido"  class="col-sm-8">
                    <h1>Error</h1>

                    <div class="alert alert-danger">
                        <strong>Error!</strong> <% out.print(mensaje); %>.
                    </div>
                </div>
        </div>
                
    </body>
</html>