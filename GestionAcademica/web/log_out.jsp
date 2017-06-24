<%-- 
    Document   : log_out
    Created on : 24-jun-2017, 11:58:10
    Author     : alvar
--%>
<%@page import="Enumerado.NombreSesiones"%>

<%
    String url_sistema = (String) session.getAttribute(NombreSesiones.URL_SISTEMA.getValor());
    String js_redirect = "window.location.replace('" + url_sistema +  "');";
    
%>

<div id="msgError" name="msgError"> 
            <label id="txtError" name="txtError">Error</label>
</div>

<div><label>Esta es la persona que inicio sesion: <% out.print(session.getAttribute(NombreSesiones.USUARIO.getValor())); %></label></div>
<div><a href="#" id="cerrar_sesion" name="cerrar_sesion">Cerrar sesion</a></div>

<script>
                $(document).ready(function() {
                    
                    document.getElementById("msgError").style.visibility='hidden';
                    
                        $('#cerrar_sesion').click(function(event) {
                                
                                document.getElementById("msgError").style.visibility='hidden';
                                
                                // Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
                                $.post('Login', {
                                        pAction : "FINALIZAR"
                                }, function(responseText) {
                                        var obj = JSON.parse(responseText);
                                
                                        if(obj.tipoMensaje == 'ERROR')
                                        {
                                            $('#txtError').text(obj.mensaje);
                                            document.getElementById("msgError").style.visibility='visible';
                                        }
                                        else
                                        {
                                            <%
                                                out.print(js_redirect);
                                            %>
                                        }
                                });
                            
                        });
                    
                });
        </script>