/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alvar
 */
public class UsuarioMoodle {

    private int id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String fullname;
    private String email;
    private String auth;
    private int suspended;
    private String idnumber;
    private String lang;
    private String calendartype;
    private String theme;
    private String timezone;
    private int mailformat;
    private String description;
    private String city;
    private String country;
    private String firstnamephonetic;
    private String lastnamephonetic;
    private String middlename;
    private String alternatename;
    private int userpicture;
    
    private List<UsuarioMoodleCustomFields> customfields;
    
    private List<UsuarioMoodleCustomFields> preferences;
    
    public UsuarioMoodle() {
        this.customfields = new ArrayList<>();
        this.preferences = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public int getSuspended() {
        return suspended;
    }

    public void setSuspended(int suspended) {
        this.suspended = suspended;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getCalendartype() {
        return calendartype;
    }

    public void setCalendartype(String calendartype) {
        this.calendartype = calendartype;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public int getMailformat() {
        return mailformat;
    }

    public void setMailformat(int mailformat) {
        this.mailformat = mailformat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFirstnamephonetic() {
        return firstnamephonetic;
    }

    public void setFirstnamephonetic(String firstnamephonetic) {
        this.firstnamephonetic = firstnamephonetic;
    }

    public String getLastnamephonetic() {
        return lastnamephonetic;
    }

    public void setLastnamephonetic(String lastnamephonetic) {
        this.lastnamephonetic = lastnamephonetic;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getAlternatename() {
        return alternatename;
    }

    public void setAlternatename(String alternatename) {
        this.alternatename = alternatename;
    }

    public int getUserpicture() {
        return userpicture;
    }

    public void setUserpicture(int userpicture) {
        this.userpicture = userpicture;
    }

    public List<UsuarioMoodleCustomFields> getCustomfields() {
        return customfields;
    }

    public void setCustomfields(List<UsuarioMoodleCustomFields> customfields) {
        this.customfields = customfields;
    }

    public List<UsuarioMoodleCustomFields> getPreferences() {
        return preferences;
    }

    public void setPreferences(List<UsuarioMoodleCustomFields> preferences) {
        this.preferences = preferences;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    
    
    

        
}

