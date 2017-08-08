<%-- 
    Document   : DefCarrera
    Created on : jun 21, 2017, 7:51:06 p.m.
    Author     : aa
--%>

<%@page import="Logica.Seguridad"%>
<%@page import="Enumerado.NombreSesiones"%>
<%@page import="Enumerado.TipoMensaje"%>
<%@page import="Utiles.Retorno_MsgObj"%>
<%@page import="Logica.LoCarrera"%>
<%@page import="Entidad.Carrera"%>
<%@page import="Enumerado.Modo"%>
<%@page import="Utiles.Utilidades"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<%
    Utilidades utilidad = Utilidades.GetInstancia();
    LoCarrera loCar = LoCarrera.GetInstancia();
    Carrera car = new Carrera();
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
    String CarCod = request.getParameter("pCarCod");
    Modo mode = Modo.valueOf(request.getParameter("MODO"));
    String js_redirect = "window.location.replace('" + urlSistema + "Definiciones/DefCarreraWW.jsp');";
    String CamposActivos = "disabled";
    String boton = "";

    if (mode.equals(Modo.UPDATE) || mode.equals(Modo.DISPLAY) || mode.equals(Modo.DELETE)) {
        Retorno_MsgObj retorno = (Retorno_MsgObj) loCar.obtener(Long.valueOf(CarCod));
        if (retorno.getMensaje().getTipoMensaje() != TipoMensaje.ERROR) {
            car = (Carrera) retorno.getObjeto();
        } else {
            out.print(retorno.getMensaje().toString());
        }
    }

    switch (mode) {
        case INSERT:
            CamposActivos = "enabled";
            boton = "<input name='BtnAceCar' id='BtnAceCar' value='Guardar' type='button' class='btn btn-success' />";
            break;
        case DELETE:
            CamposActivos = "disabled";
            boton = "<input href='#' value='Eliminar' class='btn btn-danger' type='button' data-toggle='modal' data-target='#PopUpElimCarrera'/>";
//            boton           = "btn btn-danger";
            break;
        case DISPLAY:
            CamposActivos = "disabled";
            boton = "<input name='BtnAceCar' id='BtnAceCar' value='Guardar' type='button' class='btn btn-success' />";
            break;
        case UPDATE:
            CamposActivos = "enabled";
            boton = "<input name='BtnAceCar' id='BtnAceCar' value='Guardar' type='button' class='btn btn-success' />";
            break;
    }
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gestión Académica - Carrera</title>
        <jsp:include page="/masterPage/head.jsp"/>

        <script>
            $(document).ready(function () {

                $('#BtnAceCar').click(function ()
                {
                    var codVar = $('#CarCod').val();
                    var nomVar = $('#CarNom').val();
                    var dscVar = $('#CarDsc').val();
                    var facVar = $('#CarFac').val();
                    var crtVar = $('#CarCrt').val();

                    if (nomVar == '' && $('#MODO').val() != "DELETE")
                    {
                        MostrarMensaje("ERROR", "Deberá asignar un nombre a la Carrera");
                    } else
                    {
                        if ($('#MODO').val() == "INSERT")
                        {
                            // Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
                            $.post('<% out.print(urlSistema); %>ABM_Carrera', {
                                pNom: nomVar,
                                pDsc: dscVar,
                                pfac: facVar,
                                pCrt: crtVar,
                                pAccion: "INGRESAR"
                            }, function (responseText) {
                                var obj = JSON.parse(responseText);

                                if (obj.tipoMensaje != 'ERROR')
                                {
            <%out.print(js_redirect);%>
                                } else
                                {
                                    MostrarMensaje(obj.tipoMensaje, obj.mensaje);
                                }
                            });
                        }

                        if ($('#MODO').val() == "UPDATE")
                        {
                            // Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
                            $.post('<% out.print(urlSistema); %>ABM_Carrera', {
                                pCod: codVar,
                                pNom: nomVar,
                                pDsc: dscVar,
                                pfac: facVar,
                                pCrt: crtVar,
                                pAccion: "MODIFICAR"
                            }, function (responseText) {
                                var obj = JSON.parse(responseText);

                                if (obj.tipoMensaje != 'ERROR')
                                {
            <%out.print(js_redirect);%>
                                } else
                                {
                                    MostrarMensaje(obj.tipoMensaje, obj.mensaje);
                                }
                            });
                        }

                        if ($('#MODO').val() == "DELETE")
                        {
                            $.post('<% out.print(urlSistema); %>ABM_Carrera', {
                                pCod: codVar,
                                pAccion: "ELIMINAR"
                            }, function (responseText) {
                                var obj = JSON.parse(responseText);

                                if (obj.tipoMensaje != 'ERROR')
                                {
            <%out.print(js_redirect);%>
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
                            <jsp:include page="/Definiciones/DefCarreraTabs.jsp"/>
                        </div>

                        <div class=""> 
                            <div class="" style="text-align: right;"><a href="<% out.print(urlSistema); %>Definiciones/DefCarreraWW.jsp?MODE=<%out.print(Enumerado.Modo.DISPLAY);%>">Regresar</a></div>
                        </div>

                        <div style="display:none" id="datos_ocultos" name="datos_ocultos">
                            <input type="hidden" name="MODO" id="MODO" value="<% out.print(mode); %>">
                        </div>

                        <form id="frm_objeto" name="frm_objeto">
                            <div><label>Código</label><input type="number" class="form-control" id="CarCod" name="CarCod" placeholder="Código" disabled value="<% out.print(utilidad.NuloToVacio(car.getCarCod())); %>"></div>
                            <div><label>Nombre</label><input type="text" class="form-control" id="CarNom" name="CarNom" placeholder="Nombre" <% out.print(CamposActivos); %> value="<% out.print(utilidad.NuloToVacio(car.getCarNom())); %>"></div>
                            <div><label>Descripción</label><input type="text" class="form-control" id="CarDsc" name="CarDsc" placeholder="Descripción" <% out.print(CamposActivos); %> value="<% out.print(utilidad.NuloToVacio(car.getCarDsc())); %>"></div>
                            <div><label>Facultad</label><input type="text" class="form-control" id="CarFac" name="CarFac" placeholder="Facultad" <% out.print(CamposActivos); %> value="<% out.print(utilidad.NuloToVacio(car.getCarFac())); %>"></div>
                            <div><label>Certificación</label><input type="text" class="form-control" id="CarCrt" name="CarCrt" placeholder="Certificación" <% out.print(CamposActivos); %> value="<% out.print(utilidad.NuloToVacio(car.getCarCrt())); %>"></div>

                            <br>
                            <div>
                                <%out.print(boton);%> 
                                <input value="Cancelar" class="btn btn-default" type="button" onclick="self.location.href = '<%out.print(urlSistema); %>Definiciones/DefCarreraWW.jsp'"/>
                            </div>
                        </form>
                    </div>
                </div>
            </div>   

            <jsp:include page="/masterPage/footer.jsp"/>
        </div>

        <!--Popup Confirmar Eliminación-->
        <div id="PopUpElimCarrera" class="modal fade" role="dialog">
            <!-- Modal -->
            <div class="modal-dialog modal-lg">
                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Eliminar Carrera</h4>
                    </div>
                    <div class="modal-body">
                        <div>
                            <h4>¿Desea eliminar la Carrera: <% out.print(utilidad.NuloToVacio(car.getCarNom()));%>?</h4>
                            <h4>Se eliminará tambien los Planes, Materias y Evaluaciones que contenga</h4>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <input name="BtnAceCar" id="BtnAceCar" value="Confirmar" type="button" class="btn btn-success" />
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                    </div>
                </div>
            </div>
        </div>                       
    </body>
</html>