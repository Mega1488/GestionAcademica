/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.BitacoraProceso;
import Enumerado.Proceso;
import Enumerado.TipoMensaje;
import Interfaz.InABMGenerico;
import Persistencia.PerManejador;
import SDT.SDT_Parameters;
import Utiles.Mensajes;
import Utiles.Retorno_MsgObj;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author alvar
 */
public class LoBitacora implements InABMGenerico{

    private static LoBitacora instancia;

    private LoBitacora() {
    }
    
    public static LoBitacora GetInstancia(){
        if (instancia==null)
        {
            instancia   = new LoBitacora();
        }

        return instancia;
    }
    
    @Override
    public Object guardar(Object pObjeto) {
        
        BitacoraProceso bitacora = (BitacoraProceso) pObjeto;
        
        PerManejador perManejador   = new PerManejador();
        Retorno_MsgObj retorno      = perManejador.guardar(bitacora);

        if(!retorno.SurgioError())
        {
            bitacora.setBitCod((Long) retorno.getObjeto());
            retorno.setObjeto(bitacora);
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
        
        return perManejador.obtener((Long) pObjeto, BitacoraProceso.class);
    }

    @Override
    public Retorno_MsgObj obtenerLista() {
        PerManejador perManejador   = new PerManejador();
        
        return perManejador.obtenerLista("BitacoraProceso.findAll", null);
    }
    
    public Retorno_MsgObj obtenerListaByProceso(Proceso proceso) {
        PerManejador perManejador   = new PerManejador();
        
        ArrayList<SDT_Parameters> lstParametros = new ArrayList<>();
        lstParametros.add(new SDT_Parameters(proceso, "BitProceso"));
        
        return perManejador.obtenerLista("BitacoraProceso.findByProceso", lstParametros);
    }
   
    public Retorno_MsgObj depurar(){
        Retorno_MsgObj retorno = this.obtenerLista();
        
        if(!retorno.SurgioError())
        {
            Long regEliminados = 0L;
            for(Object objeto : retorno.getLstObjetos())
            {
                this.eliminar(objeto);

                regEliminados += 1;
            }
            
            retorno.setMensaje(new Mensajes("Registros eliminados: " + regEliminados, TipoMensaje.MENSAJE));
        }
        
        return retorno;
    }
   
    public void NuevoMensaje(Mensajes mensaje, Proceso proceso){
        BitacoraProceso bit =  new BitacoraProceso(proceso, new Date(), mensaje.getMensaje(), mensaje.getTipoMensaje());
        this.guardar(bit);
    }
   
}
