package me.Haeseke1.Alliances.Arena;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Utils.ConfigManager;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class ArenaManager {
	
	private FileConfiguration arenaConfig = Main.arenaConfig;
	
  public void createArena(Arena a, Player player){
	  /*
	   * Checks if arena does already exists
	   */
	  for(String arenaName: arenaConfig.getConfigurationSection("Arenas").getKeys(false)){
		  if(a.getName().equalsIgnoreCase(arenaName)){
			  MessageManager.sendMessage(player, ChatColor.RED + "This arena does already exists: " + ChatColor.GOLD + arenaName);
			  return;
		  }
	  }
	  String name = a.getName();
	  int size = a.getSize();
	  int countdown = a.getCountdown();
	  String path = "Arenas." + name;
	  
	  arenaConfig.set(path + ".size", size);
	  arenaConfig.set(path + ".countdown", countdown);
	  arenaConfig.set(path + ".corner1", a.getCorner1());
	  arenaConfig.set(path + ".corner2", a.getCorner2());
	  arenaConfig.set(path + ".status", a.getStatus());
	  ConfigManager.saveCustomConfig(Main.arenaFile, arenaConfig);
	  MessageManager.sendMessage(player, ChatColor.GREEN + "You've successfully created an arena: " + ChatColor.GOLD + name);
	  }
}
