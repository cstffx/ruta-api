package org.xrgames.ruta.entity;

/**
 * Ocurre cuando un equipo gana una ronda.
 */
public class TeamWinsRound extends Event {
	
	public int id;
	public String teamName;
	
	public TeamWinsRound(int id, String teamName) {
		super(EventType.TEAM_WINS_GAME);
		this.id = id;
		this.teamName= teamName;
	}
	
	int getId() {
		return id;
	}
	
	public String getTeamName() {
		return this.teamName;
	}
}
