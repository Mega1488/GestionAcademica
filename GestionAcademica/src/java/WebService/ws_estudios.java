/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;

import Entidad.Escolaridad;
import Entidad.PeriodoEstudio;
import Entidad.PeriodoEstudioDocumento;
import Entidad.Persona;
import Enumerado.TipoMensaje;
import Logica.LoCalendario;
import Logica.LoPeriodo;
import Logica.LoPersona;
import SDT.SDT_PersonaEstudio;
import Utiles.Mensajes;
import Utiles.Retorno_MsgObj;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author aa
 */
@WebService(serviceName = "ws_estudios")
public class ws_estudios {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "lstEstudiosPorAlumno")
    public Retorno_MsgObj lstEstudiosPorAlumno(@WebParam(name = "token") String token, @WebParam(name = "PerCod") Long PerCod) 
    {
        Retorno_MsgObj retorno = new Retorno_MsgObj();
        LoPersona lopersona = LoPersona.GetInstancia();
        ArrayList<SDT_PersonaEstudio> lstEstudios = new ArrayList<>();
        
        if(token == null)
        {
            retorno.setMensaje(new Mensajes("No se recibió ningún valor token", TipoMensaje.ERROR));
        }
        else
        {
            if(PerCod == null)
            {
                retorno.setMensaje(new Mensajes("No se recibió ningún parametro Alumno", TipoMensaje.ERROR));
            }
            else
            {
                lstEstudios = lopersona.ObtenerEstudios(PerCod);
                for(SDT_PersonaEstudio lstEst : lstEstudios)
                {
                    retorno.setObjeto(lstEst);
                }
                
            }
        }
        return retorno;
    }
    
    @WebMethod(operationName = "lstEstudiosPreviosPorAlumno")
    public Retorno_MsgObj lstEstudiosPreviosPorAlumno(@WebParam(name = "token") String token, @WebParam(name = "PerCod") Long PerCod)
    {
        Retorno_MsgObj retorno = new Retorno_MsgObj();
        LoCalendario loCalendario = LoCalendario.GetInstancia();
        
        if(token == null)
        {
            retorno.setMensaje(new Mensajes("No se recibió ningún valor token", TipoMensaje.ERROR));
        }
        else
        {
            if(PerCod == null)
            {
                retorno.setMensaje(new Mensajes("No se recibió ningún parametro Alumno", TipoMensaje.ERROR));
            }
            else
            {
                retorno = loCalendario.ObtenerListaPendiente(PerCod);
            }
        }
        return retorno;
    }
    
    @WebMethod(operationName = "escolaridadAlumno")
    public Retorno_MsgObj escolaridadAlumno(@WebParam(name = "token") String token, @WebParam(name = "PerCod") Long PerCod) 
    {
        Retorno_MsgObj retorno = new Retorno_MsgObj();
        LoPersona loPersona = LoPersona.GetInstancia();
        ArrayList<SDT_PersonaEstudio> lstEstudio = new ArrayList<>();
        Escolaridad esc = new Escolaridad();
        
        if(token == null)
        {
            retorno.setMensaje(new Mensajes("No se recibió ningún valor token", TipoMensaje.ERROR));
        }
        else
        {
            if(token == null)
            {
                retorno.setMensaje(new Mensajes("No se recibió ningún parametro Alumno", TipoMensaje.ERROR));
            }
            else
            {
                lstEstudio = loPersona.ObtenerEstudios(PerCod);
                for(SDT_PersonaEstudio est : lstEstudio)
                {
                    for (Escolaridad escolaridad : est.getEscolaridad())
                    {
                        esc.setEscCod(escolaridad.getEscCod());
                        esc.setEscCalVal(escolaridad.getEscCalVal());
                        esc.setEscCurVal(escolaridad.getEscCurVal());
                        esc.setEscFch(escolaridad.getEscFch());
                        esc.setObjFchMod(escolaridad.getObjFchMod());
                        esc.setIngresadaPor(escolaridad.getIngresadaPor());
                        esc.setAlumno(escolaridad.getAlumno());
                        esc.setCurso(escolaridad.getCurso());
                        esc.setMateria(escolaridad.getMateria());
                        esc.setModulo(escolaridad.getModulo());
                        
                        retorno.setObjeto(esc);
                    }
                }
            }
        }
        return retorno;
    }
    
//    @WebMethod(operationName = "documentoPorId")
//    public Retorno_MsgObj documentoPorId(@WebParam(name = "token") String token, @WebParam(name = "PeriEstCod") Long PeriEstCod) 
//    {
//        Retorno_MsgObj retorno = new Retorno_MsgObj();
//        LoPeriodo loPeriodo = LoPeriodo.GetInstancia();
//        List<PeriodoEstudioDocumento> lstObjeto = new ArrayList<>();
//        
//        
//        if(token == null)
//        {
//            retorno.setMensaje(new Mensajes("No se recibió ningún valor token", TipoMensaje.ERROR));
//        }
//        else
//        {
//            if(PeriEstCod == null)
//            {
//                retorno.setMensaje(new Mensajes("No se recibió ningún parametro Documento", TipoMensaje.ERROR));
//            }
//            else
//            {
//                retorno = (Retorno_MsgObj) loPeriodo.EstudioObtener(PeriEstCod);
//                if (!retorno.SurgioErrorObjetoRequerido()) 
//                {
//                    lstObjeto = ((PeriodoEstudio) retorno.getObjeto()).getLstDocumento();
//                    retorno.setObjeto(lstObjeto);
//                }
//            }
//        }
//        return retorno;
//    }
    
//    @WebMethod(operationName = "stDocumentosById")
//    public Retorno_MsgObj stDocumentosById(@WebParam(name = "token") String token, @WebParam(name = "UsuAlumno") Long UsuAlumno) 
//    {
//        Retorno_MsgObj retorno = new Retorno_MsgObj();
//        
//        return retorno;
//    }
}
