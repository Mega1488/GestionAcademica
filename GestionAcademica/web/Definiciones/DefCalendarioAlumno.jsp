<%-- 
    Document   : DefCalendarioAlumno
    Created on : 04-jul-2017, 19:31:27
    Author     : alvar
--%>

<%@page import="Enumerado.TipoMensaje"%>
<%@page import="Utiles.Retorno_MsgObj"%>
<%@page import="Enumerado.TipoPeriodo"%>
<%@page import="Entidad.CalendarioAlumno"%>
<%@page import="Entidad.Calendario"%>
<%@page import="Enumerado.Modo"%>
<%@page import="Utiles.Utilidades"%>
<%@page import="Logica.LoCalendario"%>
<%@page import="Entidad.Parametro"%>
<%@page import="Logica.LoParametro"%>
<%
    LoParametro loParam = LoParametro.GetInstancia();
    Parametro param = loParam.obtener(1);
    LoCalendario loCalendario = LoCalendario.GetInstancia();
    Utilidades utilidad = Utilidades.GetInstancia();
    String urlSistema = utilidad.GetUrlSistema();

    Modo Mode = Modo.valueOf(request.getParameter("MODO"));
    String CalCod = request.getParameter("pCalCod");
    String CalAlCod = request.getParameter("pCalAlCod");
    String js_redirect = "window.location.replace('" + urlSistema + "Definiciones/DefCalendarioAlumnoSWW.jsp?MODO=UPDATE&pCalCod=" + CalCod + "');";

    Calendario calendario = new Calendario();
    Retorno_MsgObj retorno = (Retorno_MsgObj) loCalendario.obtener(Long.valueOf(CalCod));
    if (retorno.getMensaje().getTipoMensaje() != TipoMensaje.ERROR) {
        calendario = (Calendario) retorno.getObjeto();
    } else {
        out.print(retorno.getMensaje().toString());
    }

    CalendarioAlumno calAlumno = new CalendarioAlumno();
    calAlumno.setCalendario(calendario);

    if (Mode.equals(Modo.UPDATE) || Mode.equals(Modo.DISPLAY) || Mode.equals(Modo.DELETE)) {
        calAlumno = calendario.getAlumnoById(Long.valueOf(CalAlCod));
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
        <title>Sistema de Gestión Académica - Calendario | Alumno</title>
        <jsp:include page="/masterPage/head.jsp"/>

        <script>
            $(document).ready(function () {
                $('#btn_guardar').click(function (event) {

                    MostrarCargando(true);

                    var CalCod = $('#CalCod').val();
                    var AluPerCod = $('#AluPerCod').val();
                    var EvlCalVal = $('#EvlCalVal').val();
                    var EvlCalPerCod = $('#EvlCalPerCod').val();
                    var EvlCalEst = $('#EvlCalEst').val();
                    var EvlValPerCod = $('#EvlValPerCod').val();
                    var EvlCalObs = $('#EvlCalObs').val();
                    var EvlValObs = $('#EvlValObs').val();
                    var EvlCalFch = $('#EvlCalFch').val();
                    var EvlValFch = $('#EvlValFch').val();

                    var modo = $('#MODO').val();

                    if (ModNom == '')
                    {
                        MostrarMensaje("ERROR", "Completa los datos papa");
                        MostrarCargando(false);
                    } else
                    {

                        $.post('<% out.print(urlSistema); %>ABM_CalendarioAlumno', {
                            pCalCod: CalCod,
                            pAluPerCod: AluPerCod,
                            pEvlCalVal: EvlCalVal,
                            pEvlCalPerCod: EvlCalPerCod,
                            pEvlCalEst: EvlCalEst,
                            pEvlValPerCod: EvlValPerCod,
                            pEvlCalObs: EvlCalObs,
                            pEvlValObs: EvlValObs,
                            pEvlCalFch: EvlCalFch,
                            pEvlValFch: EvlValFch,
                            pAction: modo
                        }, function (responseText) {
                            var obj = JSON.parse(responseText);
                            MostrarCargando(false);

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

                });

            });

        </script>

    </head>
    <body>
        <div class="container-fluid">

            <div id="cabezal" name="cabezal" class="row">
                <jsp:include page="/masterPage/cabezal.jsp"/>
            </div>


            <div class="col-sm-2">
                <jsp:include page="/masterPage/menu_izquierdo.jsp" />
            </div>

            <div id="contenido" name="contenido"  class="col-sm-8">

                <h1>Alumno</h1>

                <div style="display:none" id="datos_ocultos" name="datos_ocultos">
                    <input type="hidden" name="MODO" id="MODO" value="<% out.print(Mode); %>">
                    <input type="hidden" name="CalCod" id="CalCod" value="<% out.print(CalCod); %>">
                </div>
                
                <form id="frm_objeto" name="frm_objeto">

                    <div><label>Codigo</label><input type="text" class="form-control" id="CalAlCod" name="CalAlCod" placeholder="CalAlCod" disabled value="<% out.print( utilidad.NuloToVacio(calAlumno.getCalAlCod())); %>" ></div>
                   
                    <div>
                        <label>Alumno:</label>
                        <input type="text" class="form-control" id="AluPerCod" name="AluPerCod" placeholder="AluPerCod" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio((calAlumno.getAlumno() != null ? calAlumno.getAlumno().getPerCod() : "" ))); %>" >
                        
                        <a href="#" id="btnEvlCod" name="btnEvlCod" class="glyphicon glyphicon-search" data-toggle="modal" data-target="#PopUpPersona"></a>
                        
                    </div>
                    <div><label>Calificación:</label><input type="text" class="form-control" id="EvlCalVal" name="EvlCalVal" placeholder="EvlCalVal" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio(calAlumno.getEvlCalVal())); %>" ></div>
                    <div><label>Calificado por:</label><input type="text" class="form-control" id="EvlCalPerCod" name="EvlCalPerCod" placeholder="EvlCalPerCod" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio((calAlumno.getEvlCalPor() != null ? calAlumno.getEvlCalPor().getPerCod() : "" ))); %>" ></div>
                    <div><label>Estado:</label><input type="text" class="form-control" id="EvlCalEst" name="EvlCalEst" placeholder="EvlCalEst" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio(calAlumno.getEvlCalEst())); %>" ></div>
                    <div><label>Validado por:</label><input type="text" class="form-control" id="EvlValPerCod" name="EvlValPerCod" placeholder="EvlValPerCod" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio((calAlumno.getEvlValPor() != null ? calAlumno.getEvlValPor().getPerCod() : "" ))); %>" ></div>
                    <div><label>Calificación Observaciones:</label><input type="text" class="form-control" id="EvlCalObs" name="EvlCalObs" placeholder="EvlCalObs" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio(calAlumno.getEvlCalObs())); %>" ></div>
                    <div><label>Validación observaciones:</label><input type="text" class="form-control" id="EvlValObs" name="EvlValObs" placeholder="EvlValObs" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio(calAlumno.getEvlValObs())); %>" ></div>
                    <div><label>Fecha de calificación:</label><input type="text" class="form-control" id="EvlCalFch" name="EvlCalFch" placeholder="EvlCalFch" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio(calAlumno.getEvlCalFch())); %>" ></div>
                    <div><label>Fecha de validación:</label><input type="text" class="form-control" id="EvlValFch" name="EvlValFch" placeholder="EvlValFch" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio(calAlumno.getEvlValFch())); %>" ></div>
                    
                    <div>
                        <input name="btn_guardar" id="btn_guardar" value="Guardar" type="button" />
                    </div>
                </form>
            </div>
        </div>
                    
        <div id="PopUpPersona" title="Persona" class="modal fade" role="dialog">
            <jsp:include page="/PopUps/PopUpPersona.jsp"/>
        </div>
        
    </body>
</html>