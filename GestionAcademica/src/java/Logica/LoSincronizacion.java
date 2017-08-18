/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.Objeto;
import Entidad.Parametro;
import Entidad.Persona;
import Entidad.SincInconsistenciaDatos;
import Entidad.SincRegistroEliminado;
import Entidad.Sincronizacion;
import Entidad.Sincronizacion;
import Entidad.SincronizacionInconsistencia;
import Entidad.TipoEvaluacion;
import Enumerado.EstadoInconsistencia;
import Enumerado.EstadoSincronizacion;
import Enumerado.Objetos;
import Enumerado.TipoMensaje;
import Enumerado.TipoRetorno;
import Interfaz.InABMGenerico;
import Persistencia.PerManejador;
import SDT.SDT_Parameters;
import Utiles.Mensajes;
import Utiles.Retorno_MsgObj;
import Utiles.Utilidades;
import WebServiceClient.SincronizarWSClient;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author alvar
 */
public class LoSincronizacion implements InABMGenerico{

    private static LoSincronizacion instancia;

    private LoSincronizacion() {
    }
    
    public static LoSincronizacion GetInstancia(){
        if (instancia==null)
        {
            instancia   = new LoSincronizacion();
        }

        return instancia;
    }

    @Override
    public Object guardar(Object pObjeto) {
        Sincronizacion sincro = (Sincronizacion) pObjeto;
        PerManejador perManager = new PerManejador();
            
        Retorno_MsgObj retorno = perManager.guardar(sincro);

        if(!retorno.SurgioErrorObjetoRequerido())
        {
            sincro.setSncCod((Long) retorno.getObjeto());
            retorno.setObjeto(sincro);
        }
            
        return retorno; 
    }

    @Override
    public Object actualizar(Object pObjeto) {
        
        Sincronizacion sincro  = (Sincronizacion) pObjeto;
            
        PerManejador perManager = new PerManejador();

        return perManager.actualizar(sincro);
        
    }

    @Override
    public Object eliminar(Object pObjeto) {
        PerManejador perManager = new PerManejador();
        return perManager.eliminar(pObjeto);
    }

    @Override
    public Retorno_MsgObj obtener(Object pObjeto) {
        PerManejador perManager = new PerManejador();
        return perManager.obtener((Long) pObjeto, Sincronizacion.class);        
    }

    @Override
    public Retorno_MsgObj obtenerLista() {
        
        PerManejador perManager = new PerManejador();

        return perManager.obtenerLista("Sincronizacion.findAll", null);
    }
    
    public Retorno_MsgObj obtenerListaByEstado(EstadoSincronizacion estado){
        ArrayList<SDT_Parameters> lstParametros = new ArrayList<>();
        lstParametros.add(new SDT_Parameters(estado, "SncEst"));
        
        PerManejador perManager = new PerManejador();
        
        return perManager.obtenerLista("Sincronizacion.findByEst", lstParametros);
    }
    
    public Boolean existenSincroSinCorregir(){
        Retorno_MsgObj retorno = this.obtenerListaByEstado(EstadoSincronizacion.CON_ERRORES);
        
        if(retorno.getLstObjetos() != null)
        {
            if(retorno.getLstObjetos().size() > 0)
            {
                return true;
            }
        }
        
        return false;
        
    }
      
    //------------------------------------------------------------------------
    //OBJETO
    //------------------------------------------------------------------------
    
    public Object ObjetoGuardar(Object pObjeto) {
        Objeto objeto = (Objeto) pObjeto;
        PerManejador perManager = new PerManejador();
            
        Retorno_MsgObj retorno = perManager.guardar(objeto);

        if(!retorno.SurgioErrorObjetoRequerido())
        {
            objeto.setObjCod((Long) retorno.getObjeto());
            retorno.setObjeto(objeto);
        }
            
        return retorno; 
    }

    public Object ObjetoActualizar(Object pObjeto) {
        
        Objeto objeto  = (Objeto) pObjeto;
            
        PerManejador perManager = new PerManejador();

        return perManager.actualizar(objeto);
        
    }

    public Object ObjetoEliminar(Object pObjeto) {
        PerManejador perManager = new PerManejador();
        return perManager.eliminar(pObjeto);
    }

    public Retorno_MsgObj ObjetoObtener(Object pObjeto) {
        PerManejador perManager = new PerManejador();
        return perManager.obtener((Long) pObjeto, Objeto.class);        
    }

