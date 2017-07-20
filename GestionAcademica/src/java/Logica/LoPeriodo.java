/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.Inscripcion;
import Entidad.Modulo;
import Entidad.Periodo;
import Entidad.PeriodoEstudio;
import Entidad.PeriodoEstudioAlumno;
import Entidad.PeriodoEstudioDocente;
import Entidad.PeriodoEstudioDocumento;
import Enumerado.TipoMensaje;
import Interfaz.InABMGenerico;
import Persistencia.PerPeriodo;
import Utiles.Mensajes;
import Utiles.Retorno_MsgObj;
import java.util.Date;
import java.util.List;

/**
 *
 * @author alvar
 */
public class LoPeriodo implements InABMGenerico{

    private static LoPeriodo instancia;
    private final PerPeriodo perPeriodo;

    private LoPeriodo() {
        perPeriodo  = new PerPeriodo();
    }
    
    public static LoPeriodo GetInstancia(){
        if (instancia==null)
        {
            instancia   = new LoPeriodo();
        }

        return instancia;
    }
    

    @Override
    public Object guardar(Object pObjeto) {
        Periodo periodo = (Periodo) pObjeto;
        
        //--------------------------------------------------------------------------------------------------------------------------------------------
        //MODULO
        //--------------------------------------------------------------------------------------------------------------------------------------------
        
        List<Object> lstModulo = LoCurso.GetInstancia().ModuloPorPeriodo(periodo.getPerTpo(), periodo.getPerVal()).getLstObjetos();
        if(lstModulo != null)
        {
            for(Object objeto : lstModulo)
            {
                Modulo mdl = (Modulo) objeto;
                
                PeriodoEstudio periEstudio = new PeriodoEstudio();
                periEstudio.setModulo(mdl);
                periEstudio.setPeriodo(periodo);
                
                Retorno_MsgObj retorno = LoInscripcion.GetInstancia().obtenerListaByCurso(mdl.getCurso().getCurCod());
                if(!retorno.SurgioErrorListaRequerida())
                {
                    for(Object ob : retorno.getLstObjetos())
                    {
                        Inscripcion ins = (Inscripcion) ob;
                        PeriodoEstudioAlumno periEstAlumno = new PeriodoEstudioAlumno();
                        periEstAlumno.setAlumno(ins.getAlumno());
                        periEstAlumno.setPerInsFchInsc(new Date());
                        periEstAlumno.setPeriodoEstudio(periEstudio);
                        
                        periEstudio.getLstAlumno().add(periEstAlumno);
                    }
                }
                
                periodo.getLstEstudio().add(periEstudio);
            }
        }
        
        //--------------------------------------------------------------------------------------------------------------------------------------------
        //MATERIA
        //--------------------------------------------------------------------------------------------------------------------------------------------
        
        
        return perPeriodo.guardar(periodo);
    }

    @Override
    public Object actualizar(Object pObjeto) {
        return perPeriodo.actualizar(pObjeto);
    }

    @Override
    public Object eliminar(Object pObjeto) {
        return perPeriodo.eliminar(pObjeto);
    }

    @Override
    public Retorno_MsgObj obtener(Object pObjeto) {
        return perPeriodo.obtener(pObjeto);
    }

    @Override
    public Retorno_MsgObj obtenerLista() {
        return perPeriodo.obtenerLista();
    }
 
    public PeriodoEstudio obtenerLastPeriodoEstudioByMateria(Long MatCod) {
        Periodo periodo = (Periodo) perPeriodo.obtenerUltimoByMateria(MatCod).getObjeto();
        for(PeriodoEstudio perEst : periodo.getLstEstudio())
        {
            if(perEst.getMateria().getMatCod() == MatCod)
            {
                return perEst;
            }
        }
        
        return null;
    }
    
    public PeriodoEstudio obtenerLastPeriodoEstudioByModulo(Long ModCod) {
        Periodo periodo = (Periodo) perPeriodo.obtenerUltimoByModulo(ModCod).getObjeto();
        for(PeriodoEstudio perEst : periodo.getLstEstudio())
        {
            if(perEst.getModulo().getModCod() == ModCod)
            {
                return perEst;
            }
        }
        
        return null;
    }
    
    //------------------------------------------------------------------------------------
    //-MANEJO DE ESTUDIO
    //------------------------------------------------------------------------------------
    
    public Object EstudioAgregar(PeriodoEstudio periEstudio)
    {
        boolean error           = false;
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al agregar",TipoMensaje.ERROR), periEstudio);
        
        if(!error)
        {
            Periodo periodo = periEstudio.getPeriodo();
            periEstudio.setObjFchMod(new Date());
            periodo.getLstEstudio().add(periEstudio);
            retorno = (Retorno_MsgObj) this.actualizar(periodo);
        }
       
        
        return retorno;
    }
    
    public Object EstudioActualizar(PeriodoEstudio periEstudio)
    {
        
        Periodo periodo = periEstudio.getPeriodo();
        int indice  = periodo.getLstEstudio().indexOf(periEstudio);
        periEstudio.setObjFchMod(new Date());
        periodo.getLstEstudio().set(indice, periEstudio);
        
        Retorno_MsgObj retorno = (Retorno_MsgObj) this.actualizar(periodo);

        return retorno;
    }
    
