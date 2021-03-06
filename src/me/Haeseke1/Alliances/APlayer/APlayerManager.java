package me.Haeseke1.Alliances.APlayer;

import java.io.File;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Main.SQL;
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
	
	
	public static void aPlayerStartUp(){
		for(Player player : Bukkit.getOnlinePlayers()){
			if(SQL.SQL){
				new aPlayer(player);
				continue;
			}
			File f = new File(Main.plugin.getDataFolder(), "PlayerData");
			if(!f.exists()){
				f.mkdir();
			}
			f = new File(Main.plugin.getDataFolder() + "PlayerData", player.getUniqueId().toString() + ".yml");
			FileConfiguration file = YamlConfiguration.loadConfiguration(f);
			new aPlayer(player, file);
		}
	}
	
	public static void aPlayerSave(){
		for(Player player: Bukkit.getOnlinePlayers()){
			aPlayer aPlayer = APlayerManager.getAPlayer(player);
			if(SQL.SQL){
				aPlayer.saveSQL();
				continue;
			}
			aPlayer.saveConfig();
		}
	}
	
	@SuppressWarnings("deprecation")
	public static FileConfiguration getConfig(String name){
		OfflinePlayer offline = Bukkit.getOfflinePlayer(name);
		offline.getUniqueId();
		File f = new File(Main.plugin.getDataFolder(), "PlayerData");
		if(!f.exists()){
			f.mkdir();
		}
		f = new File(f, offline.getUniqueId().toString() + ".yml");
		return YamlConfiguration.loadConfiguration(f);
	}
	
	public static void saveConfig(FileConfiguration file, UUID uuid){
		File f = new File(Main.plugin.getDataFolder() + File.separator + "PlayerData",uuid.toString() + ".yml");
 	    ConfigManager.saveCustomConfig(f,file);
	}

}
