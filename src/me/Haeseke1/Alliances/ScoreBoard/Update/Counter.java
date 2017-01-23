package me.Haeseke1.Alliances.ScoreBoard.Update;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.Haeseke1.Alliances.ScoreBoard.aScoreBoard;

public class Counter extends BukkitRunnable{

	public Counter(){
		
	}
	
	@Override
	public void run() {
		for(Player player: Bukkit.getOnlinePlayers()){
		aScoreBoard.setAllianceScoreBoard(player);
		}
	}

}
