/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.Curso;
import Entidad.Modulo;
import Entidad.TipoEvaluacion;
import Enumerado.TipoPeriodo;
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
        
        CargarTipoEvaluacion();
        CargarCurso();
        
        CargarModulo();
        
        

    }
    
    private void CargarTipoEvaluacion(){
        LoTipoEvaluacion lTpoEval = LoTipoEvaluacion.GetInstancia();
        
        TipoEvaluacion tpoEval = new TipoEvaluacion();
/*
        tpoEval.setTpoEvlNom("Parcial");
        tpoEval.setObjFchMod(new Date());
        tpoEval.setTpoEvlExm(Boolean.FALSE);
        tpoEval.setTpoEvlInsAut(Boolean.FALSE);

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
  */      
    }
    
    private void CargarCurso(){
        LoCurso lCurso = LoCurso.GetInstancia();
       
        Curso curso = new Curso();
        
        curso.setCurCatCod(1);
        curso.setCurCrt("CTC");
        curso.setCurDsc("Este es un curso creado dinamicamente para testeo");
        curso.setCurFac("Ingenieria");
        curso.setCurNom("Soporte IT");
        
        lCurso.guardar(curso);
        
    }
    
    private void CargarModulo(){
        LoCurso lCurso = LoCurso.GetInstancia();
        LoModulo lModulo = LoModulo.GetInstancia();
        
        Curso curso = lCurso.obtener(1);
        
        Modulo modulo = new Modulo();
                
        //modulo.setCurCod(1);
        modulo.setCurso(curso);
        modulo.setModCntHor(Double.MIN_NORMAL);
        modulo.setModDsc("tyest");
        modulo.setModNom("aaaa");
        modulo.setModPerVal(Double.MIN_NORMAL);
        modulo.setModTpoPer(TipoPeriodo.MODULAR);
        
        lModulo.guardar(modulo);
        
        
    }
    
    
}
