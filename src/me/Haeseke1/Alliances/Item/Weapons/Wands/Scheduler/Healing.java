package me.Haeseke1.Alliances.Item.Weapons.Wands.Scheduler;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Item.Totems.HealingTotem;

public class Healing implements Runnable{

	@Override
	public void run() {
		for(Player player: Bukkit.getOnlinePlayers()){
			for(HealingTotem totem: HealingTotem.htotems.values()){
				if(player.getName().equalsIgnoreCase(totem.owner.getName())){ totem.heal(player); continue;}
				Alliance alliance = AllianceManager.getAlliance(totem.owner);
				if(alliance.getName().equalsIgnoreCase(AllianceManager.getAlliance(player).getName())){
					totem.heal(player);
				}
				continue;
			}
		}
	}
}
