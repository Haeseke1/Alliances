package me.Haeseke1.Alliances.Item.Weapons.Swords.Schedulers;

import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;

import me.Haeseke1.Alliances.Item.Weapons.Swords.Sword;
import me.Haeseke1.Alliances.Main.Main;

public class CheckCooldowns implements Runnable{

	
	@Override
	public void run() {
		for(Entry<UUID,List<Sword>> entry : Sword.cooldowns.entrySet()){
			if(entry.getValue() == null || entry.getValue().isEmpty()){
				continue;
			}
			for(Sword sword : entry.getValue()){
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
					
					@Override
					public void run() {
						sword.cooldown(entry.getKey());
						
					}
				}, 1);
			}
		}
		
	}
}

