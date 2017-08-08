<%-- 
    Document   : DefPersona
    Created on : 30-jun-2017, 20:43:13
    Author     : alvar
--%>

<%@page import="Utiles.Retorno_MsgObj"%>
<%@page import="Enumerado.Filial"%>
<%@page import="Logica.LoPersona"%>
<%@page import="Entidad.Persona"%>
<%@page import="Utiles.Utilidades"%>
<%@page import="Enumerado.Modo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    LoPersona loPersona = LoPersona.GetInstancia();
    Utilidades utilidad = Utilidades.GetInstancia();
    String urlSistema = (String) session.getAttribute(Enumerado.NombreSesiones.URL_SISTEMA.getValor());

    //----------------------------------------------------------------------------------------------------
    //CONTROL DE ACCESO
    //----------------------------------------------------------------------------------------------------
    String usuario = (String) session.getAttribute(Enumerado.NombreSesiones.USUARIO.getValor());
    Boolean esAdm = (Boolean) session.getAttribute(Enumerado.NombreSesiones.USUARIO_ADM.getValor());
    Boolean esAlu = (Boolean) session.getAttribute(Enumerado.NombreSesiones.USUARIO_ALU.getValor());
    Boolean esDoc = (Boolean) session.getAttribute(Enumerado.NombreSesiones.USUARIO_DOC.getValor());
    Retorno_MsgObj acceso = Logica.Seguridad.GetInstancia().ControlarAcceso(usuario, esAdm, esDoc, esAlu, utilidad.GetPaginaActual(request));

    if (acceso.SurgioError()) {
        response.sendRedirect((String) acceso.getObjeto());
    }

    //----------------------------------------------------------------------------------------------------
    Modo Mode = Modo.valueOf(request.getParameter("MODO"));
    String PerCod = request.getParameter("pPerCod");
    String js_redirect = "window.location.replace('" + urlSistema + "Definiciones/DefPersonaWW.jsp');";

    Persona persona = new Persona();

    if (Mode.equals(Modo.UPDATE) || Mode.equals(Modo.DISPLAY) || Mode.equals(Modo.DELETE)) {
        persona = (Persona) loPersona.obtener(Long.valueOf(PerCod)).getObjeto();
    }

    String CamposActivos = "disabled";

    switch (Mode) {
        case INSERT:
            CamposActivos = "enabled";
            break;
        case DELETE:
            CamposActivos = "disabled";
            break;
        case DISPLAY:
            CamposActivos = "disabled";
            break;
        case UPDATE:
            CamposActivos = "enabled";
            break;
    }

