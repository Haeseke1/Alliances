package me.Haeseke1.Alliances.Arena;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.Haeseke1.Alliances.Economy.Coins;
import me.Haeseke1.Alliances.Exceptions.EmptyLocationException;
import me.Haeseke1.Alliances.Exceptions.EmptyStringException;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Utils.ConfigManager;
import me.Haeseke1.Alliances.Utils.MessageManager;
import net.md_5.bungee.api.ChatColor;

public class ArenaEvents implements Listener{

	private static FileConfiguration arenaConfig = Main.arenaConfig;
	
	@EventHandler
	public void onBreakInArena(BlockBreakEvent event){
		Player player = event.getPlayer();
		if(ArenaManager.isInArena(player)){
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void onPlaceInArena(BlockPlaceEvent event){
		Player player = event.getPlayer();
		if(ArenaManager.isInArena(player)){
			event.setCancelled(true);
		}
	}	
	@EventHandler
	public void onDamage(EntityDamageEvent event) throws EmptyStringException{
	  if(event.getEntity() instanceof Player){
		  Player player = (Player) event.getEntity();
		 if(ArenaManager.isInArena(player)){
		  Arena arena = ArenaManager.getArenaOfPlayer(player);
		  if(ArenaManager.checkStatus(arena.getName(), "COUNTING")){
			  event.setCancelled(true);
			  return;
		  }
		  if(player.getHealth() < event.getDamage()){
			  try {
				  if(ArenaManager.checkSign(arena.getName())){
					  ArenaManager.updateSign(ArenaManager.getSign(arena.getName()), arena);
					  }
				} catch (EmptyLocationException e) {
					e.printStackTrace();
				}
			  if(event.getCause() != DamageCause.ENTITY_ATTACK){
			  ArenaManager.kickOnDeath(player);
			  event.setCancelled(true);
			  }
		  }
	    }
	  }
	}
	@EventHandler
	public void onDamageByEntity(EntityDamageByEntityEvent event) throws EmptyStringException{
		if(event.getEntity() instanceof Player){
		  Player player = (Player) event.getEntity();
		 if(ArenaManager.isInArena(player)){
		  Arena arena = ArenaManager.getArenaOfPlayer(player);
		  if(ArenaManager.checkStatus(arena.getName(), "COUNTING")){
			  event.setCancelled(true);
			  return;
		  }
		  if(player.getHealth() < event.getDamage()){
			  if(event.getDamager().getType() == EntityType.PLAYER){
				  Bukkit.broadcastMessage("test");
				  Player attacker = (Player) event.getDamager();
				  Coins.addPlayerCoins(attacker, Main.settingsConfig.getInt("Arena_kill_player_reward"));
				  MessageManager.sendMessage(attacker, ChatColor.GREEN + "You've slain " + ChatColor.GOLD + player.getName() + " (+" + Main.settingsConfig.getInt("Arena_kill_player_reward") + " coins)");
			      arena.sendMessage(ChatColor.GOLD + player.getName() + ChatColor.AQUA + " has been slain by " + ChatColor.GOLD + attacker.getName(), attacker);
				  attacker.playSound(attacker.getLocation(), Sound.FIREWORK_BLAST, 1, 1);
			  try {
				  if(ArenaManager.checkSign(arena.getName())){
					  ArenaManager.updateSign(ArenaManager.getSign(arena.getName()), arena);
					  }
				} catch (EmptyLocationException e) {
					e.printStackTrace();
				}
			  ArenaManager.kickOnDeath(player);
			  event.setCancelled(true);
		   }
		  }
	    }
	  }
	}
   @EventHandler
    public void onSignCreate(SignChangeEvent event) throws EmptyStringException{
	   Player player = event.getPlayer();
		   String arenasign = event.getLine(0);
		   String arenaname = event.getLine(1).toLowerCase();
		  if(arenasign.equalsIgnoreCase("[arena]")){
			  if(ArenaManager.arenaExists(arenaname)){
				  Arena arena = ArenaManager.getArenaByName(arenaname);
				  arenaname = arenaname.substring(0, 1).toUpperCase() + arenaname.substring(1);
				  event.setLine(0, ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + arenaname);
				  event.setLine(1, ChatColor.AQUA + "" + ChatColor.BOLD + arena.getSize() / 2 + "V" + arena.getSize() / 2);
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
                  event.setLine(2, chatcolor + "[" + currentSize + "/" + arenaSize + "]");
                  event.setLine(3, chatcolor + status);
                  saveSign(arena.getName(),event.getBlock().getLocation());
                  MessageManager.sendMessage(player, ChatColor.GREEN + "Successfully created an arena join sign");
			      return;
			  }
			  MessageManager.sendMessage(player, ChatColor.RED + "This arena doesn't exists");
			  event.setCancelled(true);
		  }
   }
    @EventHandler 
    public void onRightClick(PlayerInteractEvent event){
    	if(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK){
    		Block block = event.getClickedBlock();
    		if(block.getType() == Material.WALL_SIGN){
    			Sign sign = (Sign) block.getState();
    			String arenaname = ChatColor.stripColor(sign.getLine(0));
    		  if(ArenaManager.arenaExists(arenaname)){
    			Player player = event.getPlayer();
    			try {
					ArenaManager.joinArena(player, arenaname.toLowerCase());
				} catch (EmptyStringException | EmptyLocationException e) {					e.printStackTrace();
				}
    		  }
    		}
    	}
    }
    public static void saveSign(String arenaname, Location loc){
	   ConfigManager.setLocationFromConfig(arenaConfig, "Arenas." + arenaname.toLowerCase() + ".sign", loc);
   }
    @EventHandler
    public void onLeave(PlayerQuitEvent event) throws EmptyStringException{
    	Player player = event.getPlayer();
    	if(ArenaManager.isInArena(player)){
    	  Arena arena = ArenaManager.getArenaOfPlayer(player);
    	   if(ArenaManager.checkStatus(arena.getName(), "COUNTING")){
    		   player.teleport(ArenaManager.pastLocations.get(player.getUniqueId()));
    		   ArenaManager.pastLocations.remove(player.getUniqueId());
    		   arena.getPlayersInArena().remove(player.getUniqueId());
    		   arena.sendMessage(ChatColor.GOLD + player.getName() + ChatColor.RED + " left! So we stopped the game");
    		   arena.teleportToPast();
    		   arena.getPlayersInArena().clear();
    		   ArenaManager.pastLocations.clear();
    		   Main.arenaConfig.set("Arenas." + arena.getName().toLowerCase() + ".spawns.team1.alliance", "");
    		   Main.arenaConfig.set("Arenas." + arena.getName().toLowerCase() + ".spawns.team2.alliance", "");
    	   }
    		try {
				ArenaManager.leaveArena(player);
			} catch (EmptyStringException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
}
