<%-- 
    Document   : DefCursoWW
    Created on : 03-jul-2017, 18:28:52
    Author     : alvar
--%>
<%@page import="Entidad.TipoEvaluacion"%>
<%@page import="Enumerado.TipoMensaje"%>
<%@page import="Utiles.Retorno_MsgObj"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Entidad.Curso"%>
<%@page import="java.util.List"%>
<%@page import="Logica.LoTipoEvaluacion"%>
<%@page import="Utiles.Utilidades"%>
<%

    LoTipoEvaluacion loTipoEvaluacion     = LoTipoEvaluacion.GetInstancia();
    Utilidades utilidad = Utilidades.GetInstancia();
    String urlSistema   = utilidad.GetUrlSistema();
    
    List<Object> lstTipoEvl = new ArrayList<>();
    
    Retorno_MsgObj retorno = (Retorno_MsgObj) loTipoEvaluacion.obtenerLista();
    if(retorno.getMensaje().getTipoMensaje() != TipoMensaje.ERROR && retorno.getLstObjetos() != null)
    {
        lstTipoEvl = retorno.getLstObjetos();
    }
    else
    {
        out.print(retorno.getMensaje().toString());
    }
    
    String tblVisible = (lstTipoEvl.size() > 0 ? "" : "display: none;");

%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gestión Académica - Tipos de evaluaciones</title>
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
            <h1>Tipos de evaluación</h1>
            
            <div>
                <a href="<% out.print(urlSistema); %>Definiciones/DefTipoEvaluacion.jsp?MODO=<% out.print(Enumerado.Modo.INSERT); %>">Ingresar</a>
            </div>
            
            
                <table style=' <% out.print(tblVisible); %>'>
                    <tr>
                        <th></th>
                        <th></th>
                        <th>Código</th>
                        <th>Nombre</th>
                        <th>Exámen</th>
                        <th>Inscripción automática</th>
                    </tr>
                    
                    <% for(Object objeto : lstTipoEvl)
                    {
                     TipoEvaluacion tpoEvl = (TipoEvaluacion) objeto;
                    %>
                    <tr>
                        <td><a href="<% out.print(urlSistema); %>Definiciones/DefTipoEvaluacion.jsp?MODO=<% out.print(Enumerado.Modo.DELETE); %>&pTpoEvlCod=<% out.print(tpoEvl.getTpoEvlCod()); %>" name="btn_eliminar" id="btn_eliminar" >Eliminar</a></td>
                        <td><a href="<% out.print(urlSistema); %>Definiciones/DefTipoEvaluacion.jsp?MODO=<% out.print(Enumerado.Modo.UPDATE); %>&pTpoEvlCod=<% out.print(tpoEvl.getTpoEvlCod()); %>" name="btn_editar" id="btn_editar" >Editar</a></td>
                        <td><% out.print( utilidad.NuloToVacio(tpoEvl.getTpoEvlCod())); %> </td>
                        <td><% out.print( utilidad.NuloToVacio(tpoEvl.getTpoEvlNom())); %> </td>
                        <td><% out.print( utilidad.BooleanToSiNo(tpoEvl.getTpoEvlExm())); %> </td>
                        <td><% out.print( utilidad.BooleanToSiNo(tpoEvl.getTpoEvlInsAut())); %> </td>
                    </tr>
                    <%
                    }
                    %>
                </table>
    </body>
</html>
