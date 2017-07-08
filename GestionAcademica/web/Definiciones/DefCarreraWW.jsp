<%-- 
    Document   : PCarrera
    Created on : jun 17, 2017, 11:12:28 p.m.
    Author     : aa
--%>

<%@page import="java.util.List"%>
<%@page import="Logica.LoCarrera"%>
<%@page import="Entidad.Carrera"%>
<%@page import="Utiles.Utilidades"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    Utilidades utilidad = Utilidades.GetInstancia();
    String urlSistema   = utilidad.GetUrlSistema();
    LoCarrera loCar     = LoCarrera.GetInstancia();
        
    List<Carrera> lstCarrera = loCar.obtenerLista();
    
//    String tblCarreraVisible = (lstCarrera.size() > 0 ? "" : "display: none;");
//    String js_redirect  = "window.location.replace('" + urlSistema +  "Definiciones/DefCarrera.jsp');";
%>

<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Definición de Carreras</title>
    <jsp:include page="/masterPage/head.jsp"/>

    <script>
        //----------------------------------------
        //----------------------------------------
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
        <div class="col-md-6 col-md-offset-3">
            <div class="panel panel-default">
                <div class="panel-heading"><h1>Definición de Carreras</h1></div>
                    <div class="panel-body">
                    <form>  
                        <table border= "0" width="100%">
                        <tr>
                            <td>
                                <!-- En "ejemplo" hay que poner el en lace de la pagina Inicio en este caso -->
                                <a id="lnkIni" href="ejemplo"> Inicio </a>
                                >
                                Definición de Carrera
                                
                            </td>
                            <td style="text-align:right">
                                <button type="button" id="BtnIng" class="BtnAlta" onclick= "self.location.href ='<% out.print(urlSistema); %>Definiciones/DefCarrera.jsp?MODO=<% out.print(Enumerado.Modo.INSERT); %>'"></button>
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
                                                  <label for="InputFacultad">Facultad</label>
                                                  <input type="text" class="form-control" id="TxtFacu" placeholder="Facultad">
                                                </div>                                                
                                            </td>
                                            <td class="margin">
                                                <div class="form-group">
                                                  <label for="InputCategoria">Categoría</label>
                                                  <input type="text" class="form-control" id="TxtCate" placeholder="Categoría">
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
                            <div class="panel-heading"><h10>Lista de Carreras</h10></div>
                                <div class="panel-body">
                                    <table id="TblCar" class="table table-bordered">
                                        <tr>
                                            <th></th>
                                            <th></th>
                                            <th>Código</th>
                                            <th>Nombre</th>
                                            <th>Descripción</th>
                                            <th>Facultad</th>
                                            <th>Certificación</th>
                                        </tr>
                                        <%
                                        for(Carrera car : lstCarrera)
                                        {
                                        %>
                                        <tr>
<!--                                            <td style="text-align:center"><button type="button" class="btn_eli" onclick= "self.location.href ='<% out.print(urlSistema); %>Definiciones/DefCarrera.jsp?MODO=<% out.print(Enumerado.Modo.DELETE); %>''&pCarCod='<% out.print(car.getCarCod()); %>"></button></td>
                                            <td style="text-align:center"><button type="button" class="btn_mod" onclick= "self.location.href ='<% out.print(urlSistema); %>Definiciones/DefCarrera.jsp?MODO=<% out.print(Enumerado.Modo.UPDATE); %>''&pCarCod='<% out.print(car.getCarCod()); %>"></button></td>-->
                                            
                                            <td><a href="<% out.print(urlSistema); %>Definiciones/DefCarrera.jsp?MODO=<% out.print(Enumerado.Modo.DELETE); %>&pCarCod=<% out.print(car.getCarCod()); %>" name="btn_eliminar" id="btn_eliminar" >Eliminar</a></td>
                                            <td><a href="<% out.print(urlSistema); %>Definiciones/DefCarrera.jsp?MODO=<% out.print(Enumerado.Modo.UPDATE); %>&pCarCod=<% out.print(car.getCarCod()); %>" name="btn_editar" id="btn_editar" >Editar</a></td>
                                            <td><% out.print(utilidad.NuloToCero(car.getCarCod())); %></td>
                                            <td><% out.print(utilidad.NuloToVacio(car.getCarNom())); %></td>
                                            <td><% out.print(utilidad.NuloToVacio(car.getCarDsc())); %></td>
                                            <td><% out.print(utilidad.NuloToVacio(car.getCarFac())); %></td>
                                            <td><% out.print(utilidad.NuloToVacio(car.getCarCrt())); %></td>
                                        </tr>
                                        <%
                                        }
                                        %>
                                    </table>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div> 
    </body>
</html>