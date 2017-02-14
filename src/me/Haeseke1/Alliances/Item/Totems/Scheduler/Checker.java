package me.Haeseke1.Alliances.Item.Totems.Scheduler;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Item.Totems.HealingTotem;

public class Checker implements Runnable{

	@Override
	public void run() {
        for(Player player: Bukkit.getOnlinePlayers()){
        	if(HealingTotem.htotems.containsKey(player.getUniqueId())){
        		if(player.getLocation().distance(HealingTotem.htotems.get(player.getUniqueId()).block.getLocation()) > 20){
        	    HealingTotem.htotems.get(player.getUniqueId()).removeTotem();
        		}
        	}
        }
	}

}
