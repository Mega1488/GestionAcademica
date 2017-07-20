<%-- 
    Document   : DefPeriodoWW
    Created on : 18-jul-2017, 20:39:04
    Author     : alvar
--%>

<%@page import="Enumerado.TipoPeriodo"%>
<%@page import="Entidad.Periodo"%>
<%@page import="Logica.LoPeriodo"%>
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

    LoPeriodo loPeriodo     = LoPeriodo.GetInstancia();
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
    
    Retorno_MsgObj retorno = (Retorno_MsgObj) loPeriodo.obtenerLista();
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
        <title>Sistema de Gestión Académica - Periodo</title>
        <jsp:include page="/masterPage/head.jsp"/>
    </head>
    <body>
    <div class="wrapper">
          
        <jsp:include page="/masterPage/menu_izquierdo.jsp" />

        <div id="contenido" name="contenido"  class="main-panel">
            <div class="contenedor-cabezal">
            <jsp:include page="/masterPage/cabezal.jsp"/>
            </div>
            <div class="contenedor-principal">


                <div class="col-sm-11 contenedor-texto-titulo-flotante">


                    <div class="contenedor-titulo">    
                        <p>Periodos</p>
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
                                <th>Tipo</th>
                                <th>Valor</th>
                                <th>Fecha de inicio</th>
                            </tr>
                        </thead>

                        <% for(Object objeto : lstObjeto)
                        {
                         Periodo periodo = (Periodo) objeto;
                        %>
                        <tr>
                            <td><a href="<% out.print(urlSistema); %>Definiciones/DefPeriodo.jsp?MODO=<% out.print(Enumerado.Modo.DELETE); %>&pPeriCod=<% out.print(periodo.getPeriCod()); %>" name="btn_eliminar" id="btn_eliminar" title="Eliminar" class="glyphicon glyphicon-trash"/></td>
                            <td><a href="<% out.print(urlSistema); %>Definiciones/DefPeriodo.jsp?MODO=<% out.print(Enumerado.Modo.UPDATE); %>&pPeriCod=<% out.print(periodo.getPeriCod()); %>" name="btn_editar" id="btn_editar" title="Editar" class='glyphicon glyphicon-edit'/></td>
                            <td><% out.print( utilidad.NuloToVacio(periodo.getPeriCod())); %> </td>
                            <td><% out.print( utilidad.NuloToVacio(periodo.getPerTpo().getTipoPeriodoNombre())); %> </td>
                            <td><% out.print( utilidad.NuloToVacio(periodo.getPerVal())); %> </td>
                            <td><% out.print( utilidad.NuloToVacio(periodo.getPerFchIni())); %> </td>

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
                    <h4 class="modal-title">Periodo</h4>
                </div>

                <div class="modal-body">

                    <div><label>Tipo</label>
                        <select class="form-control" id="PerTpo" name="PerTpo" placeholder="PerTpo">
                            <%
                                for(TipoPeriodo tpoPer : TipoPeriodo.values())
                                {
                                    out.println("<option value='"+tpoPer.getTipoPeriodo()+"'>"+tpoPer.getTipoPeriodoNombre()+"</option>");
                                }
                            %>

                        </select> 
                    </div>
                    <div><label>Valor</label><input type="number" class="form-control" id="PerVal" name="PerVal" placeholder="PerVal" value="" ></div>
                    <div><label>Fecha de inicio</label><input type="date" class="form-control" id="PerFchIni" name="PerFchIni" placeholder="PerFchIni" value="" ></div>
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

                        var PerTpo= $('select[name=PerTpo]').val();
                        var PerVal= $('#PerVal').val();
                        var PerFchIni= $('#PerFchIni').val();

                        if(PerVal == '')
                            {
                                MostrarMensaje("ERROR", "Completa los datos papa");
                            }
                            else
                            {


                                // Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
                                $.post('<% out.print(urlSistema); %>ABM_Periodo', {
                                    pPerTpo:PerTpo,
                                    pPerVal:PerVal,
                                    pPerFchIni:PerFchIni,
                                    pAction:"INSERT"
                                }, function(responseText) {
                                    var obj = JSON.parse(responseText);

                                    if(obj.tipoMensaje != 'ERROR')
                                    {
                                        location.reload();
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
        </div>
    </body>
</html>
