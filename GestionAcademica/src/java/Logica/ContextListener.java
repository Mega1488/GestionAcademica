/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Logica.Notificacion.SchNotificar;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author alvar
 */
public class ContextListener implements ServletContextListener {

    ConfigurableApplicationContext applicationContext = null;
    SchNotificar scheduler = null;
    
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
       
        
//        if(applicationContext == null)
//        {
//            applicationContext  = new ClassPathXmlApplicationContext("sga_config.xml");
//        }
//        
//        scheduler           = applicationContext.getBean(SchNotificar.class);
//
//        String estado = "Scheduler no iniciado";
//        
//        if(applicationContext != null)
//        {
//            if(scheduler!= null)
//            {
//                estado = "Scheduler : " + scheduler.isRunning();
//            }
//        }
//        
//        System.err.println("--------------------------------------------------------------");
//        System.err.println("Contexto iniciado: " + estado);
//        System.err.println("--------------------------------------------------------------");

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
        
//         String estado = "No scheduler";
//        
//        if(applicationContext != null)
//        {
//           applicationContext.close();
//            
//            estado = "Cerro: " + scheduler.isRunning();
//        }
//        
//        System.err.println("--------------------------------------------------------------");
//        System.err.println("Contexto detenido: " + estado);
//        System.err.println("--------------------------------------------------------------");
    }
    
}
