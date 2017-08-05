/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Notificacion;

import Enumerado.TipoMensaje;
import SDT.SDT_NotificacionEnvio;
import Utiles.Mensajes;
import Utiles.Retorno_MsgObj;

/**
 *
 * @author alvar
 */
public class NotificacionEmail {

    public NotificacionEmail() {
    }
    
    public Retorno_MsgObj Notificar(SDT_NotificacionEnvio notificacion)
    {
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Notificar Email - Notificar", TipoMensaje.MENSAJE));
        
        return retorno;
    }
    
}
