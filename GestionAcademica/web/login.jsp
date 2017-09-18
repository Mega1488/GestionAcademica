<%-- 
    Document   : login
    Created on : 01-sep-2017, 20:21:58
    Author     : alvar
--%>

<%@page import="Entidad.Persona"%>
<%@page import="Logica.Seguridad"%>
<%@page import="Logica.LoPersona"%>
<%@page import="Utiles.Mensajes"%>
<%@page import="Enumerado.TipoMensaje"%>
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
    
    
    
    /* LOGIN */
    Mensajes mensaje    = new Mensajes("Error al iniciar sesión", TipoMensaje.ERROR);
    Boolean mostrarMensaje  = false;
    String usr              = request.getParameter("pUser");
    if(usr!=null)
    {
        mostrarMensaje  = true;
        String psw      = request.getParameter("pPass"); 

       
        LoPersona loPersona = LoPersona.GetInstancia();
        Seguridad seguridad = Seguridad.GetInstancia();


        if(loPersona.IniciarSesion(usr, seguridad.cryptWithMD5(psw)))
        {
            //Inicio correctamente
            
            session.setAttribute(NombreSesiones.USUARIO.getValor(), usr);
            
            Persona persona = (Persona) LoPersona.GetInstancia().obtenerByMdlUsr(usr).getObjeto();
            session.setAttribute(NombreSesiones.USUARIO_NOMBRE.getValor(), persona.getPerNom());
            session.setAttribute(NombreSesiones.USUARIO_ADM.getValor(), persona.getPerEsAdm());
            session.setAttribute(NombreSesiones.USUARIO_ALU.getValor(), persona.getPerEsAlu());
            session.setAttribute(NombreSesiones.USUARIO_DOC.getValor(), persona.getPerEsDoc());
            session.setAttribute(NombreSesiones.USUARIO_PER.getValor(), persona.getPerCod());
            
            String web = "login.jsp";
            if(persona.getPerEsAdm())
            {
                web = "Definiciones/DefCalendarioGrid.jsp";
            }else if(persona.getPerEsDoc())
            {
                web = "Docente/EstudiosDictados.jsp";
            }else if(persona.getPerEsAlu())
            { 
                web = "Alumno/Evaluaciones.jsp";

            }

            request.getRequestDispatcher(web).forward(request, response);
            
        }
        else
        {

            //No inicio correctamente
            mensaje = new Mensajes("Usuario o contraseña incorrectos", TipoMensaje.ERROR);
        }
    }

%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
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
                <form action="#" method="post">
                    <div class="login_form">
                        <input type="text" class="form-control login_inputBorde login_inputNumero" id="pUser" name="pUser" placeholder="Usuario">
                        <input type="password" class="form-control login_inputPass" id="pPass" name="pPass" placeholder="Contraseña">
                    </div>

                    <button type="submit" name="btnLogin" id="btnLogin" class="login_boton">INGRESAR</button>
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

               <%
                   if(mostrarMensaje)
                   {
                       out.println("MostrarMensaje('"+mensaje.getTipoMensaje()+"','"+mensaje.getMensaje()+"');");
                   }
               %>
         
            });
        </script>
    </body>
</html>
