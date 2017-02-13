package me.Haeseke1.Alliances.Item.Weapons.Wands.Scheduler;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.APlayer.APlayerManager;
import me.Haeseke1.Alliances.APlayer.aPlayer;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class ManaRegen implements Runnable{

	@Override
	public void run() {
       for(Player player: Bukkit.getOnlinePlayers()){
    	   aPlayer APlayer = APlayerManager.getAPlayer(player);
    	   APlayer.addMana(APlayer.manaregen);
       }
	}

}
