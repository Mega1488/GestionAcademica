<%-- 
    Document   : EvalPendientes
    Created on : jul 27, 2017, 3:06:54 p.m.
    Author     : aa
--%>

<%@page import="Logica.LoCalendario"%>
<%@page import="Logica.LoPersona"%>
<%@page import="Entidad.Calendario"%>
<%@page import="Entidad.Persona"%>
<%--<%@page import="Persistencia.PerCalendario"%>--%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="Utiles.Retorno_MsgObj"%>
<%@page import="Logica.Seguridad"%>
<%@page import="Enumerado.NombreSesiones"%>
<%@page import="Utiles.Utilidades"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    LoPersona lopersona = LoPersona.GetInstancia();
    LoCalendario loCalendario = LoCalendario.GetInstancia();
    Utilidades utilidad = Utilidades.GetInstancia();
    
    String urlSistema = utilidad.GetUrlSistema();

    //----------------------------------------------------------------------------------------------------
    //CONTROL DE ACCESO
    //----------------------------------------------------------------------------------------------------
    String usuario = (String) session.getAttribute(NombreSesiones.USUARIO.getValor());
    Boolean esAdm = (Boolean) session.getAttribute(NombreSesiones.USUARIO_ADM.getValor());
    Boolean esAlu = (Boolean) session.getAttribute(NombreSesiones.USUARIO_ALU.getValor());
    Boolean esDoc = (Boolean) session.getAttribute(NombreSesiones.USUARIO_DOC.getValor());
    Retorno_MsgObj acceso = Seguridad.GetInstancia().ControlarAcceso(usuario, esAdm, esDoc, esAlu, utilidad.GetPaginaActual(request));

    if (acceso.SurgioError()) {
        response.sendRedirect((String) acceso.getObjeto());
    }

    //----------------------------------------------------------------------------------------------------
    Persona persona = new Persona();

    Retorno_MsgObj retPersona = lopersona.obtenerByMdlUsr(usuario);
    persona = (Persona) retPersona.getObjeto();

    List<Object> lstObjeto = new ArrayList<>();

    Retorno_MsgObj retorno = loCalendario.ObtenerEvaluacionesDocentes(persona.getPerCod());

    if (!retorno.SurgioErrorListaRequerida()) {
        lstObjeto = retorno.getLstObjetos();
    } else {
        out.print(retorno.getMensaje().toString());
    }

    String tblVisible = (lstObjeto.size() > 0 ? "" : "display: none;");


%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gestión Académica - Evaluaciones | Docente</title>
        <jsp:include page="/masterPage/head.jsp"/>
    </head>
    <body>
        <jsp:include page="/masterPage/NotificacionError.jsp"/>
        <jsp:include page="/masterPage/cabezal_menu.jsp"/>

        <!-- CONTENIDO -->
        <div class="contenido" id="contenedor">

            <div class="row">
                <div class="col-lg-12">
                    <section class="panel">
                        <header class="panel-heading">
                            CALENDARIO
                            <span class="tools pull-right">
                                <div class="hidden-xs">
                                    <div class="" style="text-align: right;"><a href="<% out.print(urlSistema); %>">Regresar</a></div>
                                </div>
                            </span>
                        </header>

                        <div class="panel-body">
                            <div class=" form">
                                <table class='table table-hover' style=' <% out.print(tblVisible); %>'>
                                    <thead>
                                        <tr>
                                            <th>Código</th>
                                            <th>Evaluación</th>
                                            <th>Carrera/Curso</th>
                                            <th>Estudio</th>
                                            <th>Fecha</th>
                                            <th>Fecha Desde</th>
                                            <th>Fecha Hasta</th>
                                            <th></th>
                                            <th></th>
                                        </tr>
                                    </thead>

                                    <tbody>
                                        <% for (Object objeto : lstObjeto) {
                                                Calendario calendario = (Calendario) objeto;
                                        %>
                                        <tr>
                                            <td><%out.print(utilidad.NuloToVacio(calendario.getCalCod())); %> </td>
                                            <td><%out.print(utilidad.NuloToVacio(calendario.getEvaluacion().getEvlNom())); %> </td>
                                            <td><%out.print(utilidad.NuloToVacio(calendario.getEvaluacion().getCarreraCursoNombre()));%></td>
                                            <td><% out.print(utilidad.NuloToVacio(calendario.getEvaluacion().getEstudioNombre())); %> </td>
                                            <td><% out.print(utilidad.NuloToVacio(calendario.getCalFch()));%> </td>
                                            <td><% out.print(utilidad.NuloToVacio(calendario.getEvlInsFchDsd()));%></td>
                                            <td><% out.print(utilidad.NuloToVacio(calendario.getEvlInsFchHst()));%></td>
                                            <td><a href="<% out.print(urlSistema); %>Docente/CalificarAlumnos.jsp?MODO=<% out.print(Enumerado.Modo.UPDATE); %>&pCalCod=<% out.print(calendario.getCalCod()); %>" name="btn_edit_doc" id="btn_edit_doc" title="Evaluar" class="glyphicon glyphicon-paste"/></td>
                                        </tr>
                                        <%
                                            }
                                        %>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </section>
                </div>
            </div>            
        </div>
        <jsp:include page="/masterPage/footer.jsp"/>
    </body>
</html>