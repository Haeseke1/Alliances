package me.Haeseke1.Alliances.APlayer;

import java.io.File;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Utils.ConfigManager;

public class APlayerManager {

	
	
	public static aPlayer getAPlayer(Player player){
		for(aPlayer aplayer : aPlayer.online_Players){
			if(aplayer.player.equals(player)){
				return aplayer;
			}
		}
		return null;
	}
	
	
	@SuppressWarnings("static-access")
	public static void aPlayerStartUp(){
		for(Player player : Bukkit.getOnlinePlayers()){
			File f = new File(Main.plugin.getDataFolder(), "PlayerData");
			if(!f.exists()){
				f.mkdir();
			}
			Bukkit.broadcastMessage(Main.plugin.getDataFolder().getName());
			f = new File(Main.plugin.getDataFolder() + "PlayerData", player.getUniqueId().toString() + ".yml");
			FileConfiguration file = YamlConfiguration.loadConfiguration(f);
			aPlayer APlayer = new aPlayer(player, file);
			APlayer.online_Players.add(APlayer);
		}
	}
	
	public static void aPlayerSave(){
		for(Player player: Bukkit.getOnlinePlayers()){
			aPlayer aPlayer = APlayerManager.getAPlayer(player);
			aPlayer.saveConfig();
		}
	}
	
	@SuppressWarnings("deprecation")
	public FileConfiguration getConfig(String name){
		OfflinePlayer offline = Bukkit.getOfflinePlayer(name);
		offline.getUniqueId();
		File f = new File(Main.plugin.getDataFolder(), "PlayerData");
		if(!f.exists()){
			f.mkdir();
		}
		f = new File(f, offline.getUniqueId().toString() + ".yml");
		return YamlConfiguration.loadConfiguration(f);
	}
	
	public void saveConfig(FileConfiguration file, UUID uuid){
		File f = new File(Main.plugin.getDataFolder() + File.separator + "PlayerData",uuid.toString() + ".yml");
 	    ConfigManager.saveCustomConfig(f,file);
	}

}
