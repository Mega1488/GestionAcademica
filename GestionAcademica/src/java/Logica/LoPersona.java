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
import java.util.Date;
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
    private Seguridad           seguridad;

    private LoPersona() {
        perPersona          = new PerPersona();
        LoParametro loParam = LoParametro.GetInstancia();
        param               = loParam.obtener(1);
        mdlRestUser         = new MoodleRestUser();
        seguridad           = Seguridad.GetInstancia();
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
    
    public Boolean IniciarSesion(String usuario, String password){
        boolean resultado = false;
        
        try
        {
            System.err.println("Usuario: " + usuario);

            Persona persona = this.obtenerByMdlUsr(usuario);

            System.err.println("Persona: " + persona.toString());
            
            if(persona.getPerCod() != null)
            {
                
                resultado = persona.getPerPass().equals(password);

                if(resultado)
                {
                    persona.setPerFchLog(new Date());
                    persona.setPerCntIntLgn(0);
                }
                else
                {
                    if(persona.getPerCntIntLgn() != null)
                    {
                        persona.setPerCntIntLgn(persona.getPerCntIntLgn() + 1);
                    }
                    else
                    {
                        persona.setPerCntIntLgn(1);
                    }
                }

                this.actualizar(persona);
            }
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        return resultado;
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
                    persona.setPerPass(seguridad.cryptWithMD5("admin"));
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
