/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Notificacion;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.context.SmartLifecycle;

/**
 *
 * @author alvar
 */

public class SchNotificar implements SmartLifecycle{

    
    
    public SchNotificar() {
        
    }
    
    private boolean isRunning = false;
    
    
//    @Scheduled(fixedDelay = 30000)
    //@Scheduled(cron="*/50 * * * * ?")
    //@Scheduled(fixedRate = 30000)
 //   @Scheduled(cron = "${cronExpression}")
    public void Tarea_Notificar()
    {
        //instanciaExiste = true;
        System.out.println("Method executed at every 30 seconds. Current time is :: "+ new Date());
        System.err.println("Notificando automaticamente");
        
        
        ManejoNotificacion notManager = new ManejoNotificacion();
        notManager.EjecutarNotificacionAutomaticamente();
        
        System.err.println("Notificaciones de sistema");
        NotificacionesInternas noInt = new NotificacionesInternas();
        noInt.EjecutarNotificacionesInternas();
        
        /*
        AsyncNotificar xthread = null;
        //Long milliseconds = 10000; // 10 seconds
          try {
            xthread = new AsyncNotificar(null, TipoNotificacion.AUTOMATICA);
            xthread.start();
          //  xthread.join(milliseconds);
          } catch (Exception ex) {
              
              Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, "[InterfacesAgent] Error" + ex);
          } finally {
            if (xthread != null && xthread.isAlive()) {
              Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, "[InterfacesAgent] Interrupting" );
              xthread.interrupt();
            }
          }
          */
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
