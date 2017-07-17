<%-- 
    Document   : DefCursoWW
    Created on : 03-jul-2017, 18:28:52
    Author     : alvar
--%>
<%@page import="Logica.Seguridad"%>
<%@page import="Enumerado.NombreSesiones"%>
<%@page import="Enumerado.TipoMensaje"%>
<%@page import="Utiles.Retorno_MsgObj"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Entidad.Curso"%>
<%@page import="java.util.List"%>
<%@page import="Logica.LoCurso"%>
<%@page import="Utiles.Utilidades"%>
<%

    LoCurso loCurso     = LoCurso.GetInstancia();
    Utilidades utilidad = Utilidades.GetInstancia();
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
    
    List<Object> lstCurso = new ArrayList<>();
    
    Retorno_MsgObj retorno = (Retorno_MsgObj) loCurso.obtenerLista();
    if(retorno.getMensaje().getTipoMensaje() != TipoMensaje.ERROR && retorno.getLstObjetos() != null)
    {
        System.err.println("Lista de objeto: " + retorno.getLstObjetos().size());
        lstCurso = retorno.getLstObjetos();
        System.err.println("Lista de curso: " + lstCurso.size());
    }
    else
    {
        out.print(retorno.getMensaje().toString());
    }
    
    String tblCursoVisible = (lstCurso.size() > 0 ? "" : "display: none;");

%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gestión Académica -  Calendario</title>
        <jsp:include page="/masterPage/head.jsp"/>
    </head>
    <body>
        <div id="cabezal" name="cabezal">
            <jsp:include page="/masterPage/cabezal.jsp"/>
        </div>

        <div style="float:left; width: 10%; height: 100%;">
            <jsp:include page="/masterPage/menu_izquierdo.jsp" />
        </div>

        <div id="contenido" name="contenido" style="float: right; width: 90%;">
            <h1>Calendario</h1>
            
            <div id="tabs" name="tabs">
                <jsp:include page="/Definiciones/DefCalendarioWWTabs.jsp"/>
            </div>
            
        
             
    </body>
</html>
