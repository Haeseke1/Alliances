package me.Haeseke1.Alliances.Crates;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import me.Haeseke1.Alliances.Main.Main;
import net.md_5.bungee.api.ChatColor;

public class KeyManager {
	
	
	public static Key getKey(String name){
		for(Key key : Key.keys){
			if(ChatColor.stripColor(key.name).equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', name)))){
				return key;
			}
		}
		return null;
	}
	
	
	public static void registerKey(){
		Key.keys = new ArrayList<Key>();
		FileConfiguration file = Main.CratesConfig;
		if(file.contains("Keys")){
			List<String> keys = file.getStringList("Keys");
			for(String key : keys){
				new Key(key);
			}
		}
		
	}
	
	
	public static void saveKey(){
		FileConfiguration file = Main.CratesConfig;
		List<String> keys = new ArrayList<String>();
		for(Key key : Key.keys){
			keys.add(key.name);
		}
		file.set("Keys", keys);		
	}
}
