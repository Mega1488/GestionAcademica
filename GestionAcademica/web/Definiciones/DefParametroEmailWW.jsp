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
    
    String tblVisible = (lstParamEml.size() > 0 ? "" : "display: none;");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gestión Académica - Versión</title>
        <jsp:include page="/masterPage/head.jsp"/>
    </head>
    <body>
     <jsp:include page="/masterPage/NotificacionError.jsp"/>
        <div class="wrapper">
            <jsp:include page="/masterPage/menu_izquierdo.jsp" />
            <div id="contenido" name="contenido" class="main-panel">
                
                <div class="contenedor-cabezal">
                    <jsp:include page="/masterPage/cabezal.jsp"/>
                </div>
                
                <div class="contenedor-principal">
                    <div class="col-sm-11 contenedor-texto-titulo-flotante">
                        
                        <div class="contenedor-titulo">    
                            <p>Parámetros de email</p>
                        </div>  
                        
                        <div style="text-align: right; padding-top: 6px; padding-bottom: 6px;">
                            <a href="<% out.print(urlSistema); %>Definiciones/DefParametroEmail.jsp?MODO=<% out.print(Enumerado.Modo.INSERT); %>" title="Ingresar" class="glyphicon glyphicon-plus"> </a>
                        </div>
                        
                        <div style="display:none" id="datos_ocultos" name="datos_ocultos">
                            <input type="hidden" name="LISTA" id="LISTA" value="">
                        </div>
                
                        <table style=' <% out.print(tblVisible); %>' class='table table-hover'>
                            <thead> 
                                <tr>
                                    <th></th>
                                    <th></th>
                                    <th>Código</th>
                                    <th>Nombre</th>
                                    <th>Servidor de correo</th>
                                    <th>Nombre del remitente</th>
                                    <th>Email del remitente</th>
                                </tr>
                            </thead>
                        <% for(ParametroEmail prmEml : lstParamEml)
                            {

                        %>
                            <tr>
                                <td><a href="<% out.print(urlSistema); %>Definiciones/DefParametroEmail.jsp?MODO=<% out.print(Enumerado.Modo.DELETE); %>&pParEmlCod=<% out.print(prmEml.getParEmlCod()); %>" name="btn_eliminar" id="btn_eliminar"   title='Eliminar' class='glyphicon glyphicon-trash btn_eliminar'></a></td>
                                <td><a href="<% out.print(urlSistema); %>Definiciones/DefParametroEmail.jsp?MODO=<% out.print(Enumerado.Modo.UPDATE); %>&pParEmlCod=<% out.print(prmEml.getParEmlCod()); %>" name="btn_editar" id="btn_editar"   title='Editar' class='glyphicon glyphicon-edit btn_editar'></a></td>
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
                </div>
            </div>
        </div>
    </body>
</html>
