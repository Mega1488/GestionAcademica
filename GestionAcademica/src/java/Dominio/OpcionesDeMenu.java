/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dominio;

import Entidad.Menu;
import Enumerado.TipoMenu;
import Logica.LoMenu;
import Utiles.Retorno_MsgObj;
import Utiles.Utilidades;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author alvar
 */
public class OpcionesDeMenu {
    private static OpcionesDeMenu instancia;
    
    private final ArrayList<Menu> lstAdministrador;
    private final ArrayList<Menu> lstDocente;
    private final ArrayList<Menu> lstAlumno;
    private final String urlSistema;
   
    private OpcionesDeMenu() {
        urlSistema = Utilidades.GetInstancia().GetUrlSistema();
        
        Retorno_MsgObj retorno = LoMenu.GetInstancia().obtenerLista();
        
        lstAdministrador = new ArrayList<>();
        lstAlumno = new ArrayList<>();
        lstDocente = new ArrayList<>();
        
        if(retorno.SurgioErrorListaRequerida())
        {
            CrearMenu();
        }
        else
        {
            LevantarMenu(retorno.getLstObjetos());
        }
        
        Collections.sort(lstAdministrador, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return ((Menu) o1).getMenOrd().compareTo(((Menu) o2).getMenOrd());
            }
        });
        
        Collections.sort(lstAlumno, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return ((Menu) o1).getMenOrd().compareTo(((Menu) o2).getMenOrd());
            }
        });
        
        Collections.sort(lstDocente, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return ((Menu) o1).getMenOrd().compareTo(((Menu) o2).getMenOrd());
            }
        });
        
    }
    
    public static OpcionesDeMenu GetInstancia(){
        if (instancia==null)
        {
            instancia   = new OpcionesDeMenu();
        }

        return instancia;
    }
    
    private void CrearMenu()
    {
        
        CargarAdministrador();
        CargarAlumno();
        CargarDocente();
        
        for(Menu menu : lstAdministrador)
        {
            LoMenu.GetInstancia().guardar(menu);
        }
        for(Menu menu : lstDocente)
        {
            LoMenu.GetInstancia().guardar(menu);
        }
        for(Menu menu : lstAlumno)
        {
            LoMenu.GetInstancia().guardar(menu);
        }
    }
    
    private void LevantarMenu(List<Object> lstMenu)
    {
        if(lstMenu.size() < 14)
        {
            for(Object objeto : lstMenu)
            {
                Menu menu = (Menu) objeto;
                LoMenu.GetInstancia().eliminar(menu);
            }
            
            CrearMenu();
        }
        else
        {
            for(Object objeto : lstMenu)
            {
                Menu menu = (Menu) objeto;
                
                if(menu.getMenTpo().equals(TipoMenu.ADMINISTRADOR)) lstAdministrador.add(menu);
                if(menu.getMenTpo().equals(TipoMenu.ALUMNO)) lstAlumno.add(menu);
                if(menu.getMenTpo().equals(TipoMenu.DOCENTE)) lstDocente.add(menu);

            }
        }
    }
    
    private void CargarAdministrador(){
        
        lstAdministrador.add(new Menu(TipoMenu.ADMINISTRADOR, urlSistema + "Definiciones/DefPersonaWW.jsp", "Personas", 1));
        lstAdministrador.add(new Menu(TipoMenu.ADMINISTRADOR, urlSistema + "Definiciones/DefCarreraWW.jsp", "Carreras", 2));
        lstAdministrador.add(new Menu(TipoMenu.ADMINISTRADOR, urlSistema + "Definiciones/DefCursoWW.jsp", "Cursos", 3));
        lstAdministrador.add(new Menu(TipoMenu.ADMINISTRADOR, urlSistema + "Definiciones/DefCalendarioGrid.jsp", "Calendario", 5));
        lstAdministrador.add(new Menu(TipoMenu.ADMINISTRADOR, urlSistema + "Definiciones/DefTipoEvaluacionWW.jsp", "Tipos de evaluación", 6));
        lstAdministrador.add(new Menu(TipoMenu.ADMINISTRADOR, urlSistema + "Definiciones/DefPeriodoWW.jsp", "Periodos", 4));
        lstAdministrador.add(new Menu(TipoMenu.ADMINISTRADOR, urlSistema + "Definiciones/DefSolicitudWW.jsp", "Solicitudes", 7));
        
        Menu sistema = new Menu(TipoMenu.ADMINISTRADOR, "#", "Sistema", 100);
        sistema.setMenIsParent(Boolean.TRUE);
        sistema.getLstSubMenu().add(new Menu(TipoMenu.ADMINISTRADOR, urlSistema + "Definiciones/DefParametro.jsp", "Parámetros", 1));
        sistema.getLstSubMenu().add(new Menu(TipoMenu.ADMINISTRADOR, urlSistema + "Definiciones/DefParametroEmailWW.jsp", "Parámetros de email", 2));
        sistema.getLstSubMenu().add(new Menu(TipoMenu.ADMINISTRADOR, urlSistema + "Definiciones/DefNotificacionWW.jsp", "Notificaciones", 3));
        sistema.getLstSubMenu().add(new Menu(TipoMenu.ADMINISTRADOR, urlSistema + "Administracion/Reportes.jsp", "Reportes", 4));
        sistema.getLstSubMenu().add(new Menu(TipoMenu.ADMINISTRADOR, urlSistema + "Administracion/DefSincWW.jsp", "Sincronización", 5));
        sistema.getLstSubMenu().add(new Menu(TipoMenu.ADMINISTRADOR, urlSistema + "Administracion/Importacion.jsp", "Importación", 6));
        sistema.getLstSubMenu().add(new Menu(TipoMenu.ADMINISTRADOR, urlSistema + "Administracion/DefWS_UserWW.jsp", "Web Services", 7));
        sistema.getLstSubMenu().add(new Menu(TipoMenu.ADMINISTRADOR, urlSistema + "Definiciones/DefVersion.jsp", "Versión", 8));
        sistema.getLstSubMenu().add(new Menu(TipoMenu.ADMINISTRADOR, urlSistema + "Administracion/BitacoraWW.jsp", "Bitácora", 9));
        
        lstAdministrador.add(sistema);
        
    }
    
    private void CargarAlumno(){
        lstAlumno.add(new Menu(TipoMenu.ALUMNO, urlSistema + "Alumno/Estudios.jsp", "Estudios", 1));
        lstAlumno.add(new Menu(TipoMenu.ALUMNO, urlSistema + "Alumno/Evaluaciones.jsp", "Evaluaciones", 2));
        lstAlumno.add(new Menu(TipoMenu.ALUMNO, urlSistema + "Alumno/Solicitudes.jsp", "Solicitudes", 4));
        lstAlumno.add(new Menu(TipoMenu.ALUMNO, urlSistema + "Alumno/Escolaridad.jsp", "Escolaridad", 3));
    }

    private void CargarDocente(){
        lstDocente.add(new Menu(TipoMenu.DOCENTE, urlSistema + "Docente/EstudiosDictados.jsp", "Estudios dictados", 1));
        lstDocente.add(new Menu(TipoMenu.DOCENTE, urlSistema + "Docente/EvalPendientes.jsp", "Evaluaciones Pendientes", 2));
    }

    public ArrayList<Menu> getLstAdministrador() {
        return lstAdministrador;
    }

    public ArrayList<Menu> getLstDocente() {
        return lstDocente;
    }


    public ArrayList<Menu> getLstAlumno() {
        return lstAlumno;
    }
    
    
}
