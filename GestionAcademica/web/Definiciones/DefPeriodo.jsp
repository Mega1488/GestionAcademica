<%-- 
    Document   : DefPeriodo
    Created on : 03-jul-2017, 18:29:13
    Author     : alvar
--%>

<%@page import="Enumerado.TipoPeriodo"%>
<%@page import="Logica.Seguridad"%>
<%@page import="Enumerado.NombreSesiones"%>
<%@page import="Enumerado.TipoMensaje"%>
<%@page import="Utiles.Retorno_MsgObj"%>
<%@page import="Moodle.MoodleCategory"%>
<%@page import="Entidad.Parametro"%>
<%@page import="Logica.LoParametro"%>
<%@page import="Logica.LoCategoria"%>
<%@page import="Entidad.Periodo"%>
<%@page import="Logica.LoPeriodo"%>
<%@page import="Utiles.Utilidades"%>
<%@page import="Enumerado.Modo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    LoPeriodo loPeriodo   = LoPeriodo.GetInstancia();
    Utilidades utilidad   = Utilidades.GetInstancia();
    String urlSistema     = (String) session.getAttribute(NombreSesiones.URL_SISTEMA.getValor());
    
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
    String PeriCod       = request.getParameter("pPeriCod");
    String js_redirect  = "window.location.replace('" + urlSistema +  "Definiciones/DefPeriodoWW.jsp');";

    Periodo periodo     = new Periodo();
    
    if(Mode.equals(Modo.UPDATE) || Mode.equals(Modo.DISPLAY) || Mode.equals(Modo.DELETE))
    {
        Retorno_MsgObj retorno = (Retorno_MsgObj) loPeriodo.obtener(Long.valueOf(PeriCod));
        
        if(!retorno.SurgioErrorObjetoRequerido())
        {
            periodo = (Periodo) retorno.getObjeto();
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
        <title>Sistema de Gestión Académica - Periodo</title>
        <jsp:include page="/masterPage/head.jsp"/>
        
        <script>
                $(document).ready(function() {
                    $('#btn_guardar').click(function(event) {
                                
                                
                                var PerCod = $('#PerCod').val();
                                var PerTpo = $('select[name=PerTpo]').val();
                                var PerVal = $('#PerVal').val();
                                var PerFchIni = $('#PerFchIni').val();
                                
                                var modo = $('#MODO').val();
                                
                                if(PerVal == '')
                                    {
                                        MostrarMensaje("ERROR", "Completa los datos papa");
                                    }
                                    else
                                    {
                                        

                                        // Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
                                        $.post('<% out.print(urlSistema); %>ABM_Periodo', {
                                                pPerCod:PerCod,
                                                pPerTpo:PerTpo,
                                                pPerVal:PerVal,
                                                pPerFchIni:PerFchIni,
                                                pAction          : modo
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
                            <jsp:include page="/Definiciones/DefPeriodoTabs.jsp"/>
                        </div>
                        
                        <div class=""> 
                            <div class="" style="text-align: right;"><a href="<% out.print(urlSistema); %>Definiciones/DefPeriodoWW.jsp">Regresar</a></div>
                        </div>
        
                        <div style="display:none" id="datos_ocultos" name="datos_ocultos">
                            <input type="hidden" name="MODO" id="MODO" value="<% out.print(Mode); %>">
                        </div>
                    
                        <form id="frm_objeto" name="frm_objeto">
                        
                            <div><label>Código</label><input type="text" class="form-control" id="PerCod" name="PerCod" placeholder="PerCod" disabled value="<% out.print( utilidad.NuloToVacio(periodo.getPeriCod())); %>" ></div>

                            <div><label>Tipo</label>
                                <select class="form-control" id="PerTpo" name="PerTpo" placeholder="PerTpo">
                                    <%
                                        for(TipoPeriodo tpoPer : TipoPeriodo.values())
                                        {
                                            if(tpoPer.equals(periodo.getPerTpo()))
                                            {
                                                out.println("<option value='"+tpoPer.getTipoPeriodo()+"' selected>"+tpoPer.getTipoPeriodoNombre()+"</option>");
                                            }
                                            else
                                            {
                                                out.println("<option value='"+tpoPer.getTipoPeriodo()+"'>"+tpoPer.getTipoPeriodoNombre()+"</option>");                                            
                                            }
                                        }
                                    %>

                                </select> 
                            </div>

                            <div><label>Valor</label><input type="text" class="form-control" id="PerVal" name="PerVal" placeholder="PerVal" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio(periodo.getPerVal())); %>" ></div>
                            <div><label>Fecha de inicio</label><input type="text" class="form-control" id="PerFchIni" name="PerFchIni" placeholder="PerFchIni" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio(periodo.getPerFchIni())); %>" ></div>


                            <input name="btn_guardar" id="btn_guardar" value="Guardar" type="button"  class="btn btn-success" />
                            <input value="Cancelar" class="btn btn-default" type="button" onclick="<% out.print(js_redirect); %> "/>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
