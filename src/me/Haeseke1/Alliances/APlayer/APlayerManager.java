package me.Haeseke1.Alliances.APlayer;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Main.Main;

public class APlayerManager {

	
	
	public static aPlayer getAPlayer(Player player){
		for(aPlayer aplayer : aPlayer.online_Players){
			if(aplayer.player.equals(player)){
				return aplayer;
			}
		}
		return null;
	}
	
	
	public static void aPlayerStartUp(){
		for(Player player : Bukkit.getOnlinePlayers()){
			File f = new File(Main.plugin.getDataFolder(), "PlayerData");
			if(!f.exists()){
				f.mkdir();
			}
			f = new File(f, player.getUniqueId().toString() + ".yml");
			FileConfiguration file = YamlConfiguration.loadConfiguration(f);
			new aPlayer(player, f, file);
		}
	}
	

}
