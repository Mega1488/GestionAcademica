/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.Notificacion;
import Entidad.NotificacionBandeja;
import Entidad.NotificacionDestinatario;
import Enumerado.BandejaEstado;
import Enumerado.BandejaTipo;
import Enumerado.ObtenerDestinatario;
import Enumerado.TipoEnvio;
import Interfaz.InABMGenerico;
import Logica.Notificacion.AsyncNotificar;
import Logica.Notificacion.ManejoNotificacion;
import Persistencia.PerBandeja;
import Utiles.Retorno_MsgObj;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alvar
 */
public class LoBandeja implements InABMGenerico{

    private static LoBandeja instancia;
    private final PerBandeja perBandeja;

    private LoBandeja() {
        perBandeja  = new PerBandeja();
    }
    
    public static LoBandeja GetInstancia(){
        if (instancia==null)
        {
            instancia   = new LoBandeja();
        }

        return instancia;
    }
    

    @Override
    public Object guardar(Object pObjeto) {
        return perBandeja.guardar(pObjeto);
    }

    @Override
    public Object actualizar(Object pObjeto) {
        return perBandeja.actualizar(pObjeto);
    }

    @Override
    public Object eliminar(Object pObjeto) {
        return perBandeja.eliminar(pObjeto);
    }

    @Override
    public Retorno_MsgObj obtener(Object pObjeto) {
        return perBandeja.obtener(pObjeto);
    }

    @Override
    public Retorno_MsgObj obtenerLista() {
        return perBandeja.obtenerLista();
    }
    
    public Retorno_MsgObj obtenerListaByTipoEstado(Long PerCod, BandejaTipo NotBanTpo, BandejaEstado NotBanEst) {
        return perBandeja.obtenerListaByTipoEstado(PerCod, NotBanTpo, NotBanEst);
    }
    
    public void NotificarPendientes(Long PerCod){
        Retorno_MsgObj retorno = this.obtenerListaByTipoEstado(PerCod, BandejaTipo.APP, BandejaEstado.SIN_LEER);
        
        if(!retorno.SurgioError())
        {
            for(Object objeto : retorno.getLstObjetos())
            {
                NotificacionBandeja bandeja = (NotificacionBandeja) objeto;
                
                Notificacion notificacion = new Notificacion();
                
                notificacion.setNotApp(Boolean.TRUE);
                notificacion.setNotAsu(bandeja.getNotBanAsu());
                notificacion.setNotCon(bandeja.getNotBanMen());
                notificacion.setNotTpoEnv(TipoEnvio.COMUN);
                notificacion.setNotObtDest(ObtenerDestinatario.UNICA_VEZ);
                
                NotificacionDestinatario destinatario = new NotificacionDestinatario();
                destinatario.setNotificacion(notificacion);
                destinatario.setPersona(bandeja.getDestinatario());
                
                notificacion.setLstDestinatario(new ArrayList<NotificacionDestinatario>());
                
                notificacion.getLstDestinatario().add(destinatario);
                
                ManejoNotificacion manager = new ManejoNotificacion();
                manager.EjecutarNotificacion(notificacion);
                  
                  
               bandeja.setNotBanEst(BandejaEstado.LEIDA);
               this.actualizar(bandeja);
                  
            }
        }
        
    }
    
   
}
