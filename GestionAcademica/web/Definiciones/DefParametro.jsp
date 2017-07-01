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
                                
                                var ParCod        = $('#ParCod').val();
                                var ParEmlCod     = $('#ParEmlCod').val();
                                var ParSisLocal   = document.getElementById('ParSisLocal').checked;
                                var ParUtlMdl     = document.getElementById('ParUtlMdl').checked;
                                var ParUrlMdl     = $('#ParUrlMdl').val();
                                var ParMdlTkn     = $('#ParMdlTkn').val();
                                var ParUrlSrvSnc  = $('#ParUrlSrvSnc').val();
                                var ParPswValExp  = $('#ParPswValExp').val();
                                var ParPswValMsg  = $('#ParPswValMsg').val();
                                var ParDiaEvlPrv  = $('#ParDiaEvlPrv').val();
                                var ParTieIna     = $('#ParTieIna').val();
                                var ParSncAct     = document.getElementById('ParSncAct').checked;
                                
                                
                                        // Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
                                        $.post('<% out.print(urlSistema); %>AM_Parametro', {
                                                pParCod         : ParCod,
                                                pParEmlCod      : ParEmlCod,
                                                pParSisLocal    : ParSisLocal,
                                                pParUtlMdl      : ParUtlMdl,
                                                pParUrlMdl      : ParUrlMdl,
                                                pParMdlTkn      : ParMdlTkn,
                                                pParUrlSrvSnc   : ParUrlSrvSnc,
                                                pParPswValExp   : ParPswValExp,
                                                pParPswValMsg   : ParPswValMsg,
                                                pParDiaEvlPrv   : ParDiaEvlPrv,
                                                pParTieIna      : ParTieIna,
                                                pParSncAct      : ParSncAct,
                                                pAction         : "ACTUALIZAR"
                                                
                                        }, function(responseText) {
                                            var obj = JSON.parse(responseText);
                                            MostrarCargando(false);
                                            
                                            MostrarMensaje(obj.tipoMensaje, obj.mensaje);

                                        });
                                
                        });
                        
                        $.post('<% out.print(urlSistema); %>AM_Parametro', {
                            pParCod : "1",        
                            pAction : "OBTENER"
                                }, function(responseText) {
                                        var obj = JSON.parse(responseText);
                                        
                                        
                                        //$('#SisVerCod').val(obj.sisVerCod);
                                        //$('#SisVer').val(obj.sisVer);
                                        //$('#SisCrgDat').prop('checked', obj.sisCrgDat);
                                        
                                        $('#ParCod').val(obj.parCod);
                                        
                                        if(obj.parametroEmail != null)
                                        {
                                            $('#ParEmlCod').val(obj.parametroEmail.ParEmlCod);
                                            $('#ParEmlNom').text(obj.parametroEmail.ParEmlNom);
                                        }
                                        
                                        $('#ParFchUltSinc').val(obj.parFchUltSinc);
                                        $('#ParSisLocal').val(obj.parSisLocal);
                                        $('#ParUtlMdl').val(obj.parUtlMdl);
                                        $('#ParUrlMdl').val(obj.parUrlMdl);
                                        $('#ParMdlTkn').val(obj.parMdlTkn);
                                        $('#ParUrlSrvSnc').val(obj.parUrlSrvSnc);
                                        $('#ParPswValExp').val(obj.parPswValExp);
                                        $('#ParPswValMsg').val(obj.parPswValMsg);
                                        $('#ParDiaEvlPrv').val(obj.parDiaEvlPrv);
                                        $('#ParTieIna').val(obj.parTieIna);
                                        $('#ParSncAct').val(obj.parSncAct);
                                        $('#ParUrlSis').val(obj.parUrlSis);
                                        
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
                <div style="width: 40%">
                    <h1>Parametros</h1>
                    <form id="frm_Version" name="frm_Version">
                        <div>
                            <label>Código:</label>
                            <input type="num" class="form-control" id="ParCod" name="ParCod" placeholder="Código" disabled>
                        </div>

                        <div>
                            <label>URL Sistema:</label>
                            <input type="text" class="form-control" id="ParUrlSis" name="ParUrlSis" placeholder="URL" disabled>
                        </div>

                        <div>
                            <label>Parámetro de email:</label>
                            <input type="num" class="form-control" id="ParEmlCod" name="ParEmlCod">
                            <label name="ParEmlNom" id="ParEmlNom"></label>
                        </div>

                        <!--------------------------------------------------------------------------------------------------------------->

                        <div>
                            <label>Sincronizar con Moodle: </label>
                            <input type="checkbox" id="ParUtlMdl" name="ParUtlMdl">
                        </div>

                        <div>
                            <label>URL de Moodle:</label>
                            <input type="text" class="form-control" id="ParUrlMdl" name="ParUrlMdl" placeholder="URL">
                        </div>

                        <div>
                            <label>Token de Moodle:</label>
                            <input type="text" class="form-control" id="ParMdlTkn" name="ParMdlTkn" placeholder="Token">
                        </div>

                        <!--------------------------------------------------------------------------------------------------------------->

                        <div>
                            <label>Sincronización activa: </label>
                            <input type="checkbox" id="ParSncAct" name="ParSncAct">
                            <label>Última sincronización</label>
                            <input type="datetime" id="ParFchUltSinc" name="ParFchUltSinc" disabled>
                        </div>

                        <div>
                            <label>Sistema local: </label>
                            <input type="checkbox" id="ParSisLocal" name="ParSisLocal">
                        </div>

                        <div>
                            <label>URL Sistema Online:</label>
                            <input type="text" class="form-control" id="ParUrlSrvSnc" name="ParUrlSrvSnc" placeholder="URL">
                        </div>

                        <!--------------------------------------------------------------------------------------------------------------->

                        <div>
                            <label>Expresión regular para validar contraseña:</label>
                            <input type="text" class="form-control" id="ParPswValExp" name="ParPswValExp" placeholder="Expresión regular">
                        </div>

                        <div>
                            <label>Mensaje en caso de contraseña incorrecta:</label>
                            <input type="text" class="form-control" id="ParPswValMsg" name="ParPswValMsg" placeholder="Mensaje">
                        </div>

                        <!--------------------------------------------------------------------------------------------------------------->

                        <div>
                            <label>Dias de anticipación para notificar fecha proxima de evaluacion:</label>
                            <input type="num" class="form-control" id="ParDiaEvlPrv" name="ParDiaEvlPrv" placeholder="Días">
                        </div>

                        <div>
                            <label>Tiempo de inactividad en meses, para notificacion de materias previas (Not. Motivacional):</label>
                            <input type="num" class="form-control" id="ParTieIna" name="ParTieIna" placeholder="Meses">
                        </div>


                        <!---------------------------------------------------------------------------------------------------------------
                        Fin del formulario
                        --------------------------------------------------------------------------------------------------------------->

                        <div>
                            <input name="btn_guardar" id="btn_guardar" value="Guardar" type="button" />
                        </div>
                    </form>
                </div>
            </div>            
    </body>
</html>
