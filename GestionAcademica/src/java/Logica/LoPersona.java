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
import Moodle.MoodleCourse;
import Moodle.MoodleRestCourse;
import Moodle.MoodleRestEnrol;
import Moodle.MoodleRestEnrolException;
import Moodle.MoodleRestException;
import Moodle.MoodleRestUser;
import Moodle.MoodleUser;
import Moodle.MoodleUserRoleException;
import Moodle.UserRole;
import Persistencia.PerPersona;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alvar
 */
public class LoPersona implements Interfaz.InPersona{
    
    private static LoPersona        instancia;
    private final PerPersona        perPersona;
    private final MoodleRestUser    mdlRestUser;
    private final MoodleRestEnrol   mdlEnrol;
    private final MoodleRestCourse  mdlCourse;
    private final Parametro         param;
    private final Seguridad         seguridad;

    private LoPersona() {
        perPersona          = new PerPersona();
        LoParametro loParam = LoParametro.GetInstancia();
        param               = loParam.obtener(1);
        mdlRestUser         = new MoodleRestUser();
        seguridad           = Seguridad.GetInstancia();
        mdlEnrol            = new  MoodleRestEnrol();
        mdlCourse           = new MoodleRestCourse();
    
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
        if(param.getParUtlMdl())
        {
            this.Mdl_ActualizarUsuario(pObjeto);
        }
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

            Persona persona = this.obtenerByMdlUsr(usuario);
            
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
                    persona.setPerPass(seguridad.cryptWithMD5("admin"));
                    this.guardar(persona);
                }
                else
                {
                    this.actualizar(persona);
                }
            }
        }
        
        ActualizarTipoUsuarioMdl();
    }
    
    private void ActualizarTipoUsuarioMdl()
    {
        try {
            MoodleCourse[] lstCurso = mdlCourse.__getAllCourses(param.getParUrlMdl()+ Constantes.URL_FOLDER_SERVICIO_MDL.getValor(), param.getParMdlTkn());
            
            for(int i = 0; i<lstCurso.length; i++)
            {
                MoodleCourse curso      = lstCurso[i];
                MoodleUser[] lstUser    = mdlEnrol.__getEnrolledUsers(param.getParUrlMdl()+ Constantes.URL_FOLDER_SERVICIO_MDL.getValor(), param.getParMdlTkn(), curso.getId(), null);
               
                if(lstUser != null)
                {
                    for(int e = 0; e < lstUser.length; e++)
                    {
                        MoodleUser mdlUsr           = lstUser[e];
                        ArrayList<UserRole> roles   = mdlUsr.getRoles();

                        Persona persona = this.obtenerByMdlUsr(mdlUsr.getUsername());
                        
                        if(!persona.getPerEsAdm())
                        {
                            persona.setPerEsAdm(Boolean.FALSE);
                            persona.setPerEsDoc(Boolean.FALSE);
                        }

                        for(UserRole userRole : roles)
                        {
                            switch(userRole.getRole())
                            {
                                case STUDENT:
                                    persona.setPerEsAlu(Boolean.TRUE);
                                    break;
                                case TEACHER:
                                    persona.setPerEsDoc(Boolean.TRUE);
                                    break;
                            }
                        }
                        
                        perPersona.actualizar(persona);

                    }
                }
                
            }
            
        } catch (MoodleRestException ex) {
            Logger.getLogger(LoPersona.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(LoPersona.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MoodleUserRoleException ex) {
            Logger.getLogger(LoPersona.class.getName()).log(Level.SEVERE, null, ex);
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
    

    private void Mdl_ActualizarUsuario(Persona persona)
    {
        MoodleUser usr = Mdl_ObtenerUsuarioByID(persona.getPerUsrModID());
        
        usr.setUsername(persona.getPerUsrMod());
        usr.setEmail(persona.getPerEml());
        usr.setFirstname(persona.getPerNom());
        usr.setLastname(persona.getPerApe());
        usr.setFirstname(persona.getPerNom());
        
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
