package com.ruinastats.loader.data;

/**
 * Representa a un partido de una jornada
 * 
 * @author Fali
 *
 */
public class MatchData {

	/** Identificador de partido (EQUIPO-LOCAL_EQUIPO-VISITANTE) */
	private String idMatch;

	/** Número de jornada */
	private int matchWeek;

	/** Equipo local */
	private String localTeam;

	/** Equipo visitante */
	private String awayTeam;

	/** Número de goles del equipo local */
	private int localGoals;

	/** Número de goles del equipo visitante */
	private int awayGoals;

	/** Número de corners del equipo local */
	private int localCorner;

	/** Número de corners del equipo visitante */
	private int awayCorner;

	/** Número de tarjetas amarillas del equipo local */
	private int localYellowCard;

	/** Número de tarjetas amarillas del equipo visitante */
	private int awayYellowCard;

	/** Número de tarjetas rojas del equipo local */
	private int localRedCard;

	/** Número de tarjetas rojas del equipo visitante */
	private int awayRedCard;

	/** Árbitro del partido */
	private String referee;

	/**
	 * Constructor
	 */
	public MatchData() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param idMatch
	 * @param numJornada
	 * @param localTeam
	 * @param awayTeam
	 * @param localGoals
	 * @param awayGoals
	 * @param localCorner
	 * @param awayCorner
	 * @param localYellowCard
	 * @param awayYellowCard
	 * @param localRedCard
	 * @param awayRedCard
	 * @param referee
	 */
	public MatchData(String idMatch, int matchWeek, String localTeam, String awayTeam, int localGoals, int awayGoals,
			int localCorner, int awayCorner, int localYellowCard, int awayYellowCard, int localRedCard, int awayRedCard,
			String referee) {
		this.idMatch = idMatch;
		this.matchWeek = matchWeek;
		this.localTeam = localTeam;
		this.awayTeam = awayTeam;
		this.localGoals = localGoals;
		this.awayGoals = awayGoals;
		this.localCorner = localCorner;
		this.awayCorner = awayCorner;
		this.localYellowCard = localYellowCard;
		this.awayYellowCard = awayYellowCard;
		this.localRedCard = localRedCard;
		this.awayRedCard = awayRedCard;
		this.referee = referee;
	}

	/**
	 * @return the idMatch
	 */
	public String getIdMatch() {
		return idMatch;
	}

	/**
	 * @param idMatch
	 *            the idMatch to set
	 */
	public void setIdMatch(String idMatch) {
		this.idMatch = idMatch;
	}

	/**
	 * @return the numJornada
	 */
	public int getMatchWeek() {
		return matchWeek;
	}

	/**
	 * @param numJornada
	 *            the numJornada to set
	 */
	public void setMatchWeek(int matchWeek) {
		this.matchWeek = matchWeek;
	}

	/**
	 * @return the localTeam
	 */
	public String getLocalTeam() {
		return localTeam;
	}

	/**
	 * @param localTeam
	 *            the localTeam to set
	 */
	public void setLocalTeam(String localTeam) {
		this.localTeam = localTeam;
	}

	/**
	 * @return the awayTeam
	 */
	public String getAwayTeam() {
		return awayTeam;
	}

	/**
	 * @param awayTeam
	 *            the awayTeam to set
	 */
	public void setAwayTeam(String awayTeam) {
		this.awayTeam = awayTeam;
	}

	/**
	 * @return the localGoals
	 */
	public int getLocalGoals() {
		return localGoals;
	}

	/**
	 * @param localGoals
	 *            the localGoals to set
	 */
	public void setLocalGoals(int localGoals) {
		this.localGoals = localGoals;
	}

	/**
	 * @return the awayGoals
	 */
	public int getAwayGoals() {
		return awayGoals;
	}

	/**
	 * @param awayGoals
	 *            the awayGoals to set
	 */
	public void setAwayGoals(int awayGoals) {
		this.awayGoals = awayGoals;
	}

	/**
	 * @return the localCorner
	 */
	public int getLocalCorner() {
		return localCorner;
	}

	/**
	 * @param localCorner
	 *            the localCorner to set
	 */
	public void setLocalCorner(int localCorner) {
		this.localCorner = localCorner;
	}

	/**
	 * @return the awayCorner
	 */
	public int getAwayCorner() {
		return awayCorner;
	}

	/**
	 * @param awayCorner
	 *            the awayCorner to set
	 */
	public void setAwayCorner(int awayCorner) {
		this.awayCorner = awayCorner;
	}

	/**
	 * @return the localYellowCard
	 */
	public int getLocalYellowCard() {
		return localYellowCard;
	}

	/**
	 * @param localYellowCard
	 *            the localYellowCard to set
	 */
	public void setLocalYellowCard(int localYellowCard) {
		this.localYellowCard = localYellowCard;
	}

	/**
	 * @return the awayYellowCard
	 */
	public int getAwayYellowCard() {
		return awayYellowCard;
	}

	/**
	 * @param awayYellowCard
	 *            the awayYellowCard to set
	 */
	public void setAwayYellowCard(int awayYellowCard) {
		this.awayYellowCard = awayYellowCard;
	}

	/**
	 * @return the localRedCard
	 */
	public int getLocalRedCard() {
		return localRedCard;
	}

	/**
	 * @param localRedCard
	 *            the localRedCard to set
	 */
	public void setLocalRedCard(int localRedCard) {
		this.localRedCard = localRedCard;
	}

	/**
	 * @return the awayRedCard
	 */
	public int getAwayRedCard() {
		return awayRedCard;
	}

	/**
	 * @param awayRedCard
	 *            the awayRedCard to set
	 */
	public void setAwayRedCard(int awayRedCard) {
		this.awayRedCard = awayRedCard;
	}

	/**
	 * @return the referee
	 */
	public String getReferee() {
		return referee;
	}

	/**
	 * @param referee
	 *            the referee to set
	 */
	public void setReferee(String referee) {
		this.referee = referee;
	}

	@Override
	public String toString() {
		return "Jornada " + this.matchWeek + ": " + this.localTeam + " - " + this.awayTeam + " (" + this.localGoals
				+ "-" + this.awayGoals + ") -> Tarjetas: " + this.localYellowCard + "(" + this.localRedCard + ")-"
				+ this.awayYellowCard + "(" + this.awayRedCard + ") [" + this.referee + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean eq = false;
		if(obj != null && obj instanceof MatchData) {
			eq = this.getIdMatch().equals(((MatchData)obj).getIdMatch());
		}
		return eq;
	}

}
