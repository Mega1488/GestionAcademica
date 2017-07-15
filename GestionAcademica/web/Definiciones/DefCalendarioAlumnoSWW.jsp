<%-- 
    Document   : DefCalendarioWW
    Created on : 03-jul-2017, 18:28:52
    Author     : alvar
--%>
<%@page import="Entidad.CalendarioAlumno"%>
<%@page import="Enumerado.Modo"%>
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
    String urlSistema   = utilidad.GetUrlSistema();
    
    Modo Mode           = Modo.valueOf(request.getParameter("MODO"));
    String CalCod       = request.getParameter("pCalCod");
    
    List<CalendarioAlumno> lstObjeto = new ArrayList<>();
    
    Retorno_MsgObj retorno = (Retorno_MsgObj) loCalendario.obtener(Long.valueOf(CalCod));
    if(!retorno.SurgioErrorObjetoRequerido())
    {
        lstObjeto = ((Calendario) retorno.getObjeto()).getLstAlumnos();
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
        <title>Sistema de Gestión Académica - Calendario | Alumnos</title>
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
                    <h1>Calendario</h1>

                    <div id="tabs" name="tabs">
                        <jsp:include page="/Definiciones/DefCalendarioTabs.jsp"/>
                    </div>

                    <div style="text-align: right; padding-top: 6px; padding-bottom: 6px;">
                        <a href="#" title="Ingresar" class="glyphicon glyphicon-plus" data-toggle="modal" data-target="#PopUpPersona"> </a>
                        <input type="hidden" name="CalCod" id="CalCod" value="<% out.print(CalCod); %>">
                    </div>


                        <table style=' <% out.print(tblVisible); %>' class='table table-hover'>
                            <tr>
                                <th></th>
                                <th>Código</th>
                                <th>Alumno</th>
                                <th>Calificación</th>
                                <th>Calificado por</th>
                                <th>Fecha</th>
                                <th>Estado</th>
                                <th>Validado por</th>
                                <th>Fecha</th>
                            </tr>

                            <% for(CalendarioAlumno calAlumno : lstObjeto)
                            {
                         
                            %>
                            <tr>
                                <td><a href="#" data-codigo="<% out.print(calAlumno.getCalAlCod()); %>" data-nombre="<% out.print(calAlumno.getAlumno().getNombreCompleto()); %>" data-toggle="modal" data-target="#PopUpEliminarAlumno" name="btn_eliminar" id="btn_eliminar" title="Eliminar" class="glyphicon glyphicon-trash btn_eliminar"/></td>
                                <td><% out.print( utilidad.NuloToVacio(calAlumno.getCalAlCod())); %> </td>
                                <td><% out.print( utilidad.NuloToVacio((calAlumno.getAlumno() != null ? calAlumno.getAlumno().getNombreCompleto() : "" ))); %> </td>
                                <td><% out.print( utilidad.NuloToVacio(calAlumno.getEvlCalVal())); %> </td>
                                <td><% out.print( utilidad.NuloToVacio((calAlumno.getEvlCalPor() != null ? calAlumno.getEvlCalPor().getNombreCompleto() : "" ))); %> </td>
                                <td><% out.print( utilidad.NuloToVacio(calAlumno.getEvlCalFch())); %> </td>
                                <td><% out.print( utilidad.NuloToVacio(calAlumno.getEvlCalEst().getEstadoNombre())); %> </td>
                                <td><% out.print( utilidad.NuloToVacio((calAlumno.getEvlValPor() != null ? calAlumno.getEvlValPor().getNombreCompleto() : "" ))); %> </td>
                                <td><% out.print( utilidad.NuloToVacio(calAlumno.getEvlValFch())); %> </td>

                            </tr>
                            <%
                            }
                            %>
                        </table>

                </div>
        </div>
                        
        <div id="PopUpPersona" title="Persona" class="modal fade" role="dialog">
            <jsp:include page="/PopUps/PopUpPersonaCalendario.jsp"/>
        </div>
        
        <div id="PopUpEliminarAlumno" title="Eliminar" class="modal fade" role="dialog">
           
            <!-- Modal -->
            <div class="modal-dialog">
                <!-- Modal content-->
                <div class="modal-content">
                  <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Eliminar</h4>
                  </div>
                  <div class="modal-body">

                      <p>Eliminar alumno: <label name="elim_nombre" id="elim_nombre"></label></p>
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
                            var CalCod = $('#CalCod').val();
                            $.post('<% out.print(urlSistema); %>ABM_CalendarioAlumno', {
                                         pCalCod: CalCod,
                                         pCalAlCod: codigo,
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
                                     $('#PopUpEliminarAlumno').modal('toggle');
                                  });
                     
                      });

                });
            </script>
        </div>
    </body>
</html>
