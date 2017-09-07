package com.ruinastats.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ruinastats.data.MatchData;

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
	/** Caché de partidos indexado por equipo */
	private Map<String, List<MatchData>> mapTeamCache;
	/** Caché de partidos indexado por árbitro */
	private Map<String, List<MatchData>> mapRefereeCache;
	/** Listado de equipos de 1 division */
	private List<String> listTeam1;
	/** Listado de equipos de 2 division */
	private List<String> listTeam2;
	/** Listado de arbitros de 1 division */
	private List<String> listReferee1;
	/** Listado de arbitros de 2 division */
	private List<String> listReferee2;
	
	/**
	 * Método que realiza la carga de datos del excel y lo inserta en una caché
	 */
	public void loaderCacheData() {
		log.info("Inicio carga de datos");
		try {
			log.info("Iniciando caché de equipos");
			mapTeamCache = new HashMap<String, List<MatchData>>();
			log.info("Iniciando caché de árbitros");
			mapRefereeCache = new HashMap<String, List<MatchData>>();
			log.info("Iniciando listado de equipos de 1 division");
			listTeam1 = new ArrayList<String>();
			log.info("Iniciando listado de equipos de 2 division");
			listTeam2 = new ArrayList<String>();
			log.info("Iniciando listado de árbitros de 1 division");
			listReferee1 = new ArrayList<String>();
			log.info("Iniciando listado de árbitros de 2 division");
			listReferee2 = new ArrayList<String>();
			
			log.info("Cargando excel -> " + excelFile);
			FileInputStream file = new FileInputStream(new File(excelFile));
			// Crear el objeto que tendra el libro de Excel
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			
			for(int i = 0; i < 2; i++) {
				//Leemos la primera hoja del excel (Estadísticas)
				XSSFSheet sheet = workbook.getSheetAt(i);
				
				for (Row row : sheet) {
					//No leemos la primera fila, ya que es la de títulos
					if(row.getRowNum() != 0) {
						//Si la celda no es numérica, dejamos de leer el excel
						if(!row.getCell(MATCH_WEEK).getCellTypeEnum().equals(CellType.NUMERIC)) {
							break;
						}
						log.info("Leyendo fila " + row.getRowNum());
						String idMatch = row.getCell(LOCAL_TEAM).getStringCellValue() + "_" + row.getCell(AWAY_TEAM).getStringCellValue();
						String referee = row.getCell(REFEREE).getStringCellValue();
						String localTeam = row.getCell(LOCAL_TEAM).getStringCellValue();
						String awayTeam = row.getCell(AWAY_TEAM).getStringCellValue();
						MatchData matchData = new MatchData(idMatch, (int)row.getCell(MATCH_WEEK).getNumericCellValue(), localTeam, awayTeam, 
								(int)row.getCell(LOCAL_GOALS).getNumericCellValue(), (int)row.getCell(AWAY_GOALS).getNumericCellValue(), (int)row.getCell(LOCAL_CORNER).getNumericCellValue(), (int)row.getCell(AWAY_CORNER).getNumericCellValue(), 
								(int)row.getCell(LOCAL_YELLOW_CARD).getNumericCellValue(), (int)row.getCell(AWAY_YELLOW_CARD).getNumericCellValue(), (int)row.getCell(LOCAL_RED_CARD).getNumericCellValue(), 
								(int)row.getCell(AWAY_RED_CARD).getNumericCellValue(), referee);
						log.info(matchData.toString());
						
						//Si la cache no contiene al equipo local, se inserta y se inicia la lista de partidos
						if(!mapTeamCache.containsKey(localTeam)) {
							List<MatchData> matchDataList = new ArrayList<MatchData>();
							matchDataList.add(matchData);
							mapTeamCache.put(localTeam, matchDataList);
						} else {
							List<MatchData> matchDataList = mapTeamCache.get(localTeam);
							if(!matchDataList.contains(matchData)) {
								matchDataList.add(matchData);
							}
						}
						
						if(i == 0) {
							//Se añade el equipo al listado de equipos de 1 division
							if(!listTeam1.contains(localTeam)) {
								listTeam1.add(localTeam);
							}
						} else if (i == 1) {
							//Se añade el equipo al listado de equipos de 2 division
							if(!listTeam2.contains(localTeam)) {
								listTeam2.add(localTeam);
							}
						}
						
						//Si la cache no contiene al equipo visitante, se inserta y se inicia la lista de partidos
						if(!mapTeamCache.containsKey(awayTeam)) {
							List<MatchData> matchDataList = new ArrayList<MatchData>();
							matchDataList.add(matchData);
							mapTeamCache.put(awayTeam, matchDataList);
						} else {
							List<MatchData> matchDataList = mapTeamCache.get(awayTeam);
							if(!matchDataList.contains(matchData)) {
								matchDataList.add(matchData);
							}
						}
						
						if(i == 0) {
							//Se añade el equipo al listado de equipos de 1 division
							if(!listTeam1.contains(awayTeam)) {
								listTeam1.add(awayTeam);
							}
						} else if (i == 1) {
							//Se añade el equipo al listado de equipos de 2 division
							if(!listTeam2.contains(awayTeam)) {
								listTeam2.add(awayTeam);
							}
						}
						
						//Si la cache no contiene al arbitro, se inserta y se inicia la lista de partidos
						if(!mapRefereeCache.containsKey(referee)) {
							List<MatchData> matchDataList = new ArrayList<MatchData>();
							matchDataList.add(matchData);
							mapRefereeCache.put(referee, matchDataList);
						} else {
							List<MatchData> matchDataList = mapRefereeCache.get(referee);
							if(!matchDataList.contains(matchData)) {
								matchDataList.add(matchData);
							}
						}
						
						if(i == 0) {
							//Se añade el árbitro al listado de árbitros de 1 division
							if(!listReferee1.contains(referee)) {
								listReferee1.add(referee);
							}
						} else if (i == 1) {
							//Se añade el árbitro al listado de árbitros de 2 division
							if(!listReferee2.contains(referee)) {
								listReferee2.add(referee);
							}
						}
					}
				}
			}
	
			Collections.sort(listTeam1);
			Collections.sort(listTeam2);
			Collections.sort(listReferee1);
			Collections.sort(listReferee2);
			
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

	/**
	 * @return the listTeam1
	 */
	public List<String> getListTeam1() {
		return listTeam1;
	}
	
	/**
	 * @return the listTeam2
	 */
	public List<String> getListTeam2() {
		return listTeam2;
	}

	/**
	 * @return the listReferee1
	 */
	public List<String> getListReferee1() {
		return listReferee1;
	}
	
	/**
	 * @return the listReferee2
	 */
	public List<String> getListReferee2() {
		return listReferee2;
	}
	
}
