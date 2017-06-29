<%-- 
    Document   : DefVersion
    Created on : 25-jun-2017, 21:43:57
    Author     : alvar
--%>

<%@page import="Entidad.ParametroEmail"%>
<%@page import="Enumerado.Modo"%>
<%@page import="Logica.LoParametroEmail"%>
<%@page import="Utiles.Utilidades"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    LoParametroEmail lPrmEml    = LoParametroEmail.GetInstancia();
    Utilidades utilidad         = Utilidades.GetInstancia();
    String urlSistema           = utilidad.GetUrlSistema();
    
    Modo Mode           = Modo.valueOf(request.getParameter("MODO"));
    String ParEmlCod    = request.getParameter("pParEmlCod");
    String js_redirect  = "window.location.replace('" + urlSistema +  "Definiciones/DefParametroEmailWW.jsp');";

    ParametroEmail paramEml = new ParametroEmail();
    
    if(Mode.equals(Modo.UPDATE) || Mode.equals(Modo.DISPLAY) || Mode.equals(Modo.DELETE))
    {
        paramEml = lPrmEml.obtener(Integer.valueOf(ParEmlCod));
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
                                
                                
                               if($('#MODO').val() == "INSERT")
                                {
                                    Guardar();
                                }

                                if($('#MODO').val() == "UPDATE")
                                {
                                    Actualizar();
                                }

                                if($('#MODO').val() == "DELETE")
                                {
                                    Eliminar();
                                }
                        });
                
                });
                
                function Guardar(){
                     MostrarCargando(true);
                                
                    //var ParEmlCod   = $('#SisVerCod').val();
                    var ParEmlNom       = $('#ParEmlNom').val();
                    var ParEmlPro       = $('#ParEmlPro').val();
                    var ParEmlSrv       = $('#ParEmlSrv').val();
                    var ParEmlPrt       = $('#ParEmlPrt').val();
                    var ParEmlDeNom     = $('#ParEmlDeNom').val();
                    var ParEmlDeEml     = $('#ParEmlDeEml').val();
                    var ParEmlUtlAut    = document.getElementById('ParEmlUtlAut').checked;
                    var ParEmlTpoAut    = $('#ParEmlTpoAut').val();  
                    var ParEmlDom       = $('#ParEmlDom').val();
                    var ParEmlUsr       = $('#ParEmlUsr').val();
                    var ParEmlPsw       = $('#ParEmlPsw').val();
                    var ParEmlSSL       = $('#ParEmlSSL').val();  
                    var ParEmlTmpEsp    = $('#ParEmlTmpEsp').val();  


                    if(ParEmlNom == '')
                    {
                        MostrarMensaje("ERROR", "Completa los datos papa");
                        MostrarCargando(false);
                    }
                    else
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
                                    pAction          : "INSERTAR"
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
                }
                
                function Actualizar(){
                MostrarCargando(true);
                                
                    var ParEmlCod       = $('#ParEmlCod').val();
                    var ParEmlNom       = $('#ParEmlNom').val();
                    var ParEmlPro       = $('#ParEmlPro').val();
                    var ParEmlSrv       = $('#ParEmlSrv').val();
                    var ParEmlPrt       = $('#ParEmlPrt').val();
                    var ParEmlDeNom     = $('#ParEmlDeNom').val();
                    var ParEmlDeEml     = $('#ParEmlDeEml').val();
                    var ParEmlUtlAut    = document.getElementById('ParEmlUtlAut').checked;
                    var ParEmlTpoAut    = $('#ParEmlTpoAut').val();  
                    var ParEmlDom       = $('#ParEmlDom').val();
                    var ParEmlUsr       = $('#ParEmlUsr').val();
                    var ParEmlPsw       = $('#ParEmlPsw').val();
                    var ParEmlSSL       = $('#ParEmlSSL').val();  
                    var ParEmlTmpEsp    = $('#ParEmlTmpEsp').val();  


                    if(ParEmlNom == '')
                    {
                        MostrarMensaje("ERROR", "Completa los datos papa");
                        MostrarCargando(false);
                    }
                    else
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
                                    pAction          : "ACTUALIZAR"
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
                }
                
                function Eliminar(){
                
                    var ParEmlCod       = $('#ParEmlCod').val();
                
                    $.post('<% out.print(urlSistema); %>ABM_ParametroEmail', {
                                    pParEmlCod       : ParEmlCod,   
                                    pAction          : "ELIMINAR"
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
                        <input type="text" class="form-control" id="ParEmlPro" name="ParEmlPro" placeholder="ParEmlPro" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio(paramEml.getParEmlPro())); %>">
                    </div>
                    
                    <div>
                        <label>Servidor de correo</label>
                        <input type="text" class="form-control" id="ParEmlSrv" name="ParEmlSrv" placeholder="ParEmlSrv" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio(paramEml.getParEmlSrv())); %>">
                    </div>
                    
                    <div>
                        <label>Puerto</label>
                        <input type="text" class="form-control" id="ParEmlPrt" name="ParEmlPrt" placeholder="ParEmlPrt" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio(paramEml.getParEmlPrt())); %>">
                    </div>
                    
                    <div>
                        <label>De nombre:</label>
                        <input type="text" class="form-control" id="ParEmlDeNom" name="ParEmlDeNom" placeholder="ParEmlDeNom" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio(paramEml.getParEmlDeNom())); %>">
                    </div>
                    
                    <div>
                        <label>De email:</label>
                        <input type="text" class="form-control" id="ParEmlDeEml" name="ParEmlDeEml" placeholder="ParEmlDeEml"  <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio(paramEml.getParEmlDeEml())); %>">
                    </div>
                    
                    <div>
                        <label>Tipo autenticación</label>
                        <input type="text" class="form-control" id="ParEmlTpoAut" name="ParEmlTpoAut" placeholder="ParEmlTpoAut"  <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio(paramEml.getParEmlTpoAut().getValor())); %>">
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
                        <input type="text" class="form-control" id="ParEmlSSL" name="ParEmlSSL" placeholder="ParEmlSSL" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio(paramEml.getParEmlSSL().getValor())); %>">
                    </div>
                    
                    <div>
                        <label>Tiempo de espera en segundos</label>
                        <input type="text" class="form-control" id="ParEmlTmpEsp" name="ParEmlTmpEsp" placeholder="ParEmlTmpEsp" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio(paramEml.getParEmlTmpEsp())); %>">
                    </div>

                    <div>
                        <label>Utiliza autenticación</label>
                        <input type="checkbox" id="ParEmlUtlAut" name="ParEmlUtlAut"  <% out.print(CamposActivos); %> <% out.print( utilidad.BooleanToChecked(paramEml.getParEmlUtlAut())); %>>
                    </div>

                    <!------------------------------------------------------------------------------------------>
                    
                    <div>
                        <input name="btn_guardar" id="btn_guardar" value="Guardar" type="button" />
                    </div>
                </form>
            </div>
            
            <div id="div_cargando" name="div_cargando"></div>
            
    </body>
</html>
