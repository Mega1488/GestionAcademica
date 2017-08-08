<%-- 
    Document   : DefModulo
    Created on : 04-jul-2017, 19:31:27
    Author     : alvar
--%>

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
    LoCurso loCurso = LoCurso.GetInstancia();
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
    String CurCod = request.getParameter("pCurCod");
    String ModCod = request.getParameter("pModCod");
    String js_redirect = "window.location.replace('" + urlSistema + "Definiciones/DefCursoModuloSWW.jsp?MODO=UPDATE&pCurCod=" + CurCod + "');";

    Curso curso = new Curso();
    Retorno_MsgObj retorno = (Retorno_MsgObj) loCurso.obtener(Long.valueOf(CurCod));
    if (retorno.getMensaje().getTipoMensaje() != TipoMensaje.ERROR) {
        curso = (Curso) retorno.getObjeto();
    } else {
        out.print(retorno.getMensaje().toString());
    }

    Modulo modulo = new Modulo();
    modulo.setCurso(curso);

    if (Mode.equals(Modo.UPDATE) || Mode.equals(Modo.DISPLAY) || Mode.equals(Modo.DELETE)) {
        modulo = curso.getModuloById(Long.valueOf(ModCod));
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
        <title>Sistema de Gestión Académica - Modulo</title>
        <jsp:include page="/masterPage/head.jsp"/>

        <script>
            $(document).ready(function () {
                $('#btn_guardar').click(function (event) {


                    var CurCod = $('#CurCod').val();
                    var ModCod = $('#ModCod').val();
                    var ModNom = $('#ModNom').val();
                    var ModDsc = $('#ModDsc').val();
                    var ModTpoPer = $('select[name=ModTpoPer]').val();
                    var MotPerVal = $('#MotPerVal').val();
                    var ModCntHor = $('#ModCntHor').val();

                    if (ModNom == '')
                    {
                        MostrarMensaje("ERROR", "Completa los datos papa");
                    } else
                    {

                        if ($('#MODO').val() == "INSERT")
                        {

                            // Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
                            $.post('<% out.print(urlSistema); %>ABM_Modulo', {
                                pCurCod: CurCod,
                                pModCod: ModCod,
                                pModNom: ModNom,
                                pModDsc: ModDsc,
                                pModTpoPer: ModTpoPer,
                                pMotPerVal: MotPerVal,
                                pModCntHor: ModCntHor,
                                pAction: "INSERTAR"
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
                        }


                        if ($('#MODO').val() == "UPDATE")
                        {
                            // Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
                            $.post('<% out.print(urlSistema); %>ABM_Modulo', {
                                pCurCod: CurCod,
                                pModCod: ModCod,
                                pModNom: ModNom,
                                pModDsc: ModDsc,
                                pModTpoPer: ModTpoPer,
                                pMotPerVal: MotPerVal,
                                pModCntHor: ModCntHor,
                                pAction: "ACTUALIZAR"
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
                        }

                        if ($('#MODO').val() == "DELETE")
                        {
                            $.post('<% out.print(urlSistema); %>ABM_Modulo', {
                                pCurCod: CurCod,
                                pModCod: ModCod,
                                pAction: "ELIMINAR"
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
                        }
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

                        <div id="tabs" name="tabs" class="contenedor-tabs">
                            <jsp:include page="/Definiciones/DefModuloTabs.jsp"/>
                        </div>

                        <div class=""> 
                            <div class="" style="text-align: right;"><a href="<% out.print(urlSistema); %>Definiciones/DefCursoModuloSWW.jsp?MODO=UPDATE&pCurCod=<% out.print(CurCod); %>">Regresar</a></div>
                        </div>

                        <div style="display:none" id="datos_ocultos" name="datos_ocultos">
                            <input type="hidden" name="MODO" id="MODO" value="<% out.print(Mode); %>">
                            <input type="hidden" name="CurCod" id="CurCod" value="<% out.print(curso.getCurCod()); %>">
                        </div>

                        <form id="frm_objeto" name="frm_objeto">

                            <div><label>ModCod</label><input type="text" class="form-control" id="ModCod" name="ModCod" placeholder="ModCod" disabled value="<% out.print(utilidad.NuloToVacio(modulo.getModCod())); %>" ></div>
                            <div><label>ModNom</label><input type="text" class="form-control" id="ModNom" name="ModNom" placeholder="ModNom" <% out.print(CamposActivos); %> value="<% out.print(utilidad.NuloToVacio(modulo.getModNom())); %>" ></div>
                            <div><label>ModDsc</label><input type="text" class="form-control" id="ModDsc" name="ModDsc" placeholder="ModDsc" <% out.print(CamposActivos); %> value="<% out.print(utilidad.NuloToVacio(modulo.getModDsc())); %>" ></div>

                            <div>
                                <label>ModTpoPer</label>
                                <select class="form-control" id="ModTpoPer" name="ModTpoPer" <% out.print(CamposActivos); %>>
                                    <%
                                        for (TipoPeriodo tpoPeriodo : TipoPeriodo.values()) {

                                            if (modulo.getModTpoPer() == tpoPeriodo) {
                                                //return filial;
                                                out.println("<option selected value='" + tpoPeriodo.getTipoPeriodo() + "'>" + tpoPeriodo.getTipoPeriodoNombre() + "</option>");
                                            } else {
                                                out.println("<option value='" + tpoPeriodo.getTipoPeriodo() + "'>" + tpoPeriodo.getTipoPeriodoNombre() + "</option>");
                                            }
                                        }
                                    %>
                                </select>
                            </div>


                            <div><label>MotPerVal</label><input type=number step=0.5 class="form-control" id="MotPerVal" name="MotPerVal" placeholder="MotPerVal" <% out.print(CamposActivos); %> value="<% out.print(utilidad.NuloToVacio(modulo.getModPerVal())); %>" ></div>
                            <div><label>ModCntHor</label><input type=number step=0.5 class="form-control" id="ModCntHor" name="ModCntHor" placeholder="ModCntHor" <% out.print(CamposActivos); %> value="<% out.print(utilidad.NuloToVacio(modulo.getModCntHor())); %>" ></div>

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