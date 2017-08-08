<%-- 
    Document   : DefModulo
    Created on : 04-jul-2017, 19:31:27
    Author     : alvar
--%>

<%@page import="Entidad.NotificacionBitacora"%>
<%@page import="Enumerado.TipoRepeticion"%>
<%@page import="Enumerado.TipoEnvio"%>
<%@page import="Enumerado.TipoNotificacion"%>
<%@page import="Logica.LoNotificacion"%>
<%@page import="Entidad.Notificacion"%>
<%@page import="Enumerado.TipoMensaje"%>
<%@page import="Utiles.Retorno_MsgObj"%>
<%@page import="Enumerado.TipoPeriodo"%>
<%@page import="Entidad.Modulo"%>
<%@page import="Entidad.Curso"%>
<%@page import="Enumerado.Modo"%>
<%@page import="Utiles.Utilidades"%>
<%@page import="Logica.LoCurso"%>
<%@page import="Entidad.Parametro"%>
<%@page import="Logica.LoParametro"%>
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
    String NotCod = request.getParameter("pNotCod");
    String NotBitCod = request.getParameter("pNotBitCod");

    String js_redirect = "window.location.replace('" + urlSistema + "Definiciones/DefNotificacionBitacoraSWW.jsp?MODO=UPDATE&pNotCod=" + NotCod + "');";

    NotificacionBitacora bitacora = new NotificacionBitacora();

    Notificacion notificacion = new Notificacion();
    Retorno_MsgObj retorno = (Retorno_MsgObj) LoNotificacion.GetInstancia().obtener(Long.valueOf(NotCod));

    if (Mode.equals(Modo.UPDATE) || Mode.equals(Modo.DISPLAY) || Mode.equals(Modo.DELETE)) {

        if (!retorno.SurgioError()) {
            notificacion = (Notificacion) retorno.getObjeto();

            bitacora = notificacion.ObtenerBitacoraByCod(Long.valueOf(NotBitCod));

        } else {
            out.print(retorno.getMensaje().toString());
        }

    }

    String CamposActivos = "disabled";

    switch (Mode) {
        case INSERT:
            CamposActivos = "enabled";
            break;
        case DELETE:
            CamposActivos = "disabled";
            break;
        case DISPLAY:
            CamposActivos = "disabled";
            break;
        case UPDATE:
            CamposActivos = "enabled";
            break;
    }

%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gestión Académica - Bitacora</title>
        <jsp:include page="/masterPage/head.jsp"/>

        <script>
            $(document).ready(function () {


                $('#btn_guardar').click(function (event) {

                    var NotBitCod = $('#NotBitCod').val();
                    var NotCod = $('#NotCod').val();

                    var Modo = $('#MODO').val();

                    if (NotBitCod == '')
                    {
                        MostrarMensaje("ERROR", "Completa los datos papa");
                    } else
                    {



                        // Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
                        $.post('<% out.print(urlSistema); %>ABM_NotificacionBitacora', {
                            pNotBitCod: NotBitCod,
                            pNotCod: NotCod,
                            pAction: Modo
                        }, function (responseText) {
                            var obj = JSON.parse(responseText);

                            MostrarMensaje(obj.tipoMensaje, obj.mensaje);

                            if (obj.tipoMensaje != 'ERROR')
                            {
            <%
                                    out.print(js_redirect);
            %>
                            }

                        });


                    }
                });

            });

        </script>

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
                            <p>Bitacora</p>
                        </div>

                        <div class=""> 
                            <div class="" style="text-align: right;"><a href="<% out.print(urlSistema); %>Definiciones/DefNotificacionBitacoraSWW.jsp?MODO=UPDATE&pNotCod=<% out.print(NotCod); %>">Regresar</a></div>
                        </div>

                        <div style="display:none" id="datos_ocultos" name="datos_ocultos">
                            <input type="hidden" name="MODO" id="MODO" value="<% out.print(Mode); %>">
                            <input type="hidden" name="NotCod" id="NotCod" value="<% out.print(NotCod); %>">
                        </div>

                        <form id="frm_objeto" name="frm_objeto">

                            <div><label>Código</label><input type="text" class="form-control" id="NotBitCod" name="NotBitCod" placeholder="NotBitCod" disabled value="<% out.print(utilidad.NuloToVacio(bitacora.getNotBitCod())); %>" ></div>
                            <div><label>Asunto</label><input type="text" class="form-control" id="NotBitAsu" name="NotBitAsu" placeholder="NotBitAsu" <% out.print(CamposActivos); %> value="<% out.print(utilidad.NuloToVacio(bitacora.getNotBitAsu())); %>" ></div>
                            <div><label>Contenido</label><% out.print(utilidad.NuloToVacio(bitacora.getNotBitCon())); %></div>
                            <div><label>Detalle</label><textarea rows="10" class="form-control" id="NotBitDet" name="NotBitDet" placeholder="NotBitDet" <% out.print(CamposActivos); %> value="<% out.print(utilidad.NuloToVacio(bitacora.getNotBitDet())); %>" ><% out.print(utilidad.NuloToVacio(bitacora.getNotBitDet())); %></textarea></div>
                            <div><label>Destinatarios</label><textarea rows="10" type="text" class="form-control" id="NotBitDst" name="NotBitDst" placeholder="NotBitDst" <% out.print(CamposActivos); %> value="<% out.print(utilidad.NuloToVacio(bitacora.getNotBitDst())); %>" ><% out.print(utilidad.NuloToVacio(bitacora.getNotBitDst())); %></textarea></div>
                            <div><label>Estado</label><input type="text" class="form-control" id="NotBitEst" name="NotBitEst" placeholder="NotBitEst" <% out.print(CamposActivos); %> value="<% out.print(utilidad.NuloToVacio(bitacora.getNotBitEst())); %>" ></div>
                            <div><label>Fecha</label><input type="text" class="form-control" id="NotBitFch" name="NotBitFch" placeholder="NotBitFch" <% out.print(CamposActivos); %> value="<% out.print(utilidad.NuloToVacio(bitacora.getNotBitFch())); %>" ></div>
                            <div><label>Persona</label><input type="text" class="form-control" id="NotPerCod" name="NotPerCod" placeholder="NotPerCod" <% out.print(CamposActivos); %> value="<% out.print(utilidad.NuloToVacio((bitacora.getPersona() != null ? bitacora.getPersona().getPerCod() : ""))); %>" ></div>

                            <div>
                                <input name="btn_guardar" id="btn_guardar" value="Guardar" type="button" class="btn btn-success"/>
                                <input value="Cancelar" class="btn btn-default" type="button" onclick="<% out.print(js_redirect);%>"/>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <jsp:include page="/masterPage/footer.jsp"/>
        </div>
    </body>
</html>