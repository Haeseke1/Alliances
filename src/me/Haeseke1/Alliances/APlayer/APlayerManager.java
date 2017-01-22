package me.Haeseke1.Alliances.APlayer;

import org.bukkit.entity.Player;

public class APlayerManager {

	
	
	public static aPlayer getAPlayer(Player player){
		for(aPlayer aplayer : aPlayer.online_Players){
			if(aplayer.player.equals(player)){
				return aplayer;
			}
		}
		return null;
	}
}
