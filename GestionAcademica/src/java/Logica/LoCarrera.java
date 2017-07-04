/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Entidad.Carrera;
import Moodle.MoodleCategory;
import Persistencia.PerCarrera;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aa
 */
public class LoCarrera implements Interfaz.InCarrera{
    private static LoCarrera        instancia;
    private final PerCarrera        perCarrera;
    private final MoodleCategory    mdlCategory;
    
    
    private LoCarrera(){
        perCarrera  = new PerCarrera();
        mdlCategory = new MoodleCategory();
    }
    
    public static LoCarrera GetInstancia(){
        if(instancia == null){
            instancia = new LoCarrera();
        }
        return instancia;
    }
    
    @Override
    public Object guardar(Carrera pCarrera) {
        return perCarrera.guardar(pCarrera);
    }

    @Override
    public Object actualizar(Carrera pCarrera) {
        boolean error = false;
        perCarrera.actualizar(pCarrera);
        return error;
    }

    @Override
    public Object eliminar(Carrera pCarrera) {
        boolean error = false;
        perCarrera.eliminar(pCarrera);
        return error;
    }

    @Override
    public Carrera obtener(Carrera pCarrera) {
        return perCarrera.obtener(pCarrera);
    }

    @Override
    public Carrera obtenerByMdlUsr(String pMdlUsr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Carrera> obtenerLista() {
        return perCarrera.obtenerLista();
    }
    
    //----------------------------------------------------------------------------------------------------
    //-Modle
    //----------------------------------------------------------------------------------------------------
    
//    public void SincronizarUsuariosMoodleSistema()
//    {
//        List<Carrera> lstCarreras = this.MdlObtenerCarreras();
//        
//        for(Carrera carrera : lstCarreras)
//        {
//            if(!carrera.getCarCatCod().equals("") )
//            {
//                if(carrera.getCarCod() == null)
//                {
//                    this.guardar(carrera);
//                }
//                else
//                {
////                    persona.setPerPass(seguridad.cryptWithMD5("admin"));
//                    this.actualizar(carrera);
//                }
//            }
//        }
//    }
    
//    private List<Carrera> MdlObtenerCarreras()
//    {
//        List<Carrera> lstCarreras = new ArrayList<>();
//        
//        try
//        {
//            Criteria criteria = new Criteria();
//
//            criteria.setKey("name");
//            criteria.setValue("");
//
//            Criteria[] lstCriteria   = new Criteria[1];
//            lstCriteria[0]           = criteria;
//            MoodleUser[] lstUsr = mdlRestUser.__getUsers(param.getParUrlMdl() + Constantes.URL_FOLDER_SERVICIO_MDL.getValor(), param.getParMdlTkn(), lstCriteria);
//
//
//             for(MoodleUser usr: lstUsr)
//             {
//                Persona persona = Mdl_UsuarioToPersona(usr);
//                lstPersonas.add(persona);
//             }
//        }
//        catch(Exception ex)
//        {
//            ex.printStackTrace();
//        }
//
//
//        return lstCarreras;
//    }
    
    public void guardarCarreraCategoria()
    {
    
    }
    
    public void ObtenerIdCategoria()
    {
//        MoodleCategory Cat = new MoodleCategory();
        
        
    }
}
