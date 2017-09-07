<%-- 
    Document   : login
    Created on : 01-sep-2017, 20:21:58
    Author     : alvar
--%>

<%@page import="Entidad.Parametro"%>
<%@page import="Logica.LoParametro"%>
<%@page import="Utiles.Utilidades"%>
<%@page import="Enumerado.NombreSesiones"%>
<%@page import="Logica.LoIniciar"%>
<%
    LoIniciar iniciar_sistema = new LoIniciar();
    iniciar_sistema.Iniciar(request);

    Utilidades util = Utilidades.GetInstancia();

    String urlSistema = util.GetInstancia().GetUrlSistema();
    
    String js_redirect = "window.location.replace('" + urlSistema + "');";

    Parametro param = LoParametro.GetInstancia().obtener();

%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gestión Académica - CTC</title>
        <jsp:include page="/masterPage/head.jsp"/>
    </head>
    <body class="body_clase">
        <jsp:include page="/masterPage/NotificacionError.jsp"/>
        
        <div class="login_fondo">		
            <div class="login_contenedor">
                <%                        if (param.getParUtlMdl()) {
                        out.println("<div class='login_aulas'>"
                                + "<a href='" + param.getParUrlMdl() + "'>"
                                + "AULAS <span class='ti-arrow-right'>"
                                + "</span></a></div>");
                    }
                %>



                <div class="login_contenedorImg"><img src="Imagenes/ctc.png" /></div>
                <h1 class="login_titulo">LOGIN</h1>
                <p class="login_texto">Bienvenido a Gestión, el servicio a estudiantes del Instituto CTC - Colonia.</p>
                <form>
                    <div class="login_form">
                        <input type="text" class="form-control login_inputBorde login_inputNumero" id="username" name="username" placeholder="Usuario">
                        <input type="password" class="form-control login_inputPass" id="password" name="password" placeholder="Contraseña">
                    </div>

                    <button type="button" name="btnLogin" id="btnLogin" class="login_boton">INGRESAR</button>
                </form>


                <a href="pswSolRecovery.jsp" class="login_olvideContrasena">¿Olvidaste tu contraseña?</a>		

            </div>
        </div>

        <div>
            <div id="div_pop_bkgr" name="div_pop_bkgr"></div>

            <div id="div_cargando" name="div_cargando">
                <div class="loading"></div>
            </div>

        </div>

       

        <script>
            $(document).ready(function () {
                MostrarCargando(false);

                $('#btnLogin').click(function (event) {
                    MostrarCargando(true);

                    var userVar = $('#username').val();
                    var passVar = $('#password').val();

                    if (userVar == '' || passVar == '')
                    {
                        MostrarMensaje("ERROR", "Debe ingresar usuario y contraseña");
                        MostrarCargando(false);
                    } else
                    {

                        // Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
                        $.post('Login', {
                            pUser: userVar,
                            pPass: passVar,
                            pAction: "INICIAR"
                        }, function (responseText) {
                            var obj = JSON.parse(responseText);

                            if (obj.tipoMensaje == 'ERROR')
                            {
                                MostrarMensaje("ERROR", obj.mensaje);
                                MostrarCargando(false);
                            } else
                            {
                                <%=js_redirect%>
                            }
                        });
                    }
                });

            });
        </script>
    </body>
</html>