    public Retorno_MsgObj ObjetoObtenerLista() {
        
        PerManejador perManager = new PerManejador();

        return perManager.obtenerLista("Objeto.findAll", null);
    }
    
    public Objeto ObjetoObtenerByNombre(String ObjNom) {
        
        PerManejador perManager = new PerManejador();
        ArrayList<SDT_Parameters> lstParametros = new ArrayList<>();
        lstParametros.add(new SDT_Parameters(ObjNom, "ObjNom"));
        Retorno_MsgObj retorno = perManager.obtenerLista("Objeto.findByObjeto", lstParametros);
        
        Objeto objeto = null;
        
        if(retorno.getLstObjetos() != null)
        {
            if(retorno.getLstObjetos().size() > 0)
            {
                objeto = (Objeto) retorno.getLstObjetos().get(0);
            }
        }
        
        return objeto;
    }
    
    public void CargaInicialObjetos(){
        if(this.ObjetoObtenerByNombre(Objetos.TIPO_EVALUACION.name()) == null)
        {
            this.ObjetoGuardar(new Objeto(Objetos.TIPO_EVALUACION.name()
                    , Objetos.TIPO_EVALUACION.getNamedQuery()
                    , Objetos.TIPO_EVALUACION.getPrimaryKey()));
        }
    }
    
    //------------------------------------------------------------------------
    //SINCRONIZAR
    //------------------------------------------------------------------------
    public void Sincronizar(){
        Parametro param = LoParametro.GetInstancia().obtener();
        
        if(param.getParSisLocal() && param.getParSncAct())
        {
            if(!this.existenSincroSinCorregir())
            {
                Date inicioProceso = new Date();
                
                Sincronizacion sincro = new Sincronizacion();
                sincro.addDetalle(inicioProceso.getTime() + " - Inicio el proceso de sincronización");
        
                Date fechaUltimaSincronizacion  = param.getParFchUltSinc();

                /*
                    Busco registros locales para determinar si debo enviar al sistema online
                */

                Retorno_MsgObj resulSincOnline = this.SincronizarSistemaOnline(this.ObtenerCambios(fechaUltimaSincronizacion));

                if(resulSincOnline.SurgioError())
                {
                    System.err.println("Surgio error al sincronizar con el sistema online");
                    sincro.setSncEst(EstadoSincronizacion.CON_ERRORES);
                    sincro = this.ProcesarInconsistencias(sincro, resulSincOnline);
                    
                }
                else
                {

                    /*
                        Impactar modificaciones en sistema local
                    */

                    Retorno_MsgObj resultado = this.ImpactarCambios(resulSincOnline);
                
                    if(resultado.SurgioError())
                    {
                        sincro.setSncEst(EstadoSincronizacion.CON_ERRORES);
                    }
                    else
                    {
                        sincro.setSncEst(EstadoSincronizacion.CORRECTO);
                    }
                    
                    sincro.addDetalle(resultado.getMensaje().getMensaje());
                    sincro.setSncObjCnt((int) resultado.getObjeto());
                    
                    //Actualizo la fecha de sincronización
                    this.ActualizarFechaSincronizacion(param);
                }
                
                sincro.setSncFch(inicioProceso);
                sincro.setSncDur(this.ObtenerDuracion(inicioProceso));
                sincro.addDetalle(new Date() + " - Fin del proceso");
                
                this.guardar(sincro);
                
            }
        }
        
    }
    
    public Retorno_MsgObj Sincronizar(Retorno_MsgObj cambios){
        Parametro param                 = LoParametro.GetInstancia().obtener();
        Retorno_MsgObj cambiosLocales   = this.ObtenerCambios(param.getParFchUltSinc());
        
        System.err.println("Cambios locales: " + cambiosLocales.getLstObjetos().size());
        
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Iniciando sincronizacion online", TipoMensaje.MENSAJE));
        
        if(this.ExistenCambios(param.getParFchUltSinc()))
        {
            retorno = this.ValidarSincronizacion(cambiosLocales, cambios);
        }
        
        if(!retorno.SurgioError())
        {
            retorno = this.ImpactarCambios(cambios);
            if(!retorno.SurgioError())
            {
                cambiosLocales.setMensaje(retorno.getMensaje());
                retorno = cambiosLocales;
                
                System.err.println("Cambios locales 2: " + retorno.getLstObjetos().size());
            }
        }
        
        return retorno;
    }
    
