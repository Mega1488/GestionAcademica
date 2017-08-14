<%-- 
    Document   : DefModulo
    Created on : 04-jul-2017, 19:31:27
    Author     : alvar
--%>

<%@page import="Enumerado.TipoRepeticion"%>
<%@page import="Enumerado.TipoEnvio"%>
<%@page import="Enumerado.ObtenerDestinatario"%>
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
    String js_redirect = "window.location.replace('" + urlSistema + "Definiciones/DefNotificacionWW.jsp');";

    Notificacion notificacion = new Notificacion();

    if (Mode.equals(Modo.UPDATE) || Mode.equals(Modo.DISPLAY) || Mode.equals(Modo.DELETE)) {
        Retorno_MsgObj retorno = (Retorno_MsgObj) LoNotificacion.GetInstancia().obtener(Long.valueOf(NotCod));
        if (!retorno.SurgioError()) {
            notificacion = (Notificacion) retorno.getObjeto();
        } else {
            out.print(retorno.getMensaje().toString());
        }

    }

    String CamposActivos    = "";
    String notInterna       = "";

    switch (Mode) {
        case DELETE:
            CamposActivos = "disabled";
            break;
        case DISPLAY:
            CamposActivos = "disabled";
            break;
        case UPDATE:
            notInterna   = (notificacion.getNotInt() == true ? "disabled" : "");
            break;
    }

