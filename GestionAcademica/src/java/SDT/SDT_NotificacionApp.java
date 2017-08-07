/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SDT;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author alvar
 */

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class SDT_NotificacionApp implements Serializable{
    private Long multicast_id;
    private Integer success;
    private Integer failure;
    private Integer canonical_ids;
    private List<SDT_NotificacionAppResultado> results;
    
    
    
    
 ///   private 

          //  {"multicast_id":5110542168779441622,"success":1,"failure":0,"canonical_ids":0,"results":[{"message_id":"0:1502069772556830%6593fe6f6593fe6f"}]}

    public SDT_NotificacionApp() {
    }

    public Long getMulticast_id() {
        return multicast_id;
    }

    public void setMulticast_id(Long multicast_id) {
        this.multicast_id = multicast_id;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Integer getFailure() {
        return failure;
    }

    public void setFailure(Integer failure) {
        this.failure = failure;
    }

    public Integer getCanonical_ids() {
        return canonical_ids;
    }

    public void setCanonical_ids(Integer canonical_ids) {
        this.canonical_ids = canonical_ids;
    }

    public List<SDT_NotificacionAppResultado> getResults() {
        return results;
    }

    public void setResults(List<SDT_NotificacionAppResultado> results) {
        this.results = results;
    }
    
    
}
