<%-- 
    Document   : log_out
    Created on : 24-jun-2017, 11:58:10
    Author     : alvar
--%>
<%@page import="Utiles.Utilidades"%>
<%@page import="Enumerado.NombreSesiones"%>

<%
    
    Utilidades utilidad = Utilidades.GetInstancia();
    String urlSistema   = utilidad.GetUrlSistema();
    
    String js_redirect = "window.location.replace('" + urlSistema +  "');";
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
                                
                                MostrarCargando(true);
                                
                                // Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
                                $.post('Login', {
                                        pAction : "FINALIZAR"
                                }, function(responseText) {
                                        var obj = JSON.parse(responseText);
                                
                                        if(obj.tipoMensaje == 'ERROR')
                                        {
                                            MostrarMensaje(obj.tipoMensaje, obj.mensaje);
                                            MostrarCargando(false);
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