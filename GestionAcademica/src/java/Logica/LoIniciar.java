/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.TipoEvaluacion;
import Utiles.Utilidades;
import java.util.Date;

/**
 *
 * @author alvar
 */
public class LoIniciar {

    private Utilidades utilidades;
    
    public LoIniciar() {
        utilidades = Utilidades.GetInstancia();
    }
    
    public void Iniciar(){
        //Validar si se debe hacer carga inicial
        //boolean cargarDatos = this.ValidarVersion();
        
        boolean cargarDatos = true;
        if (cargarDatos)
        {
            this.CargarDatosIniciales();
        }
    }
    
    private void CargarDatosIniciales(){
        TipoEvaluacion tpoEval = new TipoEvaluacion();

        tpoEval.setTpoEvlNom("Parcial");
        tpoEval.setObjFchMod(new Date());
        tpoEval.setTpoEvlExm(Boolean.FALSE);
        tpoEval.setTpoEvlInsAut(Boolean.FALSE);

        LogTipoEvaluacion lTpoEval = LogTipoEvaluacion.GetInstancia();
        tpoEval.setTpoEvlCod(lTpoEval.guardar(tpoEval));
        utilidades.MostrarMensajeConsola(this.getClass().getSimpleName(), tpoEval.toString());
        
        
        tpoEval = new TipoEvaluacion();

        tpoEval.setTpoEvlNom("Obligatorio");
        tpoEval.setObjFchMod(new Date());
        tpoEval.setTpoEvlExm(Boolean.FALSE);
        tpoEval.setTpoEvlInsAut(Boolean.FALSE);

        tpoEval.setTpoEvlCod(lTpoEval.guardar(tpoEval));
        utilidades.MostrarMensajeConsola(this.getClass().getSimpleName(), tpoEval.toString());
        
        
        tpoEval = new TipoEvaluacion();

        tpoEval.setTpoEvlNom("Examen");
        tpoEval.setObjFchMod(new Date());
        tpoEval.setTpoEvlExm(Boolean.TRUE);
        tpoEval.setTpoEvlInsAut(Boolean.FALSE);

        tpoEval.setTpoEvlCod(lTpoEval.guardar(tpoEval));
        utilidades.MostrarMensajeConsola(this.getClass().getSimpleName(), tpoEval.toString());
        
        tpoEval = lTpoEval.obtener(2);

        tpoEval.setTpoEvlNom(tpoEval.getTpoEvlNom() + " --- ");

        lTpoEval.actualizar(tpoEval);
        utilidades.MostrarMensajeConsola(this.getClass().getSimpleName(), tpoEval.toString());
        
        
        tpoEval = lTpoEval.obtener(1);

        lTpoEval.eliminar(tpoEval);
        utilidades.MostrarMensajeConsola(this.getClass().getSimpleName(), tpoEval.toString());

    }
}
