<%-- 
    Document   : index
    Created on : 24-jun-2017, 12:34:51
    Author     : alvar
--%>

<%@page import="Enumerado.NombreSesiones"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Utiles.Utilidades"%>
<%@page import="Logica.LoIniciar"%>

<%
    
    LoIniciar iniciar_sistema   = new LoIniciar();
    iniciar_sistema.Iniciar(request);
    
    session.setAttribute(NombreSesiones.URL_SISTEMA.getValor(), Utilidades.GetInstancia().GetUrlSistema());
    
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gestión Académica</title>
        <jsp:include page="/masterPage/head.jsp"/>
    </head>
    <body>
        <div class="container-fluid">
            
            <div id="cabezal" name="cabezal" class="row">
                <jsp:include page="/masterPage/cabezal.jsp"/>
            </div>
        
        
                <div class="col-sm-2">
                    <jsp:include page="/masterPage/menu_izquierdo.jsp" />
                </div>

                <div id="contenido" name="contenido"  class="col-sm-8">
                    <h1>Inicio</h1>
                    <div>
                        <label>
                            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam dignissim eros ac risus fermentum, mattis lacinia velit convallis. Vestibulum hendrerit nibh et turpis commodo finibus. Etiam mattis mauris sed lacus egestas scelerisque. Vestibulum at arcu ac urna accumsan malesuada. Morbi luctus lorem ut eros iaculis, at imperdiet lacus elementum. Suspendisse leo orci, imperdiet vitae iaculis vel, efficitur aliquam lorem. Pellentesque posuere mauris nec risus placerat semper. Nam commodo consectetur malesuada. Aenean ante lorem, vehicula sit amet est sit amet, dictum sagittis felis. Aliquam erat volutpat. Pellentesque et vehicula tortor, eu rhoncus lorem. Praesent tempor augue turpis, vel vestibulum lectus malesuada auctor. Suspendisse potenti. Suspendisse potenti. Quisque mattis nulla nec finibus tempor. Suspendisse eu finibus augue.</p>
                        </label>
                    </div>

                    <div>
                        <label>
                            <p>Cras sagittis elit at turpis eleifend lacinia. Duis eu iaculis arcu, vel consequat elit. Donec lacinia diam sed maximus tempor. Donec viverra ipsum at dolor rhoncus, in pellentesque augue ultrices. Interdum et malesuada fames ac ante ipsum primis in faucibus. In tincidunt mauris et nisl ultricies, in feugiat est pellentesque. Phasellus egestas et nibh a pharetra. Duis faucibus facilisis elit sed ullamcorper. Mauris a mattis massa, eget fermentum ligula. Nunc urna lacus, volutpat sagittis mi in, suscipit dapibus sapien. Phasellus dapibus mi eget consectetur lacinia. Nam ultrices ut elit ut posuere. Suspendisse ornare nec enim nec pellentesque. Ut aliquet commodo volutpat. Maecenas felis quam, volutpat et interdum sit amet, porttitor ullamcorper velit.</p>
                        </label>
                    </div>

                </div>
        </div>
    </body>
</html>
