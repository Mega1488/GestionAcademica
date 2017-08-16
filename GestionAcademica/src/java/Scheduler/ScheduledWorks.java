/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scheduler;

import Logica.LoWS;
import Logica.Notificacion.ManejoNotificacion;
import Logica.Notificacion.NotificacionesInternas;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author alvar
 */

public class ScheduledWorks implements SmartLifecycle{
    
    private Integer diasBefore;

    public Integer getDiasBefore() {
        return diasBefore;
    }

    public void setDiasBefore(Integer diasBefore) {
        this.diasBefore = diasBefore;
    }
    
    

    public ScheduledWorks() {
        
    }
    
    private boolean isRunning = false;
    
    
    public void Tarea_Notificar()
    {
        System.out.println("Notificar. Current time is :: "+ new Date());
        
        ManejoNotificacion notManager = new ManejoNotificacion();
        notManager.EjecutarNotificacionAutomaticamente();
        
        NotificacionesInternas noInt = new NotificacionesInternas();
        noInt.EjecutarNotificacionesInternas();
    }
    
    public void Tarea_NotificarInterno()
    {
        System.out.println("Notificar interno. Current time is :: "+ new Date());
        
        ManejoNotificacion notManager = new ManejoNotificacion();
        notManager.EjecutarNotificacionAutomaticamente();
        
        NotificacionesInternas noInt = new NotificacionesInternas();
        noInt.EjecutarNotificacionesInternas();
    }
    
    public void Tarea_BorrarWSBitacora()
    {
        System.out.println("Borrar WSBitacora. Current time is :: "+ new Date());
        LoWS.GetInstancia().EliminarBitacoraBeforeDate();
    }

    @Override
    public void start() {
        System.err.println("Iniciando scheduler");
        
        this.isRunning = true;
    }
 
    @Override
    public void stop() {
        System.err.println("Deteniendo scheduler");
        isRunning = false;
    }
 
    @Override
    public void stop(final Runnable callback) {
        System.err.println("Deteniendo scheduler callback");
        isRunning = false;
 
        try {
            //Stop listening to the queue.
 
            //Sleeping for 120 seconds so that all threads 
            //get enough time to do their cleanup  
            TimeUnit.SECONDS.sleep(120);
 
            //Shudown complete. Regular shutdown will continue.
            callback.run();
        } catch (final InterruptedException e) {
            //Looks like we got exception while shutting down, 
            //log it or do something with it
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
    }
 
    /**
     * This is the most important method. 
     * Returning Integer.MAX_VALUE only suggests that 
     * we will be the first bean to shutdown.
     */
    @Override
    public int getPhase() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isAutoStartup() {
        return false;
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }
    
}
