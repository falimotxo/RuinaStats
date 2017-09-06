package com.ruinastats.process;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.ruinastats.data.MatchData;

/**
 * Clase que realiza el procesamiento de las estadísticas
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
	/** Árbitro */
	private String referee;
	/** Caché de partidos */
	private Map<String, List<MatchData>> cacheTeam;
	/** Caché de arbitros */
	private Map<String, List<MatchData>> cacheReferee;
	
	public StatsProcess(String teamLocal, String teamAway, String referee, Map<String, List<MatchData>> cacheTeam, Map<String, List<MatchData>> cacheReferee) {
		this.teamLocal = teamLocal;
		this.teamAway = teamAway;
		this.referee = referee;
		this.cacheTeam = cacheTeam;
		this.cacheReferee = cacheReferee;
	}

	/**
	 * Obtiene las estadísticas de los datos proporcionados
	 * 
	 * @return
	 */
	public String getStats() {
		String stats = "";
		log.info("Inicio de generación de estadísticas");
		stats = "Partido: " + this.teamLocal + " - " + this.teamAway + " (" + this.referee + ")\n\n";
		stats += "==================================\n\n";
		log.info("Generación de estadísticas finalizada");
		return stats;
	}
	
}
