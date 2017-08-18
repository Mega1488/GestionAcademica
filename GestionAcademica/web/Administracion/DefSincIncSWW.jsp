<%-- 
    Document   : DefSincIncSWW
    Created on : 18-ago-2017, 10:06:56
    Author     : alvar
--%>
<%@page import="Entidad.SincronizacionInconsistencia"%>
<%@page import="Logica.LoSincronizacion"%>
<%@page import="Entidad.Sincronizacion"%>
<%@page import="Utiles.Retorno_MsgObj"%>
<%@page import="Enumerado.Modo"%>
<%@page import="Utiles.Utilidades"%>
<%
    Utilidades utilidad = Utilidades.GetInstancia();
    String urlSistema = (String) session.getAttribute(Enumerado.NombreSesiones.URL_SISTEMA.getValor());
    
    //----------------------------------------------------------------------------------------------------
    //CONTROL DE ACCESO
    //----------------------------------------------------------------------------------------------------
    String usuario = (String) session.getAttribute(Enumerado.NombreSesiones.USUARIO.getValor());
    Boolean esAdm = (Boolean) session.getAttribute(Enumerado.NombreSesiones.USUARIO_ADM.getValor());
    Boolean esAlu = (Boolean) session.getAttribute(Enumerado.NombreSesiones.USUARIO_ALU.getValor());
    Boolean esDoc = (Boolean) session.getAttribute(Enumerado.NombreSesiones.USUARIO_DOC.getValor());
    Retorno_MsgObj acceso = Logica.Seguridad.GetInstancia().ControlarAcceso(usuario, esAdm, esDoc, esAlu, utilidad.GetPaginaActual(request));

    if (acceso.SurgioError()) {
        response.sendRedirect((String) acceso.getObjeto());
    }

    //----------------------------------------------------------------------------------------------------
    
    Modo Mode = Modo.valueOf(request.getParameter("MODO"));
    String SncCod = request.getParameter("pSncCod");
    String js_redirect = "window.location.replace('" + urlSistema + "Administracion/DefSincWW.jsp');";

    Sincronizacion sincronizacion = new Sincronizacion();

    if (Mode.equals(Modo.UPDATE) || Mode.equals(Modo.DISPLAY) || Mode.equals(Modo.DELETE)) {
        Retorno_MsgObj retorno = (Retorno_MsgObj) LoSincronizacion.GetInstancia().obtener(Long.valueOf(SncCod));
        if (!retorno.SurgioError()) {
            sincronizacion = (Sincronizacion) retorno.getObjeto();
        } else {
            out.print(retorno.getMensaje().toString());
        }

    }
    
    String tblVisible = (sincronizacion.getLstInconsistencia().size() > 0 ? "" : "display: none;");

%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gestión Académica - Inconsistencias</title>
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

                        <div class="contenedor-titulo">    
                            <p>Inconsistencias</p>
                        </div>
                        
                        <div style="text-align: right; padding-top: 6px; padding-bottom: 6px;">
                            <a href="#" title="Procesar" class="glyphicon glyphicon-play" id="btn_procesar"> </a>
                        </div>

                        <div class=""> 
                            <div class="" style="text-align: right;"><a href="<% out.print(urlSistema); %>Administracion/DefSincWW.jsp">Regresar</a></div>
                        </div>

                        <div style="display:none" id="datos_ocultos" name="datos_ocultos">
                            <input type="hidden" name="MODO" id="MODO" value="<% out.print(Mode); %>">
                            <input type="hidden" name="SncCod" id="SncCod" value="<%=SncCod%>">
                        </div>

                        <table style=' <% out.print(tblVisible); %>' class='table table-hover'>
                            <thead>
                                <tr>
                                    <th></th>
                                    <th>Codigo</th>
                                    <th>Registro</th>
                                    <th>Estado</th>
                                    <th>Objeto seleccionado</th>
                                </tr>    
                            </thead>
                            <%
                                for (SincronizacionInconsistencia inc : sincronizacion.getLstInconsistencia()) {
                            %>
                            <tr>
                                <td><a href="<% out.print(urlSistema); %>Administracion/DefSincInc.jsp?MODO=<% out.print(Enumerado.Modo.UPDATE); %>&pSncCod=<% out.print(SncCod); %>&pIncCod=<% out.print(inc.getIncCod()); %>" name="btn_editar" id="btn_editar" class="glyphicon glyphicon-edit"></a></td>
                               <td><% out.print(utilidad.NuloToCero(inc.getIncCod())); %></td>
                                <td><% out.print(utilidad.NuloToVacio(inc.getObjeto().getObjNom())); %></td>
                                <td><% out.print(utilidad.NuloToVacio(inc.getIncEst())); %></td>
                                <td><% out.print(utilidad.NuloToVacio(inc.getObjetoSeleccionado())); %></td>
                            </tr>
                            <%
                                }
                            %>
                        </table>
                    </div>
                </div>
            </div>

            <jsp:include page="/masterPage/footer.jsp"/>
        </div>
        
        <script>
            $(document).ready(function () {
                    $('#btn_procesar').click(function (event) {


                        var SncCod = $('#SncCod').val();


                            // Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
                            $.post('<% out.print(urlSistema); %>ABM_Sincronizacion', {
                                pSncCod: SncCod,
                                pAction: 'INC_PROCESAR'
                            }, function (responseText) {
                                var obj = JSON.parse(responseText);

                                if (obj.tipoMensaje != 'ERROR')
                                {
                                    <%
                                        out.print(js_redirect);
                                    %>
                                } else
                                {
                                    MostrarMensaje(obj.tipoMensaje, obj.mensaje);
                                }

                            });

                        
                    });
                });
        </script>
        
    </body>
</html>
