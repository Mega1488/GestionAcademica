<%-- 
    Document   : DefVersion
    Created on : 25-jun-2017, 21:43:57
    Author     : alvar
--%>

<%@page import="Entidad.Parametro"%>
<%@page import="Logica.LoParametro"%>
<%@page import="Utiles.Retorno_MsgObj"%>
<%@page import="Utiles.Utilidades"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
    Parametro parametro = LoParametro.GetInstancia().obtener(Integer.valueOf(1));

%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gestión Académica - Versión</title>
        <jsp:include page="/masterPage/head.jsp"/>

        <script>
            $(document).ready(function () {

                $('#btn_guardar').click(function (event) {


                    var ParCod = $('#ParCod').val();
                    var ParEmlCod = $('#ParEmlCod').val();
                    var ParSisLocal = document.getElementById('ParSisLocal').checked;
                    var ParUtlMdl = document.getElementById('ParUtlMdl').checked;
                    var ParUrlMdl = $('#ParUrlMdl').val();
                    var ParMdlTkn = $('#ParMdlTkn').val();
                    var ParUrlSrvSnc = $('#ParUrlSrvSnc').val();
                    var ParPswValExp = $('#ParPswValExp').val();
                    var ParPswValMsg = $('#ParPswValMsg').val();
                    var ParDiaEvlPrv = $('#ParDiaEvlPrv').val();
                    var ParTieIna = $('#ParTieIna').val();
                    var ParSncAct = document.getElementById('ParSncAct').checked;


                    // Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
                    $.post('<% out.print(urlSistema); %>AM_Parametro', {
                        pParCod: ParCod,
                        pParEmlCod: ParEmlCod,
                        pParSisLocal: ParSisLocal,
                        pParUtlMdl: ParUtlMdl,
                        pParUrlMdl: ParUrlMdl,
                        pParMdlTkn: ParMdlTkn,
                        pParUrlSrvSnc: ParUrlSrvSnc,
                        pParPswValExp: ParPswValExp,
                        pParPswValMsg: ParPswValMsg,
                        pParDiaEvlPrv: ParDiaEvlPrv,
                        pParTieIna: ParTieIna,
                        pParSncAct: ParSncAct,
                        pAction: "ACTUALIZAR"

                    }, function (responseText) {
                        var obj = JSON.parse(responseText);

                        MostrarMensaje(obj.tipoMensaje, obj.mensaje);

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

                        <div class="contenedor-titulo">    
                            <p>Parámetros</p>
                        </div> 

                        <div style="height: 30px;"></div>

                        <form id="frm_Version" name="frm_Version">
                            <div>
                                <input type="hidden" id="ParCod" name="ParCod" placeholder="Código" disabled value="<% out.print(utilidad.NuloToVacio(parametro.getParCod())); %>">
                            </div>

                            <div>
                                <label>URL Sistema:</label>
                                <input type="text" class="form-control" id="ParUrlSis" name="ParUrlSis" placeholder="URL" disabled value="<% out.print(utilidad.NuloToVacio(parametro.getParUrlSis())); %>">
                            </div>

                            <div class="row">

                                <div class="col-lg-2">
                                    <label>Parámetro de email:</label>
                                </div>

                                <div class="col-lg-1">
                                    <input type="num" class="form-control" id="ParEmlCod" name="ParEmlCod" value="<% out.print(utilidad.NuloToVacio((parametro.getParametroEmail() != null ? parametro.getParametroEmail().getParEmlCod() : ""))); %>" disabled>                                
                                </div>
                                <div class="col-lg-2">
                                    <a href="#" id="btnParEmlCod" name="btnParEmlCod" class="glyphicon glyphicon-search" data-toggle="modal" data-target="#PopUpParamEml"></a>
                                </div>

                                <div class="col-lg-3">
                                    <label name="ParEmlNom" id="ParEmlNom" > <% out.print(utilidad.NuloToVacio((parametro.getParametroEmail() != null ? parametro.getParametroEmail().getParEmlNom() : null))); %></label>
                                </div>


                            </div>

                            <!--------------------------------------------------------------------------------------------------------------->

                            <div class="checkbox">
                                <label> <input type="checkbox" id="ParUtlMdl" name="ParUtlMdl" <% out.print(utilidad.BooleanToChecked(parametro.getParUtlMdl())); %>> Sincronizar con Moodle: </label>
                            </div>

                            <div>
                                <label>URL de Moodle:</label>
                                <input type="text" class="form-control" id="ParUrlMdl" name="ParUrlMdl" placeholder="URL" value="<% out.print(utilidad.NuloToVacio(parametro.getParUrlMdl())); %>">
                            </div>

                            <div>
                                <label>Token de Moodle:</label>
                                <input type="text" class="form-control" id="ParMdlTkn" name="ParMdlTkn" placeholder="Token" value="<% out.print(utilidad.NuloToVacio(parametro.getParMdlTkn())); %>">
                            </div>

                            <!--------------------------------------------------------------------------------------------------------------->

                            <div class="checkbox">
                                <label><input type="checkbox" id="ParSncAct" name="ParSncAct" <% out.print(utilidad.BooleanToChecked(parametro.getParSncAct())); %>> Sincronización activa</label>
                            </div>
                            <div>
                                <label>Última sincronización:</label>
                                <input type="datetime" id="ParFchUltSinc" class="form-control" name="ParFchUltSinc" disabled value="<% out.print(utilidad.NuloToVacio(parametro.getParFchUltSinc())); %>">
                            </div>

                            <div class="checkbox">
                                <label><input type="checkbox" id="ParSisLocal" name="ParSisLocal" <% out.print(utilidad.BooleanToChecked(parametro.getParSisLocal())); %>> Sistema local</label>
                            </div>

                            <div>
                                <label>URL Sistema Online:</label>
                                <input type="text" class="form-control" id="ParUrlSrvSnc" name="ParUrlSrvSnc" placeholder="URL" value="<% out.print(utilidad.NuloToVacio(parametro.getParUrlSrvSnc())); %>">
                            </div>

                            <!--------------------------------------------------------------------------------------------------------------->

                            <div>
                                <label>Expresión regular para validar contraseña:</label>
                                <input type="text" class="form-control" id="ParPswValExp" name="ParPswValExp" placeholder="Expresión regular" value="<% out.print(utilidad.NuloToVacio(parametro.getParPswValExp())); %>">
                            </div>

                            <div>
                                <label>Mensaje en caso de contraseña incorrecta:</label>
                                <input type="text" class="form-control" id="ParPswValMsg" name="ParPswValMsg" placeholder="Mensaje" value="<% out.print(utilidad.NuloToVacio(parametro.getParPswValMsg())); %>">
                            </div>

                            <!--------------------------------------------------------------------------------------------------------------->

                            <div>
                                <label>Dias de anticipación para notificar fecha proxima de evaluacion:</label>
                                <input type="number" class="form-control" id="ParDiaEvlPrv" name="ParDiaEvlPrv" placeholder="Días" value="<% out.print(utilidad.NuloToVacio(parametro.getParDiaEvlPrv())); %>">
                            </div>

                            <div>
                                <label>Tiempo de inactividad en meses, para notificacion de materias previas (Not. Motivacional):</label>
                                <input type="num" class="form-control" id="ParTieIna" name="ParTieIna" placeholder="Meses" value="<% out.print(utilidad.NuloToVacio(parametro.getParTieIna())); %>">
                            </div>


                            <!---------------------------------------------------------------------------------------------------------------
                            Fin del formulario
                            --------------------------------------------------------------------------------------------------------------->

                            <div>
                                <input name="btn_guardar" id="btn_guardar" value="Guardar" type="button" class="btn btn-success" />
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <jsp:include page="/masterPage/footer.jsp"/>
        </div>


        <div id="PopUpParamEml" class="modal fade" role="dialog">
            <!-- Modal -->
            <div class="modal-dialog">
                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Parámetro de email</h4>
                    </div>

                    <div class="modal-body">

                        <div>
                            <table name="PopUpTblParEml" id="PopUpTblParEml" class="table table-striped" cellspacing="0"  class="table" width="100%">
                                <thead>
                                    <tr>
                                        <th>Código</th>
                                        <th>Nombre</th>
                                        <th>Correo saliente</th>
                                    </tr>
                                </thead>

                            </table>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                    </div>
                </div>
            </div>



            <script type="text/javascript">

                $(document).ready(function () {

                    $(document).on('click', ".PopParamr_Seleccionar", function () {

                        var ParEmlCod = $(this).data("codigo");
                        var ParEmlNom = $(this).data("nombre");

                        $('#ParEmlCod').val(ParEmlCod);
                        $('#ParEmlNom').text(ParEmlNom);


                        $(function () {
                            $('#PopUpParamEml').modal('toggle');
                        });
                    });


                    $.post('<% out.print(urlSistema);%>ABM_ParametroEmail', {
                        pAction: "POPUP_OBTENER"
                    }, function (responseText) {

                        var parametros = JSON.parse(responseText);

                        $.each(parametros, function (f, param) {

                            param.parEmlCod = "<td> <a href='#' data-codigo='" + param.parEmlCod + "' data-nombre='" + param.parEmlNom + "' class='PopParamr_Seleccionar'>" + param.parEmlCod + " </a> </td>";
                        });

                        $('#PopUpTblParEml').DataTable({
                            data: parametros,
                            deferRender: true,
                            bLengthChange: false, //thought this line could hide the LengthMenu
                            pageLength: 10,
                            language: {
                                "lengthMenu": "Mostrando _MENU_ registros por página",
                                "zeroRecords": "No se encontraron registros",
                                "info": "Página _PAGE_ de _PAGES_",
                                "infoEmpty": "No hay registros",
                                "search": "Buscar:",
                                "paginate": {
                                    "first": "Primera",
                                    "last": "Ultima",
                                    "next": "Siguiente",
                                    "previous": "Anterior"
                                },
                                "infoFiltered": "(Filtrado de _MAX_ total de registros)"
                            }
                            , columns: [
                                {"data": "parEmlCod"},
                                {"data": "parEmlNom"},
                                {"data": "parEmlDeEml"}
                            ]

                        });

                    });



                });
            </script>
        </div>
    </body>
</html>
