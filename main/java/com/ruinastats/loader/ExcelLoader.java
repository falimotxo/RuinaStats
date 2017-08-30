package com.ruinastats.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ruinastats.loader.data.MatchData;

/**
 * Clase que gestiona la carga de datos del excel
 * 
 * @author Fali
 *
 */
public class ExcelLoader {

	private static final Logger log = Logger.getLogger(ExcelLoader.class.getName());
	
	private static final int MATCH_WEEK = 0;
	private static final int LOCAL_TEAM = 1;
	private static final int AWAY_TEAM = 2;
	private static final int LOCAL_GOALS = 3;
	private static final int AWAY_GOALS = 4;
	private static final int LOCAL_CORNER = 5;
	private static final int AWAY_CORNER = 6;
	private static final int LOCAL_YELLOW_CARD = 7;
	private static final int AWAY_YELLOW_CARD = 8;
	private static final int LOCAL_RED_CARD = 9;
	private static final int AWAY_RED_CARD = 10;
	private static final int REFEREE = 11;
	
	/** Ruta donde se encuentra el excel */
	private String excelFile = "resources/Stats.xlsx";
	/** Cach� de partidos indexado por equipo */
	private Map<String, List<MatchData>> mapTeamCache;
	/** Cach� de partidos indexado por �rbitro */
	private Map<String, List<MatchData>> mapRefereeCache;
	
	/**
	 * M�todo que realiza la carga de datos del excel y lo inserta en una cach�
	 */
	public void loaderCacheData() {
		log.info("Inicio carga de datos");
		try {
			log.info("Iniciando cach� de equipos");
			mapTeamCache = new HashMap<String, List<MatchData>>();
			log.info("Iniciando cach� de �rbitros");
			mapRefereeCache = new HashMap<String, List<MatchData>>();
			
			log.info("Cargando excel -> " + excelFile);
			FileInputStream file = new FileInputStream(new File(excelFile));
			// Crear el objeto que tendra el libro de Excel
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			//Leemos la primera hoja del excel (Estad�sticas)
			XSSFSheet sheet = workbook.getSheetAt(0);
			
			for (Row row : sheet) {
				//No leemos la primera fila, ya que es la de t�tulos
				if(row.getRowNum() != 0) {
					//Si la celda no es num�rica, dejamos de leer el excel
					if(!row.getCell(MATCH_WEEK).getCellTypeEnum().equals(CellType.NUMERIC)) {
						break;
					}
					log.info("Leyendo fila " + row.getRowNum());
					String idMatch = (int)row.getCell(MATCH_WEEK).getNumericCellValue() + "_" + row.getCell(LOCAL_TEAM).getStringCellValue() + "_" + row.getCell(AWAY_TEAM).getStringCellValue();
					MatchData matchData = new MatchData(idMatch, (int)row.getCell(MATCH_WEEK).getNumericCellValue(), row.getCell(LOCAL_TEAM).getStringCellValue(), row.getCell(AWAY_TEAM).getStringCellValue(), 
							(int)row.getCell(LOCAL_GOALS).getNumericCellValue(), (int)row.getCell(AWAY_GOALS).getNumericCellValue(), (int)row.getCell(LOCAL_CORNER).getNumericCellValue(), (int)row.getCell(AWAY_CORNER).getNumericCellValue(), 
							(int)row.getCell(LOCAL_YELLOW_CARD).getNumericCellValue(), (int)row.getCell(AWAY_YELLOW_CARD).getNumericCellValue(), (int)row.getCell(LOCAL_RED_CARD).getNumericCellValue(), 
							(int)row.getCell(AWAY_RED_CARD).getNumericCellValue(), row.getCell(REFEREE).getStringCellValue());
					log.info(matchData.toString());
					
					
				}
			}
	
			// cerramos el libro excel
			workbook.close();
			log.info("Carga de datos finalizada");
		} catch (IOException e) {
			log.severe("Error al leer excel: " + e.getMessage());
		}
	}

	/**
	 * @return the mapTeamCache
	 */
	public Map<String, List<MatchData>> getMapTeamCache() {
		return mapTeamCache;
	}

	/**
	 * @return the mapRefereeCache
	 */
	public Map<String, List<MatchData>> getMapRefereeCache() {
		return mapRefereeCache;
	}
	
}
