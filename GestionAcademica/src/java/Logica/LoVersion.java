/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.Version;
import Persistencia.PerVersion;

/**
 *
 * @author alvar
 */
public class LoVersion implements Interfaz.InVersion{
    
    private static LoVersion instancia;
    private PerVersion perVersion;

    private LoVersion() {
        perVersion  = new PerVersion();
    }
    
    public static LoVersion GetInstancia(){
        if (instancia==null)
        {
            instancia   = new LoVersion();
            
        }

        return instancia;
    }

    @Override
    public Object guardar(Version pObjeto) {
        return perVersion.guardar(pObjeto);
    }

    @Override
    public void actualizar(Version pObjeto) {
        perVersion.actualizar(pObjeto);
    }

    @Override
    public Version obtener(Object pCodigo) {
        return perVersion.obtener(pCodigo);
    }
    
    
}
