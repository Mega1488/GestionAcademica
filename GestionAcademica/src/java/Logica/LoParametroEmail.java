/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.Parametro;
import Entidad.ParametroEmail;
import Enumerado.Constantes;
import Moodle.Criteria;
import Moodle.MoodleRestUser;
import Moodle.MoodleUser;
import Moodle.UserCustomField;
import Persistencia.PerParametroEmail;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author alvar
 */
public class LoParametroEmail implements Interfaz.InParametroEmail{
    
    private static LoParametroEmail   instancia;
    private final PerParametroEmail   perParametroEmail;

    private LoParametroEmail() {
        perParametroEmail          = new PerParametroEmail();
    }
    
    public static LoParametroEmail GetInstancia(){
        if (instancia==null)
        {
            instancia   = new LoParametroEmail();
            
        }

        return instancia;
    }

    @Override
    public Object guardar(ParametroEmail pObjeto) {
        return perParametroEmail.guardar(pObjeto);
    }

    @Override
    public void actualizar(ParametroEmail pObjeto) {
        perParametroEmail.actualizar(pObjeto);
    }

    @Override
    public void eliminar(ParametroEmail pObjeto) {
        perParametroEmail.eliminar(pObjeto);
    }

    @Override
    public ParametroEmail obtener(Object pCodigo) {
        return perParametroEmail.obtener(pCodigo);
    }
    

    @Override
    public List<ParametroEmail> obtenerLista() {
        return perParametroEmail.obtenerLista();
    }
   
}
