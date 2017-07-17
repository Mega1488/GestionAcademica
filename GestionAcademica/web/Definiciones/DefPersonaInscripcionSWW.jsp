<%-- 
    Document   : DefPersonaEstudio
    Created on : 30-jun-2017, 20:50:47
    Author     : alvar
--%>
<%@page import="Entidad.Inscripcion"%>
<%@page import="Logica.LoInscripcion"%>
<%@page import="Enumerado.Modo"%>
<%@page import="Logica.Seguridad"%>
<%@page import="Enumerado.NombreSesiones"%>
<%@page import="Enumerado.TipoMensaje"%>
<%@page import="Utiles.Retorno_MsgObj"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="Utiles.Utilidades"%>

<%

    Utilidades utilidad         = Utilidades.GetInstancia();
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
    
    String PerCod       = request.getParameter("pPerCod");
    
    List<Object> lstObjeto = new ArrayList<>();
    
    Retorno_MsgObj retorno = (Retorno_MsgObj) LoInscripcion.GetInstancia().obtenerListaByAlumno(Long.valueOf(PerCod));
 
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
        <title>Sistema de Gestión Académica - Persona | Inscripción</title>
        <jsp:include page="/masterPage/head.jsp"/>
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
                    <div class="row"> 
                        <div class="col-lg-6"><h1>Inscripción</h1></div>
                        <div class="col-lg-6" style="text-align: right;"><a href="<% out.print(urlSistema); %>Definiciones/DefPersonaWW.jsp">Regresar</a></div>
                    </div>
             
                    <div id="tabs" name="tabs">
                        <jsp:include page="/Definiciones/DefPersonaTabs.jsp"/>
                    </div>

                    <div style="text-align: right; padding-top: 6px; padding-bottom: 6px;">
                        <a href="#" title="Ingresar" class="glyphicon glyphicon-plus" data-toggle="modal" data-target="#PopUpAgregar"> </a>
                        <input type="hidden" name="PerCod" id="PerCod" value="<% out.print(PerCod); %>">
                    </div>


                        <table style=' <% out.print(tblVisible); %>' class='table table-hover'>
                            <thead><tr>
                                <th></th>
                                <th>Código</th>
                                <th>Alumno</th>
                                <th>Estudio</th>
                                <th>Fecha de inscripción</th>
                                <th>Fecha de certificación</th>
                            </tr>
                            </thead>
                            
                            <tbody>
                            <% for(Object objeto : lstObjeto)
                            {
                                Inscripcion insc = (Inscripcion) objeto;
                                
                            %>
                            <tr>
                                <td><% out.print("<a href='#' data-codigo='" + insc.getInsCod() + "' data-nombre='" + insc.getAlumno().getNombreCompleto() +"' data-toggle='modal' data-target='#PopUpEliminar' name='btn_eliminar' id='btn_eliminar' title='Eliminar' class='glyphicon glyphicon-trash btn_eliminar'/>"); %> </td>
                                <td><% out.print( utilidad.NuloToVacio(insc.getInsCod())); %> </td>
                                <td><% out.print( utilidad.NuloToVacio(insc.getAlumno().getNombreCompleto())); %> </td>
                                <td><% out.print( utilidad.NuloToVacio(insc.getNombreEstudio())); %> </td>
                                <td><% out.print( utilidad.NuloToVacio(insc.getAluFchInsc())); %> </td>
                                <td><% out.print( utilidad.NuloToVacio(insc.getAluFchCert())); %> </td>
                            </tr>
                            <%
                            }
                            %>
                                </tbody>
                        </table>

                </div>
        </div>
        
        <!-- PopUp para Agregar docentes al calendario -->
                                
        <div id="PopUpAgregar" class="modal fade" role="dialog">
            <!-- Modal -->
            <div class="modal-dialog">
                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Estudio</h4>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <label class="radio-inline"><input type="radio" name="pop_TpoEst" id="pop_TpoEst" value="carrera">Carrera</label>
                            <label class="radio-inline"><input type="radio" name="pop_TpoEst" id="pop_TpoEst" value="curso">Curso</label>
                        </div>
                        
                        <div class="row">
                            <div id="pop_FltrCarrera" name="pop_FltrCarrera">
                                <select class="form-control" id="pop_FltrCarCod" name="popCarCod"></select>
                            </div>
                        </div>
                        
                        <div class="row">
                            <table id="PopUpTblEstudio" name="PopUpTblEstudio" class="table table-striped" cellspacing="0"  class="table" width="100%">
                                <thead>
                                    <tr>
                                        <th>Codigo</th>
                                        <th>Nombre</th>
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
                    
                    $('input:radio[name="pop_TpoEst"][value="carrera"]').prop("checked", true);
                    $('#pop_FltrCarrera').show();
                    
                    $('input:radio[name="pop_TpoEst"]').change(
                        function(){
                            $('#pop_FltrCarrera').show();
                            
                            if (this.checked) {
                                if(this.value == "carrera")
                                {
                                    CargarPlanes();
                                }
                                
                                if(this.value == "curso")
                                {
                                    CargarCurso();
                                    $('#pop_FltrCarrera').hide();
                                }
                            }
                    });
                        
                    
                    function CargarCurso()
                    {
                        $.post('<% out.print(urlSistema); %>ABM_Curso', {
                                         pAction: "POPUP_OBTENER"
                                     }, function (responseText) {
                                        var cursos = JSON.parse(responseText);
                                         
                                        $.each(cursos, function(f , curso) {
                                            curso.curCod = "<td> <a href='#' data-codigo='"+curso.curCod+"' data-nombre='"+curso.curNom+"' class='Pop_Seleccionar'>"+curso.curCod+" </a> </td>";
                                        });

                                         
                                        $('#PopUpTblEstudio').DataTable( {
                                            data: cursos,
                                            deferRender: true,
                                            bLengthChange : false, //thought this line could hide the LengthMenu
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
                                                { "data": "curCod" },
                                                { "data": "curNom"}
                                            ]

                                        } );

                                     });
                    }
                    
                    function CargarPlanes()
                    {
                        $.post('<% out.print(urlSistema); %>ABM_Carrera', {
                                         pAction: "POPUP_OBTENER"
                                     }, function (responseText) {
                                        var carreras = JSON.parse(responseText);
                                         
                                        $.each(carreras, function(f , carrera) {
                                            carrera.carCod = "<td> <a href='#' data-codigo='"+carrera.carCod+"' data-nombre='"+carrera.carNom+"' class='Pop_Seleccionar'>"+carrera.carCod+" </a> </td>";
                                        });

                                         
                                        $('#PopUpTblEstudio').DataTable( {
                                            data: carreras,
                                            deferRender: true,
                                            bLengthChange : false, //thought this line could hide the LengthMenu
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
                                                { "data": "carCod" },
                                                { "data": "carNom"}
                                            ]

                                        } );

                                     });
                    }
                
                    $(document).on('click', ".Pop_Seleccionar", function() {

                            var codigo = $(this).data("codigo");
                            var PerCod = $('#PerCod').val();
                            
                            var tipo    = "CARRERA";
                            
                            if($('input:radio[name="pop_TpoEst"][value="carrera"]').prop("checked"))
                            {
                                tipo = "CARRERA";
                            }
                            
                            if($('input:radio[name="pop_TpoEst"][value="curso"]').prop("checked"))
                            {
                                tipo = "CURSO";
                            }
                            
                            
                                $.post('<% out.print(urlSistema); %>ABM_Inscripcion', {
                                         pPerCod: PerCod,
                                         pCodigoEstudio: codigo,
                                         pTipoEstudio: tipo,
                                         pAction: "<% out.print(Modo.INSERT);%>"
                                     }, function (responseText) {
                                         var obj = JSON.parse(responseText);

                                         if (obj.tipoMensaje != 'ERROR')
                                         {
                                             location.reload();
                                         } else
                                         {
                                             MostrarMensaje(obj.tipoMensaje, obj.mensaje);
                                         }

                                     });

                                    $(function () {
                                            $('#PopUpAgregar').modal('toggle');
                                         });
                    });
                    
                    
                });
            </script>

        </div>
        
        <!------------------------------------------------->
        
        <!-- PopUp para Eliminar personas del calendario -->
        
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

                      <p>Eliminar la inscripción de: <label name="elim_nombre" id="elim_nombre"></label></p>
                      <p>Quiere proceder?</p>

                  </div>
                  <div class="modal-footer">
                    <button name="elim_boton_confirmar" id="elim_boton_confirmar" type="button" class="btn btn-danger" data-codigo="">Eliminar</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                  </div>
                </div>
            </div>
            <script type="text/javascript">
                $(document).ready(function() {
                    
                    $('.btn_eliminar').on('click', function(e) {
                        
                        var codigo = $(this).data("codigo");
                        var nombre = $(this).data("nombre");
                        
                        $('#elim_nombre').text(nombre);
                        $('#elim_boton_confirmar').data('codigo', codigo);
                        
                        
                      });
                      
                      $('#elim_boton_confirmar').on('click', function(e) {
                            var codigo = $('#elim_boton_confirmar').data('codigo');
                            $.post('<% out.print(urlSistema); %>ABM_Inscripcion', {
                                         pInsCod: codigo,
                                         pAction: "<% out.print(Modo.DELETE);%>"
                                     }, function (responseText) {
                                         var obj = JSON.parse(responseText);
                                         MostrarCargando(false);

                                         if (obj.tipoMensaje != 'ERROR')
                                         {
                                             location.reload();
                                         } else
                                         {
                                             MostrarMensaje(obj.tipoMensaje, obj.mensaje);
                                         }

                                     });

                             $(function () {
                                     $('#PopUpEliminar').modal('toggle');
                                  });
                     
                      });

                });
            </script>
        </div>
                                     
        <!------------------------------------------------->
                                     
    </body>
</html>
