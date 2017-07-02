/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.Parametro;
import Entidad.Persona;
import Entidad.TipoEvaluacion;
import Entidad.Version;
import Enumerado.Constantes;
import Enumerado.Filial;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author alvar
 */
public class LoIniciar {

    private final LoParametro loParam = LoParametro.GetInstancia();
    private final LoVersion loVersion = LoVersion.GetInstancia();
    private Version version;
        
        
    
    public LoIniciar() {
        version     = loVersion.obtener(1);
    }
    
    public void Iniciar(HttpServletRequest request){
        if(version == null)
        {
            this.CargarVersion();
        }
        
        if(!version.getSisCrgDat())
        {
            this.CargarDatosIniciales(request);
        }
        
        if(loParam.obtener(1).getParUtlMdl())
        {
            this.SincronizarConMoodle();
        }
    }
    
    private void CargarVersion(){
        version = new Version();
        version.setSisCrgDat(Boolean.FALSE);
        version.setSisVer(Constantes.VERSION.getValor());
        
        loVersion.guardar(version);
    }
    
    private void CargarDatosIniciales(HttpServletRequest request){
        
        CargarTipoEvaluacion();
        CargarParametros();
        CargarUrlSistema(request);
        CargarUsuarioAdministrador();
        
        version.setSisCrgDat(Boolean.TRUE);
        loVersion.actualizar(version);

    }
    
    private void CargarTipoEvaluacion(){
        LoTipoEvaluacion lTpoEval = LoTipoEvaluacion.GetInstancia();
        if(lTpoEval.obtenerLista().size() < 4)
        {
            TipoEvaluacion tpoEval = new TipoEvaluacion();

            tpoEval.setTpoEvlNom("Parcial");
            tpoEval.setTpoEvlExm(Boolean.FALSE);
            tpoEval.setTpoEvlInsAut(Boolean.TRUE);
            tpoEval.setTpoEvlCod(lTpoEval.guardar(tpoEval));
            
            tpoEval = new TipoEvaluacion();
            tpoEval.setTpoEvlNom("Obligatorio");
            tpoEval.setTpoEvlExm(Boolean.FALSE);
            tpoEval.setTpoEvlInsAut(Boolean.TRUE);
            tpoEval.setTpoEvlCod(lTpoEval.guardar(tpoEval));

            tpoEval = new TipoEvaluacion();
            tpoEval.setTpoEvlNom("Examen");
            tpoEval.setTpoEvlExm(Boolean.TRUE);
            tpoEval.setTpoEvlInsAut(Boolean.FALSE);
            tpoEval.setTpoEvlCod(lTpoEval.guardar(tpoEval));
            
            tpoEval = new TipoEvaluacion();
            tpoEval.setTpoEvlNom("Trabajo");
            tpoEval.setTpoEvlExm(Boolean.FALSE);
            tpoEval.setTpoEvlInsAut(Boolean.TRUE);
            tpoEval.setTpoEvlCod(lTpoEval.guardar(tpoEval));

        }
        
   }
    
    private void CargarParametros(){
        
        Parametro parametro = loParam.obtener(1);
        
        if(parametro == null)
        {
            parametro = new Parametro();
            parametro.setParDiaEvlPrv(7);
            parametro.setParFchUltSinc(new Date());
            parametro.setParMdlTkn("ce19d614e5a749b22d89d010a5396249");
            parametro.setParSisLocal(Boolean.FALSE);
            parametro.setParSncAct(Boolean.FALSE);
            parametro.setParUtlMdl(Boolean.FALSE);
            parametro.setParTieIna(12);
            parametro.setParUrlMdl("http://192.168.0.106");
            parametro.setParUrlSrvSnc("");
        
            loParam.guardar(parametro);
        }
        
    }
    
    private void CargarUrlSistema(HttpServletRequest request){
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
    
    private void SincronizarConMoodle(){
        LoPersona persona = LoPersona.GetInstancia();
        persona.SincronizarUsuariosMoodleSistema();
    }    
    
    private void CargarUsuarioAdministrador(){
        LoPersona loPersona   = LoPersona.GetInstancia();
        Persona persona     = new Persona();
        persona.setPerCod(1);
        persona = loPersona.obtener(persona);
        
        if(persona == null)
        {
            persona     = new Persona();

            persona.setPerApe("Administrador");
            persona.setPerCntIntLgn(0);
            persona.setPerEml("administrador@administrador.com");
            persona.setPerEsAdm(Boolean.TRUE);
            persona.setPerEsAlu(Boolean.FALSE);
            persona.setPerEsDoc(Boolean.FALSE);
            persona.setPerFil(Filial.COLONIA);
            persona.setPerNom("Administrador");
            persona.setPerNotApp(Boolean.TRUE);
            persona.setPerNotEml(Boolean.TRUE);
            persona.setPerUsrMod("sga_admin");
            persona.setPerPass(Seguridad.GetInstancia().cryptWithMD5("admin"));
            
            loPersona.guardar(persona);
        }
    }
}
