/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica.Notificacion;

import Entidad.Calendario;
import Entidad.Notificacion;
import Entidad.NotificacionConsulta;
import Entidad.NotificacionDestinatario;
import Entidad.Persona;
import Enumerado.NotificacionSistema;
import Enumerado.ObtenerDestinatario;
import Enumerado.TipoConsulta;
import Enumerado.TipoNotificacion;
import Enumerado.TipoRepeticion;
import Logica.LoCalendario;
import Logica.LoNotificacion;
import Logica.LoPersona;
import Utiles.Retorno_MsgObj;
import java.util.ArrayList;

/**
 *
 * @author alvar
 */
public class NotificacionesInternas {
    
    public void CargarNotificaciones(){
        
        //EVALUACION HABILITADA A INSCRIPCION
        LoNotificacion.GetInstancia().guardar(this.EVALUACION_HABILITADA_INSCRIPCION());
        
        //EVALUACION PROXIMA
        LoNotificacion.GetInstancia().guardar(this.EVALUACION_PROXIMA());
        
        //ESCOLARIDAD ACTUALIZADA
        LoNotificacion.GetInstancia().guardar(this.ESCOLARIDAD_ACTUALIZADA());
        
        //ALUMNOS INACTIVOS
        LoNotificacion.GetInstancia().guardar(this.ALUMNOS_INACTIVOS());
        
     }
    
    private Notificacion EVALUACION_HABILITADA_INSCRIPCION(){
        Notificacion notificacion = new Notificacion();
        notificacion.setNotNom(NotificacionSistema.EVALUACION_HABILITADA_INSCRIPCION.name());
        notificacion.setNotInt(Boolean.TRUE);       
        notificacion.setNotObtDest(ObtenerDestinatario.UNICA_VEZ);
        notificacion.setNotRepTpo(TipoRepeticion.DIAS);
        notificacion.setNotRepVal(1);
        notificacion.setNotTpo(TipoNotificacion.A_DEMANDA);

        notificacion.setNotAct(Boolean.TRUE);
        notificacion.setNotApp(Boolean.TRUE);
        notificacion.setNotEmail(Boolean.TRUE);
        notificacion.setNotWeb(Boolean.TRUE);
        
        notificacion.setNotDsc("Evaluación habilitada a inscripción");
        notificacion.setNotAsu("[%=EVALUACION] de [%=ESTUDIO]");
        notificacion.setNotCon("<p>Se puede inscribir a [%=EVALUACION] de [%=ESTUDIO] desde [%=DESDE], hasta: [%=HASTA]</p>");
        
        return notificacion;
    }
    
