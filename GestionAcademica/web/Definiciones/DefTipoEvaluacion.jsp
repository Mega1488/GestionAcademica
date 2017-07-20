<%-- 
    Document   : DefTipoEvaluacion
    Created on : 03-jul-2017, 18:29:13
    Author     : alvar
--%>

<%@page import="Logica.Seguridad"%>
<%@page import="Enumerado.NombreSesiones"%>
<%@page import="Enumerado.TipoMensaje"%>
<%@page import="Utiles.Retorno_MsgObj"%>
<%@page import="Moodle.MoodleCategory"%>
<%@page import="Entidad.Parametro"%>
<%@page import="Logica.LoParametro"%>
<%@page import="Logica.LoCategoria"%>
<%@page import="Entidad.TipoEvaluacion"%>
<%@page import="Logica.LoTipoEvaluacion"%>
<%@page import="Utiles.Utilidades"%>
<%@page import="Enumerado.Modo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    LoTipoEvaluacion loTipoEvaluacion        = LoTipoEvaluacion.GetInstancia();
    Utilidades utilidad    = Utilidades.GetInstancia();
   
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
    
    Modo Mode           = Modo.valueOf(request.getParameter("MODO"));
    String TpoEvlCod    = request.getParameter("pTpoEvlCod");
    String js_redirect  = "window.location.replace('" + urlSistema +  "Definiciones/DefTipoEvaluacionWW.jsp');";

    TipoEvaluacion tpoEvaluacion     = new TipoEvaluacion();
    
    if(Mode.equals(Modo.UPDATE) || Mode.equals(Modo.DISPLAY) || Mode.equals(Modo.DELETE))
    {
        Retorno_MsgObj retorno = (Retorno_MsgObj) loTipoEvaluacion.obtener(Long.valueOf(TpoEvlCod));
        if(!retorno.SurgioErrorObjetoRequerido())
        {
            tpoEvaluacion = (TipoEvaluacion) retorno.getObjeto();
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
        <title>Sistema de Gestión Académica - Tipo Evaluación</title>
        <jsp:include page="/masterPage/head.jsp"/>
        
        <script>
                $(document).ready(function() {
                    $('#btn_guardar').click(function(event) {
                                
                                
                                var	TpoEvlCod	= $('#TpoEvlCod').val();
                                var	TpoEvlNom	= $('#TpoEvlNom').val();
                                var	TpoEvlExm	= document.getElementById('TpoEvlExm').checked;
                                var	TpoEvlInsAut	= document.getElementById('TpoEvlInsAut').checked;
                                
                                if(TpoEvlNom == '')
                                    {
                                        MostrarMensaje("ERROR", "Completa los datos papa");
                                    }
                                    else
                                    {
                                        
                                        if($('#MODO').val() == "INSERT")
                                         {

                                                     // Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
                                                     $.post('<% out.print(urlSistema); %>ABM_TipoEvaluacion', {
                                                            pTpoEvlCod	:	TpoEvlCod,
                                                            pTpoEvlNom	:	TpoEvlNom,
                                                            pTpoEvlExm	:	TpoEvlExm,
                                                            pTpoEvlInsAut	:	TpoEvlInsAut,
                                                            pAction          : "INSERTAR"
                                                     }, function(responseText) {
                                                         var obj = JSON.parse(responseText);
                                                         
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
                                         

                                            if($('#MODO').val() == "UPDATE")
                                            {
                                                // Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
                                                $.post('<% out.print(urlSistema); %>ABM_TipoEvaluacion', {
                                                        pTpoEvlCod	:	TpoEvlCod,
                                                        pTpoEvlNom	:	TpoEvlNom,
                                                        pTpoEvlExm	:	TpoEvlExm,
                                                        pTpoEvlInsAut	:	TpoEvlInsAut,
                                                        pAction          : "ACTUALIZAR"
                                                }, function(responseText) {
                                                    var obj = JSON.parse(responseText);
                                                    
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

                                            if($('#MODO').val() == "DELETE")
                                            {
                                                $.post('<% out.print(urlSistema); %>ABM_TipoEvaluacion', {
                                                        pTpoEvlCod	:	TpoEvlCod,
                                                        pTpoEvlNom	:	TpoEvlNom,
                                                        pTpoEvlExm	:	TpoEvlExm,
                                                        pTpoEvlInsAut	:	TpoEvlInsAut,
                                                        pAction          : "ELIMINAR"
                                                }, function(responseText) {
                                                    var obj = JSON.parse(responseText);
                                                    
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
                        
                       <div class="contenedor-titulo">    
                            <p>Tipo de evaluación</p>
                        </div> 
                        
                        <div class=""> 
                            <div class="" style="text-align: right;"><a href="<% out.print(urlSistema); %>Definiciones/DefTipoEvaluacionWW.jsp">Regresar</a></div>
                        </div>
            
                        <div style="display:none" id="datos_ocultos" name="datos_ocultos">
                            <input type="hidden" name="MODO" id="MODO" value="<% out.print(Mode); %>">
                        </div>

                        <form id="frm_objeto" name="frm_objeto">

                            <div><label>Código</label><input type="text" class="form-control" id="TpoEvlCod" name="TpoEvlCod" placeholder="TpoEvlCod" disabled value="<% out.print( utilidad.NuloToVacio(tpoEvaluacion.getTpoEvlCod())); %>" ></div>
                            <div><label>Nombre</label><input type="text" class="form-control" id="TpoEvlNom" name="TpoEvlNom" placeholder="TpoEvlNom" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio(tpoEvaluacion.getTpoEvlNom())); %>" ></div>
                            <div class="checkbox"><label><input type="checkbox" id="TpoEvlExm" name="TpoEvlExm" placeholder="TpoEvlExm" <% out.print(CamposActivos); %> <% out.print( utilidad.BooleanToChecked(tpoEvaluacion.getTpoEvlExm())); %> > Es exámen</label></div>
                            <div class="checkbox"><label><input type="checkbox" id="TpoEvlInsAut" name="TpoEvlInsAut" placeholder="TpoEvlInsAut" <% out.print(CamposActivos); %> <% out.print( utilidad.BooleanToChecked(tpoEvaluacion.getTpoEvlInsAut())); %> > Inscripción automática</label></div>

                            <div>
                                <input name="btn_guardar" id="btn_guardar" value="Guardar" type="button" class="btn btn-success"/>
                                <input value="Cancelar" class="btn btn-default" type="button" onclick="<% out.print(js_redirect); %> "/>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
