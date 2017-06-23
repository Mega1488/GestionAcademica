/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.Curso;
import Entidad.Modulo;
import Entidad.Parametro;
import Entidad.TipoEvaluacion;
import Enumerado.Constantes;
import Enumerado.TipoPeriodo;
import Moodle.Criteria;
import Moodle.MoodleCallRestWebService;
import Moodle.MoodleRestException;
import Moodle.MoodleRestUser;
import Moodle.MoodleUser;
import Moodle.MoodleUserRoleException;
import Utiles.Utilidades;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alvar
 */
public class LoIniciar {

    private Utilidades utilidades;
    
    public LoIniciar() {
        utilidades = Utilidades.GetInstancia();
    }
    
    public void Iniciar(){
        //Validar si se debe hacer carga inicial
        //boolean cargarDatos = this.ValidarVersion();
        
        boolean cargarDatos = true;
        if (cargarDatos)
        {
            this.CargarDatosIniciales();
        }
    }
    
    private void CargarDatosIniciales(){
        
       // CargarTipoEvaluacion();
       // CargarCurso();
       // CargarModulo();
        
        //CargarParametros();
        
        ObtenerUsuarios();
        

    }
    
    private void CargarTipoEvaluacion(){
        LoTipoEvaluacion lTpoEval = LoTipoEvaluacion.GetInstancia();
        
        TipoEvaluacion tpoEval = new TipoEvaluacion();
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
    }
    
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
        Parametro parametro = new Parametro();
        
        parametro.setParDiaEvlPrv(7);
        parametro.setParFchUltSinc(new Date());
        parametro.setParMdlTkn("ce19d614e5a749b22d89d010a5396249");
        parametro.setParSisLocal(Boolean.FALSE);
        parametro.setParSncAct(Boolean.FALSE);
        parametro.setParTieIna(12);
        parametro.setParUrlMdl("http://192.168.0.106");
        parametro.setParUrlSrvSnc("");
        
        LoParametro loParam = LoParametro.GetInstancia();
        loParam.guardar(parametro);
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