    private Notificacion EVALUACION_PROXIMA(){
        Notificacion notificacion = new Notificacion();
        notificacion.setNotNom(NotificacionSistema.EVALUACION_PROXIMA.name());
        notificacion.setNotInt(Boolean.TRUE);       
        notificacion.setNotObtDest(ObtenerDestinatario.POR_CADA_REGISTRO);
        notificacion.setNotRepTpo(TipoRepeticion.DIAS);
        notificacion.setNotRepVal(1);
        notificacion.setNotTpo(TipoNotificacion.A_DEMANDA);

        notificacion.setNotAct(Boolean.TRUE);
        notificacion.setNotApp(Boolean.TRUE);
        notificacion.setNotEmail(Boolean.TRUE);
        notificacion.setNotWeb(Boolean.TRUE);
        
        notificacion.setNotDsc("Evaluación próxima a la fecha");
        notificacion.setNotAsu("Se aproxima [%=EVALUACION_NOMBRE] de [%=EVALUACION_ESTUDIO]");
        notificacion.setNotCon("<p>[%=EVALUACION_NOMBRE] de [%=EVALUACION_ESTUDIO] el día [%=CALENDARIO_FCH]</p>");
        
        
        NotificacionConsulta contenido = new NotificacionConsulta();
        contenido.setNotCnsTpo(TipoConsulta.CONSULTA);
        contenido.setNotificacion(notificacion);
        contenido.setNotCnsSQL("SELECT \n" +
                                "        C.CalCod AS CALENDARIO_COD  \n" +
                                "        ,C.CalFch AS CALENDARIO_FCH\n" +
                                "        ,C.EvlCod AS EVALUACION_CODIGO\n" +
                                "        ,E.EvlNom AS EVALUACION_NOMBRE\n" +
                                "        ,(\n" +
                                "                CASE \n" +
                                "                        WHEN E.MatEvlMatCod IS NOT NULL THEN (SELECT MatNom FROM MATERIA MAT WHERE MAT.MatCod = E.MatEvlMatCod)\n" +
                                "                        WHEN E.ModEvlModCod IS NOT NULL THEN (SELECT ModNom FROM MODULO MD WHERE MD.ModCod = E.ModEvlModCod)\n" +
                                "                        WHEN E.CurEvlCurCod IS NOT NULL THEN (SELECT CurNom FROM CURSO CUR WHERE CUR.CurCod = E.CurEvlCurCod)\n" +
                                "                 END\n" +
                                "        ) AS EVALUACION_ESTUDIO\n" +
                                "        \n" +
                                "FROM\n" +
                                "        CALENDARIO C\n" +
                                "        ,EVALUACION E\n" +
                                "WHERE \n" +
                                "        C.EvlCod = E.EvlCod\n" +
                                "        AND CalFch = curdate() + (SELECT ParDiaEvlPrv FROM PARAMETRO P WHERE P.ParCod = 1)");
        
        notificacion.getLstConsulta().add(contenido);
        
        
        NotificacionConsulta destinatario = new NotificacionConsulta();
        destinatario.setNotCnsTpo(TipoConsulta.INC_DESTINATARIO);
        destinatario.setNotificacion(notificacion);
        destinatario.setNotCnsSQL("SELECT 1 AS TIPO, AluPerCod AS DESTINATARIO FROM CALENDARIO_ALUMNO WHERE CalCod = [%=CALENDARIO_COD]");
        
        notificacion.getLstConsulta().add(destinatario);
        
        return notificacion;
    }
    
    private Notificacion ESCOLARIDAD_ACTUALIZADA(){
        Notificacion notificacion = new Notificacion();
        notificacion.setNotNom(NotificacionSistema.ESCOLARIDAD_ACTUALIZADA.name());
        notificacion.setNotInt(Boolean.TRUE);       
        notificacion.setNotObtDest(ObtenerDestinatario.POR_CADA_REGISTRO);
        notificacion.setNotRepTpo(TipoRepeticion.DIAS);
        notificacion.setNotRepVal(1);
        notificacion.setNotTpo(TipoNotificacion.A_DEMANDA);

        notificacion.setNotAct(Boolean.TRUE);
        notificacion.setNotApp(Boolean.TRUE);
        notificacion.setNotEmail(Boolean.TRUE);
        notificacion.setNotWeb(Boolean.TRUE);
        
        notificacion.setNotDsc("Escolaridad actualizada");
        notificacion.setNotAsu("Escolaridad actualizada");
        notificacion.setNotCon("<p>Se actualizó su escolaridad ([%=ESTUDIO]), puede consultarla desde la aplicación</p>");
        
        
        NotificacionConsulta contenido = new NotificacionConsulta();
        contenido.setNotCnsTpo(TipoConsulta.CONSULTA);
        contenido.setNotificacion(notificacion);
        contenido.setNotCnsSQL("SELECT \n" +
                                "        EscAluPerCod AS ALUMNO\n" +
                                "        ,CASE\n" +
                                "                WHEN EscMatCod IS NOT NULL THEN (SELECT MatNom FROM MATERIA MAT WHERE MAT.MatCod = EscMatCod)\n" +
                                "                WHEN EscModCod IS NOT NULL THEN (SELECT ModNom FROM MODULO MD WHERE MD.ModCod = EscModCod)\n" +
                                "                WHEN EscCurCod IS NOT NULL THEN (SELECT CurNom FROM CURSO CUR WHERE CUR.CurCod = EscCurCod)\n" +
                                "         END AS ESTUDIO\n" +
                                "FROM \n" +
                                "        ESCOLARIDAD \n" +
                                "WHERE \n" +
                                "        ESCFCH = CURDATE()");
        
        notificacion.getLstConsulta().add(contenido);
        
        
        NotificacionConsulta destinatario = new NotificacionConsulta();
        destinatario.setNotCnsTpo(TipoConsulta.INC_DESTINATARIO);
        destinatario.setNotificacion(notificacion);
        destinatario.setNotCnsSQL("SELECT 1 AS TIPO, [%=ALUMNO] AS DESTINATARIO");
        
        notificacion.getLstConsulta().add(destinatario);
        
        
        return notificacion;
    }
    
