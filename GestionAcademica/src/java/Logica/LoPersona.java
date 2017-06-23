/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.Parametro;
import Entidad.Persona;
import Enumerado.Constantes;
import Moodle.Criteria;
import Moodle.MoodleRestUser;
import Moodle.MoodleUser;
import Moodle.UserCustomField;
import Persistencia.PerPersona;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alvar
 */
public class LoPersona implements Interfaz.InPersona{
    
    private static LoPersona    instancia;
    private PerPersona          perPersona;
    private MoodleRestUser      mdlRestUser;
    private Parametro           param;

    private LoPersona() {
        perPersona          = new PerPersona();
        LoParametro loParam = LoParametro.GetInstancia();
        param               = loParam.obtener(1);
        mdlRestUser          = new MoodleRestUser();
    }
    
    public static LoPersona GetInstancia(){
        if (instancia==null)
        {
            instancia   = new LoPersona();
            
        }

        return instancia;
    }

    @Override
    public Object guardar(Persona pObjeto) {
        return perPersona.guardar(pObjeto);
    }

    @Override
    public void actualizar(Persona pObjeto) {
        perPersona.actualizar(pObjeto);
    }

    @Override
    public void eliminar(Persona pObjeto) {
        perPersona.eliminar(pObjeto);
    }

    @Override
    public Persona obtener(Object pCodigo) {
        return perPersona.obtener(pCodigo);
    }
    
    @Override
    public Persona obtenerByMdlUsr(String pMdlUsr) {
        return perPersona.obtenerByMdlUsr(pMdlUsr);
    }

    @Override
    public List<Persona> obtenerLista() {
        return perPersona.obtenerLista();
    }
    
    public Persona IniciarSesion(String usuario, String token){
        
        Persona persona = this.obtenerByMdlUsr(usuario);

        System.err.println("Iniciando login 1 : " + persona.getPerUsrModID());
        
        persona.setPerToken(token);
        this.actualizar(persona);
        
        MoodleUser usr =  this.Mdl_ObtenerUsuarioByID(persona.getPerUsrModID());

        System.err.println("Iniciando login: " + usr.getId());
        
        usr.setIdNumber(token);

        this.Mdl_ActualizarUsuario(usr);
        
        usr = this.Mdl_ObtenerUsuarioByID(persona.getPerUsrModID());
        
        System.err.println("Custom fields luego de guardar: " + usr.getIdNumber());
        
        return persona;
    }
    
    //----------------------------------------------------------------------------------------------------
    //-Modle
    //----------------------------------------------------------------------------------------------------
    
    public void SincronizarUsuariosMoodleSistema()
    {
        List<Persona> lstPersonas = this.MdlObtenerUsuarios();
        
        for(Persona persona : lstPersonas)
        {
            if(!persona.getPerUsrMod().equals("") )
            {
 
                if(persona.getPerCod() == null)
                {
                    this.guardar(persona);
                }
                else
                {
                    this.actualizar(persona);
                }
            }
        }
    }
    
    private List<Persona> MdlObtenerUsuarios()
    {
        List<Persona> lstPersonas = new ArrayList<>();
        
        try
        {
            Criteria criteria = new Criteria();

            criteria.setKey("name");
            criteria.setValue("");

            Criteria[] lstCriteria   = new Criteria[1];
            lstCriteria[0]           = criteria;
            System.err.println("url: " + param.getParUrlMdl() + Constantes.URL_FOLDER_SERVICIO_MDL.getValor());
             MoodleUser[] lstUsr = mdlRestUser.__getUsers(param.getParUrlMdl() + Constantes.URL_FOLDER_SERVICIO_MDL.getValor(), param.getParMdlTkn(), lstCriteria);


             for(MoodleUser usr: lstUsr)
             {
                 Persona persona = Mdl_UsuarioToPersona(usr);
                 lstPersonas.add(persona);
             }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }


        return lstPersonas;
    }
    
    private Persona Mdl_UsuarioToPersona(MoodleUser usuario)
    {
        Persona persona = this.obtenerByMdlUsr(usuario.getUsername());
        persona.setPerUsrMod(usuario.getUsername());
        persona.setPerEml(usuario.getEmail());
        persona.setPerNom(usuario.getFirstname());
        persona.setPerApe(usuario.getLastname());
        persona.setPerUsrModID(usuario.getId());
        
        return persona;
    }
    

    private void Mdl_ActualizarUsuario(MoodleUser usr)
    {
        try
        {
            mdlRestUser.__updateUser(param.getParUrlMdl() + Constantes.URL_FOLDER_SERVICIO_MDL.getValor(), param.getParMdlTkn(), usr);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    
    private MoodleUser Mdl_ObtenerUsuarioByID(Long id)
    {
        MoodleUser usr = new MoodleUser();

        try
        {
            usr = mdlRestUser.__getUserById(param.getParUrlMdl() + Constantes.URL_FOLDER_SERVICIO_MDL.getValor(), param.getParMdlTkn(), id);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return usr;
    }
    //----------------------------------------------------------------------------------------------------
    //-Fin Modle
    //----------------------------------------------------------------------------------------------------
}
