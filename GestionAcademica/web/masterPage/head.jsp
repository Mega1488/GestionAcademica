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


<meta http-equiv="X-UA-Compatible" content="IE=9; IE=10; IE=11; IE=EDGE" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>	
<meta name="theme-color" content="#628ec9">

<link rel="manifest" href="<%=request.getContextPath()%>/manifest.json">

<script src="<%=request.getContextPath()%>/JavaScript/jquery-3.2.1.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/JavaScript/jquery_ui/jquery-ui.min.js" type="text/javascript"></script> 
<script src="<%=request.getContextPath()%>/Bootstrap/js/bootstrap.min.js" type="text/javascript"></script>



<script src="<%=request.getContextPath()%>/JavaScript/DataTable/media/js/jquery.dataTables.min.js"></script>
<script src="<%=request.getContextPath()%>/JavaScript/DataTable/extensions/Select/js/dataTables.select.min.js"></script>

<link href="<%=request.getContextPath()%>/Estilos/sga_estyle.css"  rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath()%>/Bootstrap/css/bootstrap.min.css"  rel="stylesheet">
<link href="<%=request.getContextPath()%>/JavaScript/jquery_ui/jquery-ui.min.css" rel="stylesheet" type="text/css"/>

<!-- Fuente -->
<link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i" rel="stylesheet">

<link href="<%=request.getContextPath()%>/JavaScript/DataTable/media/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath()%>/JavaScript/DataTable/extensions/Select/css/select.dataTables.min.css" rel="stylesheet" type="text/css"/>

<link rel="stylesheet" href="<%=request.getContextPath()%>/JavaScript/FontAwesome/css/font-awesome.min.css">

<link rel="shortcut icon" type="image/png" href="<%=request.getContextPath()%>/Imagenes/ctc_ic.png"/>

<link href="<%=request.getContextPath()%>/Estilos/themify-icons.css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/JavaScript/menu.js"></script>

<script>
    
        
       
        

                function MostrarMensaje(tipoMensaje, mensaje)
                {
                    
                    MostrarCargando(false);

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
                
                $(document).ready(function () {
                    MostrarCargando(false);
                });

</script>