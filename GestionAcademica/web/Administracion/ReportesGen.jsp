<%-- 
    Document   : DefSincronizacionWW
    Created on : 18-ago-2017, 10:05:16
    Author     : alvar
--%>
<%@page import="Logica.LoTipoEvaluacion"%>
<%@page import="Entidad.Sincronizacion"%>
<%@page import="Logica.LoSincronizacion"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Logica.Seguridad"%>
<%@page import="Utiles.Retorno_MsgObj"%>
<%@page import="Enumerado.NombreSesiones"%>
<%@page import="Utiles.Utilidades"%>
<%
    Utilidades utilidad = Utilidades.GetInstancia();
    String urlSistema = utilidad.GetUrlSistema();

    //----------------------------------------------------------------------------------------------------
    //CONTROL DE ACCESO
    //----------------------------------------------------------------------------------------------------
   
    
    String usuario = (String) session.getAttribute(NombreSesiones.USUARIO.getValor());
    Boolean esAdm = (Boolean) session.getAttribute(NombreSesiones.USUARIO_ADM.getValor());
    Boolean esAlu = (Boolean) session.getAttribute(NombreSesiones.USUARIO_ALU.getValor());
    Boolean esDoc = (Boolean) session.getAttribute(NombreSesiones.USUARIO_DOC.getValor());
    Retorno_MsgObj acceso = Seguridad.GetInstancia().ControlarAcceso(usuario, esAdm, esDoc, esAlu, utilidad.GetPaginaActual(request));

    if (acceso.SurgioError()) {
        response.sendRedirect((String) acceso.getObjeto());
    }

    //----------------------------------------------------------------------------------------------------
    
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gestión Académica - Reportes</title>
        <jsp:include page="/masterPage/head.jsp"/>
        
        
        <script src="<%=request.getContextPath()%>/JavaScript/DataTable/extensions/Buttons/js/dataTables.buttons.min.js"></script>
        <script src="<%=request.getContextPath()%>/JavaScript/DataTable/extensions/Buttons/js/buttons.html5.min.js"></script>
        <script src="<%=request.getContextPath()%>/JavaScript/jszip/jszip.min.js"></script>
        <script src="<%=request.getContextPath()%>/JavaScript/pdfmake/pdfmake.min.js"></script>
        <script src="<%=request.getContextPath()%>/JavaScript/pdfmake/vfs_fonts.js"></script>
        
        <script>
            $(document).ready(function () {
                
                    var dataObject = <%=LoTipoEvaluacion.GetInstancia().getReportContent()%>;
                
                
                    $('#example').DataTable( {
                        data: dataObject.data,
                        deferRender: true,
                        bLengthChange: false, //thought this line could hide the LengthMenu
                        destroy: true,
                        pageLength: 10,
                        language: {
                            "lengthMenu": "Mostrando _MENU_ registros por página",
                            "zeroRecords": "No se encontraron registros",
                            "info": "Página _PAGE_ de _PAGES_",
                            "infoEmpty": "No hay registros",
                            "search": "Buscar:",
                            "paginate": {
                                "first": "Primera",
                                "last": "Ultima",
                                "next": "Siguiente",
                                "previous": "Anterior"
                            },
                            "infoFiltered": "(Filtrado de _MAX_ total de registros)"
                        }
                        , columns: dataObject.columns,
                        dom: 'Bfrtip',
                        buttons: [
                            'copyHtml5',
                            'excelHtml5',
                            'csvHtml5',
                            'pdfHtml5'
                        ]
                    } );
            });
        </script>
            
    </head>
    <body>
        <jsp:include page="/masterPage/NotificacionError.jsp"/>
        <jsp:include page="/masterPage/cabezal_menu.jsp"/>

        <!-- CONTENIDO -->
        <div class="contenido" id="contenedor">

            <div class="row">
                <div class="col-lg-12">
                    <section class="panel">
                        <header class="panel-heading">
                            <!-- TITULO -->
                            REPORTES
                            <!-- BOTONES -->
                         </header>
                        <div class="panel-body">
                            <div class=" form">
                                <!-- CONTENIDO -->
                                
                                <table id="example" class="display" cellspacing="0" width="100%">
                                    
                                </table>
                                
                            </div>
                        </div>
                    </section>
                </div>
            </div>
        </div>

        <jsp:include page="/masterPage/footer.jsp"/>
        
    </body>
</html>
