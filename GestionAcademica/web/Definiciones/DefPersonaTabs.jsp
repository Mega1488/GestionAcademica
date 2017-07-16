<%-- 
    Document   : DefPersonaTabs
    Created on : 30-jun-2017, 20:44:43
    Author     : alvar
--%>

<%@page import="Enumerado.NombreSesiones"%>
<%@page import="Entidad.Persona"%>
<%@page import="Logica.LoPersona"%>
<%@page import="Enumerado.Modo"%>
<%@page import="Utiles.Utilidades"%>
<%

    LoPersona loPersona = LoPersona.GetInstancia();
    String urlSistema   = (String) session.getAttribute(NombreSesiones.URL_SISTEMA.getValor());
   
    Modo Mode           = Modo.valueOf(request.getParameter("MODO"));
    String PerCod       = request.getParameter("pPerCod");
    
    boolean estudiosVisible     = false;
    boolean escolaridadVisible  = false;
    
    if(!Mode.equals(Mode.INSERT))
    {   
        Persona persona     = new Persona();

        persona = (Persona) loPersona.obtener(Long.valueOf(PerCod)).getObjeto();
        
        estudiosVisible     = (persona.getPerEsAlu() || persona.getPerEsDoc());
        escolaridadVisible  = (persona.getPerEsAlu());
            
    }
    
    
%>

<% 
    out.println("<div class='div_tabs'><a href='" + urlSistema + "Definiciones/DefPersona.jsp?MODO=" + Mode + "&pPerCod=" + PerCod + "'>Persona</a></div>");
    
    if(estudiosVisible)
    {
        String estudio ="<div class='div_tabs'><a href='" + urlSistema + "Definiciones/DefPersonaEstudioSWW.jsp?MODO=" + Mode + "&pPerCod=" + PerCod + "'>Estudios</a></div>";
        out.println(estudio);
    }
    
    if(escolaridadVisible)
    {
        String estudio = "<div class='div_tabs'><a href='" + urlSistema + "Definiciones/DefPersonaEscolaridadSWW.jsp?MODO=" + Mode + "&pPerCod=" + PerCod + "'>Escolaridad</a></div>";
        out.println(estudio);
    }
%>