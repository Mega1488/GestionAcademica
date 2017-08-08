<%-- 
    Document   : Estudios
    Created on : 26-jul-2017, 16:37:59
    Author     : alvar
--%>

<%@page import="Logica.LoCalendario"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.Comparator"%>
<%@page import="Entidad.Materia"%>
<%@page import="Entidad.PlanEstudio"%>
<%@page import="Entidad.Escolaridad"%>
<%@page import="Logica.LoPersona"%>
<%@page import="Entidad.Persona"%>
<%@page import="SDT.SDT_PersonaEstudio"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Logica.LoPeriodo"%>
<%@page import="Utiles.Retorno_MsgObj"%>
<%@page import="Logica.Seguridad"%>
<%@page import="Enumerado.NombreSesiones"%>
<%@page import="Utiles.Utilidades"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Utilidades utilidad = Utilidades.GetInstancia();

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
    ArrayList<SDT_PersonaEstudio> lstEstudio = new ArrayList<>();
    Persona persona = (Persona) LoPersona.GetInstancia().obtenerByMdlUsr(usuario).getObjeto();
    lstEstudio = LoPersona.GetInstancia().ObtenerEstudios(persona.getPerCod());

    String tblVisible = (lstEstudio.size() > 0 ? "" : "display: none;");


%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gestión Académica - Estudios</title>
        <jsp:include page="/masterPage/head.jsp"/>

        <script type="text/javascript">

            $(document).ready(function () {

                function getOffset(el) {
                    var _x = 0;
                    var _y = 0;
                    while (el && !isNaN(el.offsetLeft) && !isNaN(el.offsetTop))
                    {
                        _x += el.offsetLeft - el.scrollLeft;
                        _y += el.offsetTop - el.scrollTop;
                        el = el.offsetParent;
                    }

                    return {top: _y, left: _x};
                }

                $("#lines").attr("width", $("#cont_estudio").width());
                $("#lines").attr("height", $("#cont_estudio").height());

                $("[data-materia]").each(function () {
                    var materia = "dv_mat_" + $(this).data("id");
                    var previas = $(this).data("previas");

                    $.each(previas, function (f, previa) {

                        var previaCodigo = "dv_mat_" + previa;

                        var fromPoint = getOffset($('#' + previaCodigo)[0]);
                        var toPoint = getOffset($('#' + materia)[0]);

                        var from = function () {},
                                to = new String('to');
                        from.y = fromPoint.top - 150;
                        from.x = fromPoint.left;
                        to.y = toPoint.top - 150;
                        to.x = toPoint.left;

                        //$("#lines").html("<svg><line x1='50' y1='50' x2='350' y2='350' stroke='black'/></svg>");
                        $("#lines").html($("#lines").html() + "<line x1='" + from.x + "' y1='" + from.y + "' x2='" + to.x + "' y2='" + to.y + "' stroke='black' data-from='" + previaCodigo + "' data-to='" + materia + "'/>");

                    });
                });
                /*
                 var fromPoint = getOffset($('#first')[0]);
                 var toPoint = getOffset($('#second')[0]);
                 
                 var from = function () {},
                 to = new String('to');
                 from.y = fromPoint.top+10;
                 from.x = fromPoint.left+10;
                 to.y = toPoint.top+10; 
                 to.x = toPoint.left+10;
                 
                 $.line(from, to);
                 */
            });
        </script>
    </head>
    <body>
        <jsp:include page="/masterPage/NotificacionError.jsp"/>
        <div class="wrapper">
            <jsp:include page="/masterPage/menu_izquierdo.jsp" />

            <div id="contenido" name="contenido" class="main-panel">

                <div class="contenedor-cabezal">
                    <jsp:include page="/masterPage/cabezal.jsp"/>
                </div>

                <div class="contenedor-principal">
                    <div class="col-sm-11 contenedor-texto-titulo-flotante">
                        <div class="contenedor-titulo">    
                            <p>Estudios</p>
                        </div>

                        <div name="cont_estudio" class="col-lg-12" style='margin-top:15px; <% out.print(tblVisible); %>'>

                            <svg name="lines" id="lines" width="100%" height="100%" style="position: absolute; left: 0; top:0">

                            </svg>

                            <%
                                for (SDT_PersonaEstudio est : lstEstudio) {
                                    //--------------------------------------------------------------------------------------------------------
                                    //INICIAMOS DIV INSCRIPCION
                                    //--------------------------------------------------------------------------------------------------------
                                    out.println("<div name='div_inscripcion' class='row'>");

                                    if (est.getInscripcion().getPlanEstudio() != null) {
                                        //--------------------------------------------------------------------------------------------------------
                                        //INSCRIPCION NOMBRE
                                        //--------------------------------------------------------------------------------------------------------

                                        PlanEstudio plan = est.getInscripcion().getPlanEstudio();
                                        //MOSTRAR TODAS LAS MATERIAS DEL PLAN.

                                        out.println("<div name='div_inscripcion_nombre' class='col-lg-12'><h2>" + plan.getCarreraPlanNombre() + "</h2></div>");

                                        Double periodo = 0.0;
                                        boolean cerrarDivPeriodo = false;

                                        for (Materia materia : plan.getLstMateria()) {
                                            //--------------------------------------------------------------------------------------------------------
                                            //MANEJAMOS DIV CONTENEDOR SEMESTRE
                                            //--------------------------------------------------------------------------------------------------------
                                            if (!materia.getMatPerVal().equals(periodo)) {
                                                periodo = materia.getMatPerVal();

                                                if (cerrarDivPeriodo) {
                                                    //--------------------------------------------------------------------------------------------------------
                                                    //FINALIZAMOS DIV CONTENEDOR SEMESTRE
                                                    //--------------------------------------------------------------------------------------------------------

                                                    out.println("</div>");
                                                }

                                                //--------------------------------------------------------------------------------------------------------
                                                //INICIAMOS DIV CONTENEDOR SEMESTRE
                                                //--------------------------------------------------------------------------------------------------------
                                                out.println("<div name='div_semestre' class='col-lg-12'> ");
                                                out.println("<div class='col-lg-12'><h3>" + materia.getMatTpoPer().getTipoPeriodoNombre() + ": " + materia.getMatPerVal() + "</h3></div>");
                                                cerrarDivPeriodo = true;
                                            }

                                            //--------------------------------------------------------------------------------------------------------
                                            //INICIO DIV MATERIA
                                            //--------------------------------------------------------------------------------------------------------
                                            out.println("<div class='col-lg-3' id='dv_mat_" + materia.getMatCod() + "' data-materia='" + materia.getMatNom() + "' data-id='" + materia.getMatCod() + "' data-previas='" + materia.ObtenerPreviasCodigos() + "'><div class='caja_estudio'>");

                                            String progreso = "";
                                            String escolaridad = "<div name='escolaridad'>";

                                            if (LoCalendario.GetInstancia().AlumnoCursoEstudio(persona.getPerCod(), materia, null, null)) {
                                                //--------------------------------------------------------------------------------------------------------
                                                //ALUMNO CURSO MATERIA
                                                //--------------------------------------------------------------------------------------------------------
                                                progreso = "En curso";

                                                for (Escolaridad esc : est.getEscolaridad()) {
                                                    if (esc.getMateria() != null) {
                                                        if (esc.getMateria().getMatCod().equals(materia.getMatCod())) {
                                                            //--------------------------------------------------------------------------------------------------------
                                                            //SE APLICA ESTADO DE MATERIA
                                                            //--------------------------------------------------------------------------------------------------------
                                                            if (esc.Revalida()) {
                                                                progreso = "Revalida";
                                                            } else if (esc.getAprobado()) {
                                                                progreso = "Cursada";
                                                            }

                                                            //--------------------------------------------------------------------------------------------------------
                                                            //SE AGREGA DATO DE ESCOLARIDAD
                                                            //--------------------------------------------------------------------------------------------------------
                                                            escolaridad += "<div name='una_escolaridad'>";
                                                            escolaridad += "<div>Fecha: <label>" + esc.getEscFch() + "</label></div>\n";
                                                            escolaridad += "<div>Curso: <label>" + esc.getEscCurVal() + "</label></div>\n";
                                                            escolaridad += "<div>Examen: <label>" + esc.getEscCalVal() + "</label></div>\n";
                                                            escolaridad += "<div>Estado: <label>" + esc.getAprobacion() + "</label></div>\n";
                                                            escolaridad += "</div>";
                                                        }
                                                    }

                                                }

                                            } else {
                                                //--------------------------------------------------------------------------------------------------------
                                                //ALUMNO NO CURSO MATERIA
                                                //--------------------------------------------------------------------------------------------------------
                                                progreso = "No cursada";
                                            }

                                            escolaridad += "</div>";

                                            //-NOMBRE
                                            out.println("<div>" + materia.getMatNom() + "</div>");

                                            //-PROGRESO
                                            out.println("<div>Progreso: <label>" + progreso + "</label></div>");

                                            out.println(escolaridad);

                                            //--------------------------------------------------------------------------------------------------------
                                            //FIN DIV MATERIA
                                            //--------------------------------------------------------------------------------------------------------
                                            out.println("</div></div>");
                                        }

                                        //--------------------------------------------------------------------------------------------------------
                                        //FINALIZAMOS DIV CONTENEDOR SEMESTRE SI QUEDO ABIERTO
                                        //--------------------------------------------------------------------------------------------------------
                                        if (plan.getLstMateria() != null) {
                                            if (plan.getLstMateria().size() > 0) {
                                                out.println("</div>");
                                            }
                                        }

                                    }

                                    if (est.getInscripcion().getCurso() != null) {
                                        //MOSTRAR TODAS LOS MODULOS DEL PLAN.
                                    }

                                    //--------------------------------------------------------------------------------------------------------
                                    //FIN DIV INSCRIPCION
                                    //--------------------------------------------------------------------------------------------------------
                                    out.println("</div>");

                                }
                            %>
                        </div>
                    </div>
                </div>
            </div>

            <jsp:include page="/masterPage/footer.jsp"/>
        </div>

        <!-- <div name="lines" id="lines">
 
         </div> -->
    </body>
</html>