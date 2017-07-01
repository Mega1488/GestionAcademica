<%-- 
    Document   : DefPersonaWW
    Created on : 30-jun-2017, 20:44:29
    Author     : alvar
--%>

<%@page import="Entidad.Persona"%>
<%@page import="java.util.List"%>
<%@page import="Utiles.Utilidades"%>
<%@page import="Logica.LoPersona"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%

    LoPersona loPersona = LoPersona.GetInstancia();
    Utilidades utilidad = Utilidades.GetInstancia();
    String urlSistema   = utilidad.GetUrlSistema();
    
    List<Persona> lstPersonas = loPersona.obtenerLista();
    
    System.err.println("Tamaño: " + lstPersonas.size());
    
    String tblPersonaVisible = (lstPersonas.size() > 0 ? "" : "display: none;");

%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gestión Académica - Personas</title>
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
            <h1>Personas</h1>
            
            <div>
                <a href="<% out.print(urlSistema); %>Definiciones/DefPersona.jsp?MODO=<% out.print(Enumerado.Modo.INSERT); %>">Ingresar</a>
            </div>
            
            <div>
                <table style=' <% out.print(tblPersonaVisible); %>'>
                    <tr>
                        <th></th>
                        <th></th>
                        <th>Código</th>
                        <th>Nombre</th>
                        <th>Apellido</th>
                        <th>Email</th>
                        <th>Filial</th>
                        <th>Número en libra</th>
                        <th>Docente</th>
                        <th>Alumno</th>
                        <th>Administrador</th>
                    </tr>
                <% for(Persona persona : lstPersonas)
                    {
                     
                %>
                    <tr>
                        <td><a href="<% out.print(urlSistema); %>Definiciones/DefPersona.jsp?MODO=<% out.print(Enumerado.Modo.DELETE); %>&pPerCod=<% out.print(persona.getPerCod()); %>" name="btn_eliminar" id="btn_eliminar" >Eliminar</a></td>
                        <td><a href="<% out.print(urlSistema); %>Definiciones/DefPersona.jsp?MODO=<% out.print(Enumerado.Modo.UPDATE); %>&pPerCod=<% out.print(persona.getPerCod()); %>" name="btn_editar" id="btn_editar" >Editar</a></td>
                        <td><% out.print( utilidad.NuloToCero(persona.getPerCod())); %> </td>
                        <td><% out.print( utilidad.NuloToVacio(persona.getPerNom())); %></td>
                        <td><% out.print( utilidad.NuloToVacio(persona.getPerApe())); %></td>
                        <td><% out.print( utilidad.NuloToVacio(persona.getPerEml())); %></td>
                        <td><% out.print( utilidad.NuloToVacio(persona.getPerFil().getFilialNom())); %></td>
                        <td><% out.print( utilidad.NuloToCero(persona.getPerNroLib())); %></td>
                        <td><% out.print( utilidad.BooleanToSiNo(persona.getPerEsDoc())); %></td>
                        <td><% out.print( utilidad.BooleanToSiNo(persona.getPerEsAlu())); %></td>
                        <td><% out.print( utilidad.BooleanToSiNo(persona.getPerEsAdm())); %></td>
                    </tr>
                        <%
                }
                %>
            </table>
            </div>
            
        </div>
    </body>
</html>