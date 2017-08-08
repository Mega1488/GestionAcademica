<%-- 
    Document   : DefMateriaEvaluacionWW
    Created on : jul 20, 2017, 3:28:32 p.m.
    Author     : aa
--%>

<%@page import="Entidad.MateriaPrevia"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
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

    LoCarrera loCarrera = LoCarrera.GetInstancia();
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
    String PlaEstCod = request.getParameter("pPlaEstCod");
    String CarCod = request.getParameter("pCarCod");
    String MatCod = request.getParameter("pMatCod");

    PlanEstudio planEstudio = new PlanEstudio();
    Materia materia = new Materia();

    List<MateriaPrevia> lstMatPrevia = new ArrayList<>();

    Retorno_MsgObj retorno = (Retorno_MsgObj) loCarrera.obtener(Long.valueOf(CarCod));
    if (!retorno.SurgioErrorObjetoRequerido()) {
        planEstudio = ((Carrera) retorno.getObjeto()).getPlanEstudioById(Long.valueOf(PlaEstCod));
        materia = planEstudio.getMateriaById(Long.valueOf(MatCod));
        lstMatPrevia = materia.getLstPrevias();
    } else {
        out.print(retorno.getMensaje().toString());
    }

    String tblVisible = (lstMatPrevia.size() > 0 ? "" : "display: none;");

