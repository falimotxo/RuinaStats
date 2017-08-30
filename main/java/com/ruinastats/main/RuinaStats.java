package com.ruinastats.main;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.ruinastats.loader.ExcelLoader;
import com.ruinastats.loader.data.MatchData;

/**
 * Clase principal de RuinaStats
 * 
 * @author Fali
 *
 */
public class RuinaStats {

	private static final Logger log = Logger.getLogger(RuinaStats.class.getName());
	
	public static void main(String[] args) {
		log.info("Inicializando RuinaStats");
		ExcelLoader excelLoader = new ExcelLoader();
		excelLoader.loaderCacheData();
		
		Map<String, List<MatchData>> cacheTeam = excelLoader.getMapTeamCache();
		Map<String, List<MatchData>> cacheReferee = excelLoader.getMapRefereeCache();
		
		log.info("Finalizado RuinaStats");
	}

}
