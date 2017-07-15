<%-- 
    Document   : DefCalendario
    Created on : 03-jul-2017, 18:29:13
    Author     : alvar
--%>

<%@page import="Enumerado.TipoMensaje"%>
<%@page import="Utiles.Retorno_MsgObj"%>
<%@page import="Moodle.MoodleCategory"%>
<%@page import="Entidad.Parametro"%>
<%@page import="Logica.LoParametro"%>
<%@page import="Logica.LoCategoria"%>
<%@page import="Entidad.Calendario"%>
<%@page import="Logica.LoCalendario"%>
<%@page import="Utiles.Utilidades"%>
<%@page import="Enumerado.Modo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    LoParametro loParam    = LoParametro.GetInstancia();
    Parametro param        = loParam.obtener(1);
    LoCalendario loCalendario        = LoCalendario.GetInstancia();
    Utilidades utilidad    = Utilidades.GetInstancia();
    String urlSistema      = utilidad.GetUrlSistema();
    
    Modo Mode           = Modo.valueOf(request.getParameter("MODO"));
    String CalCod       = request.getParameter("pCalCod");
    String js_redirect  = "window.location.replace('" + urlSistema +  "Definiciones/DefCalendarioWW.jsp');";

    Calendario calendario     = new Calendario();
    
    if(Mode.equals(Modo.UPDATE) || Mode.equals(Modo.DISPLAY) || Mode.equals(Modo.DELETE))
    {
        Retorno_MsgObj retorno = (Retorno_MsgObj) loCalendario.obtener(Long.valueOf(CalCod));
        
        if(!retorno.SurgioErrorObjetoRequerido())
        {
            calendario = (Calendario) retorno.getObjeto();
        }
        else
        {
            out.print(retorno.getMensaje().toString());
        }
    }
    
    String CamposActivos = "disabled";
    
    switch(Mode)
    {
        case INSERT: CamposActivos = "enabled";
            break;
        case DELETE: CamposActivos = "disabled";
            break;
        case DISPLAY: CamposActivos = "disabled";
            break;
        case UPDATE: CamposActivos = "enabled";
            break;
    }
    
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gestión Académica - Calendario</title>
        <jsp:include page="/masterPage/head.jsp"/>
        
        <script>
                $(document).ready(function() {
                    $('#btn_guardar').click(function(event) {
                                
                                MostrarCargando(true);
                                
                                var CalCod= $('#CalCod').val();
                                var EvlCod= $('#EvlCod').val();
                                var CalFch= $('#CalFch').val();
                                var EvlInsFchDsd= $('#EvlInsFchDsd').val();
                                var EvlInsFchHst= $('#EvlInsFchHst').val();
                                
                                var modo = $('#MODO').val();
                                
                                if(CalFch == '')
                                    {
                                        MostrarMensaje("ERROR", "Completa los datos papa");
                                        MostrarCargando(false);
                                    }
                                    else
                                    {
                                        

                                        // Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
                                        $.post('<% out.print(urlSistema); %>ABM_Calendario', {
                                               pCalCod:CalCod,
                                               pEvlCod:EvlCod,
                                               pCalFch:CalFch,
                                               pEvlInsFchDsd:EvlInsFchDsd,
                                               pEvlInsFchHst:EvlInsFchHst,
                                               pAction          : modo
                                        }, function(responseText) {
                                            var obj = JSON.parse(responseText);
                                            MostrarCargando(false);

                                            if(obj.tipoMensaje != 'ERROR')
                                            {
                                                <%
                                                    out.print(js_redirect);
                                                %>     
                                            }
                                            else
                                            {
                                                MostrarMensaje(obj.tipoMensaje, obj.mensaje);
                                            }

                                        });
                                          
                                    }
                        });
                    /*    
                    
                    $(function () {
                        $( "#PopUpEvaluacion" ).dialog({
                          autoOpen: false
                        });

                        $("#btnEvlCod").click(function() {
                          $("#PopUpEvaluacion").dialog('open');
                        });
                      });

*/
/*$("#PopUpEvaluacion").dialog({
                       autoOpen: false,
                       modal: true
                     });

                    $("#btnEvlCod").on("click", function(e) {
                        e.preventDefault();
                        $("#PopUpEvaluacion").dialog("open");
                    });

  */                  
                });
            
        </script>
        
    </head>
    <body>
        <div class="container-fluid">
            <div id="cabezal" name="cabezal" class="row">
                <jsp:include page="/masterPage/cabezal.jsp"/>
            </div>

            <div class="col-sm-2">
                <jsp:include page="/masterPage/menu_izquierdo.jsp" />
            </div>

            <div id="contenido" name="contenido" class="col-sm-8">
                
                <h1>Definición de calendario</h1>

                
                <div id="tabs" name="tabs">
                    <jsp:include page="/Definiciones/DefCalendarioTabs.jsp"/>
                </div>

                <h2>Calendario</h2>
                
                <div style="display:none" id="datos_ocultos" name="datos_ocultos">
                        <input type="hidden" name="MODO" id="MODO" value="<% out.print(Mode); %>">
                    </div>
                    <form id="frm_objeto" name="frm_objeto">
                        
                        <div class="row">
                        <div class="col-lg-1"><label>Código</label><input type="text" class="form-control" id="CalCod" name="CalCod" placeholder="CalCod" disabled value="<% out.print( utilidad.NuloToVacio(calendario.getCalCod())); %>" ></div>
                        </div>
                        
                            <label>Evaluación:</label>
                            <div class="row"> 
                                <div class="col-lg-1">
                                    <input type="text" class="form-control" id="EvlCod" name="EvlCod" placeholder="EvlCod" disabled value="<% out.print( utilidad.NuloToVacio( (calendario.getEvaluacion() == null ? "" : calendario.getEvaluacion().getEvlCod()))); %>" >
                                </div>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" id="EvlNom" name="EvlNom" placeholder="EvlNom" disabled value="<% out.print( utilidad.NuloToVacio( (calendario.getEvaluacion() == null ? "" : calendario.getEvaluacion().getEvlNom()))); %>" >
                                </div>
                                <div class="col-lg-3">
                                    <a href="#" id="btnEvlCod" name="btnEvlCod" class="glyphicon glyphicon-search" data-toggle="modal" data-target="#PopUpEvaluacion"></a>
                                </div>
                            </div>
                         
                                
                        <div class="row">
                            <div class="col-lg-2">
                                <label>Fecha:</label>
                             <input type="date" class="form-control" id="CalFch" name="CalFch" placeholder="CalFch" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio(calendario.getCalFch())); %>" >
                            </div>                            
                            <div class="col-lg-2"><label>Inscripción desde:</label><input type="date" class="form-control" id="EvlInsFchDsd" name="EvlInsFchDsd" placeholder="EvlInsFchDsd" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio(calendario.getEvlInsFchDsd())); %>" ></div>                       
                            <div class="col-lg-2"><label>Inscripción hasta:</label><input type="date" class="form-control" id="EvlInsFchHst" name="EvlInsFchHst" placeholder="EvlInsFchHst" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio(calendario.getEvlInsFchHst())); %>" ></div>
                        </div>
                            
                        
                        <input name="btn_guardar" id="btn_guardar" value="Guardar" type="button"  class="btn btn-default" />
                </form>
            </div>
        </div>
                    
        <div id="PopUpEvaluacion" title="Evaluacion" class="modal fade" role="dialog">
            <jsp:include page="/PopUps/PopUpEvaluacion.jsp"/>
        </div>
 
    </body>
</html>
