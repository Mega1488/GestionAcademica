/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SDT;

import java.io.Serializable;
import java.util.ArrayList;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author alvar
 */
public class SDT_ReportColumns implements Serializable{
    
  
        private String title;

        public SDT_ReportColumns(String title) {
            this.title = title;
        }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
        
        
        
    
    

}

/*
 var dataObject = {
                                        columns: [{
                                          title: "NAME"
                                        }, {
                                          title: "COUNTY"
                                        }],
                                        data: [
                                          ["John Doe", "Fresno"],
                                          ["Billy", "Fresno"],
                                          ["Tom", "Kern"],
                                          ["King Smith", "Kings"]
                                        ]
                                      };
*/