%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gestión Académica - Persona</title>
        <jsp:include page="/masterPage/head.jsp"/>

        <script>
            $(document).ready(function () {
                $('#btn_guardar').click(function (event) {


                    var PerCod = $('#PerCod').val();
                    var PerNom = $('#PerNom').val();
                    var PerApe = $('#PerApe').val();
                    var PerDoc = $('#PerDoc').val();
                    var PerUsrMod = $('#PerUsrMod').val();
                    var PerEsDoc = document.getElementById('PerEsDoc').checked;
                    var PerEsAdm = document.getElementById('PerEsAdm').checked;
                    var PerEsAlu = document.getElementById('PerEsAlu').checked;
                    var PerNroLib = $('#PerNroLib').val();
                    var PerNroEstOrt = $('#PerNroEstOrt').val();
                    var PerFil = $('select[name=PerFil]').val();
                    var PerEml = $('#PerEml').val();
                    var ObjFchMod = $('#ObjFchMod').val();
                    var PerPass = $('#PerPass').val();
                    var PerNotEml = document.getElementById('PerNotEml').checked;
                    var PerNotApp = document.getElementById('PerNotApp').checked;

                    if (PerNom == '')
                    {
                        MostrarMensaje("ERROR", "Completa los datos papa");
                    } else
                    {

                        if ($('#MODO').val() == "INSERT")
                        {

                            // Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
                            $.post('<% out.print(urlSistema); %>ABM_Persona', {
                                pPerCod: PerCod,
                                pPerNom: PerNom,
                                pPerApe: PerApe,
                                pPerDoc: PerDoc,
                                pPerUsrMod: PerUsrMod,
                                pPerEsDoc: PerEsDoc,
                                pPerEsAdm: PerEsAdm,
                                pPerEsAlu: PerEsAlu,
                                pPerNroLib: PerNroLib,
                                pPerNroEstOrt: PerNroEstOrt,
                                pPerFil: PerFil,
                                pPerEml: PerEml,
                                pObjFchMod: ObjFchMod,
                                pPerNotEml: PerNotEml,
                                pPerNotApp: PerNotApp,
                                pPerPass: PerPass,
                                pAction: "INSERTAR"
                            }, function (responseText) {
                                var obj = JSON.parse(responseText);

                                if (obj.tipoMensaje != 'ERROR')
                                {
            <%
                                                                 out.print(js_redirect);
            %>
                                } else
                                {
                                    MostrarMensaje(obj.tipoMensaje, obj.mensaje);
                                }

                            });
                        }


                        if ($('#MODO').val() == "UPDATE")
                        {
                            // Si en vez de por post lo queremos hacer por get, cambiamos el $.post por $.get
                            $.post('<% out.print(urlSistema); %>ABM_Persona', {
                                pPerCod: PerCod,
                                pPerNom: PerNom,
                                pPerApe: PerApe,
                                pPerDoc: PerDoc,
                                pPerUsrMod: PerUsrMod,
                                pPerEsDoc: PerEsDoc,
                                pPerEsAdm: PerEsAdm,
                                pPerEsAlu: PerEsAlu,
                                pPerNroLib: PerNroLib,
                                pPerNroEstOrt: PerNroEstOrt,
                                pPerFil: PerFil,
                                pPerEml: PerEml,
                                pObjFchMod: ObjFchMod,
                                pPerNotEml: PerNotEml,
                                pPerNotApp: PerNotApp,
                                pPerPass: PerPass,
                                pAction: "ACTUALIZAR"
                            }, function (responseText) {
                                var obj = JSON.parse(responseText);

                                if (obj.tipoMensaje != 'ERROR')
                                {
            <%
                                                            out.print(js_redirect);
            %>
                                } else
                                {
                                    MostrarMensaje(obj.tipoMensaje, obj.mensaje);
                                }

                            });
                        }

                        if ($('#MODO').val() == "DELETE")
                        {
                            $.post('<% out.print(urlSistema); %>ABM_Persona', {
                                pPerCod: PerCod,
                                pAction: "ELIMINAR"
                            }, function (responseText) {
                                var obj = JSON.parse(responseText);

                                if (obj.tipoMensaje != 'ERROR')
                                {
            <%
                                                            out.print(js_redirect);
            %>
                                } else
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

                        <div id="tabs" name="tabs" class="contenedor-tabs">
                            <jsp:include page="/Definiciones/DefPersonaTabs.jsp"/>
                        </div>

                        <div class=""> 
                            <div style="text-align: right;"><a href="<% out.print(urlSistema); %>Definiciones/DefPersonaWW.jsp">Regresar</a></div>
                        </div>



                        <div style="display:none" id="datos_ocultos" name="datos_ocultos">
                            <input type="hidden" name="MODO" id="MODO" value="<% out.print(Mode); %>">
                        </div>
                        <form id="frm_Version" name="frm_Version">
                            <div><label>Código:</label><input type="text" class="form-control" id="PerCod" name="PerCod" placeholder="Código" disabled value="<% out.print(utilidad.NuloToVacio(persona.getPerCod())); %>" ></div>
                            <div><label>Nombre:</label><input type="text" class="form-control" id="PerNom" name="PerNom" placeholder="Nombre" <% out.print(CamposActivos); %> value="<% out.print(utilidad.NuloToVacio(persona.getPerNom())); %>" ></div>
                            <div><label>Apellido:</label><input type="text" class="form-control" id="PerApe" name="PerApe" placeholder="Apellido" <% out.print(CamposActivos); %> value="<% out.print(utilidad.NuloToVacio(persona.getPerApe())); %>" ></div>
                            <div><label>Documento:</label><input type="text" class="form-control" id="PerDoc" name="PerDoc" placeholder="Documento" <% out.print(CamposActivos); %> value="<% out.print(utilidad.NuloToVacio(persona.getPerDoc())); %>" ></div>
                            <div><label>Usuario en moodle:</label><input type="text" class="form-control" id="PerUsrMod" name="PerUsrMod" placeholder="Usuario" <% out.print(CamposActivos); %> value="<% out.print(utilidad.NuloToVacio(persona.getPerUsrMod())); %>" ></div>
                            <div><label>Password:</label><input type="password" class="form-control" id="PerPass" name="PerPass"  <% out.print(CamposActivos); %> value="" ></div>

                            <div class="checkbox"><label><input type="checkbox" id="PerEsDoc" name="PerEsDoc"  <% out.print(CamposActivos); %> <% out.print(utilidad.BooleanToChecked(persona.getPerEsDoc())); %> > Es docente</label></div>
                            <div class="checkbox"><label><input type="checkbox"  id="PerEsAdm" name="PerEsAdm" <% out.print(CamposActivos); %> <% out.print(utilidad.BooleanToChecked(persona.getPerEsAdm())); %>> Es administrador</label></div>
                            <div class="checkbox"><label><input type="checkbox"  id="PerEsAlu" name="PerEsAlu"  <% out.print(CamposActivos); %> <% out.print(utilidad.BooleanToChecked(persona.getPerEsAlu())); %>> Es alumno</label></div>

                            <div><label>Número en libra:</label><input type="number" class="form-control" id="PerNroLib" name="PerNroLib" placeholder="Número" <% out.print(CamposActivos); %> value="<% out.print(utilidad.NuloToCero(persona.getPerNroLib())); %>" ></div>
                            <div><label>Número estudiante:</label><input type="number" class="form-control" id="PerNroEstOrt" name="PerNroEstOrt" placeholder="Número" <% out.print(CamposActivos); %> value="<% out.print(utilidad.NuloToCero(persona.getPerNroEstOrt())); %>" ></div>
                            <div>
                                <label>Filial:</label>
                                <select class="form-control" id="PerFil" name="PerFil" <% out.print(CamposActivos); %>>
                                    <%
                                        for (Filial filial : Filial.values()) {
                                            if (filial == persona.getPerFil()) {
                                                //return filial;
                                                out.println("<option selected value='" + filial.getFilial() + "'>" + filial.getFilialNom() + "</option>");
                                            } else {
                                                out.println("<option value='" + filial.getFilial() + "'>" + filial.getFilialNom() + "</option>");
                                            }
                                        }
                                    %>
                                </select>
                            </div>
                            <div><label>Email:</label><input type="email" class="form-control" id="PerEml" name="PerEml" placeholder="Email" <% out.print(CamposActivos); %> value="<% out.print(utilidad.NuloToVacio(persona.getPerEml())); %>" ></div>
                            <div class="checkbox"><label><input type="checkbox"  id="PerNotEml" name="PerNotEml"  <% out.print(CamposActivos); %> <% out.print(utilidad.BooleanToChecked(persona.getPerNotEml())); %> > Notificar por email</label></div>
                            <div class="checkbox"><label><input type="checkbox"  id="PerNotApp" name="PerNotApp"  <% out.print(CamposActivos); %> <% out.print(utilidad.BooleanToChecked(persona.getPerNotApp())); %> > Notificar por aplicación</label></div>

                            <div>
                                <input name="btn_guardar" id="btn_guardar" value="Guardar" class="btn btn-success" type="button" />
                                <input value="Cancelar" class="btn btn-default" type="button" onclick="<% out.print(js_redirect);%>"/>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <jsp:include page="/masterPage/footer.jsp"/>
        </div>

    </body>
</html>
