package me.Haeseke1.Alliances.ScoreBoard.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;

import me.Haeseke1.Alliances.ScoreBoard.aScoreBoard;

public class Join implements Listener{

	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		if(player.getScoreboard().getObjective(DisplaySlot.SIDEBAR) != null){
			player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).unregister();
		}
		player.setScoreboard(aScoreBoard.board);
		
	}
	
}
