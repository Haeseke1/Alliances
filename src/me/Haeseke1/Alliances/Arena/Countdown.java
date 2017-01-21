package me.Haeseke1.Alliances.Arena;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.Haeseke1.Alliances.Exceptions.EmptyLocationException;
import me.Haeseke1.Alliances.Exceptions.EmptyStringException;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Utils.MessageManager;
import net.md_5.bungee.api.ChatColor;

public class Countdown extends BukkitRunnable{
    
	private int time;
	private Arena arena;
	private FileConfiguration arenaConfig = Main.arenaConfig;
	
	public Countdown(String arenaname){
	 	this.arena = ArenaManager.getArenaByName(arenaname.toLowerCase());
	    this.time = arena.getCountdown();
	}
	
	@Override
	public void run() {
	 if(this.arena.getPlayersInArena().size() != 1){
        if(this.time == arena.getCountdown()){
        	try {
				ArenaManager.setStatus(arena.getName(), "COUNTING", null);
			} catch (EmptyLocationException e) {
				e.printStackTrace();
			}
  		  try {
  			ArenaManager.updateSign(ArenaManager.getSign(arena.getName()), arena);
  		} catch (EmptyLocationException e) {
  			e.printStackTrace();
  		} catch (EmptyStringException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        }
        if(time == 0){
        	String arenaname = this.arena.getName();
        	try {
				ArenaManager.setStatus(arenaname, "PLAYING", null);
			} catch (EmptyLocationException e) {
				e.printStackTrace();
			}
  		  try {
  			ArenaManager.updateSign(ArenaManager.getSign(arenaname), arena);
  		} catch (EmptyLocationException | EmptyStringException e) {
  			e.printStackTrace();
  		}
        }
        for(UUID playerUUID: arena.getPlayersInArena().keySet()){
        	Player player = Bukkit.getPlayer(playerUUID);
        	switch(time){
        	case 60:
        		MessageManager.sendMessage(player, ChatColor.AQUA + "The fight will start in " + ChatColor.GOLD + "1" + ChatColor.AQUA + " minute");
        		break; 
        	case 30:
        		MessageManager.sendMessage(player, ChatColor.AQUA + "The fight will start in " + ChatColor.GOLD + "30" + ChatColor.AQUA + " seconds");
        		break; 
        	case 15:
        		MessageManager.sendMessage(player, ChatColor.AQUA + "The fight will start in " + ChatColor.GOLD + "15" + ChatColor.AQUA + " seconds");
        		break;
        	case 10:
        		MessageManager.sendMessage(player, ChatColor.AQUA + "The fight will start in " + ChatColor.GOLD + "10" + ChatColor.AQUA + " seconds");
                break;
        	}
        	if(time <= 5 && time > 1){
        		MessageManager.sendMessage(player, ChatColor.AQUA + "The fight will start in " + ChatColor.GOLD + time + ChatColor.AQUA + " seconds");
        	}
        	
        	if(time == 1){
        		MessageManager.sendMessage(player, ChatColor.AQUA + "The fight will start in " + ChatColor.GOLD + "1" + ChatColor.AQUA + " second");
        	}
        	
        	if(time == 0){
        		MessageManager.sendMessage(player, ChatColor.AQUA + "Teleporting players...");  	
        	}
        	if(time == -1){
        	    try {
					ArenaManager.spawnPlayers(this.arena.getName());
				} catch (EmptyLocationException e) {
					Bukkit.broadcastMessage("error");
					this.cancel();
					e.printStackTrace();
				}
        	  }
        	}
        time --;
	  }else{
		  for(UUID playerUUID: arena.getPlayersInArena().keySet()){
			  Player player = Bukkit.getPlayer(playerUUID);
			  MessageManager.sendMessage(player, ChatColor.GREEN + "You've won the game! " + ChatColor.GOLD + "(+5 coins)");
			  player.teleport(ArenaManager.pastLocations.get(playerUUID));
			  try {
				ArenaManager.setStatus(arena.getName(), "PLAYABLE", null);
			} catch (EmptyLocationException e) {
				e.printStackTrace();
			}
			  ArenaManager.pastLocations.remove(playerUUID);
			  arena.getPlayersInArena().remove(playerUUID);
			  try {
					ArenaManager.updateSign(ArenaManager.getSign(arena.getName()), arena);
				} catch (EmptyLocationException | EmptyStringException e) {
					e.printStackTrace();
				}
		    }
		  arenaConfig.set("Arenas." + arena.getName().toLowerCase() + ".spawns.team1.alliance", "");
		  arenaConfig.set("Arenas." + arena.getName().toLowerCase() + ".spawns.team2.alliance", "");
		  Bukkit.getScheduler().cancelTask(this.getTaskId());
	  }
	}

}
