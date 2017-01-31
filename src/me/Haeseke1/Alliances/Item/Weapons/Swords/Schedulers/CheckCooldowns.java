package me.Haeseke1.Alliances.Item.Weapons.Swords.Schedulers;

import java.util.List;
import java.util.Map.Entry;

import me.Haeseke1.Alliances.Item.Weapons.Swords.Sword;

import java.util.UUID;

public class CheckCooldowns implements Runnable{

	
	@Override
	public void run() {
		for(Entry<UUID,List<Sword>> entry : Sword.cooldowns.entrySet()){
			for(Sword sword : entry.getValue()){
				sword.cooldown(entry.getKey());
			}
		}
		
	}
}

