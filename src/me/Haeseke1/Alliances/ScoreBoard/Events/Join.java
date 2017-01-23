package me.Haeseke1.Alliances.ScoreBoard.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.Haeseke1.Alliances.ScoreBoard.aScoreBoard;
import me.Haeseke1.Alliances.ScoreBoard.aScoreBoardManager;

public class Join implements Listener{

	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		player.setScoreboard(aScoreBoard.createScoreBoard(player));
		
	}
	
}
