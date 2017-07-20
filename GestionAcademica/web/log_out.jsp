<%-- 
    Document   : log_out
    Created on : 24-jun-2017, 11:58:10
    Author     : alvar
--%>
<%@page import="Utiles.Utilidades"%>
<%@page import="Enumerado.NombreSesiones"%>

<%
    
    String usuarioNombre = (String) session.getAttribute(NombreSesiones.USUARIO_NOMBRE.getValor());
%>

<div  class="" style="text-align: right;">
    <p>
        <label>Bienvenido: <% out.print(usuarioNombre); %></label>
    </p>
    <p>
        <a href="#" id="cerrar_sesion" name="cerrar_sesion">Cerrar sesion</a>
    </p>
</div>

<script>
                $(document).ready(function() {
                    
                    var urlAct = $('#sga_url').val();
                    
                    MostrarCargando(false);
                    
                        $('#cerrar_sesion').click(function(event) {
                                
                                MostrarCargando(true);
                                
                                // Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
                                $.post(urlAct + 'Login', {
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
                                            window.location.replace(urlAct);
                                        }
                                });
                            
                        });
                    
                });
        </script>