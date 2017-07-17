<%-- 
    Document   : DefVersion
    Created on : 25-jun-2017, 21:43:57
    Author     : alvar
--%>

<%@page import="Entidad.Version"%>
<%@page import="Logica.LoVersion"%>
<%@page import="Logica.Seguridad"%>
<%@page import="Utiles.Retorno_MsgObj"%>
<%@page import="Enumerado.NombreSesiones"%>
<%@page import="Utiles.Utilidades"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
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
    
    Version version     = LoVersion.GetInstancia().obtener(Integer.valueOf(1));
    
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gestión Académica - Versión</title>
        <jsp:include page="/masterPage/head.jsp"/>
        
        <script>
                $(document).ready(function() {
                        
                        $('#btn_guardar').click(function(event) {
                                
                                
                    
                                var SisVerCod   = $('#SisVerCod').val();
                                var SisVer      = $('#SisVer').val();
                                var SisCrgDat   = document.getElementById('SisCrgDat').checked;
                                
                                
                                if(SisVerCod == '' || SisVer == '')
                                {
                                    MostrarMensaje("ERROR", "Completa los datos papa");
                                }
                                else
                                {
                                        // Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
                                        $.post('<% out.print(urlSistema); %>AM_Version', {
                                                pSisVerCod   : SisVerCod,
                                                pSisCrgDat   : SisCrgDat,
                                                pAction      : "ACTUALIZAR"
                                        }, function(responseText) {
                                            var obj = JSON.parse(responseText);
                                            
                                            MostrarMensaje(obj.tipoMensaje, obj.mensaje);

                                        });
                                }
                        });
                        
                    
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
                
                <div class="row"> 
                    <div class="col-lg-6"><h1>Versión</h1></div>
                </div>

                <form id="frm_Version" name="frm_Version">
                    <div>
                        <input type="hidden" id="SisVerCod" name="SisVerCod" placeholder="Código" disabled value="<% out.print( utilidad.NuloToVacio(version.getSisVerCod())); %>">
                    </div>
                    
                    <div>
                        <label>Versión:</label>
                        <input type="text" class="form-control" id="SisVer" name="SisVer" placeholder="Versión" disabled value="<% out.print( utilidad.NuloToVacio(version.getSisVer())); %>">
                    </div>

                    <div class="checkbox">
                        <label> <input type="checkbox" id="SisCrgDat" name="SisCrgDat" <% out.print( utilidad.BooleanToChecked(version.getSisCrgDat())); %>> Datos iniciales cargados</label>
                    </div>

                    <div>
                        <input name="btn_guardar" id="btn_guardar" value="Guardar" type="button" class="btn btn-success" />
                    </div>
                </form>
            </div>
            
        </div>
            
    </body>
</html>