%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gestión Académica - Notificacion</title>
        <jsp:include page="/masterPage/head.jsp"/>

        <link href="<% out.print(urlSistema); %>JavaScript/summernote/summernote.css" rel="stylesheet">
        <script src="<% out.print(urlSistema); %>JavaScript/summernote/summernote.js"></script>
        <script src="<% out.print(urlSistema); %>JavaScript/summernote/lang/summernote-es-ES.js"></script>

        <script>
            $(document).ready(function () {


                $('#btn_guardar').click(function (event) {

                    var NotCod = $('#NotCod').val();
                    var NotAsu = $('#NotAsu').val();
                    var NotCon = $('#NotCon').summernote('code'); //$('#NotCon').val();
                    var NotDsc = $('#NotDsc').val();
                    var NotNom = $('#NotNom').val();
                    var NotRepHst = $('#NotRepHst').val();
                    var NotRepVal = $('#NotRepVal').val();

                    var NotWeb = document.getElementById('NotWeb').checked;
                    var NotEmail = document.getElementById('NotEmail').checked;
                    var NotApp = document.getElementById('NotApp').checked;
                    var NotAct = document.getElementById('NotAct').checked;

                    var NotTpo = $('select[name=NotTpo]').val();
                    var NotTpoEnv = $('select[name=NotTpoEnv]').val();
                    var NotObtDest = $('select[name=NotObtDest]').val();
                    var NotRepTpo = $('select[name=NotRepTpo]').val();


                    var Modo = $('#MODO').val();

                    if (NotNom == '')
                    {
                        MostrarMensaje("ERROR", "Completa los datos papa");
                    } else
                    {



                        // Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
                        $.post('<% out.print(urlSistema); %>ABM_Notificacion', {
                            pNotCod: NotCod,
                            pNotAct: NotAct,
                            pNotApp: NotApp,
                            pNotAsu: NotAsu,
                            pNotCon: NotCon,
                            pNotDsc: NotDsc,
                            pNotEmail: NotEmail,
                            pNotNom: NotNom,
                            pNotObtDest: NotObtDest,
                            pNotRepHst: NotRepHst,
                            pNotRepTpo: NotRepTpo,
                            pNotRepVal: NotRepVal,
                            pNotTpo: NotTpo,
                            pNotTpoEnv: NotTpoEnv,
                            pNotWeb: NotWeb,
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


                $(document).ready(function () {
                    $('#NotCon').summernote({
                        lang: 'es-ES',
                        height: 300
                    });
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

                        <div id="tabs" name="tabs" class="contenedor-tabs">
                            <jsp:include page="/Definiciones/DefNotificacionTabs.jsp"/>
                        </div>

                        <div class=""> 
                            <div class="" style="text-align: right;"><a href="<% out.print(urlSistema); %>Definiciones/DefNotificacionWW.jsp">Regresar</a></div>
                        </div>

                        <div style="display:none" id="datos_ocultos" name="datos_ocultos">
                            <input type="hidden" name="MODO" id="MODO" value="<% out.print(Mode); %>">
                        </div>

                        <form id="frm_objeto" name="frm_objeto">

                            <div><label>Código</label><input type="text" class="form-control" id="NotCod" name="NotCod" placeholder="NotCod" disabled value="<% out.print(utilidad.NuloToVacio(notificacion.getNotCod())); %>" ></div>
                            <div><label>Activa</label><input type="checkbox"  id="NotAct" name="NotAct" placeholder="NotAct" <% out.print(CamposActivos); %>  <% out.print(utilidad.BooleanToChecked(notificacion.getNotAct())); %> ></div>
                            <div><label>Nombre</label><input type="text" class="form-control" id="NotNom" name="NotNom" placeholder="NotNom" <% out.print(CamposActivos); %> <% out.print(notInterna); %> value="<% out.print(utilidad.NuloToVacio(notificacion.getNotNom())); %>" ></div>
                            <div><label>Descripcion</label><input type="text" class="form-control" id="NotDsc" name="NotDsc" placeholder="NotDsc" <% out.print(CamposActivos); %> value="<% out.print(utilidad.NuloToVacio(notificacion.getNotDsc())); %>" ></div>

                            <div><label>Aplicacion</label><input type="checkbox" id="NotApp" name="NotApp" placeholder="NotApp" <% out.print(CamposActivos); %> <% out.print(utilidad.BooleanToChecked(notificacion.getNotApp())); %> ></div>
                            <div><label>Email</label><input type="checkbox"  id="NotEmail" name="NotEmail" placeholder="NotEmail" <% out.print(CamposActivos); %> <% out.print(utilidad.BooleanToChecked(notificacion.getNotEmail())); %> ></div>
                            <div><label>Web</label><input type="checkbox"  id="NotWeb" name="NotWeb" placeholder="NotWeb" <% out.print(CamposActivos); %> <% out.print(utilidad.BooleanToChecked(notificacion.getNotWeb())); %> ></div>

                            <div>
                                <label>Obtener destinatarios</label>
                                <select class="form-control" id="NotObtDest" name="NotObtDest" <% out.print(CamposActivos); %> <% out.print(notInterna); %>>
                                    <%
                                        for (ObtenerDestinatario obtDestinatario : ObtenerDestinatario.values()) {

                                            String seleccionado = "";
                                            if (notificacion.getNotObtDest() != null) {
                                                if (notificacion.getNotObtDest().equals(obtDestinatario)) {
                                                    seleccionado = "selected";
                                                }
                                            }

                                            out.println("<option " + seleccionado + " value='" + obtDestinatario.getValor() + "'>" + obtDestinatario.getNombre() + "</option>");

                                        }
                                    %>
                                </select>
                            </div>


                            <div>
                                <label>Tipo de notificacion</label>
                                <select class="form-control" id="NotTpo" name="NotTpo" <% out.print(CamposActivos); %> <% out.print(notInterna); %> >
                                    <%
                                        for (TipoNotificacion tpoNot : TipoNotificacion.values()) {

                                            String seleccionado = "";
                                            if (notificacion.getNotTpo() != null) {
                                                if (notificacion.getNotTpo().equals(tpoNot)) {
                                                    seleccionado = "selected";
                                                }
                                            }

                                            out.println("<option " + seleccionado + " value='" + tpoNot.getValor() + "'>" + tpoNot.getNombre() + "</option>");

                                        }
                                    %>
                                </select>
                            </div>

                            <div>
                                <label>Tipo de envio</label>
                                <select class="form-control" id="NotTpoEnv" name="NotTpoEnv" <% out.print(CamposActivos); %> <% out.print(notInterna); %>>
                                    <%
                                        for (TipoEnvio tpoEnv : TipoEnvio.values()) {

                                            String seleccionado = "";
                                            if (notificacion.getNotTpoEnv() != null) {
                                                if (notificacion.getNotTpoEnv().equals(tpoEnv)) {
                                                    seleccionado = "selected";
                                                }
                                            }

                                            out.println("<option " + seleccionado + " value='" + tpoEnv.getValor() + "'>" + tpoEnv.getNombre() + "</option>");

                                        }
                                    %>
                                </select>
                            </div>


                            <div>
                                <label>Tipo de repeticion</label>
                                <select class="form-control" id="NotRepTpo" name="NotRepTpo" <% out.print(CamposActivos); %> >
                                    <%
                                        for (TipoRepeticion tpoRep : TipoRepeticion.values()) {

                                            String seleccionado = "";
                                            if (notificacion.getNotRepTpo() != null) {
                                                if (notificacion.getNotRepTpo().equals(tpoRep)) {
                                                    seleccionado = "selected";
                                                }
                                            }

                                            out.println("<option " + seleccionado + " value='" + tpoRep.getValor() + "'>" + tpoRep.getNombre() + "</option>");

                                        }
                                    %>
                                </select>
                            </div>

                            <div><label>Repetir</label><input type="text" class="form-control" id="NotRepVal" name="NotRepVal" placeholder="NotRepVal" <% out.print(CamposActivos); %>  value="<% out.print(utilidad.NuloToVacio(notificacion.getNotRepVal())); %>" ></div>
                            <div><label>Repetir hasta</label><input type="date" class="form-control" id="NotRepHst" name="NotRepHst" placeholder="NotRepHst" <% out.print(CamposActivos); %>  value="<% out.print(utilidad.NuloToVacio(notificacion.getNotRepHst())); %>" ></div>


                            <div><label>Asunto</label><input type="text" class="form-control" id="NotAsu" name="NotAsu" placeholder="NotAsu" <% out.print(CamposActivos); %> value="<% out.print(utilidad.NuloToVacio(notificacion.getNotAsu())); %>" ></div>
                            <div>
                                <label>Contenido</label>
                                <div id="NotCon" name="NotCon"><% out.print(utilidad.NuloToVacio(notificacion.getNotCon())); %></div>
                            </div>

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