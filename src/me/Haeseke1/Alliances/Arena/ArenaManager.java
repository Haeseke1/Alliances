package me.Haeseke1.Alliances.Arena;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Exceptions.EmptyIntException;
import me.Haeseke1.Alliances.Exceptions.EmptyLocationException;
import me.Haeseke1.Alliances.Exceptions.EmptyStringException;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Utils.ConfigManager;
import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.regionSelect.regionSelect;

public class ArenaManager {
	
	private static FileConfiguration arenaConfig = Main.arenaConfig;
	public static List<Arena> arenas = new ArrayList<>();
	public static HashMap<UUID,Location> pastLocations = new HashMap<>();
	
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
	  arenaConfig.set(path + ".spawns.team1.alliance", "");
	  arenaConfig.set(path + ".spawns.team2.alliance", "");
	  arenas.add(a);
	  MessageManager.sendMessage(player, ChatColor.GREEN + "You've successfully created an arena: " + ChatColor.GOLD + name);
	  }
  /*
   * Loads the arena into the config
   */
  public static void loadArena() throws EmptyIntException, EmptyLocationException, EmptyStringException{
     if((new File(Main.plugin.getDataFolder(),"arenas.yml")).exists()){
	  if(arenaConfig.getConfigurationSection("Arenas").getKeys(false) != null){
		  for(String name: arenaConfig.getConfigurationSection("Arenas").getKeys(false)){
			  String path = "Arenas." + name;
			  arenaConfig.set(path + ".spawns.team1.alliance", "");
			  arenaConfig.set(path + ".spawns.team2.alliance", "");
			  Arena a = new Arena(name, ConfigManager.getIntFromConfig(arenaConfig ,"Arenas." + name + ".size"), ConfigManager.getIntFromConfig(arenaConfig ,"Arenas." + name + ".countdown"), ConfigManager.getLocationFromConfig(arenaConfig, "Arenas." + name + ".corner1"), ConfigManager.getLocationFromConfig(arenaConfig, "Arenas." + name + ".corner2"));
			  arenas.add(a);
		      if(!ConfigManager.getStringFromConfig(arenaConfig, path + ".status").equalsIgnoreCase("UNDER_MAINTANCE")){
		      arenaConfig.set(path + ".status", "PLAYABLE");
		      }
		      if(arenaConfig.get(path + ".sign") != null){
		      ArenaManager.updateSign(ArenaManager.getSign(a.getName()), a);
		      }
		  }
		  MessageManager.sendRemarkMessage("Loaded the arenas");
	  }
     }
     return;
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
  public static void joinArena(Player player, String arenaname) throws EmptyStringException, EmptyLocationException{
	 Arena arena = ArenaManager.getArenaByName(arenaname);
   if(ArenaManager.arenaExists(arenaname)){
	if(checkStatus(arenaname,"PLAYABLE")){
	 if(!isInArena(player)){
	  if(AllianceManager.playerIsInAlli(player)){
		 if(ArenaManager.arenaExists(arenaname)){
		  Alliance alliance = AllianceManager.getAlliance(player);
		  Alliance al = AllianceManager.getAlliance(player);
		  if(TeamManager.teamExists(alliance, arenaname)){
		    if(ArenaManager.checkTeamSize(TeamManager.getTeamNumber(alliance, arenaname), arenaname) != (arena.getSize() / 2)){
			  arena.sendMessage(ChatColor.GOLD + player.getName() + ChatColor.GREEN + " joined the arena!",player);
		      ArenaManager.getArenaByName(arenaname).getPlayersInArena().put(player.getUniqueId(), AllianceManager.getAlliance(player));
			  MessageManager.sendMessage(player, ChatColor.GREEN + "You've successfully joined an arena");
			  pastLocations.put(player.getUniqueId(), player.getLocation());
			  player.teleport(ArenaManager.getLobby(arenaname));
			  startArena(arenaname.toLowerCase());
			  if(checkSign(arenaname)){
			  ArenaManager.updateSign(ArenaManager.getSign(arenaname), arena);
			  }
			  al.sendPlayersMessage(ChatColor.GOLD + player.getName() + ChatColor.GREEN + " queued up for an arena fight!",player);
			  return;
		    }
			  MessageManager.sendMessage(player, ChatColor.RED + "Your alliance team is full");
			  return;
		  }
		  if(TeamManager.teamIsFree(1, arenaname)){
			  arena.sendMessage(ChatColor.GOLD + player.getName() + ChatColor.GREEN + " joined the arena!",player);
			  arenaConfig.set("Arenas." + arenaname.toLowerCase() + ".spawns.team1.alliance", alliance.getName());
			  ArenaManager.getArenaByName(arenaname).getPlayersInArena().put(player.getUniqueId(), AllianceManager.getAlliance(player));
			  pastLocations.put(player.getUniqueId(), player.getLocation());
			  player.teleport(ArenaManager.getLobby(arenaname));
			  MessageManager.sendMessage(player, ChatColor.GREEN + "You've successfully joined an arena " + ChatColor.GOLD + "[" + arena.getCurrentSize() + "/" + arena.getSize() + "]");
			  startArena(arenaname.toLowerCase());
			  if(checkSign(arenaname)){
			  ArenaManager.updateSign(ArenaManager.getSign(arenaname), arena);
			  }
			  al.sendPlayersMessage(ChatColor.GOLD + player.getName() + ChatColor.GREEN + " joined your alliance in an arena fight!",player);			  return;
		  }
		  if(TeamManager.teamIsFree(2, arenaname)){
			  arena.sendMessage(ChatColor.GOLD + player.getName() + ChatColor.GREEN + " joined the arena!",player);
			  arenaConfig.set("Arenas." + arenaname.toLowerCase() + ".spawns.team2.alliance", alliance.getName());
			  ArenaManager.getArenaByName(arenaname).getPlayersInArena().put(player.getUniqueId(), AllianceManager.getAlliance(player));
			  pastLocations.put(player.getUniqueId(), player.getLocation());
			  player.teleport(ArenaManager.getLobby(arenaname));
			  MessageManager.sendMessage(player, ChatColor.GREEN + "You've successfully joined an arena " + ChatColor.GOLD + "[" + arena.getCurrentSize() + "/" + arena.getSize() + "]");
			  startArena(arenaname.toLowerCase());
			  if(checkSign(arenaname)){
			  ArenaManager.updateSign(ArenaManager.getSign(arenaname), arena);
			  }
			  al.sendPlayersMessage(ChatColor.GOLD + player.getName() + ChatColor.GREEN + " joined your alliance in an arena fight!",player);
			  return;
		  }
		  MessageManager.sendMessage(player, ChatColor.RED + "This arena is full");
		  return;
	    } 
		  MessageManager.sendMessage(player, ChatColor.RED + "This arena doesn't exists");
		 return;
	  }
	  MessageManager.sendMessage(player, ChatColor.RED + "You're not in an alliance");
	  return;
    }
	  MessageManager.sendMessage(player, ChatColor.RED + "You're already in an arena");
	  return;
	}
	  MessageManager.sendMessage(player, ChatColor.RED + "This arena isn't joinable at the moment");
	  return;
   }
	 MessageManager.sendMessage(player, ChatColor.RED + "This arena doesn't exists");
	 return;
  }
  public static void addSpawn(String name, int teamNumber, Player player){
	 if(arenaExists(name)){
	  int spawnCount = arenaConfig.getConfigurationSection("Arenas." + name.toLowerCase() + ".spawns.team" + teamNumber).getKeys(false).size();
	  if((spawnCount - 1) != (arenaConfig.getInt("Arenas." + name.toLowerCase() + ".size") / 2)){
	  ConfigManager.setLocationFromConfig(arenaConfig, "Arenas." + name.toLowerCase() + ".spawns.team" + teamNumber + "." + (spawnCount - 1), player.getLocation());
      MessageManager.sendMessage(player, ChatColor.GREEN + "You've successfully added a spawn to team " + teamNumber + ChatColor.GOLD + " #" + (spawnCount - 1));
	  return;
	  }
	  MessageManager.sendMessage(player, ChatColor.RED + "Team " + teamNumber + " has reached his maximum spawn number");
	  return;
    }
	  MessageManager.sendMessage(player, ChatColor.RED + "This arena doesn't exists");
	 return;
  }  
  public static boolean isInArena(Player player){
	  for(Arena a: arenas){
		  for(UUID playerUUID: a.getPlayersInArena().keySet()){
			  if(player.getUniqueId() == playerUUID){
				  return true;
			  }
		  }
	  }
	  return false;
  }
  public static int checkTeamSize(int team,String arenaName){
	  Arena arena = getArenaByName(arenaName);
	  int count = 0;
	  String allianceName = arenaConfig.getString("Arenas." + arenaName + ".spawns.team" + team + ".alliance");
	  for(Alliance a: arena.getPlayersInArena().values()){
		  if(a.getName().equalsIgnoreCase(allianceName)){
			  count ++;
		  }
	  }
	  return count;
  }
  public static void setLobby(String arenaname, Player player){
	 if(ArenaManager.arenaExists(arenaname)){
	  if(arenaConfig.getString("Arenas." + arenaname + ".status").equalsIgnoreCase("UNDER_MAINTANCE")){
		  ConfigManager.setLocationFromConfig(arenaConfig, "Arenas." + arenaname + ".lobby", player.getLocation());
		  MessageManager.sendMessage(player, ChatColor.GREEN + "Succesfully set the lobby for " + arenaname);
		  return;
	  }
	  MessageManager.sendMessage(player, ChatColor.RED + "This arena isn't under maintance");
	  return;
  }
	  MessageManager.sendMessage(player, ChatColor.RED + "This arena doesn't exists");
	  return;
 }
  public static boolean checkStatus(String arenaname, String status) throws EmptyStringException{
	  if(ConfigManager.getStringFromConfig(arenaConfig, "Arenas." + arenaname + ".status").equalsIgnoreCase(status)){
		  return true;
	  }
	  return false;
  }
  public static void setStatus(String arenaname, String status, Player player) throws EmptyLocationException{
	  if(ArenaManager.arenaExists(arenaname)){
		  if(status.equalsIgnoreCase("UNDER_MAINTANCE")){
			  Arena a = ArenaManager.getArenaByName(arenaname);
			  for(UUID playerUUID: a.getPlayersInArena().keySet()){
				  Player targetPlayer = Bukkit.getPlayer(playerUUID);
				  targetPlayer.teleport(pastLocations.get(playerUUID));
				  MessageManager.sendMessage(targetPlayer, ChatColor.RED + "A staff member changed the arena status to under maintance");
				  a.getPlayersInArena().remove(playerUUID);
				  pastLocations.remove(playerUUID);
			  }
			  try {
					ArenaManager.updateSign(ArenaManager.getSign(arenaname), a);
				} catch (EmptyLocationException | EmptyStringException e) {
					e.printStackTrace();
				}
			  arenaConfig.set("Arenas." + arenaname + ".status", status);
		  }
		  if(status.equalsIgnoreCase("PLAYABLE")){
			  if(checkLobby(arenaname)){
                if(checkSpawns(arenaname)){	 
				  arenaConfig.set("Arenas." + arenaname + ".status", status);
				  if(player != null){
				  MessageManager.sendMessage(player, ChatColor.GREEN + "Changed the arena status to " + status);
				  }
				 if(checkSign(arenaname)){
				  try {
						ArenaManager.updateSign(ArenaManager.getSign(arenaname), ArenaManager.getArenaByName(arenaname));
					} catch (EmptyLocationException | EmptyStringException e) {
						e.printStackTrace();
					}
				 }
				  return;
                }
  			    MessageManager.sendMessage(player, ChatColor.RED + "Their aren't enough spawns for both teams");
                return;
			  }
			  MessageManager.sendMessage(player, ChatColor.RED + "You need to set a lobby to this arena");
			  return;
		  }
		  if(status.equalsIgnoreCase("COUNTING")
				  || status.equalsIgnoreCase("PLAYING")){
			  arenaConfig.set("Arenas." + arenaname + ".status", status);
			  return;
		  }
		  MessageManager.sendMessage(player, ChatColor.GREEN + "Changed the status to " + status);
          return;
	  }
	  MessageManager.sendMessage(player, ChatColor.RED + "This arena doens't exists");
	  return;
  }
  public static boolean checkLobby(String arenaname){
	  if(arenaConfig.get("Arenas." + arenaname.toLowerCase() + ".lobby") == null)  return false;
	  return true;
  }
  public static boolean checkSpawns(String arenaname){
	  int count = 0;
	  for(String team1: arenaConfig.getConfigurationSection("Arenas." + arenaname.toLowerCase() + ".spawns.team1").getKeys(false)){
		  count ++;
	  }
	  for(String team2: arenaConfig.getConfigurationSection("Arenas." + arenaname.toLowerCase() + ".spawns.team2").getKeys(false)){
		  count ++;
	  }
	  count = count -= 2;
	  if(count == arenaConfig.getInt("Arenas." + arenaname + ".size")){
		  return true;
	  }
	  return false;
  }
  public static Location getLobby(String arenaname) throws EmptyLocationException{
	  return ConfigManager.getLocationFromConfig(arenaConfig, "Arenas." + arenaname.toLowerCase() + ".lobby");
  }
  public static void startArena(String arenaname) throws IllegalArgumentException, EmptyStringException{
	  Arena a = ArenaManager.getArenaByName(arenaname);
	if(ArenaManager.checkTeamSize(1, arenaname) + ArenaManager.checkTeamSize(2, arenaname) == a.getSize()){
	 if(ArenaManager.checkStatus(arenaname, "PLAYABLE")){
	  Countdown counter = new Countdown(arenaname);
	  counter.runTaskTimer(Main.plugin, 0L, 20L);
	  a.sendMessage(ChatColor.GREEN + "The arena is full right now");
     }
	}
  }
  public static void leaveArena(Player player) throws EmptyStringException{
	 if(ArenaManager.isInArena(player)){
		 String arenaname = ArenaManager.getArenaOfPlayer(player).getName();
	  if(ArenaManager.checkStatus(arenaname, "COUNTING")){
		  MessageManager.sendMessage(player, ChatColor.RED + "You can't leave the arena while it's counting down");
		  return;
	  }
	  if(ArenaManager.checkStatus(arenaname,"PLAYING")){
		  player.teleport(pastLocations.get(player.getUniqueId()));
		  Arena a = ArenaManager.getArenaByName(arenaname);
		  a.getPlayersInArena().remove(player.getUniqueId());
		  pastLocations.remove(player.getUniqueId());
		  if(player != null){
		  MessageManager.sendMessage(player, ChatColor.GREEN + "You've left the arena");
		  }
		  try {
			  if(checkSign(arenaname)){
			  ArenaManager.updateSign(ArenaManager.getSign(arenaname), a);
			  }
		} catch (EmptyLocationException e) {
			e.printStackTrace();
		}
		  if(!TeamManager.checkTeams(arenaname)){
		  AllianceManager.getAlliance(player).addLose(player);
		  }else{
		  a.sendMessage(ChatColor.GOLD + player.getName() + ChatColor.RED + " left the game",player);
		  AllianceManager.getAlliance(player).sendPlayersMessage(ChatColor.GOLD + player.getName() + ChatColor.RED + " left an arena fight!", player);
		  }
		  return;
	  }
     player.teleport(pastLocations.get(player.getUniqueId()));
	 Arena a = ArenaManager.getArenaByName(arenaname);
	 a.getPlayersInArena().remove(player.getUniqueId());
	 pastLocations.remove(player.getUniqueId());
	  try {
		  if(checkSign(arenaname)){
		  ArenaManager.updateSign(ArenaManager.getSign(arenaname), a);
		  }
	} catch (EmptyLocationException e) {
		e.printStackTrace();
	}
	 MessageManager.sendMessage(player, ChatColor.GREEN + "You've left the arena");
	  return;
    }
	 MessageManager.sendMessage(player, ChatColor.RED + "You aren't in an arena");
	 return;
  }
  public static void spawnPlayers(String arenaname) throws EmptyLocationException{
	  Arena a = ArenaManager.getArenaByName(arenaname);
	 for(UUID playerUUID: a.getPlayersInArena().keySet()){
		 Player player = Bukkit.getPlayer(playerUUID);
		 Alliance al = AllianceManager.getAlliance(player);
		 int count1 = 0;
		 int count2 = 0;
		 if(TeamManager.getTeamNumber(al, arenaname) == 1){
			 player.teleport(ConfigManager.getLocationFromConfig(arenaConfig, "Arenas." + arenaname.toLowerCase() + ".spawns.team1." + count1));
			 count1 ++;
		 }
		 if(TeamManager.getTeamNumber(al, arenaname) == 2){
			 player.teleport(ConfigManager.getLocationFromConfig(arenaConfig, "Arenas." + arenaname.toLowerCase() + ".spawns.team2." + count2));
			 count2 ++;
		 }
		 player.setGameMode(GameMode.SURVIVAL);
		 player.setHealth(player.getMaxHealth());
	 }
  }
  public static Arena getArenaOfPlayer(Player player){
	  for(Arena a: arenas){
		  for(UUID playerUUID: a.getPlayersInArena().keySet()){
			  if(playerUUID == player.getUniqueId()){
				  return a;
			  }
		  }
	  }
	  return null;
  }
  public static void kickOnDeath(Player player){
	  Alliance al = AllianceManager.getAlliance(player);
	  Arena arena = ArenaManager.getArenaOfPlayer(player);
	  player.teleport(pastLocations.get(player.getUniqueId()));
	  arena.getPlayersInArena().remove(player.getUniqueId());
	  pastLocations.remove(player.getUniqueId());
	  player.setHealth(player.getMaxHealth());
      if(teamIsEmpty(player,arena)){
	  al.addLose(player);
	  return;
        }
      ArenaManager.sendMessage(arena, ChatColor.GOLD + player.getName() + ChatColor.AQUA + " has been slain");
      return;
  }
  public static boolean teamIsEmpty(Player player, Arena arena){
	  Alliance al = AllianceManager.getAlliance(player);
	  for(UUID playerUUID: arena.getPlayersInArena().keySet()){
		  if(al == arena.getPlayersInArena().get(playerUUID)){
			  return false;
		  }
	  }
	  return true;
  }
  public static void sendMessage(Arena arena,String message){
	  for(UUID playerUUID: arena.getPlayersInArena().keySet()){
		  Player player = Bukkit.getPlayer(playerUUID);
		  MessageManager.sendMessage(player, message);
	  }
  }
  public static boolean checkSign(String arenaname) throws EmptyLocationException{
	  if(arenaConfig.get("Arenas." + arenaname.toLowerCase() + ".sign") != null){
		  Location loc = ConfigManager.getLocationFromConfig(arenaConfig, "Arenas." + arenaname.toLowerCase() + ".sign");
		  Block block = loc.getBlock();
		  if(block.getType() != Material.WALL_SIGN){
			  Bukkit.broadcastMessage(block.getType().toString());
			  return false;
		  }
		  return true;
	  }
	  return false;
  }
  public static Sign getSign(String arenaname) throws EmptyLocationException{ 
	 Location location = ConfigManager.getLocationFromConfig(arenaConfig, "Arenas." + arenaname.toLowerCase() + ".sign");
     if(location.getBlock() != null){
	  Block block = location.getBlock();
      if(block.getType() == Material.WALL_SIGN 
    		  || block.getType() == Material.SIGN){
    	  Sign sign = (Sign) location.getBlock().getState();
    	  return sign;
      }
      return null;
   }
     return null;
  }
  public static void updateSign(Sign sign, Arena arena) throws EmptyStringException, IndexOutOfBoundsException, EmptyLocationException{
	 if(checkSign(arena.getName())){
	  String arenaname = arena.getName();
	  arenaname = arenaname.substring(0, 1).toUpperCase() + arenaname.substring(1);
	  sign.setLine(0, ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + arenaname);
	  sign.setLine(1, ChatColor.AQUA + "" + ChatColor.BOLD + arena.getSize() / 2 + "V" + arena.getSize() / 2);
      int arenaSize = arena.getSize();
      int currentSize = arena.getPlayersInArena().size();
      ChatColor chatcolor = null;
      String status = ConfigManager.getStringFromConfig(arenaConfig, "Arenas." + arena.getName().toLowerCase() + ".status");
      switch(status){
      case "PLAYABLE":
    	  chatcolor = ChatColor.GREEN;
    	  break;
      case "COUNTING":
    	  chatcolor = ChatColor.GOLD;
    	  break;
      case "PLAYING":
    	  chatcolor = ChatColor.RED;
      case "UNDER_MAINTANCE":
    	  chatcolor = ChatColor.RED;
      default:
    	  break;
      }
      sign.setLine(2, chatcolor + "[" + currentSize + "/" + arenaSize + "]");
      sign.setLine(3, chatcolor + status);
      sign.update();
      return;
	 }
  }
}
