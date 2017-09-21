/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SDT;

/**
 *
 * @author alvar
 */
public class SDT_NotificacionNotification {
    private String body;
    private String title;
    private String icon;
    private String sound;

    public SDT_NotificacionNotification(String body, String title, String icon, String sound) {
        this.body = body;
        this.title = title;
        this.icon = icon;
        this.sound = sound;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    
    
    
}
