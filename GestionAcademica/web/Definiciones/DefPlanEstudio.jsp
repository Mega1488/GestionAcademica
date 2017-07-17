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
    
    String DefPlaEst        = "DefPlanEstudioWW.jsp";
    String DefMat           = "DefMateriaWW.jsp";
    String PlaEstCod        = request.getParameter("pPlaEstCod");
    String CarCod           = request.getParameter("pCarCod");
    Modo mode               = Modo.valueOf(request.getParameter("MODO"));
    String js_redirect      = "window.location.replace('" + urlSistema +  "Definiciones/DefPlanEstudioWW.jsp?MODO=" + Enumerado.Modo.DISPLAY + "&pCarCod="+ CarCod +"');";
    String CamposActivos    = "disabled";
    
    Date fecha              = new Date();
    DateFormat f            = new SimpleDateFormat("dd/MM/YYYY");
    String today            = "";
    
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
            System.err.println("ELSE:");
            out.print(retorno.getMensaje().toString());
        }
    }
    
    if(mode.equals(Modo.UPDATE) || mode.equals(Modo.DISPLAY) || mode.equals(Modo.DELETE))
    {
        plan = car.getPlanEstudioById(Long.valueOf(PlaEstCod));
    }
    
    if (mode.equals(Modo.DELETE) || mode.equals(Modo.UPDATE))
    {
        today = "Fecha de Modificación: " + f.format(plan.getObjFchMod());
    }
    else
    {
        today = "Fecha de Modificación: " + f.format(fecha);
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
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
        <title>Ingreso de Plan de Estudio</title>
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
        <div id="cabezal" name="cabezal">
            <jsp:include page="/masterPage/cabezal.jsp"/>
        </div>

        <div style="float:left; width: 10%; height: 100%;">
            <jsp:include page="/masterPage/menu_izquierdo.jsp" />
        </div>

        <div id="contenido" name="contenido" style="float: right; width: 90%;">
            <div class="col-md-8 col-md-offset-1">
                <div class="panel-heading"><h1>Ingreso Plan de Estudio</h1><hr size="200" style="color: #000000;"/></div>   
                    <table border= "0" width="100%">
                        <tr>
                            <td>
                                    <a href="<% out.print(urlSistema); %>"> Inicio </a> >
                                    <a href="<% out.print(DefPlaEst); %>?MODE=<%out.print(Enumerado.Modo.DISPLAY);%>&pCarCod=<%out.print(CarCod.toString());%>"> Plan de Estudio </a> >
                                    Ingreso Plan de Estudio
                            </td>
                        </tr>
                        <tr>
                            <div style="display:none" id="datos_ocultos" name="datos_ocultos">
                                <input type="hidden" name="MODO" id="MODO" value="<% out.print(mode); %>">
                                <input type="hidden" name="CarCod" id="CarCod" value="<% out.print(CarCod); %>">
                            </div>
                            <td align="right">
                                <div id="msgFecha" name="msgFecha">
                                    <% out.println(today); %>
                                </div>
                            </td>
                        </tr>
                    </table>
                    <div class="panel panel-default">
                        <div class="panel-heading"><h10>
                            <%
                                if(mode.equals(Modo.UPDATE) ||  mode.equals(Modo.DISPLAY))
                                { 
                            %>
                                <a href="<% out.print(DefPlaEst); %>?MODE=<%out.print(Enumerado.Modo.DISPLAY);%>&pCarCod=<%out.print(CarCod.toString());%>"> Plan de Estudio </a>
                                /
                                <a id="lnkDefCar" href="<% out.println(DefMat); %>"> Materias </a>
                            <%
                                } else {
                            %>
                                <a href="<% out.println(DefPlaEst); %>?MODO=<%out.print(Enumerado.Modo.DISPLAY);%>&pCarCod=<%out.print(CarCod.toString());%>"> Plan de Estudio </a>
                            <%
                                } 
                            %>
                        </h10></div>
                        <div class="panel-body">
                            <table border= "0" width="100%">
                                <tr>
                                    <td>
                                        <div class="form-group">
                                            <label for="ImputCodigo">Código</label>
                                            <input type="text" class="form-control" id="PlaEstCod" placeholder="Código" disabled value="<% out.print( utilidad.NuloToCero(plan.getPlaEstCod())); %>">
                                        </div>
                                    </td>
                                    <td>
                                        <div class="margin">
                                            <label for="InputNombre">Nombre</label>
                                            <input type="text" class="form-control" id="PlaEstNom" placeholder="Nombre" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio(plan.getPlaEstNom())); %>">
                                        </div>                                            
                                    </td>
                                </tr>
                                <tr>
                                    <td class="form-group">
                                        <div class="form-group">
                                            <label for="InputDescripcion">Descripción</label>
                                            <input type="text" class="form-control" id="PlaEstDsc" placeholder="Descripción" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio(plan.getPlaEstDsc())); %>">
                                        </div>                                                
                                    </td>
                                    <td>
                                        <div class="margin">
                                            <label for="InputFacultad">Creditos Necesarios</label>
                                            <input type="text" class="form-control" id="PlaEstCre" placeholder="Creditos Necesarios" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToCero(plan.getPlaEstCreNec())); %>">
                                        </div>                                            
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                    <table>
                        <tr>
                            <td style="text-align:right" class="margin">
                                <button type="button" id="BtnAcePla" class="btn btn-default">Aceptar</button>
                            </td>
                            <td style="text-align:right" class="margin">
                                <input type="button" class="btn btn-default" value="Volver" id="BtnVolPla" name="BtnVolPla" onclick= "self.location.href='<%out.print(urlSistema); %>Definiciones/DefPlanEstudioWW.jsp?MODO=<% out.print(Enumerado.Modo.DISPLAY); %>&pCarCod=<%out.print(CarCod.toString());%>'"/>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        <div id="div_cargando" name="div_cargando"></div>
    </body>
</html>