package me.Haeseke1.Alliances.Utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerManager {
	
	
	
	public static boolean isPlayerOnline(String name){
		name.replace(" ", "");
		for(Player player : Bukkit.getOnlinePlayers()){
			if(player.getName().equalsIgnoreCase(name)){
				return true;
			}
		}
		return false;
	}
	
	public static Player getPlayer(String name){
		for(Player player : Bukkit.getOnlinePlayers()){
			if(player.getName().equalsIgnoreCase(name)){
				return player;
			}
		}
		return null;
	}
}
