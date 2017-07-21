<%-- 
    Document   : PCarrera
    Created on : jun 17, 2017, 11:12:28 p.m.
    Author     : aa
--%>

<%@page import="Logica.Seguridad"%>
<%@page import="Enumerado.NombreSesiones"%>
<%@page import="Enumerado.TipoMensaje"%>
<%@page import="Utiles.Retorno_MsgObj"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="Logica.LoCarrera"%>
<%@page import="Entidad.Carrera"%>
<%@page import="Utiles.Utilidades"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    Utilidades utilidad = Utilidades.GetInstancia();
    LoCarrera loCar     = LoCarrera.GetInstancia();    
    String urlSistema   = (String) session.getAttribute(NombreSesiones.URL_SISTEMA.getValor());
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
    
    List<Object> lstCarrera = new ArrayList<>();
    
    Retorno_MsgObj retorno = (Retorno_MsgObj) loCar.obtenerLista();
    if(retorno.getMensaje().getTipoMensaje() != TipoMensaje.ERROR && retorno.getLstObjetos() != null)
    {
        lstCarrera = retorno.getLstObjetos();
    }
    else
    {
        out.print(retorno.getMensaje().toString());
    }
    
    String tblCarreraVisible = (lstCarrera.size() > 0 ? "" : "display: none;");
%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Sistema de Gestión Académica - Carreras</title>
    <jsp:include page="/masterPage/head.jsp"/>
</head>
    <body>
        <div class="wrapper">
            <jsp:include page="/masterPage/menu_izquierdo.jsp" />
            <div id="contenido" name="contenido"  class="main-panel">
                <div class="contenedor-cabezal">
                    <jsp:include page="/masterPage/cabezal.jsp"/>
                </div>
                <div class="contenedor-principal">
                    <div class="col-sm-11 contenedor-texto-titulo-flotante">
                        <div class="contenedor-titulo">    
                            <p>Carreras</p>
                        </div>

                        <div style="text-align: right; padding-top: 6px; padding-bottom: 6px;">
                            <a href="<% out.print(urlSistema); %>Definiciones/DefCarrera.jsp?MODO=<% out.print(Enumerado.Modo.INSERT); %>" title="Ingresar" class="glyphicon glyphicon-plus"> </a>
                        </div>

                        <table style=' <% out.print(tblCarreraVisible); %>' class='table table-hover'>
                            <thead>
                                <tr>
                                    <th></th>
                                    <th></th>
                                    <th>Código</th>
                                    <th>Nombre</th>
                                    <th>Facultad</th>
                                    <th>Categoría</th>
                                </tr>    
                            </thead>
                            <%
                            for(Object obj : lstCarrera)
                            {
                                Carrera car = (Carrera)obj;
                            %>
                            <tr>
                                <td><a href="<% out.print(urlSistema); %>Definiciones/DefCarrera.jsp?MODO=<% out.print(Enumerado.Modo.DELETE); %>&pCarCod=<% out.print(car.getCarCod()); %>" name="btn_eliminar" id="btn_eliminar" title="Eliminar" class="glyphicon glyphicon-trash"></a></td>
                                <td><a href="<% out.print(urlSistema); %>Definiciones/DefCarrera.jsp?MODO=<% out.print(Enumerado.Modo.UPDATE); %>&pCarCod=<% out.print(car.getCarCod()); %>" name="btn_editar" id="btn_editar" title="Editar" class="glyphicon glyphicon-edit"></a></td>
                                <td><% out.print(utilidad.NuloToCero(car.getCarCod())); %></td>
                                <td><% out.print(utilidad.NuloToVacio(car.getCarNom())); %></td>
                                <td><% out.print(utilidad.NuloToVacio(car.getCarDsc())); %></td>
                                <td><% out.print(utilidad.NuloToVacio(car.getCarFac())); %></td>
                                <td><% out.print(utilidad.NuloToVacio(car.getCarCrt())); %></td>
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