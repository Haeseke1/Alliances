package me.Haeseke1.Alliances.Arena;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Exceptions.EmptyIntException;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Utils.ConfigManager;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class ArenaManager {
	
	private static FileConfiguration arenaConfig = Main.arenaConfig;
	public static List<Arena> arenas = new ArrayList<>();
	
  public static void createArena(Arena a, Player player){
	  /*
	   * Checks if arena does already exists
	   */
	  for(Arena arena: ArenaManager.arenas){
		  if(a.getName().equalsIgnoreCase(arena.getName())){
			  MessageManager.sendMessage(player, ChatColor.RED + "This arena does already exists: " + ChatColor.GOLD + arena.getName());
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
	  arenaConfig.set(path + ".spawns", "");
	  arenas.add(a);
	  MessageManager.sendMessage(player, ChatColor.GREEN + "You've successfully created an arena: " + ChatColor.GOLD + name);
	  }
  public static void loadArena() throws EmptyIntException{
	  if(arenaConfig.getConfigurationSection("Arenas").getKeys(false) != null){
		  for(String name: arenaConfig.getConfigurationSection("Arenas").getKeys(false)){
			  Arena a = new Arena(name, ConfigManager.getIntFromConfig(arenaConfig ,"Arenas." + name + ".size"), 0, null, null);
		  }
	  }
  }
}