%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gestión Académica - Materia | Previas</title>
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
                            <jsp:include page="/Definiciones/DefMateriaTabs.jsp"/>
                        </div>

                        <div class=""> 
                            <div class="" style="text-align: right;"><a href="<% out.print(urlSistema); %>Definiciones/DefMateriaSWW.jsp?MODO=DISPLAY&pCarCod=<% out.print(CarCod); %>&pPlaEstCod=<% out.print(PlaEstCod); %>&pMatCod=<% out.print(MatCod); %>">Regresar</a></div>
                        </div>

                        <div style="text-align: right; padding-top: 6px; padding-bottom: 6px;">
                            <a href="#" title="Ingresar" class="glyphicon glyphicon-plus" data-toggle="modal" data-target="#PopUpAgregar"> </a>
                            <input type="hidden" name="MODO" id="MODO" value="<% out.print(Mode); %>">
                            <input type="hidden" name="CarCod" id="CarCod" value="<% out.print(CarCod); %>">
                            <input type="hidden" name="PlaEstCod" id="PlaEstCod" value="<% out.print(PlaEstCod); %>">
                            <input type="hidden" name="MatCod" id="MatCod" value="<% out.print(MatCod); %>">
                        </div>

                        <table style=' <% out.print(tblVisible); %>' class='table table-hover'>
                            <thead>
                                <tr>
                                    <th></th>
                                    <th>Código</th>
                                    <th>Nombre</th>
                                    <th>Cantidad de Horas</th>
                                    <th>Tipo de Aprobación</th>
                                    <th>Tipo de Período</th>
                                    <th>Valor del Período</th>
                                    <th>Materias Previas</th>
                                </tr>
                            </thead>

                            <%
                                for (MateriaPrevia matPrevia : lstMatPrevia) {
                            %>
                            <tr>
                                <td><% out.print("<a href='#' data-codigo='" + matPrevia.getMatPreCod() + "' data-nombre='" + matPrevia.getMateriaPrevia().getMatNom() + "' data-toggle='modal' data-target='#PopUpEliminar' name='btn_eliminar' id='btn_eliminar' title='Eliminar' class='glyphicon glyphicon-trash btn_eliminar'/>"); %> </td>
                                <td><% out.print(utilidad.NuloToVacio(matPrevia.getMateriaPrevia().getMatCod())); %> </td>
                                <td><% out.print(utilidad.NuloToVacio(matPrevia.getMateriaPrevia().getMatNom())); %> </td>
                                <td><% out.print(utilidad.NuloToVacio(matPrevia.getMateriaPrevia().getMatCntHor())); %> </td>
                                <td><% out.print(utilidad.NuloToVacio(matPrevia.getMateriaPrevia().getMatTpoApr().getTipoAprobacionN())); %> </td>
                                <td><% out.print(utilidad.NuloToVacio(matPrevia.getMateriaPrevia().getMatTpoPer().getTipoPeriodoNombre())); %> </td>
                                <td><% out.print(utilidad.NuloToVacio(matPrevia.getMateriaPrevia().getMatPerVal())); %> </td>
                                <td><%out.print(utilidad.NuloToCero(matPrevia.getMateriaPrevia().getLstPrevias().size())); %></td>
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


        <!-- PopUp para Eliminar -->

        <div id="PopUpEliminar"  class="modal fade" role="dialog">

            <!-- Modal -->
            <div class="modal-dialog">
                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Eliminar</h4>
                    </div>
                    <div class="modal-body">

                        <p>Eliminar la materia previa: <label name="elim_nombre" id="elim_nombre"></label></p>
                        <p>Quiere proceder?</p>

                    </div>
                    <div class="modal-footer">
                        <button name="elim_boton_confirmar" id="elim_boton_confirmar" type="button" class="btn btn-danger" data-codigo="">Eliminar</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                    </div>
                </div>
            </div>
            <script type="text/javascript">
                $(document).ready(function () {

                    $('.btn_eliminar').on('click', function (e) {

                        var codigo = $(this).data("codigo");
                        var nombre = $(this).data("nombre");

                        $('#elim_nombre').text(nombre);
                        $('#elim_boton_confirmar').data('codigo', codigo);


                    });

                    $('#elim_boton_confirmar').on('click', function (e) {

                        var codigo = $('#elim_boton_confirmar').data('codigo');
                        var CarCod = $('#CarCod').val();
                        var PlaEstCod = $('#PlaEstCod').val();
                        var MatCod = $('#MatCod').val();

                        $.post('<% out.print(urlSistema); %>ABM_Materia', {
                            pCarCod: CarCod,
                            pPlaEstCod: PlaEstCod,
                            pMatCod: MatCod,
                            pMatPreCod: codigo,
                            pAccion: "ELIMINAR_PREVIA"
                        }, function (responseText) {
                            var obj = JSON.parse(responseText);

                            $(function () {
                                $('#PopUpEliminar').modal('toggle');
                            });

                            MostrarMensaje(obj.tipoMensaje, obj.mensaje);

                            if (obj.tipoMensaje != 'ERROR')
                            {
                                location.reload();
                            }

                        });
                    });

                });
            </script>
        </div>


        <!-- PopUp para Agregar -->

        <div id="PopUpAgregar"  class="modal fade" role="dialog">

            <!-- Modal -->
            <div class="modal-dialog">
                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Agregar</h4>
                    </div>
                    <div class="modal-body">

                        <p>Seleccione una materia</p>

                        <select class="form-control" id="MatPreCod" name="MatPreCod">
                            <%
                                for (Materia mat : planEstudio.getLstMateria()) {
                                    if (materia.getMatCod() != mat.getMatCod()) {
                                        out.println("<option value='" + mat.getMatCod() + "'>" + mat.getMatNom() + "</option>");
                                    }
                                }
                            %>
                        </select>

                    </div>
                    <div class="modal-footer">
                        <button name="agregar_boton_confirmar" id="agregar_boton_confirmar" type="button" class="btn btn-success" data-codigo="">Confirmar</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                    </div>
                </div>
            </div>
            <script type="text/javascript">
                $(document).ready(function () {

                    $('#agregar_boton_confirmar').on('click', function (e) {

                        var codigo = $('select[name=MatPreCod]').val();
                        var CarCod = $('#CarCod').val();
                        var PlaEstCod = $('#PlaEstCod').val();
                        var MatCod = $('#MatCod').val();

                        $.post('<% out.print(urlSistema);%>ABM_Materia', {
                            pCarCod: CarCod,
                            pPlaEstCod: PlaEstCod,
                            pMatCod: MatCod,
                            pPreMatCod: codigo,
                            pAccion: "AGREGAR_PREVIA"
                        }, function (responseText) {
                            var obj = JSON.parse(responseText);

                            $(function () {
                                $('#PopUpAgregar').modal('toggle');
                            });

                            MostrarMensaje(obj.tipoMensaje, obj.mensaje);

                            if (obj.tipoMensaje != 'ERROR')
                            {
                                location.reload();
                            }

                        });



                    });

                });
            </script>
        </div>
    </body>
</html>