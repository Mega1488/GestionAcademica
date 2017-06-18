<%-- 
    Document   : login
    Created on : 18-jun-2017, 12:50:32
    Author     : alvar
--%>

<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.OutputStreamWriter"%>
<%@page import="java.net.HttpURLConnection"%>
<%@page import="java.net.URL"%>
<%@page import="javax.sound.midi.SysexMessage"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            String urlstring    = "http://192.168.0.106/login/index.php";
            String usuario      = request.getParameter("username");
            String passwrd      = request.getParameter("password");
            String errorcode    = request.getParameter("errorcode");
            int error           = 0;

                    
            System.err.println("usuario" + usuario);
            System.err.println("passwrd" + passwrd);
            System.err.println("errorcode" + errorcode);
            
            //if(errorcode == null || errorcode.isEmpty())
            //{
                if(usuario != null && passwrd != null)
                {
                    
                       out.println("<form id='myForm' action='" + urlstring + "' method='post'>");
                       
                       out.println("<input type='hidden' name='username' value='"+usuario+"'>");
                       out.println("<input type='hidden' name='password' value='"+passwrd+"'>");
                        
                       out.println("</form>");
                       out.println("<script type='text/javascript'>");
                       out.println("     document.getElementById('myForm').submit();");
                       out.println("</script>");
                }
                else                    
                {
                    if(errorcode != null && !errorcode.isEmpty())
                    {
                    
                         try{
                                System.err.println("Error en texto: " + errorcode);
                                error = Integer.parseInt(errorcode.trim().toString());
                            }
                            catch(Exception ex)
                            {
                                ex.printStackTrace();
                            }
           
                        System.err.println("Error numerico: " + error);
                        
                        switch(error){
                            case 1: 
                                    out.println("<p>Error al iniciar sesion</p>");
                                    break;
                            case 2: 
                                    out.println("<p>Error al iniciar sesion</p>");
                                    break;
                            case 3: 
                                    out.println("<p>Error al iniciar sesion: Usuario o contraseña incorrecto</p>");
                                    break;
                            default: out.println("<p>Error al iniciar sesion: No se recibio parametro</p>");
                                    break;
                        }
                    }
                    
                }

            
        %>

        <h1>Hello World!</h1>
        
        <form class="loginform" name="login" method="post" action="#">

            <p>Username :

            <input size="10" name="username" />

            </p>

            <p>Password :

            <input size="10" name="password" type="password" />

            </p>

            <p>

            <input name="Submit" value="Login" type="submit" />

            </p>

        </form>
    </body>
</html>