    private Boolean ExistenCambios(Date fecha){
        
        Retorno_MsgObj retorno = this.ObtenerCambios(fecha);
        
        if(retorno.getLstObjetos() != null)
        {
            if(retorno.getLstObjetos().size() > 0)
            {
                return true;
            }
        }
        
        return false;
    }
    
    private Retorno_MsgObj ObtenerCambios(Date fecha){
        PerManejador perManager = new PerManejador();
        
        ArrayList<SDT_Parameters> lstParametros = new ArrayList<>();
        lstParametros.add(new SDT_Parameters(fecha, "ObjFchMod"));
        
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Obtener cambios", TipoMensaje.MENSAJE));
        retorno.setLstObjetos(new ArrayList<>());
        
        
        //OBTENER ELIMINACIONES
        Retorno_MsgObj Deletes      = new Retorno_MsgObj(new Mensajes(TipoRetorno.ELIMINACION.name(), TipoMensaje.MENSAJE));
        Retorno_MsgObj lstObjeto    = perManager.obtenerLista("SincRegistroELiminado.findModAfter", lstParametros);

        if(lstObjeto.getLstObjetos() != null)
        {
            if(lstObjeto.getLstObjetos().size() > 0)
            {
                Deletes.setLstObjetos(lstObjeto.getLstObjetos());
                retorno.getLstObjetos().add(Deletes);
            }
        }
        
        
        
        //--------------------------------------------------------------------------------------------------------------

        //OBTENER INSERTS Y UPDATES
        Retorno_MsgObj InsertsUpdates = new Retorno_MsgObj(new Mensajes(TipoRetorno.INSERT_UPDATE.name(), TipoMensaje.MENSAJE));
        
        lstObjeto = this.ObjetoObtenerLista();

        if(lstObjeto.getLstObjetos() != null)
        {
            if(lstObjeto.getLstObjetos().size() > 0)
            {
                InsertsUpdates.setLstObjetos(new ArrayList<>());
                
                for(Object obj : lstObjeto.getLstObjetos())
                {
                    Objeto objeto       = (Objeto) obj;
                    
                    /*
                        Llamo a la query por defecto, para obtener la cantidad de registros modificados despues de X fecha
                    */
                    
                    Retorno_MsgObj modObjects = perManager.obtenerLista(objeto.getObjNmdQry() + ".findModAfter", lstParametros);
                    
                    if(modObjects.getLstObjetos() != null)
                    {
                        if(modObjects.getLstObjetos().size() > 0)
                        {
                            Retorno_MsgObj objetoModificado = new Retorno_MsgObj();
                            
                            objetoModificado.setObjeto(obj);
                            objetoModificado.setLstObjetos(modObjects.getLstObjetos());
                            
                            InsertsUpdates.getLstObjetos().add(objetoModificado);
                        }
                    }
                }
                
                retorno.getLstObjetos().add(InsertsUpdates);
            }
        }
        
        return retorno;
    }
    
    private Retorno_MsgObj SincronizarSistemaOnline(Retorno_MsgObj modificaciones){
        
        SincronizarWSClient cliWS = new SincronizarWSClient();
        
        return cliWS.Sincronizar(modificaciones);
    }
    
    private Retorno_MsgObj ImpactarCambios(Retorno_MsgObj cambios){
        
        Integer registrosAfectados = 0;

        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes(new Date() + " - Impactando cambios", TipoMensaje.MENSAJE), registrosAfectados);
        
