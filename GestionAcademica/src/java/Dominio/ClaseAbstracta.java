/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dominio;

import Enumerado.Objetos;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Column;
import javax.persistence.Id;

/**
 *
 * @author alvar
 */
public abstract class ClaseAbstracta {
    
    private final SimpleDateFormat dtFrmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    
    public String GetNamePrimaryKey(){
        for (Field field : this.getClass().getDeclaredFields()) 
        {
        
            if(fieldIsPrimaryKey(field))
            {
                return field.getName();
            }
        }

        return null;
    }
    
    public Long GetPrimaryKey(){
        for (Field field : this.getClass().getDeclaredFields()) 
        {
        
            if(fieldIsPrimaryKey(field))
            {
                if(field.getType().equals(Long.class))
                {
                    try {
                        field.setAccessible(true);
                        if(field.get(this) != null)
                        {
                            return (Long) field.get(this);
                        }
                    } 
                    catch (IllegalArgumentException | IllegalAccessException ex) {
                            Logger.getLogger(ClaseAbstracta.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

        return null;
    }
    
    public String getInsertQuery(){
        String insert = "INSERT INTO " + Objetos.fromQueryName(this.getClass().getSimpleName().toUpperCase()).name() + "(";//) VALUES()";
      
        
        String columns  = null;
        String values   = null;
        for (Field field : this.getClass().getDeclaredFields()) 
        {
        
            if(fieldIsColumn(field))
            {
                if(columns == null)
                {
                    columns = field.getName();
                }
                else
                {
                    columns  += ", " + field.getName();
                }
                
                if(values == null)
                {
                    values = "" + getParsedValue(field);
                }
                else
                {
                    values  += ", " + getParsedValue(field);
                }
            }
       }
        
        
        insert += columns +") VALUES(" + values + ")";
        
        return insert;
    }
    
    public String getUpdateQuery(){
        String update = "UPDATE " + Objetos.fromQueryName(this.getClass().getSimpleName().toUpperCase()).getNamedQuery() + " SET ";
        
        
        
        String sets   = null;

        for (Field field : this.getClass().getDeclaredFields()) 
        {
        
            if(fieldIsColumn(field) && !fieldIsPrimaryKey(field))
            {
                if(sets == null)
                {
                    sets = field.getName() + " = " + this.getParsedValue(field);
                }
                else
                {
                    sets  += ", " + field.getName() + " = " + this.getParsedValue(field);
                }
            }
       }
        
        
        update += sets + " WHERE " + this.GetNamePrimaryKey() + " = " + this.GetPrimaryKey();
        
        return update;
    }
    
    private Boolean fieldIsColumn(Field campo){
        Annotation[] lstAnot = campo.getAnnotations();
        for(Annotation anot : lstAnot)
        {
           if(anot.annotationType().equals(Column.class)){
               return true;
           }
        }

        return false;
        
    }
    
    private Boolean fieldIsPrimaryKey(Field campo){
        Annotation[] lstAnot = campo.getAnnotations();
        for(Annotation anot : lstAnot)
        {
           if(anot.annotationType().equals(Id.class)){
               return true;
           }
        }

        return false;
        
    }
    
    private String getParsedValue(Field campo){
        String value = null;

        campo.setAccessible(true);

        try{
            if(campo.get(this) != null)
            {
                
                if(campo.getType().equals(String.class))
                {
                    value = "'" + campo.get(this) + "'";
                }
                else
                {
                    if(campo.getType().equals(Date.class))
                    {
                        value = "'" + dtFrmt.format((Date) campo.get(this)) + "'";
                    }
                    else
                    {
                        value = campo.get(this).toString();
                    }
                }
            }
        }
        catch(IllegalAccessException ex)
        {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        
        return value;
    }
    
}
