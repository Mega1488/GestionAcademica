/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.Curso;
import Entidad.Escolaridad;
import Entidad.Inscripcion;
import Entidad.Materia;
import Entidad.MateriaRevalida;
import Entidad.Modulo;
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
import SDT.SDT_PersonaEstudio;
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
        Retorno_MsgObj retorno  = new Retorno_MsgObj(new Mensajes("Error", TipoMensaje.ERROR), pObjeto);
        
        if(this.ValidarEmail(pObjeto.getPerEml(), null))
        {
            retorno = new Retorno_MsgObj(new Mensajes("El email ya existe", TipoMensaje.ERROR), null);
            error   = true;
        }
        
        if(this.ValidarUsuario(pObjeto.getPerUsrMod(), null))
        {
            retorno = new Retorno_MsgObj(new Mensajes("El usuario ya existe", TipoMensaje.ERROR), null);
            error   = true;
        }
        
        if(param.getParUtlMdl())
        {
            if(pObjeto.getPerUsrModID() == null)
            {
                retorno = this.Mdl_AgregarUsuario(pObjeto);
            }
            else
            {
                retorno = this.Mdl_ActualizarUsuario(pObjeto);
            }
            
            error = retorno.SurgioError();
        }    


        
        if(!error)
        {
            retorno = (Retorno_MsgObj) perPersona.guardar(pObjeto);
        }
        
        return retorno;
    }

    @Override
    public Object actualizar(Persona pObjeto) {
        boolean error           = false;
        Retorno_MsgObj retorno  = new Retorno_MsgObj(new Mensajes("Error al actualizar", TipoMensaje.ERROR), pObjeto);
        
        
        if(this.ValidarEmail(pObjeto.getPerEml(), pObjeto.getPerCod()))
        {
            retorno = new Retorno_MsgObj(new Mensajes("El email ya existe", TipoMensaje.ERROR), null);
            error   = true;
        }
        
        if(this.ValidarUsuario(pObjeto.getPerUsrMod(), pObjeto.getPerCod()))
        {
            retorno = new Retorno_MsgObj(new Mensajes("El usuario ya existe", TipoMensaje.ERROR), null);
            error   = true;
        }
        
        if(param.getParUtlMdl())
        {
            retorno = this.Mdl_ActualizarUsuario(pObjeto);
            error = retorno.SurgioErrorObjetoRequerido();
        }


        if(!error)
        {
            retorno = (Retorno_MsgObj) perPersona.actualizar((Persona) retorno.getObjeto());
        }
        
        return retorno;
    }

    @Override
    public Object eliminar(Persona pObjeto) {
       Retorno_MsgObj retorno  = this.Mdl_EliminarUsuario(pObjeto);
       
       if(!retorno.SurgioError())
       {
           retorno = (Retorno_MsgObj) perPersona.eliminar(pObjeto);
       }
       
       return retorno;
    }

    @Override
    public Retorno_MsgObj obtener(Object pCodigo) {
        return perPersona.obtener(pCodigo);
    }
    
    @Override
    public Retorno_MsgObj obtenerByMdlUsr(String pMdlUsr) {
        return perPersona.obtenerByMdlUsr(pMdlUsr);
    }

    @Override
    public Retorno_MsgObj obtenerLista() {
        return perPersona.obtenerLista();
    }
    
    public Retorno_MsgObj obtenerPopUp(Long CarCod, Long PlaEstCod, Long CurCod, String PerNom, String PerApe, Boolean docente, Boolean alumno)
    {
        Retorno_MsgObj retorno = perPersona.obtenerPopUp(CarCod, PlaEstCod, CurCod, PerNom, PerApe, docente, alumno);
        for(Object objeto : retorno.getLstObjetos())
        {
            Persona persona = (Persona) objeto;
            
            persona.setLstEstudios(this.ObtenerEstudios(persona.getPerCod()));
            
        }

        return retorno;
        
    }
    
    public Boolean IniciarSesion(String usuario, String password){
        boolean resultado = false;
        
            Persona persona = new Persona();
            
            Retorno_MsgObj retorno = this.obtenerByMdlUsr(usuario);
            
            if(!retorno.SurgioErrorObjetoRequerido())
            {
                persona = (Persona) retorno.getObjeto();
            }
            else
            {
                retorno = perPersona.obtenerByEmail(usuario);
                if(!retorno.SurgioErrorObjetoRequerido())
                {
                    persona = (Persona) retorno.getObjeto();
                }
            }
            
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
                    persona.setPerCntIntLgn((persona.getPerCntIntLgn() != null ? persona.getPerCntIntLgn() + 1 : 1));
                }

                this.actualizar(persona);
            }
            
        
        return resultado;
    }
    
    private boolean ValidarEmail(String email, Long idOriginal){
        boolean error = false;

        Retorno_MsgObj retorno = perPersona.obtenerByEmail(email);
        if(idOriginal == null)
        {
            error = retorno.getObjeto() != null;
        }
        else
        {
            if(retorno.getObjeto() != null)
            {
                error = !((Persona) retorno.getObjeto()).getPerCod().equals(idOriginal);
            }
        }
        
        return error;
    }
    
    private boolean ValidarUsuario(String usuario, Long idOriginal){
        boolean error = false;

        Retorno_MsgObj retorno = perPersona.obtenerByMdlUsr(usuario);
        if(idOriginal == null)
        {
            error = retorno.getObjeto() != null;
        }
        else
        {
            if(retorno.getObjeto() != null)
            {
                error = !((Persona) retorno.getObjeto()).getPerCod().equals(idOriginal);
            }
        }
        
        return error;
    }
    
    public ArrayList<SDT_PersonaEstudio> ObtenerEstudios(Long PerCod)
    {
        ArrayList<SDT_PersonaEstudio> lstEstudios = new ArrayList<>();;
        
        Retorno_MsgObj inscripcion                  = LoInscripcion.GetInstancia().obtenerListaByAlumno(PerCod);
        for(Object objeto : inscripcion.getLstObjetos())
        {
            lstEstudios  = PersonaAgregarEstudio(lstEstudios, null, (Inscripcion) objeto);
        }
        
        Retorno_MsgObj retorno = LoEscolaridad.GetInstancia().obtenerListaByAlumno(PerCod);
        
        if(!retorno.SurgioErrorListaRequerida())
        {

            
                    
            for(Object objeto : retorno.getLstObjetos())
            {
                Escolaridad escolaridad = (Escolaridad) objeto;
                
                if(escolaridad.getMateria() != null) retorno = LoInscripcion.GetInstancia().obtenerInscByPlan(PerCod, escolaridad.getMateria().getPlan().getPlaEstCod());
                
                if(escolaridad.getModulo() != null) retorno = LoInscripcion.GetInstancia().obtenerInscByCurso(PerCod, escolaridad.getModulo().getCurso().getCurCod());
                
                if(escolaridad.getCurso() != null) retorno = LoInscripcion.GetInstancia().obtenerInscByCurso(PerCod, escolaridad.getCurso().getCurCod());

                if(!retorno.SurgioError()) lstEstudios = PersonaAgregarEstudio(lstEstudios, escolaridad, (Inscripcion) retorno.getObjeto());
                
                
            }
        }
        
        return lstEstudios;
    }
    
    private ArrayList<SDT_PersonaEstudio> PersonaAgregarEstudio(ArrayList<SDT_PersonaEstudio> lstEstudio, Escolaridad escolaridad, Inscripcion inscripcion)
    {
        boolean existe = false;
        
        if(inscripcion == null)
        {
           inscripcion = new Inscripcion();
           inscripcion.setInsCod(Long.valueOf("0"));
        }
        
        for(SDT_PersonaEstudio est : lstEstudio)
        {
            if(est.getInscripcion().getInsCod().equals(inscripcion.getInsCod())){
                existe = true;
                est.getEscolaridad().add(escolaridad);
                break;
            }
        }
        
        if(!existe){
            SDT_PersonaEstudio est = new SDT_PersonaEstudio();
            est.setInscripcion(inscripcion);
            
            if(escolaridad != null) est.getEscolaridad().add(escolaridad);
            
            for(MateriaRevalida matRvl : inscripcion.getLstRevalidas())
            {
                Escolaridad esc = new Escolaridad();
                esc.setAlumno(inscripcion.getAlumno());
                esc.setEscCalVal(Double.NaN);
                esc.setMateria(matRvl.getMateria());
                est.getEscolaridad().add(esc);
            }
            
            lstEstudio.add(est);
        }
        
        

        return lstEstudio;
    }
    
    public boolean PersonaAproboEstudio(Long PerCod, Materia materia, Modulo modulo, Curso curso)
    {
        
        ArrayList<SDT_PersonaEstudio> lstEstudio = this.ObtenerEstudios(PerCod);
        
        for(SDT_PersonaEstudio estudio : lstEstudio)
        {
            for(Escolaridad escolaridad : estudio.getEscolaridad())
            {
                if(escolaridad.getMateria() != null && materia != null)  if(escolaridad.getMateria().equals(materia)) return escolaridad.getAprobado();
                if(escolaridad.getCurso() != null && curso != null)  if(escolaridad.getCurso().equals(curso)) return escolaridad.getAprobado();
                if(escolaridad.getModulo() != null && modulo != null)  if(escolaridad.getModulo().equals(modulo)) return escolaridad.getAprobado();
            }
        }
        
        return false;
    }
    
    public Boolean PersonaPuedeDarExamen(Long PerCod, Materia materia, Modulo modulo, Curso curso)
    {
        return LoCalendario.GetInstancia().AlumnoPuedeDarExamen(PerCod, materia, modulo, curso);
        
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

                        Persona persona = (Persona) this.obtenerByMdlUsr(mdlUsr.getUsername()).getObjeto();
                        
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
            
        } catch (MoodleRestException | UnsupportedEncodingException | MoodleUserRoleException ex) {
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
        catch(MoodleRestException | MoodleUserRoleException ex)
        {
            System.err.println("Error: " + ex.getLocalizedMessage());
        }


        return lstPersonas;
    }
    
    private Persona Mdl_UsuarioToPersona(MoodleUser usuario)
    {
        Persona persona = (Persona) this.obtenerByMdlUsr(usuario.getUsername()).getObjeto();
        if(persona == null)
        {
            persona = new Persona();
        }
        persona.setPerUsrMod(usuario.getUsername());
        persona.setPerEml(usuario.getEmail());
        persona.setPerNom(usuario.getFirstname());
        persona.setPerApe(usuario.getLastname());
        persona.setPerUsrModID(usuario.getId());
        
        return persona;
    }
    
    //----------------------------------------------------------------------------------------------------
    
    private Retorno_MsgObj Mdl_AgregarUsuario(Persona persona)
    {
        Retorno_MsgObj retorno;

        MoodleUser usr = new MoodleUser();
        
        usr.setUsername(persona.getPerUsrMod());
        usr.setEmail(persona.getPerEml());
        usr.setFirstname(persona.getPerNom());
        usr.setLastname(persona.getPerApe());
        usr.setFirstname(persona.getPerNom());
        usr.setPassword("21!_646?adD.09Ajku");
        
        try {

            usr = mdlRestUser.__createUser(param.getParUrlMdl() + Constantes.URL_FOLDER_SERVICIO_MDL.getValor(), param.getParMdlTkn(), usr);
            persona.setPerUsrModID(usr.getId());
            retorno = new Retorno_MsgObj(new Mensajes("Cambios correctos", TipoMensaje.MENSAJE), persona);
            
        } catch (MoodleRestException ex) {

            retorno = new Retorno_MsgObj(new Mensajes("Error: " + ex.getMessage(), TipoMensaje.ERROR), persona);
            Logger.getLogger(LoEstudio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return retorno;
    }
    
    private Retorno_MsgObj Mdl_ActualizarUsuario(Persona persona)
    {
        Retorno_MsgObj retorno = Mdl_ObtenerUsuarioByID(persona.getPerUsrModID());
        
        if(!retorno.SurgioErrorObjetoRequerido())
        {
            MoodleUser usr      = (MoodleUser) retorno.getObjeto();
        
            usr.setUsername(persona.getPerUsrMod());
            usr.setEmail(persona.getPerEml());
            usr.setFirstname(persona.getPerNom());
            usr.setLastname(persona.getPerApe());
            usr.setFirstname(persona.getPerNom());
        
            try
            {
                mdlRestUser.__updateUser(param.getParUrlMdl() + Constantes.URL_FOLDER_SERVICIO_MDL.getValor(), param.getParMdlTkn(), usr);
                retorno = new Retorno_MsgObj(new Mensajes("Cambios correctos", TipoMensaje.MENSAJE), persona);
            } catch (MoodleRestException ex) {

                retorno = new Retorno_MsgObj(new Mensajes("Error: " + ex.getMessage(), TipoMensaje.ERROR), persona);
                Logger.getLogger(LoEstudio.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        
        return retorno;
    }
    
    private Retorno_MsgObj Mdl_EliminarUsuario(Persona persona)
    {
        Retorno_MsgObj retorno;
        
        try
        {
            mdlRestUser.__deleteUser(param.getParUrlMdl() + Constantes.URL_FOLDER_SERVICIO_MDL.getValor(), param.getParMdlTkn(), persona.getPerUsrModID());
            
            retorno = new Retorno_MsgObj(new Mensajes("Cambios correctos", TipoMensaje.MENSAJE), null);
        } catch (MoodleRestException ex) {

            retorno = new Retorno_MsgObj(new Mensajes("Error: " + ex.getMessage(), TipoMensaje.ERROR), persona);
            Logger.getLogger(LoEstudio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            retorno = new Retorno_MsgObj(new Mensajes("Error: " + ex.getMessage(), TipoMensaje.ERROR), persona);
            Logger.getLogger(LoPersona.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return retorno;
    }
    
    
    private Retorno_MsgObj Mdl_ObtenerUsuarioByID(Long id)
    {
        Retorno_MsgObj retorno;
 
        try
        {
            MoodleUser usr = mdlRestUser.__getUserById(param.getParUrlMdl() + Constantes.URL_FOLDER_SERVICIO_MDL.getValor(), param.getParMdlTkn(), id);
            retorno = new Retorno_MsgObj(new Mensajes("Ok", TipoMensaje.MENSAJE), usr);
        }
        catch (MoodleRestException ex) {

            retorno = new Retorno_MsgObj(new Mensajes("Error: " + ex.getMessage(), TipoMensaje.ERROR), null);
            Logger.getLogger(LoEstudio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            retorno = new Retorno_MsgObj(new Mensajes("Error: " + ex.getMessage(), TipoMensaje.ERROR), null);
            Logger.getLogger(LoPersona.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retorno;
    }
    //----------------------------------------------------------------------------------------------------
    //-Fin Modle
    //----------------------------------------------------------------------------------------------------
}
