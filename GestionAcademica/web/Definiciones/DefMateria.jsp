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
    String js_redirect      = "window.location.replace('" + urlSistema +  "Definiciones/DefMateriaSWW.jsp?MODO="+ mode.DISPLAY +"&pPlaEstCod="+PlaEstCod+"&pCarCod="+ CarCod +"');";
    String CamposActivos    = "disabled";
    String boton            = "";
    
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
            boton           = "<input name='BtnAceMat' id='BtnAceMat' value='Guardar' type='button' class='btn btn-success' />";
            break;
        case DELETE:
            CamposActivos = "disabled";
            boton           = "<input href='#' value='Eliminar' class='btn btn-danger' type='button' data-toggle='modal' data-target='#PopUpElimMateria'/>";
            break;
        case DISPLAY:
            CamposActivos = "disabled";
            boton           = "<input name='BtnAceMat' id='BtnAceMat' value='Guardar' type='button' class='btn btn-success' />";
            break;
        case UPDATE:
            CamposActivos = "enabled";
            boton           = "<input name='BtnAceMat' id='BtnAceMat' value='Guardar' type='button' class='btn btn-success' />";
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
                            <div class="" style="text-align: right;"><a href="<% out.print(urlSistema); %>Definiciones/DefMateriaSWW.jsp?MODO=<%out.print(Enumerado.Modo.DISPLAY);%>&pPlaEstCod=<%out.print(PlaEstCod.toString());%>&pCarCod=<%out.print(CarCod.toString());%>">Regresar</a></div>
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
                                <%out.print(boton);%>
                                <input value="Cancelar" class="btn btn-default" type="button" onclick="self.location.href='<%out.print(urlSistema); %>Definiciones/DefMateriaSWW.jsp?MODO=<%out.print(Enumerado.Modo.DISPLAY);%>&pPlaEstCod=<%out.print(PlaEstCod.toString());%>&pCarCod=<%out.print(CarCod.toString());%>'"/>
                            </div>                
                        </form>
                    </div>                                            
                </div>
            </div>
        </div>
        
        <!--Popup Confirmar Eliminación-->
        <div id="PopUpElimMateria" class="modal fade" role="dialog">
        <!-- Modal -->
            <div class="modal-dialog modal-lg">
                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Eliminar Materia</h4>
                    </div>
                    <div class="modal-body">
                        <div>
                            <h4>¿Seguro que quiere eliminar la Materia: <% out.print( utilidad.NuloToVacio(mat.getMatNom())); %>?</h4>
                            <h4>Se eliminará tambien las Evaluaciones que contenga</h4>
                        </div>
                    </div>
                    <div class="modal-footer">
                      <input name="BtnAceMat" id="BtnAceMat" value="Confirmar" type="button" class="btn btn-success"/>
                      <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>