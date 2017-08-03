<%-- 
    Document   : DefCalendarioWW
    Created on : 03-jul-2017, 18:28:52
    Author     : alvar
--%>
<%@page import="Entidad.Notificacion"%>
<%@page import="Logica.LoNotificacion"%>
<%@page import="Logica.Seguridad"%>
<%@page import="Enumerado.NombreSesiones"%>
<%@page import="Enumerado.TipoMensaje"%>
<%@page import="Utiles.Retorno_MsgObj"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Entidad.Calendario"%>
<%@page import="java.util.List"%>
<%@page import="Logica.LoCalendario"%>
<%@page import="Utiles.Utilidades"%>
<%

    Utilidades utilidad         = Utilidades.GetInstancia();
    String urlSistema           = (String) session.getAttribute(NombreSesiones.URL_SISTEMA.getValor());
    
    //----------------------------------------------------------------------------------------------------
    //CONTROL DE ACCESO
    //----------------------------------------------------------------------------------------------------
    
    String  usuario = (String) session.getAttribute(NombreSesiones.USUARIO.getValor());
    Boolean esAdm   = (Boolean) session.getAttribute(NombreSesiones.USUARIO_ADM.getValor());
    Boolean esAlu   = (Boolean) session.getAttribute(NombreSesiones.USUARIO_ALU.getValor());
    Boolean esDoc   = (Boolean) session.getAttribute(NombreSesiones.USUARIO_DOC.getValor());
    Retorno_MsgObj acceso = Seguridad.GetInstancia().ControlarAcceso(usuario, esAdm, esDoc, esAlu, utilidad.GetPaginaActual(request));
    
    if(acceso.SurgioError()) response.sendRedirect((String) acceso.getObjeto());
            
    //----------------------------------------------------------------------------------------------------
    
    List<Object> lstObjeto = new ArrayList<>();
    
    Retorno_MsgObj retorno = (Retorno_MsgObj) LoNotificacion.GetInstancia().obtenerLista();
    if(!retorno.SurgioError())
    {
        lstObjeto = retorno.getLstObjetos();
    }
    else
    {
        out.print(retorno.getMensaje().toString());
    }
    
    String tblVisible = (lstObjeto.size() > 0 ? "" : "display: none;");

%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gestión Académica - Notificaciones</title>
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
                            <p>Notificaciones</p>
                        </div> 
                        
                        <div style="text-align: right; padding-top: 6px; padding-bottom: 6px;">
                            <a href="<% out.print(urlSistema); %>Definiciones/DefNotificacion.jsp?MODO=<% out.print(Enumerado.Modo.INSERT); %>" title="Ingresar" class="glyphicon glyphicon-plus"> </a>
                        </div>

                        <table style=' <% out.print(tblVisible); %>' class='table table-hover'>
                            <thead>
                                <tr>
                                    <th></th>
                                    <th></th>
                                    <th>Código</th>
                                    <th>Nombre</th>
                                    <th>Descripción</th>
                                    <th>Tipo</th>
                                    <th>Medio</th>
                                    <th>Activa</th>
                                </tr>
                            </thead>

                            <% for(Object objeto : lstObjeto)
                            {
                                Notificacion notificacion = (Notificacion) objeto;
                            %>
                            <tr>
                                <td><a href="<% out.print(urlSistema); %>Definiciones/DefNotificacion.jsp?MODO=<% out.print(Enumerado.Modo.DELETE); %>&pNotCod=<% out.print(notificacion.getNotCod()); %>" name="btn_eliminar" id="btn_eliminar" title="Eliminar" class="glyphicon glyphicon-trash"/></td>
                                <td><a href="<% out.print(urlSistema); %>Definiciones/DefNotificacion.jsp?MODO=<% out.print(Enumerado.Modo.UPDATE); %>&pNotCod=<% out.print(notificacion.getNotCod()); %>" name="btn_editar" id="btn_editar" title="Editar" class='glyphicon glyphicon-edit'/></td>
                                <td><% out.print( utilidad.NuloToVacio(notificacion.getNotCod())); %> </td>
                                <td><% out.print( utilidad.NuloToVacio(notificacion.getNotNom())); %> </td>
                                <td><% out.print( utilidad.NuloToVacio(notificacion.getNotDsc())); %> </td>
                                <td><% out.print( utilidad.NuloToVacio(notificacion.getNotTpo().getNombre())); %> </td>
                                <td><% out.print( utilidad.NuloToVacio(notificacion.getMedio())); %> </td>
                                <td><% out.print( utilidad.BooleanToSiNo(notificacion.getNotAct())); %> </td>

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
