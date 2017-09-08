package com.ruinastats.process;

import java.text.DecimalFormat;
import java.util.ArrayList;
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

	public StatsProcess(String teamLocal, String teamAway, String referee, Map<String, List<MatchData>> cacheTeam,
			Map<String, List<MatchData>> cacheReferee) {
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
		stats = "Partido: " + this.teamLocal + " - " + this.teamAway;
		if (this.referee != null) {
			stats += " (" + this.referee + ")\n\n";
		} else {
			stats += "\n\n";
		}
		stats += "==================================\n\n";

		List<MatchData> listMatchLocal = cacheTeam.get(teamLocal);
		List<MatchData> listMatchAway = cacheTeam.get(teamAway);
		
		List<MatchData> listMatchReferee = null;
		if(referee != null) {
			listMatchReferee = cacheReferee.get(referee);
		}

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
		float totalMatchsReferee = 0;
		if(this.referee != null) {
			totalMatchsReferee = listMatchReferee.size();
		}
		
		// Total de goles de los partidos de los dos equipos
		float totalGoalsMatchTeams = 0;
		// N�mero de overs 2.5 goles de los partidos de los dos equipos
		float overTotalGoalsMatchTeams = 0;
		// N�mero de partidos con ambos marcan
		float bothScoreGoals = 0;
		// N�mero de corners
		float totalCornersMatchTeams = 0;
		// N�mero de tarjetas
		float totalCardsMatchTeams = 0;
		for (MatchData matchData : listMatchTotal) {
			// Media de goles por partido
			float totalGoalsMatch = matchData.getLocalGoals() + matchData.getAwayGoals();
			totalGoalsMatchTeams += totalGoalsMatch;
			if (totalGoalsMatch > 2.5) {
				overTotalGoalsMatchTeams++;
			}
			// Ambos marcan
			if (matchData.getLocalGoals() > 0 && matchData.getAwayGoals() > 0) {
				bothScoreGoals++;
			}
			// Media de corners
			totalCornersMatchTeams += matchData.getLocalCorner() + matchData.getAwayCorner();
			// Media de tarjetas
			totalCardsMatchTeams += matchData.getLocalYellowCard() + matchData.getAwayYellowCard()
					+ matchData.getLocalRedCard() + matchData.getAwayRedCard();
		}

		// Total de goles en los partidos del equipo local
		float totalGoalsMatchTeamLocal = 0;
		// Total de goles en los partidos del equipo local jugando como local
		float totalGoalsMatchTeamLocalPlayingLocal = 0;
		// Total de goles del equipo local jugando como local
		float totalGoalsTeamLocalPlayingLocal = 0;
		// N�mero de overs 2.5 goles en los partidos del equipo local
		float overTotalGoalsMatchTeamLocal = 0;
		// N�mero de overs 2.5 goles en los partidos del equipo local jugando como local
		float overTotalGoalsMatchTeamLocalPlayingLocal = 0;
		// N�mero de partidos del equipo local jugando como local
		float totalMatchsLocalPlayingLocal = 0;
		// N�mero de partidos con ambos marcan del equipo local
		float bothScoreGoalsTeamLocal = 0;
		// N�mero de partidos con ambos marcan del equipo local jugando como local
		float bothScoreGoalsTeamLocalPlayingLocal = 0;
		// N�mero de partidos con porter�a a cero del equipo local
		float goalToZeroTeamLocal = 0;
		// N�mero de partidos con porter�a a cero del equipo local jugando como local
		float goalToZeroTeamLocalPlayingLocal = 0;
		// N�mero de corners en el partido del equipo local
		float cornersTeamLocal = 0;
		// N�mero de corners del equipo local jugando como local
		float cornersTeamLocalPlayingLocal = 0;
		// N�mero de corners en contra del equipo local jugando como local
		float cornersTeamAwayPlayingLocal = 0;
		// N�mero de corners en el partido del equipo local
		float cardsTeamLocal = 0;
		// N�mero de corners del equipo local jugando como local
		float cardsTeamLocalPlayingLocal = 0;
		;
		for (MatchData matchData : listMatchLocal) {
			// Media de goles por partido del equipo local
			float totalGoalsMatch = matchData.getLocalGoals() + matchData.getAwayGoals();
			totalGoalsMatchTeamLocal += totalGoalsMatch;

			if (totalGoalsMatch > 2.5) {
				overTotalGoalsMatchTeamLocal++;
			}
			// Ambos marcan
			if (matchData.getLocalGoals() > 0 && matchData.getAwayGoals() > 0) {
				bothScoreGoalsTeamLocal++;
			}
			// Porter�a a cero
			if (matchData.getLocalTeam().equals(this.teamLocal)) {
				if (matchData.getAwayGoals() == 0) {
					goalToZeroTeamLocal++;
				}
			} else {
				if (matchData.getLocalGoals() == 0) {
					goalToZeroTeamLocal++;
				}
			}
			// Media de corners en los partidos del equipo local
			cornersTeamLocal += matchData.getLocalCorner() + matchData.getAwayCorner();
			// Media de tarjetas en los partidos del equipo local
			cardsTeamLocal += matchData.getLocalYellowCard() + matchData.getAwayYellowCard()
					+ matchData.getLocalRedCard() + matchData.getAwayRedCard();

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
				// Ambos marcan
				if (matchData.getLocalGoals() > 0 && matchData.getAwayGoals() > 0) {
					bothScoreGoalsTeamLocalPlayingLocal++;
				}
				// Porter�a a cero
				if (matchData.getAwayGoals() == 0) {
					goalToZeroTeamLocalPlayingLocal++;
				}
				// Corners del equipo local jugando como local
				cornersTeamLocalPlayingLocal += matchData.getLocalCorner();
				// Corners en contra del equipo local jugando como local
				cornersTeamAwayPlayingLocal += matchData.getAwayCorner();
				// Tarjetas en el partido del equipo local jugando como local
				cardsTeamLocalPlayingLocal += matchData.getLocalYellowCard() + matchData.getAwayYellowCard()
						+ matchData.getLocalRedCard() + matchData.getAwayRedCard();
			}
		}

		// Total de goles en los partidos del equipo visitante
		float totalGoalsMatchTeamAway = 0;
		// Total de goles en los partidos del equipo visitante jugando como visitante
		float totalGoalsMatchTeamAwayPlayingAway = 0;
		// Total de goles del equipo visitante jugando como visitante
		float totalGoalsTeamAwayPlayingAway = 0;
		// N�mero de overs 2.5 goles en los partidos del equipo visitante
		float overTotalGoalsMatchTeamAway = 0;
		// N�mero de overs 2.5 goles en los partidos del equipo visitante jugando como
		// visitante
		float overTotalGoalsMatchTeamAwayPlayingAway = 0;
		// N�mero de partidos del equipo visitante jugando como visitante
		float totalMatchsAwayPlayingAway = 0;
		// N�mero de partidos con ambos marcan del equipo visitante
		float bothScoreGoalsTeamAway = 0;
		// N�mero de partidos con ambos marcan del equipo visitante jugando como
		// visitante
		float bothScoreGoalsTeamAwayPlayingAway = 0;
		// N�mero de partidos con porter�a a cero del equipo visitante
		float goalToZeroTeamAway = 0;
		// N�mero de partidos con porter�a a cero del equipo visitante jugando como
		// visitante
		float goalToZeroTeamAwayPlayingAway = 0;
		// N�mero de corners en el partido del equipo visitante
		float cornersTeamAway = 0;
		// N�mero de corners del equipo visitante jugando como visitante
		float cornersTeamAwayPlayingAway = 0;
		// N�mero de corners en contra del equipo visitante jugando como visitante
		float cornersTeamLocalPlayingAway = 0;
		// N�mero de corners en el partido del equipo visitante
		float cardsTeamAway = 0;
		// N�mero de corners del equipo visitante jugando como visitante
		float cardsTeamAwayPlayingAway = 0;
		for (MatchData matchData : listMatchAway) {
			// Media de goles por partido del equipo visitante
			float totalGoalsMatch = matchData.getLocalGoals() + matchData.getAwayGoals();
			totalGoalsMatchTeamAway += totalGoalsMatch;
			if (totalGoalsMatch > 2.5) {
				overTotalGoalsMatchTeamAway++;
			}
			// Ambos marcan
			if (matchData.getLocalGoals() > 0 && matchData.getAwayGoals() > 0) {
				bothScoreGoalsTeamAway++;
			}
			// Porter�a a cero
			if (matchData.getLocalTeam().equals(this.teamAway)) {
				if (matchData.getAwayGoals() == 0) {
					goalToZeroTeamAway++;
				}
			} else {
				if (matchData.getLocalGoals() == 0) {
					goalToZeroTeamAway++;
				}
			}
			// Media de corners en los partidos del equipo visitante
			cornersTeamAway += matchData.getLocalCorner() + matchData.getAwayCorner();
			// Media de tarjetas en los partidos del equipo visitante
			cardsTeamAway += matchData.getLocalYellowCard() + matchData.getAwayYellowCard()
					+ matchData.getLocalRedCard() + matchData.getAwayRedCard();

			// Media de goles por partido del equipo visitante jugando como visitante
			if (matchData.getAwayTeam().equals(this.teamAway)) {
				totalMatchsAwayPlayingAway++;
				float totalGoalsWithAwayMatch = matchData.getLocalGoals() + matchData.getAwayGoals();
				totalGoalsTeamAwayPlayingAway = matchData.getAwayGoals();
				totalGoalsMatchTeamAwayPlayingAway += totalGoalsWithAwayMatch;
				if (totalGoalsWithAwayMatch > 2.5) {
					overTotalGoalsMatchTeamAwayPlayingAway++;
				}
				// Ambos marcan
				if (matchData.getLocalGoals() > 0 && matchData.getAwayGoals() > 0) {
					bothScoreGoalsTeamAwayPlayingAway++;
				}
				// Porter�a a cero
				if (matchData.getLocalGoals() == 0) {
					goalToZeroTeamAwayPlayingAway++;
				}
				// Corners del equipo visitante jugando como visitante
				cornersTeamAwayPlayingAway += matchData.getAwayCorner();
				// Corners en contra del equipo visitante jugando como visitante
				cornersTeamLocalPlayingAway += matchData.getLocalCorner();
				// Tarjetas en el partido del equipo visitante jugando como visitante
				cardsTeamAwayPlayingAway += matchData.getLocalYellowCard() + matchData.getAwayYellowCard()
						+ matchData.getLocalRedCard() + matchData.getAwayRedCard();
			}
		}

		//Informaci�n del �rbitro
		// N�mero de tarjetas del �rbitro en el partido
		float totalCardsReferee = 0;
		if(referee != null) {
			for (MatchData matchData : listMatchReferee) {
				totalCardsReferee += matchData.getLocalYellowCard() + matchData.getAwayYellowCard() + matchData.getLocalRedCard() + matchData.getAwayRedCard();
			}
		}
		DecimalFormat df = new DecimalFormat("0.00");
		
		stats += "--> Goles por partido: " + df.format((totalGoalsMatchTeams / totalMatchs)) + " | Fiabilidad o2.5 goles: "
				+ df.format(((overTotalGoalsMatchTeams / totalMatchs) * 100)) + "% - u2.5 goles: "
				+ df.format((100 - ((overTotalGoalsMatchTeams / totalMatchs) * 100))) + "%\n";
		stats += "--> Goles por partido del " + this.teamLocal + ": " + df.format((totalGoalsMatchTeamLocal / totalMatchsLocal))
				+ " | Fiabilidad o2.5 goles: " + df.format(((overTotalGoalsMatchTeamLocal / totalMatchsLocal) * 100)) + "%\n";
		stats += "--> Goles por partido en casa del " + this.teamLocal + ": "
				+ df.format((totalGoalsMatchTeamLocalPlayingLocal / totalMatchsLocalPlayingLocal)) + " | Fiabilidad o2.5 goles: "
				+ df.format(((overTotalGoalsMatchTeamLocalPlayingLocal / totalMatchsLocalPlayingLocal) * 100)) + "%\n";
		stats += "--> Goles del " + this.teamLocal + " jugando en casa: "
				+ df.format((totalGoalsTeamLocalPlayingLocal / totalMatchsLocalPlayingLocal)) + "\n";
		stats += "--> Goles por partido del " + this.teamAway + ": " + df.format((totalGoalsMatchTeamAway / totalMatchsAway))
				+ " | Fiabilidad o2.5 goles: " + df.format(((overTotalGoalsMatchTeamAway / totalMatchsAway) * 100)) + "%\n";
		stats += "--> Goles por partido fuera de casa del " + this.teamAway + ": "
				+ df.format((totalGoalsMatchTeamAwayPlayingAway / totalMatchsAwayPlayingAway)) + " | Fiabilidad o2.5 goles: "
				+ df.format(((overTotalGoalsMatchTeamAwayPlayingAway / totalMatchsAwayPlayingAway) * 100)) + "%\n";
		stats += "--> Goles del " + this.teamAway + " jugando como visitante: "
				+ df.format((totalGoalsTeamAwayPlayingAway / totalMatchsAwayPlayingAway)) + "\n";
		stats += "--> Ambos marcan por partido: " + df.format((bothScoreGoals / totalMatchs)) + " | Fiabilidad: "
				+ df.format(((bothScoreGoals / totalMatchs) * 100)) + "%\n";
		stats += "--> Ambos marcan por partido del " + this.teamLocal + ": "
				+ df.format((bothScoreGoalsTeamLocal / totalMatchsLocal)) + " | Fiabilidad: "
				+ df.format(((bothScoreGoalsTeamLocal / totalMatchsLocal)) * 100) + "%\n";
		stats += "--> Ambos marcan por partido del " + this.teamLocal + " jugando como local: "
				+ df.format((bothScoreGoalsTeamLocalPlayingLocal / totalMatchsLocalPlayingLocal)) + " | Fiabilidad: "
				+ df.format(((bothScoreGoalsTeamLocalPlayingLocal / totalMatchsLocalPlayingLocal) * 100)) + "%\n";
		stats += "--> Ambos marcan por partido del " + this.teamAway + ": " + df.format((bothScoreGoalsTeamAway / totalMatchsAway))
				+ " | Fiabilidad: " + df.format(((bothScoreGoalsTeamAway / totalMatchsAway) * 100)) + "%\n";
		stats += "--> Ambos marcan por partido del " + this.teamAway + " jugando como visitante: "
				+ df.format((bothScoreGoalsTeamAwayPlayingAway / totalMatchsAwayPlayingAway)) + " | Fiabilidad: "
				+ df.format(((bothScoreGoalsTeamAwayPlayingAway / totalMatchsAwayPlayingAway) * 100)) + "%\n";
		stats += "--> Porter�a a cero por partido del " + this.teamLocal + ": "
				+ df.format((goalToZeroTeamLocal / totalMatchsLocal)) + " | Fiabilidad: "
				+ df.format(((goalToZeroTeamLocal / totalMatchsLocal) * 100)) + "%\n";
		stats += "--> Porter�a a cero por partido del " + this.teamLocal + " jugando como local: "
				+ df.format((goalToZeroTeamLocalPlayingLocal / totalMatchsLocalPlayingLocal)) + " | Fiabilidad: "
				+ df.format(((goalToZeroTeamLocalPlayingLocal / totalMatchsLocalPlayingLocal) * 100)) + "%\n";
		stats += "--> Porter�a a cero por partido del " + this.teamAway + ": " + df.format((goalToZeroTeamAway / totalMatchsAway))
				+ " | Fiabilidad: " + df.format(((goalToZeroTeamAway / totalMatchsAway) * 100)) + "%\n";
		stats += "--> Porter�a a cero por partido del " + this.teamAway + " jugando como visitante: "
				+ df.format((goalToZeroTeamAwayPlayingAway / totalMatchsAwayPlayingAway)) + " | Fiabilidad: "
				+ df.format(((goalToZeroTeamAwayPlayingAway / totalMatchsAwayPlayingAway) * 100)) + "%\n";
		stats += "\n";
		stats += "--> Corners por partido: " + df.format((totalCornersMatchTeams / totalMatchs)) + "\n";
		stats += "--> Corners en los partidos del " + this.teamLocal + ": " + df.format((cornersTeamLocal / totalMatchsLocal))
				+ "\n";
		stats += "--> Corners a favor del " + this.teamLocal + " jugando como local: "
				+ df.format((cornersTeamLocalPlayingLocal / totalMatchsLocalPlayingLocal)) + "\n";
		stats += "--> Corners en contra del " + this.teamLocal + " jugando como local: "
				+ df.format((cornersTeamAwayPlayingLocal / totalMatchsLocalPlayingLocal)) + "\n";
		stats += "--> Corners en los partidos del " + this.teamAway + ": " + df.format((cornersTeamAway / totalMatchsAway)) + "\n";
		stats += "--> Corners a favor del " + this.teamAway + " jugando como visitante: "
				+ df.format((cornersTeamAwayPlayingAway / totalMatchsAwayPlayingAway)) + "\n";
		stats += "--> Corners en contra del " + this.teamAway + " jugando como visitante: "
				+ df.format((cornersTeamLocalPlayingAway / totalMatchsAwayPlayingAway)) + "\n";
		stats += "\n";
		stats += "--> Tarjetas por partido: " + df.format((totalCardsMatchTeams / totalMatchs)) + "\n";
		stats += "--> Tarjetas en los partidos del " + this.teamLocal + ": " + df.format((cardsTeamLocal / totalMatchsLocal))
				+ "\n";
		stats += "--> Tarjetas en los partidos del " + this.teamLocal + " jugando como local: "
				+ df.format((cardsTeamLocalPlayingLocal / totalMatchsLocalPlayingLocal)) + "\n";
		stats += "--> Tarjetas en los partidos del " + this.teamAway + ": " + df.format((cardsTeamAway / totalMatchsAway))
				+ "\n";
		stats += "--> Tarjetas en los partidos del " + this.teamAway + " jugando como visitante: "
				+ df.format((cardsTeamAwayPlayingAway / totalMatchsAwayPlayingAway)) + "\n";
		
		if(this.referee != null) {
			stats += "\n";
			stats += "--> Tarjetas en los partidos del " + this.referee + ": " + df.format((totalCardsReferee / totalMatchsReferee))
					+ "\n";
		}
		
		log.info("Generaci�n de estad�sticas finalizada");
		return stats;
	}

}
