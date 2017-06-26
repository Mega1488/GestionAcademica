<%-- 
    Document   : head
    Created on : 24-jun-2017, 11:59:53
    Author     : alvar
--%>
<%@page import="Utiles.Utilidades"%>
<%
    
    Utilidades utilidad = Utilidades.GetInstancia();
    String urlSistema   = utilidad.GetUrlSistema();
    
    String jquery   = "'" + urlSistema + "JavaScript/jquery-3.2.1.js'";
    String css      = "'" + urlSistema + "Estilos/sga_estyle.css'";
    String bootstrap = "'" + urlSistema + "Bootstrap/css/bootstrap.min.css'";
%>

<script src=<% out.print(jquery); %> type="text/javascript"></script>
<link href=<% out.print(css); %>  rel="stylesheet" type="text/css"/>
<link href=<% out.print(bootstrap); %>  rel="stylesheet">
   

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