<%-- 
    Document   : DefEvaluacion
    Created on : 06-jul-2017, 20:19:49
    Author     : alvar
--%>

<%@page import="Logica.LoCarrera"%>
<%@page import="Entidad.Materia"%>
<%@page import="Entidad.PlanEstudio"%>
<%@page import="Entidad.Carrera"%>
<%@page import="Entidad.Modulo"%>
<%@page import="Enumerado.TipoMensaje"%>
<%@page import="Utiles.Retorno_MsgObj"%>
<%@page import="Entidad.TipoEvaluacion"%>
<%@page import="java.util.List"%>
<%@page import="Logica.LoTipoEvaluacion"%>
<%@page import="Entidad.Evaluacion"%>
<%@page import="Moodle.MoodleCategory"%>
<%@page import="Entidad.Parametro"%>
<%@page import="Logica.LoParametro"%>
<%@page import="Logica.LoCategoria"%>
<%@page import="Entidad.Curso"%>
<%@page import="Logica.LoCurso"%>
<%@page import="Utiles.Utilidades"%>
<%@page import="Enumerado.Modo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    LoCurso loCurso         = LoCurso.GetInstancia();
    LoCarrera loCarrera     = LoCarrera.GetInstancia();
    Utilidades utilidad     = Utilidades.GetInstancia();
    String urlSistema       = (String) session.getAttribute(Enumerado.NombreSesiones.URL_SISTEMA.getValor());
    
    //----------------------------------------------------------------------------------------------------
    //CONTROL DE ACCESO
    //----------------------------------------------------------------------------------------------------
    
    String  usuario = (String) session.getAttribute(Enumerado.NombreSesiones.USUARIO.getValor());
    Boolean esAdm   = (Boolean) session.getAttribute(Enumerado.NombreSesiones.USUARIO_ADM.getValor());
    Boolean esAlu   = (Boolean) session.getAttribute(Enumerado.NombreSesiones.USUARIO_ALU.getValor());
    Boolean esDoc   = (Boolean) session.getAttribute(Enumerado.NombreSesiones.USUARIO_DOC.getValor());
    Retorno_MsgObj acceso = Logica.Seguridad.GetInstancia().ControlarAcceso(usuario, esAdm, esDoc, esAlu, utilidad.GetPaginaActual(request));
    
    if(acceso.SurgioError()) response.sendRedirect((String) acceso.getObjeto());
            
    //----------------------------------------------------------------------------------------------------
    
    Modo Mode                 = Modo.valueOf(request.getParameter("MODO"));
    String Relacion           = request.getParameter("pRelacion");
    String CurEvlCurCod       = request.getParameter("pCurEvlCurCod");
    String ModEvlCurCod       = request.getParameter("pModEvlCurCod");
    String ModEvlModCod       = request.getParameter("pModEvlModCod");
    String MatEvlCarCod       = request.getParameter("pCarCod");
    String MatEvlPlaEstCod    = request.getParameter("pPlaEstCod");
    String MatEvlMatCod       = request.getParameter("pMatCod");
    String EvlCod             = request.getParameter("pEvlCod");
    
    
    Evaluacion evaluacion     = new Evaluacion();
    
    List<Object> lstTpoEvaluacion = LoTipoEvaluacion.GetInstancia().obtenerLista().getLstObjetos();
    
    String urlRetorno   = "";
    String boton        = "";
    
    if(Relacion.equals("CURSO"))
    {
        urlRetorno  = urlSistema +  "Definiciones/DefCursoEvaluacionSWW.jsp?MODO=UPDATE&pCurCod=" + CurEvlCurCod;
        
        Curso curso     = new Curso();
        Retorno_MsgObj retorno = (Retorno_MsgObj) loCurso.obtener(Long.valueOf(CurEvlCurCod));
        if(retorno.getMensaje().getTipoMensaje() != TipoMensaje.ERROR)
        {
            curso = (Curso) retorno.getObjeto();
        }
        else
        {
            out.print(retorno.getMensaje().toString());
        }
        
        if(Mode.equals(Modo.UPDATE) || Mode.equals(Modo.DISPLAY) || Mode.equals(Modo.DELETE))
        {
            evaluacion = curso.getEvaluacionById(Long.valueOf(EvlCod));
        }

    }
    if(Relacion.equals("MODULO"))
    {
        urlRetorno  = urlSistema +  "Definiciones/DefModuloEvaluacionSWW.jsp?MODO=UPDATE&pCurCod=" + ModEvlCurCod + "&pModCod=" + ModEvlModCod;
        
        Curso curso     = new Curso();
        Modulo modulo   = new Modulo();
        Retorno_MsgObj retorno = (Retorno_MsgObj) loCurso.obtener(Long.valueOf(ModEvlCurCod));
        if(retorno.getMensaje().getTipoMensaje() != TipoMensaje.ERROR)
        {
            curso = (Curso) retorno.getObjeto();
            modulo = curso.getModuloById(Long.valueOf(ModEvlModCod));
        }
        else
        {
            out.print(retorno.getMensaje().toString());
        }
        
        if(Mode.equals(Modo.UPDATE) || Mode.equals(Modo.DISPLAY) || Mode.equals(Modo.DELETE))
        {
            evaluacion = modulo.getEvaluacionById(Long.valueOf(EvlCod));
        }
    }
    if(Relacion.equals("MATERIA"))
    {   
        urlRetorno  = urlSistema +  "Definiciones/DefMateriaEvaluacionSWW.jsp?MODO=DISPLAY&pPlaEstCod=" + MatEvlPlaEstCod + "&pCarCod=" + MatEvlCarCod + "&pMatCod=" + MatEvlMatCod;
        
        Carrera car         = new Carrera();
        PlanEstudio plan    = new PlanEstudio();
        Materia mat         = new Materia();
        
        Retorno_MsgObj retorno = (Retorno_MsgObj) loCarrera.obtener(Long.valueOf(MatEvlCarCod));
        if(retorno.getMensaje().getTipoMensaje() != TipoMensaje.ERROR)
        {
            car = (Carrera) retorno.getObjeto();
            plan = car.getPlanEstudioById(Long.valueOf(MatEvlPlaEstCod));
            mat = plan.getMateriaById(Long.valueOf(MatEvlMatCod));
        }
        else
        {
            out.print(retorno.getMensaje().toString());
        }
        
        if(Mode.equals(Modo.UPDATE) || Mode.equals(Modo.DISPLAY) || Mode.equals(Modo.DELETE))
        {
            evaluacion = mat.getEvaluacionById(Long.valueOf(EvlCod));
        }
    }
    
    String js_redirect      = "window.location.replace('" + urlRetorno + "');";;
    String CamposActivos    = "disabled";
    
    switch(Mode)
    {
        case INSERT: 
            CamposActivos = "enabled";
            boton           = "<input name='btn_guardar' id='btn_guardar' value='Guardar' type='button' class='btn btn-success' />";
            break;
        case DELETE: 
            CamposActivos = "disabled";
            boton           = "<input href='#' value='Eliminar' class='btn btn-danger' type='button' data-toggle='modal' data-target='#PopUpElimEvaluacion'/>";
            break;
        case DISPLAY: 
            CamposActivos = "disabled";
            boton           = "<input name='btn_guardar' id='btn_guardar' value='Guardar' type='button' class='btn btn-success' />";
            break;
        case UPDATE: 
            CamposActivos = "enabled";
            boton           = "<input name='btn_guardar' id='btn_guardar' value='Guardar' type='button' class='btn btn-success' />";
            break;
    }
    
    
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gestión Académica - Evaluación</title>
        <jsp:include page="/masterPage/head.jsp"/>
        
        <script>
                $(document).ready(function() {
                    $('#btn_guardar').click(function(event) {
                                
                                var	EvlCod	= $('#EvlCod').val();
                                var	MatEvlCarCod	= $('#MatEvlCarCod').val();
                                var	MatEvlPlaEstCod	= $('#MatEvlPlaEstCod').val();
                                var	MatEvlMatCod	= $('#MatEvlMatCod').val();
                                var	CurEvlCurCod	= $('#CurEvlCurCod').val();
                                var	ModEvlCurCod	= $('#ModEvlCurCod').val();
                                var	ModEvlModCod	= $('#ModEvlModCod').val();
                                var	EvlNom	= $('#EvlNom').val();
                                var	EvlDsc	= $('#EvlDsc').val();
                                var	EvlNotTot	= $('#EvlNotTot').val();
                                var	TpoEvlCod	= $('select[name=TpoEvlCod]').val();
                                
                                if(EvlNom == '')
                                    {
                                        MostrarMensaje("ERROR", "Completa los datos papa");
                                    }
                                    else
                                    {
                                        
                                        if($('#MODO').val() == "INSERT")
                                         {

                                                     // Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
                                                     $.post('<% out.print(urlSistema); %>ABM_Evaluacion', {
                                                            pEvlCod	:	EvlCod,
                                                            pMatEvlCarCod	:	MatEvlCarCod,
                                                            pMatEvlPlaEstCod	:	MatEvlPlaEstCod,
                                                            pMatEvlMatCod	:	MatEvlMatCod,
                                                            pCurEvlCurCod	:	CurEvlCurCod,
                                                            pModEvlCurCod	:	ModEvlCurCod,
                                                            pModEvlModCod	:	ModEvlModCod,
                                                            pEvlNom	:	EvlNom,
                                                            pEvlDsc	:	EvlDsc,
                                                            pEvlNotTot	:	EvlNotTot,
                                                            pTpoEvlCod	:	TpoEvlCod,
                                                            pAction          : "INSERTAR"
                                                     }, function(responseText) {
                                                         var obj = JSON.parse(responseText);
                                                         
                                                         if(obj.tipoMensaje != 'ERROR')
                                                         {
                                                             <%
                                                                 out.print(js_redirect);
                                                             %>     
                                                         }
                                                         else
                                                         {
                                                             MostrarMensaje(obj.tipoMensaje, obj.mensaje);
                                                         }

                                                     });
                                            }
                                         

                                            if($('#MODO').val() == "UPDATE")
                                            {
                                                // Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
                                                   $.post('<% out.print(urlSistema); %>ABM_Evaluacion', {

                                                        pEvlCod	:	EvlCod,
                                                        pMatEvlCarCod	:	MatEvlCarCod,
                                                        pMatEvlPlaEstCod	:	MatEvlPlaEstCod,
                                                        pMatEvlMatCod	:	MatEvlMatCod,
                                                        pCurEvlCurCod	:	CurEvlCurCod,
                                                        pModEvlCurCod	:	ModEvlCurCod,
                                                        pModEvlModCod	:	ModEvlModCod,
                                                        pEvlNom	:	EvlNom,
                                                        pEvlDsc	:	EvlDsc,
                                                        pEvlNotTot	:	EvlNotTot,
                                                        pTpoEvlCod	:	TpoEvlCod,
                                                        pAction          : "ACTUALIZAR"
                                                }, function(responseText) {
                                                    var obj = JSON.parse(responseText);
                                                    
                                                    if(obj.tipoMensaje != 'ERROR')
                                                    {
                                                        <%
                                                            out.print(js_redirect);
                                                        %>     
                                                    }
                                                    else
                                                    {
                                                        MostrarMensaje(obj.tipoMensaje, obj.mensaje);
                                                    }

                                                });
                                            }

                                            if($('#MODO').val() == "DELETE")
                                            {
                                                $.post('<% out.print(urlSistema); %>ABM_Evaluacion', {
                                                                
                                                        pEvlCod	:	EvlCod,
                                                        pMatEvlCarCod	:	MatEvlCarCod,
                                                        pMatEvlPlaEstCod	:	MatEvlPlaEstCod,
                                                        pMatEvlMatCod	:	MatEvlMatCod,
                                                        pCurEvlCurCod	:	CurEvlCurCod,
                                                        pModEvlCurCod	:	ModEvlCurCod,
                                                        pModEvlModCod	:	ModEvlModCod,
                                                        pEvlNom	:	EvlNom,
                                                        pEvlDsc	:	EvlDsc,
                                                        pEvlNotTot	:	EvlNotTot,
                                                        pTpoEvlCod	:	TpoEvlCod, 
                                                        pAction          : "ELIMINAR"
                                                }, function(responseText) {
                                                    var obj = JSON.parse(responseText);
                                                    
                                                    if(obj.tipoMensaje != 'ERROR')
                                                    {
                                                        <%
                                                            out.print(js_redirect);
                                                        %>     
                                                    }
                                                    else
                                                    {
                                                        MostrarMensaje(obj.tipoMensaje, obj.mensaje);
                                                    }

                                                });
                                            }
                                    }
                        });
                
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
                            <p>Evaluación</p>
                        </div>  
                        
                        <div class=""> 
                            <div class="" style="text-align: right;"><a href="<% out.print(urlRetorno); %>">Regresar</a></div>
                        </div>
                        
                        <div style="display:none" id="datos_ocultos" name="datos_ocultos">
                            <input type="hidden" name="MODO" id="MODO" value="<% out.print(Mode); %>">
                            <input type="hidden" name="MatEvlCarCod" id="MatEvlCarCod" value="<% out.print(MatEvlCarCod); %>">
                            <input type="hidden" name="MatEvlPlaEstCod" id="MatEvlPlaEstCod" value="<% out.print(MatEvlPlaEstCod); %>">
                            <input type="hidden" name="MatEvlMatCod" id="MatEvlMatCod" value="<% out.print(MatEvlMatCod); %>">
                            <input type="hidden" name="CurEvlCurCod" id="CurEvlCurCod" value="<% out.print(CurEvlCurCod); %>">
                            <input type="hidden" name="ModEvlCurCod" id="ModEvlCurCod" value="<% out.print(ModEvlCurCod); %>">
                            <input type="hidden" name="ModEvlModCod" id="ModEvlModCod" value="<% out.print(ModEvlModCod); %>">

                        </div>
                        <form id="frm_objeto" name="frm_objeto">

                            <div><label>EvlCod</label><input type="text" class="form-control" id="EvlCod" name="EvlCod" placeholder="EvlCod" disabled value="<% out.print( utilidad.NuloToVacio(evaluacion.getEvlCod())); %>" ></div>
                            <div><label>EvlNom</label><input type="text" class="form-control" id="EvlNom" name="EvlNom" placeholder="EvlNom" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio(evaluacion.getEvlNom())); %>" ></div>
                            <div><label>EvlDsc</label><input type="text" class="form-control" id="EvlDsc" name="EvlDsc" placeholder="EvlDsc" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio(evaluacion.getEvlDsc())); %>" ></div>
                            <div><label>EvlNotTot</label><input type="number" class="form-control" id="EvlNotTot" name="EvlNotTot" placeholder="EvlNotTot" <% out.print(CamposActivos); %> value="<% out.print( utilidad.NuloToVacio(evaluacion.getEvlNotTot())); %>" ></div>

                                   <div>
                                            <label>Tipo Evaluacion</label>
                                            <select class="form-control" id="TpoEvlCod" name="TpoEvlCod" <% out.print(CamposActivos); %>>
                                                <%
                                                    for( Object tpoEvalOb : lstTpoEvaluacion){

                                                        TipoEvaluacion tpoEval = (TipoEvaluacion) tpoEvalOb;

                                                        if(evaluacion.getTpoEvl() == null)
                                                        {
                                                            out.println("<option value='" + tpoEval.getTpoEvlCod() + "'>" + tpoEval.getTpoEvlNom() + "</option>");
                                                        }
                                                        else
                                                        {

                                                            if(tpoEval.getTpoEvlCod() == evaluacion.getTpoEvl().getTpoEvlCod())
                                                            {
                                                                //return filial;
                                                                out.println("<option selected value='" + tpoEval.getTpoEvlCod() + "'>" + tpoEval.getTpoEvlNom() + "</option>");
                                                            }
                                                            else
                                                            {
                                                                out.println("<option value='" + tpoEval.getTpoEvlCod() + "'>" + tpoEval.getTpoEvlNom() + "</option>");
                                                            }
                                                        }
                                                    }
                                                %>
                                            </select>
                                    </div>
                            <br>
                            <div>
                                <%out.print(boton);%>
                                <input value="Cancelar" class="btn btn-default" type="button" onclick="<% out.print(js_redirect); %> "/>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
                            
        <!--Popup Confirmar Eliminación-->
        <div id="PopUpElimEvaluacion" class="modal fade" role="dialog">
        <!-- Modal -->
            <div class="modal-dialog modal-lg">
                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Eliminar Evaluación</h4>
                    </div>
                    <div class="modal-body">
                        <div>
                            <h4>¿Seguro que quiere eliminar la Evaluación: <% out.print( utilidad.NuloToVacio(evaluacion.getEvlNom())); %> del Estudio: <% out.print(evaluacion.getEstudioNombre()); %>?</h4>
<!--                            <h4>Se eliminará tambien las Evaluaciones que contenga</h4>-->
                        </div>
                    </div>
                    <div class="modal-footer">
                      <input name="btn_guardar" id="btn_guardar" value="Confirmar" type="button" class="btn btn-success"/>
                      <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                    </div>
                </div>
            </div>
        </div>
                            
    </body>
</html>