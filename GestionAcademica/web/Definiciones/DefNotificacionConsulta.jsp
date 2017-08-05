<%-- 
    Document   : DefModulo
    Created on : 04-jul-2017, 19:31:27
    Author     : alvar
--%>

<%@page import="Enumerado.TipoConsulta"%>
<%@page import="Entidad.NotificacionConsulta"%>
<%@page import="Enumerado.TipoRepeticion"%>
<%@page import="Enumerado.TipoEnvio"%>
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
    String NotCnsCod = request.getParameter("pNotCnsCod");
    
    String js_redirect = "window.location.replace('" + urlSistema + "Definiciones/DefNotificacionConsultaSWW.jsp?MODO=UPDATE&pNotCod="+NotCod+"');";

    NotificacionConsulta consulta = new NotificacionConsulta();

    Notificacion notificacion   = new Notificacion();
    Retorno_MsgObj retorno      = (Retorno_MsgObj) LoNotificacion.GetInstancia().obtener(Long.valueOf(NotCod));

    if (Mode.equals(Modo.UPDATE) || Mode.equals(Modo.DISPLAY) || Mode.equals(Modo.DELETE)) {
       
        if (!retorno.SurgioError()) {
            notificacion = (Notificacion) retorno.getObjeto();
            
            consulta = notificacion.ObtenerConsultaByCod(Long.valueOf(NotCnsCod));
            
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
        <title>Sistema de Gestión Académica - Consulta</title>
        <jsp:include page="/masterPage/head.jsp"/>

        <script>
            $(document).ready(function () {


                $('#btn_guardar').click(function (event) {

                    var NotCod= $('#NotCod').val();
                    var NotCnsCod= $('#NotCnsCod').val();
                    var NotCnsSQL= $('#NotCnsSQL').val();
                    var NotCnsTpo= $('select[name=NotCnsTpo]').val();
                    
                    var Modo = $('#MODO').val();

                    if (NotCnsSQL == '')
                    {
                        MostrarMensaje("ERROR", "Completa los datos papa");
                    } else
                    {



                        // Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
                        $.post('<% out.print(urlSistema); %>ABM_NotificacionConsulta', {
                            pNotCnsCod:NotCnsCod,
                            pNotCnsSQL:NotCnsSQL,
                            pNotCnsTpo:NotCnsTpo,
                            pNotCod:NotCod,
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
                            <p>Consulta</p>
                        </div>

                        <div class=""> 
                            <div class="" style="text-align: right;"><a href="<% out.print(urlSistema); %>Definiciones/DefNotificacionConsultaSWW.jsp?MODO=UPDATE&pNotCod=<% out.print(NotCod); %>">Regresar</a></div>
                        </div>

                        <div style="display:none" id="datos_ocultos" name="datos_ocultos">
                            <input type="hidden" name="MODO" id="MODO" value="<% out.print(Mode); %>">
                            <input type="hidden" name="NotCod" id="NotCod" value="<% out.print(NotCod); %>">
                        </div>

                        <form id="frm_objeto" name="frm_objeto">

                            <div><label>Código</label><input type="text" class="form-control" id="NotCnsCod" name="NotCnsCod" placeholder="NotCnsCod" disabled value="<% out.print( utilidad.NuloToVacio(consulta.getNotCnsCod())); %>" ></div>
                            
                            <div>
                                <label>Tipo</label>
                                <select class="form-control" id="NotCnsTpo" name="NotCnsTpo" <% out.print(CamposActivos); %>>
                                    <%
                                        for (TipoConsulta tpoConsulta : TipoConsulta.values()) {
                                            
                                            String seleccionado = "";
                                            if(consulta.getNotCnsTpo() != null) if (consulta.getNotCnsTpo().equals(tpoConsulta)) seleccionado = "selected";
                                            
                                            out.println("<option " + seleccionado + " value='" + tpoConsulta.getValor() + "'>" + tpoConsulta.getNombre() + "</option>");
                                            
                                        }
                                    %>
                                </select>
                            </div>
                            
                                    <div><label>Query:</label><textarea rows="10" class="form-control" id="NotCnsSQL" name="NotCnsSQL" placeholder="NotCnsSQL" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio(consulta.getNotCnsSQL())); %>" ><% out.print( utilidad.NuloToVacio(consulta.getNotCnsSQL())); %></textarea></div>
                            
                            <div>
                                <input name="btn_guardar" id="btn_guardar" value="Guardar" type="button" class="btn btn-success"/>
                                <input value="Cancelar" class="btn btn-default" type="button" onclick="<% out.print(js_redirect);%>"/>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>