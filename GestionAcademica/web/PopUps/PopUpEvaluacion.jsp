<%-- 
    Document   : PopUpEvaluacion
    Created on : 12-jul-2017, 10:55:35
    Author     : alvar
--%>

<%@page import="Entidad.Curso"%>
<%@page import="Enumerado.TipoMensaje"%>
<%@page import="Utiles.Retorno_MsgObj"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Logica.LoCurso"%>
<%@page import="Utiles.Utilidades"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%

    Utilidades utilidad = Utilidades.GetInstancia();
    String urlSistema   = utilidad.GetUrlSistema();
    
    LoCurso loCurso     = LoCurso.GetInstancia();
    
    List<Object> lstCurso = new ArrayList<>();
    
    Retorno_MsgObj retorno = (Retorno_MsgObj) loCurso.obtenerLista();
    if(!retorno.SurgioErrorListaRequerida())
    {
        lstCurso = retorno.getLstObjetos();
    }
    else
    {
        out.print(retorno.getMensaje().toString());
    }
    

%>

<!-- Modal -->
<div class="modal-dialog">
    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Evaluaciones</h4>
      </div>
      <div class="modal-body">
        
          <div class="row">
              <div class="col-lg-2"> <label>Curso:</label> </div>
              <div class="col-lg-3"> <select class="form-control" id="popCurCod" name="popCurCod"></select> </div>
              <div class="col-lg-2"> <label>Módulo:</label> </div>
              <div class="col-lg-3"> <select class="form-control" id="popModCod" name="popModCod"></select></div>
          </div>
          
          <div class="row">
              <div style="text-align: right;">
                <button class="btn btn-default" id="PopEvl_btnBuscar" name="PopEvl_btnBuscar">Buscar</button>
              </div>
          </div>
 
            <div>
                <table class="table">
                            <tr>
                                <th>Código</th>
                                <th>Nombre</th>
                                <th>Descripción</th>
                                <th>Tipo</th>
                                <th>Nota toal</th>

                            </tr>
                            <tbody  id="PopUp_TblEvaluacion" name="PopUp_TblEvaluacion">

                            </tbody>
                        </table>
            </div>

            <div style="display:none">
                <%
                        out.println("<input type='hidden' id='popCursos' name='popCursos' value='"+utilidad.ObjetoToJson(lstCurso)+"'>");
                %>
            </div>
          
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
</div>
    
    
    
    <script type="text/javascript">
        
        $(document).ready(function() {
            
            var cursos = JSON.parse($('#popCursos').val());
            
            $.each(cursos, function (i, objeto) {
                //alert(objeto.curNom);
                
                
                 $('#popCurCod').append($('<option>', { 
                    value: objeto.curCod,
                    text : objeto.curNom 
                }));
                
                if(i == 0)
                {
                    CargarModulo(objeto.curCod);
                }
                
            });
 
            $('#popCurCod').on('change', function() {
                 CargarModulo($('select[name=popCurCod]').val());
              })
              
            function CargarModulo(codigo){
                
                $('#popModCod').empty();
                
                $('#popModCod').append($('<option>', { 
                                text : "" 
                            }));
                            
                
                $.each(cursos, function (e, objeto) {
                    if(objeto.curCod == codigo)
                    {
                        
                        var modulos = objeto.lstModulos;
                        $.each(modulos, function (i, modulo) {
                         
                             $('#popModCod').append($('<option>', { 
                                value: modulo.modCod,
                                text : modulo.modNom 
                            }));

                        });
                        
                        return false;
                    }
                });
            }
            


            $("#PopEvl_btnBuscar").click(function(){
               
                var odd_even    = false;
                var tbl_row     = "";
                var tbl_body    = "";
                
                var CurCod= $('select[name=popCurCod]').val();
                var ModCod= $('select[name=popModCod]').val();
                
                var evaluaciones;
                
                
                $.each(cursos, function (e, objeto) {
                    if(objeto.curCod == CurCod)
                    {
                        
                        if(ModCod != "")
                        {
                            var modulos = objeto.lstModulos;
                            $.each(modulos, function (i, modulo) {

                                if(modulo.modCod == ModCod)
                                {
                                    evaluaciones = modulo.lstEvaluacion;
                                    return false;
                                }
                            });
                        }
                        
                        else
                        {
                            evaluaciones = objeto.lstEvaluacion;
                        }
                        
                         return false;
                    }
                });
                
                
                $.each(evaluaciones, function(f , evaluacion) {
                        
                        tbl_row = "<td> <a href='#' data-codigo='"+evaluacion.evlCod+"' data-nombre='"+evaluacion.evlNom+"' class='PopEvl_Seleccionar'>"+evaluacion.evlCod+" </a> </td>";
                        tbl_row += "<td>"+evaluacion.evlNom+"</td>";
                        tbl_row += "<td>"+evaluacion.evlDsc+"</td>";
                        tbl_row += "<td>"+"</td>";
                        tbl_row += "<td>"+evaluacion.evlNotTot+"</td>";
                        
                        tbl_body += "<tr class=\""+( odd_even ? "odd" : "even")+"\">"+tbl_row+"</tr>";
                        odd_even = !odd_even;            
                    });
                       
                $("#PopUp_TblEvaluacion").html(tbl_body);
                
            });

        
        $(document).on('click', ".PopEvl_Seleccionar", function() {
                
                $('#EvlCod').val($(this).data("codigo"));
                $('#EvlNom').val($(this).data("nombre"));
                
                $(function () {
                    $('#PopUpEvaluacion').modal('toggle');
                 });
                //$("#PopUpEvaluacion").dialog("close");       
        });
        

        });
        </script>
        
        
        
        
       