    private Notificacion ALUMNOS_INACTIVOS(){
        Notificacion notificacion = new Notificacion();
        notificacion.setNotNom(NotificacionSistema.ALUMNOS_INACTIVOS.name());
        notificacion.setNotInt(Boolean.TRUE);       
        notificacion.setNotObtDest(ObtenerDestinatario.UNICA_VEZ);
        notificacion.setNotRepTpo(TipoRepeticion.MESES);
        notificacion.setNotRepVal(1);
        notificacion.setNotTpo(TipoNotificacion.A_DEMANDA);

        notificacion.setNotAct(Boolean.TRUE);
        notificacion.setNotApp(Boolean.TRUE);
        notificacion.setNotEmail(Boolean.TRUE);
        notificacion.setNotWeb(Boolean.TRUE);
        
        notificacion.setNotDsc("Alumnos inactivos");
        notificacion.setNotAsu("Falta poco para tu titulo!");
        notificacion.setNotCon("<p>Puedes dar las evaluaciones para poder conseguir tu titulo</p>");
        
        return notificacion;
    }
    
    //--------------------------------------------------------------------------
    //EJECUTAR
    //--------------------------------------------------------------------------
    
    public void EjecutarNotificacionesInternas(){
        Retorno_MsgObj retorno = LoNotificacion.GetInstancia().obtenerLista();
        
        if(!retorno.SurgioErrorListaRequerida())
        {
            for(Object objeto : retorno.getLstObjetos())
            {
                Notificacion notificacion = (Notificacion) objeto;
                
                if(notificacion.getNotAct() && notificacion.getNotInt())
                {
                    if(notificacion.NotificarAutomaticamente())
                    {
                        if(notificacion.getNotNom().equals(NotificacionSistema.EVALUACION_HABILITADA_INSCRIPCION.name()))
                        {
                            this.Notificar_EVALUACION_HABILITADA_INSCRIPCION(notificacion);
                        }

                        if(notificacion.getNotNom().equals(NotificacionSistema.EVALUACION_PROXIMA.toString()))
                        {
                            this.Notificar_EVALUACION_PROXIMA(notificacion);
                        }

                        if(notificacion.getNotNom().equals(NotificacionSistema.ESCOLARIDAD_ACTUALIZADA.toString()))
                        {
                            this.Notificar_ESCOLARIDAD_ACTUALIZADA(notificacion);
                        }

                        if(notificacion.getNotNom().equals(NotificacionSistema.ALUMNOS_INACTIVOS.toString()))
                        {
                            this.Notificar_ALUMNOS_INACTIVOS(notificacion);
                        }
                    }
                }
            }
        }
    }
    
    //--------------------------------------------------------------------------
    
