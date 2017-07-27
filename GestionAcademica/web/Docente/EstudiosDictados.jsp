<%-- 
    Document   : EvalPendientes
    Created on : jul 23, 2017, 3:43:29 p.m.
    Author     : aa
--%>

<%@page import="Entidad.Evaluacion"%>
<%@page import="Entidad.PeriodoEstudio"%>
<%@page import="Entidad.PeriodoEstudioDocente"%>
<%@page import="Logica.LoPeriodo"%>
<%@page import="Entidad.Periodo"%>
<%@page import="java.time.Period"%>
<%@page import="SDT.SDT_PersonaEstudio"%>
<%@page import="Entidad.Persona"%>
<%@page import="Logica.LoPersona"%>
<%@page import="Enumerado.TipoMensaje"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Logica.Seguridad"%>
<%@page import="Utiles.Retorno_MsgObj"%>
<%@page import="Utiles.Utilidades"%>
<%@page import="Enumerado.NombreSesiones"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    LoPersona lopersona         = LoPersona.GetInstancia();
    LoPeriodo loPer             = LoPeriodo.GetInstancia();
    Utilidades utilidad         = Utilidades.GetInstancia();
    String urlSistema           = (String) session.getAttribute(NombreSesiones.URL_SISTEMA.getValor());
    
    //----------------------------------------------------------------------------------------------------
    //CONTROL DE ACCESO
    //----------------------------------------------------------------------------------------------------
    
    String  usuario = (String) session.getAttribute(NombreSesiones.USUARIO.getValor());
    Boolean esAdm   = (Boolean) session.getAttribute(NombreSesiones.USUARIO_ADM.getValor());
    Boolean esAlu   = (Boolean) session.getAttribute(NombreSesiones.USUARIO_ALU.getValor());
    Boolean esDoc   = (Boolean) session.getAttribute(NombreSesiones.USUARIO_DOC.getValor());
    Retorno_MsgObj acceso = Seguridad.GetInstancia().ControlarAcceso(usuario, esAdm, esDoc, esAlu, utilidad.GetPaginaActual(request));
    
    if(acceso.SurgioError()) response.sendRedirect((String) acceso.getObjeto());
            
    //----------------------------------------------------------------------------------------------------
    Persona persona             = new Persona();
    Periodo per                 = new Periodo();

    List<Object> lstPer       = new ArrayList<>();
    List<PeriodoEstudio> lstObjeto     = new ArrayList<>();
    
    Retorno_MsgObj retPersona   = lopersona.obtenerByMdlUsr(usuario);
    persona                     = (Persona) retPersona.getObjeto();
    
    Retorno_MsgObj retorno      = loPer.obtenerLista();
    
    if(!retorno.SurgioError() && !retPersona.SurgioErrorObjetoRequerido())
    {
        lstPer = retorno.getLstObjetos();
        for(Object obj : lstPer)
        {
            per = (Periodo) obj;
            for(PeriodoEstudio periodoEstudio : per.getLstEstudio())
            {
                if(periodoEstudio.getExisteDocente(persona.getPerCod()))
                {           
                    lstObjeto.add(periodoEstudio);
                }
            }
        }
    }
    else
    {
        if (retorno.SurgioError())
        {
            out.print(retorno.getMensaje().toString());
        }
        else
        {
            out.print(retPersona.getMensaje().toString());
        }
    }
    
    String tblVisible = (lstObjeto.size() > 0 ? "" : "display: none;");


%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gestión Académica - Estudios | Docente</title>
        <jsp:include page="/masterPage/head.jsp"/>
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
                
                        <div class=""> 
                            <div class="" style="text-align: right;"><a href="<% out.print(urlSistema); %>">Regresar</a></div>
                        </div>
                        
                        <table style=' <% out.print(tblVisible); %>' class='table table-hover'>
                            <thead>
                                <tr>
                                    <th>Código</th>
                                    <th>Nombre</th>
                                    <th>Tipo de Estudio</th>
                                    <th>Alumnos</th>
                                    <th>Docentes</th>
                                    <th></th>
                                    <th></th>
                                </tr>
                            </thead>

                            <tbody>
                            <% for(PeriodoEstudio perEstudio : lstObjeto)
                            {
                                
                            %>
                            <tr>
                                <td><%out.print( utilidad.NuloToVacio(perEstudio.getPeriEstCod())); %> </td>
                                <td><%out.print( utilidad.NuloToVacio(perEstudio.getEstudioNombre())); %> </td>
                                <td><%out.print( utilidad.NuloToVacio(perEstudio.getEstudioTipo()));%></td>
                                <td><% out.print( utilidad.NuloToVacio(perEstudio.getCantidadAlumnos())); %> </td>
                                <td><% out.print( utilidad.NuloToVacio((perEstudio.getCantidadDocente())));%> </td>
                                <td><a href="<% out.print(urlSistema); %>Docente/EstudioDocumentos.jsp?MODO=<% out.print(Enumerado.Modo.UPDATE); %>&pPeriEstCod=<% out.print(perEstudio.getPeriEstCod()); %>" name="btn_edit_doc" id="btn_edit_doc" title="Documentos" class="glyphicon glyphicon-file"/></td>
                                <!--Mejorar el boton de abajo-->
                                <td><a href="<% out.print(urlSistema); %>Definiciones/EvalPendientes.jsp?MODO=<% out.print(Enumerado.Modo.UPDATE); %>&pPeriEstCod=<% out.print(perEstudio.getPeriEstCod()); %>" name="btn_edit_doc" id="btn_edit_doc" title="Evaluar" class="glyphicon glyphicon-paste"/></td>
                            </tr>
                            <%
                            }
                            %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>                             
    </body>
</html>