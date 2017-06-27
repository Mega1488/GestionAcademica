<%-- 
    Document   : DefVersion
    Created on : 25-jun-2017, 21:43:57
    Author     : alvar
--%>

<%@page import="Utiles.Utilidades"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Utilidades utilidad = Utilidades.GetInstancia();
    String urlSistema   = utilidad.GetUrlSistema();
    
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gestión Académica - Versión</title>
        <jsp:include page="/masterPage/head.jsp"/>
        
        <script>
                $(document).ready(function() {
                        MostrarCargando(false);
                        
                        $('#btn_guardar').click(function(event) {
                                
                                MostrarCargando(true);
                                
                    
                                var SisVerCod   = $('#SisVerCod').val();
                                var SisVer      = $('#SisVer').val();
                                var SisCrgDat   = document.getElementById('SisCrgDat').checked;
                                
                                
                                if(SisVerCod == '' || SisVer == '')
                                {
                                    MostrarMensaje("ERROR", "Completa los datos papa");
                                    MostrarCargando(false);
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
                                            MostrarCargando(false);
                                            
                                            MostrarMensaje(obj.tipoMensaje, obj.mensaje);

                                        });
                                }
                        });
                        
                        $.post('<% out.print(urlSistema); %>AM_Version', {
                            pSisVerCod : "1",        
                            pAction : "OBTENER"
                                }, function(responseText) {
                                        var obj = JSON.parse(responseText);
                                        
                                        $('#SisVerCod').val(obj.sisVerCod);
                                        $('#SisVer').val(obj.sisVer);
                                        $('#SisCrgDat').prop('checked', obj.sisCrgDat);
                                        
                                });
                                
                    
                    
                });
        </script>
        
    </head>
    <body>
        <div>
            <div id="cabezal" name="cabezal">
                <jsp:include page="/masterPage/cabezal.jsp"/>
            </div>

            <div style="float:left; width: 10%; height: 100%;">
                <jsp:include page="/masterPage/menu_izquierdo.jsp" />
            </div>

            <div id="contenido" name="contenido" style="float: right; width: 90%;">
                <h1>Versión</h1>
                <form id="frm_Version" name="frm_Version">
                    <div>
                        <label>Código:</label>
                        <input type="num" class="form-control" id="SisVerCod" name="SisVerCod" placeholder="Código" disabled>
                    </div>
                    
                    <div>
                        <label>Versión:</label>
                        <input type="text" class="form-control" id="SisVer" name="SisVer" placeholder="Versión" disabled>
                    </div>

                    <div>
                        <label>Datos iniciales cargados:</label>
                        <input type="checkbox" id="SisCrgDat" name="SisCrgDat" >
                    </div>

                    <div>
                        <input name="btn_guardar" id="btn_guardar" value="Guardar" type="button" />
                    </div>
                </form>
            </div>
            
            <div id="div_cargando" name="div_cargando"></div>
            
    </body>
</html>
