package me.Haeseke1.Alliances.Arena;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Exceptions.EmptyIntException;
import me.Haeseke1.Alliances.Exceptions.EmptyLocationException;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Utils.ConfigManager;
import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.regionSelect.regionSelect;

public class ArenaManager {
	
	private static FileConfiguration arenaConfig = Main.arenaConfig;
	public static List<Arena> arenas = new ArrayList<>();
	
  public static void createArena(Arena a, Player player){
	  /*
	   * Checks if arena does already exists
	   */
	  if(arenaExists(a.getName())){
		  MessageManager.sendMessage(player, ChatColor.RED + "This arena does already exists: " + ChatColor.GOLD + a.getName());
		  return;
	  }
	  String name = a.getName();
	  int size = a.getSize();
	  int countdown = a.getCountdown();
	  String path = "Arenas." + name.toLowerCase();
	  arenaConfig.set(path + ".size", size);
	  arenaConfig.set(path + ".countdown", countdown);
      ConfigManager.setLocationFromConfig(arenaConfig, path + ".corner1", regionSelect.getRegion(player, "left"));
      ConfigManager.setLocationFromConfig(arenaConfig, path + ".corner2", regionSelect.getRegion(player, "right"));
	  arenaConfig.set(path + ".status", a.getStatus());
	  arenaConfig.set(path + ".spawns", "");
	  arenas.add(a);
	  MessageManager.sendMessage(player, ChatColor.GREEN + "You've successfully created an arena: " + ChatColor.GOLD + name);
	  }
  /*
   * Loads the arena into the config
   */
  public static void loadArena() throws EmptyIntException, EmptyLocationException{
	  if(arenaConfig.getConfigurationSection("Arenas").getKeys(false) != null){
		  for(String name: arenaConfig.getConfigurationSection("Arenas").getKeys(false)){
			  Arena a = new Arena(name, ConfigManager.getIntFromConfig(arenaConfig ,"Arenas." + name + ".size"), ConfigManager.getIntFromConfig(arenaConfig ,"Arenas." + name + ".countdown"), ConfigManager.getLocationFromConfig(arenaConfig, "Arenas." + name + ".corner1"), ConfigManager.getLocationFromConfig(arenaConfig, "Arenas." + name + ".corner2"));
			  arenas.add(a);
		  }
		  MessageManager.sendAlertMessage("Loaded the arenas");
	  }
  }
  
  /*
   * Removes the arena from the game
   */
  public static void removeArena(String name, Player player){
	  if(arenaExists(name)){
		  arenaConfig.set("Arenas." + name.toLowerCase(), null);
		  arenas.remove(getArenaByName(name));
		  MessageManager.sendMessage(player, ChatColor.GREEN + "Successfully removed the arena");
		  return;
	  }
	  MessageManager.sendMessage(player, ChatColor.RED + "This arena doesn't exists");
  }
  
  public static boolean arenaExists(String name){
	  for(Arena a: arenas){
		  if(name.toLowerCase().equalsIgnoreCase(a.getName().toLowerCase())){
			  return true;
		  }
	  }
	  return false;
  }
  
  public static Arena getArenaByName(String name){
	  for(Arena a: arenas){
		  if(a.getName().toLowerCase().equalsIgnoreCase(name)){
			  return a;
		  }
	  }
	  return null;
  }
  
  public static void joinArena(Player player){
	  
  }
}
