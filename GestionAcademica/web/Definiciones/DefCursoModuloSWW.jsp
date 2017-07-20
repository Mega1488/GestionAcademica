<%-- 
    Document   : DefCursoModuloSWW
    Created on : 03-jul-2017, 18:37:11
    Author     : alvar
--%>

<%@page import="Logica.Seguridad"%>
<%@page import="Enumerado.NombreSesiones"%>
<%@page import="Enumerado.TipoMensaje"%>
<%@page import="Utiles.Retorno_MsgObj"%>
<%@page import="Entidad.Modulo"%>
<%@page import="Enumerado.Modo"%>
<%@page import="Entidad.Curso"%>
<%@page import="java.util.List"%>
<%@page import="Logica.LoCurso"%>
<%@page import="Utiles.Utilidades"%>
<%

    LoCurso loCurso     = LoCurso.GetInstancia();
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
    
    Modo Mode           = Modo.valueOf(request.getParameter("MODO"));
    String CurCod       = request.getParameter("pCurCod");
    
    Curso curso     = new Curso();
   
    Retorno_MsgObj retorno = (Retorno_MsgObj) loCurso.obtener(Long.valueOf(CurCod));
    if(retorno.getMensaje().getTipoMensaje() != TipoMensaje.ERROR)
    {
        curso = (Curso) retorno.getObjeto();
    }
    else
    {
        out.print(retorno.getMensaje().toString());
    }
    
    String CamposActivos = "disabled";
    
    switch(Mode)
    {
        case INSERT: CamposActivos = "enabled";
            break;
        case DELETE: CamposActivos = "disabled";
            break;
        case DISPLAY: CamposActivos = "disabled";
            break;
        case UPDATE: CamposActivos = "enabled";
            break;
    }
    
    
    String tblModuloVisible = (curso.getLstModulos().size() > 0 ? "" : "display: none;");

%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gestión Académica - Curso | Modulos</title>
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
                            <jsp:include page="/Definiciones/DefCursoTabs.jsp"/>
                        </div>
                
                        <div class=""> 
                            <div class="" style="text-align: right;"><a href="<% out.print(urlSistema); %>Definiciones/DefCursoWW.jsp">Regresar</a></div>
                        </div>

                        <div style="display:none" id="datos_ocultos" name="datos_ocultos">
                            <input type="hidden" name="MODO" id="MODO" value="<% out.print(Mode); %>">
                            <input type="hidden" name="CurCod" id="CurCod" value="<% out.print(curso.getCurCod()); %>">
                        </div>

                        <div style="text-align: right; padding-top: 6px; padding-bottom: 6px;">
                            <a href="<% out.print(urlSistema); %>Definiciones/DefModulo.jsp?MODO=<% out.print(Enumerado.Modo.INSERT); %>&pCurCod=<% out.print(curso.getCurCod()); %>" title="Ingresar" class="glyphicon glyphicon-plus"></a>
                        </div>


                        <table style=' <% out.print(tblModuloVisible); %>' class='table table-hover'>
                            <thead>
                                <tr>
                                    <th></th>
                                    <th></th>
                                    <th>Código</th>
                                    <th>Nombre</th>
                                    <th>Descripción</th>
                                    <th>Período</th>
                                    <th>Horas</th>

                                </tr>
                            </thead>

                            <% for(Modulo modulo : curso.getLstModulos())
                            {

                            %>
                            <tr>
                                <td><a href="<% out.print(urlSistema); %>Definiciones/DefModulo.jsp?MODO=<% out.print(Enumerado.Modo.DELETE); %>&pCurCod=<% out.print(curso.getCurCod()); %>&pModCod=<% out.print(modulo.getModCod()); %>" name="btn_eliminar" id="btn_eliminar"  title="Eliminar" class="glyphicon glyphicon-trash"></a></td>
                                <td><a href="<% out.print(urlSistema); %>Definiciones/DefModulo.jsp?MODO=<% out.print(Enumerado.Modo.UPDATE); %>&pCurCod=<% out.print(curso.getCurCod()); %>&pModCod=<% out.print(modulo.getModCod()); %>" name="btn_editar" id="btn_editar" title="Editar"  class="glyphicon glyphicon-edit"></a></td>

                                <td><% out.print( utilidad.NuloToVacio(modulo.getModCod())); %> </td>
                                <td><% out.print( utilidad.NuloToVacio(modulo.getModNom())); %> </td>
                                <td><% out.print( utilidad.NuloToVacio(modulo.getModDsc())); %> </td>
                                <td><% out.print( utilidad.NuloToVacio(modulo.getModTpoPer().getTipoPeriodoNombre())); %> </td>
                                <td><% out.print( utilidad.NuloToVacio(modulo.getModCntHor())); %> </td>

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
