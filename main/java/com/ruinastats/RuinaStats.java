package com.ruinastats;

import java.util.logging.Logger;

import com.ruinastats.gui.RuinaStatsGui;
import com.ruinastats.loader.ExcelLoader;

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
		
		RuinaStatsGui gui = new RuinaStatsGui(excelLoader);
		gui.setVisible(true);
	}

}
