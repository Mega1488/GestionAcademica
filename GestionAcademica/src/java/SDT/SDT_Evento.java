/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SDT;

import Utiles.JSonDateyyyyMMddSerializer;
import java.io.Serializable;
import java.util.Date;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author alvar
 */

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class SDT_Evento implements Serializable{
    /*
    @Documentacion https://fullcalendar.io/docs/event_data/Event_Object/
    */
    private Long id;
    private String title;
    private Boolean allDay;
    private Date start;
    private Date end;
    private Boolean editable;
    private Boolean startEditable;
    private Boolean durationEditable;
    private Boolean resourceEditable;
    private String description;
    
    public SDT_Evento() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getAllDay() {
        return allDay;
    }

    public void setAllDay(Boolean allDay) {
        this.allDay = allDay;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    @JsonSerialize(using=JSonDateyyyyMMddSerializer.class)
    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

  
    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public Boolean getStartEditable() {
        return startEditable;
    }

    public void setStartEditable(Boolean startEditable) {
        this.startEditable = startEditable;
    }

    public Boolean getDurationEditable() {
        return durationEditable;
    }

    public void setDurationEditable(Boolean durationEditable) {
        this.durationEditable = durationEditable;
    }

    public Boolean getResourceEditable() {
        return resourceEditable;
    }

    public void setResourceEditable(Boolean resourceEditable) {
        this.resourceEditable = resourceEditable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
    
}
