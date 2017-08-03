<%-- 
    Document   : DefCalendarioWW
    Created on : 03-jul-2017, 18:28:52
    Author     : alvar
--%>
<%@page import="Entidad.NotificacionBitacora"%>
<%@page import="Entidad.NotificacionBitacora"%>
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
    
    String NotCod = request.getParameter("pNotCod");
    
    List<NotificacionBitacora> lstObjeto = new ArrayList<>();
    
    Retorno_MsgObj retorno = (Retorno_MsgObj) LoNotificacion.GetInstancia().obtener(Long.valueOf(NotCod));
    if(!retorno.SurgioError())
    {
        Notificacion notificacion = (Notificacion) retorno.getObjeto();
        lstObjeto = notificacion.getLstBitacora();
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
        <title>Sistema de Gestión Académica - Bitacora</title>
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
                        
                       <div id="tabs" name="tabs" class="contenedor-tabs">
                            <jsp:include page="/Definiciones/DefNotificacionTabs.jsp"/>
                        </div>

                        <div class=""> 
                            <div class="" style="text-align: right;"><a href="<% out.print(urlSistema); %>Definiciones/DefNotificacionWW.jsp">Regresar</a></div>
                        </div>
                        
                        <table style=' <% out.print(tblVisible); %>' class='table table-hover'>
                            <thead>
                                <tr>
                                    <th></th>
                                    <th></th>
                                    <th>Código</th>
                                    <th>Fecha</th>
                                    <th>Estado</th>
                                </tr>
                            </thead>

                            <% for(NotificacionBitacora bitacora : lstObjeto)
                            {

                            %>
                            <tr>
                                <td><a href="<% out.print(urlSistema); %>Definiciones/DefNotificacionBitacora.jsp?MODO=<% out.print(Enumerado.Modo.DELETE); %>&pNotCod=<% out.print(bitacora.getNotificacion().getNotCod()); %>&pNotBitCod=<% out.print(bitacora.getNotBitCod()); %>" name="btn_eliminar" id="btn_eliminar" title="Eliminar" class="glyphicon glyphicon-trash"/></td>
                                <td><a href="<% out.print(urlSistema); %>Definiciones/DefNotificacionBitacora.jsp?MODO=<% out.print(Enumerado.Modo.DISPLAY); %>&pNotCod=<% out.print(bitacora.getNotificacion().getNotCod()); %>&pNotBitCod=<% out.print(bitacora.getNotBitCod()); %>" name="btn_ver" id="btn_ver" title="Ver" class='glyphicon glyphicon-search'/></td>
                                <td><% out.print( utilidad.NuloToVacio(bitacora.getNotBitCod())); %> </td>
                                <td><% out.print( utilidad.NuloToVacio(bitacora.getNotBitFch())); %> </td>
                                <td><% out.print( utilidad.NuloToVacio(bitacora.getNotBitEst().getNombre())); %> </td>

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
