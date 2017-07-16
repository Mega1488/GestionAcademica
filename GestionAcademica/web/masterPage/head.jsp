<%-- 
    Document   : head
    Created on : 24-jun-2017, 11:59:53
    Author     : alvar
--%>
<%@page import="Dominio.Sitios"%>
<%@page import="Entidad.Persona"%>
<%@page import="Logica.LoPersona"%>
<%@page import="Enumerado.NombreSesiones"%>
<%@page import="Enumerado.Accion"%>
<%@page import="Enumerado.Modo"%>
<%@page import="Logica.Seguridad"%>
<%@page import="Utiles.Utilidades"%>
<%
    
    Utilidades utilidad = Utilidades.GetInstancia();
    String urlSistema   = utilidad.GetUrlSistema();
    
    String jquery       = "'" + urlSistema + "JavaScript/jquery-3.2.1.js'";
    String jquery_ui    = "'" + urlSistema + "JavaScript/jquery_ui/jquery-ui.js'";
    String bootstrap_js = "'" + urlSistema + "Bootstrap/js/bootstrap.min.js'";
    
    String jquery_css   = "'" + urlSistema + "JavaScript/jquery_ui/jquery-ui.css'";
    String css          = "'" + urlSistema + "Estilos/sga_estyle.css'";
    String bootstrap    = "'" + urlSistema + "Bootstrap/css/bootstrap.min.css'";
    
   

%>

<script src=<% out.print(jquery); %> type="text/javascript"></script>
<script src=<% out.print(jquery_ui); %> type="text/javascript"></script> 
<script src=<% out.print(bootstrap_js); %> type="text/javascript"></script>
<script src="https://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>

<link href=<% out.print(css); %>  rel="stylesheet" type="text/css"/>
<link href=<% out.print(bootstrap); %>  rel="stylesheet">
<link href=<% out.print(jquery_css); %> rel="stylesheet" type="text/css"/>

<link href="https://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css"/>


<script>
    
        
       
        

                function MostrarMensaje(tipoMensaje, mensaje)
                {

                    $('#txtError').text(mensaje);

                    if(tipoMensaje == 'ERROR')
                    {
                        $("#msgError").attr('class', 'alert alert-danger div_msg');
                    }

                    if(tipoMensaje == 'ADVERTENCIA')
                    {
                        $("#msgError").attr('class', 'alert alert-warning div_msg');
                    }

                    if(tipoMensaje == 'MENSAJE')
                    {
                        $("#msgError").attr('class', 'alert alert-success div_msg');
                    }

                    $("#msgError").show();

                    setTimeout(function(){
                        //do what you need here
                        $("#msgError").hide();
                    }, 2000);
                }


                function MostrarCargando(mostrar)
                {
                    if(mostrar)
                    {
                        $("#div_cargando").attr('class', 'div_cargando_load');
                    }
                    else
                    {
                        $("#div_cargando").attr('class', 'div_cargando');
                    }
                }

</script>