    private void Notificar_EVALUACION_HABILITADA_INSCRIPCION(Notificacion notificacion){
        ManejoNotificacion notManager = new ManejoNotificacion();
        
        ArrayList<NotificacionDestinatario> lstDstOriginal = new ArrayList<>();
        for(NotificacionDestinatario dst : notificacion.getLstDestinatario())
        {
            lstDstOriginal.add(dst);
        }
        
        String asunto = notificacion.getNotAsu();
        String conten = notificacion.getNotCon();
        
        Retorno_MsgObj retorno = LoCalendario.GetInstancia().ObtenerListaInscripcion();
        
        if(!retorno.SurgioErrorListaRequerida())
        {
            for(Object objeto : retorno.getLstObjetos())
            {
                Calendario calendar = (Calendario) objeto;
                
                Retorno_MsgObj lstPersona = LoPersona.GetInstancia().obtenerLista();
                
                if(!lstPersona.SurgioErrorListaRequerida())
                {
                    for(Object obj : lstPersona.getLstObjetos())
                    {
                        Persona persona = (Persona) obj;

                        if(persona.getPerEsAlu())
                        {
                            if(!LoPersona.GetInstancia().PersonaAproboEstudio(persona.getPerCod(), calendar.getEvaluacion().getMatEvl(), calendar.getEvaluacion().getModEvl(), calendar.getEvaluacion().getCurEvl()) || !LoPersona.GetInstancia().PersonaRevalidaMateria(persona.getPerCod(), calendar.getEvaluacion().getMatEvl()))
                            {
                                if(LoCalendario.GetInstancia().AlumnoPuedeDarExamen(persona.getPerCod(), calendar.getEvaluacion().getMatEvl(), calendar.getEvaluacion().getModEvl(), calendar.getEvaluacion().getCurEvl()))
                                {
                                    notificacion = this.ArmarDestinatario(notificacion, persona);
                                }
                            }
                        }
                    }
                }
                
                notificacion.setNotAsu(asunto);
                notificacion.setNotCon(conten);
                
                notificacion.setNotAsu(notificacion.getNotAsu().replace("[%=EVALUACION]", calendar.getEvaluacion().getEvlNom()));
                notificacion.setNotAsu(notificacion.getNotAsu().replace("[%=ESTUDIO]", calendar.getEvaluacion().getEstudioNombre()));
                
                notificacion.setNotCon(notificacion.getNotCon().replace("[%=EVALUACION]", calendar.getEvaluacion().getEvlNom()));
                notificacion.setNotCon(notificacion.getNotCon().replace("[%=ESTUDIO]", calendar.getEvaluacion().getEstudioNombre()));
                notificacion.setNotCon(notificacion.getNotCon().replace("[%=DESDE]", calendar.getEvlInsFchDsd().toString()));
                notificacion.setNotCon(notificacion.getNotCon().replace("[%=HASTA]", calendar.getEvlInsFchHst().toString()));
                
                
                notManager.EjecutarNotificacion(notificacion);
            }
        }
        
        notificacion.setNotAsu(asunto);
        notificacion.setNotCon(conten);

        notificacion = (Notificacion) LoNotificacion.GetInstancia().obtener(notificacion.getNotCod()).getObjeto();
        notificacion.setLstDestinatario(lstDstOriginal);
        LoNotificacion.GetInstancia().actualizar(notificacion);
        
    }
    
    //--------------------------------------------------------------------------
    
    private void Notificar_EVALUACION_PROXIMA(Notificacion notificacion){
        ManejoNotificacion notManager = new ManejoNotificacion();
        notManager.EjecutarNotificacion(notificacion);
    }
    
    //--------------------------------------------------------------------------
    
    private void Notificar_ESCOLARIDAD_ACTUALIZADA(Notificacion notificacion){
        
    }
    
    //--------------------------------------------------------------------------
    
    private void Notificar_ALUMNOS_INACTIVOS(Notificacion notificacion){
        
    }
    
    //--------------------------------------------------------------------------
    
    private Notificacion ArmarDestinatario(Notificacion notificacion, Persona persona){
        NotificacionDestinatario dest = new NotificacionDestinatario();
        
        dest.setPersona(persona);
        dest.setNotificacion(notificacion);
        
        notificacion.getLstDestinatario().add(dest);
        
        return notificacion;
    }
}
