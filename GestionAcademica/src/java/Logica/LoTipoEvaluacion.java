/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.TipoEvaluacion;
import Interfaz.InTipoEvaluacion;
import Persistencia.PerManejador;
import Utiles.Retorno_MsgObj;
import java.util.Date;

/**
 *
 * @author alvar
 */
public class LoTipoEvaluacion implements InTipoEvaluacion{

    private static LoTipoEvaluacion instancia;

    private LoTipoEvaluacion() {
        System.err.println("Se crea instancia");
    }
    
    public static LoTipoEvaluacion GetInstancia(){
        if (instancia==null)
        {
            instancia   = new LoTipoEvaluacion();
        }

        return instancia;
    }
    

    @Override
    public Object guardar(TipoEvaluacion pTipoEvaluacion) {
        PerManejador perManejador   = new PerManejador();
        
        pTipoEvaluacion.setObjFchMod(new Date());
        
        Retorno_MsgObj retorno      = perManejador.guardar(pTipoEvaluacion);
        
        if(!retorno.SurgioError())
        {
            pTipoEvaluacion.setTpoEvlCod((Long) retorno.getObjeto());
            retorno.setObjeto(pTipoEvaluacion);
            
        }
        
        return retorno;
    }

    @Override
    public Object actualizar(TipoEvaluacion pTipoEvaluacion) {
        PerManejador perManejador   = new PerManejador();
        
        pTipoEvaluacion.setObjFchMod(new Date());

        return perManejador.actualizar(pTipoEvaluacion);
    }

    @Override
    public Object eliminar(TipoEvaluacion pTipoEvaluacion) {
        PerManejador perManejador   = new PerManejador();
        
        return perManejador.eliminar(pTipoEvaluacion);
    }

    @Override
    public Retorno_MsgObj obtener(Long pTpoEvlCod) {
        PerManejador perManejador   = new PerManejador();
        return perManejador.obtener(pTpoEvlCod, TipoEvaluacion.class);
    }

    @Override
    public Retorno_MsgObj obtenerLista() {
        PerManejador perManejador   = new PerManejador();
        return perManejador.obtenerLista("TipoEvaluacion.findAll", null);
    }
    
    private String getReportColumns(){
        return new TipoEvaluacion().GetReportColumns();
    }
    
    private String getReportData(){
        String data = "data: [";
        
        String contenido = "";
        for(Object objeto : this.obtenerLista().getLstObjetos())
        {
            TipoEvaluacion tpoEvl = (TipoEvaluacion) objeto;
            
            if(contenido.isEmpty())
            {
                contenido = tpoEvl.GetReportData();
            }
            else
            {
                contenido += ", " + tpoEvl.GetReportData();
            }
            
        }
        
        data += contenido;
        data += "]";
        return data;
    }
    
    
    public String getReportContent(){
        return "{" + this.getReportColumns() + ", " + this.getReportData() + "}";
    }
    
}
