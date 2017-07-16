<%-- 
    Document   : DefCalendarioWW
    Created on : 03-jul-2017, 18:28:52
    Author     : alvar
--%>
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

    LoCalendario loCalendario     = LoCalendario.GetInstancia();
    Utilidades utilidad = Utilidades.GetInstancia();
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
    
    Retorno_MsgObj retorno = (Retorno_MsgObj) loCalendario.obtenerLista();
    if(!retorno.SurgioErrorListaRequerida())
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
        <title>Sistema de Gestión Académica - Calendario</title>
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
                    <h1>Calendario</h1>

                    <div id="tabs" name="tabs">
                        <jsp:include page="/Definiciones/DefCalendarioWWTabs.jsp"/>
                    </div>

                    <div style="text-align: right; padding-top: 6px; padding-bottom: 6px;">
                        <a href="<% out.print(urlSistema); %>Definiciones/DefCalendario.jsp?MODO=<% out.print(Enumerado.Modo.INSERT); %>" title="Ingresar" class="glyphicon glyphicon-plus"> </a>
                    </div>


                        <table style=' <% out.print(tblVisible); %>' class='table table-hover'>
                            <tr>
                                <th></th>
                                <th></th>
                                <th>Código</th>
                                <th>Evaluación</th>
                                <th>Estudio</th>
                                <th>Nombre</th>
                                <th>Fecha</th>
                                <th>Inscripción desde</th>
                                <th>Inscripcion hasta</th>
                            </tr>

                            <% for(Object objeto : lstObjeto)
                            {
                             Calendario calendario = (Calendario) objeto;
                            %>
                            <tr>
                                <td><a href="<% out.print(urlSistema); %>Definiciones/DefCalendario.jsp?MODO=<% out.print(Enumerado.Modo.DELETE); %>&pCalCod=<% out.print(calendario.getCalCod()); %>" name="btn_eliminar" id="btn_eliminar" title="Eliminar" class="glyphicon glyphicon-trash"/></td>
                                <td><a href="<% out.print(urlSistema); %>Definiciones/DefCalendario.jsp?MODO=<% out.print(Enumerado.Modo.UPDATE); %>&pCalCod=<% out.print(calendario.getCalCod()); %>" name="btn_editar" id="btn_editar" title="Editar" class='glyphicon glyphicon-edit'/></td>
                                <td><% out.print( utilidad.NuloToVacio(calendario.getCalCod())); %> </td>
                                <td><% out.print( utilidad.NuloToVacio(calendario.getEvaluacion().getEvlNom() )); %> </td>
                                <td><% out.print( utilidad.NuloToVacio(calendario.getEvaluacion().getEstudioTipo())); %> </td>
                                <td><% out.print( utilidad.NuloToVacio(calendario.getEvaluacion().getEstudioNombre())); %> </td>
                                <td><% out.print( utilidad.NuloToVacio(calendario.getCalFch())); %> </td>
                                <td><% out.print( utilidad.NuloToVacio(calendario.getEvlInsFchDsd())); %> </td>
                                <td><% out.print( utilidad.NuloToVacio(calendario.getEvlInsFchHst())); %> </td>

                            </tr>
                            <%
                            }
                            %>
                        </table>

                </div>
        </div>
    </body>
</html>
