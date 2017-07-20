<%-- 
    Document   : DefCalendarioWW
    Created on : 03-jul-2017, 18:28:52
    Author     : alvar
--%>
<%@page import="Logica.Seguridad"%>
<%@page import="Enumerado.NombreSesiones"%>
<%@page import="Enumerado.TipoMensaje"%>
<%@page import="Utiles.Retorno_MsgObj"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Entidad.Calendario"%>
<%@page import="java.util.List"%>
<%@page import="Logica.LoCalendario"%>
<%@page import="Utiles.Utilidades"%>
<%

    LoCalendario loCalendario     = LoCalendario.GetInstancia();
    Utilidades utilidad = Utilidades.GetInstancia();
    String urlSistema           = (String) session.getAttribute(NombreSesiones.URL_SISTEMA.getValor());
    
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
    
    List<Object> lstObjeto = new ArrayList<>();
    
    Retorno_MsgObj retorno = (Retorno_MsgObj) loCalendario.obtenerLista();
    if(!retorno.SurgioErrorListaRequerida())
    {
        lstObjeto = retorno.getLstObjetos();
    }
    else
    {
        out.print(retorno.getMensaje().toString());
    }
    
    String tblVisible = (lstObjeto.size() > 0 ? "" : "display: none;");

%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gestión Académica - Calendario</title>
        <jsp:include page="/masterPage/head.jsp"/>
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
                            <jsp:include page="/Definiciones/DefCalendarioWWTabs.jsp"/>
                        </div>
                
                        <div style="text-align: right; padding-top: 6px; padding-bottom: 6px;">
                            <a href="#" title="Ingresar" class="glyphicon glyphicon-plus" data-toggle="modal" data-target="#PopUpAgregar"> </a>
                        </div>

                        <table style=' <% out.print(tblVisible); %>' class='table table-hover'>
                            <thead>
                                <tr>
                                    <th></th>
                                    <th></th>
                                    <th>Código</th>
                                    <th>Evaluación</th>
                                    <th>Estudio</th>
                                    <th>Nombre</th>
                                    <th>Fecha</th>
                                    <th>Inscripción desde</th>
                                    <th>Inscripcion hasta</th>
                                </tr>
                            </thead>

                            <% for(Object objeto : lstObjeto)
                            {
                             Calendario calendario = (Calendario) objeto;
                            %>
                            <tr>
                                <td><a href="<% out.print(urlSistema); %>Definiciones/DefCalendario.jsp?MODO=<% out.print(Enumerado.Modo.DELETE); %>&pCalCod=<% out.print(calendario.getCalCod()); %>" name="btn_eliminar" id="btn_eliminar" title="Eliminar" class="glyphicon glyphicon-trash"/></td>
                                <td><a href="<% out.print(urlSistema); %>Definiciones/DefCalendario.jsp?MODO=<% out.print(Enumerado.Modo.UPDATE); %>&pCalCod=<% out.print(calendario.getCalCod()); %>" name="btn_editar" id="btn_editar" title="Editar" class='glyphicon glyphicon-edit'/></td>
                                <td><% out.print( utilidad.NuloToVacio(calendario.getCalCod())); %> </td>
                                <td><% out.print( utilidad.NuloToVacio(calendario.getEvaluacion().getEvlNom() )); %> </td>
                                <td><% out.print( utilidad.NuloToVacio(calendario.getEvaluacion().getEstudioTipo())); %> </td>
                                <td><% out.print( utilidad.NuloToVacio(calendario.getEvaluacion().getEstudioNombre())); %> </td>
                                <td><% out.print( utilidad.NuloToVacio(calendario.getCalFch())); %> </td>
                                <td><% out.print( utilidad.NuloToVacio(calendario.getEvlInsFchDsd())); %> </td>
                                <td><% out.print( utilidad.NuloToVacio(calendario.getEvlInsFchHst())); %> </td>

                            </tr>
                            <%
                            }
                            %>
                        </table>

                    </div>
                </div>
            </div>
        </div>
             
                        
        <div id="PopUpAgregar" class="modal fade" role="dialog">
               <!-- Modal -->
                <div class="modal-dialog modal-lg">
                    <!-- Modal content-->
                    <div class="modal-content">
                      <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Evaluaciones</h4>
                        <input type="hidden" name="obj_calendario" id="obj_calendario" value="<% out.print(utilidad.ObjetoToJson(new Calendario())); %>">
                      </div>
                     
                        <div class="modal-body">

                            <div>
                                <table name="PopUpTblEvaluaciones" id="PopUpTblEvaluaciones" class="table table-striped" cellspacing="0" width="100%">
                                    <thead>
                                        <tr>
                                            <th>Estudio</th>
                                            <th>Evaluación</th>
                                            <th>Inscripción automatica</th>
                                            <th>Fecha</th>
                                            <th>Inscripción desde</th>
                                            <th>Inscripción hasta</th>
                                            <th></th>
                                        </tr>
                                    </thead>

                                </table>
                            </div>
                      </div>
                      <div class="modal-footer">
                        <input name="btn_guardar" id="btn_guardar" value="Guardar" class="btn btn-success" type="button" />
                        <input type="button" class="btn btn-default" value="Cancelar" data-dismiss="modal" />
                      </div>
                    </div>
                </div>
               
                <script type="text/javascript">

                    $(document).ready(function() {

                        $(document).on('click', "#btn_guardar", function() {

                            var table = $('#PopUpTblEvaluaciones').DataTable();
                            
                            var count =  table.rows({selected:true}).count();
                            
                            var rows = table.rows({selected:true});
                            
                            var error           = false;
                            
                            for(i=0;i<count;i++)
                            {
                                var objeto  = rows.data()[i];
                                var fila    = rows.nodes()[i];
                                
                                var fechaEvaluacion = fila.cells[3].lastChild.value;
                                var fechaDesde      = fila.cells[4].lastChild.value;
                                var fechaHasta      = fila.cells[5].lastChild.value;
                                
                                if(fechaEvaluacion == "")
                                {
                                    MostrarMensaje("ERROR", "Debe ingresar una fecha de evaluación para: " + objeto.evlNom);
                                    error = true;
                                }
                                
                                if(!objeto.tpoEvl.tpoEvlInsAut)
                                {
                                    if(fechaDesde == "")
                                    {
                                        MostrarMensaje("ERROR", "Debe ingresar una fecha de inicio de inscripción para: " + objeto.evlNom);
                                        error = true;
                                    }

                                    if(fechaHasta == "")
                                    {
                                        MostrarMensaje("ERROR", "Debe ingresar una fecha de fin de inscripción para: " + objeto.evlNom);
                                        error = true;
                                    }
                                }
                                
                            }
                            
                             
                             
                             if(count < 1)
                            {
                                MostrarMensaje("ERROR", "Debe seleccionar al menos una fila");
                            }
                            else
                            {
                                if(!error)
                                {
                               
                                   
                                var listaCalendario = new Array(count);

                                    //Procesar
                                    for(i=0;i<count;i++)
                                    {
                                       
                                            var calendario = JSON.parse('{"evaluacion":{"evlCod":null},"calFch":null,"calCod":null,"evlInsFchHst":null,"evlInsFchDsd":null}');
                                            var objeto  = rows.data()[i];
                                            var fila    = rows.nodes()[i];

                                            var fechaEvaluacion = fila.cells[3].lastChild.value;
                                            var fechaDesde      = fila.cells[4].lastChild.value;
                                            var fechaHasta      = fila.cells[5].lastChild.value;
                                            
                                            calendario.evaluacion.evlCod = objeto.evlCod;
                                            calendario.calFch = fechaEvaluacion;
                                            calendario.evlInsFchHst = fechaDesde;
                                            calendario.evlInsFchDsd = fechaHasta;
                                            
                                            listaCalendario[i] = calendario;

                                    }
                                    
                                    
                                    $.ajax({
                                            url: '<% out.print(urlSistema); %>ABM_Calendario',
                                                type: 'POST',
                                                data: {
                                                    pLstCalendario: JSON.stringify(listaCalendario),
                                                    pAction          : "INSERT_LIST"
                                                },
                                                async: false,
                                                cache: false,
                                                timeout: 30000,
                                                success: function(responseText) {
                                                    var obj = JSON.parse(responseText);
                                                    
                                                    if(obj.tipoMensaje == 'ERROR')
                                                    {
                                                        MostrarMensaje(obj.tipoMensaje, obj.mensaje);

                                                    }
                                                    else{
                                                        location.reload();
                                                    }
                                                    
                                                }
                                        });

                                    
                                }

                            }
                            
                           // $(function () {
                           //         $('#PopUpTblEvaluaciones').modal('toggle');
                           //      });
                        });

                        $.post('<% out.print(urlSistema); %>ABM_Evaluacion', {
                            pAction : "POPUP_LISTAR"
                            }, function(responseText) {

                                var evaluaciones = JSON.parse(responseText);

                                $('#PopUpTblEvaluaciones').DataTable( {
                                    data: evaluaciones,
                                    deferRender: true,
                                    bLengthChange : false, //thought this line could hide the LengthMenu
                                    pageLength: 10,
                                    select: {
                                        style:    'multi',
                                        selector: 'td:last-child'
                                    },
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
                                    },
                                    columns: [
                                        {"data": "estudioNombre"},
                                        {"data": "evlNom"},
                                        {"data": "inscripcionAutomatica"},
                                        {   
                                            "orderable":      false,
                                            "data":           null,
                                            "defaultContent": '<input type="date" name="cel_fecha">'
                                        },
                                        {   
                                            "orderable":      false,
                                            "data":           null,
                                            "defaultContent": '<input type="date" name="cel_fecha_desde">'
                                        },
                                        {   
                                            "orderable":      false,
                                            "data":           null,
                                            "defaultContent": '<input type="date" name="cel_fecha_hasta" >'
                                        },
                                        {
                                            "className":      'select-checkbox',
                                            "orderable":      false,
                                            "data":           null,
                                            "defaultContent": ''
                                        }
                                    ]

                                } );

                        });

      
                    });
                </script>
        </div>
    </body>
</html>
