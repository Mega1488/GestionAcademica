<%-- 
    Document   : DefMateriaEvaluacionWW
    Created on : jul 20, 2017, 3:28:32 p.m.
    Author     : aa
--%>

<%@page import="Entidad.Evaluacion"%>
<%@page import="Entidad.Materia"%>
<%@page import="Entidad.Carrera"%>
<%@page import="Entidad.PlanEstudio"%>
<%@page import="Logica.LoCarrera"%>
<%@page import="Enumerado.TipoMensaje"%>
<%@page import="Enumerado.Modo"%>
<%@page import="Utiles.Retorno_MsgObj"%>
<%@page import="Utiles.Utilidades"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%

    LoCarrera loCarrera     = LoCarrera.GetInstancia();
    Utilidades utilidad     = Utilidades.GetInstancia();
    String urlSistema       = (String) session.getAttribute(Enumerado.NombreSesiones.URL_SISTEMA.getValor());
    
    //----------------------------------------------------------------------------------------------------
    //CONTROL DE ACCESO
    //----------------------------------------------------------------------------------------------------
    
    String  usuario = (String) session.getAttribute(Enumerado.NombreSesiones.USUARIO.getValor());
    Boolean esAdm   = (Boolean) session.getAttribute(Enumerado.NombreSesiones.USUARIO_ADM.getValor());
    Boolean esAlu   = (Boolean) session.getAttribute(Enumerado.NombreSesiones.USUARIO_ALU.getValor());
    Boolean esDoc   = (Boolean) session.getAttribute(Enumerado.NombreSesiones.USUARIO_DOC.getValor());
    Retorno_MsgObj acceso = Logica.Seguridad.GetInstancia().ControlarAcceso(usuario, esAdm, esDoc, esAlu, utilidad.GetPaginaActual(request));
    
    if(acceso.SurgioError()) response.sendRedirect((String) acceso.getObjeto());
            
    //----------------------------------------------------------------------------------------------------
    
    Modo Mode           = Modo.valueOf(request.getParameter("MODO"));
    String PlaEstCod    = request.getParameter("pPlaEstCod");
    String CarCod       = request.getParameter("pCarCod");
    String MatCod       = request.getParameter("pMatCod");
    
    Carrera car         = new Carrera();
    PlanEstudio plan    = new PlanEstudio();
    Materia mat         = new Materia();
//    Evaluacion eva      = new Evaluacion();

    Retorno_MsgObj retorno = (Retorno_MsgObj) loCarrera.obtener(Long.valueOf(CarCod));
    if(retorno.getMensaje().getTipoMensaje() != TipoMensaje.ERROR)
    {
        car     = (Carrera) retorno.getObjeto();
        plan    = car.getPlanEstudioById(Long.valueOf(PlaEstCod));
        mat     = plan.getMateriaById(Long.valueOf(MatCod));
//        eva     = mat.getEvaluacionById(Long.valueOf(mat.));
    }
    else
    {
        out.print(retorno.getMensaje().toString());
    }
    
    String tblVisible = (mat.getLstEvaluacion().size() > 0 ? "" : "display: none;");

%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gestión Académica - Materia | Evaluación</title>
        <jsp:include page="/masterPage/head.jsp"/>
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/masterPage/menu_izquierdo.jsp" />
            
            <div id="contenido" name="contenido" class="main-panel">
                
                <div class="contenedor-cabezal">
                    <jsp:include page="/masterPage/cabezal.jsp"/>
                </div>
                
                <div class="contenedor-principal">
                    <div class="col-sm-11 contenedor-texto-titulo-flotante">
                        
                        <div id="tabs" name="tabs" class="contenedor-tabs">
                            <jsp:include page="/Definiciones/DefMateriaEvaluacionSWWTabs.jsp"/>
                        </div>
                
                        <div class=""> 
                            <div class="" style="text-align: right;"><a href="<% out.print(urlSistema); %>Definiciones/DefMateriaWW.jsp?MODO=DISPLAY&pCarCod=<% out.print(CarCod); %>&pPlaEstCod=<% out.print(PlaEstCod); %>&pMatCod=<% out.print(MatCod); %>">Regresar</a></div>
                        </div>
            
                        <div style="text-align: right; padding-top: 6px; padding-bottom: 6px;">
                                <a href="<% out.print(urlSistema); %>Definiciones/DefEvaluacion.jsp?MODO=<% out.print(Enumerado.Modo.INSERT); %>&pRelacion=MATERIA&pCarCod=<% out.print(car.getCarCod()); %>&pPlaEstCod=<% out.print(plan.getPlaEstCod()); %>&pMatCod=<% out.print(mat.getMatCod()); %>" title="Ingresar" class="glyphicon glyphicon-plus"> </a>
                                <input type="hidden" name="MODO" id="MODO" value="<% out.print(Mode); %>">
                                <input type="hidden" name="CarCod" id="CurCod" value="<% out.print(car.getCarCod()); %>">
                                <input type="hidden" name="PlaEstCod" id="ModCod" value="<% out.print(plan.getPlaEstCod()); %>">
                                <input type="hidden" name="MatCod" id="ModCod" value="<% out.print(mat.getMatCod()); %>">
                        </div>
            
                        <table style=' <% out.print(tblVisible); %>' class='table table-hover'>
                            <thead>
                                <tr>
                                    <th></th>
                                    <th></th>
                                    <th>Código</th>
                                    <th>Nombre</th>
                                    <th>Descripción</th>
                                    <th>Tipo</th>
                                    <th>Nota toal</th>
                                </tr>
                            </thead>

                            <% for(Evaluacion evaluacion : mat.getLstEvaluacion())
                            {

                            %>
                            <tr>
                                <td><a href="<% out.print(urlSistema); %>Definiciones/DefEvaluacion.jsp?MODO=<% out.print(Enumerado.Modo.DELETE); %>&pRelacion=MATERIA&pCarCod=<% out.print(car.getCarCod()); %>&pPlaEstCod=<% out.print(plan.getPlaEstCod()); %>&pMatCod=<% out.print(mat.getMatCod()); %>&pEvlCod=<% out.print(evaluacion.getEvlCod()); %>" name="btn_eliminar" id="btn_eliminar" title='Eliminar' class='glyphicon glyphicon-trash btn_eliminar'></a></td>
                                <td><a href="<% out.print(urlSistema); %>Definiciones/DefEvaluacion.jsp?MODO=<% out.print(Enumerado.Modo.UPDATE); %>&pRelacion=MATERIA&pCarCod=<% out.print(car.getCarCod()); %>&pPlaEstCod=<% out.print(plan.getPlaEstCod()); %>&pMatCod=<% out.print(mat.getMatCod()); %>&pEvlCod=<% out.print(evaluacion.getEvlCod()); %>" name="btn_editar" id="btn_editar" title='Editar' class='glyphicon glyphicon-edit btn_editar'></a></td>

                                <td><% out.print( utilidad.NuloToVacio(evaluacion.getEvlCod())); %> </td>
                                <td><% out.print( utilidad.NuloToVacio(evaluacion.getEvlNom())); %> </td>
                                <td><% out.print( utilidad.NuloToVacio(evaluacion.getEvlDsc())); %> </td>
                                <td><% out.print( utilidad.NuloToVacio(evaluacion.getTpoEvl().getTpoEvlNom())); %> </td>
                                <td><% out.print( utilidad.NuloToVacio(evaluacion.getEvlNotTot())); %> </td>

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