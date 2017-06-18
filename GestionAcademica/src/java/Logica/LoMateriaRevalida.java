/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.MateriaRevalida;
import Persistencia.PerMateriaRevalida;
import java.util.List;

/**
 *
 * @author alvar
 */
public class LoMateriaRevalida implements Interfaz.InMateriaRevalida{
    
    private static LoMateriaRevalida instancia;
    private PerMateriaRevalida perMateriaRevalida;

    private LoMateriaRevalida() {
        perMateriaRevalida  = new PerMateriaRevalida();
    }
    
    public static LoMateriaRevalida GetInstancia(){
        if (instancia==null)
        {
            instancia   = new LoMateriaRevalida();
            
        }

        return instancia;
    }

    @Override
    public Object guardar(MateriaRevalida pObjeto) {
        return perMateriaRevalida.guardar(pObjeto);
    }

    @Override
    public void actualizar(MateriaRevalida pObjeto) {
        perMateriaRevalida.actualizar(pObjeto);
    }

    @Override
    public void eliminar(MateriaRevalida pObjeto) {
        perMateriaRevalida.eliminar(pObjeto);
    }

    @Override
    public MateriaRevalida obtener(Object pCodigo) {
        return perMateriaRevalida.obtener(pCodigo);
    }

    @Override
    public List<MateriaRevalida> obtenerLista() {
        return perMateriaRevalida.obtenerLista();
    }
    
}
