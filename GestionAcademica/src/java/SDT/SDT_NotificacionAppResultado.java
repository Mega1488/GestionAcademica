/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SDT;

import java.io.Serializable;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author alvar
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class SDT_NotificacionAppResultado implements Serializable{
    private String message_id;
    private String error;

    public SDT_NotificacionAppResultado() {
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    
    
    
    
}
