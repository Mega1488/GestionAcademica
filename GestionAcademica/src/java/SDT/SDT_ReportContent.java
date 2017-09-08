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
public class SDT_ReportContent implements Serializable{
    
    private ArrayList<SDT_ReportColumns> columns;
    private ArrayList<ArrayList<String>> data;

    public SDT_ReportContent() {
    }

    public ArrayList<SDT_ReportColumns> getColumns() {
        if(columns == null) columns = new ArrayList<>();
        return columns;
    }

    public void setColumns(ArrayList<SDT_ReportColumns> columns) {
        this.columns = columns;
    }

    public ArrayList<ArrayList<String>> getData() {
        if(data == null) data=new ArrayList<>();
        return data;
    }

    public void setData(ArrayList<ArrayList<String>> data) {
        this.data = data;
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