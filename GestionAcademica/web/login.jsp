<%-- 
    Document   : login
    Created on : 18-jun-2017, 12:50:32
    Author     : alvar
--%>

<%@page import="Entidad.Persona"%>
<%@page import="Logica.LoPersona"%>
<%@page import="java.security.SecureRandom"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="Enumerado.NombreSesiones"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.OutputStreamWriter"%>
<%@page import="java.net.HttpURLConnection"%>
<%@page import="java.net.URL"%>
<%@page import="javax.sound.midi.SysexMessage"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    String url_sistema = (String) session.getAttribute(NombreSesiones.URL_SISTEMA.getValor());
    String js_redirect = "window.location.replace('" + url_sistema +  "');";
    
%>

        <script>
                $(document).ready(function() {
                    
                        document.getElementById("msgError").style.visibility='hidden';
                        document.getElementById("div_cargando").className  = 'div_cargando';
                        
                        
                       
                        
                        $('#submit').click(function(event) {
                                document.getElementById("div_cargando").className  = 'div_cargando_load';
                                document.getElementById("msgError").style.visibility='hidden';
                    
                                var userVar   = $('#username').val();
                                var passVar = $('#password').val();
                                
                                if(userVar == '' || passVar == '')
                                {
                                    $('#txtError').text("Completa los datos papa");
                                    document.getElementById("msgError").style.visibility='visible'; 
                                }
                                else
                                {
                                
                                // Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
                                $.post('Login', {
                                        pUser   : userVar,
                                        pPass   : passVar,
                                        pAction : "INICIAR"
                                }, function(responseText) {
                                        var obj = JSON.parse(responseText);
                                
                                        if(obj.tipoMensaje == 'ERROR')
                                        {
                                            $('#txtError').text(obj.mensaje);
                                            document.getElementById("msgError").style.visibility='visible';
                                            document.getElementById("div_cargando").className  = 'div_cargando';
                                        }
                                        else
                                        {
                                            <%
                                                out.print(js_redirect);
                                            %>     
                                        }
                                });
                            }
                        });
                    
                });
        </script>

    
        <div id="msgError" name="msgError"> 
            <label id="txtError" name="txtError">Error</label>
        </div>
        
        <form name="login">

            <p>Username :

            <input size="10" name="username" id="username" />

            </p>

            <p>Password :

            <input size="10" name="password" id="password" type="password" />

            </p>

            <p>

            <input name="submit" id="submit" value="Login" type="button" />

            </p>

        </form>
        
        
        <div id="div_cargando" name="div_cargando">
            
        </div>
