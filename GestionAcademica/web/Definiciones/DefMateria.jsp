<%-- 
    Document   : DefMateria
    Created on : jul 17, 2017, 4:21:56 p.m.
    Author     : aa
--%>

<%@page import="Entidad.Carrera"%>
<%@page import="Enumerado.TipoAprobacion"%>
<%@page import="Enumerado.TipoPeriodo"%>
<%@page import="Logica.Seguridad"%>
<%@page import="Utiles.Retorno_MsgObj"%>
<%@page import="Entidad.Materia"%>
<%@page import="Logica.LoCarrera"%>
<%@page import="Entidad.PlanEstudio"%>
<%@page import="Enumerado.NombreSesiones"%>
<%@page import="Utiles.Utilidades"%>
<%@page import="Enumerado.Modo"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%    
    Utilidades utilidad     = Utilidades.GetInstancia();
    PlanEstudio plan        = new PlanEstudio();
    Carrera car             = new Carrera();
    Materia mat             = new Materia();
    LoCarrera   loCar       = LoCarrera.GetInstancia();
    
    String urlSistema       = (String) session.getAttribute(NombreSesiones.URL_SISTEMA.getValor());
    //----------------------------------------------------------------------------------------------------
    //CONTROL DE ACCESO
    //----------------------------------------------------------------------------------------------------
    
    String  usuario = (String) session.getAttribute(NombreSesiones.USUARIO.getValor());
    Boolean esAdm   = (Boolean) session.getAttribute(NombreSesiones.USUARIO_ADM.getValor());
    Boolean esAlu   = (Boolean) session.getAttribute(NombreSesiones.USUARIO_ALU.getValor());
    Boolean esDoc   = (Boolean) session.getAttribute(NombreSesiones.USUARIO_DOC.getValor());
    Retorno_MsgObj acceso = Seguridad.GetInstancia().ControlarAcceso(usuario, esAdm, esDoc, esAlu, utilidad.GetPaginaActual(request));
    
    if(acceso.SurgioError()) response.sendRedirect((String) acceso.getObjeto());
            
    //----------------------------------------------------------------------------------------------------
    
    String PlaEstCod        = request.getParameter("pPlaEstCod");
    String CarCod           = request.getParameter("pCarCod");
    String MatCod           = request.getParameter("pMatCod");
    Modo mode               = Modo.valueOf(request.getParameter("MODO"));
    String js_redirect      = "window.location.replace('" + urlSistema +  "Definiciones/DefMateriaWW.jsp?MODE="+ mode.DISPLAY +"&pPlaEstCod="+PlaEstCod+"&pCarCod="+ CarCod +"');";
    String CamposActivos    = "disabled";
    
    if(mode.equals(Modo.UPDATE) || mode.equals(Modo.DISPLAY) || mode.equals(Modo.DELETE))
    {
        Retorno_MsgObj retorno = (Retorno_MsgObj) loCar.obtener(Long.valueOf(CarCod));
        if(!retorno.SurgioErrorObjetoRequerido())
        {
            car     = (Carrera) retorno.getObjeto();
            plan    = car.getPlanEstudioById(Long.valueOf(PlaEstCod));
            mat     = plan.getMateriaById(Long.valueOf(MatCod));
        }
        else
        {
            out.print(retorno.getMensaje().toString());
        }
    }
    
    switch(mode)
    {
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

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gestión Académica - Materia</title>
        <jsp:include page="/masterPage/head.jsp"/>

        <script>
                $(document).ready(function() 
                {
                    $('#BtnAceMat').click(function()
                    {
                        var vCarCod          = $('#CarCod').val();
                        var PlaEstCod       = $('#PlaEstCod').val();
                        var MatCod          = $('#MatCod').val();
                        var MatNom          = $('#MatNom').val();
                        var MatCntHor       = $('#MatCntHor').val();
                        var MatTpoApr       = $('select[name=MatTpoApr]').val();
                        var MatTpoPer       = $('select[name=MatTpoPer]').val();
                        var MatPerVal       = $('#MatPerVal').val();
                        var PreMatCod       = $('#PreMatCod').val();
                        var accion          = $('#MODO').val();

                        if(MatNom == '' && $('#MODO').val()!= "DELETE")
                        {
                            MostrarMensaje("ERROR", "Deberá asignar un nombre a la Materia");
                        }
                        else
                        {
                            // Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
                            $.post('<% out.print(urlSistema); %>ABM_Materia', {
                                    pCarCod         : vCarCod,
                                    pPlaEstCod      : PlaEstCod,
                                    pMatCod         : MatCod,
                                    pMatNom         : MatNom,
                                    pMatCntHor      : MatCntHor,
                                    pMatTpoApr      : MatTpoApr,
                                    pMatTpoPer      : MatTpoPer,
                                    pMatPerVal      : MatPerVal,
                                    pPreMatCod      : PreMatCod,
                                    pAccion         : accion
                            }, function(responseText) {
                                var obj = JSON.parse(responseText);
                               
                                if (obj.tipoMensaje != 'ERROR')
                                {
                                    <%out.print(js_redirect);%>
                                }
                                else
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
                            <div class="" style="text-align: right;"><a href="<% out.print(urlSistema); %>Definiciones/DefMateriaWW.jsp?MODE=<%out.print(Enumerado.Modo.DISPLAY);%>&pPlaEstCod=<%out.print(PlaEstCod.toString());%>&pCarCod=<%out.print(CarCod.toString());%>">Regresar</a></div>
                        </div>
                        
                        <div style="display:none" id="datos_ocultos" name="datos_ocultos">
                            <input type="hidden" name="MODO" id="MODO" value="<% out.print(mode); %>">
                            <input type="hidden" name="PlaEstCod" id="PlaEstCod" value="<% out.print(PlaEstCod); %>">
                            <input type="hidden" name="CarCod" id="CarCod" value="<% out.print(CarCod); %>">
                        </div>

                        <form id="frm_objeto" name="frm_objeto">
                            
                            <div><label>Código</label><input type="number" class="form-control" id="MatCod" name="MatCod" placeholder="Código" disabled value="<% out.print( utilidad.NuloToVacio(mat.getMatCod())); %>"></div>
                            <div><label>Nombre</label><input type="text" class="form-control" id="MatNom" name="MatName" placeholder="Nombre" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio(mat.getMatNom())); %>"></div>
                            <div><label>Cantidad de horas</label><input type="number" step="0.5" class="form-control" id="MatCntHor" name="MatCntHor" placeholder="Cantidad de horas" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio(mat.getMatCntHor())); %>"></div>
                            <div><label>Tipo de aprobación</label> 
                            <select class="form-control" id="MatTpoApr" name="MatTpoApr" <%out.print(CamposActivos);%>>
                                <%
                                    for(TipoAprobacion tpoApr : TipoAprobacion.values())
                                    {
                                        if(mat.getMatTpoApr() == tpoApr)
                                        {
                                            out.println("<option selected value='" + tpoApr.getTipoAprobacionC() + "'>" + tpoApr.getTipoAprobacionN() + "</option>");
                                        }
                                        else
                                        {
                                            out.println("<option value='" + tpoApr.getTipoAprobacionC() + "'>" + tpoApr.getTipoAprobacionN() + "</option>");
                                        }
                                    }
                                %>
                            </select>
                            </div>
                            <label>Tipo de Período</label>      
                            <select class="form-control" id="MatTpoPer" name="MatTpoPer" <%out.print(CamposActivos);%>>
                                <%
                                    for(TipoPeriodo tpoPer : TipoPeriodo.values())
                                    {
                                        if(mat.getMatTpoPer() == tpoPer)
                                        {
                                            out.println("<option selected value='" + tpoPer.getTipoPeriodo() + "'>" + tpoPer.getTipoPeriodoNombre() + "</option>");
                                        }
                                        else
                                        {
                                            out.println("<option value='" + tpoPer.getTipoPeriodo() + "'>" + tpoPer.getTipoPeriodoNombre() + "</option>");
                                        }
                                    }
                                %>
                            </select>
                            <div><label>Valor del Período</label><input type="number" step="0.5" class="form-control" id="MatPerVal" name="MatPerVal" placeholder="Valor del Período" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio(mat.getMatPerVal())); %>"></div>
                            <div>
                                <label>Materia Previa</label>
                                <input type="text" class="form-control" id="PreMatCod" name="PreMatCod" placeholder="Materia Previa" disabled value="<% out.print(utilidad.NuloToVacio((mat.getMateriaPrevia() == null ? "" : mat.getMateriaPrevia().getMatNom()))); %>" >
                                <a href="#" id="btnMatCod" name="btnMatCod" class="glyphicon glyphicon-search" data-toggle="modal" data-target="#PopUpMateria"></a>
                            </div>

                            <div>
                                <input name="BtnAceMat" id="BtnAceMat" value="Guardar" type="button" class="btn btn-success"/>
                                <input value="Cancelar" class="btn btn-default" type="button" onclick="self.location.href='<%out.print(urlSistema); %>Definiciones/DefMateriaWW.jsp?MODE=<%out.print(Enumerado.Modo.DISPLAY);%>&pPlaEstCod=<%out.print(PlaEstCod.toString());%>&pCarCod=<%out.print(CarCod.toString());%>'"/>
                            </div>                
                        </form>
                    </div>                                            
                </div>
            </div>
        </div>
        
        <!--Popup Materia Pendiente-->
        <div id="PopUpMateria" class="modal fade" role="dialog">
        <!-- Modal -->
            <div class="modal-dialog modal-lg">
                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Materias</h4>
                    </div>
                    <div class="modal-body">
                        <div>
                            <table id="PopUpTblMateria" name="PopUpTblMateria" class="table table-striped" cellspacing="0"  class="table" width="100%">
                                <thead>
                                    <tr>
                                        <th>Codigo</th>
                                        <th>Nombre</th>
                                        <th>Cantidad horas</th>
                                        <th>Tipo Aprobación</th>
                                        <th>Tipo Periodo</th>
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

                $(document).ready(function() {

                    Buscar();

                    $(document).on('click', ".PopPer_Seleccionar", function() 
                    {
                        var MatCod = $(this).data("codigo");
                        $('#PreMatCod').val(MatCod);

                        $(function () 
                        {
                            $('#PopUpMateria').modal('toggle');
                        });
                    });

                    function Buscar()
                    {
                        var PlaEstCod   = $('#PlaEstCod').val();

                        $.post('<% out.print(urlSistema); %>ABM_Materia', 
                        {
                            popPlaEstCod    : PlaEstCod,
                            pAccion         : "POPUP_OBTENER"
                        }
                        , function(responseText)
                        {
                            var materias = JSON.parse(responseText);

                            $.each(materias, function(f , materia) {
                                
                                materia.matCod = "<td> <a href='#' data-codigo='"+ materia.matCod+"' data-nombre='"+materia.matNom+"' class='PopPer_Seleccionar'>"+materia.matCod+" </a> </td>";
                            
                            });

                            $('#PopUpTblMateria').DataTable( {
                                data: materias,
                                deferRender: true,
                                bLengthChange: false, //thought this line could hide the LengthMenu
                                pageLength: 10,
                                language: {
                                    "lengthMenu": "Mostrando _MENU_ registros por página",
                                    "zeroRecords": "No se encontraron registros",
                                    "info": "Página _PAGE_ de _PAGES_",
                                    "infoEmpty": "No hay registros",
                                    "search":         "Buscar:",
                                    "paginate": {
                                            "first":      "Primera",
                                            "last":       "Ultima",
                                            "next":       "Siguiente",
                                            "previous":   "Anterior"
                                        },
                                    "infoFiltered": "(Filtrado de _MAX_ total de registros)"
                                }
                                ,columns: [
                                    { "data": "matCod" },
                                    { "data": "matNom"},
                                    { "data": "matCntHor"},
                                    { "data": "matTpoApr"},
                                    { "data": "matTpoPer"}
                                ]
                            });
                        });
                    }
                });
            </script>
        </div>
    </body>
</html>