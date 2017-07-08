<%-- 
    Document   : DefCursoEvaluacionSWW
    Created on : 06-jul-2017, 20:02:25
    Author     : alvar
--%>

<%@page import="Enumerado.TipoMensaje"%>
<%@page import="Utiles.Retorno_MsgObj"%>
<%@page import="Entidad.Evaluacion"%>
<%@page import="Entidad.Modulo"%>
<%@page import="Enumerado.Modo"%>
<%@page import="Entidad.Curso"%>
<%@page import="java.util.List"%>
<%@page import="Logica.LoCurso"%>
<%@page import="Utiles.Utilidades"%>
<%

    LoCurso loCurso     = LoCurso.GetInstancia();
    Utilidades utilidad = Utilidades.GetInstancia();
    String urlSistema   = utilidad.GetUrlSistema();
    Modo Mode           = Modo.valueOf(request.getParameter("MODO"));
    String CurCod       = request.getParameter("pCurCod");
    String ModCod       = request.getParameter("pModCod");
    
    Curso curso     = new Curso();
    Modulo modulo   = new Modulo();

    Retorno_MsgObj retorno = (Retorno_MsgObj) loCurso.obtener(Long.valueOf(CurCod));
    if(retorno.getMensaje().getTipoMensaje() != TipoMensaje.ERROR)
    {
        curso = (Curso) retorno.getObjeto();
        modulo = curso.getModuloById(Long.valueOf(ModCod));
    }
    else
    {
        out.print(retorno.getMensaje().toString());
    }
    
   
    
    
    String tblVisible = (modulo.getLstEvaluacion().size() > 0 ? "" : "display: none;");

%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gestión Académica - Modulo | Evaluación</title>
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
            <div id="tabs" name="tabs">
                <jsp:include page="/Definiciones/DefModuloTabs.jsp"/>
            </div>
            
            <h1>Modulo | Evaluaciones</h1>
            
            <div style="display:none" id="datos_ocultos" name="datos_ocultos">
                <input type="hidden" name="MODO" id="MODO" value="<% out.print(Mode); %>">
                <input type="hidden" name="CurCod" id="CurCod" value="<% out.print(curso.getCurCod()); %>">
                <input type="hidden" name="ModCod" id="ModCod" value="<% out.print(modulo.getModCod()); %>">
            </div>

            <div>
                <a href="<% out.print(urlSistema); %>Definiciones/DefEvaluacion.jsp?MODO=<% out.print(Enumerado.Modo.INSERT); %>&pRelacion=MODULO&pModEvlCurCod=<% out.print(curso.getCurCod()); %>&pModEvlModCod=<% out.print(modulo.getModCod()); %>">Ingresar</a>
            </div>
            
            
                <table style=' <% out.print(tblVisible); %>'>
                    <tr>
                        <th></th>
                        <th></th>
                        <th>Código</th>
                        <th>Nombre</th>
                        <th>Descripción</th>
                        <th>Tipo</th>
                        <th>Nota toal</th>

                    </tr>
                    
                    <% for(Evaluacion evaluacion : modulo.getLstEvaluacion())
                    {
                     
                    %>
                    <tr>
                        <td><a href="<% out.print(urlSistema); %>Definiciones/DefEvaluacion.jsp?MODO=<% out.print(Enumerado.Modo.DELETE); %>&pRelacion=MODULO&pModEvlCurCod=<% out.print(curso.getCurCod()); %>&pModEvlModCod=<% out.print(modulo.getModCod()); %>&pEvlCod=<% out.print(evaluacion.getEvlCod()); %>" name="btn_eliminar" id="btn_eliminar" >Eliminar</a></td>
                        <td><a href="<% out.print(urlSistema); %>Definiciones/DefEvaluacion.jsp?MODO=<% out.print(Enumerado.Modo.UPDATE); %>&pRelacion=MODULO&pModEvlCurCod=<% out.print(curso.getCurCod()); %>&pModEvlModCod=<% out.print(modulo.getModCod()); %>&pEvlCod=<% out.print(evaluacion.getEvlCod()); %>" name="btn_editar" id="btn_editar" >Editar</a></td>
                        
                        <td><% out.print( utilidad.NuloToVacio(evaluacion.getEvlCod())); %> </td>
                        <td><% out.print( utilidad.NuloToVacio(evaluacion.getEvlNom())); %> </td>
                        <td><% out.print( utilidad.NuloToVacio(evaluacion.getEvlDsc())); %> </td>
                        <td><% out.print( utilidad.NuloToVacio(evaluacion.getEvlNotTot())); %> </td>
                        <td><% out.print( utilidad.NuloToVacio(evaluacion.getTpoEvl())); %> </td>
                        
                    </tr>
                    <%
                    }
                    %>
                </table>

        </div>
    </body>
</html>

