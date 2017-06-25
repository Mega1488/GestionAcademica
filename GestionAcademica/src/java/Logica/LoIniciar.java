/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.Curso;
import Entidad.Modulo;
import Entidad.Parametro;
import Enumerado.TipoPeriodo;
import Utiles.Utilidades;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author alvar
 */
public class LoIniciar {

    private Utilidades utilidades;
    private LoParametro loParam = LoParametro.GetInstancia();
        
    
    public LoIniciar() {
        utilidades = Utilidades.GetInstancia();
    }
    
    public void Iniciar(HttpServletRequest request){
        //Validar si se debe hacer carga inicial
        //boolean cargarDatos = this.ValidarVersion();
        
        boolean cargarDatos = true;
        if (cargarDatos)
        {
            this.CargarDatosIniciales(request);
        }
    }
    
    private void CargarDatosIniciales(HttpServletRequest request){
        
       // CargarTipoEvaluacion();
       // CargarCurso();
       // CargarModulo();
        
        CargarParametros();
        CargarUrlSistema(request);
        ObtenerUsuarios();
        

    }
    
  //  private void CargarTipoEvaluacion(){
  //      LoTipoEvaluacion lTpoEval = LoTipoEvaluacion.GetInstancia();
        
  //      TipoEvaluacion tpoEval = new TipoEvaluacion();
/*
        tpoEval.setTpoEvlNom("Parcial");
        tpoEval.setObjFchMod(new Date());
        tpoEval.setTpoEvlExm(Boolean.FALSE);
        tpoEval.setTpoEvlInsAut(Boolean.FALSE);

        tpoEval.setTpoEvlCod(lTpoEval.guardar(tpoEval));
        utilidades.MostrarMensajeConsola(this.getClass().getSimpleName(), tpoEval.toString());
        
        
        tpoEval = new TipoEvaluacion();

        tpoEval.setTpoEvlNom("Obligatorio");
        tpoEval.setObjFchMod(new Date());
        tpoEval.setTpoEvlExm(Boolean.FALSE);
        tpoEval.setTpoEvlInsAut(Boolean.FALSE);

        tpoEval.setTpoEvlCod(lTpoEval.guardar(tpoEval));
        utilidades.MostrarMensajeConsola(this.getClass().getSimpleName(), tpoEval.toString());
        
        
        tpoEval = new TipoEvaluacion();

        tpoEval.setTpoEvlNom("Examen");
        tpoEval.setObjFchMod(new Date());
        tpoEval.setTpoEvlExm(Boolean.TRUE);
        tpoEval.setTpoEvlInsAut(Boolean.FALSE);

        tpoEval.setTpoEvlCod(lTpoEval.guardar(tpoEval));
        utilidades.MostrarMensajeConsola(this.getClass().getSimpleName(), tpoEval.toString());
        
        tpoEval = lTpoEval.obtener(2);

        tpoEval.setTpoEvlNom(tpoEval.getTpoEvlNom() + " --- ");

        lTpoEval.actualizar(tpoEval);
        utilidades.MostrarMensajeConsola(this.getClass().getSimpleName(), tpoEval.toString());
        
        
        tpoEval = lTpoEval.obtener(1);

        lTpoEval.eliminar(tpoEval);
        utilidades.MostrarMensajeConsola(this.getClass().getSimpleName(), tpoEval.toString());
  */      
   // }
    
    private void CargarCurso(){
        LoCurso lCurso = LoCurso.GetInstancia();
       
        Curso curso = new Curso();
        
        curso.setCurCatCod(1);
        curso.setCurCrt("CTC");
        curso.setCurDsc("Este es un curso creado dinamicamente para testeo");
        curso.setCurFac("Ingenieria");
        curso.setCurNom("Soporte IT");
        
        lCurso.guardar(curso);
        
    }
    
    private void CargarModulo(){
        LoCurso lCurso = LoCurso.GetInstancia();
        LoModulo lModulo = LoModulo.GetInstancia();
        
        Curso curso = lCurso.obtener(1);
        
        Modulo modulo = new Modulo();
                
        //modulo.setCurCod(1);
        modulo.setCurso(curso);
        modulo.setModCntHor(Double.MIN_NORMAL);
        modulo.setModDsc("tyest");
        modulo.setModNom("aaaa");
        modulo.setModPerVal(Double.MIN_NORMAL);
        modulo.setModTpoPer(TipoPeriodo.MODULAR);
        
        lModulo.guardar(modulo);
        
        
    }
    
    private void CargarParametros(){
        
        Parametro parametro = loParam.obtener(1);
        
        if(parametro.getParCod() == null)
        {
            parametro.setParDiaEvlPrv(7);
            parametro.setParFchUltSinc(new Date());
            parametro.setParMdlTkn("ce19d614e5a749b22d89d010a5396249");
            parametro.setParSisLocal(Boolean.FALSE);
            parametro.setParSncAct(Boolean.FALSE);
            parametro.setParTieIna(12);
            parametro.setParUrlMdl("http://192.168.0.106");
            parametro.setParUrlSrvSnc("");
        
            loParam.guardar(parametro);
        }
        
    }
    
    private void CargarUrlSistema(HttpServletRequest request)
    {
        String urlSistema = "";

        try
        {
            URL reconstructedURL = new URL(request.getScheme(),
                                       request.getServerName(),
                                       request.getServerPort(),
                                       request.getRequestURI());
            urlSistema = reconstructedURL.toString();
        }
        catch(MalformedURLException ex)
        {
            ex.printStackTrace();
        }
        
        Parametro param = loParam.obtener(1);
        param.setParUrlSis(urlSistema);
        loParam.actualizar(param);
        
    }
    
    private void ObtenerUsuarios(){
        LoPersona persona = LoPersona.GetInstancia();
        
        persona.SincronizarUsuariosMoodleSistema();
        
        
        //LoConsumirServicioMdl lmd = LoConsumirServicioMdl.GetInstancia();
        //lmd.ProbarServicio();
        
        /*
       // persona.IniciarSesion("admin", "este_es_un_token");
       
       LoParametro loParam = LoParametro.GetInstancia();
       Parametro param = loParam.obtener(1);
       
       MoodleRestUser mdlRestUsr = new MoodleRestUser();
       
       //MoodleRestUser.getUsers(criteria)
       
       
       Criteria criteria = new Criteria();
       
       criteria.setKey("name");
       criteria.setValue("");
       
       Criteria[] lstCriteria   = new Criteria[1];
       lstCriteria[0]           = criteria;
       
       
        try {
            MoodleUser[] lstUser = mdlRestUsr.__getUsers(param.getParUrlMdl() + Constantes.URL_FOLDER_SERVICIO_MDL.getValor(), param.getParMdlTkn(), lstCriteria);
            
                        
            //MoodleUser[] lstUser = MoodleRestUser.getUsers(lstCriteria);
            
            System.err.println("Cantidad con coso nuevo: " + lstUser.length);

        } catch (MoodleRestException ex) {
            Logger.getLogger(LoIniciar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MoodleUserRoleException ex) {
            Logger.getLogger(LoIniciar.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
       */
       
       
    }
    
    
    
}