        if(cambios.getLstObjetos() != null)
        {
            PerManejador perManager = new PerManejador();
            
            if(cambios.getLstObjetos().size() > 0)
            {
                if(cambios.getLstObjetos().size() > 2)
                {
                    retorno.getMensaje().setMensaje(retorno.getMensaje().getMensaje() + "\n" + new Date() + " - Solo se pueden recibir dos elementos, registros y eliminaciones.");
                    retorno.getMensaje().setTipoMensaje(TipoMensaje.ERROR);
                    
                    System.err.println("ERROR: Solo se pueden recibir dos elementos, registros y eliminaciones.");
                }
                else
                {
                    //OBTENGO LISTA MODIFICACIONES Y LISTA DE INGRESOS
                    Retorno_MsgObj Deletes      = new Retorno_MsgObj();
                    Retorno_MsgObj InsertUpdate = new Retorno_MsgObj();
                    
                        
                    for(Object obj : cambios.getLstObjetos())
                    {
                        Retorno_MsgObj auxiliar = (Retorno_MsgObj) obj;
                        if(auxiliar.getMensaje().getMensaje().equals(TipoRetorno.ELIMINACION.name()))
                        {
                            Deletes = auxiliar;
                        }
                        if(auxiliar.getMensaje().getMensaje().equals(TipoRetorno.INSERT_UPDATE.name()))
                        {
                            InsertUpdate = auxiliar;
                        }
                    }
                    
                    
                    //ELIMINACIONES
                    if(Deletes.getLstObjetos() != null)
                    {
                        registrosAfectados += Deletes.getLstObjetos().size();
                        
                        for(Object deleted : Deletes.getLstObjetos())
                        {
                            SincRegistroEliminado objetoEliminado = (SincRegistroEliminado) deleted;

                            this.EliminarRegistro(objetoEliminado);                          
                        }
                    }
                    
                    
                    //MODIFICACIONES E INGRESOS                  
                    if(InsertUpdate.getLstObjetos() != null)
                    {
                        registrosAfectados += InsertUpdate.getLstObjetos().size();
                        
                        for(Object camb : InsertUpdate.getLstObjetos())
                        {
                            Retorno_MsgObj objetoModificado = (Retorno_MsgObj) camb;

                            //Objeto modificado
                            Objeto objMod = (Objeto) objetoModificado.getObjeto();

                            for(Object registro : objetoModificado.getLstObjetos())
                            {
                                if(this.ExisteRegistro(objMod, registro))
                                {
                                    perManager.actualizar(registro);
                                }
                                else
                                {
                                    Long idOriginal = this.ObtenerPrimaryKey(objMod, registro);

                                    Retorno_MsgObj regNuevo = perManager.guardar(registro);

                                    Long idGenerado = this.ObtenerPrimaryKey(objMod, regNuevo);

                                    this.ActualizarPrimaryKeyManualmente(objMod, idGenerado, idOriginal);
                                }
                            }

                        }
                    }
                }
            }
        }
        
        retorno.setObjeto(registrosAfectados);
        
