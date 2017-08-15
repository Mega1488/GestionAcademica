<%-- 
    Document   : DefCurso
    Created on : 03-jul-2017, 18:29:13
    Author     : alvar
--%>

<%@page import="Logica.Seguridad"%>
<%@page import="Enumerado.NombreSesiones"%>
<%@page import="Enumerado.TipoMensaje"%>
<%@page import="Utiles.Retorno_MsgObj"%>
<%@page import="Moodle.MoodleCategory"%>
<%@page import="Entidad.Parametro"%>
<%@page import="Logica.LoParametro"%>
<%@page import="Logica.LoCategoria"%>
<%@page import="Entidad.Curso"%>
<%@page import="Logica.LoCurso"%>
<%@page import="Utiles.Utilidades"%>
<%@page import="Enumerado.Modo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    LoParametro loParam = LoParametro.GetInstancia();
    Parametro param = loParam.obtener();
    LoCategoria loCat = LoCategoria.GetInstancia();
    LoCurso loCurso = LoCurso.GetInstancia();
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
    Modo Mode = Modo.valueOf(request.getParameter("MODO"));
    String CurCod = request.getParameter("pCurCod");
    String js_redirect = "window.location.replace('" + urlSistema + "Definiciones/DefCursoWW.jsp');";

    Curso curso = new Curso();

    if (Mode.equals(Modo.UPDATE) || Mode.equals(Modo.DISPLAY) || Mode.equals(Modo.DELETE)) {
        Retorno_MsgObj retorno = (Retorno_MsgObj) loCurso.obtener(Long.valueOf(CurCod));
        if (retorno.getMensaje().getTipoMensaje() != TipoMensaje.ERROR) {
            curso = (Curso) retorno.getObjeto();
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

    MoodleCategory[] lstCategorias = null;
    if (param.getParUtlMdl()) {
        lstCategorias = loCat.Mdl_ObtenerListaCategorias();
    }

%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gestión Académica - Persona</title>
        <jsp:include page="/masterPage/head.jsp"/>

        <script>
            $(document).ready(function () {
                $('#btn_guardar').click(function (event) {


                    var CurCod = $('#CurCod').val();
                    var CurNom = $('#CurNom').val();
                    var CurDsc = $('#CurDsc').val();
                    var CurFac = $('#CurFac').val();
                    var CurCrt = $('#CurCrt').val();
                    var CurCatCod = $('select[name=CurCatCod]').val();

                    if (CurNom == '')
                    {
                        MostrarMensaje("ERROR", "Completa los datos papa");
                    } else
                    {

                        if ($('#MODO').val() == "INSERT")
                        {

                            // Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
                            $.post('<% out.print(urlSistema); %>ABM_Curso', {
                                pCurCod: CurCod,
                                pCurNom: CurNom,
                                pCurDsc: CurDsc,
                                pCurFac: CurFac,
                                pCurCrt: CurCrt,
                                pCurCatCod: CurCatCod,
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
                            $.post('<% out.print(urlSistema); %>ABM_Curso', {
                                pCurCod: CurCod,
                                pCurNom: CurNom,
                                pCurDsc: CurDsc,
                                pCurFac: CurFac,
                                pCurCrt: CurCrt,
                                pCurCatCod: CurCatCod,
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
                            $.post('<% out.print(urlSistema); %>ABM_Curso', {
                                pCurCod: CurCod,
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
                            <jsp:include page="/Definiciones/DefCursoTabs.jsp"/>
                        </div>

                        <div class=""> 
                            <div class="" style="text-align: right;"><a href="<% out.print(urlSistema); %>Definiciones/DefCursoWW.jsp">Regresar</a></div>
                        </div>

                        <div style="display:none" id="datos_ocultos" name="datos_ocultos">
                            <input type="hidden" name="MODO" id="MODO" value="<% out.print(Mode); %>">
                        </div>

                        <form id="frm_objeto" name="frm_objeto">

                            <div><label>Código</label><input type="text" class="form-control" id="CurCod" name="CurCod" placeholder="CurCod" disabled value="<% out.print(utilidad.NuloToVacio(curso.getCurCod())); %>" ></div>
                            <div><label>Nombre</label><input type="text" class="form-control" id="CurNom" name="CurNom" placeholder="CurNom" <% out.print(CamposActivos); %> value="<% out.print(utilidad.NuloToVacio(curso.getCurNom())); %>" ></div>
                            <div><label>Descripción</label><input type="text" class="form-control" id="CurDsc" name="CurDsc" placeholder="CurDsc" <% out.print(CamposActivos); %> value="<% out.print(utilidad.NuloToVacio(curso.getCurDsc())); %>" ></div>
                            <div><label>Facultad</label><input type="text" class="form-control" id="CurFac" name="CurFac" placeholder="CurFac" <% out.print(CamposActivos); %> value="<% out.print(utilidad.NuloToVacio(curso.getCurFac())); %>" ></div>
                            <div><label>Certificación</label><input type="text" class="form-control" id="CurCrt" name="CurCrt" placeholder="CurCrt" <% out.print(CamposActivos); %> value="<% out.print(utilidad.NuloToVacio(curso.getCurCrt())); %>" ></div>

                            <%
                                if (param.getParUtlMdl()) {
                            %>
                            <div>
                                <label>Categoría en moodle</label>
                                <select class="form-control" id="CurCatCod" name="CurCatCod" <% out.print(CamposActivos); %>>
                                    <option value=''>Nueva</option>
                                    <%
                                        for (int i = 0; i < lstCategorias.length; i++) {
                                            MoodleCategory mdlCat = lstCategorias[i];

                                            if (mdlCat.getId() == curso.getCurCatCod()) {
                                                //return filial;
                                                out.println("<option selected value='" + mdlCat.getId() + "'>" + mdlCat.getName() + "</option>");
                                            } else {
                                                out.println("<option value='" + mdlCat.getId() + "'>" + mdlCat.getName() + "</option>");
                                            }
                                        }
                                    %>
                                </select>
                            </div>

                            <%
                                }
                            %>
                            <div>
                                <input name="btn_guardar" id="btn_guardar" value="Guardar" type="button" class="btn btn-success" />
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
