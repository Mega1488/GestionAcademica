<%-- 
    Document   : DefCalendarioWW
    Created on : 03-jul-2017, 18:28:52
    Author     : alvar
--%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.Comparator"%>
<%@page import="Entidad.NotificacionBitacora"%>
<%@page import="Entidad.NotificacionBitacora"%>
<%@page import="Entidad.Notificacion"%>
<%@page import="Logica.LoNotificacion"%>
<%@page import="Logica.Seguridad"%>
<%@page import="Enumerado.NombreSesiones"%>
<%@page import="Enumerado.TipoMensaje"%>
<%@page import="Utiles.Retorno_MsgObj"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Entidad.Calendario"%>
<%@page import="java.util.List"%>
<%@page import="Logica.LoCalendario"%>
<%@page import="Utiles.Utilidades"%>
<%

    Utilidades utilidad = Utilidades.GetInstancia();
    String urlSistema = (String) session.getAttribute(NombreSesiones.URL_SISTEMA.getValor());

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
    String NotCod = request.getParameter("pNotCod");

    List<NotificacionBitacora> lstObjeto = new ArrayList<>();

    Retorno_MsgObj retorno = (Retorno_MsgObj) LoNotificacion.GetInstancia().obtener(Long.valueOf(NotCod));
    if (!retorno.SurgioError()) {
        Notificacion notificacion = (Notificacion) retorno.getObjeto();
        lstObjeto = notificacion.getLstBitacora();

        Collections.sort(lstObjeto, new Comparator<NotificacionBitacora>() {

            public int compare(NotificacionBitacora o1, NotificacionBitacora o2) {

                int sComp = o2.getNotBitFch().compareTo(o1.getNotBitFch());

                if (sComp != 0) {
                    return sComp;
                } else {
                    Long x1 = ((NotificacionBitacora) o2).getNotBitCod();
                    Long x2 = ((NotificacionBitacora) o1).getNotBitCod();
                    return x1.compareTo(x2);
                }
            }

        });

    } else {
        out.print(retorno.getMensaje().toString());
    }

    String tblVisible = (lstObjeto.size() > 0 ? "" : "display: none;");

%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gestión Académica - Bitacora</title>
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
                            <jsp:include page="/Definiciones/DefNotificacionTabs.jsp"/>
                        </div>

                        <div class=""> 
                            <div class="" style="text-align: right;"><a href="<% out.print(urlSistema); %>Definiciones/DefNotificacionWW.jsp">Regresar</a></div>
                        </div>

                        <div style="text-align: right; padding-top: 6px; padding-bottom: 6px;">
                            <a href="#" title="Depurar" class="glyphicon glyphicon-trash" data-toggle="modal" data-target="#PopUpDepurar"> </a>
                        </div>

                        <div style="display:none" id="datos_ocultos" name="datos_ocultos">
                            <input type="hidden" name="NotCod" id="NotCod" value="<% out.print(NotCod); %>">
                        </div>

                        <table style=' <% out.print(tblVisible); %>' class='table table-hover'>
                            <thead>
                                <tr>
                                    <th></th>
                                    <th></th>
                                    <th>Código</th>
                                    <th>Fecha</th>
                                    <th>Estado</th>
                                    <th>Destinatario</th>
                                </tr>
                            </thead>

                            <% for (NotificacionBitacora bitacora : lstObjeto) {

                            %>
                            <tr>
                                <td><a href="<% out.print(urlSistema); %>Definiciones/DefNotificacionBitacora.jsp?MODO=<% out.print(Enumerado.Modo.DELETE); %>&pNotCod=<% out.print(bitacora.getNotificacion().getNotCod()); %>&pNotBitCod=<% out.print(bitacora.getNotBitCod()); %>" name="btn_eliminar" id="btn_eliminar" title="Eliminar" class="glyphicon glyphicon-trash"/></td>
                                <td><a href="<% out.print(urlSistema); %>Definiciones/DefNotificacionBitacora.jsp?MODO=<% out.print(Enumerado.Modo.DISPLAY); %>&pNotCod=<% out.print(bitacora.getNotificacion().getNotCod()); %>&pNotBitCod=<% out.print(bitacora.getNotBitCod()); %>" name="btn_ver" id="btn_ver" title="Ver" class='glyphicon glyphicon-search'/></td>
                                <td><% out.print(utilidad.NuloToVacio(bitacora.getNotBitCod())); %> </td>
                                <td><% out.print(utilidad.NuloToVacio(bitacora.getNotBitFch())); %> </td>
                                <td><% out.print(utilidad.NuloToVacio(bitacora.getNotBitEst().getNombre())); %> </td>
                                <td><% out.print(utilidad.NuloToVacio(bitacora.getNotBitDst())); %> </td>

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



        <!-- PopUp para depurar -->

        <div id="PopUpDepurar" class="modal fade" role="dialog">
            <!-- Modal -->
            <div class="modal-dialog">
                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Depurar</h4>
                    </div>
                    <div class="modal-body">
                        <div>
                            <p>Confirma la eliminación de todos los registros?</p>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button name="boton_confirmar" id="boton_confirmar" type="button" class="btn btn-danger" data-codigo="">Confirmar</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                    </div>
                </div>
            </div>



            <script type="text/javascript">

                $(document).ready(function () {


                    $('#boton_confirmar').on('click', function (e) {
                        var NotCod = $('#NotCod').val();

                        $.post('<% out.print(urlSistema);%>NotificationManager', {
                            pNotCod: NotCod,
                            pAction: "DEPURAR_BITACORA"
                        }, function (responseText) {
                            var obj = JSON.parse(responseText);

                            $(function () {
                                $('#PopUpDepurar').modal('toggle');
                            });

                            MostrarMensaje(obj.tipoMensaje, obj.mensaje);

                            location.reload();


                        });



                    });


                });
            </script>
        </div>

    </body>
</html>
