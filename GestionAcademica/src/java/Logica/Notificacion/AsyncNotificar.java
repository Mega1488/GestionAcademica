/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Notificacion;

import Entidad.Notificacion;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alvar
 */
public class AsyncNotificar extends Thread {
        /** The command agent to execute. */
        private final Notificacion not;
 
        /**
         * Constructor
         * 
         * @param pNot - The agent to execute.
         */
        public AsyncNotificar(Notificacion pNot) {
            this.not = pNot;
        }
 
        /**
         * Run the thread.
         */
        @Override
        public void run() {
            try {
                
                ManejoNotificacion notManager = new ManejoNotificacion();
        
                notManager.EjecutarNotificacion(not);
            } catch (Exception e) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            }
        }
    }