        return retorno;
    }
    
    private Boolean ExisteRegistro(Objeto objMod, Object registro){
        
        Objetos objeto = Objetos.valueOf(objMod.getObjNom());
        
        switch(objeto)
        {
            case TIPO_EVALUACION:
                TipoEvaluacion regCast = (TipoEvaluacion) registro;
                return !LoTipoEvaluacion.GetInstancia().obtener(regCast.getTpoEvlCod()).SurgioErrorObjetoRequerido();
        }
        
        return false;
    }
    
    private Long ObtenerPrimaryKey(Objeto objMod, Object registro){
        
        Objetos objeto = Objetos.valueOf(objMod.getObjNom());
        
        switch(objeto)
        {
            case TIPO_EVALUACION:
                TipoEvaluacion regCast = (TipoEvaluacion) registro;
                return regCast.getTpoEvlCod();
        }
        
        return null;
    }
    
    private void ActualizarPrimaryKeyManualmente(Objeto objMod, Long idOriginal, Long idNuevo){
        String query = "UPDATE " + objMod.getObjNmdQry()
                + " SET " + objMod.getPrimaryKey().getObjCmpNom() + " = " + idNuevo 
                + " WHERE " + objMod.getPrimaryKey().getObjCmpNom() + " = " + idOriginal;
        
        PerManejador perManager = new PerManejador();
        Retorno_MsgObj retorno  = perManager.ejecutarUpdateQuery(query);
        
        if(retorno.SurgioError())
        {
            System.err.println(retorno.getMensaje().toString());
        }
        
    }
    
    private void EliminarRegistro(SincRegistroEliminado objEliminado){
        
        String query = "DELETE FROM " + objEliminado.getObjeto().getObjNmdQry() 
                + " WHERE "+ objEliminado.getObjeto().getPrimaryKey().getObjCmpNom() 
                +" = " + objEliminado.getObjeto().getObjCod();
        
        
        PerManejador perManager = new PerManejador();
        Retorno_MsgObj retorno  = perManager.ejecutarUpdateQuery(query);
        
        if(retorno.SurgioError())
        {
            System.err.println(retorno.getMensaje().toString());
        }
        
    }
    
    private void ActualizarFechaSincronizacion(Parametro param){
        param.setParFchUltSinc(new Date());
        LoParametro.GetInstancia().actualizar(param);
        
        //Actualizar fecha al sistema online
        SincronizarWSClient cliWS = new SincronizarWSClient();
        cliWS.ActualizarFecha(param.getParFchUltSinc());
    }
    
    private Sincronizacion ProcesarInconsistencias(Sincronizacion sincro, Retorno_MsgObj retorno){
        if(retorno.getMensaje().getMensaje().equals(TipoRetorno.INCONSISTENCIA.name()))
        {
            sincro.addDetalle(new Date() + " - Surgio error al sincronizar con el sistema online - Genero inconsistencias que deberan ser corregidas");
            
            if(retorno.getLstObjetos() != null)
            {
                for(Object obj : retorno.getLstObjetos())
                {
                    SincronizacionInconsistencia sncInc = (SincronizacionInconsistencia) obj;
                    sncInc.setSincronizacion(sincro);
                    sincro.getLstInconsistencia().add(sncInc);
                }
            }
        }
        else
        {
            sincro.addDetalle(new Date() + " - " + retorno.getMensaje().toString());
        }
        
        return sincro;
    }
    
    private String ObtenerDuracion(Date inicioProceso){
        Long tiempoMls  =  new Date().getTime() - inicioProceso.getTime();
        String duracion;
        
        if(tiempoMls > 1000)
        {
            Long tiempoS = tiempoMls / 1000;
            
            if(tiempoS > 60)
            {
                Long tiempoM = tiempoS / 60;
                if(tiempoM > 60)
                {
                    Long tiempoH = tiempoM / 60;
                    if(tiempoH > 24)
                    {
                        Long tiempoD = tiempoH / 24;
                        
                        duracion = tiempoD + " días";
                    }
                    else
                    {
                        duracion = tiempoH + " horas";
                    }
                }
                else
                {
                    duracion = tiempoM + " minutos";
                }
            }
            else
            {
                duracion = tiempoS + " segundos";
            }
        }
        else
        {
            duracion = tiempoMls + " milisegundos";
        }
        
        return duracion;
    }
    
    private Retorno_MsgObj ValidarSincronizacion(Retorno_MsgObj cambiosLocales, Retorno_MsgObj cambiosNuevos){
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Importacion valida", TipoMensaje.MENSAJE));
        retorno.setLstObjetos(new ArrayList<>());
        
        if(cambiosLocales.getLstObjetos() != null)
        {
            if(cambiosLocales.getLstObjetos().size() > 0)
            {
                if(cambiosLocales.getLstObjetos().size() > 2)
                {
                    retorno.getMensaje().setMensaje(retorno.getMensaje().getMensaje() + "\n" + new Date() + " - Solo se pueden recibir dos elementos, registros y eliminaciones.");
                    retorno.getMensaje().setTipoMensaje(TipoMensaje.ERROR);
                    
                    System.err.println("ERROR: Solo se pueden recibir dos elementos, registros y eliminaciones.");
                }
                else
                {
                    //OBTENGO LISTA MODIFICACIONES Y LISTA DE INGRESOS
                    Retorno_MsgObj InsertUpdate = new Retorno_MsgObj();
                    
                        
                    for(Object obj : cambiosLocales.getLstObjetos())
                    {
                        Retorno_MsgObj auxiliar = (Retorno_MsgObj) obj;
                        if(auxiliar.getMensaje().getMensaje().equals(TipoRetorno.INSERT_UPDATE.name()))
                        {
                            InsertUpdate = auxiliar;
                        }
                    }
                    
                    //MODIFICACIONES E INGRESOS                  
                    if(InsertUpdate.getLstObjetos() != null)
                    {
                        
                        for(Object camb : InsertUpdate.getLstObjetos())
                        {
                            Retorno_MsgObj objetoModificado = (Retorno_MsgObj) camb;

                            //Objeto modificado
                            Objeto objMod = (Objeto) objetoModificado.getObjeto();

                            for(Object registro : objetoModificado.getLstObjetos())
                            {
                                //VALIDAR
                                Retorno_MsgObj validar = this.ValidarSincronizacionNivelDos(objMod, registro, cambiosNuevos);
                                if(validar.SurgioError())
                                {
                                    if(validar.getMensaje().getMensaje().equals(TipoRetorno.INCONSISTENCIA.name()))
                                    {
                                        //Agrego inconsistencia
                                        retorno.getLstObjetos().add(validar.getObjeto());
                                        retorno.setMensaje(validar.getMensaje());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        return retorno;
    }
    
    private Retorno_MsgObj ValidarSincronizacionNivelDos(Objeto objMod, Object registro, Retorno_MsgObj cambiosNuevos){
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Sin inconsistencia", TipoMensaje.MENSAJE));
        
        if(cambiosNuevos.getLstObjetos() != null)
        {
            if(cambiosNuevos.getLstObjetos().size() > 0)
            {
                if(cambiosNuevos.getLstObjetos().size() > 2)
                {
                    retorno.getMensaje().setMensaje(retorno.getMensaje().getMensaje() + "\n" + new Date() + " - Solo se pueden recibir dos elementos, registros y eliminaciones.");
                    retorno.getMensaje().setTipoMensaje(TipoMensaje.ERROR);
                    
                    System.err.println("ERROR: Solo se pueden recibir dos elementos, registros y eliminaciones.");
                }
                else
                {
                    //OBTENGO LISTA MODIFICACIONES Y LISTA DE INGRESOS
                    Retorno_MsgObj InsertUpdate = new Retorno_MsgObj();
                    
                        
                    for(Object obj : cambiosNuevos.getLstObjetos())
                    {
                        Retorno_MsgObj auxiliar = (Retorno_MsgObj) obj;
                        if(auxiliar.getMensaje().getMensaje().equals(TipoRetorno.INSERT_UPDATE.name()))
                        {
                            InsertUpdate = auxiliar;
                        }
                    }
                    
                    //MODIFICACIONES E INGRESOS                  
                    if(InsertUpdate.getLstObjetos() != null && objMod != null)
                    {
                        
                        for(Object camb : InsertUpdate.getLstObjetos())
                        {
                            Retorno_MsgObj objetoModificado = (Retorno_MsgObj) camb;

                            //Objeto modificado
                            Objeto objMd = (Objeto) objetoModificado.getObjeto();

                            for(Object reg : objetoModificado.getLstObjetos())
                            {
                                if(objMod.getObjNom().equals(objMd.getObjNom()))
                                {
                                    //VALIDAR
                                    if(this.EsInconsistencia(objMd, registro, reg))
                                    {
                                        //ERROR,SIGNIFICA QUE AMBOS MODIFICARON EL MISMO DATO, RETORNA INCONSISTENCIA
                                        retorno.setObjeto(this.ArmarInconsistencia(objMd, registro, reg));
                                        retorno.setMensaje(new Mensajes(TipoRetorno.INCONSISTENCIA.name(), TipoMensaje.ERROR));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }  
        
        return retorno;
    }
    
    private Boolean EsInconsistencia(Objeto objMod, Object objLocal, Object objNuevo){
        Boolean error = false;
        Objetos objeto = Objetos.valueOf(objMod.getObjNom());
        
        switch(objeto)
        {
            case TIPO_EVALUACION:
                TipoEvaluacion regLocal = (TipoEvaluacion) objLocal;
                TipoEvaluacion regNuevo = (TipoEvaluacion) objNuevo;
                error = regLocal.getTpoEvlCod().equals(regNuevo.getTpoEvlCod());
                break;
        }
        
        return error;
    }
    
    private SincronizacionInconsistencia ArmarInconsistencia(Objeto objeto, Object regUno, Object regDos){
        
        SincronizacionInconsistencia inc = new SincronizacionInconsistencia();
        inc.setIncEst(EstadoInconsistencia.CON_ERRORES);

        ArrayList<SincInconsistenciaDatos> lstDatos = new ArrayList<>();
        lstDatos.add(new SincInconsistenciaDatos(inc, objeto, Utilidades.GetInstancia().ObjetoToJson(regUno)));
        lstDatos.add(new SincInconsistenciaDatos(inc, objeto, Utilidades.GetInstancia().ObjetoToJson(regDos)));
        
        inc.setLstDatos(lstDatos);
        
        return inc;
    }
}
