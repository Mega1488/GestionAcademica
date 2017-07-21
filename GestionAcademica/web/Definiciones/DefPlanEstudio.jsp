<%-- 
    Document   : DefPlanEstudio
    Created on : jul 7, 2017, 8:10:58 p.m.
    Author     : aa
--%>

<%@page import="Entidad.Carrera"%>
<%@page import="Logica.Seguridad"%>
<%@page import="Enumerado.NombreSesiones"%>
<%@page import="Enumerado.TipoMensaje"%>
<%@page import="Utiles.Retorno_MsgObj"%>
<%@page import="Logica.LoCarrera"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="Enumerado.Modo"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="Entidad.PlanEstudio"%>
<%@page import="Utiles.Utilidades"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%    
    Utilidades utilidad     = Utilidades.GetInstancia();
    PlanEstudio plan        = new PlanEstudio();
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
    Modo mode               = Modo.valueOf(request.getParameter("MODO"));
    String js_redirect      = "window.location.replace('" + urlSistema +  "Definiciones/DefPlanEstudioSWW.jsp?MODO=" + Enumerado.Modo.DISPLAY + "&pCarCod="+ CarCod +"');";
    String CamposActivos    = "disabled";
    String boton            = "";
    
    Carrera car = new Carrera();
    if(mode.equals(Modo.UPDATE) || mode.equals(Modo.DISPLAY) || mode.equals(Modo.DELETE))
    {
        Retorno_MsgObj retorno = (Retorno_MsgObj) loCar.obtener(Long.valueOf(CarCod));
        if(!retorno.SurgioErrorObjetoRequerido())
        {
            car = (Carrera) retorno.getObjeto();
        }
        else
        {
            out.print(retorno.getMensaje().toString());
        }
    }
    
    if(mode.equals(Modo.UPDATE) || mode.equals(Modo.DISPLAY) || mode.equals(Modo.DELETE))
    {
        plan = car.getPlanEstudioById(Long.valueOf(PlaEstCod));
    }
    
    switch(mode)
    {
        case INSERT:
            CamposActivos = "enabled";
            boton           = "<input name='BtnAcePla' id='BtnAcePla' value='Guardar' type='button' class='btn btn-success' />";
            break;
        case DELETE:
            CamposActivos = "disabled";
            boton           = "<input href='#' value='Eliminar' class='btn btn-danger' type='button' data-toggle='modal' data-target='#PopUpElimPlan'/>";
            break;
        case DISPLAY:
            CamposActivos = "disabled";
            boton           = "<input name='BtnAcePla' id='BtnAcePla' value='Guardar' type='button' class='btn btn-success' />";
            break;
        case UPDATE:
            CamposActivos = "enabled";
            boton           = "<input name='BtnAcePla' id='BtnAcePla' value='Guardar' type='button' class='btn btn-success' />";
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
                MostrarCargando(false);
                
                $('#BtnAcePla').click(function()
                {
                 MostrarCargando(true);
                    var PlaEstCod          = $('#PlaEstCod').val();
                    var PlaEstNom          = $('#PlaEstNom').val();
                    var PlaEstDsc          = $('#PlaEstDsc').val();
                    var PlaEstCre          = $('#PlaEstCre').val();
                    var CarCod             = $('#CarCod').val();
                    var accion             = $('#MODO').val();
                    
                    if(PlaEstNom == '' && $('#MODO').val()!= "DELETE")
                    {
                        MostrarMensaje("ERROR", "Deberá asignar un nombre al Plan de Estudio");
                        MostrarCargando(false);
                    }
                    else
                    {
                        // Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
                        $.post('<% out.print(urlSistema); %>ABM_PlanEstudio', {
                                pPlaEstCod          : PlaEstCod,
                                pPlaEstNom          : PlaEstNom,
                                pPlaEstDsc          : PlaEstDsc,
                                pPlaEstCreNec       : PlaEstCre,
                                pCarCod             : CarCod,
                                pAccion             : accion
                        }, function(responseText) {
                            var obj = JSON.parse(responseText);
                            MostrarCargando(false);
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
                            <jsp:include page="/Definiciones/DefPlanEstudioTabs.jsp"/>
                        </div>
                        
                        <div class=""> 
                            <div class="" style="text-align: right;"><a href="<% out.print(urlSistema); %>Definiciones/DefPlanEstudioSWW.jsp?MODO=<%out.print(Enumerado.Modo.DISPLAY);%>&pCarCod=<%out.print(CarCod);%>">Regresar</a></div>
                        </div>
                        
                        <div style="display:none" id="datos_ocultos" name="datos_ocultos">
                            <input type="hidden" name="MODO" id="MODO" value="<% out.print(mode); %>">
                            <input type="hidden" name="CarCod" id="CarCod" value="<% out.print(CarCod); %>">
                        </div>
                        
                        <form id="frm_objeto" name="frm_objeto">

                                <div><label>Código</label><input type="text" class="form-control" id="PlaEstCod" placeholder="Código" disabled value="<% out.print( utilidad.NuloToVacio(plan.getPlaEstCod())); %>"></div>
                                <div><label>Nombre</label><input type="text" class="form-control" id="PlaEstNom" placeholder="Nombre" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio(plan.getPlaEstNom())); %>"></div>
                                <div><label>Descripción</label><input type="text" class="form-control" id="PlaEstDsc" placeholder="Descripción" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio(plan.getPlaEstDsc())); %>"></div>
                                <div><label>Creditos Necesarios</label><input type="text" class="form-control" id="PlaEstCre" placeholder="Creditos Necesarios" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToCero(plan.getPlaEstCreNec())); %>"></div>                                    

                                <br>
                                <div>
                                    <%out.print(boton);%>
                                    <input value="Cancelar" class="btn btn-default" type="button" onclick="self.location.href='<%out.print(urlSistema); %>Definiciones/DefPlanEstudioSWW.jsp?MODO=<% out.print(Enumerado.Modo.DISPLAY); %>&pCarCod=<%out.print(CarCod.toString());%>'"/>
                                </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
                                
        <!--Popup Confirmar Eliminación-->
        <div id="PopUpElimPlan" class="modal fade" role="dialog">
        <!-- Modal -->
            <div class="modal-dialog modal-lg">
                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Eliminar Plan</h4>
                    </div>
                    <div class="modal-body">
                        <div>
                            <h4>¿Seguro que quiere eliminar el Plan: <% out.print( utilidad.NuloToVacio(plan.getPlaEstNom())); %>?</h4>
                            <h4>Se eliminará tambien las Materias y Evaluaciones que contenga</h4>
                        </div>
                    </div>
                    <div class="modal-footer">
                      <input name="BtnAcePla" id="BtnAcePla" value="Confirmar" type="button" class="btn btn-success" />
                      <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                    </div>
                </div>
            </div>
        </div>          
    </body>
</html>