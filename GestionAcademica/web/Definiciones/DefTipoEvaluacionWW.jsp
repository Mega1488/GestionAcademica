<%-- 
    Document   : DefCursoWW
    Created on : 03-jul-2017, 18:28:52
    Author     : alvar
--%>
<%@page import="Logica.Seguridad"%>
<%@page import="Enumerado.NombreSesiones"%>
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
        <div class="container-fluid">
            
            <div id="cabezal" name="cabezal" class="row">
                <jsp:include page="/masterPage/cabezal.jsp"/>
            </div>
        
        
            <div class="col-sm-2">
                <jsp:include page="/masterPage/menu_izquierdo.jsp" />
            </div>

            <div id="contenido" name="contenido"  class="col-sm-8">
                <h1>Tipos de evaluación</h1>
                
                <div style="text-align: right; padding-top: 6px; padding-bottom: 6px;">
                    <a href="<% out.print(urlSistema); %>Definiciones/DefTipoEvaluacion.jsp?MODO=<% out.print(Enumerado.Modo.INSERT); %>" title="Ingresar" class="glyphicon glyphicon-plus"> </a>
                </div>
            
            
                <table style=' <% out.print(tblVisible); %>' class='table table-hover'>
                    <thead>
                        <tr>
                            <th></th>
                            <th></th>
                            <th>Código</th>
                            <th>Nombre</th>
                            <th>Exámen</th>
                            <th>Inscripción automática</th>
                        </tr>
                    </thead>
                    
                    <% for(Object objeto : lstTipoEvl)
                    {
                     TipoEvaluacion tpoEvl = (TipoEvaluacion) objeto;
                    %>
                    <tr>
                        <td><a href="<% out.print(urlSistema); %>Definiciones/DefTipoEvaluacion.jsp?MODO=<% out.print(Enumerado.Modo.DELETE); %>&pTpoEvlCod=<% out.print(tpoEvl.getTpoEvlCod()); %>" name="btn_eliminar" id="btn_eliminar"  title='Eliminar' class='glyphicon glyphicon-trash btn_eliminar'></a></td>
                        <td><a href="<% out.print(urlSistema); %>Definiciones/DefTipoEvaluacion.jsp?MODO=<% out.print(Enumerado.Modo.UPDATE); %>&pTpoEvlCod=<% out.print(tpoEvl.getTpoEvlCod()); %>" name="btn_editar" id="btn_editar"  title='Editar' class='glyphicon glyphicon-edit btn_editar'></a></td>
                        <td><% out.print( utilidad.NuloToVacio(tpoEvl.getTpoEvlCod())); %> </td>
                        <td><% out.print( utilidad.NuloToVacio(tpoEvl.getTpoEvlNom())); %> </td>
                        <td><% out.print( utilidad.BooleanToSiNo(tpoEvl.getTpoEvlExm())); %> </td>
                        <td><% out.print( utilidad.BooleanToSiNo(tpoEvl.getTpoEvlInsAut())); %> </td>
                    </tr>
                    <%
                    }
                    %>
                </table>
            </div>
        </div>
    </body>
</html>
