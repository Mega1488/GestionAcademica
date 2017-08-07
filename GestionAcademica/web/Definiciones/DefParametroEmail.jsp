<%-- 
    Document   : DefVersion
    Created on : 25-jun-2017, 21:43:57
    Author     : alvar
--%>

<%@page import="Utiles.Retorno_MsgObj"%>
<%@page import="Enumerado.TipoSSL"%>
<%@page import="Enumerado.TipoAutenticacion"%>
<%@page import="Enumerado.ProtocoloEmail"%>
<%@page import="Entidad.ParametroEmail"%>
<%@page import="Enumerado.Modo"%>
<%@page import="Logica.LoParametroEmail"%>
<%@page import="Utiles.Utilidades"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    LoParametroEmail lPrmEml    = LoParametroEmail.GetInstancia();
    Utilidades utilidad    = Utilidades.GetInstancia();
    String urlSistema           = (String) session.getAttribute(Enumerado.NombreSesiones.URL_SISTEMA.getValor());
    
    //----------------------------------------------------------------------------------------------------
    //CONTROL DE ACCESO
    //----------------------------------------------------------------------------------------------------
    
    String  usuario = (String) session.getAttribute(Enumerado.NombreSesiones.USUARIO.getValor());
    Boolean esAdm   = (Boolean) session.getAttribute(Enumerado.NombreSesiones.USUARIO_ADM.getValor());
    Boolean esAlu   = (Boolean) session.getAttribute(Enumerado.NombreSesiones.USUARIO_ALU.getValor());
    Boolean esDoc   = (Boolean) session.getAttribute(Enumerado.NombreSesiones.USUARIO_DOC.getValor());
    Retorno_MsgObj acceso = Logica.Seguridad.GetInstancia().ControlarAcceso(usuario, esAdm, esDoc, esAlu, utilidad.GetPaginaActual(request));
    
    if(acceso.SurgioError()) response.sendRedirect((String) acceso.getObjeto());
            
    //----------------------------------------------------------------------------------------------------
    
    Modo Mode           = Modo.valueOf(request.getParameter("MODO"));
    String ParEmlCod    = request.getParameter("pParEmlCod");
    String js_redirect  = "window.location.replace('" + urlSistema +  "Definiciones/DefParametroEmailWW.jsp');";

    ParametroEmail paramEml = new ParametroEmail();
    
    if(Mode.equals(Modo.UPDATE) || Mode.equals(Modo.DISPLAY) || Mode.equals(Modo.DELETE))
    {
        paramEml = lPrmEml.obtener(Long.valueOf(ParEmlCod));
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
        <title>Sistema de Gestión Académica - Versión</title>
        <jsp:include page="/masterPage/head.jsp"/>
        
        <script>
                $(document).ready(function() {
                    
                    $('#btn_guardar').click(function(event) {
                                
                                
                                var ParEmlCod       = $('#ParEmlCod').val();
                                var ParEmlNom       = $('#ParEmlNom').val();
                                var ParEmlPro       = $('select[name=ParEmlPro]').val();
                                var ParEmlSrv       = $('#ParEmlSrv').val();
                                var ParEmlPrt       = $('#ParEmlPrt').val();
                                var ParEmlDeNom     = $('#ParEmlDeNom').val();
                                var ParEmlDeEml     = $('#ParEmlDeEml').val();
                                var ParEmlUtlAut    = document.getElementById('ParEmlUtlAut').checked;
                                var ParEmlTpoAut    = $('select[name=ParEmlTpoAut]').val();
                                var ParEmlDom       = $('#ParEmlDom').val();
                                var ParEmlUsr       = $('#ParEmlUsr').val();
                                var ParEmlPsw       = $('#ParEmlPsw').val();
                                var ParEmlSSL       = $('select[name=ParEmlSSL]').val(); 
                                var ParEmlTmpEsp    = $('#ParEmlTmpEsp').val();
                                var ParEmlDebug    = document.getElementById('ParEmlDebug').checked;
                                var ParEmlReqConf    = document.getElementById('ParEmlReqConf').checked;
                                
                                if(ParEmlNom == '')
                                    {
                                        MostrarMensaje("ERROR", "Completa los datos papa");
                                    }
                                    else
                                    {
                                        
                                        if($('#MODO').val() == "INSERT")
                                         {

                                                     // Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
                                                     $.post('<% out.print(urlSistema); %>ABM_ParametroEmail', {
                                                             pParEmlNom       : ParEmlNom,   
                                                             pParEmlPro       : ParEmlPro,   
                                                             pParEmlSrv       : ParEmlSrv,   
                                                             pParEmlPrt       : ParEmlPrt,   
                                                             pParEmlDeNom     : ParEmlDeNom,   
                                                             pParEmlDeEml     : ParEmlDeEml,   
                                                             pParEmlUtlAut    : ParEmlUtlAut,   
                                                             pParEmlTpoAut    : ParEmlTpoAut,   
                                                             pParEmlDom       : ParEmlDom,   
                                                             pParEmlUsr       : ParEmlUsr,   
                                                             pParEmlPsw       : ParEmlPsw,   
                                                             pParEmlSSL       : ParEmlSSL,   
                                                             pParEmlTmpEsp    : ParEmlTmpEsp,   
                                                             pParEmlDebug     : ParEmlDebug,
                                                             pParEmlReqConf   : ParEmlReqConf,
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
                                                $.post('<% out.print(urlSistema); %>ABM_ParametroEmail', {
                                                        pParEmlCod       : ParEmlCod,   
                                                        pParEmlNom       : ParEmlNom,   
                                                        pParEmlPro       : ParEmlPro,   
                                                        pParEmlSrv       : ParEmlSrv,   
                                                        pParEmlPrt       : ParEmlPrt,   
                                                        pParEmlDeNom     : ParEmlDeNom,   
                                                        pParEmlDeEml     : ParEmlDeEml,   
                                                        pParEmlUtlAut    : ParEmlUtlAut,   
                                                        pParEmlTpoAut    : ParEmlTpoAut,   
                                                        pParEmlDom       : ParEmlDom,   
                                                        pParEmlUsr       : ParEmlUsr,   
                                                        pParEmlPsw       : ParEmlPsw,   
                                                        pParEmlSSL       : ParEmlSSL,   
                                                        pParEmlTmpEsp    : ParEmlTmpEsp,  
                                                        pParEmlDebug     : ParEmlDebug,
                                                        pParEmlReqConf   : ParEmlReqConf,
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
                                                $.post('<% out.print(urlSistema); %>ABM_ParametroEmail', {
                                                        pParEmlCod       : ParEmlCod,   
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
        <jsp:include page="/masterPage/NotificacionError.jsp"/>
        <div class="wrapper">
            <jsp:include page="/masterPage/menu_izquierdo.jsp" />
            
            <div id="contenido" name="contenido" class="main-panel">
                
                <div class="contenedor-cabezal">
                    <jsp:include page="/masterPage/cabezal.jsp"/>
                </div>
                
                <div class="contenedor-principal">
                    <div class="col-sm-11 contenedor-texto-titulo-flotante">
                        
                       <div class="contenedor-titulo">    
                            <p>Parametro email</p>
                        </div> 
                        
                        <div class=""> 
                            <div class="" style="text-align: right;"><a href="<% out.print(urlSistema); %>Definiciones/DefParametroEmailWW.jsp">Regresar</a></div>
                        </div>
                
                        <div style="display:none" id="datos_ocultos" name="datos_ocultos">
                            <input type="hidden" name="MODO" id="MODO" value="<% out.print(Mode); %>">
                        </div>

                        <form id="frm_Version" name="frm_Version">

                            <!------------------------------------------------------------------------------------------>
                            <div>
                                <label>Código:</label>
                                <input type="num" class="form-control" id="ParEmlCod" name="ParEmlCod" placeholder="Código" value="<% out.print( utilidad.NuloToVacio(paramEml.getParEmlCod())); %>" disabled>
                            </div>

                            <!------------------------------------------------------------------------------------------> 

                            <div>
                                <label>Nombre</label>
                                <input type="text" class="form-control" id="ParEmlNom" name="ParEmlNom" placeholder="ParEmlNom" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio(paramEml.getParEmlNom())); %>" >
                            </div>

                            <div>
                                <label>Protocolo</label>
                                <select class="form-control" id="ParEmlPro" name="ParEmlPro" <% out.print(CamposActivos); %>>
                                    <%
                                        for (ProtocoloEmail protocolo : ProtocoloEmail.values()){
                                            if(protocolo == paramEml.getParEmlPro()){
                                                out.println("<option selected value='" + protocolo.getCod() + "'>" + protocolo.getNom() + "</option>");
                                            }
                                            else
                                            {
                                                out.println("<option value='" + protocolo.getCod() + "'>" + protocolo.getNom() + "</option>");
                                            }
                                        }
                                    %>
                                </select>
                            </div>

                            <div>
                                <label>Servidor de correo</label>
                                <input type="text" class="form-control" id="ParEmlSrv" name="ParEmlSrv" placeholder="ParEmlSrv" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio(paramEml.getParEmlSrv())); %>">
                            </div>

                            <div>
                                <label>Puerto</label>
                                <input type="number" class="form-control" id="ParEmlPrt" name="ParEmlPrt" placeholder="ParEmlPrt" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio(paramEml.getParEmlPrt())); %>">
                            </div>

                            <div>
                                <label>De nombre:</label>
                                <input type="text" class="form-control" id="ParEmlDeNom" name="ParEmlDeNom" placeholder="ParEmlDeNom" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio(paramEml.getParEmlDeNom())); %>">
                            </div>

                            <div>
                                <label>De email:</label>
                                <input type="email" class="form-control" id="ParEmlDeEml" name="ParEmlDeEml" placeholder="ParEmlDeEml"  <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio(paramEml.getParEmlDeEml())); %>">
                            </div>

                            <div>
                                <label>Tipo autenticación</label>
                                <select class="form-control" id="ParEmlTpoAut" name="ParEmlTpoAut" <% out.print(CamposActivos); %>>
                                    <%
                                        for (TipoAutenticacion tpoAut : TipoAutenticacion.values()){
                                            if(tpoAut == paramEml.getParEmlTpoAut()){
                                                out.println("<option selected value='" + tpoAut.getCod() + "'>" + tpoAut.getNom() + "</option>");
                                            }
                                            else
                                            {
                                                out.println("<option value='" + tpoAut.getCod() + "'>" + tpoAut.getNom() + "</option>");
                                            }
                                        }
                                    %>
                                </select>
                            </div>

                            <div>
                                <label>Dominio</label>
                                <input type="text" class="form-control" id="ParEmlDom" name="ParEmlDom" placeholder="ParEmlDom" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio(paramEml.getParEmlDom())); %>">
                            </div>

                            <div>
                                <label>Usuario</label>
                                <input type="text" class="form-control" id="ParEmlUsr" name="ParEmlUsr" placeholder="ParEmlUsr" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio(paramEml.getParEmlUsr())); %>">
                            </div>

                            <div>
                                <label>Contraseña</label>
                                <input type="text" class="form-control" id="ParEmlPsw" name="ParEmlPsw" placeholder="ParEmlPsw" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio(paramEml.getParEmlPsw())); %>">
                            </div>

                            <div>
                                <label>SSL</label>
                                <select class="form-control" id="ParEmlSSL" name="ParEmlSSL" <% out.print(CamposActivos); %>>
                                    <%
                                        for (TipoSSL tpoSSL : TipoSSL.values()){
                                            if(tpoSSL == paramEml.getParEmlSSL()){
                                                out.println("<option selected value='" + tpoSSL.getCod() + "'>" + tpoSSL.getNom() + "</option>");
                                            }
                                            else
                                            {
                                                out.println("<option value='" + tpoSSL.getCod() + "'>" + tpoSSL.getNom() + "</option>");
                                            }
                                        }
                                    %>
                                </select>
                            </div>

                            <div>
                                <label>Tiempo de espera en segundos</label>
                                <input type="number" class="form-control" id="ParEmlTmpEsp" name="ParEmlTmpEsp" placeholder="ParEmlTmpEsp" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio(paramEml.getParEmlTmpEsp())); %>">
                            </div>

                            <div class="checkbox">
                                <label><input type="checkbox" id="ParEmlUtlAut" name="ParEmlUtlAut"  <% out.print(CamposActivos); %> <% out.print( utilidad.BooleanToChecked(paramEml.getParEmlUtlAut())); %>> Utiliza autenticación</label>
                            </div>
                            
                            <div class="checkbox">
                                <label><input type="checkbox" id="ParEmlDebug" name="ParEmlDebug"  <% out.print(CamposActivos); %> <% out.print( utilidad.BooleanToChecked(paramEml.getParEmlDebug())); %>> Debug</label>
                            </div>
                            
                            <div class="checkbox">
                                <label><input type="checkbox" id="ParEmlReqConf" name="ParEmlReqConf"  <% out.print(CamposActivos); %> <% out.print( utilidad.BooleanToChecked(paramEml.getParEmlReqConf())); %>> Requiere confirmacion</label>
                            </div>

                            <!------------------------------------------------------------------------------------------>

                            <div>
                                <input name="btn_guardar" id="btn_guardar" value="Guardar" type="button" class="btn btn-success" />
                                <input value="Cancelar" class="btn btn-default" type="button" onclick="<% out.print(js_redirect); %> "/>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
