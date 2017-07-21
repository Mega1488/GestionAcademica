<%-- 
    Document   : DefMateriaWW
    Created on : jul 17, 2017, 4:22:19 p.m.
    Author     : aa
--%>

<%@page import="Entidad.Materia"%>
<%@page import="Enumerado.TipoMensaje"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="Entidad.PlanEstudio"%>
<%@page import="Entidad.Carrera"%>
<%@page import="Logica.Seguridad"%>
<%@page import="Utiles.Retorno_MsgObj"%>
<%@page import="Enumerado.NombreSesiones"%>
<%@page import="Logica.LoCarrera"%>
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
    String PlaEstCod    = request.getParameter("pPlaEstCod");
    
    PlanEstudio plan = new PlanEstudio();
    Carrera car = new Carrera();
    
    List<Materia> lstMaterias = new ArrayList<>();
    
    Retorno_MsgObj retorno = (Retorno_MsgObj) loCar.obtener(Long.valueOf(CarCod));
        
    if(retorno.getMensaje().getTipoMensaje() != TipoMensaje.ERROR && retorno.getObjeto() != null)
    {
        car = (Carrera) retorno.getObjeto();
        plan = car.getPlanEstudioById(Long.valueOf(PlaEstCod));
        lstMaterias = plan.getLstMateria();
    }
    else
    {
        out.print(retorno.getMensaje().toString());
    }
    
    String tblMateriaVisible = (lstMaterias.size() > 0 ? "" : "display: none;");
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gestión Académica - Materias</title>
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
                            <jsp:include page="/Definiciones/DefPlanEstudioTabs.jsp"/>
                        </div>
                        
                        <div class=""> 
                            <div class="" style="text-align: right;"><a href="<% out.print(urlSistema); %>Definiciones/DefPlanEstudioSWW.jsp?MODO=<%out.print(Enumerado.Modo.DISPLAY);%>&pPlaEstCod=<%out.print(PlaEstCod.toString());%>&pCarCod=<%out.print(CarCod.toString());%>">Regresar</a></div>
                        </div>
                        
                        <div style="text-align: right; padding-top: 6px; padding-bottom: 6px;">
                            <a href="<% out.print(urlSistema); %>Definiciones/DefMateria.jsp?MODO=<% out.print(Enumerado.Modo.INSERT); %>&pPlaEstCod=<%out.print(PlaEstCod.toString());%>&pCarCod=<%out.print(CarCod.toString());%>" title="Ingresar" class="glyphicon glyphicon-plus"> </a>
                        </div>

                        <table style=' <% out.print(tblMateriaVisible); %>' class='table table-hover'>
                            <thead>
                                <tr>
                                    <th></th>
                                    <th></th>
                                    <th>Código</th>
                                    <th>Nombre</th>
                                    <th>Cantidad de Horas</th>
                                    <th>Tipo de Aprobación</th>
                                    <th>Tipo de Período</th>
                                    <th>Valor del Período</th>
                                    <th>Materia Previa</th>
                                </tr>
                            </thead>
                            
                            <%
                                for(Materia mat : lstMaterias)
                                {
                            %>
                            <tr>
                                <td><a href="<% out.print(urlSistema); %>Definiciones/DefMateria.jsp?MODO=<% out.print(Enumerado.Modo.DELETE); %>&pPlaEstCod=<% out.print(PlaEstCod.toString()); %>&pCarCod=<% out.print(CarCod.toString()); %>&pMatCod=<% out.print(mat.getMatCod().toString()); %>" name="btn_eliminar" id="btn_eliminar" title="Eliminar" class="glyphicon glyphicon-trash"></a></td>
                                <td><a href="<% out.print(urlSistema); %>Definiciones/DefMateria.jsp?MODO=<% out.print(Enumerado.Modo.UPDATE); %>&pPlaEstCod=<% out.print(PlaEstCod.toString()); %>&pCarCod=<% out.print(CarCod.toString()); %>&pMatCod=<% out.print(mat.getMatCod().toString()); %>" name="btn_editar" id="btn_editar" title="Editar" class="glyphicon glyphicon-edit"></a></td>
                                <td><% out.print( utilidad.NuloToVacio(mat.getMatCod())); %> </td>
                                <td><% out.print( utilidad.NuloToVacio(mat.getMatNom())); %> </td>
                                <td><% out.print( utilidad.NuloToVacio(mat.getMatCntHor())); %> </td>
                                <td><% out.print( utilidad.NuloToVacio(mat.getMatTpoApr().getTipoAprobacionN())); %> </td>
                                <td><% out.print( utilidad.NuloToVacio(mat.getMatTpoPer().getTipoPeriodoNombre())); %> </td>
                                <td><% out.print( utilidad.NuloToVacio(mat.getMatPerVal())); %> </td>
                                <td>
                                <%
                                    if(mat.getMateriaPrevia() != null)
                                    {
                                %>
                                    <% out.print( utilidad.NuloToVacio(mat.getMateriaPrevia().getMatNom())); %>
                                <%
                                    }
                                %>
                                </td>
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
