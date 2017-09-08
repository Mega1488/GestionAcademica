/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Dominio.SincHelper;
import Entidad.BitacoraProceso;
import Entidad.Curso;
import Entidad.Inscripcion;
import Entidad.Persona;
import Entidad.PlanEstudio;
import Enumerado.Extensiones;
import Enumerado.Proceso;
import Enumerado.TipoMensaje;
import Utiles.Mensajes;
import Utiles.Retorno_MsgObj;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author alvar
 */
public class LoImportacion {
    private static LoImportacion instancia;
    private final SimpleDateFormat yMd_HMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    private LoImportacion() {
    }
    
    public static LoImportacion GetInstancia(){
        if (instancia==null)
        {
            instancia   = new LoImportacion();
        }

        return instancia;
    }
    
    
    public Retorno_MsgObj ImportarPersonasPlan(Long PlaEstCod, String filePath){
        Retorno_MsgObj retorno      = LoCarrera.GetInstancia().PlanEstudioObtener(PlaEstCod);
        Integer personasImportadas      = 0;
        Integer inscripcionesImportadas = 0;
                
        if(!retorno.SurgioErrorObjetoRequerido())
        {
            PlanEstudio plan = (PlanEstudio) retorno.getObjeto();
            
            retorno = this.ImportarPersonas(filePath);
            
            if(!retorno.SurgioErrorListaRequerida())
            {
                for(Object objeto : retorno.getLstObjetos())
                {
                    Persona persona = (Persona) objeto;
                    
                    if(persona.getPerCod() != null)
                    {
                        personasImportadas += 1;
                        
                        try
                        {
                            Calendar fchIns = Calendar.getInstance();
                            fchIns.setTime(persona.getFechaInicio());

                            Inscripcion ins = new Inscripcion();
                            ins.setAlumno(persona);
                            ins.setInsGenAnio(fchIns.get(Calendar.YEAR));
                            ins.setAluFchInsc(fchIns.getTime());
                            ins.setPlanEstudio(plan);

                            Retorno_MsgObj insRet = (Retorno_MsgObj) LoInscripcion.GetInstancia().guardar(ins);
                            if(!insRet.SurgioError())
                            {
                                inscripcionesImportadas += 1;
                            }

                            //-MENSAJE PARA BITACORA
                            retorno.getLstMensajes().add(new Mensajes("Inscripción de " 
                                    + persona.getPerCod()
                                    + " - " + persona.getNombreCompleto()
                                    + ", al plan " 
                                    + plan.getCarreraPlanNombre()
                                    + ". Resultado: " 
                                    + (insRet.SurgioError() ? "ERROR : " + insRet.getMensaje().getMensaje() : "CORRECTO")
                                    , insRet.getMensaje().getTipoMensaje()));

                        }
                        catch(NullPointerException ex)
                        {
                            retorno.getLstMensajes().add(new Mensajes("Error: \n" + ex, TipoMensaje.ERROR));
                    
                            Logger.getLogger(SincHelper.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
        
        retorno.setMensaje(new Mensajes("Personas importadas: " + personasImportadas
                + " - Inscripciones realizadas: " + inscripcionesImportadas
                , (retorno.SurgioErrorListaMensajes() ? TipoMensaje.ADVERTENCIA : TipoMensaje.MENSAJE)));
        
        this.ImpactarEnBitacora(retorno);
        
        return retorno;
    }
    
    public Retorno_MsgObj ImportarPersonasCurso(Long CurCod, String filePath){
        Retorno_MsgObj retorno          = LoCurso.GetInstancia().obtener(CurCod);
        Integer personasImportadas      = 0;
        Integer inscripcionesImportadas = 0;
                
        if(!retorno.SurgioErrorObjetoRequerido())
        {
            Curso curso = (Curso) retorno.getObjeto();
            
            retorno = this.ImportarPersonas(filePath);
            
            if(!retorno.SurgioErrorListaRequerida())
            {
                for(Object objeto : retorno.getLstObjetos())
                {
                    Persona persona = (Persona) objeto;
                    if(persona.getPerCod() != null)
                    {
                        personasImportadas += 1;
                        
                        try
                        {
                            Calendar fchIns = Calendar.getInstance();
                            fchIns.setTime(persona.getFechaInicio());

                            Inscripcion ins = new Inscripcion();
                            ins.setAlumno(persona);
                            ins.setInsGenAnio(fchIns.get(Calendar.YEAR));
                            ins.setAluFchInsc(fchIns.getTime());
                            ins.setCurso(curso);

                            Retorno_MsgObj insRet = (Retorno_MsgObj) LoInscripcion.GetInstancia().guardar(ins);
                            if(!insRet.SurgioError())
                            {
                                inscripcionesImportadas += 1;
                            }

                            //-MENSAJE PARA BITACORA
                            retorno.getLstMensajes().add(new Mensajes("Inscripción de " 
                                    + persona.getPerCod()
                                    + " - " + persona.getNombreCompleto()
                                    + ", al curso " 
                                    + curso.getCurNom()
                                    + ". Resultado: " 
                                    + (insRet.SurgioError() ? "ERROR : " + insRet.getMensaje().getMensaje() : "CORRECTO")
                                    , insRet.getMensaje().getTipoMensaje()));
                            
                        }
                        catch(NullPointerException ex)
                        {
                            retorno.getLstMensajes().add(new Mensajes("Error: \n" + ex, TipoMensaje.ERROR));
                    
                            Logger.getLogger(SincHelper.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        
                    }
                }
            }
        }
        
        retorno.setMensaje(new Mensajes("Personas importadas: " + personasImportadas
                + " - Inscripciones realizadas: " + inscripcionesImportadas
                , (retorno.SurgioErrorListaMensajes() ? TipoMensaje.ADVERTENCIA : TipoMensaje.MENSAJE)));
        
        this.ImpactarEnBitacora(retorno);
        
        return retorno;
    }
    
    public Retorno_MsgObj ImportarPersonasEscolaridad(String filePath){
        
        return null;
    }
    
    private Retorno_MsgObj ImportarPersonas(String filePath){
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Importacion de personas", TipoMensaje.MENSAJE));
        
        File archivo = new File(filePath);
        
        if(archivo.exists())
        {
            if(FilenameUtils.isExtension(archivo.getName(), Extensiones.XLS.getValor()))
            {
                try {
                    retorno = this.ProcesarXLS(archivo);
                } catch (IOException ex) {
                    retorno.setMensaje(new Mensajes("Error al leer archivo: " + ex, TipoMensaje.ERROR));
                    Logger.getLogger(LoImportacion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            if(FilenameUtils.isExtension(archivo.getName(), Extensiones.XLSX.getValor()))
            {
                try {
                    retorno = this.ProcesarXLSX(archivo);
                } catch (IOException ex) {
                    retorno.setMensaje(new Mensajes("Error al leer archivo: " + ex, TipoMensaje.ERROR));
                    Logger.getLogger(LoImportacion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            
            //PROCESAR PERSONAS
            if(!retorno.SurgioError())
            {
                for(Object objeto : retorno.getLstObjetos())
                {
                    Persona persona = (Persona) objeto;
                    
                    Retorno_MsgObj perRet = (Retorno_MsgObj) LoPersona.GetInstancia().guardar(persona);
                    
                    if(!perRet.SurgioError())
                    {
                        persona = (Persona) perRet.getObjeto();
                    }
                    
                    //-MENSAJE PARA BITACORA
                    retorno.getLstMensajes().add(new Mensajes("Registro de " 
                            + persona.getPerCod()
                            + " - " + persona.getNombreCompleto()
                            + ". Resultado: " 
                            + (perRet.SurgioError() ? "ERROR : " + perRet.getMensaje().getMensaje() : "CORRECTO")
                            , perRet.getMensaje().getTipoMensaje()));
                    
                    objeto = persona;
                    
                }
            }
            
            //archivo.delete();
            
        }
        else
        {
            retorno.setMensaje(new Mensajes("No existe el archivo", TipoMensaje.ERROR));
        }
        
        
        return retorno;
    }
    
    private Retorno_MsgObj ProcesarXLSX(File archivo) throws FileNotFoundException, IOException{
        
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Leyendo archivo", TipoMensaje.MENSAJE));
        
        FileInputStream file = new FileInputStream(archivo);
	// Crear el objeto que tendra el libro de Excel
	XSSFWorkbook workbook;

        workbook = new XSSFWorkbook(file);

        /*
	 * Obtenemos la primera pestaña a la que se quiera procesar indicando el indice.
	 * Una vez obtenida la hoja excel con las filas que se quieren leer obtenemos el iterator
	 * que nos permite recorrer cada una de las filas que contiene.
	 */
	XSSFSheet sheet = workbook.getSheetAt(0);
	Iterator<Row> rowIterator = sheet.iterator();
        
        List<String> lstCampos  = new ArrayList<>();
        
	Row row;
	// Recorremos todas las filas para mostrar el contenido de cada celda
	while (rowIterator.hasNext()){
	    row = rowIterator.next();
            
            if(row.getCell(0) == null)
            {
                return retorno;
            }
            
            
            // Obtenemos el iterator que permite recorres todas las celdas de una fila
            Iterator<Cell> cellIterator = row.cellIterator();
            Cell celda;
            
            Persona persona = new Persona();
            
            while (cellIterator.hasNext()){
                celda = cellIterator.next();

                // Dependiendo del formato de la celda el valor se debe mostrar como String, Fecha, boolean, entero...
                
                String value = null;
                Object valorAux;

                switch(celda.getCellTypeEnum()) {
                case NUMERIC:
                    if( DateUtil.isCellDateFormatted(celda) ){
                       valorAux=celda.getDateCellValue();
                       value = yMd_HMS.format(valorAux);
                       
                    }else{
                       valorAux=celda.getNumericCellValue();
                       
                       value = valorAux.toString();
                       value = value.replace(".0", "");
                    }
                    break;
                case STRING:
                    value=celda.getStringCellValue();
                    break;
                case BOOLEAN:
                    valorAux=celda.getBooleanCellValue();
                    value = valorAux.toString();
                    break;
                }
                
                if(celda.getColumnIndex() == 0 && (value == null || value.isEmpty()))
                {
                    return retorno;
                }
                
                if(value!= null)
                {
                    if(row.getRowNum() == 0)
                    {
                        lstCampos.add((String) value);
                    }
                    else
                    {



                        Retorno_MsgObj setFieldRet = persona.setField(lstCampos.get(celda.getColumnIndex()), value);

                        if(setFieldRet.SurgioError())
                        {
                            retorno.getLstMensajes().add(setFieldRet.getMensaje());
                            retorno.getMensaje().setTipoMensaje(TipoMensaje.ADVERTENCIA);
                        }
                    }
                }
            }

            if(row.getRowNum() > 0)
            {
                retorno.getLstObjetos().add(persona);
            }
            
            System.err.println("-------------------------------");
            System.err.println("Fila: " + row.getRowNum());
            System.err.println("Personas: " + retorno.getLstObjetos().size());
            
	}
 
	// cerramos el libro excel
	workbook.close();
        
        return retorno;
    }
    
    private Retorno_MsgObj ProcesarXLS(File archivo) throws FileNotFoundException, IOException{
        
        Retorno_MsgObj retorno = new Retorno_MsgObj(new Mensajes("Leyendo archivo", TipoMensaje.MENSAJE));
        
        FileInputStream file = new FileInputStream(archivo);
	// Crear el objeto que tendra el libro de Excel
	HSSFWorkbook workbook;
        workbook = new HSSFWorkbook(file);
 
	/*
	 * Obtenemos la primera pestaña a la que se quiera procesar indicando el indice.
	 * Una vez obtenida la hoja excel con las filas que se quieren leer obtenemos el iterator
	 * que nos permite recorrer cada una de las filas que contiene.
	 */
        
        List<String> lstCampos  = new ArrayList<>();
       
        HSSFSheet sheet = workbook.getSheetAt(0);
	Iterator<Row> rowIterator = sheet.iterator();
 
	Row row;
	// Recorremos todas las filas para mostrar el contenido de cada celda
	while (rowIterator.hasNext()){
	    row = rowIterator.next();
            
            if(row.getCell(0) == null)
            {
                return retorno;
            }
            
            Persona persona = new Persona();
 
	    // Obtenemos el iterator que permite recorres todas las celdas de una fila
	    Iterator<Cell> cellIterator = row.cellIterator();
	    Cell celda;
 
	    while (cellIterator.hasNext()){
		celda = cellIterator.next();
 
		// Dependiendo del formato de la celda el valor se debe mostrar como String, Fecha, boolean, entero...
		String value = null;
                Object valorAux;

                switch(celda.getCellTypeEnum()) {
                case NUMERIC:
                    if( DateUtil.isCellDateFormatted(celda) ){
                       System.out.println(celda.getDateCellValue());
                       valorAux=celda.getDateCellValue();
                       value = yMd_HMS.format(valorAux);
                       
                    }else{
                       System.out.println(celda.getNumericCellValue());
                       valorAux=celda.getNumericCellValue();
                       value = valorAux.toString();
                       
                    }
                    break;
                case STRING:
                    System.out.println(celda.getStringCellValue());
                    value=celda.getStringCellValue();
                    break;
                case BOOLEAN:
                    System.out.println(celda.getBooleanCellValue());
                    valorAux=celda.getBooleanCellValue();
                    value = valorAux.toString();
                    break;
                }
                
                if(celda.getColumnIndex() == 0 && value == null)
                {
                    return retorno;
                }
                
                if(value != null)
                {
                    if(row.getRowNum() == 1)
                    {
                        lstCampos.add((String) value);
                    }
                    else
                    {

                        Retorno_MsgObj setFieldRet = persona.setField(lstCampos.get(celda.getColumnIndex()), value.toString());

                        if(setFieldRet.SurgioError())
                        {
                            retorno.getLstMensajes().add(setFieldRet.getMensaje());
                            retorno.getMensaje().setTipoMensaje(TipoMensaje.ADVERTENCIA);
                        }
                    }
                }
            }
            
            retorno.getLstObjetos().add(persona);
            
	}
 
	// cerramos el libro excel
	workbook.close();
        
        return retorno;
    }
    
    private void ImpactarEnBitacora(Retorno_MsgObj retorno){
        BitacoraProceso bit = new BitacoraProceso();
        bit.fromRetorno(retorno, Proceso.IMPORTACION);
        
        LoBitacora.GetInstancia().guardar(bit);
    }
    
}
