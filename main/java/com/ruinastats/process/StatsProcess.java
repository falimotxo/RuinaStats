package com.ruinastats.process;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.ruinastats.data.MatchData;

/**
 * Clase que realiza el procesamiento de las estad�sticas
 * 
 * @author Fali
 *
 */
public class StatsProcess {

	private static final Logger log = Logger.getLogger(StatsProcess.class.getName());
	
	/** Equipo Local */
	private String teamLocal;
	/** Equipo Visitante */
	private String teamAway;
	/** �rbitro */
	private String referee;
	/** Cach� de partidos */
	private Map<String, List<MatchData>> cacheTeam;
	/** Cach� de arbitros */
	private Map<String, List<MatchData>> cacheReferee;
	
	public StatsProcess(String teamLocal, String teamAway, String referee, Map<String, List<MatchData>> cacheTeam, Map<String, List<MatchData>> cacheReferee) {
		this.teamLocal = teamLocal;
		this.teamAway = teamAway;
		this.referee = referee;
		this.cacheTeam = cacheTeam;
		this.cacheReferee = cacheReferee;
	}

	/**
	 * Obtiene las estad�sticas de los datos proporcionados
	 * 
	 * @return
	 */
	public String getStats() {
		String stats = "";
		log.info("Inicio de generaci�n de estad�sticas");
		stats = "Partido: " + this.teamLocal + " - " + this.teamAway + " (" + this.referee + ")\n\n";
		stats += "==================================\n\n";
		log.info("Generaci�n de estad�sticas finalizada");
		return stats;
	}
	
}
