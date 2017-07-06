<%-- 
    Document   : DefCursoWW
    Created on : 03-jul-2017, 18:28:52
    Author     : alvar
--%>
<%@page import="Entidad.Curso"%>
<%@page import="java.util.List"%>
<%@page import="Logica.LoCurso"%>
<%@page import="Utiles.Utilidades"%>
<%

    LoCurso loCurso     = LoCurso.GetInstancia();
    Utilidades utilidad = Utilidades.GetInstancia();
    String urlSistema   = utilidad.GetUrlSistema();
    
    List<Curso> lstCurso = loCurso.obtenerLista();
    
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
                    
                    <% for(Curso curso : lstCurso)
                    {
                     
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
