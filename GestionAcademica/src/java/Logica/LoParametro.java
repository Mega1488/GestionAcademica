/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.Parametro;
import Persistencia.PerManejador;
import Utiles.Retorno_MsgObj;

/**
 *
 * @author alvar
 */
public class LoParametro{
    
    private static LoParametro instancia;
    
    private Parametro parametro;

    private LoParametro() {
    }
    
    public static LoParametro GetInstancia(){
        if (instancia==null)
        {
            instancia   = new LoParametro();
            
        }

        return instancia;
    }

    public Object guardar(Parametro pObjeto) {
        
        pObjeto.setParCod(Long.valueOf("1"));
        
        PerManejador perManejador   = new PerManejador();
        Retorno_MsgObj retorno      = perManejador.guardar(pObjeto);

        if(!retorno.SurgioError())
        {
            pObjeto.setParCod((Long) retorno.getObjeto());
            retorno.setObjeto(pObjeto);
            this.parametro = pObjeto;
        }
        
        return retorno;
        
    }

    public void actualizar(Parametro pObjeto) {
        this.parametro = pObjeto;
        PerManejador perManejador   = new PerManejador();
        perManejador.actualizar(pObjeto);
    }

    public Parametro obtener() {
        
        if(this.parametro == null)
        {
            parametro         = new Parametro();
            PerManejador perManejador   = new PerManejador();

            Retorno_MsgObj retorno      = perManejador.obtener(Long.valueOf("1"), Parametro.class);
            if(!retorno.SurgioErrorObjetoRequerido())
            {
                parametro = (Parametro) retorno.getObjeto();
            }
            else
            {
                parametro = null;
            }
        }
        
        return parametro;
    }
    
    
}
