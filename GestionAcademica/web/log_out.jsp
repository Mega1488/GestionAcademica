<%-- 
    Document   : log_out
    Created on : 24-jun-2017, 11:58:10
    Author     : alvar
--%>
<%@page import="Entidad.NotificacionBandeja"%>
<%@page import="Enumerado.BandejaEstado"%>
<%@page import="Enumerado.BandejaTipo"%>
<%@page import="Logica.LoBandeja"%>
<%@page import="Utiles.Retorno_MsgObj"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Utiles.Utilidades"%>
<%@page import="Enumerado.NombreSesiones"%>

<%
    
    String usuarioNombre    = (String) session.getAttribute(NombreSesiones.USUARIO_NOMBRE.getValor());
    Long PerCod             = (Long) session.getAttribute(NombreSesiones.USUARIO_PER.getValor());    

    List<Object> lstBandeja = new ArrayList<>();
    
    if(PerCod != null)
    {
        Retorno_MsgObj retorno = (Retorno_MsgObj) LoBandeja.GetInstancia().obtenerListaByTipoEstado(PerCod, BandejaTipo.WEB, BandejaEstado.SIN_LEER);
        if(!retorno.SurgioError())
        {
            lstBandeja = retorno.getLstObjetos();
        }
        else
        {
            out.print(retorno.getMensaje().toString());
        }
    }
    Integer cantidad = lstBandeja.size();

%>

<div  class="col-lg-12" style="text-align: right;">
    <div class="col-lg-10">
        <p>Notificaciones: <label><a href="#" id="btn_ver_bandeja"><%out.print(cantidad);%></a></label></p>
        
        <div name="div_notificaciones" id="div_notificaciones" class="col-lg-2 div_notificaciones" style="display: none;" >
            <%
                for(Object objeto : lstBandeja)
                {
                  NotificacionBandeja bandeja = (NotificacionBandeja) objeto;  
                  out.println("<div class='row'><div data-toggle='modal' data-target='#PopUpMensaje' data-codigo='"+bandeja.getNotBanCod()+"' data-asunto='"+bandeja.getNotBanAsu()+"' data-mensaje='"+bandeja.getNotBanMen()+"' class='btn_ver_msg'>" + bandeja.getNotBanAsu() + "</div></div>");
                }
            %>
            
        </div>
        
    </div>
    <div class="col-lg-2">
    <p>
        <label>Bienvenido: <% out.print(usuarioNombre); %></label>
    </p>
    <p>
        <a href="#" id="cerrar_sesion" name="cerrar_sesion">Cerrar sesion</a>
    </p>
    </div>
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
   