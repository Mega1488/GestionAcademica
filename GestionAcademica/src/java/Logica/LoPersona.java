/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.Parametro;
import Entidad.Persona;
import Enumerado.Constantes;
import Enumerado.TipoMensaje;
import Moodle.Criteria;
import Moodle.MoodleCourse;
import Moodle.MoodleRestCourse;
import Moodle.MoodleRestEnrol;
import Moodle.MoodleRestException;
import Moodle.MoodleRestUser;
import Moodle.MoodleUser;
import Moodle.MoodleUserRoleException;
import Moodle.UserRole;
import Persistencia.PerPersona;
import Utiles.Mensajes;
import Utiles.Retorno_MsgObj;
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
        boolean error           = false;
        Mensajes mensaje        = new Mensajes("Error", TipoMensaje.ERROR);
        Retorno_MsgObj retorno  = new Retorno_MsgObj();
        
        if(perPersona.obtenerByEmail(pObjeto.getPerEml()).getPerCod() != null)
        {
            mensaje = new Mensajes("El email ya existe", TipoMensaje.ERROR);
            error   = true;
        }
        
        if(perPersona.obtenerByMdlUsr(pObjeto.getPerUsrMod()).getPerCod() != null)
        {
            mensaje = new Mensajes("El usuario ya existe", TipoMensaje.ERROR);
            error   = true;
        }


        Object objeto = null;
        
        if(!error)
        {
            objeto = perPersona.guardar(pObjeto);
            
            if(((Persona) objeto).getPerCod() == null)
            {
                mensaje = new Mensajes("Error al guardar", TipoMensaje.ERROR);
            }
            else
            {
                mensaje = new Mensajes("Cambios guardados correctamente", TipoMensaje.MENSAJE);
                 
                if(param.getParUtlMdl())
                {
                    mensaje = this.Mdl_AgregarUsuario(pObjeto);

                    if(mensaje.getTipoMensaje() == TipoMensaje.ERROR)
                    {
                        this.eliminar((Persona) objeto);
                    }
                }
            }
        }
        
        retorno = new Retorno_MsgObj(mensaje, objeto);
        return retorno;
    }

    @Override
    public Object actualizar(Persona pObjeto) {
        boolean error           = false;
        Mensajes mensaje        = new Mensajes("Error", TipoMensaje.ERROR);
        Retorno_MsgObj retorno  = new Retorno_MsgObj();
        
        if(perPersona.obtenerByEmail(pObjeto.getPerEml()).getPerCod() != null && perPersona.obtenerByMdlUsr(pObjeto.getPerUsrMod()).getPerCod() != pObjeto.getPerCod())
        {
            mensaje = new Mensajes("El email ya existe", TipoMensaje.ERROR);
            error   = true;
        }
        
        if(perPersona.obtenerByMdlUsr(pObjeto.getPerUsrMod()).getPerCod() != null && perPersona.obtenerByMdlUsr(pObjeto.getPerUsrMod()).getPerCod() != pObjeto.getPerCod())
        {
            mensaje = new Mensajes("El usuario ya existe", TipoMensaje.ERROR);
            error   = true;
        }


        if(!error)
        {
            Persona sinModificar = perPersona.obtener(pObjeto);
            
            error = (boolean) perPersona.actualizar(pObjeto);
            
            if(!error)
            {
                mensaje = new Mensajes("Cambios aplicados", TipoMensaje.MENSAJE);
                
                if(param.getParUtlMdl())
                {
                    mensaje = this.Mdl_ActualizarUsuario(pObjeto);
                    
                    if(mensaje.getTipoMensaje() == TipoMensaje.ERROR)
                    {
                        this.actualizar(sinModificar);
                    }
                }
            }
            else
            {
                mensaje = new Mensajes("Error al aplicar cambios", TipoMensaje.ERROR);
            }
        }
        
        retorno = new Retorno_MsgObj(mensaje, null);
        return retorno;
    }

    @Override
    public Object eliminar(Persona pObjeto) {
       boolean error           = false;
       Mensajes mensaje        = new Mensajes("Error", TipoMensaje.ERROR);
       Retorno_MsgObj retorno  = new Retorno_MsgObj();
       
       if(!perPersona.ValidarEliminacion(pObjeto))
       {
           mensaje = this.Mdl_EliminarUsuario(pObjeto);

            if(mensaje.getTipoMensaje() != TipoMensaje.ERROR)
            {
                error = (boolean) perPersona.eliminar(pObjeto);
                if(error)
                {
                    mensaje = new Mensajes("Error al impactar en la base de datos", TipoMensaje.ERROR);
                }
            }
       }
       else
       {
           mensaje = new Mensajes("Error al impactar en la base de datos", TipoMensaje.ERROR);
       }
       
       retorno = new Retorno_MsgObj(mensaje, null);
       
       return retorno;
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
        
        SincronizarTipoUsuarioMdl();
    }
    
    private void SincronizarTipoUsuarioMdl()
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
    
    private Mensajes Mdl_AgregarUsuario(Persona persona)
    {
        Mensajes mensaje = new Mensajes("Error al impactar en moodle", TipoMensaje.ERROR);

        MoodleUser usr = new MoodleUser();
        
        usr.setUsername(persona.getPerUsrMod());
        usr.setEmail(persona.getPerEml());
        usr.setFirstname(persona.getPerNom());
        usr.setLastname(persona.getPerApe());
        usr.setFirstname(persona.getPerNom());
        usr.setPassword("21!_646?adD.09Ajku");
        
        try
        {
            usr = mdlRestUser.__createUser(param.getParUrlMdl() + Constantes.URL_FOLDER_SERVICIO_MDL.getValor(), param.getParMdlTkn(), usr);
            
            persona.setPerUsrModID(usr.getId());
            
            this.actualizar(persona);
            
            mensaje = new Mensajes("Cambios correctos", TipoMensaje.MENSAJE);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        return mensaje;
    }
    
    private Mensajes Mdl_ActualizarUsuario(Persona persona)
    {
        Mensajes mensaje    = new Mensajes("Error al impactar en moodle", TipoMensaje.ERROR);
        MoodleUser usr      = Mdl_ObtenerUsuarioByID(persona.getPerUsrModID());
        
        usr.setUsername(persona.getPerUsrMod());
        usr.setEmail(persona.getPerEml());
        usr.setFirstname(persona.getPerNom());
        usr.setLastname(persona.getPerApe());
        usr.setFirstname(persona.getPerNom());
        
        try
        {
            mdlRestUser.__updateUser(param.getParUrlMdl() + Constantes.URL_FOLDER_SERVICIO_MDL.getValor(), param.getParMdlTkn(), usr);
            mensaje = new Mensajes("Cambios correctos", TipoMensaje.MENSAJE);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        return mensaje;
    }
    
    private Mensajes Mdl_EliminarUsuario(Persona persona)
    {
        Mensajes mensaje = new Mensajes("Error al impactar en moodle", TipoMensaje.ERROR);

        try
        {
            mdlRestUser.__deleteUser(param.getParUrlMdl() + Constantes.URL_FOLDER_SERVICIO_MDL.getValor(), param.getParMdlTkn(), persona.getPerUsrModID());
            mensaje = new Mensajes("Cambios correctos", TipoMensaje.MENSAJE);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        return mensaje;
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
