<%-- 
    Document   : DefPlanEstudioWW
    Created on : jul 7, 2017, 8:10:09 p.m.
    Author     : aa
--%>

<%@page import="Entidad.Carrera"%>
<%@page import="Logica.LoCarrera"%>
<%@page import="Utiles.Retorno_MsgObj"%>
<%@page import="Logica.Seguridad"%>
<%@page import="Enumerado.NombreSesiones"%>
<%@page import="Enumerado.Modo"%>
<%@page import="java.util.List"%>
<%@page import="Entidad.PlanEstudio"%>
<%@page import="Utiles.Utilidades"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    Utilidades utilidad = Utilidades.GetInstancia();
    LoCarrera loCar     = LoCarrera.GetInstancia();
    String urlSistema   = (String) session.getAttribute(NombreSesiones.URL_SISTEMA.getValor());
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
    
    String carrera      = "DefCarreraWW.jsp";
    String CarCod       = request.getParameter("pCarCod");
    
    Carrera car = new Carrera();
    
    Retorno_MsgObj retorno = (Retorno_MsgObj) loCar.obtener(Long.valueOf(CarCod));
    if(!retorno.SurgioErrorObjetoRequerido())
    {
        car = (Carrera) retorno.getObjeto();
    }
    else
    {
        out.print(retorno.getMensaje().toString());
    }
    
//    LoPlanEstudio loPE  = LoPlanEstudio.GetInstancia();
    
//    List<PlanEstudio> lstPlanEstudio    = loPE.obtenerLista();
%>

<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Definición Plan de Estudio</title>
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
        <div class="col-md-8 col-md-offset-1">
            <div class="panel-heading"><h1>Definición Plan de Estudio</h1><hr size="200" style="color: #000000;"/></div>
            
                <table border= "0" width="100%">
                    <tr>
                        <td>
                            <!-- En "ejemplo" hay que poner el en lace de la pagina Inicio en este caso -->
                            <a href=<% out.print(urlSistema); %>> Inicio </a>
                            >
                            <a href=<% out.print(carrera); %>> Carrera </a>
                            >
                            Definición Plan de Estudio

                        </td>
                        <td style="text-align:right">
                            <button type="button" id="BtnIngPla" class="BtnAlta" onclick= "self.location.href ='<% out.print(urlSistema); %>Definiciones/DefPlanEstudio.jsp?MODO=<% out.print(Enumerado.Modo.INSERT); %>&pCarCod=<%out.print(CarCod.toString());%>'"></button>
                        </td>
                    </tr>
                </table>
                <div class="panel panel-default">
                    <div class="panel-heading"><h10>Filtros</h10></div>
                    <div class="panel-body">
                        <table border= "0" width="100%">
                            <tr>
                                <td>
                                    <div class="form-group">
                                        <label for="InputCodigo">Código</label>
                                        <input type="text" class="form-control" id="TxtCod" placeholder="Código">
                                      </div>
                                </td>
                                <td class="margin">
                                    <div class="form-group">
                                      <label for="InputNombre">Nombre</label>
                                      <input type="text" class="form-control" id="TxtNom" placeholder="Nombre">
                                    </div>                                            
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div class="form-group">
                                      <label for="InputDescripción">Descripción</label>
                                      <input type="text" class="form-control" id="TxtDsc" placeholder="Descripción">
                                    </div>                                                
                                </td>
                                <td class="margin">
                                    <div class="form-group">
                                      <label for="InputCreditos">Creditos Necesarios</label>
                                      <input type="text" class="form-control" id="TxtCate" placeholder="Creditos Necesarios">
                                    </div>                                            
                                </td>
                            </tr>
                            <tr>
                                <td>

                                </td>
                                <td style="text-align:right">
                                    <button id="BtnBus" class="btn btn-default">Buscar</button>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>

                <div class="panel panel-default">
                    <div class="panel-heading"><h10>Lista Plan de Estudio</h10></div>
                    <div class="panel-body">
                        <table id="TblCar" class="table table-bordered">
                            <tr>
                                <th></th>
                                <th></th>
                                <th>Código</th>
                                <th>Nombre</th>
                                <th>Descripción</th>
                                <th>Creditos Necesarios</th>
                            </tr>
                            <%
                                for(PlanEstudio PE : car.getPlan())
                                {
                            %>
                            <tr>
                                <td><a href="<% out.print(urlSistema); %>Definiciones/DefPlanEstudio.jsp?MODO=<% out.print(Enumerado.Modo.DELETE); %>&pPlaEstCod=<% out.print(PE.getPlaEstCod()); %>&pCarCod=<% out.print(CarCod.toString()); %>" name="btn_elim_PlaEst" id="btn_elim_PlaEst" >Eliminar</a></td>
                                <td><a href="<% out.print(urlSistema); %>Definiciones/DefPlanEstudio.jsp?MODO=<% out.print(Enumerado.Modo.UPDATE); %>&pPlaEstCod=<% out.print(PE.getPlaEstCod()); %>&pCarCod=<% out.print(CarCod.toString()); %>" name="btn_edit_PlaEst" id="btn_edit_PlaEst" >Editar</a></td>
                                <td><% out.print(utilidad.NuloToCero(PE.getPlaEstCod())); %></td>
                                <td><% out.print(utilidad.NuloToVacio(PE.getPlaEstNom())); %></td>
                                <td><% out.print(utilidad.NuloToVacio(PE.getPlaEstDsc())); %></td>
                                <td><% out.print(utilidad.NuloToVacio(PE.getPlaEstCreNec())); %></td>
                            </tr>
                            <%
                                }
                            %>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
