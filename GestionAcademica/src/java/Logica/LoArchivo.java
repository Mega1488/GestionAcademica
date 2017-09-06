/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.Archivo;
import Enumerado.TipoArchivo;
import Interfaz.InABMGenerico;
import Persistencia.PerManejador;
import SDT.SDT_Parameters;
import Utiles.Retorno_MsgObj;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author alvar
 */
public class LoArchivo implements InABMGenerico{

    private static LoArchivo instancia;

    private LoArchivo() {
    }
    
    public static LoArchivo GetInstancia(){
        if (instancia==null)
        {
            instancia   = new LoArchivo();
        }

        return instancia;
    }
    
    @Override
    public Object guardar(Object pObjeto) {
        
        Archivo archivo = (Archivo) pObjeto;
        
        archivo.setObjFchMod(new Date());
        
        PerManejador perManejador   = new PerManejador();
        Retorno_MsgObj retorno      = perManejador.guardar(archivo);

        if(!retorno.SurgioError())
        {
            archivo.setArcCod((Long) retorno.getObjeto());
            retorno.setObjeto(archivo);
        }
        
        return retorno;
    }

    @Override
    public Object actualizar(Object pObjeto) {
        
        PerManejador perManejador   = new PerManejador();

        return perManejador.actualizar(pObjeto);
    }

    @Override
    public Object eliminar(Object pObjeto) {
        PerManejador perManejador   = new PerManejador();
        return perManejador.eliminar(pObjeto);
    }

    @Override
    public Retorno_MsgObj obtener(Object pObjeto) {
        
        PerManejador perManejador   = new PerManejador();
        
        return perManejador.obtener((Long) pObjeto, Archivo.class);
    }

    @Override
    public Retorno_MsgObj obtenerLista() {
        PerManejador perManejador   = new PerManejador();
        
        return perManejador.obtenerLista("Archivo.findAll", null);
    }
    
    public Retorno_MsgObj obtenerListaByTipo(TipoArchivo tpoArch) {
        PerManejador perManejador   = new PerManejador();
        
        ArrayList<SDT_Parameters> lstParametros = new ArrayList<>();
        lstParametros.add(new SDT_Parameters(tpoArch, "ArcTpo"));
        
        return perManejador.obtenerLista("Archivo.findByTipo", lstParametros);
    }
    
   
}
