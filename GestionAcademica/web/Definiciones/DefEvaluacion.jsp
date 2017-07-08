<%-- 
    Document   : DefEvaluacion
    Created on : 06-jul-2017, 20:19:49
    Author     : alvar
--%>

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
    LoParametro loParam    = LoParametro.GetInstancia();
    Parametro param        = loParam.obtener(1);
    LoCurso loCurso        = LoCurso.GetInstancia();
    Utilidades utilidad    = Utilidades.GetInstancia();
    String urlSistema      = utilidad.GetUrlSistema();
    
    Modo Mode                 = Modo.valueOf(request.getParameter("MODO"));
    String Relacion           = request.getParameter("pRelacion");
    String CurEvlCurCod       = request.getParameter("pCurEvlCurCod");
    String ModEvlCurCod       = request.getParameter("pModEvlCurCod");
    String ModEvlModCod       = request.getParameter("pModEvlModCod");
    String MatEvlCarCod       = request.getParameter("pModCod");
    String MatEvlPlaEstCod    = request.getParameter("pModCod");
    String MatEvlMatCod       = request.getParameter("pModCod");
    String EvlCod             = request.getParameter("pEvlCod");
    
    String js_redirect        = "";
    Evaluacion evaluacion     = new Evaluacion();
    
    List<TipoEvaluacion> lstTpoEvaluacion = LoTipoEvaluacion.GetInstancia().obtenerLista();
    
    if(Relacion.equals("CURSO"))
    {
        js_redirect  = "window.location.replace('" + urlSistema +  "Definiciones/DefCursoEvaluacionSWW.jsp?MODO=UPDATE&pCurCod=" + CurEvlCurCod + "');";
        
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
        js_redirect  = "window.location.replace('" + urlSistema +  "Definiciones/DefModuloEvaluacionSWW.jsp?MODO=UPDATE&pCurCod=" + ModEvlCurCod + "&pModCod=" + ModEvlModCod + "');";
        
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
        //js_redirect  = "window.location.replace('" + urlSistema +  "Definiciones/DefMateriaEvaluacionSWW.jsp?MODO=UPDATE&pCurCod=" + CurEvlCurCod + "');";
    }
    
    
    
        
    
    
    
    
    String CamposActivos = "disabled";
    
    switch(Mode)
    {
        case INSERT: CamposActivos = "enabled";
            break;
        case DELETE: CamposActivos = "disabled";
            break;
        case DISPLAY: CamposActivos = "disabled";
            break;
        case UPDATE: CamposActivos = "enabled";
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
                                
                                MostrarCargando(true);
                                
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
                                        MostrarCargando(false);
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
                                                         MostrarCargando(false);

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
                                                    MostrarCargando(false);

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
                                                    MostrarCargando(false);

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
        <div id="cabezal" name="cabezal">
            <jsp:include page="/masterPage/cabezal.jsp"/>
        </div>

        <div style="float:left; width: 10%; height: 100%;">
            <jsp:include page="/masterPage/menu_izquierdo.jsp" />
        </div>

        <div id="contenido" name="contenido" style="float: right; width: 90%;">
            <div id="tabs" name="tabs">
                <jsp:include page="/Definiciones/DefCursoTabs.jsp"/>
            </div>
            
            <h1>Evaluación</h1>
            
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
                                            for( TipoEvaluacion tpoEval : lstTpoEvaluacion){
                                            
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
        
                    <div>
                        <input name="btn_guardar" id="btn_guardar" value="Guardar" type="button" />
                    </div>
            </form>
        </div>
    </body>
</html>