/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Enumerado.BandejaEstado;
import Enumerado.BandejaTipo;
import Interfaz.InABMGenerico;
import Persistencia.PerBandeja;
import Utiles.Retorno_MsgObj;

/**
 *
 * @author alvar
 */
public class LoBandeja implements InABMGenerico{

    private static LoBandeja instancia;
    private final PerBandeja perBandeja;

    private LoBandeja() {
        perBandeja  = new PerBandeja();
    }
    
    public static LoBandeja GetInstancia(){
        if (instancia==null)
        {
            instancia   = new LoBandeja();
        }

        return instancia;
    }
    

    @Override
    public Object guardar(Object pObjeto) {
        return perBandeja.guardar(pObjeto);
    }

    @Override
    public Object actualizar(Object pObjeto) {
        return perBandeja.actualizar(pObjeto);
    }

    @Override
    public Object eliminar(Object pObjeto) {
        return perBandeja.eliminar(pObjeto);
    }

    @Override
    public Retorno_MsgObj obtener(Object pObjeto) {
        return perBandeja.obtener(pObjeto);
    }

    @Override
    public Retorno_MsgObj obtenerLista() {
        return perBandeja.obtenerLista();
    }
    
    public Retorno_MsgObj obtenerListaByTipoEstado(Long PerCod, BandejaTipo NotBanTpo, BandejaEstado NotBanEst) {
        return perBandeja.obtenerListaByTipoEstado(PerCod, NotBanTpo, NotBanEst);
    }
    
   
}
