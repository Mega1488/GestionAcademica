<%-- 
    Document   : DefPlanEstudioWW
    Created on : jul 7, 2017, 8:10:09 p.m.
    Author     : aa
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="Entidad.Carrera"%>
<%@page import="Logica.LoCarrera"%>
<%@page import="Utiles.Retorno_MsgObj"%>
<%@page import="Logica.Seguridad"%>
<%@page import="Enumerado.NombreSesiones"%>
<%@page import="Enumerado.Modo"%>
<%@page import="java.util.List"%>
<%@page import="Entidad.PlanEstudio"%>
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

    String CarCod       = request.getParameter("pCarCod");
    List<PlanEstudio> lstPlanEstudio = new ArrayList<>();
    Carrera car = new Carrera();
//    PlanEstudio plan = new PlanEstudio();
    
    Retorno_MsgObj retorno = (Retorno_MsgObj) loCar.obtener(Long.valueOf(CarCod));
    if(!retorno.SurgioErrorObjetoRequerido())
    {
        car = (Carrera) retorno.getObjeto();
        lstPlanEstudio = car.getPlan();
    }
    else
    {
        out.print(retorno.getMensaje().toString());
    }

    String tblPlanEstudioVisible = (lstPlanEstudio.size() > 0 ? "" : "display: none;");
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gestión Académica - Planes de Estudio</title>
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

                        <div id="tabs" name="tabs" class="contenedor-tabs">
                            <jsp:include page="/Definiciones/DefCarreraTabs.jsp"/>
                        </div>
                        
                        <div class=""> 
                            <div class="" style="text-align: right;"><a href="<% out.print(urlSistema); %>Definiciones/DefCarreraWW.jsp?MODE=<%out.print(Enumerado.Modo.DISPLAY);%>">Regresar</a></div>
                        </div>
                        
                        <div style="text-align: right; padding-top: 6px; padding-bottom: 6px;">
                            <a href="<% out.print(urlSistema); %>Definiciones/DefPlanEstudio.jsp?MODO=<% out.print(Enumerado.Modo.INSERT); %>&pCarCod=<%out.print(CarCod.toString());%>" title="Ingresar" class="glyphicon glyphicon-plus"> </a>
                        </div>
                        
                        <table style='<% out.print(tblPlanEstudioVisible); %>' class='table table-hover'>
                            <thead>
                                <tr>
                                    <th></th>
                                    <th></th>
                                    <th>Código</th>
                                    <th>Nombre</th>
                                    <th>Descripción</th>
                                    <th>Creditos Necesarios</th>
                                </tr>
                            </thead>
                            <%
                                for(PlanEstudio PE : lstPlanEstudio)
                                {
                            %>
                            <tr>
                                <td><a href="<% out.print(urlSistema); %>Definiciones/DefPlanEstudio.jsp?MODO=<% out.print(Enumerado.Modo.DELETE); %>&pCarCod=<% out.print(CarCod.toString()); %>&pPlaEstCod=<% out.print(PE.getPlaEstCod()); %>" name="btn_eliminar" id="btn_eliminar" title="Eliminar" class="glyphicon glyphicon-trash"></a></td>
                                <td><a href="<% out.print(urlSistema); %>Definiciones/DefPlanEstudio.jsp?MODO=<% out.print(Enumerado.Modo.UPDATE); %>&pCarCod=<% out.print(CarCod.toString()); %>&pPlaEstCod=<% out.print(PE.getPlaEstCod()); %>" name="btn_editar" id="btn_editar" title="Editar" class="glyphicon glyphicon-edit"></a></td>
                                <td><% out.print(utilidad.NuloToCero(PE.getPlaEstCod())); %></td>
                                <td><% out.print(utilidad.NuloToVacio(PE.getPlaEstNom())); %></td>
                                <td><% out.print(utilidad.NuloToVacio(PE.getPlaEstDsc())); %></td>
                                <td><% out.print(utilidad.NuloToVacio(PE.getPlaEstCreNec())); %></td>
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
