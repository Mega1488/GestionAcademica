<%-- 
    Document   : DefVersion
    Created on : 25-jun-2017, 21:43:57
    Author     : alvar
--%>

<%@page import="Utiles.Retorno_MsgObj"%>
<%@page import="Logica.LoParametroEmail"%>
<%@page import="Entidad.ParametroEmail"%>
<%@page import="java.util.List"%>
<%@page import="Utiles.Utilidades"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Utilidades utilidad    = Utilidades.GetInstancia();
    String urlSistema           = (String) session.getAttribute(Enumerado.NombreSesiones.URL_SISTEMA.getValor());
    
    //----------------------------------------------------------------------------------------------------
    //CONTROL DE ACCESO
    //----------------------------------------------------------------------------------------------------
    
    String  usuario = (String) session.getAttribute(Enumerado.NombreSesiones.USUARIO.getValor());
    Boolean esAdm   = (Boolean) session.getAttribute(Enumerado.NombreSesiones.USUARIO_ADM.getValor());
    Boolean esAlu   = (Boolean) session.getAttribute(Enumerado.NombreSesiones.USUARIO_ALU.getValor());
    Boolean esDoc   = (Boolean) session.getAttribute(Enumerado.NombreSesiones.USUARIO_DOC.getValor());
    Retorno_MsgObj acceso = Logica.Seguridad.GetInstancia().ControlarAcceso(usuario, esAdm, esDoc, esAlu, utilidad.GetPaginaActual(request));
    
    if(acceso.SurgioError()) response.sendRedirect((String) acceso.getObjeto());
            
    //----------------------------------------------------------------------------------------------------
    
    LoParametroEmail  loParamEml     = LoParametroEmail.GetInstancia();
    List<ParametroEmail> lstParamEml = loParamEml.obtenerLista();
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gestión Académica - Versión</title>
        <jsp:include page="/masterPage/head.jsp"/>
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
                <div> <a href="<% out.print(urlSistema); %>Definiciones/DefParametroEmail.jsp?MODO=<% out.print(Enumerado.Modo.INSERT); %>">Ingresar</a> </div>
                
                <table>
                    <tr>
                        <th></th>
                        <th></th>
                        <th>Código</th>
                        <th>Nombre</th>
                        <th>Servidor de correo</th>
                        <th>Nombre del remitente</th>
                        <th>Email del remitente</th>
                    </tr>
                <% for(ParametroEmail prmEml : lstParamEml)
                    {
                     
                %>
                    <tr>
                        <td><a href="<% out.print(urlSistema); %>Definiciones/DefParametroEmail.jsp?MODO=<% out.print(Enumerado.Modo.DELETE); %>&pParEmlCod=<% out.print(prmEml.getParEmlCod()); %>" name="btn_eliminar" id="btn_eliminar" >Eliminar</a></td>
                        <td><a href="<% out.print(urlSistema); %>Definiciones/DefParametroEmail.jsp?MODO=<% out.print(Enumerado.Modo.UPDATE); %>&pParEmlCod=<% out.print(prmEml.getParEmlCod()); %>" name="btn_editar" id="btn_editar" >Editar</a></td>
                        <td><% out.print(utilidad.NuloToCero(prmEml.getParEmlCod())); %></td>
                        <td><% out.print(utilidad.NuloToVacio(prmEml.getParEmlNom())); %></td>
                        <td><% out.print(utilidad.NuloToVacio(prmEml.getParEmlSrv())); %></td>
                        <td><% out.print(utilidad.NuloToVacio(prmEml.getParEmlDeNom())); %></td>
                        <td><% out.print(utilidad.NuloToVacio(prmEml.getParEmlDeEml())); %></td>
                    </tr>
                    
                <%
                    }
                %>
                
                </table>
            </div>
            
  
    </body>
</html>
