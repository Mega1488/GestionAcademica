<%-- 
    Document   : DefCarrera
    Created on : jun 21, 2017, 7:51:06 p.m.
    Author     : aa
--%>

<%@page import="Enumerado.Modo"%>
<%@page import="Utiles.Utilidades"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<%    
    Utilidades utilidad = Utilidades.GetInstancia();
    String urlSistema   = utilidad.GetUrlSistema();
   
    String DefCar       = "DefCarreraWW.jsp";
    Modo mode           = Modo.valueOf(request.getParameter("MODO"));
    String js_redirect  = "window.location.replace('" + urlSistema +  "Definiciones/DefCarreraWW.jsp');";
    String CamposActivos = "disabled";
    
    Date fecha          = new Date();
    DateFormat f        = new SimpleDateFormat("dd/mm/yyyy");
    String today        = "";
    
    if (mode.equals(Modo.DELETE) || mode.equals(Modo.UPDATE))
    {
        today = "Fecha de Modificación: " + "XX/XX/XXXX";
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
                    
                    if(nomVar == '')
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
                                    pNom          : nomVar,
                                    pDsc          : dscVar,
                                    pfac          : facVar,
                                    pCrt          : crtVar,
                                    pAccion       : "MODIFICAR"
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
                            $.post('<% out.print(urlSistema); %>ABM_Persona', {
                                    pCod	: codVar,   
                                    pAction     : "ELIMINAR"
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
    <!--<div class="row">-->
        <div class="col-md-6 col-md-offset-3">
            <div class="panel panel-default">
                <div class="panel-heading"><h1>Ingreso de Carrera</h1></div>
                    <div class="panel-body">
                    <form>  
                        <table border= "0" width="100%">
                            <tr>
                                <td>
                                    <!-- En "ejemplo" hay que poner el en lace de la pagina Inicio en este caso -->
                                    <a id="lnkIni" href="ejemplo"> Inicio </a> >
                                    <a id="lnkDefCar" href="<%out.println(DefCar);%>"> Definición de Carrera </a> >
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
                            <div class="panel-heading"><h10>Datos de la Carrera</h10></div>
                                <div class="panel-body">
                                    <table border= "0" width="100%">
                                        <tr>
                                            <td>
                                                <div class="form-group">
                                                <label>Código</label>
                                                <imput type="text" class="form-control" id="CarCod" placeholder="Código" disabled/>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <div class="form-group">
                                                  <label for="InputNombre">Nombre</label>
                                                  <input type="text" class="form-control" id="CarNom" placeholder="Nombre" <% out.print(CamposActivos); %> >
                                                </div>                                            
                                            </td>
                                            <td class="margin">
                                                <div class="form-group">
                                                  <label for="InputDescripcion">Descripción</label>
                                                  <input type="text" class="form-control" id="CarDsc" placeholder="Descripción" <% out.print(CamposActivos); %> >
                                                </div>                                                
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <div class="form-group">
                                                  <label for="InputFacultad">Facultad</label>
                                                  <input type="text" class="form-control" id="CarFac" placeholder="Facultad" <% out.print(CamposActivos); %> >
                                                </div>                                            
                                            </td>
                                            <td class="margin">
                                                <div class="form-group">
                                                  <label for="InputCertificacion">Certificación</label>
                                                  <input type="text" class="form-control" id="CarCrt" placeholder="Certificación" <% out.print(CamposActivos); %> >
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
                  </form>
                </div>
            </div>
        </div>
    <!--</div>-->
    </div>
    <div id="div_cargando" name="div_cargando"></div>
    </body>
</html>