/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utiles;

/**
 *
 * @author alvar
 */
public class Utilidades {
    private static Utilidades instancia;

    private Utilidades() {
    }
    
    public static Utilidades GetInstancia(){
        if (instancia == null)
        {
            instancia = new Utilidades();
        }
        
        return instancia;
    }
    
    public void MostrarMensajeConsola(String TAG, String Msg){
        System.err.println(TAG + " ---> " + Msg);
    }
    
}
