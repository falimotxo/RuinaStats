package com.ruinastats.process;

import java.util.ArrayList;
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

	public StatsProcess(String teamLocal, String teamAway, String referee, Map<String, List<MatchData>> cacheTeam,
			Map<String, List<MatchData>> cacheReferee) {
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
		stats = "Partido: " + this.teamLocal + " - " + this.teamAway;
		if (this.referee != null) {
			stats += " (" + this.referee + ")\n\n";
		} else {
			stats += "\n\n";
		}
		stats += "==================================\n\n";

		List<MatchData> listMatchLocal = cacheTeam.get(teamLocal);
		List<MatchData> listMatchAway = cacheTeam.get(teamAway);

		// Metemos en una lista todos los partidos del equipo local y visitante
		// Hay que controlar que no se inserten partidos repetidos (caso que hayan
		// jugado antes entre ellos)
		List<MatchData> listMatchTotal = new ArrayList<MatchData>();
		listMatchTotal.addAll(listMatchLocal);
		for (MatchData matchDataAway : listMatchAway) {
			if (!listMatchTotal.contains(matchDataAway)) {
				listMatchTotal.add(matchDataAway);
			}
		}

		float totalMatchsLocal = listMatchLocal.size();
		float totalMatchsAway = listMatchAway.size();
		float totalMatchs = listMatchTotal.size();

		//Total de goles de los partidos de los dos equipos
		float totalGoalsMatchTeams = 0;
		//Número de overs 2.5 goles de los partidos de los dos equipos
		float overTotalGoalsMatchTeams = 0;
		//Número de partidos con ambos marcan
		float bothScoreGoals = 0;
		for (MatchData matchData : listMatchTotal) {
			// Media de goles por partido
			float totalGoalsMatch = matchData.getLocalGoals() + matchData.getAwayGoals();
			totalGoalsMatchTeams += totalGoalsMatch;
			if (totalGoalsMatch > 2.5) {
				overTotalGoalsMatchTeams++;
			}
			if(matchData.getLocalGoals() > 0 && matchData.getAwayGoals() > 0) {
				bothScoreGoals++;
			}
		}

		//Total de goles en los partidos del equipo local
		float totalGoalsMatchTeamLocal = 0;
		//Total de goles en los partidos del equipo local jugando como local
		float totalGoalsMatchTeamLocalPlayingLocal = 0;
		//Total de goles del equipo local jugando como local
		float totalGoalsTeamLocalPlayingLocal = 0;
		//Número de overs 2.5 goles en los partidos del equipo local
		float overTotalGoalsMatchTeamLocal = 0;
		//Número de overs 2.5 goles en los partidos del equipo local jugando como local
		float overTotalGoalsMatchTeamLocalPlayingLocal = 0;
		//Número de partidos del equipo local jugando como local
		float totalMatchsLocalPlayingLocal = 0;
		//Número de partidos con ambos marcan del equipo local
		float bothScoreGoalsTeamLocal = 0;
		//Número de partidos con ambos marcan del equipo local jugando como local
		float bothScoreGoalsTeamLocalPlayingLocal = 0;
		//Número de partidos con portería a cero del equipo local
		float goalToZeroTeamLocal = 0;
		//Número de partidos con portería a cero del equipo local jugando como local
		float goalToZeroTeamLocalPlayingLocal = 0;
		for (MatchData matchData : listMatchLocal) {
			// Media de goles por partido del equipo local
			float totalGoalsMatch = matchData.getLocalGoals() + matchData.getAwayGoals();
			totalGoalsMatchTeamLocal += totalGoalsMatch;
			
			if (totalGoalsMatch > 2.5) {
				overTotalGoalsMatchTeamLocal++;
			}
			//Ambos marcan
			if(matchData.getLocalGoals() > 0 && matchData.getAwayGoals() > 0) {
				bothScoreGoalsTeamLocal++;
			}
			//Portería a cero
			if(matchData.getLocalTeam().equals(this.teamLocal)) {
				if(matchData.getAwayGoals() == 0) {
					goalToZeroTeamLocal++;
				}
			} else {
				if(matchData.getLocalGoals() == 0) {
					goalToZeroTeamLocal++;
				}
			}
			// Media de goles por partido del equipo local jugando como local
			// Media de goles del equipo local jugando como local
			if (matchData.getLocalTeam().equals(this.teamLocal)) {
				totalMatchsLocalPlayingLocal++;
				float totalGoalsWithLocalMatch = matchData.getLocalGoals() + matchData.getAwayGoals();
				totalGoalsTeamLocalPlayingLocal += matchData.getLocalGoals();
				totalGoalsMatchTeamLocalPlayingLocal += totalGoalsWithLocalMatch;
				if (totalGoalsWithLocalMatch > 2.5) {
					overTotalGoalsMatchTeamLocalPlayingLocal++;
				}
				//Ambos marcan
				if(matchData.getLocalGoals() > 0 && matchData.getAwayGoals() > 0) {
					bothScoreGoalsTeamLocalPlayingLocal++;
				}
				//Portería a cero
				if(matchData.getAwayGoals() == 0) {
					goalToZeroTeamLocalPlayingLocal++;
				}
			}
			
		}
		
		//Total de goles en los partidos del equipo visitante
		float totalGoalsMatchTeamAway = 0;
		//Total de goles en los partidos del equipo visitante jugando como visitante
		float totalGoalsMatchTeamAwayPlayingAway = 0;
		//Total de goles del equipo visitante jugando como visitante
		float totalGoalsTeamAwayPlayingAway = 0;
		//Número de overs 2.5 goles en los partidos del equipo visitante
		float overTotalGoalsMatchTeamAway = 0;
		//Número de overs 2.5 goles en los partidos del equipo visitante jugando como visitante
		float overTotalGoalsMatchTeamAwayPlayingAway = 0;
		//Número de partidos del equipo visitante jugando como visitante
		float totalMatchsAwayPlayingAway = 0;
		//Número de partidos con ambos marcan del equipo visitante
		float bothScoreGoalsTeamAway = 0;
		//Número de partidos con ambos marcan del equipo visitante jugando como visitante
		float bothScoreGoalsTeamAwayPlayingAway = 0;
		//Número de partidos con portería a cero del equipo visitante
		float goalToZeroTeamAway = 0;
		//Número de partidos con portería a cero del equipo visitante jugando como visitante
		float goalToZeroTeamAwayPlayingAway = 0;
		for (MatchData matchData : listMatchAway) {
			// Media de goles por partido del equipo visitante
			float totalGoalsMatch = matchData.getLocalGoals() + matchData.getAwayGoals();
			totalGoalsMatchTeamAway += totalGoalsMatch;
			if (totalGoalsMatch > 2.5) {
				overTotalGoalsMatchTeamAway++;
			}
			//Ambos marcan
			if(matchData.getLocalGoals() > 0 && matchData.getAwayGoals() > 0) {
				bothScoreGoalsTeamAway++;
			}
			//Portería a cero
			if(matchData.getLocalTeam().equals(this.teamAway)) {
				if(matchData.getAwayGoals() == 0) {
					goalToZeroTeamAway++;
				}
			} else {
				if(matchData.getLocalGoals() == 0) {
					goalToZeroTeamAway++;
				}
			}
			// Media de goles por partido del equipo visitante jugando como visitante
			if (matchData.getAwayTeam().equals(this.teamAway)) {
				totalMatchsAwayPlayingAway++;
				float totalGoalsWithAwayMatch = matchData.getLocalGoals() + matchData.getAwayGoals();
				totalGoalsTeamAwayPlayingAway = matchData.getAwayGoals();
				totalGoalsMatchTeamAwayPlayingAway += totalGoalsWithAwayMatch;
				if (totalGoalsWithAwayMatch > 2.5) {
					overTotalGoalsMatchTeamAwayPlayingAway++;
				}
				//Ambos marcan
				if(matchData.getLocalGoals() > 0 && matchData.getAwayGoals() > 0) {
					bothScoreGoalsTeamAwayPlayingAway++;
				}
				//Portería a cero
				if(matchData.getLocalGoals() == 0) {
					goalToZeroTeamAwayPlayingAway++;
				}
			}
		}

		stats += "--> Media de goles por partido: " + (totalGoalsMatchTeams / totalMatchs) + " | Fiabilidad o2.5 goles: "
				+ ((overTotalGoalsMatchTeams / totalMatchs) * 100) + "%\n";
		stats += "--> Media de goles por partido del " + this.teamLocal + ": "
				+ (totalGoalsMatchTeamLocal / totalMatchsLocal) + " | Fiabilidad o2.5 goles: "
				+ ((overTotalGoalsMatchTeamLocal / totalMatchsLocal) * 100) + "%\n";
		stats += "--> Media de goles por partido en casa del " + this.teamLocal + ": "
				+ (totalGoalsMatchTeamLocalPlayingLocal / totalMatchsLocalPlayingLocal) + " | Fiabilidad o2.5 goles: "
				+ ((overTotalGoalsMatchTeamLocalPlayingLocal / totalMatchsLocalPlayingLocal) * 100) + "%\n";
		stats += "--> Media de goles del " + this.teamLocal + " jugando en casa: "
				+ (totalGoalsTeamLocalPlayingLocal / totalMatchsLocalPlayingLocal) + "\n";
		stats += "--> Media de goles por partido del " + this.teamAway + ": " + (totalGoalsMatchTeamAway / totalMatchsAway)
				+ " | Fiabilidad o2.5 goles: "
				+ ((overTotalGoalsMatchTeamAway / totalMatchsAway) * 100) + "%\n";
		stats += "--> Media de goles por partido fuera de casa del " + this.teamAway + ": "
				+ (totalGoalsMatchTeamAwayPlayingAway / totalMatchsAwayPlayingAway) + " | Fiabilidad o2.5 goles: "
				+ ((overTotalGoalsMatchTeamAwayPlayingAway / totalMatchsAwayPlayingAway) * 100) + "%\n";
		stats += "--> Media de goles del " + this.teamAway + " jugando como visitante: "
				+ (totalGoalsTeamAwayPlayingAway / totalMatchsAwayPlayingAway) + "\n";
		stats += "--> Media de ambos marcan por partido: " + (bothScoreGoals / totalMatchs) + " | Fiabilidad: "
				+ ((bothScoreGoals / totalMatchs) * 100) + "%\n";
		stats += "--> Media de ambos marcan por partido del " + this.teamLocal + ": " + (bothScoreGoalsTeamLocal / totalMatchsLocal) + " | Fiabilidad: "
				+ ((bothScoreGoalsTeamLocal / totalMatchsLocal) * 100) + "%\n";
		stats += "--> Media de ambos marcan por partido del " + this.teamLocal + " jugando como local: " + (bothScoreGoalsTeamLocalPlayingLocal / totalMatchsLocalPlayingLocal) + " | Fiabilidad: "
				+ ((bothScoreGoalsTeamLocalPlayingLocal / totalMatchsLocalPlayingLocal) * 100) + "%\n";
		stats += "--> Media de ambos marcan por partido del " + this.teamAway + ": " + (bothScoreGoalsTeamAway / totalMatchsAway) + " | Fiabilidad: "
				+ ((bothScoreGoalsTeamAway / totalMatchsAway) * 100) + "%\n";
		stats += "--> Media de ambos marcan por partido del " + this.teamAway + " jugando como visitante: " + (bothScoreGoalsTeamAwayPlayingAway / totalMatchsAwayPlayingAway) + " | Fiabilidad: "
				+ ((bothScoreGoalsTeamAwayPlayingAway / totalMatchsAwayPlayingAway) * 100) + "%\n";
		stats += "--> Media de portería a cero por partido del " + this.teamLocal + ": " + (goalToZeroTeamLocal / totalMatchsLocal) + " | Fiabilidad: "
				+ ((goalToZeroTeamLocal / totalMatchsLocal) * 100) + "%\n";
		stats += "--> Media de portería a cero por partido del " + this.teamLocal + " jugando como local: " + (goalToZeroTeamLocalPlayingLocal / totalMatchsLocalPlayingLocal) + " | Fiabilidad: "
				+ ((goalToZeroTeamLocalPlayingLocal / totalMatchsLocalPlayingLocal) * 100) + "%\n";
		stats += "--> Media de portería a cero por partido del " + this.teamAway + ": " + (goalToZeroTeamAway / totalMatchsAway) + " | Fiabilidad: "
				+ ((goalToZeroTeamAway / totalMatchsAway) * 100) + "%\n";
		stats += "--> Media de portería a cero por partido del " + this.teamAway + " jugando como visitante: " + (goalToZeroTeamAwayPlayingAway / totalMatchsAwayPlayingAway) + " | Fiabilidad: "
				+ ((goalToZeroTeamAwayPlayingAway / totalMatchsAwayPlayingAway) * 100) + "%\n";

		log.info("Generación de estadísticas finalizada");
		return stats;
	}

}
