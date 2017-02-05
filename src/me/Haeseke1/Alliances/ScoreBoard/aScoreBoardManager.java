package me.Haeseke1.Alliances.ScoreBoard;


import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

import me.Haeseke1.Alliances.APlayer.aPlayer;

public class aScoreBoardManager {

	
	public static void setScore(aPlayer aplayer, String scorename, int score, Objective obj, String color, String value) {
		Score online = obj.getScore(scorename);
		online.setScore(score);
		if (value != null) {
			Score valueS = obj.getScore(value);
			valueS.setScore(score - 1);
			Score space = obj.getScore(color.toString());
			space.setScore(score - 2);
			aplayer.addScore(scorename);
			aplayer.addScore(value);
			aplayer.addScore(color.toString());
			return;
		}
		Score space = obj.getScore(color.toString());
		space.setScore(score - 1);
		aplayer.addScore(scorename);
		aplayer.addScore(color.toString());
		return;
	}
	
	public static void updateScore(aPlayer aplayer, String scorename, int score, Objective obj, String color, String value) {
		if(aplayer.scores.contains(value) && aplayer.scores.contains(scorename)){
			return;
		}
		aplayer.resetScore();
		Score online = obj.getScore(scorename);
		online.setScore(score);
		if (value != null) {
			Score valueS = obj.getScore(value);
			valueS.setScore(score - 1);
			Score space = obj.getScore(color);
			space.setScore(score - 2);
			aplayer.addScore(scorename);
			aplayer.addScore(value);
			aplayer.addScore(color);
			return;
		}
		Score space = obj.getScore(color);
		space.setScore(score - 1);
		aplayer.addScore(scorename);
		aplayer.addScore(color);
		return;
	}
}
