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
    Utilidades utilidad     = Utilidades.GetInstancia();
    LoCarrera loCar         = LoCarrera.GetInstancia();
    Carrera car             = new Carrera();
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
    
    String DefCar           = "DefCarreraWW.jsp";
    String PlaEst           = "DefPlanEstudioWW.jsp";
    String CarCod           = request.getParameter("pCarCod");
    Modo mode               = Modo.valueOf(request.getParameter("MODO"));
    String js_redirect      = "window.location.replace('" + urlSistema +  "Definiciones/DefCarreraWW.jsp');";
    String CamposActivos    = "disabled";
    
    Date fecha              = new Date();
    DateFormat f            = new SimpleDateFormat("dd/MM/YYYY");
    String today            = "";
    
    if(mode.equals(Modo.UPDATE) || mode.equals(Modo.DISPLAY) || mode.equals(Modo.DELETE))
    {
        Retorno_MsgObj retorno = (Retorno_MsgObj) loCar.obtener(Long.valueOf(CarCod));
        if(retorno.getMensaje().getTipoMensaje() != TipoMensaje.ERROR)
        {
            car = (Carrera) retorno.getObjeto();
        }
        else
        {
            out.print(retorno.getMensaje().toString());
        }
    }
    
    if (mode.equals(Modo.DELETE) || mode.equals(Modo.UPDATE))
    {
        today = "Fecha de Modificación: " + f.format(car.getObjFchMod());
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
    <title>Ingreso de Carreras</title>
    <jsp:include page="/masterPage/head.jsp"/>
    
    <script>
            $(document).ready(function() {
                MostrarCargando(false);
                
                $('#BtnAceCar').click(function() {
                 MostrarCargando(true);
                    var codVar          = $('#CarCod').val();
                    var nomVar          = $('#CarNom').val();
                    var dscVar          = $('#CarDsc').val();
                    var facVar          = $('#CarFac').val();
                    var crtVar          = $('#CarCrt').val();
                    
                    if(nomVar == '' && $('#MODO').val()!= "DELETE")
                    {
                        MostrarMensaje("ERROR", "Deberá asignar un nombre a la Carrera");
                        MostrarCargando(false);
                    }
                    else
                    {
                        if ($('#MODO').val()== "INSERT")
                        {
                            // Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
                            $.post('<% out.print(urlSistema); %>ABM_Carrera', {
                                    pNom          : nomVar,
                                    pDsc          : dscVar,
                                    pfac          : facVar,
                                    pCrt          : crtVar,
                                    pAccion       : "INGRESAR"
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
                        
                        if ($('#MODO').val()== "UPDATE")
                        {
                            // Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
                            $.post('<% out.print(urlSistema); %>ABM_Carrera', {
                                    pCod        : codVar,
                                    pNom        : nomVar,
                                    pDsc        : dscVar,
                                    pfac        : facVar,
                                    pCrt        : crtVar,
                                    pAccion     : "MODIFICAR"
                            }, function(responseText) {
                                var obj = JSON.parse(responseText);
                                MostrarCargando(false);

                                if(obj.tipoMensaje != 'ERROR')
                                {
                                    <%out.print(js_redirect);%>
                                }
                                else
                                {
                                    MostrarMensaje(obj.tipoMensaje, obj.mensaje);
                                }
                            });
                        }
                        
                        if ($('#MODO').val()== "DELETE")
                        {
                            $.post('<% out.print(urlSistema); %>ABM_Carrera', {
                                    pCod	: codVar,   
                                    pAccion     : "ELIMINAR"
                            }, function(responseText) {
                                var obj = JSON.parse(responseText);
                                MostrarCargando(false);

                                if(obj.tipoMensaje != 'ERROR')
                                {
                                    <%out.print(js_redirect);%>     
                                }
                                else
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
    <div id="cabezal" name="cabezal">
        <jsp:include page="/masterPage/cabezal.jsp"/>
    </div>

    <div style="float:left; width: 10%; height: 100%;">
        <jsp:include page="/masterPage/menu_izquierdo.jsp" />
    </div>

    <div id="contenido" name="contenido" style="float: right; width: 90%;">
        <div class="col-md-8 col-md-offset-1">
            <div class="panel-heading"><h1>Ingreso de Carrera</h1><hr size="200" style="color: #000000;"/></div>
                <table border= "0" width="100%">
                    <tr>
                        <td>
                            <!-- En "ejemplo" hay que poner el en lace de la pagina Inicio en este caso -->
                            <a id="lnkIni" href=<%out.println(urlSistema);%>> Inicio </a> >
                            <a id="lnkDefCar" href="<%out.println(DefCar);%>"> Carreras </a> >
                            Ingreso de Carrera                                    
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <br>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div style="display:none" id="datos_ocultos" name="datos_ocultos">
                                <input type="hidden" name="MODO" id="MODO" value="<% out.print(mode); %>">
                            </div>
                        </td>
                        <td align="right">
                            <div id="msgFecha" name="msgFecha"> 
                                <%out.println(today);%>
                            </div>
                        </td>
                    </tr>
                </table>
                <div class="panel panel-default">
                    <div class="panel-heading"><h10>
                        <%if(mode.equals(Modo.UPDATE) || mode.equals(Modo.DISPLAY))
                        {%>
                            <a href="<%out.println(DefCar);%>"> Carreras </a>
                            /
                            <a href="<%out.println(PlaEst);%>?&pCarCod=<%out.print(CarCod.toString());%>"> Plan de Estudio </a>
                        <%}else{%>  
                            <a href="<%out.println(DefCar);%>"> Carreras </a>
                        <%}%>
                    </h10></div>
                    <div class="panel-body">
                        <table border= "0" width="100%">
                            <tr>
                                <td>
                                    <div class="form-group">
                                        <label>Código</label>
                                        <!--<imput type="text" class="form-control" id="CarCod" placeholder="Código" disabled value="<% out.print( utilidad.NuloToVacio(car.getCarCod())); %>">-->
                                        <input type="text" class="form-control" id="CarCod" placeholder="Código" disabled value="<% out.print( utilidad.NuloToVacio(car.getCarCod())); %>">
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div class="form-group">
                                      <label for="InputNombre">Nombre</label>
                                      <input type="text" class="form-control" id="CarNom" placeholder="Nombre" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio(car.getCarNom())); %>">
                                    </div>                                            
                                </td>
                                <td class="margin">
                                    <div class="form-group">
                                      <label for="InputDescripcion">Descripción</label>
                                      <input type="text" class="form-control" id="CarDsc" placeholder="Descripción" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio(car.getCarDsc())); %>">
                                    </div>                                                
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div class="form-group">
                                      <label for="InputFacultad">Facultad</label>
                                      <input type="text" class="form-control" id="CarFac" placeholder="Facultad" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio(car.getCarFac())); %>">
                                    </div>                                            
                                </td>
                                <td class="margin">
                                    <div class="form-group">
                                      <label for="InputCertificacion">Certificación</label>
                                      <input type="text" class="form-control" id="CarCrt" placeholder="Certificación" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio(car.getCarCrt())); %>">
                                    </div>                                                
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
                <table>
                    <tr>
                        <td style="text-align:right" class="margin">
                            <button type="button" id="BtnAceCar" class="btn btn-default">Aceptar</button>
                        </td>
                        <td>
                        </td>
                        <td style="text-align:right" class="margin">
                            <input type="button" class="btn btn-default" value="Volver" id="BtnVol" name="BtnVol" onclick= "self.location.href ='<% out.print(urlSistema); %>Definiciones/DefCarreraWW.jsp'" />
                        </td>
                    </tr>
                </table>
            </div>
        </div>
        <div id="div_cargando" name="div_cargando"></div>
    </body>
</html>