    public Object EstudioEliminar(PeriodoEstudio periEstudio)
    {
        boolean error           = false;
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al eliminar", TipoMensaje.ERROR), periEstudio);
       
        if(!error)
        {
            Periodo periodo = periEstudio.getPeriodo();
            int indice  = periodo.getLstEstudio().indexOf(periEstudio);
            periodo.getLstEstudio().remove(indice);
       
            retorno = (Retorno_MsgObj) this.actualizar(periodo);
        }
        return retorno;
    }
    
    public Retorno_MsgObj obtenerPeriodoEstudio(Long PeriEstCod)
    {
        return perPeriodo.obtenerPeriodoEstudio(PeriEstCod);
    }
    
    //------------------------------------------------------------------------------------
    //-MANEJO DE ALUMNO
    //------------------------------------------------------------------------------------
    
    public Object AlumnoAgregar(PeriodoEstudioAlumno alumno)
    {
        boolean error           = false;
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al agregar",TipoMensaje.ERROR), alumno);
        
        if(!error)
        {
            PeriodoEstudio periEst = alumno.getPeriodoEstudio();
            alumno.setObjFchMod(new Date());
            
            periEst.getLstAlumno().add(alumno);
            retorno = (Retorno_MsgObj) this.EstudioActualizar(periEst);
        }
       
        
        return retorno;
    }
    
    public Object AlumnoActualizar(PeriodoEstudioAlumno alumno)
    {
        
        PeriodoEstudio periEst = alumno.getPeriodoEstudio();
        int indice  = periEst.getLstAlumno().indexOf(alumno);
        alumno.setObjFchMod(new Date());
        periEst.getLstAlumno().set(indice, alumno);

        Retorno_MsgObj retorno = (Retorno_MsgObj) this.EstudioActualizar(periEst);

        return retorno;
    }
    
    public Object AlumnoEliminar(PeriodoEstudioAlumno alumno)
    {
        boolean error           = false;
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al eliminar", TipoMensaje.ERROR), alumno);
       
        if(!error)
        {
            PeriodoEstudio periodo = alumno.getPeriodoEstudio();
            
            int indice  = periodo.getLstAlumno().indexOf(alumno);
            
            periodo.getLstAlumno().remove(indice);

            retorno = (Retorno_MsgObj) this.EstudioActualizar(periodo);
        }
        return retorno;
    }
   
    
    //------------------------------------------------------------------------------------
    //-MANEJO DE DOCENTES
    //------------------------------------------------------------------------------------
    
    public Object DocenteAgregar(PeriodoEstudioDocente docente)
    {
        boolean error           = false;
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al agregar",TipoMensaje.ERROR), docente);
        
        if(!error)
        {
            PeriodoEstudio periodo = docente.getPeriodoEstudio();
            docente.setObjFchMod(new Date());
            
            periodo.getLstDocente().add(docente);
            retorno = (Retorno_MsgObj) this.EstudioActualizar(periodo);
        }
       
        
        return retorno;
    }
    
    public Object DocenteActualizar(PeriodoEstudioDocente docente)
    {
        
        PeriodoEstudio periodo = docente.getPeriodoEstudio();
        int indice  = periodo.getLstDocente().indexOf(docente);
        docente.setObjFchMod(new Date());
        periodo.getLstDocente().set(indice, docente);

        Retorno_MsgObj retorno = (Retorno_MsgObj) this.EstudioActualizar(periodo);

        return retorno;
    }
    
    public Object DocenteEliminar(PeriodoEstudioDocente docente)
    {
        boolean error           = false;
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al eliminar", TipoMensaje.ERROR), docente);
       
        if(!error)
        {
            PeriodoEstudio periodo = docente.getPeriodoEstudio();
            int indice  = periodo.getLstDocente().indexOf(docente);
            periodo.getLstDocente().remove(indice);

            retorno = (Retorno_MsgObj) this.EstudioActualizar(periodo);
        }
        return retorno;
    }
    
    //------------------------------------------------------------------------------------
    //-MANEJO DE DOCUMENTO
    //------------------------------------------------------------------------------------
    
    public Object DocumentoAgregar(PeriodoEstudioDocumento documento)
    {
        boolean error           = false;
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al agregar",TipoMensaje.ERROR), documento);
        
        if(!error)
        {
            PeriodoEstudio periodo = documento.getPeriodo();
            documento.setObjFchMod(new Date());
            
            periodo.getLstDocumento().add(documento);
            retorno = (Retorno_MsgObj) this.EstudioActualizar(periodo);
        }
       
        
        return retorno;
    }
    
    public Object DocumentoActualizar(PeriodoEstudioDocumento documento)
    {
        
        PeriodoEstudio periodo = documento.getPeriodo();
        int indice  = periodo.getLstDocumento().indexOf(documento);
        documento.setObjFchMod(new Date());
        periodo.getLstDocumento().set(indice, documento);

        Retorno_MsgObj retorno = (Retorno_MsgObj) this.EstudioActualizar(periodo);

        return retorno;
    }
    
    public Object DocumentoEliminar(PeriodoEstudioDocumento documento)
    {
        boolean error           = false;
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Error al eliminar", TipoMensaje.ERROR), documento);
       
        if(!error)
        {
            PeriodoEstudio periodo = documento.getPeriodo();
            int indice  = periodo.getLstDocumento().indexOf(documento);
            periodo.getLstDocumento().remove(indice);
            
            retorno = (Retorno_MsgObj) this.EstudioActualizar(periodo);
        }
        return retorno;
    }
    
    
}
