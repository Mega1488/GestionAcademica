/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dominio;

import Enumerado.Objetos;
import Utiles.Utilidades;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author alvar
 */

//@JsonIgnoreProperties({"insertQuery", "updateQuery"})

public abstract class SincHelper{
    
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
                            Logger.getLogger(SincHelper.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

        return null;
    }
    
    @JsonIgnore
    public String getInsertQuery(){
        String insert = "INSERT INTO " + Objetos.fromQueryName(this.getClass().getSimpleName().toUpperCase()).name() + "(";
        
        String columns  = "";
        String values   = "";
        for (Field field : this.getClass().getDeclaredFields()) 
        {
        
            if(fieldIsColumn(field) || fieldIsJoinColumn(field))
            {
                //-------------------------------------------------------------
                //Nombre del campo
                //-------------------------------------------------------------
                String name = (this.fieldIsJoinColumn(field) ? this.fieldJoinColumnName(field) : field.getName());
                
                columns += (columns.isEmpty() ? name :  ", " + name);
               
                //-------------------------------------------------------------
                //Valor del campo
                //-------------------------------------------------------------
                
                values += (values.isEmpty() ? getParsedValue(field) :  ", " + getParsedValue(field));
 
            }
       }
        
        
        insert += columns +") VALUES(" + values + ")";
        
        System.err.println("Query: " + insert);
        return insert;
    }
    
    @JsonIgnore
    public String getUpdateQuery(){
        String update = "UPDATE " + Objetos.fromQueryName(this.getClass().getSimpleName().toUpperCase()).getNamedQuery() + " SET ";
        
        
        
        String sets   = "";

        for (Field field : this.getClass().getDeclaredFields()) 
        {
        
            if((fieldIsColumn(field) || fieldIsJoinColumn(field) ) && !fieldIsPrimaryKey(field))
            {
                String name = (this.fieldIsJoinColumn(field) ? this.fieldJoinColumnName(field) : field.getName());
                
                sets += (sets.isEmpty() ? name + " = " + this.getParsedValue(field) : ", " + name + " = " + this.getParsedValue(field));
                
            }
       }
        
        
        update += sets + " WHERE " + this.GetNamePrimaryKey() + " = " + this.GetPrimaryKey();
        
        System.err.println("Query: " + update);
        
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

    private Boolean fieldIsJoinColumn(Field campo){
        Annotation[] lstAnot = campo.getAnnotations();
        
        for(Annotation anot : lstAnot)
        {
           if(anot.annotationType().equals(ManyToOne.class) || anot.annotationType().equals(OneToOne.class)){
               return true;
           }
        }

        return false;
    }
    
    private String fieldJoinColumnName(Field campo){
        
        JoinColumn j = campo.getAnnotation(JoinColumn.class);
        
        return j.name();
        
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
                
                if(fieldIsJoinColumn(campo))
                {
                    value = Utilidades.GetInstancia().ObtenerPrimaryKey(campo.get(this)).toString();
                }
                else
                {

                    if(campo.getType().equals(String.class))
                    {
                        value = "'" + campo.get(this) + "'";
                    }

                    if(campo.getType().equals(Date.class))
                    {
                        value = "'" + dtFrmt.format((Date) campo.get(this)) + "'";
                    }
                    
                    if(campo.getType() instanceof Class && ((Class<?>)campo.getType()).isEnum())
                    {
                        Enum en = (Enum) campo.get(this);
                        
                        value = en.ordinal() + "";
                    }
                        
                    if(value == null) value = campo.get(this).toString();
                    
                }
            }
        }
        catch(IllegalAccessException ex)
        {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        
        return value;
    }
    
    public void CastFromObject(Object fromObjeto){
        Object toObject = this;
        
        try{
            
            for (Field field : this.getClass().getDeclaredFields()) 
            {
                field.setAccessible(true);
                
                for (Field fld : fromObjeto.getClass().getDeclaredFields()) 
                {
                    fld.setAccessible(true);

                    if(field.getType().equals(fld.getType()) && field.getName().equals(fld.getName()))
                    {
                        field.set(toObject, fld.get(fromObjeto));
                    }
                }                
            }
        }
        catch(SecurityException | IllegalArgumentException | IllegalAccessException ex)
        {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        
        //return toObject;
        
    }
}
