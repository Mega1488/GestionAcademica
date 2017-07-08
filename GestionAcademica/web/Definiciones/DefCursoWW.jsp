<%-- 
    Document   : DefCursoWW
    Created on : 03-jul-2017, 18:28:52
    Author     : alvar
--%>
<%@page import="Enumerado.TipoMensaje"%>
<%@page import="Utiles.Retorno_MsgObj"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Entidad.Curso"%>
<%@page import="java.util.List"%>
<%@page import="Logica.LoCurso"%>
<%@page import="Utiles.Utilidades"%>
<%

    LoCurso loCurso     = LoCurso.GetInstancia();
    Utilidades utilidad = Utilidades.GetInstancia();
    String urlSistema   = utilidad.GetUrlSistema();
    
    List<Object> lstCurso = new ArrayList<>();
    
    Retorno_MsgObj retorno = (Retorno_MsgObj) loCurso.obtenerLista();
    if(retorno.getMensaje().getTipoMensaje() != TipoMensaje.ERROR && retorno.getLstObjetos() != null)
    {
        System.err.println("Lista de objeto: " + retorno.getLstObjetos().size());
        lstCurso = retorno.getLstObjetos();
        System.err.println("Lista de curso: " + lstCurso.size());
    }
    else
    {
        out.print(retorno.getMensaje().toString());
    }
    
    String tblCursoVisible = (lstCurso.size() > 0 ? "" : "display: none;");

%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gestión Académica - Cursos</title>
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
            <h1>Cursos</h1>
            
            <div>
                <a href="<% out.print(urlSistema); %>Definiciones/DefCurso.jsp?MODO=<% out.print(Enumerado.Modo.INSERT); %>">Ingresar</a>
            </div>
            
            
                <table style=' <% out.print(tblCursoVisible); %>'>
                    <tr>
                        <th></th>
                        <th></th>
                        <th>Código</th>
                        <th>Nombre</th>
                        <th>Descripción</th>
                        <th>Facultad</th>
                        <th>Certificación</th>
                    </tr>
                    
                    <% for(Object objeto : lstCurso)
                    {
                     Curso curso = (Curso) objeto;
                    %>
                    <tr>
                        <td><a href="<% out.print(urlSistema); %>Definiciones/DefCurso.jsp?MODO=<% out.print(Enumerado.Modo.DELETE); %>&pCurCod=<% out.print(curso.getCurCod()); %>" name="btn_eliminar" id="btn_eliminar" >Eliminar</a></td>
                        <td><a href="<% out.print(urlSistema); %>Definiciones/DefCurso.jsp?MODO=<% out.print(Enumerado.Modo.UPDATE); %>&pCurCod=<% out.print(curso.getCurCod()); %>" name="btn_editar" id="btn_editar" >Editar</a></td>
                        <td><% out.print( utilidad.NuloToVacio(curso.getCurCod())); %> </td>
                        <td><% out.print( utilidad.NuloToVacio(curso.getCurNom())); %> </td>
                        <td><% out.print( utilidad.NuloToVacio(curso.getCurDsc())); %> </td>
                        <td><% out.print( utilidad.NuloToVacio(curso.getCurFac())); %> </td>
                        <td><% out.print( utilidad.NuloToVacio(curso.getCurCrt())); %> </td>
                        
                    </tr>
                    <%
                    }
                    %>
                </table>
    </body>
</html>
