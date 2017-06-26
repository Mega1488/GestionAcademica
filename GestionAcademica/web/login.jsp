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
<%@page import="Utiles.Utilidades"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.OutputStreamWriter"%>
<%@page import="java.net.HttpURLConnection"%>
<%@page import="java.net.URL"%>
<%@page import="javax.sound.midi.SysexMessage"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    Utilidades utilidad = Utilidades.GetInstancia();
    String urlSistema   = utilidad.GetUrlSistema();
    
    String js_redirect = "window.location.replace('" + urlSistema +  "');";
    
%>

        <script>
                $(document).ready(function() {
                    
                    MostrarCargando(false);
                        
                        $('#submit').click(function(event) {
                            MostrarCargando(true);                       
                    
                                var userVar = $('#username').val();
                                var passVar = $('#password').val();
                                
                                if(userVar == '' || passVar == '')
                                {
                                    MostrarMensaje("ERROR", "Completa los datos papa");
                                    MostrarCargando(false);
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
                                                    MostrarMensaje("ERROR", obj.mensaje);
                                                    MostrarCargando(false);
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
        
        
       
