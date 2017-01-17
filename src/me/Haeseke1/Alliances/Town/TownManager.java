package me.Haeseke1.Alliances.Town;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Exp.Exp;

public class TownManager {
	
	
	public static void createTown(Player player, Alliance alli, Chunk chunk){
		if(!alli.getOwner().equals(player.getUniqueId())){
			
			return;
		}
		if(alli.getTown() != null){
			
			return;
		}
		if(Exp.getLevel(alli.getExp()) < 3){
			
			return;
		}
		
		alli.setTown(new Town(chunk, alli));
	}
	
	
	
}
