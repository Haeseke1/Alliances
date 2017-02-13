package me.Haeseke1.Alliances.Item.Weapons.Swords;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class SwordManager {
	
	
	
	public static boolean hasSword(UUID player, String name){
		if(Sword.cooldowns.containsKey(player)){
			List<Sword> swords = Sword.cooldowns.get(player);
			for(Sword sword : swords){
				if(sword.name.equalsIgnoreCase(name)){
					return true;
				}
			}
		}
		return false;
	}
	
	public static Sword getSword(UUID player, String name){
		if(Sword.cooldowns.containsKey(player)){
			List<Sword> swords = Sword.cooldowns.get(player);
			for(Sword sword : swords){
				if(sword.name.equalsIgnoreCase(name)){
					return sword;
				}
			}
		}
		return null;
	}
	
	public static void putSword(UUID player, Sword sword){
		List<Sword> swords = new ArrayList<>();
		if(Sword.cooldowns.containsKey(player)){
			swords = Sword.cooldowns.get(player);
			swords.add(sword);
			Sword.cooldowns.replace(player, swords);
		}else{
			swords.add(sword);
			Sword.cooldowns.put(player, swords);
		}
	}
	
	
	public static boolean removeSword(UUID player, Sword sword){
		List<Sword> swords = new ArrayList<>();
		if(Sword.cooldowns.containsKey(player)){
			swords = Sword.cooldowns.get(player);
			if(swords.contains(sword)){
				swords.remove(sword);
				if(swords.size() == 0){
					Sword.cooldowns.remove(player);
					return true;
				}
				Sword.cooldowns.replace(player, swords);
				return true;
			}
			return false;
		}
		return false;
	}
	
	
	
	
}
