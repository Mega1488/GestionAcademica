/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.Modulo;
import Persistencia.PerModulo;
import java.util.List;

/**
 *
 * @author alvar
 */
public class LoModulo implements Interfaz.InModulo{
    
    private static LoModulo instancia;
    private PerModulo perModulo;

    private LoModulo() {
        perModulo  = new PerModulo();
    }
    
    public static LoModulo GetInstancia(){
        if (instancia==null)
        {
            instancia   = new LoModulo();
            
        }

        return instancia;
    }

    @Override
    public Object guardar(Modulo pObjeto) {
        return perModulo.guardar(pObjeto);
    }

    @Override
    public void actualizar(Modulo pObjeto) {
        perModulo.actualizar(pObjeto);
    }

    @Override
    public void eliminar(Modulo pObjeto) {
        perModulo.eliminar(pObjeto);
    }

    @Override
    public Modulo obtener(Object pCodigo) {
        return perModulo.obtener(pCodigo);
    }

    @Override
    public List<Modulo> obtenerLista() {
        return perModulo.obtenerLista();
    }
    
}
