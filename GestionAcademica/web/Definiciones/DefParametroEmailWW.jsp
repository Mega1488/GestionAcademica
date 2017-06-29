<%-- 
    Document   : DefVersion
    Created on : 25-jun-2017, 21:43:57
    Author     : alvar
--%>

<%@page import="Logica.LoParametroEmail"%>
<%@page import="Entidad.ParametroEmail"%>
<%@page import="java.util.List"%>
<%@page import="Utiles.Utilidades"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Utilidades utilidad = Utilidades.GetInstancia();
    LoParametroEmail  loParamEml  = LoParametroEmail.GetInstancia();
    String urlSistema   = utilidad.GetUrlSistema();
    List<ParametroEmail> lstParamEml = loParamEml.obtenerLista();
    System.err.println("Lista: " + lstParamEml.size());
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gestión Académica - Versión</title>
        <jsp:include page="/masterPage/head.jsp"/>
        
        <script>
                $(document).ready(function() {
                        
                       
                    
                       
                    
                });
        </script>
        
    </head>
    <body>
     
            <div id="cabezal" name="cabezal">
                <jsp:include page="/masterPage/cabezal.jsp"/>
            </div>

            <div style="float:left; width: 10%; height: 100%;">
                <jsp:include page="/masterPage/menu_izquierdo.jsp" />
            </div>

            <div id="contenido" name="contenido" style="float: right; width: 90%;">
                <div style="display:none" id="datos_ocultos" name="datos_ocultos">
                    <input type="hidden" name="LISTA" id="LISTA" value="">
                </div>
                
                <h1>Parámetro email</h1>
                <div> <a href="<% out.print(urlSistema); %>Definiciones/DefParametroEmail.jsp?MODO=<% out.print(Enumerado.Modo.INSERT); %>">Agregar nuevo parametro email</a> </div>
                
                <table>
                    <th>
                        <td></td>
                        <td></td>
                        <td>Código</td>
                        <td>Nombre</td>
                        <td>Servidor de correo</td>
                        <td>Nombre del remitente</td>
                        <td>Email del remitente</td>
                    </th>
                <% for(ParametroEmail prmEml : lstParamEml)
                    {
                     
                %>
                    <tr>
                        <td><a href="<% out.print(urlSistema); %>Definiciones/DefParametroEmail.jsp?MODO=<% out.print(Enumerado.Modo.DELETE); %>&pParEmlCod=<% out.print(prmEml.getParEmlCod()); %>" name="btn_eliminar" id="btn_eliminar" >Eliminar</a></td>
                        <td><a href="<% out.print(urlSistema); %>Definiciones/DefParametroEmail.jsp?MODO=<% out.print(Enumerado.Modo.UPDATE); %>&pParEmlCod=<% out.print(prmEml.getParEmlCod()); %>" name="btn_editar" id="btn_editar" >Editar</a></td>
                        <td><% out.print(prmEml.getParEmlCod()); %></td>
                        <td><% out.print(prmEml.getParEmlNom()); %></td>
                        <td><% out.print(prmEml.getParEmlSrv()); %></td>
                        <td><% out.print(prmEml.getParEmlDeNom()); %></td>
                        <td><% out.print(prmEml.getParEmlDeEml()); %></td>
                    </tr>
                    
                <%
                    }
                %>
                
                </table>
            </div>
            
  
    </body>
</html>
